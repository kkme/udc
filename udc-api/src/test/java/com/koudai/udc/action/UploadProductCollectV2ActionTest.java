package com.koudai.udc.action;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.domain.ProductCollect;
import com.koudai.udc.domain.factory.ProductCollectFactory;
import com.koudai.udc.domain.service.CounterService;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.opensymphony.xwork.ActionSupport;

public class UploadProductCollectV2ActionTest extends TestCase {

	private UploadProductCollectV2Action action;

	private CounterService mockCounterService;

	private ProductCollectDAO mockProductCollectDAOW;

	private ProductCollectFactory mockProductCollectFactory;

	private ProductCollect mockProductCollect;

	private static final String IN = "{\"productcollect\":[{\"machineID\":0,\"userIDReal\":\"@taobao:xxx\",\"userIDAnonymous\":\"@anonymous:xxx\",\"productID\":0},{\"machineID\":1,\"userIDReal\":\"@taobao:xxx\",\"userIDAnonymous\":\"@anonymous:xxx\",\"productID\":1}]}";

	protected void setUp() throws Exception {
		action = new UploadProductCollectV2Action();
		mockCounterService = EasyMock.createStrictMock(CounterService.class);
		mockProductCollectDAOW = EasyMock.createStrictMock(ProductCollectDAO.class);
		mockProductCollectFactory = EasyMock.createStrictMock(ProductCollectFactory.class);
		mockProductCollect = EasyMock.createStrictMock(ProductCollect.class);
		action.setCounterService(mockCounterService);
		action.setProductCollectDAOW(mockProductCollectDAOW);
		action.setProductCollectFactory(mockProductCollectFactory);
		action.setUpload_productcollect_in(IN);
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockProductCollectDAOW.getProductCollectByUserAndProductId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockProductCollect);
		EasyMock.expect(mockProductCollect.getCounterService()).andReturn(mockCounterService);
		mockProductCollect.normal((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject());
		EasyMock.expect(mockProductCollectDAOW.getProductCollectByUserAndProductId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(null);
		EasyMock.expect(mockProductCollectFactory.newInstance((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockProductCollect);
		mockProductCollectDAOW.save(mockProductCollect);
		mockCounterService.increaseProductCount((String) EasyMock.anyObject());
		EasyMock.expect(mockProductCollectDAOW.getProductCollectByUserAndProductId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockProductCollect);
		EasyMock.expect(mockProductCollect.getCounterService()).andReturn(null);
		mockProductCollect.setCounterService(mockCounterService);
		mockProductCollect.normal((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject());
		EasyMock.expect(mockProductCollectDAOW.getProductCollectByUserAndProductId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(null);
		EasyMock.expect(mockProductCollectFactory.newInstance((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockProductCollect);
		mockProductCollectDAOW.save(mockProductCollect);
		mockCounterService.increaseProductCount((String) EasyMock.anyObject());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testReturnSuccessWhenProductIdIsNull() throws Exception {
		action.setUpload_productcollect_in("{\"productcollect\":[{\"machineID\":0,\"userIDReal\":0,\"userIDAnonymous\":0},{\"machineID\":1,\"userIDReal\":0,\"userIDAnonymous\":0}]}");
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testReturnSuccessWhenProductIdIsEmpty() throws Exception {
		action.setUpload_productcollect_in("{\"productcollect\":[{\"machineID\":0,\"userIDReal\":0,\"userIDAnonymous\":0,\"productID\":\"\"},{\"machineID\":1,\"userIDReal\":0,\"userIDAnonymous\":0,\"productID\":\"\"}]}");
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadProductCollectInIsNull() throws Exception {
		action.setUpload_productcollect_in(null);
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
		action.setUpload_productcollect_in("");
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
		EasyMock.replay(mockCounterService, mockProductCollectDAOW, mockProductCollectFactory, mockProductCollect);
	}

	private void verify() {
		EasyMock.verify(mockCounterService, mockProductCollectDAOW, mockProductCollectFactory, mockProductCollect);
	}

}
