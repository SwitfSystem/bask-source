package com.cloud.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig{

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
		FastJsonRedisSerializer<Object> fast = new FastJsonRedisSerializer<Object>(Object.class);
		redisTemplate.setValueSerializer(fast);
		redisTemplate.setHashValueSerializer(fast);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "stringTemplate")
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(factory);
		return template;
	}
}