package com.koudai.udc.action;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.domain.ProductCollect;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.opensymphony.xwork.ActionSupport;

public class CancelProductCollectActionTest extends TestCase {

	private CancelProductCollectAction action;

	private ProductCollectDAO mockProductCollectDAOW;

	private ProductCollect mockProductCollect;

	private static final String IN = "{\"cancelcollect\":[{\"machineID\":0,\"userID\":0,\"productID\":0},{\"machineID\":1,\"userID\":1,\"productID\":1}]}";

	protected void setUp() throws Exception {
		action = new CancelProductCollectAction();
		mockProductCollectDAOW = EasyMock.createStrictMock(ProductCollectDAO.class);
		mockProductCollect = EasyMock.createStrictMock(ProductCollect.class);
		action.setProductCollectDAOW(mockProductCollectDAOW);
		action.setCancel_productcollect_in(IN);
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockProductCollectDAOW.getProductCollectByUserAndProductId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockProductCollect);
		mockProductCollect.cancel();
		EasyMock.expect(mockProductCollectDAOW.getProductCollectByUserAndProductId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockProductCollect);
		mockProductCollect.cancel();
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadProductCollectInIsNull() throws Exception {
		action.setCancel_productcollect_in(null);
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadProductCollectInIsEmpty() throws Exception {
		action.setCancel_productcollect_in("");
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
		EasyMock.replay(mockProductCollectDAOW, mockProductCollect);
	}

	private void verify() {
		EasyMock.verify(mockProductCollectDAOW, mockProductCollect);
	}

}
