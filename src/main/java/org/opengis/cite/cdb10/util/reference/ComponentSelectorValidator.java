package org.opengis.cite.cdb10.util.reference;

import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Provides methods to validate if a Component Selector is used properly.
 */
public class ComponentSelectorValidator extends Validator {

	/**
	 * Document from Components_Selectors.xml
	 */
	private Document doc;

	/**
	 * XPath query used for filtering Datasets
	 */
	protected final String DATASETS_QUERY = "/DatasetSelectors/DatasetSelector/Datasets/Dataset[text()='%s']/../..";

	/**
	 * Initialize this validator with the contents of an XML Document.
	 * @param doc XML Document from Component_Selectors.xml
	 */
	public ComponentSelectorValidator(Document doc) {
		this.doc = doc;
	}

	/**
	 * Use Component Selector reference to check that a Component Selector level 1 is
	 * allowed for a Dataset.
	 * @param cs1 String of the level one Component Selector
	 * @param dataset String of the Dataset ID
	 * @return true/false
	 */
	public boolean isValidComponentSelector1ForDataset(String cs1, String dataset) {
		String datasetPath = String.format(DATASETS_QUERY, dataset);
		String csAttrPath = String.format(
				"ComponentSelector[@kind='%s'] | ComponentSelector[@minimum <= '%s' and @maximum >= '%s']", cs1, cs1,
				cs1);

		NodeList selector = null;
		try {
			selector = compileAndEvaluate(datasetPath, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		// Check if Dataset is not located in reference
		if (selector.getLength() == 0) {
			return false;
		}

		NodeList csNodes = null;
		try {
			csNodes = compileAndEvaluate(csAttrPath, selector.item(0));
		}
		catch (XPathExpressionException e) {
			return false;
		}

		if (csNodes.getLength() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Use Component Selector reference to check that a Component Selector level 2 is
	 * allowed for a CS1 and Dataset.
	 * @param cs2 String of the level two Component Selector
	 * @param cs1 String of the level one Component Selector
	 * @param dataset String of the Dataset ID
	 * @return true/false
	 */
	public boolean isValidComponentSelector2ForDataset(String cs2, String cs1, String dataset) {
		String datasetPath = String.format(DATASETS_QUERY, dataset);

		NodeList selector = null;
		try {
			selector = compileAndEvaluate(datasetPath, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		// Check if Dataset is not located in reference
		if (selector.getLength() == 0) {
			return false;
		}

		// Select all possible combinations of exact and range values for
		// component selectors.
		String cs2Path = String.format("ComponentSelector[@kind='%s']/ComponentSelector[@index='%s'] | "
				+ "ComponentSelector[@minimum <= '%s' and @maximum >= '%s']/ComponentSelector[@index='%s'] | "
				+ "ComponentSelector[@kind='%s']/ComponentSelector[@minimum <= '%s' and @maximum >= '%s'] | "
				+ "ComponentSelector[@minimum <= '%s' and @maximum >= '%s']/ComponentSelector[@minimum <= '%s' and @maximum >= '%s']",
				cs1, cs2, cs1, cs1, cs2, cs1, cs2, cs2, cs1, cs1, cs2, cs2);
		NodeList cs2Nodes = null;
		try {
			cs2Nodes = compileAndEvaluate(cs2Path, selector.item(0));
		}
		catch (XPathExpressionException e) {
			return false;
		}

		if (cs2Nodes.getLength() > 0) {
			return true;
		}

		return false;
	}

}
