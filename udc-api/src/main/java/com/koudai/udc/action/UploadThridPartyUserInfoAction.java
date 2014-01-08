package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.UserBasicInfo;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserBasicInfoDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UserBasicInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadThridPartyUserInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -5059378576318209048L;

	private static final Logger LOGGER = Logger.getLogger(UploadThridPartyUserInfoAction.class);

	private String uploadThirdparyUserinfoIn;

	private UserBasicInfoDAO userBasicInfoDAOW;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(uploadThirdparyUserinfoIn)) {
			throw new IncorrectInputParameterException("upload_thirdpary_userinfo_in is null or empty");
		}
		LOGGER.info("Upload third party user info request is : " + uploadThirdparyUserinfoIn);
		JSONObject content = new JSONObject(uploadThirdparyUserinfoIn);
		final String thirdPartyId = content.optString(UserBasicInfoKey.THIRD_PARTY_ID, null);
		if (S.isInvalidValue(thirdPartyId)) {
			throw new IncorrectInputParameterException("Third party id is null or empty when uploading third party user info");
		}
		final String nick = content.optString(UserBasicInfoKey.NICK, null);
		if (S.isInvalidValue(nick)) {
			throw new IncorrectInputParameterException("Nick is null or empty when uploading third party user info");
		}
		UserBasicInfo userBasicInfo = userBasicInfoDAOW.getUserBasicInfoByThirdPartyId(thirdPartyId);
		if (userBasicInfo == null) {
			throw new IncorrectInputParameterException("Can not find third party user with name < " + thirdPartyId + " >");
		}
		userBasicInfo.setName(nick);
		userBasicInfo.setLastLoginTime(new Date());
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadThridPartyUserInfo cost>>>" + costTime);
	}

	public void setUpload_thirdpary_userinfo_in(String upload_thirdpary_userinfo_in) {
		this.uploadThirdparyUserinfoIn = upload_thirdpary_userinfo_in;
	}

	public void setUserBasicInfoDAOW(UserBasicInfoDAO userBasicInfoDAOW) {
		this.userBasicInfoDAOW = userBasicInfoDAOW;
	}

}
