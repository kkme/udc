package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.TaobaoBindingInfo;

public interface TaobaoBindingInfoFactory {

	TaobaoBindingInfo newInstance(String userId, String bindId);

}
