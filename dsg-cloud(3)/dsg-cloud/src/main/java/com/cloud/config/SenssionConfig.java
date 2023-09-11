package com.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloud.netty.NettyServerSenssion;

@Configuration
public class SenssionConfig {
	
	@Bean(value="nettySenssion")
	public NettyServerSenssion getNettySenssion() {
		NettyServerSenssion nettySenssion = new NettyServerSenssion();
		return nettySenssion;
	}
}
