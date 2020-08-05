package org.opengis.cite.cdb10.util.reference;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DisCountryCodesValidator extends Validator {

	/**
	 * Document from DIS_Country_codes.xml
	 */
	private Document doc;

	/**
	 * Initialize this validator with the contents of an XML Document.
	 * @param doc XML Document from DIS_Country_codes.xml
	 */
	public DisCountryCodesValidator(Document doc) {
		this.doc = doc;
	}
	
	public boolean isValidCountryCode(Integer code) {
		String query = "/DIS_Country_Codes/Country[@code = \"" + code.toString() +
				"\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	public boolean isValidCountryName(String name) {
		String query = "/DIS_Country_Codes/Country[@name = \"" + name + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	public String countryNameForCode(Integer code) {
		String query = "/DIS_Country_Codes/Country[@code = \"" + code.toString() +
				"\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return null;
		}
		
		Node currentItem = matches.item(0);
		String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
		return name;
	}
}
