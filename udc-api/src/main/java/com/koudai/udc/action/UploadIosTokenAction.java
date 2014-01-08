package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.IosPush;
import com.koudai.udc.domain.factory.IosPushFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.IosPushDAO;
import com.koudai.udc.utils.IosPushKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadIosTokenAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 5402627263013954439L;

	private static final Logger LOGGER = Logger.getLogger(UploadIosTokenAction.class);

	private String iosTokenIn;

	private IosPushDAO iosPushDAOW;

	private IosPushFactory iosPushFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(iosTokenIn)) {
			throw new IncorrectInputParameterException("upload_ios_token_in is null or empty");
		}
		LOGGER.info("Upload ios token request is : " + iosTokenIn);
		JSONObject content = new JSONObject(iosTokenIn);
		JSONObject inObject = content.getJSONObject(IosPushKey.CONTENT_KEY);
		final String uuid = inObject.optString(IosPushKey.UUID, null);
		final String token = inObject.optString(IosPushKey.TOKEN, null);
		if (S.isInvalidValue(uuid)) {
			throw new IncorrectInputParameterException("UUID upload is null or empty");
		}
		if (S.isInvalidValue(token)) {
			throw new IncorrectInputParameterException("Token upload is null or empty");
		}
		IosPush iosPush = iosPushDAOW.getIosPushByUUID(uuid);
		if (iosPush == null) {
			iosPushDAOW.save(iosPushFactory.newInstance(uuid, token));
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadIosToken cost>>>" + costTime);
	}

	public void setUpload_ios_token_in(String upload_ios_token_in) {
		this.iosTokenIn = upload_ios_token_in;
	}

	public void setIosPushDAOW(IosPushDAO iosPushDAOW) {
		this.iosPushDAOW = iosPushDAOW;
	}

	public void setIosPushFactory(IosPushFactory iosPushFactory) {
		this.iosPushFactory = iosPushFactory;
	}

}
