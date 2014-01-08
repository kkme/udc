package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.factory.BindingInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.exception.TransactionCallbackException;
import com.koudai.udc.persistence.BindingInfoDAO;
import com.koudai.udc.service.UserDataService;
import com.koudai.udc.utils.BindingInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadBindingInfoV3Action extends JsonResultWithoutTransactionAction {

	private static final long serialVersionUID = -7175504470888856523L;
	private static final Logger LOGGER = Logger.getLogger(UploadBindingInfoV3Action.class);

	private String bindinginfoIn;

	private BindingInfoDAO bindingInfoDAOW;
	private BindingInfoFactory bindingInfoFactory;
	private UserDataService userDataService;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(bindinginfoIn)) {
			throw new IncorrectInputParameterException("upload_bindinginfo_in of version 3 is null or empty");
		}
		LOGGER.info("Upload binding info request of version 3 is : " + bindinginfoIn);

		JSONObject content = new JSONObject(bindinginfoIn);
		JSONObject inObject = content.getJSONObject(BindingInfoKey.CONTENT_KEY);
		final String userId = inObject.optString(BindingInfoKey.USER_ID, null);
		final String machineId = inObject.optString(BindingInfoKey.MACHINE_ID, null);

		if (S.isInvalidValue(userId) || S.isAnonymousUser(userId)) {
			throw new IncorrectInputParameterException("User id is invalid");
		}
		if (S.isInvalidValue(machineId)) {
			throw new IncorrectInputParameterException("Machine id is invalid");
		}

		if (isRollbackForNewBindingInfo(userId, machineId)) {
			throw new TransactionCallbackException("Save binding info failed with user id < " + userId + " > and machine id < " + machineId + " >");
		}
		if (isRollbackForDealWithAnonymousData(userId, machineId)) {
			throw new TransactionCallbackException("Deal with anonymous data failed with user id < " + userId + " > and machine id < " + machineId + " >");
		}

		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadBindingInfo3 cost>>>" + costTime);
	}

	private boolean isRollbackForNewBindingInfo(String userId, String machineId) {
		return transactionTemplate.execute(new NewBindingInfoV2TransactionCallback(this, userId, machineId));
	}

	private boolean isRollbackForDealWithAnonymousData(String userId, String machineId) {
		return transactionTemplate.execute(new DealWithAnonymousDataTransactionCallback(this, userId, machineId));
	}

	public void setUpload_bindinginfo_in(String upload_bindinginfo_in) {
		this.bindinginfoIn = upload_bindinginfo_in;
	}

	public BindingInfoDAO getBindingInfoDAOW() {
		return bindingInfoDAOW;
	}

	public void setBindingInfoDAOW(BindingInfoDAO bindingInfoDAOW) {
		this.bindingInfoDAOW = bindingInfoDAOW;
	}

	public BindingInfoFactory getBindingInfoFactory() {
		return bindingInfoFactory;
	}

	public void setBindingInfoFactory(BindingInfoFactory bindingInfoFactory) {
		this.bindingInfoFactory = bindingInfoFactory;
	}

	public UserDataService getUserDataService() {
		return userDataService;
	}

	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}

}
