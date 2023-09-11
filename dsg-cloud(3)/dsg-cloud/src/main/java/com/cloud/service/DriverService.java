package com.cloud.service;

import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.bean.Driver;
import com.cloud.bean.Note;
import com.cloud.bean.User;
import com.cloud.mapper.DriverMapper;
import com.cloud.util.DateUtils;
import com.cloud.util.PageUtil;
import com.cloud.util.SysResult;

@Service
public class DriverService {
	
	private static Logger log = LoggerFactory.getLogger("DRIVER");
	
	@Autowired
	private DriverMapper driverMapper;
	/**
	 * 管理员获取所有设备
	 * @param page
	 * @return
	 */
	public SysResult getDriverList(Integer page) {
		if(page == null) {
			page = 1;
		}
		int count = driverMapper.selectDriverByCount();
		PageUtil<Driver> pageUtil = new PageUtil<Driver>(20,count,page);
		int start = pageUtil.getStart();
		int end = pageUtil.getEnd();
		ArrayList<Driver> list = driverMapper.selectAll(start,end);
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}
	/**
	 * 用户获取自己的设备信息
	 * @param page
	 * @return
	 */
	public SysResult getDriverList(Integer page,User user) {
		if(page == null) {
			page = 1;
		}
		int count = 1;
		PageUtil<Driver> pageUtil = new PageUtil<Driver>(20,count,page);
		Driver driver = driverMapper.selectDriverByPhone(user.getPhone());
		ArrayList<Driver> list = new ArrayList<Driver>();
		list.add(driver);
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}

	public void set(String gjnum, String ipnum) {
		// 设置同一ip在线数量限制  挂机数量限制
		log.info("设置同一IP在线数量={} 挂机数量=", ipnum,gjnum);
		driverMapper.updateSet(gjnum,ipnum);
	}

	public void note(String note) {
		// 设置公告
		Note n = new Note();
		n.setContent(note);
		n.setDate(DateUtils.date2String(new Date(), "yyyyMMdd HH:mm:ss"));
		driverMapper.insertNote(n);
		log.info("更新公告 内容={}",note);
	}
	
}
