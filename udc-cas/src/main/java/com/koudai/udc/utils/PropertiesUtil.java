package com.koudai.udc.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class PropertiesUtil {

	private static final Logger LOGGER = Logger.getLogger(PropertiesUtil.class);

	public static Properties getPropertiesWithString(String source) {
		String temp = source.replace("&", "\n");
		ByteArrayInputStream stream = new ByteArrayInputStream(temp.getBytes());
		Properties properties = new Properties();
		try {
			properties.load(stream);
			return properties;
		} catch (IOException e) {
			LOGGER.error(e);
			return null;
		}
	}

}
