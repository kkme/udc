package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.UserActivation;
import com.koudai.udc.domain.factory.UserActivationFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserActivationDAO;
import com.koudai.udc.utils.PayInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadUserIosPayInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -1472908612818206541L;

	private static final Logger LOGGER = Logger.getLogger(UploadUserIosPayInfoAction.class);

	private String iosPayInfoIn;

	private UserActivationDAO userActivationDAOW;

	private UserActivationFactory userActivationFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(iosPayInfoIn)) {
			throw new IncorrectInputParameterException("iosPayInfoIn is null or empty");
		}
		LOGGER.info("UploadUserIosPayInfo request is : " + iosPayInfoIn);
		JSONObject content = new JSONObject(iosPayInfoIn);
		JSONObject inObject = content.getJSONObject(PayInfoKey.CONTENT_KEY);
		final String userId = inObject.optString(PayInfoKey.USERID, null);
		if (S.isInvalidValue(userId) || !S.isRealUser(userId)) {
			throw new IncorrectInputParameterException("User id is invalid");
		}
		UserActivation userActivation = userActivationDAOW.getUserActivationByUserId(userId);
		if (userActivation == null) {
			userActivationDAOW.save(userActivationFactory.newInstance(userId));
		} else {
			userActivation.setIosPay(true);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadUserIosPayInfo cost>>>" + costTime);
	}

	public void setIos_pay_info_in(String ios_pay_info_in) {
		this.iosPayInfoIn = ios_pay_info_in;
	}

	public void setUserActivationDAOW(UserActivationDAO userActivationDAOW) {
		this.userActivationDAOW = userActivationDAOW;
	}

	public void setUserActivationFactory(UserActivationFactory userActivationFactory) {
		this.userActivationFactory = userActivationFactory;
	}

}
