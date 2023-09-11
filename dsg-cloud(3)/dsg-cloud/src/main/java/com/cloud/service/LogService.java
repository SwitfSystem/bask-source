package com.cloud.service;

import java.io.File;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cloud.bean.Log;
import com.cloud.mapper.LogMapper;
import com.cloud.util.PageUtil;
import com.cloud.util.SysResult;

@Service
public class LogService {
	
	private static Logger log = LoggerFactory.getLogger("LOG");
	
	@Value("${logpath}")
	private String logpath;
	
	@Autowired
	private LogMapper logMapper;
	
	private static boolean isInit = false;
	
	public void conf() {
		if(!isInit) {
			init();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000*60*60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					init();
					log.info("日志刷新");
				}
			}).start();
		}
		isInit = true;
	}
	
	private void init() {
		File file = new File(logpath);
		File[] listFiles = file.listFiles();
		for(File f:listFiles) {
			String name = f.getName();
			Integer md5 = name.hashCode();
			Integer hasmd5 = logMapper.selectMd5(md5);
			if(hasmd5 == null) {
				continue;
			}
			Long size = f.length() / 1024; // KB
			String date = name.substring(name.indexOf("_")+1, name.indexOf("."));
			String url = f.getPath();
			Log log = new Log();
			log.setName(name);
			log.setSize(size+"");
			log.setType(name.split("_")[0]);
			log.setUrl(url);
			log.setDate(date);
			log.setMd5(md5);
			logMapper.insertOne(log);
		}
	}

	public SysResult logList(String type, Integer page) {
		conf();
		if(page == null) {
			page = 1;
		}
		int count = logMapper.selectLogCount(type);
		PageUtil<Log> pageUtil = new PageUtil<Log>(20,count,page);
		int start = pageUtil.getStart();
		int end = pageUtil.getEnd();
		ArrayList<Log> list = logMapper.selectType(type, start, end);
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}

	public SysResult log(Integer id) {
		conf();
		String name = logMapper.selectOne(id);
		//String url = logpath + File.separator + name;
		//StringBuffer buf = FileUtil.read(new File(url), null);
		//return new SysResult(0,"",true,buf.toString());
		return new SysResult(0,"success",true,name);
	}
	
	
}
