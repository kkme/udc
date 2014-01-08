package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.koudai.udc.service.Md5Generator;
import com.opensymphony.xwork.ActionSupport;

public class GetProductCollectMd5ActionTest extends TestCase {

	private GetProductCollectMd5Action action;

	private Md5Generator mockMd5Generator;

	private ProductCollectDAO mockProductCollectDAOR;

	private static final String USER_ID = "@sina:123";

	private static final int START = 0;

	private static final int END = 1;

	private static final List<String> PRODUCT_ID_LIST = new ArrayList<String>();

	static {
		PRODUCT_ID_LIST.add("p1");
	}

	protected void setUp() throws Exception {
		action = new GetProductCollectMd5Action();
		mockMd5Generator = EasyMock.createStrictMock(Md5Generator.class);
		mockProductCollectDAOR = EasyMock.createStrictMock(ProductCollectDAO.class);
		action.setMd5Generator(mockMd5Generator);
		action.setProductCollectDAOR(mockProductCollectDAOR);
		action.setUserID(USER_ID);
		action.setStartPos(START);
		action.setEndPos(END);
	}

	public void testHappyPath() throws Exception {
		EasyMock.expect(mockProductCollectDAOR.getProductIdsByUserIdAndStartAndEndPosOrderByTime(USER_ID, START, END)).andReturn(PRODUCT_ID_LIST);
		EasyMock.expect(mockMd5Generator.getMd5Code(PRODUCT_ID_LIST)).andReturn("");
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testHappyPathWhenStartAndEndPositionIsZero() throws Exception {
		action.setStartPos(0);
		action.setEndPos(0);
		EasyMock.expect(mockProductCollectDAOR.getAllProductIdsByUserIdOrderByTime(USER_ID)).andReturn(PRODUCT_ID_LIST);
		EasyMock.expect(mockMd5Generator.getMd5Code(PRODUCT_ID_LIST)).andReturn("");
		replay();
		assertEquals(ActionSupport.SUCCESS, action.execute());
		assertTrue(action.getResult().toString().contains("\"status_code\":0"));
		verify();
	}

	public void testHappyPathWhenProductIdsIsEmpty() throws Exception {
		EasyMock.expect(mockProductCollectDAOR.getProductIdsByUserIdAndStartAndEndPosOrderByTime(USER_ID, START, END)).andReturn(new ArrayList<String>());
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
		EasyMock.replay(mockMd5Generator, mockProductCollectDAOR);
	}

	private void verify() {
		EasyMock.verify(mockMd5Generator, mockProductCollectDAOR);
	}

}
