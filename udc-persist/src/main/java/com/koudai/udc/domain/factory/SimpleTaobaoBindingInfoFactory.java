package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.TaobaoBindingInfo;

public class SimpleTaobaoBindingInfoFactory implements TaobaoBindingInfoFactory {

	@Override
	public TaobaoBindingInfo newInstance(String userId, String bindId) {
		return new TaobaoBindingInfo(userId, bindId);
	}

}
