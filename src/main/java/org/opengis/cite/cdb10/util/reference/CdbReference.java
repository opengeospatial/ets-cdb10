package org.opengis.cite.cdb10.util.reference;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Open the embedded CDB reference files so they may be used in validation tests. This
 * class can be used to generate validator classes, which should be used in your test
 * methods.
 */
public class CdbReference {

	/**
	 * <p>
	 * Constructor for CdbReference.
	 * </p>
	 */
	public CdbReference() {
	}

	/**
	 * Create a Validator for "Component Selectors" from the reference files.
	 * @return A Validator for Component Selectors
	 */
	public ComponentSelectorValidator buildComponentSelectorValidator() {
		Document doc = documentForResource("/Reference/Component_Selectors.xml");
		return new ComponentSelectorValidator(doc);
	}

	/**
	 * Create a Validator for "Datasets" from the reference files.
	 * @return A Validator for Datasets
	 */
	public DatasetsValidator buildDatasetsValidator() {
		Document doc = documentForResource("/Reference/Datasets.xml");
		return new DatasetsValidator(doc);
	}

	/**
	 * Create a Validator for "DIS Country Codes" from the reference files.
	 * @return A Validator for DIS Country Codes
	 */
	public DisCountryCodesValidator buildDisCountryCodesValidator() {
		Document doc = documentForResource("/Reference/DIS_Country_Codes.xml");
		return new DisCountryCodesValidator(doc);
	}

	/**
	 * Create a Validator for "Feature Data Dictionary" from the reference files.
	 * @return A Validator for Feature Data Dictionary
	 */
	public FeatureDataDictionaryValidator buildFeatureDataDictionaryValidator() {
		Document doc = documentForResource("/Reference/Feature_Data_Dictionary.xml");
		return new FeatureDataDictionaryValidator(doc);
	}

	/**
	 * Create a Validator for "Moving Model Codes" from the reference files.
	 * @return A Validator for Moving Model Codes
	 */
	public MovingModelCodesValidator buildMovingModelCodesValidator() {
		Document doc = documentForResource("/Reference/Moving_Model_Codes.xml");
		return new MovingModelCodesValidator(doc);
	}

	/**
	 * Get a Resource from a path, and load it into an XML Document.
	 * @param resourcePath Path to resource bundled by Maven
	 * @return XML Document
	 */
	private Document documentForResource(String resourcePath) {
		InputStream in = getClass().getResourceAsStream(resourcePath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		Document doc = null;
		try {
			doc = builder.parse(in);
		}
		catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

}
