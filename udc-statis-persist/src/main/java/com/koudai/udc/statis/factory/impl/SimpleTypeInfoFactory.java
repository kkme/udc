package com.koudai.udc.statis.factory.impl;

import com.koudai.udc.statis.domain.TypeInfo;
import com.koudai.udc.statis.factory.TypeInfoFactory;

public class SimpleTypeInfoFactory implements TypeInfoFactory {

	@Override
	public TypeInfo newInstance(String typeId, String typeName) {
		return new TypeInfo(typeId, typeName);
	}

}
