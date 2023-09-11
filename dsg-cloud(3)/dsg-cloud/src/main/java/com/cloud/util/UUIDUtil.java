package com.cloud.util;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String result = uuid.toString().replace("-", "").toUpperCase();
		return result;
	}
}
