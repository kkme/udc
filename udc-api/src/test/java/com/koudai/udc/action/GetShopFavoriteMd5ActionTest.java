package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.koudai.udc.service.Md5Generator;
import com.opensymphony.xwork.ActionSupport;

public class GetShopFavoriteMd5ActionTest extends TestCase {

	private GetShopFavoriteMd5Action action;

	private Md5Generator mockMd5Generator;

	private ShopFavoriteDAO mockShopFavoriteDAOR;

	private static final String USER_ID = "@sina:123";

	private static final int START = 0;

	private static final int END = 1;

	private static final List<String> SHOP_ID_LIST = new ArrayList<String>();

	static {
		SHOP_ID_LIST.add("p1");
	}

	protected void setUp() throws Exception {
		action = new GetShopFavoriteMd5Action();
		mockMd5Generator = EasyMock.createStrictMock(Md5Generator.class);
		mockShopFavoriteDAOR = EasyMock.createStrictMock(ShopFavoriteDAO.class);
		action.setMd5Generator(mockMd5Generator);
		action.setShopFavoriteDAOR(mockShopFavoriteDAOR);
		action.setUserID(USER_ID);
		action.setStartPos(START);
		action.setEndPos(END);
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockShopFavoriteDAOR.getShopIdsByUserIdAndStartAndEndPosOrderByTime(USER_ID, START, END)).andReturn(SHOP_ID_LIST);
		EasyMock.expect(mockMd5Generator.getMd5Code(SHOP_ID_LIST)).andReturn("123");
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertFalse(action.getResult().toString().contains("\"md5\":\"\""));
		verify();
	}

	public void testHappyPathWhenStartAndEndPositionIsZero() throws Exception {
		action.setStartPos(0);
		action.setEndPos(0);
		EasyMock.expect(mockShopFavoriteDAOR.getAllShopIdsByUserIdOrderByTime(USER_ID)).andReturn(SHOP_ID_LIST);
		EasyMock.expect(mockMd5Generator.getMd5Code(SHOP_ID_LIST)).andReturn("123");
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertFalse(action.getResult().toString().contains("\"md5\":\"\""));
		verify();
	}

	public void testHappyPathWhenShopIdsIsEmpty() throws Exception {
		EasyMock.expect(mockShopFavoriteDAOR.getShopIdsByUserIdAndStartAndEndPosOrderByTime(USER_ID, START, END)).andReturn(new ArrayList<String>());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertTrue(action.getResult().toString().contains("\"md5\":\"\""));
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
		EasyMock.replay(mockMd5Generator, mockShopFavoriteDAOR);
	}

	private void verify() {
		EasyMock.verify(mockMd5Generator, mockShopFavoriteDAOR);
	}

}
