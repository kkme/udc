package com.koudai.udc.statis.factory;

import com.koudai.udc.statis.domain.TypeInfo;

public interface TypeInfoFactory {

	TypeInfo newInstance(String typeId, String typeName);

}
