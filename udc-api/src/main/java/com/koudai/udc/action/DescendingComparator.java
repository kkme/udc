package com.koudai.udc.action;

import java.io.Serializable;
import java.util.Comparator;

import com.koudai.udc.domain.ProductCollectTop;

public class DescendingComparator implements Serializable, Comparator<ProductCollectTop> {

	private static final long serialVersionUID = 6640114648945166976L;

	@Override
	public int compare(ProductCollectTop before, ProductCollectTop after) {
		return (after.getCollectedNum() - before.getCollectedNum());
	}

}
