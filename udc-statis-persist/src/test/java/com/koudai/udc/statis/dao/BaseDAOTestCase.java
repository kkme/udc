package com.koudai.udc.statis.dao;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

@SuppressWarnings("deprecation")
public abstract class BaseDAOTestCase extends AbstractTransactionalDataSourceSpringContextTests {

	public BaseDAOTestCase() {
		this.setDefaultRollback(true);
		this.setAutowireMode(AUTOWIRE_BY_NAME);
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:/applicationContext-*.xml" };
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
	}

}
