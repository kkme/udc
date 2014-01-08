package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.UserDataService;
import com.koudai.udc.utils.BindingInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class ReplaceUserDataAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 5638363287758229416L;

	private static final Logger LOGGER = Logger.getLogger(ReplaceUserDataAction.class);

	private String replaceUserdataIn;

	private UserDataService userDataService;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(replaceUserdataIn)) {
			throw new IncorrectInputParameterException("replace_userdata_in is null or empty");
		}
		LOGGER.info("Replace user data request is : " + replaceUserdataIn);

		JSONObject inObject = new JSONObject(replaceUserdataIn);
		final String mainId = inObject.optString(BindingInfoKey.MAIN_ID, null);
		final String subId = inObject.optString(BindingInfoKey.SUB_ID, null);

		if (S.isInvalidValue(subId) || !S.isAnonymousUser(subId) || S.INVALID_SUB_IDS.contains(subId)) {
			throw new IncorrectInputParameterException("Sub id of replace user data request is invalid");
		}
		if (S.isInvalidValue(mainId) || !S.isRealUser(mainId)) {
			throw new IncorrectInputParameterException("Main id of replace user data request is invalid");
		}

		final String machineId = subId.split(S.PREFIX_ANONYMOUS)[1];
		userDataService.replace(mainId, subId, machineId);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("replaceUserData cost>>>" + costTime);
	}

	public void setReplace_userdata_in(String replace_userdata_in) {
		this.replaceUserdataIn = replace_userdata_in;
	}

	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}

}
