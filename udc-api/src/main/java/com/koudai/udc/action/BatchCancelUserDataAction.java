package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.UserDataService;
import com.koudai.udc.utils.BindingInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class BatchCancelUserDataAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -6174468197666862939L;

	private static final Logger LOGGER = Logger.getLogger(BatchCancelUserDataAction.class);

	private String cancelUserdataIn;

	private UserDataService userDataService;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(cancelUserdataIn)) {
			throw new IncorrectInputParameterException("batchcancel_userdata_in is null or empty");
		}
		LOGGER.info("Batch cancel user data request is : " + cancelUserdataIn);

		JSONObject inObject = new JSONObject(cancelUserdataIn);
		final String userId = inObject.optString(BindingInfoKey.USER_ID, null);
		if (S.isInvalidValue(userId)) {
			throw new IncorrectInputParameterException("User id is null or empty when batch canceling user data");
		}
		userDataService.batchCancel(userId);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("batchCancelUserData cost>>>" + costTime);
	}

	public void setBatchcancel_userdata_in(String batchcancel_userdata_in) {
		this.cancelUserdataIn = batchcancel_userdata_in;
	}

	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}

}
