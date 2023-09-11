package com.cloud.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.bean.Bill;
import com.cloud.bean.BillDetail;

@Mapper
public interface BillMapper {

	ArrayList<Bill> selectList(@Param("start")Integer start,@Param("end")Integer end);
	
	void insertBill(Bill bill);
	
	void deleteBillByPhone(String phone);

	BillDetail selectBillDetail(@Param("user_id")Integer user_id,@Param("order")String order);

	ArrayList<Bill> selectBillByPhone(String keywords);

	Integer selectBillCount();
	
	Integer selectBillCountUser(Integer user_id);

	int selectBillCountByStatus(Integer status);

	ArrayList<Bill> selectListByStatus(@Param("start")Integer start,@Param("end")Integer end,@Param("status")Integer status);
	
	ArrayList<Bill> selectListByStatusUser(@Param("user_id")Integer user_id, @Param("start")Integer start,@Param("end")Integer end,@Param("status")Integer status);

	Bill selectBillById(Integer id);

	void updateBill(Bill bill);

}
