package com.koudai.udc.action;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.domain.DeviceInfo;
import com.koudai.udc.domain.Platform;
import com.koudai.udc.domain.factory.DeviceInfoFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.DeviceInfoDAO;
import com.opensymphony.xwork.ActionSupport;

public class UploadDeviceInfoActionTest extends TestCase {

	private UploadDeviceInfoAction action;

	private DeviceInfoDAO mockDeviceInfoDAOW;

	private DeviceInfoFactory mockDeviceInfoFactory;

	private static final String IN = "{\"deviceinfo\":{\"machineID\":\"123\",\"macID\":\"789\",\"platform\":\"Android\"}}";

	protected void setUp() throws Exception {
		action = new UploadDeviceInfoAction();
		mockDeviceInfoDAOW = EasyMock.createStrictMock(DeviceInfoDAO.class);
		mockDeviceInfoFactory = EasyMock.createStrictMock(DeviceInfoFactory.class);
		action.setDeviceInfoDAOW(mockDeviceInfoDAOW);
		action.setDeviceInfoFactory(mockDeviceInfoFactory);
		action.setUpload_deviceinfo_in(IN);
	}

	public void testSaveANewItemWhenNotExist() throws Exception {
		EasyMock.expect(mockDeviceInfoDAOW.getDeviceInfoByMachineIdAndPlatform((String) EasyMock.anyObject(), (Platform) EasyMock.anyObject())).andReturn(null);
		EasyMock.expect(mockDeviceInfoFactory.newInstance((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (Platform) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(new DeviceInfo());
		mockDeviceInfoDAOW.save((DeviceInfo) EasyMock.anyObject());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testSaveANewItemWhenExist() throws Exception {
		EasyMock.expect(mockDeviceInfoDAOW.getDeviceInfoByMachineIdAndPlatform((String) EasyMock.anyObject(), (Platform) EasyMock.anyObject())).andReturn(new DeviceInfo());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadDeviceinfoInIsNullOrEmpty() throws Exception {
		action.setUpload_deviceinfo_in(null);
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	private void replay() {
		EasyMock.replay(mockDeviceInfoDAOW, mockDeviceInfoFactory);
	}

	private void verify() {
		EasyMock.verify(mockDeviceInfoDAOW, mockDeviceInfoFactory);
	}

}
