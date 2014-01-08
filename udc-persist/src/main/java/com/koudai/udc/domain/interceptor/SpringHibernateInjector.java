package com.koudai.udc.domain.interceptor;

import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public class SpringHibernateInjector implements PostLoadEventListener, BeanFactoryAware {

	private static final long serialVersionUID = -2517351854121824577L;

	private transient AutowireCapableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		beanFactory = (AutowireCapableBeanFactory) factory;
	}

	@Override
	public void onPostLoad(PostLoadEvent event) {
		Object hibernateObject = event.getEntity();
		beanFactory.autowireBeanProperties(hibernateObject, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
	}

}
