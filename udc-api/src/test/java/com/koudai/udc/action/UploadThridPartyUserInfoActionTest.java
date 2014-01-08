package com.koudai.udc.action;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.domain.UserBasicInfo;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.UserBasicInfoDAO;
import com.opensymphony.xwork.ActionSupport;

public class UploadThridPartyUserInfoActionTest extends TestCase {

	private UploadThridPartyUserInfoAction action;

	private UserBasicInfoDAO mockUserBasicInfoDAOW;

	@Override
	protected void setUp() throws Exception {
		action = new UploadThridPartyUserInfoAction();
		mockUserBasicInfoDAOW = EasyMock.createStrictMock(UserBasicInfoDAO.class);
		action.setUserBasicInfoDAOW(mockUserBasicInfoDAOW);
		action.setUpload_thirdpary_userinfo_in("{\"thirdPartyID\":\"@taobao:123\",\"nick\":\"456\"}");
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockUserBasicInfoDAOW.getUserBasicInfoByThirdPartyId((String) EasyMock.anyObject())).andReturn(new UserBasicInfo());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testReturnErrorWhenRequestIsNull() throws Exception {
		action.setUpload_thirdpary_userinfo_in(null);
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testReturnErrorWhenRequestIsEmpty() throws Exception {
		action.setUpload_thirdpary_userinfo_in("");
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testReturnErrorWhenThirdIdIsNull() throws Exception {
		action.setUpload_thirdpary_userinfo_in("{\"nick\":\"456\"}");
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testReturnErrorWhenThirdIdIsEmpty() throws Exception {
		action.setUpload_thirdpary_userinfo_in("{\"thirdPartyID\":\"\",\"nick\":\"456\"}");
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testReturnErrorWhenNickIsNull() throws Exception {
		action.setUpload_thirdpary_userinfo_in("{\"thirdPartyID\":\"@taobao:123\"}");
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testReturnErrorWhenNickIsEmpty() throws Exception {
		action.setUpload_thirdpary_userinfo_in("{\"thirdPartyID\":\"@taobao:123\",\"nick\":\"\"}");
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testReturnErrorWhenUserBasicInfoNotExist() throws Exception {
		EasyMock.expect(mockUserBasicInfoDAOW.getUserBasicInfoByThirdPartyId((String) EasyMock.anyObject())).andReturn(null);
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	private void replay() {
		EasyMock.replay(mockUserBasicInfoDAOW);
	}

	private void verify() {
		EasyMock.verify(mockUserBasicInfoDAOW);
	}

}
