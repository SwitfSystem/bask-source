package com.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.config.VerifyToken;
import com.cloud.service.SearchService;
import com.cloud.util.SysResult;

@Controller
@ResponseBody
@CrossOrigin
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public SysResult search(String token,String type,Integer page,String name,String words,Integer status) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 1) {
			return new SysResult(2,token,"无权操作",false,null);
		}
		switch(type) {
			case "user":
				result = searchService.searchUser(words,name);
			case "agent":
				result = searchService.searchAgent(words,name);
			case "bill":
				result = searchService.searchBill(words);
			case "driver":
				result = searchService.searchDriver(words,name,status,page);
			default:
				result = new SysResult(1,token,"参数错误",false,null);
				
		}
		result.setToken(token);
		return result;
	}
}
