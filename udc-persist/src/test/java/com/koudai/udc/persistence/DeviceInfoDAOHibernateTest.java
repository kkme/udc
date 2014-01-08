package com.koudai.udc.persistence;

import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.DeviceInfo;
import com.koudai.udc.domain.Platform;
import com.koudai.udc.domain.factory.DeviceInfoFactory;
import com.koudai.udc.utils.SpringFactory;

public class DeviceInfoDAOHibernateTest extends TestCase {

	private DeviceInfoDAO deviceInfoDAOW;

	private DeviceInfoDAO deviceInfoDAOR;

	private DeviceInfoFactory deviceInfoFactory;

	@Override
	protected void setUp() throws Exception {
		deviceInfoDAOW = (DeviceInfoDAO) SpringFactory.bean("deviceInfoDAOW");
		deviceInfoDAOR = (DeviceInfoDAO) SpringFactory.bean("deviceInfoDAOR");
		deviceInfoFactory = (DeviceInfoFactory) SpringFactory.bean("deviceInfoFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		DeviceInfo deviceInfo = deviceInfoFactory.newInstance("123", "456");
		deviceInfoDAOW.save(deviceInfo);
		assertNotNull(deviceInfo.getId());
	}

	public void testGetDeviceInfoByMachineId() throws Exception {
		DeviceInfo deviceInfo = deviceInfoDAOW.getDeviceInfoByMachineIdAndPlatform("machine1", Platform.iphone);
		assertEquals(new Long(1), deviceInfo.getId());
		assertEquals("mac1", deviceInfo.getMacId());
		assertNotNull(deviceInfo.getId());
	}

	public void testGetTokensBySoftwarVersionAndPlatformByRD() throws Exception {
		List<String> tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 0, 5);
		assertEquals(3, tokens.size());
		assertEquals("token2", tokens.get(0));
		assertEquals("token3", tokens.get(1));
		assertEquals("token7", tokens.get(2));
		tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 0, 3);
		assertEquals(3, tokens.size());
		assertEquals("token2", tokens.get(0));
		assertEquals("token3", tokens.get(1));
		assertEquals("token7", tokens.get(2));
		tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 0, 2);
		assertEquals(2, tokens.size());
		assertEquals("token2", tokens.get(0));
		assertEquals("token3", tokens.get(1));
		tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 0, 1);
		assertEquals(1, tokens.size());
		assertEquals("token2", tokens.get(0));
		tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 1, 1);
		assertEquals(1, tokens.size());
		assertEquals("token3", tokens.get(0));
	}

	public void testGetTokensBySoftwarVersionAndPlatformByWR() throws Exception {
		List<String> tokens = deviceInfoDAOW.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 0, 5);
		assertEquals(3, tokens.size());
		assertEquals("token2", tokens.get(0));
		assertEquals("token3", tokens.get(1));
		assertEquals("token7", tokens.get(2));
		tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 0, 3);
		assertEquals(3, tokens.size());
		assertEquals("token2", tokens.get(0));
		assertEquals("token3", tokens.get(1));
		assertEquals("token7", tokens.get(2));
		tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 0, 2);
		assertEquals(2, tokens.size());
		assertEquals("token2", tokens.get(0));
		assertEquals("token3", tokens.get(1));
		tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 0, 1);
		assertEquals(1, tokens.size());
		assertEquals("token2", tokens.get(0));
		tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform("1.3.2", "2.0.2", Platform.iphone, 1, 1);
		assertEquals(1, tokens.size());
		assertEquals("token3", tokens.get(0));
	}

}
