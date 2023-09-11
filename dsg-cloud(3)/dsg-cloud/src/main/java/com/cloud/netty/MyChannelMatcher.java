package com.cloud.netty;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;

public class MyChannelMatcher implements ChannelMatcher {
	
	private String id;
	
	public MyChannelMatcher(String id) {
		this.id = id;
	}
	
	@Override
	public boolean matches(Channel channel) {
		return channel.id().asLongText().equals(this.id);
	}

}
