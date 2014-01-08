package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.TaobaoBindingInfo;
import com.koudai.udc.domain.exception.InvalidStatusException;
import com.koudai.udc.domain.factory.TaobaoBindingInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoBindingInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadTaobaoBindingInfoAction extends TaobaoBindingAction {

	private static final long serialVersionUID = 7043173823728858233L;

	private static final Logger LOGGER = Logger.getLogger(UploadTaobaoBindingInfoAction.class);

	private TaobaoBindingInfoFactory taobaoBindingInfoFactory;

	private String bindingInfoIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(bindingInfoIn)) {
			throw new IncorrectInputParameterException("upload_taobao_binding_info_in is null or empty");
		}
		LOGGER.info("Upload taobao binding info request is : " + bindingInfoIn);
		JSONObject content = new JSONObject(bindingInfoIn);
		JSONObject inObject = content.getJSONObject(TaobaoBindingInfoKey.UPLOAD_CONTENT_KEY);
		final String userId = inObject.optString(TaobaoBindingInfoKey.USER_ID, null);
		final String bindId = inObject.optString(TaobaoBindingInfoKey.BIND_ID, null);
		if (S.isInvalidValue(userId) || S.isInvalidValue(bindId) || !S.isBindUser(userId) || !S.isTaobaoUser(bindId)) {
			throw new IncorrectInputParameterException("User < " + userId + " > try to bind taobao user < " + bindId + " > failed");
		}
		bindTaobaoUser(userId, bindId);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadTaobaoBindingInfo cost>>>" + costTime);
	}

	private void bindTaobaoUser(final String userId, final String bindId) throws InvalidStatusException {
		TaobaoBindingInfo taobaoBindingInfo = taobaoBindingInfoDAOW.getTaobaoBindingInfoByUserId(userId);
		if (taobaoBindingInfo == null) {
			taobaoBindingInfoDAOW.save(taobaoBindingInfoFactory.newInstance(userId, bindId));
			return;
		}
		taobaoBindingInfo.bind(bindId);
	}

	public void setTaobaoBindingInfoFactory(TaobaoBindingInfoFactory taobaoBindingInfoFactory) {
		this.taobaoBindingInfoFactory = taobaoBindingInfoFactory;
	}

	public void setUpload_taobao_binding_info_in(String upload_taobao_binding_info_in) {
		this.bindingInfoIn = upload_taobao_binding_info_in;
	}

}
