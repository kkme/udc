package com.koudai.udc.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.IosPushInfo;
import com.koudai.udc.domain.Platform;
import com.koudai.udc.domain.PushInfoType;
import com.koudai.udc.domain.factory.IosPushInfoFactory;
import com.koudai.udc.utils.SpringFactory;

public class IosPushInfoDAOHibernateTest extends TestCase {

	private IosPushInfoDAO iosPushInfoDAOW;

	private IosPushInfoFactory iosPushInfoFactory;

	@Override
	protected void setUp() throws Exception {
		iosPushInfoDAOW = (IosPushInfoDAO) SpringFactory.bean("iosPushInfoDAOW");
		iosPushInfoFactory = (IosPushInfoFactory) SpringFactory.bean("iosPushInfoFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		Calendar newDate = Calendar.getInstance();
		newDate.set(Calendar.YEAR, 2010);
		newDate.set(Calendar.MONTH, Calendar.JANUARY);
		newDate.set(Calendar.DAY_OF_MONTH, 1);
		newDate.set(Calendar.HOUR_OF_DAY, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		Date date = newDate.getTime();
		IosPushInfo iosPushInfo = iosPushInfoFactory.newInstance("m2", "123", "taobao123:0", PushInfoType.SINGLE.getCode(), "12345", Platform.iphone);
		iosPushInfo.setCreateTime(date);
		iosPushInfoDAOW.save(iosPushInfo);
		assertNotNull(iosPushInfo.getId());
	}

	public void testGetPushInfosByCreateTimeAndStartAndEndPosByWR() throws Exception {
		Calendar newDate = Calendar.getInstance();
		newDate.set(Calendar.YEAR, 2011);
		newDate.set(Calendar.MONTH, Calendar.JANUARY);
		newDate.set(Calendar.DAY_OF_MONTH, 1);
		newDate.set(Calendar.HOUR_OF_DAY, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		Date date = newDate.getTime();
		List<IosPushInfo> pushInfos = iosPushInfoDAOW.getPushInfosByCreateTimeAndStartAndEndPosAndPlatform(date, 0, 2, Platform.iphone);
		assertEquals(3, pushInfos.size());
		assertEquals("taobao3", pushInfos.get(0).getFirstProductName());
		assertEquals("taobao1", pushInfos.get(1).getFirstProductName());
		assertEquals("taobao5", pushInfos.get(2).getFirstProductName());
		pushInfos = iosPushInfoDAOW.getPushInfosByCreateTimeAndStartAndEndPosAndPlatform(date, 0, 500, Platform.iphone);
		assertEquals(5, pushInfos.size());
		assertEquals("taobao3", pushInfos.get(0).getFirstProductName());
		assertEquals("taobao1", pushInfos.get(1).getFirstProductName());
		assertEquals("taobao5", pushInfos.get(2).getFirstProductName());
		assertEquals("taobao4", pushInfos.get(3).getFirstProductName());
		assertEquals("taobao2", pushInfos.get(4).getFirstProductName());
		pushInfos = iosPushInfoDAOW.getPushInfosByCreateTimeAndStartAndEndPosAndPlatform(date, 0, 0, Platform.iphone);
		assertEquals(1, pushInfos.size());
		assertEquals("taobao3", pushInfos.get(0).getFirstProductName());
	}

}
