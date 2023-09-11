package com.cloud.util;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**雪花全局ID生成工具
 * 流水号/Token生成器
 * @author touch
 *
 */
public class TokenUtil {
	private final Integer workId;
	private final Integer workBit;
	private Integer sequence;
	private final Integer sequenceBit;
	private final Integer randomBit;
	private Long lastTime;
	private static final BigInteger UNIT = new BigInteger("10"); //序号扩容最大范围
	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyMMddHHmmss");
	
	/**
	 * @param workId 		机器码
	 * @param workBit		机器码位数
	 * @param sequenceBit	序号位数
	 * @param randomBit		随机数位数
	 */
	public TokenUtil(Integer workId,Integer workBit,Integer sequenceBit,Integer randomBit) {
		this.workId = workId;
		this.workBit = workBit;
		this.sequenceBit = sequenceBit;
		this.randomBit = randomBit;
		this.lastTime = 0L;
		Integer maxValue = UNIT.pow(workBit).intValue() -1;
		if(workId > maxValue) {
			throw new IllegalArgumentException(String.format("workId rande is 0 and [%d]", maxValue));
		}
	}
	
	public synchronized String nextId() {
		Long newTime = now();
		if(newTime < lastTime) {
			throw new RuntimeException(String.format("time < [%d]", lastTime - newTime));
		}
		if(newTime.equals(lastTime)) {
			sequence = sequence + 1;
			if(sequence > (UNIT.pow(sequenceBit).intValue()-1)) {
				newTime =  nextMillis(lastTime);
			}
		}else {
			sequence = 0;
		}
		lastTime = newTime;
		Long rondom = ThreadLocalRandom.current().nextLong(0,UNIT.pow(randomBit).intValue());
		return String.format("%012d", newTime)+String.format("%0"+workBit+"d", workId)+
				String.format("%0"+sequenceBit+"d", sequence)+String.format("%0"+randomBit+"d", rondom);
	}
	
	private Long now() {
		LocalDateTime time = LocalDateTime.now();
		return Long.parseLong(time.format(FORMAT));
	}
	
	private Long nextMillis(final Long lastTime) {
		Long time = this.now();
		while(time < lastTime) {
			time = this.now();
		}
		return time;
	}
	
}
