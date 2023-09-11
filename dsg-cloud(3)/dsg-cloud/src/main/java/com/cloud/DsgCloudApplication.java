package com.cloud;

import java.net.InetSocketAddress;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cloud.netty.NettyServer;

@SpringBootApplication
@MapperScan("com.cloud.mapper")
public class DsgCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsgCloudApplication.class, args);
		NettyServer nettyServer = new NettyServer();
		nettyServer.start(new InetSocketAddress("0.0.0.0", 7000));
	}
}
