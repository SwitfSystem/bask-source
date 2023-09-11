package com.cloud.service;

import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.cloud.bean.Bill;
import com.cloud.bean.BillDetail;
import com.cloud.bean.User;
import com.cloud.mapper.BillMapper;
import com.cloud.mapper.UserMapper;
import com.cloud.util.DateUtils;
import com.cloud.util.PageUtil;
import com.cloud.util.SysResult;

@Service
public class BillService {
	
	private static Logger log = LoggerFactory.getLogger("BILL");
	
	@Autowired
	private BillMapper billMapper;
	@Autowired
	private UserMapper userMapper;
	/**
	 * 管理员获取所有佣金提现信息
	 * @param page
	 * @param status
	 * @return
	 */
	public SysResult selectList(Integer page,Integer status) {
		if(page == null) {
			page = 1;
		}
		int count = 0;
		ArrayList<Bill> list = null;
		PageUtil<Bill> pageUtil = null;
		if(status == null) {
			count = billMapper.selectBillCount();
			pageUtil = new PageUtil<Bill>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = billMapper.selectList(start,end);
		}else if(status==0 || status==1 || status==2){
			count = billMapper.selectBillCountByStatus(status);
			pageUtil = new PageUtil<Bill>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = billMapper.selectListByStatus(start,end,status);
		}else {
			return new SysResult(1,"参数错误",false,null);
		}
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}
	/**
	 * 用户获取佣金提现信息
	 * @param page
	 * @param status
	 * @return
	 */
	public SysResult selectList(Integer page,Integer status,User user) {
		if(page == null) {
			page = 1;
		}
		int count = 0;
		ArrayList<Bill> list = null;
		PageUtil<Bill> pageUtil = null;
		if(status == null) {
			count = billMapper.selectBillCountUser(user.getId());
			pageUtil = new PageUtil<Bill>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = billMapper.selectList(start,end);
		}else if(status==0 || status==1 || status==2){
			count = billMapper.selectBillCountByStatus(status);
			pageUtil = new PageUtil<Bill>(20,count,page);
			int start = pageUtil.getStart();
			int end = pageUtil.getEnd();
			list = billMapper.selectListByStatusUser(user.getId(),start,end,status);
		}else {
			return new SysResult(1,"参数错误",false,null);
		}
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}

	public SysResult seletBillDetail(Integer id,String order) {
		Bill bill = billMapper.selectBillById(id);
		BillDetail detail = billMapper.selectBillDetail(bill.getUser_id(),order);
		return new SysResult(0,"success",true,detail);
	}

	public SysResult update(Integer id, Integer status, String reason) {
		Bill bill = billMapper.selectBillById(id);
		if(status == 0) {
			bill.setStatus(status);
			bill.setReason("通过");
			log.info("佣金提现审批通过 用户={} 金额={}",bill.getPhone(),bill.getMoney());
		}else if(status == 1) {
			bill.setStatus(status);
			bill.setReason(reason);
			log.info("佣金提现审批拒绝 用户={} 金额={}",bill.getPhone(),bill.getMoney());
		}
		billMapper.updateBill(bill);
		return new SysResult(0,"success",true,bill);
	}

	public SysResult cash_out(String phone,String account, Double bill) {
		if(!StringUtils.hasLength(phone) || bill==null) {
			return new SysResult(1,"参数错误",false,null);
		}
		User user = userMapper.selectByPhone(phone);
		if(user == null) {
			return new SysResult(1,"用户不存在",false,null);
		}
		double b = user.getBalance();
		if(bill > b) {
			return new SysResult(1,"提现金额过大",false,null);
		}
		Bill bi = new Bill();
		bi.setBalance(b);
		bi.setMoney(bill);
		bi.setPhone(phone);
		bi.setUser_id(user.getId());
		bi.setStatus(2);
		bi.setTime(DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		bi.setAlipay(account);
		billMapper.insertBill(null);
		return new SysResult(0,"success",true,null);
	}
}
