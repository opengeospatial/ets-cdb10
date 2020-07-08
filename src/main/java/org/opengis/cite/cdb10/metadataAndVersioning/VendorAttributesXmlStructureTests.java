package org.opengis.cite.cdb10.metadataAndVersioning;

import java.io.IOException;
import java.nio.file.Files;

import org.opengis.cite.cdb10.util.metadataXml.VendorAttributesXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * Created by martin on 2016-09-09.
 */
public class VendorAttributesXmlStructureTests extends Capability2Tests {
	
	private VendorAttributesXml vendorAttributes;

	private void loadXmlFile() {
		this.vendorAttributes = new VendorAttributesXml(path);
	}
	
	private Boolean xmlFileExists() {
		return Files.exists(this.vendorAttributes.getXmlFilePath());
	}

	/**
	 * Verify that the schema file exists for a Vendor Attributes XML file.
	 * If no Vendor Attributes XML file exists, then this test is skipped.
	 */
    @Test(description = "OGC 15-113r5, Section 3.1.1")
    public void verifyVendorAttributesXsdFileExists() {
        this.loadXmlFile();
        if (!this.xmlFileExists()) { return; }
        
		Assert.assertTrue(vendorAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", vendorAttributes.getXsdFileName()));
    }

    /** If the Vendor Attributes XML and Schema files exist, then verify the
     * XML against the schema.
     * 
     * @throws IOException Error reading XML or Schema file
     * @throws SAXException Error parsing XML or Schema file
     */
    @Test(description = "OGC 15-113r5, A.1.19, Test 76")
    public void verifyVendorAttributesXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        if (!this.xmlFileExists()) { return; }
        
        Assert.assertTrue(vendorAttributes.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", vendorAttributes.getXmlFileName()));
        Assert.assertTrue(vendorAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", vendorAttributes.getXsdFileName()));
        
        String errors = vendorAttributes.schemaValidationErrors();
        Assert.assertEquals(errors, "", vendorAttributes.getXmlFileName() + 
        		" does not validate against its XML Schema file. Errors: " + errors);
    }
}
