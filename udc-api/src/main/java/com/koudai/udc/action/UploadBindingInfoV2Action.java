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

public class UploadBindingInfoV2Action extends JsonResultWithoutTransactionAction {

	private static final long serialVersionUID = -7917790136047139592L;
	private static final Logger LOGGER = Logger.getLogger(UploadBindingInfoV2Action.class);

	private String bindinginfoIn;

	private BindingInfoDAO bindingInfoDAOW;
	private BindingInfoFactory bindingInfoFactory;
	private UserDataService userDataService;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(bindinginfoIn)) {
			throw new IncorrectInputParameterException("upload_bindinginfo_in of version 2 is null or empty");
		}
		LOGGER.info("Upload binding info request of version 2 is : " + bindinginfoIn);

		JSONObject content = new JSONObject(bindinginfoIn);
		JSONObject inObject = content.getJSONObject(BindingInfoKey.CONTENT_KEY);
		final String mainId = inObject.optString(BindingInfoKey.MAIN_ID, null);
		final String subId = inObject.optString(BindingInfoKey.SUB_ID, null);

		if (S.isInvalidValue(subId) || !S.isAnonymousUser(subId) || S.INVALID_SUB_IDS.contains(subId)) {
			throw new IncorrectInputParameterException("Sub id of synchronize user data request is invalid");
		}
		if (S.isInvalidValue(mainId) || !S.isRealUser(mainId)) {
			throw new IncorrectInputParameterException("Main id of synchronize user data request is invalid");
		}

		final String machineId = subId.split(S.PREFIX_ANONYMOUS)[1];
		if (isRollbackForNewBindingInfo(mainId, machineId)) {
			throw new TransactionCallbackException("Save new binding info failed with main id < " + mainId + " > and sub id < " + subId + " >");
		}
		if (isRollbackForSynchronizing(mainId, subId, machineId)) {
			throw new TransactionCallbackException("Synchronize user data failed with main id < " + mainId + " > and sub id < " + subId + " >");
		}

		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadBindingInfo2 cost>>>" + costTime);
	}

	private Boolean isRollbackForNewBindingInfo(final String mainId, final String machineId) {
		return transactionTemplate.execute(new NewBindingInfoTransactionCallback(this, mainId, machineId));
	}

	private Boolean isRollbackForSynchronizing(final String mainId, final String subId, final String machineId) {
		return transactionTemplate.execute(new SynchronizeUserDataTransactionCallback(this, mainId, subId, machineId));
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
