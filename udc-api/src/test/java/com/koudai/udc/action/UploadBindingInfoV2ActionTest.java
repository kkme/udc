package com.koudai.udc.action;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.springframework.transaction.support.TransactionTemplate;

import com.koudai.udc.domain.factory.BindingInfoFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.BindingInfoDAO;
import com.koudai.udc.service.UserDataService;
import com.opensymphony.xwork.ActionSupport;

public class UploadBindingInfoV2ActionTest extends TestCase {

	private static final String TAOBAO_USER_IN = "{\"bindinginfo\":{\"usedIDMain\":\"@taobao:123\",\"userIDSub\":\"@anonymous:m1\"}}";
	private static final String SINA_USER_IN = "{\"bindinginfo\":{\"usedIDMain\":\"@sina:123\",\"userIDSub\":\"@anonymous:m1\"}}";
	private static final String QQ_USER_IN = "{\"bindinginfo\":{\"usedIDMain\":\"@qq:123\",\"userIDSub\":\"@anonymous:m1\"}}";

	private UploadBindingInfoV2Action action;
	private BindingInfoDAO mockBindingInfoDAOW;
	private BindingInfoFactory mockBindingInfoFactory;
	private UserDataService mockUserDataService;
	protected TransactionTemplate mockTransactionTemplate;

	protected void setUp() throws Exception {
		action = new UploadBindingInfoV2Action();
		mockBindingInfoDAOW = EasyMock.createStrictMock(BindingInfoDAO.class);
		mockBindingInfoFactory = EasyMock.createStrictMock(BindingInfoFactory.class);
		mockUserDataService = EasyMock.createStrictMock(UserDataService.class);
		mockTransactionTemplate = EasyMock.createStrictMock(TransactionTemplate.class);
		action.setBindingInfoDAOW(mockBindingInfoDAOW);
		action.setBindingInfoFactory(mockBindingInfoFactory);
		action.setUserDataService(mockUserDataService);
		action.setTransactionTemplate(mockTransactionTemplate);
		action.setUpload_bindinginfo_in(TAOBAO_USER_IN);
	}

	public void testHappyPathWhenTaobaoUser() throws Exception {
		EasyMock.expect(mockTransactionTemplate.execute((NewBindingInfoTransactionCallback) EasyMock.anyObject())).andReturn(false);
		EasyMock.expect(mockTransactionTemplate.execute((SynchronizeUserDataTransactionCallback) EasyMock.anyObject())).andReturn(false);
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testHappyPathWhenSinaUser() throws Exception {
		action.setUpload_bindinginfo_in(SINA_USER_IN);
		EasyMock.expect(mockTransactionTemplate.execute((NewBindingInfoTransactionCallback) EasyMock.anyObject())).andReturn(false);
		EasyMock.expect(mockTransactionTemplate.execute((SynchronizeUserDataTransactionCallback) EasyMock.anyObject())).andReturn(false);
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testHappyPathWhenQQUser() throws Exception {
		action.setUpload_bindinginfo_in(QQ_USER_IN);
		EasyMock.expect(mockTransactionTemplate.execute((NewBindingInfoTransactionCallback) EasyMock.anyObject())).andReturn(false);
		EasyMock.expect(mockTransactionTemplate.execute((SynchronizeUserDataTransactionCallback) EasyMock.anyObject())).andReturn(false);
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenMainAndSubUserAreAnonymous() throws Exception {
		action.setUpload_bindinginfo_in("{\"bindinginfo\":{\"usedIDMain\":\"@anonymous:m1\",\"userIDSub\":\"@anonymous:m1\"}}");
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadBindinginfoInIsNull() throws Exception {
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

	public void testThrowIncorrectInputParameterExceptionWhenUploadBindinginfoInIsEmpty() throws Exception {
		action.setUpload_bindinginfo_in("");
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenMainIdIsNull() throws Exception {
		action.setUpload_bindinginfo_in("{\"bindinginfo\":{\"userIDSub\":\"@anonymous:m1\"}}");
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenSubIdIsNull() throws Exception {
		action.setUpload_bindinginfo_in("{\"bindinginfo\":{\"userIDMain\":\"@taobao:123\"}}");
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenSubIdRuleIsInvalid() throws Exception {
		action.setUpload_bindinginfo_in("{\"bindinginfo\":{\"usedIDMain\":\"@taobao:123\",\"userIDSub\":\"m1\"}}");
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testNewBindingInfoTransactionCallback() throws Exception {
		EasyMock.expect(mockTransactionTemplate.execute((NewBindingInfoTransactionCallback) EasyMock.anyObject())).andReturn(true);
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testSynchronizeUserDataTransactionCallback() throws Exception {
		EasyMock.expect(mockTransactionTemplate.execute((NewBindingInfoTransactionCallback) EasyMock.anyObject())).andReturn(false);
		EasyMock.expect(mockTransactionTemplate.execute((SynchronizeUserDataTransactionCallback) EasyMock.anyObject())).andReturn(true);
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
		EasyMock.replay(mockBindingInfoDAOW, mockBindingInfoFactory, mockUserDataService, mockTransactionTemplate);
	}

	private void verify() {
		EasyMock.verify(mockBindingInfoDAOW, mockBindingInfoFactory, mockUserDataService, mockTransactionTemplate);
	}

}
