package com.koudai.udc.action;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.domain.ShopFavorite;
import com.koudai.udc.domain.factory.ShopFavoriteFactory;
import com.koudai.udc.domain.service.CounterService;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.opensymphony.xwork.ActionSupport;

public class UploadShopFavoriteV2ActionTest extends TestCase {

	private UploadShopFavoriteV2Action action;

	private CounterService mockCounterService;

	private ShopFavoriteDAO mockShopFavoriteDAOW;

	private ShopFavoriteFactory mockShopFavoriteFactory;

	private ShopFavorite mockShopFavorite;

	private static final String IN = "{\"shopfavorite\":[{\"machineID\":0,\"userIDReal\":\"@taobao:xxx\",\"userIDAnonymous\":\"@anonymous:xxx\",\"shopID\":0},{\"machineID\":1,\"userIDReal\":\"@taobao:xxx\",\"userIDAnonymous\":\"@anonymous:xxx\",\"shopID\":1}]}";

	protected void setUp() throws Exception {
		action = new UploadShopFavoriteV2Action();
		mockCounterService = EasyMock.createStrictMock(CounterService.class);
		mockShopFavoriteDAOW = EasyMock.createStrictMock(ShopFavoriteDAO.class);
		mockShopFavoriteFactory = EasyMock.createStrictMock(ShopFavoriteFactory.class);
		mockShopFavorite = EasyMock.createStrictMock(ShopFavorite.class);
		action.setCounterService(mockCounterService);
		action.setShopFavoriteDAOW(mockShopFavoriteDAOW);
		action.setShopFavoriteFactory(mockShopFavoriteFactory);
		action.setUpload_shopfavorite_in(IN);
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockShopFavoriteDAOW.getShopFavoriteByUserAndShopId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockShopFavorite);
		EasyMock.expect(mockShopFavorite.getCounterService()).andReturn(mockCounterService);
		mockShopFavorite.normal((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject());
		EasyMock.expect(mockShopFavoriteDAOW.getShopFavoriteByUserAndShopId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(null);
		EasyMock.expect(mockShopFavoriteFactory.newInstance((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockShopFavorite);
		mockShopFavoriteDAOW.save(mockShopFavorite);
		mockCounterService.increaseShopCount((String) EasyMock.anyObject());
		EasyMock.expect(mockShopFavoriteDAOW.getShopFavoriteByUserAndShopId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockShopFavorite);
		EasyMock.expect(mockShopFavorite.getCounterService()).andReturn(null);
		mockShopFavorite.setCounterService(mockCounterService);
		mockShopFavorite.normal((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject());
		EasyMock.expect(mockShopFavoriteDAOW.getShopFavoriteByUserAndShopId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(null);
		EasyMock.expect(mockShopFavoriteFactory.newInstance((String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockShopFavorite);
		mockShopFavoriteDAOW.save(mockShopFavorite);
		mockCounterService.increaseShopCount((String) EasyMock.anyObject());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testReturnSuccessWhenProductIdIsNull() throws Exception {
		action.setUpload_shopfavorite_in("{\"shopfavorite\":[{\"machineID\":0,\"userIDReal\":0,\"userIDAnonymous\":0},{\"machineID\":1,\"userIDReal\":0,\"userIDAnonymous\":0}]}");
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testReturnSuccessWhenProductIdIsEmpty() throws Exception {
		action.setUpload_shopfavorite_in("{\"shopfavorite\":[{\"machineID\":0,\"userIDReal\":0,\"userIDAnonymous\":0,\"shopID\":\"\"},{\"machineID\":1,\"userIDReal\":0,\"userIDAnonymous\":0,\"shopID\":\"\"}]}");
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadShopFavoriteInIsNull() throws Exception {
		action.setUpload_shopfavorite_in(null);
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
		}
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadShopFavoriteInIsEmpty() throws Exception {
		action.setUpload_shopfavorite_in("");
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
		EasyMock.replay(mockCounterService, mockShopFavoriteDAOW, mockShopFavoriteFactory, mockShopFavorite);
	}

	private void verify() {
		EasyMock.verify(mockCounterService, mockShopFavoriteDAOW, mockShopFavoriteFactory, mockShopFavorite);
	}

}
