package org.opengis.cite.cdb10.util.reference;

import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Provides methods to validate Moving Model Codes.
 */
public class MovingModelCodesValidator extends Validator {

	/**
	 * Document from Moving_Model_Codes.xml
	 */
	private Document doc;

	/**
	 * Initialize this validator with the contents of an XML Document.
	 * @param doc XML Document from Moving_Model_Codes.xml
	 */
	public MovingModelCodesValidator(Document doc) {
		this.doc = doc;
	}

	/**
	 * <p>
	 * isValidCategoryCode.
	 * </p>
	 * @param code a {@link java.lang.Integer} object
	 * @return a boolean
	 */
	public boolean isValidCategoryCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind/Domain/Category[@code = \"" + code.toString() + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidDomainCode.
	 * </p>
	 * @param code a {@link java.lang.Integer} object
	 * @return a boolean
	 */
	public boolean isValidDomainCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind/Domain[@code = \"" + code.toString() + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidKindCode.
	 * </p>
	 * @param code a {@link java.lang.Integer} object
	 * @return a boolean
	 */
	public boolean isValidKindCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind[@code = \"" + code.toString() + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidCategoryName.
	 * </p>
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidCategoryName(String name) {
		String query = "/Moving_Model_Codes/Kind/Domain/Category[@name = \"" + name + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidDomainName.
	 * </p>
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidDomainName(String name) {
		String query = "/Moving_Model_Codes/Kind/Domain[@name = \"" + name + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidKindName.
	 * </p>
	 * @param name a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidKindName(String name) {
		String query = "/Moving_Model_Codes/Kind[@name = \"" + name + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * categoryNameForCode.
	 * </p>
	 * @param code a {@link java.lang.Integer} object
	 * @return a {@link java.lang.String} object
	 */
	public String categoryNameForCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind/Domain/Category[@code = \"" + code.toString() + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return null;
		}

		Node currentItem = matches.item(0);
		String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
		return name;
	}

	/**
	 * <p>
	 * domainNameForCode.
	 * </p>
	 * @param code a {@link java.lang.Integer} object
	 * @return a {@link java.lang.String} object
	 */
	public String domainNameForCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind/Domain[@code = \"" + code.toString() + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return null;
		}

		Node currentItem = matches.item(0);
		String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
		return name;
	}

	/**
	 * <p>
	 * kindNameForCode.
	 * </p>
	 * @param code a {@link java.lang.Integer} object
	 * @return a {@link java.lang.String} object
	 */
	public String kindNameForCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind[@code = \"" + code.toString() + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return null;
		}

		Node currentItem = matches.item(0);
		String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
		return name;
	}

}
