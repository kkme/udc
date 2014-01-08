package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class DescendingComparator implements Serializable, Comparator<Map.Entry<String, Integer>> {

	private static final long serialVersionUID = -193591089685966106L;

	@Override
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		return (o2.getValue() - o1.getValue());
	}

}
