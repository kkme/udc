package com.koudai.udc.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class XmlParseService {
	private static final Logger LOGGER = Logger.getLogger(XmlParseService.class);

	@SuppressWarnings("unchecked")
	public static String getValueByElementName(String xmlSource, String elementName) {
		String value = null;
		StringReader read = new StringReader(xmlSource);
		InputSource source = new InputSource(read);
		SAXBuilder sb = new SAXBuilder();
		try {
			Document doc = sb.build(source);
			Element root = doc.getRootElement();
			List<Element> childElements = root.getChildren();
			for (int i = 0; i < childElements.size(); i++) {
				Element et = (Element) childElements.get(i);
				if (elementName.equals(et.getName())) {
					return et.getValue();
				}
			}
		} catch (IOException e) {
			LOGGER.error("IOException :" + e.getMessage() + ",xmlsource :" + xmlSource);
		} catch (JDOMException e) {
			LOGGER.error("JDOMException " + e.getMessage() + ",xmlsource :" + xmlSource);
		}
		return value;
	}

}
