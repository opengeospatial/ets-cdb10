package org.opengis.cite.cdb10.metadataAndVersioning;

import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * <p>
 * GeomaticsAttributesXmlStructureTests class.
 * </p>
 *
 */
public class GeomaticsAttributesXmlStructureTests extends Capability2Tests {

	private final static String SKIP_GEOMATICS_ATTRIBUTES = "The OGC CDB 1.0 standard is unclear about whether Vendor_Attributes.xsd and Geomatics_Attributes.xsd, or Vector_Attributes.xsd should be used. Therefore this test is skipped.";

	/**
	 * Verify that the schema file exists for a Geomatics Attributes XML file. If no
	 * Geomatics Attributes XML file exists, then this test is skipped.
	 *
	 * Due to ambiguities in OGC CDB Standard 1.0, this test is always skipped.
	 */
	@Test(description = "OGC 15-113r5, Section 3.1.1")
	public void verifyGeomaticsAttributesSchemaExists() {
		throw new SkipException(SKIP_GEOMATICS_ATTRIBUTES);
	}

	/**
	 * If the Geomatics Attributes XML and Schema files exist, then verify the XML against
	 * the schema.
	 *
	 * Due to ambiguities in OGC CDB Standard 1.0, this test is always skipped.
	 */
	@Test(description = "OGC 15-113r5, A.1.19, Test 77")
	public void verifyGeomaticsAttributesAgainstSchema() {
		throw new SkipException(SKIP_GEOMATICS_ATTRIBUTES);
	}

}
