package com.koudai.udc.service;

public interface Properties {

	String get(String key);

	String get(String key, String defaultValue);

	String get(String key, String srcCharset, String desCharset);

}
