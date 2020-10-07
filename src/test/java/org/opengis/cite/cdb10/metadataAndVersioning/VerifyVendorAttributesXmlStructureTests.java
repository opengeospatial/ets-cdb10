package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.testng.SkipException;

public class VerifyVendorAttributesXmlStructureTests extends MetadataTestFixture<VendorAttributesXmlStructureTests> {
    
    public VerifyVendorAttributesXmlStructureTests() { testSuite = new VendorAttributesXmlStructureTests(); }
    
    @Test
    public void verifyVendorAttributesSchemaExists_Skips() {
    	// setup: No Vendor_Attributes.xml
    	expectedException.expect(SkipException.class);
        expectedException.expectMessage("The OGC CDB 1.0 standard is unclear about whether Vendor_Attributes.xsd and Geomatics_Attributes.xsd, or Vector_Attributes.xsd should be used. Therefore this test is skipped.");
        
    	// execute
    	testSuite.verifyVendorAttributesSchemaExists();
    }
}
