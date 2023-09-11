package com.cloud.service;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cloud.bean.Source;
import com.cloud.mapper.SourceMapper;
import com.cloud.util.PageUtil;
import com.cloud.util.SysResult;

@Service
public class SourceService {
	
	@Autowired
	private SourceMapper sourceMapper;

	public SysResult get(String type, Integer page) {
		if(page == null) {
			page = 1;
		}
		switch (type) {
			case "doc":
				Integer count = sourceMapper.selectSourceCountDoc();
				PageUtil<Source> pageUtil = new PageUtil<Source>(20,count,page);
				Integer start = pageUtil.getStart();
				Integer end = pageUtil.getEnd();
				ArrayList<Source> list = sourceMapper.selectSourceDoc(start,end);
				pageUtil.setList(list);
				return new SysResult(0,"success",true,pageUtil);
			default:
				return new SysResult(1,"暂时只支持文档资源",false,null);
		}
	}

	public SysResult add(String type, String name, String comment) {
		if(StringUtils.hasLength(type)&&StringUtils.hasLength(name)&&StringUtils.hasLength(comment)) {
			Source s = new Source();
			s.setComment(comment);
			s.setName(name);
			s.setCreatetime(new Date(System.currentTimeMillis()));
			s.setUpdatetime(new Date(System.currentTimeMillis()));
			sourceMapper.insertSource(s);
			return new SysResult(0,"success",true,null);
		}
		return new SysResult(1,"参数错误",false,null);
	}

}
