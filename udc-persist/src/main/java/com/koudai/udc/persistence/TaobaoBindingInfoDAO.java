package com.koudai.udc.persistence;

import com.koudai.udc.domain.TaobaoBindingInfo;

public interface TaobaoBindingInfoDAO {

	void save(TaobaoBindingInfo taobaoBindingInfo);

	TaobaoBindingInfo getTaobaoBindingInfoByUserId(String userId);

}
