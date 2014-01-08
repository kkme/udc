package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.opensymphony.xwork.ActionSupport;

public class GetShopFavoriteActionTest extends TestCase {

	private GetShopFavoriteAction action;

	private ShopFavoriteDAO mockShopFavoriteDAOR;

	private static final String USER_ID = "@sina:123";
	private static final int START = 1;
	private static final int END = 3;

	private static final List<String> SHOP_IDS = new ArrayList<String>();

	static {
		SHOP_IDS.add("123456");
	}

	protected void setUp() throws Exception {
		action = new GetShopFavoriteAction();
		mockShopFavoriteDAOR = EasyMock.createStrictMock(ShopFavoriteDAO.class);
		action.setShopFavoriteDAOR(mockShopFavoriteDAOR);
		action.setUserID(USER_ID);
		action.setStartPos(START);
		action.setEndPos(END);
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockShopFavoriteDAOR.getShopIdsByUserIdAndStartAndEndPosOrderByTime(USER_ID, START, END)).andReturn(SHOP_IDS);
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertTrue(action.getResult().toString().contains("\"shop_num\":1"));
		verify();
	}

	public void testHappyPathWhenShopIdsIsEmpty() throws Exception {
		EasyMock.expect(mockShopFavoriteDAOR.getShopIdsByUserIdAndStartAndEndPosOrderByTime(USER_ID, START, END)).andReturn(new ArrayList<String>());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertTrue(action.getResult().toString().contains("\"shop_num\":0"));
		verify();
	}

	public void testHappyPathWhenStartAndEndPositionIsZero() throws Exception {
		action.setStartPos(0);
		action.setEndPos(0);
		EasyMock.expect(mockShopFavoriteDAOR.getAllShopIdsByUserIdOrderByTime(USER_ID)).andReturn(new ArrayList<String>());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertTrue(action.getResult().toString().contains("\"shop_num\":0"));
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUserIdIsNull() throws Exception {
		action.setUserID(null);
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
			assertTrue(action.getResult().toString().contains("User id is null or empty"));
		}
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenUserIdIsEmpty() throws Exception {
		action.setUserID("");
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
			assertTrue(action.getResult().toString().contains("User id is null or empty"));
		}
		verify();
	}

	public void testThrowIncorrectInputParameterExceptionWhenStartMoreThanEndPosition() throws Exception {
		action.setStartPos(2);
		action.setEndPos(1);
		replay();
		try {
			action.execute();
		} catch (ActionErrorDispatchException e) {
			assertEquals(ActionSupport.ERROR, e.getResultCode());
			assertTrue(action.getResult().toString().contains("\"status_code\":1"));
			assertTrue(action.getResult().toString().contains("Start position should less than end position"));
		}
		verify();
	}

	private void replay() {
		EasyMock.replay(mockShopFavoriteDAOR);
	}

	private void verify() {
		EasyMock.verify(mockShopFavoriteDAOR);
	}

}
