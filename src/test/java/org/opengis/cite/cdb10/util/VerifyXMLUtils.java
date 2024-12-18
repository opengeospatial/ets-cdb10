package org.opengis.cite.cdb10.util;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Verifies the behavior of the XMLUtils class.
 */
public class VerifyXMLUtils {

	public VerifyXMLUtils() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.newDocumentBuilder();
	}

	@Test
	public void expandCharacterEntity() {
		String text = "Ce n&apos;est pas";
		String result = XMLUtils.expandReferencesInText(text);
		Assert.assertTrue("Expected result to contain an apostrophe (')", result.contains("'"));
	}

	@Test
	public void expandNumericCharacterReference() {
		String text = "Montr&#xe9;al";
		String result = XMLUtils.expandReferencesInText(text);
		Assert.assertEquals("Expected result to contain character é (U+00E9)", "Montréal", result);
	}

}
