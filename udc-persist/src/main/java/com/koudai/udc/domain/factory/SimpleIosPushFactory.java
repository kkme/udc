package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.IosPush;

public class SimpleIosPushFactory implements IosPushFactory {

	@Override
	public IosPush newInstance(String uuid, String token) {
		return new IosPush(uuid, token);
	}

}
