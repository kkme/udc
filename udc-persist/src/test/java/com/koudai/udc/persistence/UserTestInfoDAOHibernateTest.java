package com.koudai.udc.persistence;

import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.UserTestInfo;
import com.koudai.udc.domain.factory.UserTestInfoFactory;
import com.koudai.udc.utils.SpringFactory;

public class UserTestInfoDAOHibernateTest extends TestCase {

	private UserTestInfoDAO userTestInfoDAOR;

	private UserTestInfoDAO userTestInfoDAOW;

	private UserTestInfoFactory userTestInfoFactory;

	@Override
	protected void setUp() throws Exception {
		userTestInfoDAOW = (UserTestInfoDAO) SpringFactory.bean("userTestInfoDAOW");
		userTestInfoDAOR = (UserTestInfoDAO) SpringFactory.bean("userTestInfoDAOR");
		userTestInfoFactory = (UserTestInfoFactory) SpringFactory.bean("userTestInfoFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		String userId = "@taobao:test3";
		String questionId = "1";
		String answerId = "3";
		UserTestInfo userTestInfo = userTestInfoFactory.newInstance(userId, questionId, answerId);
		userTestInfoDAOW.save(userTestInfo);
		assertNotNull(userTestInfo.getId());
		assertEquals(userId, userTestInfo.getUserId());
	}

	public void testGetUserTestInfoSuccessfullyByRD() throws Exception {
		String userId = "@taobao:test";
		String questionID = "3";
		UserTestInfo userTestInfo2 = userTestInfoDAOR.getUserTestInfoByUserIdAndQuestionId(userId, questionID);
		assertEquals("4,5", userTestInfo2.getAnswerId());
		String wrongQuestionId = "4";
		UserTestInfo userTestInfo3 = userTestInfoDAOR.getUserTestInfoByUserIdAndQuestionId(userId, wrongQuestionId);
		assertNull(userTestInfo3);
	}

	public void testGetUserTestInfoSuccessfullyByWR() throws Exception {
		String userId = "@taobao:test";
		String questionID = "3";
		UserTestInfo userTestInfo2 = userTestInfoDAOW.getUserTestInfoByUserIdAndQuestionId(userId, questionID);
		assertEquals("4,5", userTestInfo2.getAnswerId());
		String wrongQuestionId = "4";
		UserTestInfo userTestInfo3 = userTestInfoDAOW.getUserTestInfoByUserIdAndQuestionId(userId, wrongQuestionId);
		assertNull(userTestInfo3);
	}

	public void testGetUserTestInfoListByUserIdByRD() {
		String userId = "@taobao:test2";
		List<UserTestInfo> userTestInfoListByUserId = userTestInfoDAOR.getUserTestInfoListByUserId(userId);
		assertEquals(2, userTestInfoListByUserId.size());
	}

	public void testGetUserTestInfoListByUserIdByWR() {
		String userId = "@taobao:test2";
		List<UserTestInfo> userTestInfoListByUserId = userTestInfoDAOW.getUserTestInfoListByUserId(userId);
		assertEquals(2, userTestInfoListByUserId.size());
	}

}
