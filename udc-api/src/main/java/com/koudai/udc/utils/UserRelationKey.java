package com.koudai.udc.utils;

public final class UserRelationKey {

	private static final String PREFIX_ANONYMOUS = "@anonymous:";

	public static boolean isAnonymousUser(String userId) {
		return userId.contains(PREFIX_ANONYMOUS);
	}

	public static String getAnonymousUser(String machineId) {
		return new StringBuffer(PREFIX_ANONYMOUS).append(machineId).toString();
	}

}
