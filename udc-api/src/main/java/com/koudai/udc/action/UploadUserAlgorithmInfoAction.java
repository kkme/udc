package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.UserAlgorithmInfo;
import com.koudai.udc.domain.factory.UserAlgorithmInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserAlgorithmInfoDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UserAlgorithmInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadUserAlgorithmInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -8732382021231270143L;

	private static final Logger LOGGER = Logger.getLogger(UploadUserAlgorithmInfoAction.class);

	private UserAlgorithmInfoDAO userAlgorithmInfoDAOW;

	private UserAlgorithmInfoFactory userAlgorithmInfoFactory;

	private String algorithmInfoIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(algorithmInfoIn)) {
			throw new IncorrectInputParameterException("algorithm_info_in is null or empty");
		}
		LOGGER.info("Upload user algorithm info request is : " + algorithmInfoIn);
		JSONObject content = new JSONObject(algorithmInfoIn);
		final String userId = content.optString(UserAlgorithmInfoKey.USER_ID, null);
		final int algorithmVersion = content.optInt(UserAlgorithmInfoKey.ALGORITHM_VERSION, 0);
		final String sessionVersion = content.optString(UserAlgorithmInfoKey.SESSION_VERSION, null);
		final String userStyle = content.optString(UserAlgorithmInfoKey.USER_STYLE, null);
		if (S.isInvalidValue(userId) || !S.isRealUser(userId)) {
			throw new IncorrectInputParameterException("User id is invalid");
		}
		if (algorithmVersion == 0 || (algorithmVersion == 5 && sessionVersion == null)) {
			throw new IncorrectInputParameterException("Algorithm version is invalid");
		}
		UserAlgorithmInfo userAlgorithmInfo = userAlgorithmInfoDAOW.getUserAlgorithmInfoByUserId(userId);
		if (userAlgorithmInfo == null) {
			userAlgorithmInfoDAOW.save(userAlgorithmInfoFactory.newInstance(userId, algorithmVersion, sessionVersion, userStyle));
			return;
		}
		userAlgorithmInfo.modifyProperties(algorithmVersion, sessionVersion, userStyle);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadUserAlgorithmInfo cost>>>" + costTime);
	}

	public void setUserAlgorithmInfoDAOW(UserAlgorithmInfoDAO userAlgorithmInfoDAOW) {
		this.userAlgorithmInfoDAOW = userAlgorithmInfoDAOW;
	}

	public void setUserAlgorithmInfoFactory(UserAlgorithmInfoFactory userAlgorithmInfoFactory) {
		this.userAlgorithmInfoFactory = userAlgorithmInfoFactory;
	}

	public void setAlgorithm_info_in(String algorithm_info_in) {
		this.algorithmInfoIn = algorithm_info_in;
	}

}
