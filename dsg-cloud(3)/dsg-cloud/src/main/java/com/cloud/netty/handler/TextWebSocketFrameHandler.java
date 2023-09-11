package com.cloud.netty.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.bean.Task;
import com.cloud.netty.NettyServerSenssion;
import com.cloud.netty.message.ResponseMessage;
import com.cloud.util.DateUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private static Logger log = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);
	private NettyServerSenssion nettySenssion = new NettyServerSenssion();
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		log.info("客户端发送消息...");
		String text = msg.text();
		String phone = "";
		try {
			JSONObject json = JSON.parseObject(text);
			String password = json.getString("password");
			phone = json.getString("phone");
			if(password != null) {
				nettySenssion.put(phone, ctx.channel());
				Task task = new Task();
				task.setIp(json.getString("ip"));
				task.setMac(json.getString("mac"));
				task.setOs(json.getString("os"));
				task.setPlatform(Integer.parseInt(json.getString("platform")));
				task.setToken(json.getString("token"));
				task.setPhone(phone);
				task.setStarttime(DateUtils.date2String(new Date()));
				nettySenssion.getFreeMap().put(phone, task);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		ResponseMessage response = new ResponseMessage();
		response.setCode(0);
		response.setMsg("连接成功");
		response.setContent("连接成功");
		ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(response)));
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		log.info("客户端连接...");
		nettySenssion.add2Group(ctx.channel());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		log.info("客户端断开连接");
		nettySenssion.remove(ctx.channel());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.info("发生异常");
		nettySenssion.remove(ctx.channel());
		ctx.close();
	}
}
