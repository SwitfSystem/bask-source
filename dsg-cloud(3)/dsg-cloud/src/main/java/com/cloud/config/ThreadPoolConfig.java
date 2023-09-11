package com.cloud.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfig {
	
	@Bean(value="threadPool")
	public ExecutorService getExecutorInstance() {
		ExecutorService executor = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
		return executor;
	}
}