package com.koudai.udc.action;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.domain.ShopFavorite;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.opensymphony.xwork.ActionSupport;

public class CancelShopFavoriteActionTest extends TestCase {

	private CancelShopFavoriteAction action;

	private ShopFavoriteDAO mockShopFavoriteDAOW;

	private ShopFavorite mockShopFavorite;

	private static final String IN = "{\"cancelfavorite\":[{\"machineID\":0,\"userID\":0,\"shopID\":0},{\"machineID\":1,\"userID\":1,\"shopID\":1}]}";

	protected void setUp() throws Exception {
		action = new CancelShopFavoriteAction();
		mockShopFavoriteDAOW = EasyMock.createStrictMock(ShopFavoriteDAO.class);
		mockShopFavorite = EasyMock.createStrictMock(ShopFavorite.class);
		action.setShopFavoriteDAOW(mockShopFavoriteDAOW);
		action.setCancel_shoppingfavorite_in(IN);
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockShopFavoriteDAOW.getShopFavoriteByUserAndShopId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockShopFavorite);
		mockShopFavorite.cancel();
		EasyMock.expect(mockShopFavoriteDAOW.getShopFavoriteByUserAndShopId((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(mockShopFavorite);
		mockShopFavorite.cancel();
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUploadShopFavoriteInIsNull() throws Exception {
		action.setCancel_shoppingfavorite_in(null);
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
		action.setCancel_shoppingfavorite_in("");
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
		EasyMock.replay(mockShopFavoriteDAOW, mockShopFavorite);
	}

	private void verify() {
		EasyMock.verify(mockShopFavoriteDAOW, mockShopFavorite);
	}

}
