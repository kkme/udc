package com.koudai.udc.action;

import java.io.Serializable;
import java.util.Comparator;

public class AscendingComparator implements Serializable, Comparator<String> {

	private static final long serialVersionUID = 6029646278965618291L;

	@Override
	public int compare(String value1, String value2) {
		return (value1.compareTo(value2));
	}

}
