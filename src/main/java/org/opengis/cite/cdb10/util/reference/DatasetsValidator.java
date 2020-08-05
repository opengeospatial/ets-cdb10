package org.opengis.cite.cdb10.util.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Provides methods to validate if a Dataset follows the standard.
 */
public class DatasetsValidator extends Validator {
	/**
	 * Document from Components_Selectors.xml
	 */
	private Document doc;

	/**
	 * Initialize this validator with the contents of an XML Document.
	 * @param doc XML Document from Datasets.xml
	 */
	public DatasetsValidator(Document doc) {
		this.doc = doc;
	}

	/**
	 * Dataset codes between 900 - 999 (inclusive) will always be valid as
	 * extended datasets.
	 * @param code Integer for Dataset code (no leading zeros)
	 * @return true/false
	 */
	public boolean isExtendedCode(Integer code) {
		return code >= 900 && code <= 999;
	}

	/**
	 * Check if an integer code is valid according to the embedded Datasets.xml.
	 * Dataset codes between 900 - 999 (inclusive) will always be valid as
	 * extended datasets.
	 * @param code Integer for Dataset code (no leading zeros)
	 * @return true/false
	 */
	public boolean isValidCode(Integer code) {
		if (isExtendedCode(code)) {
			return true;
		}
		
		NodeList nodeList;
		try {
			nodeList = compileAndEvaluate("//Dataset", doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		ArrayList<Integer> codes = new ArrayList<Integer>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			Integer intCode = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());
			codes.add(intCode);
		}

		return codes.contains(code);
	}

	/**
	 * Check if a dataset name is valid according to the embedded Datasets.xml
	 * @param datasetName String name of the dataset
	 * @return true/false
	 */
	public boolean isValidName(String datasetName) {
		NodeList nodeList;
		try {
			nodeList = compileAndEvaluate("//Dataset", doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			names.add(currentItem.getAttributes().getNamedItem("name").getNodeValue());
		}

		return names.contains(datasetName);
	}

	/**
	 * Lookup a String name dataset for an Integer code according to the 
	 * embedded Datasets.xml
	 * @param code Integer for Dataset code (no leading zeros)
	 * @return String name for Dataset
	 */
	public String datasetNameForCode(Integer code) {
		NodeList nodeList;
		try {
			nodeList = compileAndEvaluate("//Dataset", doc);
		} catch (XPathExpressionException e) {
			return null;
		}

		Map<Integer, String> codeMap = new HashMap<Integer, String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			Integer intCode = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());
			String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
			codeMap.put(intCode, name);
		}
		return codeMap.get(code);
	}
}
