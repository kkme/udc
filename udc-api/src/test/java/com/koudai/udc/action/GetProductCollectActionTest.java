package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.opensymphony.xwork.ActionSupport;

public class GetProductCollectActionTest extends TestCase {

	private GetProductCollectAction action;

	private ProductCollectDAO mockProductCollectDAOR;

	private static final String USER_ID = "@sina:123";
	private static final int START = 0;
	private static final int END = 2;

	private static final List<String> PRODUCT_IDS = new ArrayList<String>();

	static {
		PRODUCT_IDS.add("123456");
	}

	protected void setUp() throws Exception {
		action = new GetProductCollectAction();
		mockProductCollectDAOR = EasyMock.createStrictMock(ProductCollectDAO.class);
		action.setProductCollectDAOR(mockProductCollectDAOR);
		action.setUserID(USER_ID);
		action.setStartPos(START);
		action.setEndPos(END);
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockProductCollectDAOR.getProductIdsByUserIdAndStartAndEndPosOrderByTime(USER_ID, START, END)).andReturn(PRODUCT_IDS);
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertTrue(action.getResult().toString().contains("\"product_num\":1"));
		verify();
	}

	public void testHappyPathWhenProductIdsIsEmpty() throws Exception {
		EasyMock.expect(mockProductCollectDAOR.getProductIdsByUserIdAndStartAndEndPosOrderByTime(USER_ID, START, END)).andReturn(new ArrayList<String>());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertTrue(action.getResult().toString().contains("\"product_num\":0"));
		verify();
	}

	public void testHappyPathWhenStartAndEndPositionIsZero() throws Exception {
		action.setStartPos(0);
		action.setEndPos(0);
		EasyMock.expect(mockProductCollectDAOR.getAllProductIdsByUserIdOrderByTime(USER_ID)).andReturn(new ArrayList<String>());
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		assertTrue(action.getResult().toString().contains("\"product_num\":0"));
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
		EasyMock.replay(mockProductCollectDAOR);
	}

	private void verify() {
		EasyMock.verify(mockProductCollectDAOR);
	}

}
