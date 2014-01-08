package com.koudai.udc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class S {

	public static final String EMPTY_STR = "";
	public static final String ZERO = "0";
	public static final String TWO = "2";
	public static final String NA = "N/A";

	public static final String TAOBAO_USER_NAME_RULE = "^(@taobao):.*";
	public static final String SINA_USER_NAME_RULE = "^(@sina):.*";
	public static final String QQ_USER_NAME_RULE = "^(@qq):.*";
	public static final String REAL_USER_NAME_RULE = "^(@taobao|@sina|@qq):.*";
	public static final String ANONYMOUS_USER_NAME_RULE = "^(@anonymous):.*";

	public static final String PRODUCT_CSV_PREFIX = "product_collect-";
	public static final String SHOP_CSV_PREFIX = "shop_favorite-";

	public static final String CSV_FILE_SUFFIX = ".csv";
	public static final String TXT_FILE_SUFFIX = ".txt";
	public static final String TIME_SUFFIX = "ms";

	public static final String COMMA_STR = ",";
	public static final char COMMA = ',';
	public static final String CHAR_SET = "utf-8";
	public static final String LF = System.getProperty("line.separator");

	public static final String TAOBAO_ITEM_URL_PREFIX = "http://item.taobao.com/item.htm?id=";

	public static final String TOTAL_PRODUCT_PREFIX = "total_product.";
	public static final String TOTAL_SHOP_PREFIX = "total_shop.";

	public static final String TOP_10_WITH_TYPE_PRODUCT_PREFIX = "top10_with_type_product.";
	public static final String TOP_1000_WITH_TYPE_PRODUCT_PREFIX = "top1000_with_type_product.";

	public static final String TOTAL_RESULT_PREFIX = "total.";
	public static final String TOP_RESULT_PREFIX = "top10.";
	public static final String API_RESULT_PREFIX = "api.";
	public static final String API_PEAK_RESULT_PREFIX = "api.2030~2300.";

	public static final String API_DATA_PREFIX = "cost.";
	public static final String API_PEAK_DATA_PREFIX = "cost.peak.";

	public static final String EDITOR_PREFIX = "editor_";
	public static final String CLICK_PREFIX = "click_";
	public static final String BUY_PREFIX = "buy_";
	public static final String COLLECT_PREFIX = "collect_";

	public static final String TOP_30_SORT_FILE_NAME = "sortProductCollectTop30.txt";
	public static final String TOP_10_SORT_FILE_NAME = "sortProductCollectTop10.txt";

	public static final int THIRTY = 30;
	public static final int TEN = 10;
	public static final int ONE_THOUSAND = 1000;
	public static final int ZERO_NUMBER = 0;

	public static final List<String> TOTAL_NUM_TAGS = new ArrayList<String>();
	static {
		TOTAL_NUM_TAGS.add("all");
		TOTAL_NUM_TAGS.add("real");
		TOTAL_NUM_TAGS.add("taobao");
		TOTAL_NUM_TAGS.add("sina");
		TOTAL_NUM_TAGS.add("qq");
		TOTAL_NUM_TAGS.add("anony");
	}

	public static boolean isTaobaoUser(String userId) {
		return Pattern.matches(TAOBAO_USER_NAME_RULE, userId);
	}

	public static boolean isSinaUser(String userId) {
		return Pattern.matches(SINA_USER_NAME_RULE, userId);
	}

	public static boolean isQQUser(String userId) {
		return Pattern.matches(QQ_USER_NAME_RULE, userId);
	}

	public static boolean isRealUser(String userId) {
		return Pattern.matches(REAL_USER_NAME_RULE, userId);
	}

	public static boolean isAnonymousUser(String userId) {
		return Pattern.matches(ANONYMOUS_USER_NAME_RULE, userId);
	}

}
