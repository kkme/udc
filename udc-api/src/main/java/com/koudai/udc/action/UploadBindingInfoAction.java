package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.factory.BindingInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.BindingInfoDAO;
import com.koudai.udc.utils.BindingInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadBindingInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -2942435853273866910L;
	private static final Logger LOGGER = Logger.getLogger(UploadBindingInfoAction.class);

	private String bindinginfoIn;

	private BindingInfoDAO bindingInfoDAOW;
	private BindingInfoFactory bindingInfoFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(bindinginfoIn)) {
			throw new IncorrectInputParameterException("upload_bindinginfo_in is null or empty");
		}
		LOGGER.info("Upload binding info request is : " + bindinginfoIn);
		JSONObject content = new JSONObject(bindinginfoIn);
		JSONObject inObject = content.getJSONObject(BindingInfoKey.CONTENT_KEY);
		final String machineId = inObject.optString(BindingInfoKey.MACHINE_ID, null);
		final String userId = inObject.optString(BindingInfoKey.USER_ID, null);
		bindingInfoDAOW.save(bindingInfoFactory.newInstance(machineId, userId));
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadBindingInfo cost>>>" + costTime);
	}

	public void setUpload_bindinginfo_in(String upload_bindinginfo_in) {
		this.bindinginfoIn = upload_bindinginfo_in;
	}

	public void setBindingInfoDAOW(BindingInfoDAO bindingInfoDAOW) {
		this.bindingInfoDAOW = bindingInfoDAOW;
	}

	public void setBindingInfoFactory(BindingInfoFactory bindingInfoFactory) {
		this.bindingInfoFactory = bindingInfoFactory;
	}

}
