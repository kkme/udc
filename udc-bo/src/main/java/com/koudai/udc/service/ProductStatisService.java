package com.koudai.udc.service;

import java.util.List;

import com.koudai.udc.exception.TransactionCallbackException;

public interface ProductStatisService {

	void dealWithStatisDate(String day, List<String> productIds) throws TransactionCallbackException;

}
