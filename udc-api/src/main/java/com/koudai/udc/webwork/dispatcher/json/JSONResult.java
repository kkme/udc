/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.koudai.udc.webwork.dispatcher.json;

import java.io.OutputStream;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;

/**
 * <!-- START SNIPPET: description -->
 * 
 * Attempt to retrieve an instance of {@link JSONObject} from WebWork's
 * ValueStack through property returned from {@link #getJSONObjectProperty()}
 * and write the String representation of the retrived {@link JSONObject} to
 * {@link HttpServletResponse}'s outputstream. Normally having accessor methods
 * for the property in WebWork's action would do the trick.
 * 
 * <!-- END SNIPPET: description -->
 * 
 * 
 * <!-- START SNIPPET: params -->
 * 
 * <ul>
 * <li>contentType - Defines the content-type header to be used. Default to
 * 'application/json'</li>
 * <li>jsonObjectProperty - Defines the property used to look up WebWork's
 * ValueStack to find an instance of {@link JSONObject}. Default to
 * 'jsonObject'.
 * </ul>
 * 
 * <!-- END SNIPPET: params -->
 * 
 * 
 * <pre>
 * <!-- START SNIPPET: examples -->
 * 
 *  &lt;action name="getActiveCustomers" class="..."&gt;
 *      &lt;result name="success" type="json"&gt;
 *          &lt;param name="jsonObjectProperty"&gt;activeCustomer&lt;/param&gt;
 *          &lt;param name="contentType"&gt;application/json&lt;/param&gt;
 *      &lt;/result&gt;
 *      ...
 *  &lt;/action>&gt;
 * 
 *  Or just
 * 
 *  &lt;action name="getActiveCustomers" class="..."&gt;
 *      &lt;result name="success" type="json" /&gt;
 *      ...
 *  &lt;/action&gt;
 * 
 *  if we used the default property ('jsonObject') and default content-type ('application/json')
 * 
 * <!-- END SNIPPET: examples -->
 * </pre>
 * 
 * @author tmjee
 * @version $Date$ $Id$
 */
public class JSONResult implements Result {

	private static final long serialVersionUID = -7726111298481129505L;

	private static final Log LOG = LogFactory.getLog(JSONResult.class);

	private String jsonObjectProperty = "jsonObject";
	private String contentType = "application/json";

	private String encoding = "utf-8";

	/**
	 * Writes the string representation of {@link JSONObject} defined through
	 * {@link #getJSONObjectProperty()} to
	 * {@link javax.servlet.http.HttpServletResponse}'s outputstream.
	 * 
	 * @param invocation
	 * @throws Exception
	 */
	public void execute(ActionInvocation invocation) throws Exception {

		if (LOG.isDebugEnabled()) {
			LOG.debug("executing JSONResult");
		}

		JSONObject jsonObject = getJSONObject(invocation);
		if (jsonObject != null) {
			String json = jsonObject.toString();
			HttpServletResponse response = getServletResponse(invocation);
			response.setContentType(getContentType());
			response.setContentLength(json.getBytes().length);

			// byte[] bs = json.getBytes(getEncoding());
			// response.setContentLength(bs.length);

			OutputStream os = response.getOutputStream();
			// os.write(bs);
			os.write(json.getBytes());
			os.flush();

			if (LOG.isDebugEnabled()) {
				LOG.debug("written [" + json + "] to HttpServletResponse outputstream");
			}
		}
	}

	/**
	 * Attempt to look up a
	 * {@link com.opensymphony.webwork.dispatcher.json.JSONObject} instance
	 * through the property ({@link #getJSONObjectProperty()}) by looking up the
	 * property in WebWork's ValueStack. It shall be found if there's accessor
	 * method for the property in WebWork's action itself.
	 * <p/>
	 * Returns null if one cannot be found.
	 * <p/>
	 * We could override this method to return the desired JSONObject when
	 * writing testcases.
	 * 
	 * @param invocation
	 * @return {@link JSONObject} or null if one cannot be found
	 */
	protected JSONObject getJSONObject(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		Object obj = actionContext.getValueStack().findValue(jsonObjectProperty);

		if (obj == null) {
			LOG.error("property [" + jsonObjectProperty + "] returns null, expecting JSONObject", new WebWorkException());
			return null;
		}
		if (!JSONObject.class.isInstance(obj)) {
			LOG.debug("build json object by reflection.");
			JSONObject jsonObj = new JSONObject();
			for (Field field : obj.getClass().getDeclaredFields()) {
				String getter = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
				jsonObj.append(field.getName(), obj.getClass().getDeclaredMethod(getter).invoke(obj));
			}
			return jsonObj;
		}
		return (JSONObject) obj;
	}

	/**
	 * Returns a {@link javax.servlet.http.HttpServletResponse} by looking it up
	 * through WebWork's ActionContext Map. </p> We could override this method
	 * to return the desired Mock HttpServletResponse when writing testcases.
	 * 
	 * @param invocation
	 * @return {@link javax.servlet.http.HttpServletResponse}
	 */
	protected HttpServletResponse getServletResponse(ActionInvocation invocation) {
		return (HttpServletResponse) invocation.getInvocationContext().getContextMap().get(ServletActionContext.HTTP_RESPONSE);
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getJsonObjectProperty() {
		return jsonObjectProperty;
	}

	public void setJsonObjectProperty(String jsonObjectProperty) {
		this.jsonObjectProperty = jsonObjectProperty;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
