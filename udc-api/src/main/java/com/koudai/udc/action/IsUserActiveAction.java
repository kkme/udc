package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.UserActivation;
import com.koudai.udc.domain.factory.UserActivationFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.exception.UserNotActiveParameterException;
import com.koudai.udc.persistence.UserActivationDAO;
import com.koudai.udc.service.impl.MyStreetConfiguration;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class IsUserActiveAction extends ActionSupport {

	private static final long serialVersionUID = -174745310209646561L;

	private static final Logger LOGGER = Logger.getLogger(IsUserActiveAction.class);

	private String userId;

	private UserActivationDAO userActivationDAOW;

	private UserActivationFactory userActivationFactory;

	private MyStreetConfiguration myStreetConfiguration;

	private JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(userId) || !S.isRealUser(userId)) {
				throw new IncorrectInputParameterException("User id is invalid");
			}
			LOGGER.info("IsUserActive request userID is : " + userId);
			checkUserActivation();
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("isUserActive cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getUserActiveResult(S.USER_NOT_ACTIVATED, e.getMessage());
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	private void checkUserActivation() throws UserNotActiveParameterException {
		UserActivation userActivation = userActivationDAOW.getUserActivationByUserId(userId);
		if (userActivation == null) {
			if (myStreetConfiguration.isVipModel()) {
				throw new UserNotActiveParameterException("User < " + userId + " > have not been activated");
			} else {
				userActivation = userActivationFactory.newInstance(userId, true);
				userActivationDAOW.save(userActivation);
			}
		}
		if (myStreetConfiguration.getWhiteList().contains(userId.toLowerCase())) {
			result = getUserActiveResult(S.WHITE_LIST_USER, S.EMPTY_STR);
			return;
		}
		if (userActivation.isAlreadyTest()) {
			result = getUserActiveResult(S.USER_ACTIVATED_AND_TESTED, S.EMPTY_STR);
			return;
		} else if (userActivation.isActive()) {
			result = getUserActiveResult(S.USER_ACTIVATED_NOT_TESTED, S.EMPTY_STR);
			return;
		}
		throw new UserNotActiveParameterException("User < " + userId + " > have not been activated");
	}

	private JSONObject getUserActiveResult(int code, String reason) {
		try {
			JSONObject result = new JSONObject();
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			result.put("status", statusObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public JSONObject getResult() {
		return result;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setUserActivationDAOW(UserActivationDAO userActivationDAOW) {
		this.userActivationDAOW = userActivationDAOW;
	}

	public void setUserActivationFactory(UserActivationFactory userActivationFactory) {
		this.userActivationFactory = userActivationFactory;
	}

	public void setMyStreetConfiguration(MyStreetConfiguration myStreetConfiguration) {
		this.myStreetConfiguration = myStreetConfiguration;
	}

}
