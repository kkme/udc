package com.koudai.udc.statis.dao;

import com.koudai.udc.statis.dao.TypeInfoDAO;
import com.koudai.udc.statis.domain.TypeInfo;
import com.koudai.udc.statis.factory.TypeInfoFactory;

public class TypeInfoDAOTest extends BaseDAOTestCase {

	private TypeInfoDAO typeInfoDAO;

	private TypeInfoFactory typeInfoFactory;

	public void testSaveSuccessfully() throws Exception {
		TypeInfo typeInfo = typeInfoFactory.newInstance("123", "456");
		typeInfoDAO.save(typeInfo);
		assertNotNull(typeInfo.getId());
		assertEquals(new Long(1), typeInfo.getId());
	}

	public void setTypeInfoDAO(TypeInfoDAO typeInfoDAO) {
		this.typeInfoDAO = typeInfoDAO;
	}

	public void setTypeInfoFactory(TypeInfoFactory typeInfoFactory) {
		this.typeInfoFactory = typeInfoFactory;
	}

}
