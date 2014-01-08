package com.koudai.udc.statis.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.koudai.udc.statis.dao.TypeInfoDAO;
import com.koudai.udc.statis.domain.TypeInfo;

public class TypeInfoDAOImpl extends SqlMapClientDaoSupport implements TypeInfoDAO {

	@Override
	public void save(TypeInfo typeInfo) {
		getSqlMapClientTemplate().insert("TypeInfo.save", typeInfo);
	}

}
