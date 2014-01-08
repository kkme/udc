package com.koudai.udc.persistence;

import com.koudai.udc.domain.IosPush;

public interface IosPushDAO {

	void save(IosPush iosPush);

	IosPush getIosPushByUUID(String uuid);

}
