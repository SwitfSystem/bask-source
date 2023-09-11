package com.cloud.netty;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.cloud.bean.Task;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyServerSenssion {
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private static Map<String,Channel> phoneChannelMap = new ConcurrentHashMap<String,Channel>();
	
	private static Map<String,ChannelId> phoneChannelIdMap = new ConcurrentHashMap<String,ChannelId>();
	
	private static Map<String,Task> RUN_MAP = new ConcurrentHashMap<String,Task>();
   private static Map<String,Task> FREE_MAP = new ConcurrentHashMap<String,Task>();
   //绑定用户和channel
   private void setAttribute(Channel channel, String phone) {
	   AttributeKey<String> key = AttributeKey.valueOf("phone");
	   channel.attr(key).set(phone);
   }
   //判断此channel是否有用户
   public boolean hasUser(Channel channel) {
	   AttributeKey<String> key = AttributeKey.valueOf("phone");
	   return channel.hasAttr(key) || channel.attr(key).get() != null;
   }
   //判断用户是否上线
   public boolean isOnline(String phone) {
	   return phoneChannelMap.containsKey(phone) && phoneChannelMap.get(phone) != null;
   }
	//添加用户映射
	public void put(String phone, Channel channel) {
		phoneChannelMap.put(phone, channel);
		phoneChannelIdMap.put(phone, channel.id());
		setAttribute(channel, phone);
	}
	//删除用户映射
	public void remove(Channel channel) {
		String phone;
		if(hasUser(channel)) {
			AttributeKey<String> key = AttributeKey.valueOf("phone");
			phone = channel.attr(key).getAndSet(null);
			phoneChannelMap.remove(phone);
			phoneChannelIdMap.remove(phone);
			FREE_MAP.remove(phone);
			RUN_MAP.remove(phone);
		}
	}
	// 发送任务
	public synchronized void send(String phone,TextWebSocketFrame tw) {
		tw.retain();
		ChannelId channelId = phoneChannelIdMap.get(phone);
		if(channelId != null) {
			channelGroup.writeAndFlush(tw, new MyChannelMatcher(channelId.asLongText()));
		}
	}
	// 发送给所有客户端
	public void sendAll(TextWebSocketFrame tw) {
		channelGroup.writeAndFlush(tw);
	}
	// 所有在线客户端
	public int total() {
		return channelGroup.size();
	}
	// 空闲客户端
	public int free() {
		return FREE_MAP.size();
	}
	// 运行客户端
	public int running() {
		return RUN_MAP.size();
	}
	// 添加通道
	public void add2Group(Channel ch) {
		channelGroup.add(ch);
	}

	public Iterator<Map.Entry<String, Task>> getFreeTaskMap() {
		Iterator<Map.Entry<String, Task>> it = FREE_MAP.entrySet().iterator();
		return it;
	}
	
	public Iterator<Map.Entry<String, Task>> getRunningTaskMap() {
		Iterator<Map.Entry<String, Task>> it = RUN_MAP.entrySet().iterator();
		return it;
	}
	
	public void addTask(String phone, Task task) {
		RUN_MAP.put(phone, task);
		FREE_MAP.remove(phone);
	}
	
	public Task removeTask(String phone) {
		Task task = RUN_MAP.remove(phone);
		FREE_MAP.put(phone, task);
		return task;
	}

	public Map<String,Task> getFreeMap() {
		return FREE_MAP;
	}
}
