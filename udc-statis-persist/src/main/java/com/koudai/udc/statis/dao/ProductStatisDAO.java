package com.koudai.udc.statis.dao;

import java.util.List;

import com.koudai.udc.statis.domain.ProductStatis;

public interface ProductStatisDAO {

	void save(ProductStatis productStatis);

	void batchSave(List<ProductStatis> productStatisList);

	ProductStatis getProductStatisByProductId(String productId);

	void update(ProductStatis productStatis);

	void batchUpdate(List<ProductStatis> productStatisList);

}
