package com.koudai.udc.service.impl;

import com.koudai.udc.utils.Properties;

public class FilePathConfiguration {

	private static final String REPORT_DIR = "report_dir";

	private static final String SHELL_RESULT_DIR = "shell_result_dir";

	private static final String CODE_RESULT_DIR = "code_result_dir";

	private static final String SOURCE_DIR = "data_source_dir";

	private static final String UPLOAD_TOP_URL = "upload_top_url";

	private static final String UPLOAD_TOP30_URL = "upload_top30_url";

	private static final String QUERY_TYPE_URL = "query_type_url";

	private static final String APP_TOP_CHECK_URL = "app_top_check_url";

	private final Properties properties;

	public FilePathConfiguration(Properties properties) {
		this.properties = properties;
	}

	public String getReportDir() {
		return properties.get(REPORT_DIR);
	}

	public String getShellResultDir() {
		return properties.get(SHELL_RESULT_DIR);
	}

	public String getCodeResultDir() {
		return properties.get(CODE_RESULT_DIR);
	}

	public String getSourceDir() {
		return properties.get(SOURCE_DIR);
	}

	public String getUploadTopUrl() {
		return properties.get(UPLOAD_TOP_URL);
	}

	public String getUploadTop30Url() {
		return properties.get(UPLOAD_TOP30_URL);
	}

	public String getQueryTypeUrl() {
		return properties.get(QUERY_TYPE_URL);
	}

	public String getAppTopCheckUrl() {
		return properties.get(APP_TOP_CHECK_URL);
	}

}
