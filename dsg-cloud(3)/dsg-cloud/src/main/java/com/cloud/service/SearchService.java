package com.cloud.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cloud.bean.Bill;
import com.cloud.bean.Driver;
import com.cloud.bean.User;
import com.cloud.mapper.BillMapper;
import com.cloud.mapper.DriverMapper;
import com.cloud.mapper.UserMapper;
import com.cloud.util.PageUtil;
import com.cloud.util.SysResult;

@Service
public class SearchService {
	
	private static Logger log = LoggerFactory.getLogger("SEARCH");
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BillMapper billMapper;
	
	@Autowired
	private DriverMapper dirverMapper;

	public SysResult searchUser(String keywords, String type) {
		if(!StringUtils.hasLength(keywords) || !StringUtils.hasLength(type)) {
			return new SysResult(1,"",false,null);
		}
		User user = null;
		if("phone".equals(type)) {
			user = userMapper.selectByPhone(type);
		}else if("name".equals(type)) {
			user = userMapper.selectByName(type);
		}
		if(user == null) {
			return new SysResult(1,"",false,null);
		}else {
			return new SysResult(0,"",true,user);
		}
	}

	public SysResult searchAgent(String keywords, String type) {
		if(!StringUtils.hasLength(keywords) || !StringUtils.hasLength(type)) {
			return new SysResult(1,"",false,null);
		}
		User user = null;
		if("phone".equals(type)) {
			user = userMapper.selectAgentByPhone(type);
		}else if("name".equals(type)) {
			user = userMapper.selectAgentByName(type);
		}
		if(user == null) {
			return new SysResult(1,"",false,null);
		}else {
			return new SysResult(0,"",true,user);
		}
	}

	public SysResult searchBill(String keywords) {
		if(!StringUtils.hasLength(keywords)) {
			return new SysResult(1,"",false,null);
		}
		ArrayList<Bill> list = billMapper.selectBillByPhone(keywords);
		if(list.size() < 1) {
			return new SysResult(1,"",false,null);
		}else {
			return new SysResult(0,"",true,list);
		}
	}

	public SysResult searchDriver(String keywords, String type, Integer status,Integer page) {
		log.info("搜索设备 keywords={} type={} status={}",keywords,type,status);
		if(page==null) {
			page = 1;
		}
		ArrayList<Driver> list = new ArrayList<Driver>();
		if(!StringUtils.hasLength(keywords) || !StringUtils.hasLength(type+"")) {
			int count = dirverMapper.selectDriverByCountStatus(status);
			PageUtil<Driver> pageUtil = new PageUtil<Driver>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = dirverMapper.selectDriverByStatus(start,end,status);
			pageUtil.setList(list);
			return new SysResult(0,"success",true,pageUtil);
		}
		Driver driver = null;
		switch(type) {
			case "phone":
				driver = dirverMapper.selectDriverByPhone(keywords);
				break;
			case "username":
				driver = dirverMapper.selectDriverByName(keywords);
				break;
			default:
				driver = dirverMapper.selectDriverByPhone(keywords);
				break;
		}
		if(driver == null) {
			return new SysResult(1,"无数据",false,null);
		}else {
			list.add(driver);
			return new SysResult(0,"success",true,list);
		}
	}
	
	
}
