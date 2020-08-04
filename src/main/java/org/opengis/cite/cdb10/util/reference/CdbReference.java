package org.opengis.cite.cdb10.util.reference;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Open the embedded CDB reference files so they may be used in validation 
 * tests. This class can be used to generate validator classes, which should be
 * used in your test methods.
 *
 */
public class CdbReference {
	public CdbReference() {
	}

	/**
	 * Create a Validator for "Component Selectors" from the reference files.
	 * @return A Validator for Component Selectors
	 */
	public ComponentSelectorValidator buildComponentSelectorValidator() {
		InputStream in = getClass().getResourceAsStream("/Reference/Component_Selectors.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		Document doc = null;
		try {
			doc = builder.parse(in);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return new ComponentSelectorValidator(doc);
	}
}
