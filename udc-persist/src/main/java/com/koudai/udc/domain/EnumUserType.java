package com.koudai.udc.domain;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.hibernate.usertype.UserType;

public class EnumUserType<T extends Enum<?>> implements UserType {

	private static final Logger LOGGER = Logger.getLogger(EnumUserType.class);

	private static final int[] SQL_TYPES = new int[] { Types.VARCHAR };

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public boolean equals(Object x, Object y) {
		return x == y;
	}

	public Object deepCopy(Object value) {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	@SuppressWarnings("unchecked")
	public Object nullSafeGet(ResultSet resultSet, String[] names, Object arg2) throws SQLException {
		String name = resultSet.getString(names[0]);
		return T.valueOf(returnedClass(), name);
	}

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void nullSafeSet(PreparedStatement statement, Object value, int index) throws SQLException {
		if (value == null) {
			LOGGER.debug("binding to null");
			statement.setNull(index, Types.VARCHAR);
		} else {
			statement.setString(index, value.toString());
		}
	}

	public int hashCode(Object arg0) {
		return 0;
	}

	public Serializable disassemble(Object arg0) {
		return null;
	}

	public Object assemble(Serializable arg0, Object arg1) {
		return null;
	}

	public Object replace(Object arg0, Object arg1, Object arg2) {
		return null;
	}
}
