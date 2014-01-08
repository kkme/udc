package com.koudai.udc.statis.dao;

import java.util.List;

import com.koudai.udc.statis.domain.ProductType;

public interface ProductTypeDAO {

	void save(ProductType productType);

	void batchSave(List<ProductType> productTypes);

	void batchDelete(List<ProductType> productTypes);

	List<String> getTypeIdsByProductId(String productId);

}
