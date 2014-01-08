package com.koudai.udc.persistence;

import junit.framework.TestCase;

import com.koudai.udc.domain.TaobaoBindingInfo;
import com.koudai.udc.domain.factory.TaobaoBindingInfoFactory;
import com.koudai.udc.utils.SpringFactory;

public class TaobaoBindingInfoDAOHibernateTest extends TestCase {

	private TaobaoBindingInfoDAO taobaoBindingInfoDAOW;

	private TaobaoBindingInfoDAO taobaoBindingInfoDAOR;

	private TaobaoBindingInfoFactory taobaoBindingInfoFactory;

	@Override
	protected void setUp() throws Exception {
		taobaoBindingInfoDAOW = (TaobaoBindingInfoDAO) SpringFactory.bean("taobaoBindingInfoDAOW");
		taobaoBindingInfoDAOR = (TaobaoBindingInfoDAO) SpringFactory.bean("taobaoBindingInfoDAOR");
		taobaoBindingInfoFactory = (TaobaoBindingInfoFactory) SpringFactory.bean("taobaoBindingInfoFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		TaobaoBindingInfo taobaoBindingInfo = taobaoBindingInfoFactory.newInstance("@qq:22222", "@taobao:889");
		taobaoBindingInfoDAOW.save(taobaoBindingInfo);
		assertNotNull(taobaoBindingInfo.getId());
	}

	public void testGetTaobaoBindingInfoByUserIdByRD() throws Exception {
		TaobaoBindingInfo taobaoBindingInfo = taobaoBindingInfoDAOR.getTaobaoBindingInfoByUserId("@sina:123");
		assertNotNull(taobaoBindingInfo);
		assertEquals("@taobao:456", taobaoBindingInfo.getBindId());
		assertTrue(taobaoBindingInfo.isBound());
	}

	public void testGetTaobaoBindingInfoByUserIdByWR() throws Exception {
		TaobaoBindingInfo taobaoBindingInfo = taobaoBindingInfoDAOW.getTaobaoBindingInfoByUserId("@sina:123");
		assertNotNull(taobaoBindingInfo);
		assertEquals("@taobao:456", taobaoBindingInfo.getBindId());
		assertTrue(taobaoBindingInfo.isBound());
	}

}
