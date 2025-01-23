package org.opengis.cite.cdb10.util.reference;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;

/**
 * Common functions for various validators.
 */
public class Validator {

	/**
	 * Wrap the compilation and evaluation of an XPath in a function.
	 * @param expression The XPath expression
	 * @param doc A Document or Node
	 * @return List of Nodes
	 * @throws javax.xml.xpath.XPathExpressionException Error when evaluating XPath
	 * Expression
	 */
	protected NodeList compileAndEvaluate(String expression, Object doc) throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression datasetExp = xPath.compile(expression);
		return (NodeList) datasetExp.evaluate(doc, XPathConstants.NODESET);
	}

}
