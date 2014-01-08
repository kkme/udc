package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.TaobaoBindingInfo;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.utils.S;

public class CancelTaobaoBindingAction extends TaobaoBindingAction {

	private static final long serialVersionUID = -4338672612985348261L;

	private static final Logger LOGGER = Logger.getLogger(CancelTaobaoBindingAction.class);

	private String userId;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(userId) || !S.isBindUser(userId)) {
			throw new IncorrectInputParameterException("User id is invalid");
		}
		LOGGER.info("User < " + userId + " > try to unbind taobao info");
		TaobaoBindingInfo taobaoBindingInfo = taobaoBindingInfoDAOW.getTaobaoBindingInfoByUserId(userId);
		if (taobaoBindingInfo == null) {
			throw new IncorrectInputParameterException("User < " + userId + " > has not bound any taobao user");
		}
		taobaoBindingInfo.unbind();
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("cancelTaobaoBinding cost>>>" + costTime);
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

}
