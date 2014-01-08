package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.IosPush;

public interface IosPushFactory {

	IosPush newInstance(String uuid, String token);

}
