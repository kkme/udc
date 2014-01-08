package com.koudai.udc.persistence;

import java.util.Date;
import java.util.List;

import com.koudai.udc.domain.IosPushInfo;
import com.koudai.udc.domain.Platform;

public interface IosPushInfoDAO {

	void save(IosPushInfo iosPushInfo);

	List<IosPushInfo> getPushInfosByCreateTimeAndStartAndEndPos(Date createTime, int start, int end);

	List<IosPushInfo> getPushInfosByCreateTimeAndStartAndEndPosAndPlatform(Date createTime, int start, int end, Platform platform);

}
