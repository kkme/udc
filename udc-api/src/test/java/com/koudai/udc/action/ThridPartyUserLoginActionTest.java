package com.koudai.udc.action;

import java.util.Date;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserBasicInfo;
import com.koudai.udc.domain.UserTaobaoInfo;
import com.koudai.udc.domain.factory.UserBasicInfoFactory;
import com.koudai.udc.domain.factory.UserTaobaoInfoFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.UserBasicInfoDAO;
import com.koudai.udc.persistence.UserTaobaoInfoDAO;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class ThridPartyUserLoginActionTest extends TestCase {

	private ThridPartyUserLoginAction action;

	private UserBasicInfoDAO mockUserBasicInfoDAOW;

	private UserBasicInfoFactory mockUserBasicInfoFactory;

	private UserTaobaoInfoDAO mockUserTaobaoInfoDAOW;

	private UserTaobaoInfoFactory mockUserTaobaoInfoFactory;

	protected void setUp() throws Exception {
		action = new ThridPartyUserLoginAction();
		mockUserBasicInfoDAOW = EasyMock.createStrictMock(UserBasicInfoDAO.class);
		mockUserBasicInfoFactory = EasyMock.createStrictMock(UserBasicInfoFactory.class);
		mockUserTaobaoInfoDAOW = EasyMock.createStrictMock(UserTaobaoInfoDAO.class);
		mockUserTaobaoInfoFactory = EasyMock.createStrictMock(UserTaobaoInfoFactory.class);
		action.setUserBasicInfoDAOW(mockUserBasicInfoDAOW);
		action.setUserBasicInfoFactory(mockUserBasicInfoFactory);
		action.setUserTaobaoInfoDAOW(mockUserTaobaoInfoDAOW);
		action.setUserTaobaoInfoFactory(mockUserTaobaoInfoFactory);
		action.setLogin_userinfo_in("{\"thirdPartyID\":\"@taobao:123\",\"token\":\"123\",\"refreshToken\":\"456\"}");
	}

	public void testHappyPathWhenFirstLogin() throws Exception {
		EasyMock.expect(mockUserBasicInfoDAOW.getUserBasicInfoByThirdPartyId((String) EasyMock.anyObject())).andReturn(null);
		EasyMock.expect(mockUserBasicInfoFactory.newInstance((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), EasyMock.anyChar(), (Date) EasyMock.anyObject(), (String) EasyMock.anyObject(), (Date) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (Date) EasyMock.anyObject())).andReturn(new UserBasicInfo());
		mockUserBasicInfoDAOW.save((UserBasicInfo) EasyMock.isA(UserBasicInfo.class));
		EasyMock.expect(mockUserTaobaoInfoDAOW.getUserTaobaoInfoByUserIdAndPlatform((String) EasyMock.anyObject(), (LoginPlatform) EasyMock.anyObject())).andReturn(null);
		EasyMock.expect(mockUserTaobaoInfoFactory.newInstance((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), EasyMock.anyInt(), (String) EasyMock.anyObject(), EasyMock.anyInt(), (LoginPlatform) EasyMock.anyObject())).andReturn(new UserTaobaoInfo());
		mockUserTaobaoInfoDAOW.save((UserTaobaoInfo) EasyMock.isA(UserTaobaoInfo.class));
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		verify();
	}

	public void testHappyPathWhenHasLogin() throws Exception {
		UserBasicInfo userBasicInfo = new UserBasicInfo();
		userBasicInfo.setName("123");
		userBasicInfo.setEnabled(true);
		EasyMock.expect(mockUserBasicInfoDAOW.getUserBasicInfoByThirdPartyId((String) EasyMock.anyObject())).andReturn(userBasicInfo);
		EasyMock.expect(mockUserTaobaoInfoDAOW.getUserTaobaoInfoByUserIdAndPlatform((String) EasyMock.anyObject(), (LoginPlatform) EasyMock.anyObject())).andReturn(new UserTaobaoInfo());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testReturnErrorWhenInputIsNull() throws Exception {
		action.setLogin_userinfo_in(null);
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":2"));
		}
		verify();
	}

	public void testReturnErrorWhenInputIsEmpty() throws Exception {
		action.setLogin_userinfo_in("");
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":2"));
		}
		verify();
	}

	public void testReturnErrorWhenThirdIdIsNull() throws Exception {
		action.setLogin_userinfo_in(new JSONObject().toString());
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":2"));
		}
		verify();
	}

	public void testReturnErrorWhenThirdIdIsEmpty() throws Exception {
		action.setLogin_userinfo_in("{\"thirdPartyID\":\"\"}");
		replay();
		try {
			assertEquals(ActionSupport.ERROR, action.execute());
		} catch (ActionErrorDispatchException e) {
			assertTrue(action.getResult().toString().contains("\"status_code\":2"));
		}
		verify();
	}

	private void replay() {
		EasyMock.replay(mockUserBasicInfoDAOW, mockUserBasicInfoFactory, mockUserTaobaoInfoDAOW, mockUserTaobaoInfoFactory);
	}

	private void verify() {
		EasyMock.verify(mockUserBasicInfoDAOW, mockUserBasicInfoFactory, mockUserTaobaoInfoDAOW, mockUserTaobaoInfoFactory);
	}

}
