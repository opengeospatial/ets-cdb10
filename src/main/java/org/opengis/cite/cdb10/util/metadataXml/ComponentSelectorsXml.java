package org.opengis.cite.cdb10.util.metadataXml;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.xpath.XPathExpressionException;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComponentSelectorsXml {
	protected File xmlFile;
	
	public ComponentSelectorsXml(String xmlPath) {
		this.xmlFile = Paths.get(xmlPath).toFile();
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
		NodeList selector = XMLUtils.getNodeList(datasetPath, this.xmlFile.toPath());
		
		// Check if Dataset is not located in reference
		if (selector.getLength() == 0) {
			return false;
		}
		
		NodeList csNodes = null;
		try {
			csNodes = XMLUtils.evaluateXPath(selector.item(0), csAttrPath, null);
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
		NodeList selector = XMLUtils.getNodeList(datasetPath, this.xmlFile.toPath());
		
		// Check if Dataset is not located in reference
		if (selector.getLength() == 0) {
			return false;
		}
		
		String cs2Path = String.format("ComponentSelector[@kind='%s']/ComponentSelector[@index='%s']", cs1, cs2);
		NodeList cs2Nodes = null;
		try {
			cs2Nodes = XMLUtils.evaluateXPath(selector.item(0), cs2Path, null);
		} catch (XPathExpressionException e) {
			return false;
		}
		
		if (cs2Nodes.getLength() > 0) {
			return true;
		}

		return false;
	}

}
