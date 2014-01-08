package com.koudai.udc.action;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.UserActivation;
import com.koudai.udc.domain.UserTestInfo;
import com.koudai.udc.domain.factory.UserTestInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.exception.UserNotActiveParameterException;
import com.koudai.udc.persistence.UserActivationDAO;
import com.koudai.udc.persistence.UserTestInfoDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UserTestInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadUserTestInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -7906213931080857573L;

	private static final Logger LOGGER = Logger.getLogger(UploadUserTestInfoAction.class);

	private String userTestInfoIn;

	private UserTestInfoDAO userTestInfoDAOW;

	private UserActivationDAO userActivationDAOW;

	private UserTestInfoFactory userTestInfoFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(userTestInfoIn)) {
			throw new IncorrectInputParameterException("user_test_info_in is null or empty");
		}
		LOGGER.info("UploadUserTestInfo request is : " + userTestInfoIn);
		JSONObject content = new JSONObject(userTestInfoIn);
		final String userId = content.optString(UserTestInfoKey.USERID, null);
		if (S.isInvalidValue(userId) || !S.isRealUser(userId)) {
			throw new IncorrectInputParameterException("User id is invalid");
		}
		UserActivation userActivation = userActivationDAOW.getUserActivationByUserId(userId);
		if (userActivation == null || !userActivation.isActive()) {
			throw new UserNotActiveParameterException("User < " + userId + " > have not been activated");
		}
		JSONArray inArray = content.getJSONArray(UserTestInfoKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String questionId = singleObject.optString(UserTestInfoKey.QUESTIONID, null);
			String answerId = singleObject.optString(UserTestInfoKey.ANSWERID, null);
			if (S.isInvalidValue(questionId) || S.isInvalidValue(answerId)) {
				throw new IncorrectInputParameterException("questionId or answerId is null or empty");
			}
			saveOrUpdateUserTestInfo(userId, questionId, answerId);
		}
		if (inArray.length() > 0 && !userActivation.isAlreadyTest()) {
			userActivation.setAlreadyTest(true);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadUserTestInfo cost>>>" + costTime);
	}

	private void saveOrUpdateUserTestInfo(final String userId, final String questionId, String answerId) {
		UserTestInfo userTestInfo = userTestInfoDAOW.getUserTestInfoByUserIdAndQuestionId(userId, questionId);
		if (answerId.contains(S.COMMA_STR)) {
			List<String> answerIds = Arrays.asList(answerId.split(S.COMMA_STR));
			Collections.sort(answerIds, new AscendingComparator());
			answerId = getFormatterAnswerId(answerIds);
		}
		if (userTestInfo == null) {
			userTestInfoDAOW.save(userTestInfoFactory.newInstance(userId, questionId, answerId));
		} else if (!answerId.equals(userTestInfo.getAnswerId())) {
			userTestInfo.setAnswerId(answerId);
		}
	}

	public String getFormatterAnswerId(List<String> answerIds) {
		StringBuffer sb = new StringBuffer();
		for (String answerId : answerIds) {
			sb.append(answerId).append(S.COMMA_STR);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public void setUser_test_info_in(String user_test_info_in) {
		this.userTestInfoIn = user_test_info_in;
	}

	public void setUserTestInfoDAOW(UserTestInfoDAO userTestInfoDAOW) {
		this.userTestInfoDAOW = userTestInfoDAOW;
	}

	public void setUserActivationDAOW(UserActivationDAO userActivationDAOW) {
		this.userActivationDAOW = userActivationDAOW;
	}

	public void setUserTestInfoFactory(UserTestInfoFactory userTestInfoFactory) {
		this.userTestInfoFactory = userTestInfoFactory;
	}

}
