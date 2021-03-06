package com.koudai.udc.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.koudai.udc.service.Properties;

public class FileBasedProperties implements Properties {

	private static final Logger LOGGER = Logger.getLogger(FileBasedProperties.class);

	private ResourceBundle properties;

	private String filename;

	public FileBasedProperties() {
	}

	public FileBasedProperties(String basename) {
		this.filename = basename;
		properties = PropertyResourceBundle.getBundle(basename);
	}

	public String get(String key) {
		return properties.getString(key);
	}

	public String get(String key, String defaultValue) {
		try {
			return properties.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.warn("load " + key + " from the property file <" + filename + ">, use the default value: " + defaultValue);
			return defaultValue;
		}
	}

	@Override
	public String get(String key, String srcCharset, String desCharset) {
		try {
			return new String(properties.getString(key).getBytes(srcCharset), desCharset);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
