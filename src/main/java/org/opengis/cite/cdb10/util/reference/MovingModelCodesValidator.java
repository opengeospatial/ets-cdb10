package org.opengis.cite.cdb10.util.reference;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Provides methods to validate Moving Model Codes.
 */
public class MovingModelCodesValidator {

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
	
	public boolean isValidCategoryCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind/Domain/Category[@code = \"" +
					code.toString() + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	public boolean isValidDomainCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind/Domain[@code = \"" +
				code.toString() + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return false;
		}
		
		return (matches.getLength() > 0);
	}

	public boolean isValidKindCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind[@code = \"" + code.toString() +
				"\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	public boolean isValidCategoryName(String name) {
		String query = "/Moving_Model_Codes/Kind/Domain/Category[@name = \"" +
				name + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	public boolean isValidDomainName(String name) {
		String query = "/Moving_Model_Codes/Kind/Domain[@name = \"" + name + "\"]";
		NodeList matches;
		try {			
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	public boolean isValidKindName(String name) {
		String query = "/Moving_Model_Codes/Kind[@name = \"" + name + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		} catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	public String categoryNameForCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind/Domain/Category[@code = \"" +
				code.toString() + "\"]";
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

	public String domainNameForCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind/Domain[@code = \"" +
				code.toString() + "\"]";
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

	public String kindNameForCode(Integer code) {
		String query = "/Moving_Model_Codes/Kind[@code = \"" + code.toString() +
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
	
	/**
	 * Wrap the compilation and evaluation of an XPath in a function.
	 * @param expression The XPath expression
	 * @param doc A Document or Node
	 * @return List of Nodes
	 * @throws XPathExpressionException Error when evaluating XPath Expression
	 */
	private NodeList compileAndEvaluate(String expression, Object doc) throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression datasetExp = xPath.compile(expression);
		return (NodeList) datasetExp.evaluate(doc, XPathConstants.NODESET);
	}
}
