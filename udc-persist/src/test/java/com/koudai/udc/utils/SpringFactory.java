package com.koudai.udc.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFactory {

	private static ClassPathXmlApplicationContext appContext;

	static {
		String[] context = { "classpath:/applicationContext-*.xml" };
		appContext = new ClassPathXmlApplicationContext(context);
	}

	private SpringFactory() {
	}

	public static Object bean(String beanName) {
		return appContext.getBean(beanName);
	}
}
