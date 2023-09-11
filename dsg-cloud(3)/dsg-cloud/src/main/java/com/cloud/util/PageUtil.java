package com.cloud.util;

import java.util.List;
/**
 * 分页工具
 * @author touch
 *
 * @param <T>
 */
public class PageUtil<T> {
	//每页数据量
	private Integer num = 10;
	//总页数
	private Integer total;
	//当前页码
	private Integer current;
	//总数据量
	private Integer count;
	//起始
	private Integer start;
	//终止
	private Integer end;
	//页面管理
	private int[] manager = new int[10];
	//查询分页结果
	private List<T> list;
	
	/**
	 * 用于读取部分数据直接作为当前页
	 * @param num
	 * @param count
	 * @param current
	 */
	public PageUtil(int num,int count,int current) {
		this.num = num;
		this.count = count;
		this.current = current;
		this.manager = null;
		this.total = count % num > 0 ? (count / num) + 1 : count / num;
		pageInit(num,count,current);
	}
	/**用于读取所有数据进行分页
	 * @param num 每页数据量
	 * @param count 总数据量
	 * @param current 当前页码
	 * @param list 实际数据集
	 */
	public PageUtil(int num,int count,int current,List<T> list) {
		this.num = num;
		this.count = count;
		this.current = current;
		this.list = list;
		init(count,num,current);
	}
	
	private void pageInit(int num,int count,int current) {
		if(current == 1) {
			this.start = 0;
		}else {
			int a = count % num;
			int b = count / num;
			if(a > 0) {
				b = b + 1;
			}
			if(current > b) {
				this.current = b;
				current = b;
			}
			this.start = (current - 1) * num;
		}
		this.end = start + num;
	}
	
	private void init(int count,int num,int current) {
		this.total = count / num;
		if((count % num) > 0) {
			this.total = total + 1;
		}
		this.manager = new int[]{1,2,3,4,5,6,7,8,9,10};
		if(this.total > 10) {
			
			int i = (manager[0] + manager[9])/2;
			if(current > i) {
				for(int j = 0;j < 10;j++) {
					this.manager[j] = current - 5 + j;
				}
				int c = total - current;
				int d = total - 9;
				if(c < 5) {
					for(int j = 0;j < 10;j++) {
						this.manager[j] = d + j;
					}
				}
			}
		}else {
			this.manager = new int[total];
			for(int i = 0;i < total;i++) {
				this.manager[i] = i + 1;
			}
		}
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int[] getManager() {
		return manager;
	}
	public void setManager(int[] manager) {
		this.manager = manager;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	
}
