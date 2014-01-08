package com.koudai.udc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.support.TransactionTemplate;

import com.koudai.udc.domain.Editor;
import com.koudai.udc.exception.TransactionCallbackException;
import com.koudai.udc.service.EditorService;
import com.koudai.udc.statis.dao.ProductRecommendDAO;
import com.koudai.udc.statis.dao.ProductStatisDAO;
import com.koudai.udc.statis.dao.ProductTypeDAO;
import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.domain.ProductType;
import com.koudai.udc.statis.factory.ProductRecommendFactory;
import com.koudai.udc.statis.factory.ProductStatisFactory;
import com.koudai.udc.statis.factory.ProductTypeFactory;
import com.koudai.udc.utils.FileUtil;
import com.koudai.udc.utils.S;

public class EditorServiceimpl implements EditorService {

	private static final Logger LOGGER = Logger.getLogger(EditorServiceimpl.class);

	private FilePathConfiguration filePathConfiguration;

	private ProductRecommendDAO productRecommendDAO;

	private ProductTypeDAO productTypeDAO;

	private ProductStatisDAO productStatisDAO;

	private ProductRecommendFactory productRecommendFactory;

	private ProductTypeFactory productTypeFactory;

	private ProductStatisFactory productStatisFactory;

	private TransactionTemplate transactionTemplate;

	@Override
	public void dealWithEditorData(String day) throws TransactionCallbackException {
		List<Editor> editors = readEditorFile(day);
		if (editors == null || editors.size() == 0) {
			throw new TransactionCallbackException("Editor file is empty");
		}
		List<Editor> saveEditors = new ArrayList<Editor>();
		List<Editor> updateEditors = new ArrayList<Editor>();
		for (Editor editor : editors) {
			ProductRecommend productRecommend = productRecommendDAO.getProductRecommendByProductId(editor.getProductRecommend().getProductId());
			if (productRecommend == null) {
				saveEditors.add(editor);
			} else {
				updateEditors.add(editor);
			}
		}
		List<ProductRecommend> saveRecommends = new ArrayList<ProductRecommend>();
		List<ProductType> saveTypes = new ArrayList<ProductType>();
		for (Editor saveEditor : saveEditors) {
			saveRecommends.add(saveEditor.getProductRecommend());
			saveTypes.addAll(saveEditor.getProductTypes());
		}
		List<ProductRecommend> updateRecommends = new ArrayList<ProductRecommend>();
		List<ProductType> updateTypes = new ArrayList<ProductType>();
		for (Editor updateEditor : updateEditors) {
			updateRecommends.add(updateEditor.getProductRecommend());
			updateTypes.addAll(updateEditor.getProductTypes());
		}
		dealWithRecommends(saveRecommends, updateRecommends);
		dealWithTypes(saveTypes, updateTypes);
		dealWithProductStatisList(saveRecommends);
	}

	private List<Editor> readEditorFile(String day) {
		StringBuffer path = new StringBuffer(filePathConfiguration.getShellResultDir());
		path.append(S.EDITOR_PREFIX).append(day).append(S.TXT_FILE_SUFFIX);
		List<String> multipleLine = FileUtil.readMultipleLine(path.toString());
		List<Editor> editors = new ArrayList<Editor>();
		for (String singleLine : multipleLine) {
			String[] split = singleLine.split(S.COMMA_STR);
			if (split.length < 4) {
				LOGGER.error("Error editor data and source is < " + singleLine + " >");
				continue;
			}
			List<ProductType> productTypes = new ArrayList<ProductType>();
			final String productId = split[0];
			final String userId = split[1];
			Date pushTime = new Timestamp(Long.parseLong(split[2]));
			ProductRecommend productRecommend = productRecommendFactory.newInstance(userId, productId, pushTime);
			for (int i = 3; i < split.length; i++) {
				final String typeId = split[i];
				productTypes.add(productTypeFactory.newInstance(productId, typeId));
			}
			editors.add(new Editor(productRecommend, productTypes));
		}
		return editors;
	}

	private void dealWithRecommends(List<ProductRecommend> saveRecommends, List<ProductRecommend> updateRecommends) throws TransactionCallbackException {
		if (transactionTemplate.execute(new RecommendTransactionCallback(this, saveRecommends, updateRecommends))) {
			throw new TransactionCallbackException("Deal with product recommends failed");
		}
	}

	private void dealWithTypes(List<ProductType> saveTypes, List<ProductType> updateTypes) throws TransactionCallbackException {
		if (transactionTemplate.execute(new TypeTransactionCallback(this, saveTypes, updateTypes))) {
			throw new TransactionCallbackException("Deal with product types failed");
		}
	}

	private void dealWithProductStatisList(List<ProductRecommend> saveRecommends) throws TransactionCallbackException {
		List<String> productIds = new ArrayList<String>();
		for (ProductRecommend saveRecommend : saveRecommends) {
			productIds.add(saveRecommend.getProductId());
		}
		if (transactionTemplate.execute(new SaveProductStatisTransactionCallback(this, productIds))) {
			throw new TransactionCallbackException("Save product statis failed");
		}
	}

	public void setFilePathConfiguration(FilePathConfiguration filePathConfiguration) {
		this.filePathConfiguration = filePathConfiguration;
	}

	public ProductRecommendDAO getProductRecommendDAO() {
		return productRecommendDAO;
	}

	public void setProductRecommendDAO(ProductRecommendDAO productRecommendDAO) {
		this.productRecommendDAO = productRecommendDAO;
	}

	public ProductTypeDAO getProductTypeDAO() {
		return productTypeDAO;
	}

	public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}

	public ProductStatisDAO getProductStatisDAO() {
		return productStatisDAO;
	}

	public void setProductStatisDAO(ProductStatisDAO productStatisDAO) {
		this.productStatisDAO = productStatisDAO;
	}

	public void setProductRecommendFactory(ProductRecommendFactory productRecommendFactory) {
		this.productRecommendFactory = productRecommendFactory;
	}

	public void setProductTypeFactory(ProductTypeFactory productTypeFactory) {
		this.productTypeFactory = productTypeFactory;
	}

	public ProductStatisFactory getProductStatisFactory() {
		return productStatisFactory;
	}

	public void setProductStatisFactory(ProductStatisFactory productStatisFactory) {
		this.productStatisFactory = productStatisFactory;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

}
