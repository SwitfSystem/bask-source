package com.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.bean.User;
import com.cloud.config.VerifyToken;
import com.cloud.service.BillService;
import com.cloud.util.SysResult;

@Controller
@ResponseBody
@RequestMapping("/bill")
@CrossOrigin
public class BillController {
	
	private static Logger log = LoggerFactory.getLogger("BILL");
	
	@Autowired
	private BillService billService;
	
	@RequestMapping("/list")
	public SysResult showBillList(String token,Integer page,Integer status) {
		log.info("获取用户佣金列表 page={}",page);
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = billService.selectList(page,status);
		}else {
			User user = (User)result.getData();
			result = billService.selectList(page,status,user);
		}
		result.setToken(token);
		return result;
	}
	
	@RequestMapping("/detail")
	public SysResult showBillDetail(String token,Integer id,String order) {
		log.info("获取佣金详情 order={}",id);
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else {
			result = billService.seletBillDetail(id,order);
		}
		result.setToken(token);
		return result;
	}
	
	@PostMapping("/update")
	public SysResult update(String token,Integer id,Integer status,String reason) {
		log.info("进行提现审批 id={} status={}",id,status);
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = billService.update(id,status,reason);
		}else {
			result = new SysResult(2,"","无权审批",false,null);
		}
		result.setToken(token);
		return result;
	}
	
	@PostMapping("/cashout")
	public SysResult cashout(String token,String phone,String account,Double bill) {
		log.info("用户发起提现申请 phone={} account={} bill={}",phone,account,bill);
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else {
			result = billService.cash_out(phone,account,bill);
		}
		result.setToken(token);
		return result;
	}
}
