package com.koudai.udc.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.apache.log4j.Logger;

public final class HttpUtil {

	private static final Logger LOGGER = Logger.getLogger(HttpUtil.class);

	private static final int CONNECTION_TIMEOUT = 30000;
	private static final int READ_TIMEOUT = 30000;

	public static String post(String url, Map<String, String> requestParameters) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {
			byte[] parametersUrlByte = initRequestParameters(requestParameters);
			URL requestUrl = new URL(url);
			connection = (HttpURLConnection) requestUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);
			connection.setDoOutput(true);
			connection.getOutputStream().write(parametersUrlByte, 0, parametersUrlByte.length);
			connection.getOutputStream().flush();
			connection.getOutputStream().close();
			is = connection.getInputStream();
			bis = new BufferedInputStream(is);
			ByteArrayBuffer bab = new ByteArrayBuffer(32);
			int current = 0;
			while ((current = bis.read()) != -1) {
				bab.append((byte) current);
			}
			return EncodingUtils.getString(bab.toByteArray(), HTTP.UTF_8);
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				LOGGER.error(e);
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static String post(String url, String requestParameters) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {
			byte[] parametersUrlByte = requestParameters.getBytes(HTTP.UTF_8);
			URL requestUrl = new URL(url);
			connection = (HttpURLConnection) requestUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);
			connection.setDoOutput(true);
			connection.getOutputStream().write(parametersUrlByte, 0, parametersUrlByte.length);
			connection.getOutputStream().flush();
			connection.getOutputStream().close();
			is = connection.getInputStream();
			bis = new BufferedInputStream(is);
			ByteArrayBuffer bab = new ByteArrayBuffer(32);
			int current = 0;
			while ((current = bis.read()) != -1) {
				bab.append((byte) current);
			}
			return EncodingUtils.getString(bab.toByteArray(), HTTP.UTF_8);
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				LOGGER.error(e);
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static byte[] initRequestParameters(Map<String, String> parameters) throws UnsupportedEncodingException {
		StringBuffer parametersUrl = new StringBuffer();
		for (Iterator<Entry<String, String>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Entry<String, String> element = (Entry<String, String>) iter.next();
			parametersUrl.append(element.getKey().toString());
			parametersUrl.append("=");
			parametersUrl.append(element.getValue());
			parametersUrl.append("&");
		}
		if (parametersUrl.length() > 0) {
			parametersUrl = parametersUrl.deleteCharAt(parametersUrl.length() - 1);
		}
		return parametersUrl.toString().getBytes(HTTP.UTF_8);
	}

	public static String requestAndGetResponse(String url) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {
			URL requestUrl = new URL(url);
			connection = (HttpURLConnection) requestUrl.openConnection();
			connection.setRequestProperty("Content-type", "application/json");
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);
			is = connection.getInputStream();
			bis = new BufferedInputStream(is);
			ByteArrayBuffer bab = new ByteArrayBuffer(32);
			int current = 0;
			while ((current = bis.read()) != -1) {
				bab.append((byte) current);
			}
			return EncodingUtils.getString(bab.toByteArray(), HTTP.UTF_8);
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				LOGGER.error(e);
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
