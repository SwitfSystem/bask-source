package com.cloud.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.cloud.bean.Driver;
import com.cloud.bean.Task;
import com.cloud.bean.TaskRecord;
import com.cloud.bean.User;
import com.cloud.config.VerifyToken;
import com.cloud.mapper.DriverMapper;
import com.cloud.mapper.TaskMapper;
import com.cloud.mapper.UserMapper;
import com.cloud.netty.NettyServerSenssion;
import com.cloud.netty.message.ResponseMessage;
import com.cloud.util.DateUtils;
import com.cloud.util.PageUtil;
import com.cloud.util.SysResult;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@Service
public class TaskService {
	private static Logger log = LoggerFactory.getLogger("TASK");
	
	@Resource(name="threadPool")
	private ExecutorService executor;
	
	@Resource(name="nettySenssion")
	private NettyServerSenssion nettySenssion;
	
	@Autowired
	private TaskMapper taskMapper;
	
	@Autowired
	private DriverMapper driverMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	public SysResult newTask(Integer id, Integer platform, String content, String code, Integer num, Integer method, Long time) {
		// 批量发布新任务
		int free = nettySenssion.free();
		if(num>free) {
			return new SysResult(1,"任务发布失败,任务数量过大",false,null);
		}
		ResponseMessage response = new ResponseMessage();
		response.setCode(Integer.parseInt(code));
		response.setContent(content);
		response.setMsg("执行新任务");
		response.setPlatform(platform);
		TextWebSocketFrame tw = new TextWebSocketFrame(JSON.toJSONString(response));
		int i = 0;
		String[] excuteUser = new String[num];
		Iterator<Map.Entry<String, Task>> it = nettySenssion.getFreeTaskMap();
		while(it.hasNext()) {
			if(i == num) {
				break;
			}
			Map.Entry<String, Task> entry = it.next();
			String phone = entry.getKey();
			Task task = entry.getValue();
			task.setPlatform(platform);
			task.setContent(content);
			task.setStatus(0);
			task.setStarttime(DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
			task.setCode(Integer.parseInt(code));
			executor.execute(new Runnable() {
				@Override
				public void run() {
					nettySenssion.send(phone, tw);
					Integer taskid = taskMapper.insertTaskUser(task);
					task.setId(taskid);
					nettySenssion.addTask(phone, task);
					Driver driver = driverMapper.selectDriverByPhone(phone);
					if(driver == null) {
						driver = new Driver();
						User user = userMapper.selectByPhone(phone);
						driver.setUsername(user.getUsername());
						driver.setPhone(phone);
						driver.setIp(task.getIp());
						driver.setMac(task.getMac());
						driver.setOs(task.getOs());
						driver.setPlatform(task.getPlatform());
						driver.setStatus(task.getStatus()+"");
						driver.setLasttime(DateUtils.date2String(new Date()));
						driver.setCounttime(DateUtils.date2String(new Date()));
						driverMapper.insertDriver(driver);
					}else {
						driver.setIp(task.getIp());
						driver.setMac(task.getMac());
						driver.setOs(task.getOs());
						driver.setPlatform(task.getPlatform());
						driver.setStatus(task.getStatus()+"");
						driver.setLasttime(DateUtils.date2String(new Date()));
						driver.setCounttime(DateUtils.date2String(new Date()));
						driverMapper.updateDriver(driver);
					}
				}
			});
			excuteUser[i] = phone;
			i = i + 1;
		}
		StringBuffer buf = new StringBuffer();
		for(String phone:excuteUser) {
			buf.append(phone).append(",");
		}
		TaskRecord t = new TaskRecord();
		t.setTask_create_user(id);
		t.setTask_start_time(new Date());
		t.setTask_end_time(new Date());
		t.setTask_status(0);
		t.setTask_user_count(num);
		t.setTask_complete_count(0);
		t.setTask_id(code);
		t.setTask_byexecute_user(buf.toString());
		t.setTask_prameter(null);
		
		taskMapper.insertTask(t);
		log.info("发布新任务 任务内容={} 任务数量={}",content,num);
		return new SysResult(0,"任务发布成功",true,null);
	}

	public SysResult flush() {
		Map<String,Object> map = new ConcurrentHashMap<String,Object>();
		ArrayList<Task> list = new ArrayList<Task>();
		int free = nettySenssion.free();
		int run = nettySenssion.running();
		int total = nettySenssion.total();
		map.put("total", total);
		map.put("free", free);
		map.put("run", run);
		log.info("设备在线情况: {}-{}-{}",free,run,total);
		Iterator<Map.Entry<String, Task>> it = nettySenssion.getRunningTaskMap();
		while(it.hasNext()) {
			Map.Entry<String, Task> entry = it.next();
			Task value = entry.getValue();
			list.add(value);
		}
		map.put("list", list);
		return new SysResult(0,"任务监测...",true,map);
	}

	public void updateTask(String phone,String token,Integer code) {
		// 客户端完成任务释放
		Task task = nettySenssion.removeTask(phone);
		if(code == 0) {
			log.info("用户任务完成 用户={} 任务内容={}",phone,task.getContent());
			TaskRecord ot = taskMapper.selectTask(task.getCode());
			int a = ot.getTask_complete_count() + 1;
			if(ot.getTask_user_count() == a) {
				ot.setTask_complete_count(a);
				ot.setTask_status(1);
				ot.setTask_end_time(new Date());
				taskMapper.updateTask(ot);
				log.info("总任务完成 任务内容={}",task.getContent());
				return;
			}
			ot.setTask_complete_count(a);
			taskMapper.updateTask(ot);
			task.setEndtime(DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
			taskMapper.updateTaskUser(task);
			executor.execute(new Runnable() {
				@Override
				public void run() {
					Integer status = 1;
					Integer total = 0;
					Double balance = 0d;
					ArrayList<Task> list = taskMapper.selectAllTaskToBill(phone,status);
					for(Task t:list) {
						String start = t.getStarttime();
						String end = t.getEndtime();
						Long time = DateUtils.compareTo(start, end, "yyyy-MM-dd HH:mm:ss");
						int r = (int)(time / 60000);//分钟
						total = total + r;
					}
					User user = userMapper.selectByPhone(phone);
					Integer agentlevel = user.getAgentlevel();
					Integer viplevel = user.getViplevel();
					balance = VerifyToken.computer(agentlevel,viplevel,0);
					user.setBalance(balance);
					userMapper.updateBalance(user);
				}
			});
		}else {
			log.error("用户任务失败 用户={} 任务内容={}",phone,task.getContent());
		}
	}
	/**
	 * 管理员获取所有任务列表
	 * @param page
	 * @param status
	 * @return
	 */
	public SysResult allTask(Integer page,Integer status) {
		if(page == null) {
			page = 1;
		}
		int count = 0;
		PageUtil<TaskRecord> pageUtil = null;
		ArrayList<TaskRecord> list = null;
		if(status==0||status==1||status==2) {
			count = taskMapper.selectTaskRecordByCountAndStatus(status);
			pageUtil = new PageUtil<TaskRecord>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = taskMapper.selectAllTaskRecordByStatus(start,end,status);
		}else{
			count = taskMapper.selectTaskRecordByCount();
			pageUtil = new PageUtil<TaskRecord>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = taskMapper.selectAllTaskRecord(start,end);
		}
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}
	/**
	 * 用户获取任务列表
	 * @param page
	 * @param status 0=执行中 1=已完成 2=排队中 -1=已结算
	 * @return
	 */
	public SysResult allTask(Integer page,Integer status,User user) {
		if(page == null) {
			page = 1;
		}
		int count = 0;
		PageUtil<Task> pageUtil = null;
		ArrayList<Task> list = null;
		if(status==0||status==1||status==2||status==-1) {
			count = taskMapper.selectTaskByCountAndStatus(user.getPhone(),status);
			pageUtil = new PageUtil<Task>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = taskMapper.selectAllTaskByStatus(user.getPhone(),start,end,status);
		}else{
			count = taskMapper.selectTaskByCount(user.getPhone());
			pageUtil = new PageUtil<Task>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = taskMapper.selectAllTask(user.getPhone(),start,end);
		}
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}
}
