package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.testng.SkipException;

public class VerifyGeomaticsAttributesXmlStructureTests extends MetadataTestFixture<GeomaticsAttributesXmlStructureTests> {
	
    public VerifyGeomaticsAttributesXmlStructureTests() {
        testSuite = new GeomaticsAttributesXmlStructureTests();
    }
    
    @Test
    public void verifyGeomaticsAttributesSchemaExists_Skips() {
    	// setup: No Geomatics_Attributes.xml
    	expectedException.expect(SkipException.class);
        expectedException.expectMessage("The OGC CDB 1.0 standard is unclear about whether Vendor_Attributes.xsd and Geomatics_Attributes.xsd, or Vector_Attributes.xsd should be used. Therefore this test is skipped.");
        
    	// execute
    	testSuite.verifyGeomaticsAttributesSchemaExists();
    }
}
