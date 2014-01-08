package com.koudai.udc.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.support.TransactionTemplate;

import com.koudai.udc.exception.TransactionCallbackException;
import com.koudai.udc.service.ProductStatisService;
import com.koudai.udc.statis.dao.ProductStatisDAO;
import com.koudai.udc.statis.dao.ProductStatisPeriodDAO;
import com.koudai.udc.statis.domain.ProductStatis;
import com.koudai.udc.statis.domain.ProductStatisPeriod;
import com.koudai.udc.statis.factory.ProductStatisFactory;
import com.koudai.udc.statis.factory.ProductStatisPeriodFactory;
import com.koudai.udc.utils.S;

public class ProductStatisServiceImpl implements ProductStatisService {

	private static final Logger LOGGER = Logger.getLogger(ProductStatisServiceImpl.class);

	private ProductStatisDAO productStatisDAO;

	private ProductStatisPeriodDAO productStatisPeriodDAO;

	private ProductStatisFactory productStatisFactory;

	private ProductStatisPeriodFactory productStatisPeriodFactory;

	private FilePathConfiguration filePathConfiguration;

	private TransactionTemplate transactionTemplate;

	@Override
	public void dealWithStatisDate(String day, List<String> productIds) throws TransactionCallbackException {
		Map<String, ProductStatisPeriod> productStatisPeriodMap = new HashMap<String, ProductStatisPeriod>();
		Map<String, ProductStatis> productStatisMap = new HashMap<String, ProductStatis>();
		for (String productId : productIds) {
			productStatisPeriodMap.put(productId, productStatisPeriodFactory.newInstance(productId, day));
			productStatisMap.put(productId, productStatisFactory.newInstance(productId));
		}
		initPeriodAndAllClickedNum(day, productIds, productStatisPeriodMap, productStatisMap);
		initPeriodAndAllPurchasedNum(day, productIds, productStatisPeriodMap, productStatisMap);
		initPeriodAndAllCollectedNum(day, productIds, productStatisPeriodMap, productStatisMap);
		batchSavePeriodData(productStatisPeriodMap);
		batchUpdatePeriodData(productStatisMap);
	}

	private void initPeriodAndAllClickedNum(String day, List<String> productIds, Map<String, ProductStatisPeriod> productStatisPeriodMap, Map<String, ProductStatis> productStatisMap) {
		StringBuffer path = new StringBuffer(filePathConfiguration.getShellResultDir());
		path.append(S.CLICK_PREFIX).append(day).append(S.TXT_FILE_SUFFIX);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path.toString());
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(S.COMMA_STR);
				final String productId = split[0].trim();
				int clickedNum = 0;
				try {
					clickedNum = Integer.valueOf(split[1].trim());
				} catch (Exception e) {
					LOGGER.error("Product id < " + productId + " > has invalid click number");
					continue;
				}
				if (productIds.contains(productId)) {
					ProductStatisPeriod productStatisPeriod = productStatisPeriodMap.get(productId);
					productStatisPeriod.setClickedNum(clickedNum);
					ProductStatis productStatis = productStatisMap.get(productId);
					productStatis.setClickedNum(clickedNum);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Read file failed", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e1) {
				LOGGER.error("Close file reader failed", e1);
			}
		}

	}

	private void initPeriodAndAllPurchasedNum(String day, List<String> productIds, Map<String, ProductStatisPeriod> productStatisPeriodMap, Map<String, ProductStatis> productStatisMap) {
		StringBuffer path = new StringBuffer(filePathConfiguration.getShellResultDir());
		path.append(S.BUY_PREFIX).append(day).append(S.TXT_FILE_SUFFIX);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path.toString());
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(S.COMMA_STR);
				final String productId = split[0].trim();
				int purchasedNum = 0;
				try {
					purchasedNum = Integer.valueOf(split[1].trim());
				} catch (Exception e) {
					LOGGER.error("Product id < " + productId + " > has invalid purchase number");
					continue;
				}
				if (productIds.contains(productId)) {
					ProductStatisPeriod productStatisPeriod = productStatisPeriodMap.get(productId);
					productStatisPeriod.setPurchasedNum(purchasedNum);
					ProductStatis productStatis = productStatisMap.get(productId);
					productStatis.setPurchasedNum(purchasedNum);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Read file failed", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e1) {
				LOGGER.error("Close file reader failed", e1);
			}
		}
	}

	private void initPeriodAndAllCollectedNum(String day, List<String> productIds, Map<String, ProductStatisPeriod> productStatisPeriodMap, Map<String, ProductStatis> productStatisMap) {
		StringBuffer path = new StringBuffer(filePathConfiguration.getShellResultDir());
		path.append(S.COLLECT_PREFIX).append(day).append(S.TXT_FILE_SUFFIX);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path.toString());
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(S.COMMA_STR);
				final String productId = split[0].trim();
				int collectedNum = 0;
				try {
					collectedNum = Integer.valueOf(split[1].trim());
				} catch (Exception e) {
					LOGGER.error("Product id < " + productId + " > has invalid collect number");
					continue;
				}
				if (productIds.contains(productId)) {
					ProductStatisPeriod productStatisPeriod = productStatisPeriodMap.get(productId);
					productStatisPeriod.setCollectedNum(collectedNum);
					ProductStatis productStatis = productStatisMap.get(productId);
					productStatis.setCollectedNum(collectedNum);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Read file failed", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e1) {
				LOGGER.error("Close file reader failed", e1);
			}
		}
	}

	private void batchSavePeriodData(Map<String, ProductStatisPeriod> productStatisPeriodMap) throws TransactionCallbackException {
		if (transactionTemplate.execute(new ProductStatisPeriodTransactionCallback(this, productStatisPeriodMap))) {
			throw new TransactionCallbackException("Deal with product statis periods failed");
		}
	}

	private void batchUpdatePeriodData(Map<String, ProductStatis> productStatisMap) throws TransactionCallbackException {
		if (transactionTemplate.execute(new UpdateProductStatisTransactionCallback(this, productStatisMap))) {
			throw new TransactionCallbackException("Deal with product statis list failed");
		}
	}

	public ProductStatisDAO getProductStatisDAO() {
		return productStatisDAO;
	}

	public void setProductStatisDAO(ProductStatisDAO productStatisDAO) {
		this.productStatisDAO = productStatisDAO;
	}

	public ProductStatisPeriodDAO getProductStatisPeriodDAO() {
		return productStatisPeriodDAO;
	}

	public void setProductStatisPeriodDAO(ProductStatisPeriodDAO productStatisPeriodDAO) {
		this.productStatisPeriodDAO = productStatisPeriodDAO;
	}

	public void setProductStatisFactory(ProductStatisFactory productStatisFactory) {
		this.productStatisFactory = productStatisFactory;
	}

	public void setProductStatisPeriodFactory(ProductStatisPeriodFactory productStatisPeriodFactory) {
		this.productStatisPeriodFactory = productStatisPeriodFactory;
	}

	public void setFilePathConfiguration(FilePathConfiguration filePathConfiguration) {
		this.filePathConfiguration = filePathConfiguration;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

}
