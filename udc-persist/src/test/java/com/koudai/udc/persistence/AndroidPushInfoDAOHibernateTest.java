package com.koudai.udc.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.AndroidPushInfo;
import com.koudai.udc.domain.PushInfoType;
import com.koudai.udc.domain.factory.AndroidPushInfoFactory;
import com.koudai.udc.utils.SpringFactory;

public class AndroidPushInfoDAOHibernateTest extends TestCase {

	private AndroidPushInfoDAO androidPushInfoDAOW;

	private AndroidPushInfoFactory androidPushInfoFactory;

	@Override
	protected void setUp() throws Exception {
		androidPushInfoDAOW = (AndroidPushInfoDAO) SpringFactory.bean("androidPushInfoDAOW");
		androidPushInfoFactory = (AndroidPushInfoFactory) SpringFactory.bean("androidPushInfoFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		AndroidPushInfo androidPushInfo = androidPushInfoFactory.newInstance("m2", "123", "taobao123:0", PushInfoType.SINGLE.getCode());
		androidPushInfoDAOW.save(androidPushInfo);
		assertNotNull(androidPushInfo.getId());
	}

	public void testGetLatestPushInfoByMachineIdAndCreateTimeByWR() throws Exception {
		Calendar newDate = Calendar.getInstance();
		newDate.set(Calendar.YEAR, 2011);
		newDate.set(Calendar.MONTH, Calendar.JANUARY);
		newDate.set(Calendar.DAY_OF_MONTH, 1);
		newDate.set(Calendar.HOUR_OF_DAY, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		Date date = newDate.getTime();
		AndroidPushInfo pushInfo = androidPushInfoDAOW.getLatestPushInfoByMachineIdAndCreateTime("m1", date);
		assertNotNull(pushInfo);
		assertEquals("taobao3", pushInfo.getFirstProductName());
		pushInfo = androidPushInfoDAOW.getLatestPushInfoByMachineIdAndCreateTime("m1111", date);
		assertNull(pushInfo);
	}

	public void testGetPushInfosByMachineIdAndCreateTimeByWR() throws Exception {
		Calendar newDate = Calendar.getInstance();
		newDate.set(Calendar.YEAR, 2011);
		newDate.set(Calendar.MONTH, Calendar.JANUARY);
		newDate.set(Calendar.DAY_OF_MONTH, 1);
		newDate.set(Calendar.HOUR_OF_DAY, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		Date date = newDate.getTime();
		List<AndroidPushInfo> pushInfos = androidPushInfoDAOW.getPushInfosByMachineIdAndCreateTime("m1", date);
		assertEquals(5, pushInfos.size());
		assertEquals("taobao3", pushInfos.get(0).getFirstProductName());
		assertEquals("taobao1", pushInfos.get(1).getFirstProductName());
		assertEquals("taobao5", pushInfos.get(2).getFirstProductName());
		assertEquals("taobao4", pushInfos.get(3).getFirstProductName());
		assertEquals("taobao2", pushInfos.get(4).getFirstProductName());
	}

}
