package com.koudai.udc.service;

import java.util.Map;

public interface GetTopProductService {

	Map<String, Map<String, Integer>> getTopProduct(int limit, String filePath);

}
