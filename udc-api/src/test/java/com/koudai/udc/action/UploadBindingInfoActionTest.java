package com.koudai.udc.action;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.domain.BindingInfo;
import com.koudai.udc.domain.factory.BindingInfoFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.BindingInfoDAO;
import com.opensymphony.xwork.ActionSupport;

public class UploadBindingInfoActionTest extends TestCase {

	private UploadBindingInfoAction action;

	private BindingInfoDAO mockBindingInfoDAOW;
	private BindingInfoFactory mockBindingInfoFactory;

	private static final String IN = "{\"bindinginfo\":{\"machineID\":\"123\",\"userID\":\"@anonymous:123\"}}";

	protected void setUp() throws Exception {
		action = new UploadBindingInfoAction();
		mockBindingInfoDAOW = EasyMock.createStrictMock(BindingInfoDAO.class);
		mockBindingInfoFactory = EasyMock.createStrictMock(BindingInfoFactory.class);
		action.setBindingInfoDAOW(mockBindingInfoDAOW);
		action.setBindingInfoFactory(mockBindingInfoFactory);
		action.setUpload_bindinginfo_in(IN);
	}

	public void testSaveANewBindingInfoWhenExist() throws Exception {
		EasyMock.expect(mockBindingInfoFactory.newInstance((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(new BindingInfo());
		mockBindingInfoDAOW.save((BindingInfo) EasyMock.anyObject());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadBindinginfoInIsNullOrEmpty() throws Exception {
		action.setUpload_bindinginfo_in(null);
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
		EasyMock.replay(mockBindingInfoDAOW, mockBindingInfoFactory);
	}

	private void verify() {
		EasyMock.verify(mockBindingInfoDAOW, mockBindingInfoFactory);
	}

}
