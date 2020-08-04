package org.opengis.cite.cdb10.util.reference;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Provides methods to validate if a Component Selector is used properly.
 */
public class ComponentSelectorValidator {
	private Document doc;

	/**
	 * Initialize this validator with the contents of an XML Document.
	 * @param doc XML Document from Component_Selectors.xml
	 */
	public ComponentSelectorValidator(Document doc) {
		this.doc = doc;
	}

	/**
	 * Use Component Selector reference to check that a Component Selector level
	 * 1 is allowed for a Dataset.
	 * @param cs1 String of the level one Component Selector
	 * @param dataset String of the Dataset ID
	 * @return true/false
	 */
	public boolean isValidComponentSelector1ForDataset(String cs1, String dataset) {
		String datasetPath = String.format("/DatasetSelectors/DatasetSelector/Datasets/Dataset[text()='%s']/../..", dataset);
		String csAttrPath = String.format("ComponentSelector[@kind='%s'] | ComponentSelector[@minimum <= '%s' and @maximum >= '%s']", cs1, cs1, cs1);
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression datasetExp = null;
		NodeList selector = null;
		try {
			datasetExp = xPath.compile(datasetPath);
			selector = (NodeList) datasetExp.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return false;
		}
		
		
		// Check if Dataset is not located in reference
		if (selector.getLength() == 0) {
			return false;
		}
		
		XPathExpression csAttrExp = null;
		NodeList csNodes = null;
		try {
			csAttrExp = xPath.compile(csAttrPath);
			csNodes = (NodeList) csAttrExp.evaluate(selector.item(0), XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return false;
		}
		
		if (csNodes.getLength() > 0) {
			return true;
		}
	
		return false;
	}

	/**
	 * Use Component Selector reference to check that a Component Selector level
	 * 2 is allowed for a CS1 and Dataset.
	 * @param cs2 String of the level two Component Selector
	 * @param cs1 String of the level one Component Selector
	 * @param dataset String of the Dataset ID
	 * @return true/false
	 */
	public boolean isValidComponentSelector2ForDataset(String cs2, String cs1, String dataset) {
		String datasetPath = String.format("/DatasetSelectors/DatasetSelector/Datasets/Dataset[text()='%s']/../..", dataset);
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression datasetExp = null;
		NodeList selector = null;
		try {
			datasetExp = xPath.compile(datasetPath);
			selector = (NodeList) datasetExp.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return false;
		}
		
		// Check if Dataset is not located in reference
		if (selector.getLength() == 0) {
			return false;
		}
		
		// Select all possible combinations of exact and range values for
		// component selectors.
		String cs2Path = String.format(
				"ComponentSelector[@kind='%s']/ComponentSelector[@index='%s'] | " +
				"ComponentSelector[@minimum <= '%s' and @maximum >= '%s']/ComponentSelector[@index='%s'] | " +
				"ComponentSelector[@kind='%s']/ComponentSelector[@minimum <= '%s' and @maximum >= '%s'] | " +
				"ComponentSelector[@minimum <= '%s' and @maximum >= '%s']/ComponentSelector[@minimum <= '%s' and @maximum >= '%s']",
				cs1, cs2, cs1, cs1, cs2, cs1, cs2, cs2, cs1, cs1, cs2, cs2
				);
		XPathExpression cs2Exp = null;
		NodeList cs2Nodes = null;
		try {
			cs2Exp = xPath.compile(cs2Path);
			cs2Nodes = (NodeList) cs2Exp.evaluate(selector.item(0), XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return false;
		}
		
		if (cs2Nodes.getLength() > 0) {
			return true;
		}

		return false;
	}
}
