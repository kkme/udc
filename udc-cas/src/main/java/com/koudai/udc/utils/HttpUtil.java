package com.koudai.udc.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.apache.log4j.Logger;

import com.koudai.udc.exception.DomainException;

public final class HttpUtil {

	private static final Logger LOGGER = Logger.getLogger(HttpUtil.class);

	private static final int CONNECTION_TIMEOUT = 15000;
	private static final int READ_TIMEOUT = 15000;

	public static String get(String url, String contentType) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {
			URL requestUrl = new URL(url);
			connection = (HttpURLConnection) requestUrl.openConnection();
			if (contentType != null) {
				connection.setRequestProperty("Content-type", contentType);
			}
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);
			int code = connection.getResponseCode();
			if (S.HTTP_SUCCESS_CODE != code) {
				throw new DomainException("Requeset url < " + url + " > failed with status code < " + code + " >");
			}
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
			closeResource(connection, is, bis);
		}
	}

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
			int code = connection.getResponseCode();
			if (S.HTTP_SUCCESS_CODE != code) {
				throw new DomainException("Requeset url < " + url + " > failed with status code < " + code + " >");
			}
			is = connection.getInputStream();
			bis = new BufferedInputStream(is);
			ByteArrayBuffer bab = new ByteArrayBuffer(32);
			int current = 0;
			while ((current = bis.read()) != -1) {
				bab.append((byte) current);
			}
			return EncodingUtils.getString(bab.toByteArray(), HTTP.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
			return null;
		} finally {
			closeResource(connection, is, bis);
		}
	}

	private static void closeResource(HttpURLConnection connection, InputStream is, BufferedInputStream bis) {
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

	private static byte[] initRequestParameters(Map<String, String> parameters) throws UnsupportedEncodingException {
		StringBuffer parametersUrl = new StringBuffer();
		for (Iterator<Entry<String, String>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Entry<String, String> element = (Entry<String, String>) iter.next();
			parametersUrl.append(element.getKey().toString());
			parametersUrl.append("=");
			parametersUrl.append(URLEncoder.encode(element.getValue(), "utf-8"));
			parametersUrl.append("&");
		}
		if (parametersUrl.length() > 0) {
			parametersUrl = parametersUrl.deleteCharAt(parametersUrl.length() - 1);
		}
		return parametersUrl.toString().getBytes(HTTP.UTF_8);
	}

}
