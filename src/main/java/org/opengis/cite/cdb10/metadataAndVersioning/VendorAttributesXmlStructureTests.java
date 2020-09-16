package org.opengis.cite.cdb10.metadataAndVersioning;

import java.io.IOException;

import org.opengis.cite.cdb10.util.metadataXml.VendorAttributesXml;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * Created by martin on 2016-09-09.
 */
public class VendorAttributesXmlStructureTests extends Capability2Tests {
	
	private final static String NO_XML_SKIP = "Will not check for Vendor Attributes Schema file as no Vendor Attributes XML file exists.";
	
	private VendorAttributesXml vendorAttributes;

	/**
	 * Initialize the VendorAttributesXml validation class.
	 */
	private void loadXmlFile() {
		this.vendorAttributes = new VendorAttributesXml(path);
	}
	
	private Boolean xmlFileExists() {
		return this.vendorAttributes.xmlFileExists();
	}

	/**
	 * Verify that the schema file exists for a Vendor Attributes XML file.
	 * If no Vendor Attributes XML file exists, then this test is skipped.
	 */
    @Test(description = "OGC 15-113r5, Section 3.1.1")
    public void verifyVendorAttributesXsdFileExists() {
        this.loadXmlFile();
        // If there is no "Vendor_Attributes.xml", then skip
        if (!this.xmlFileExists()) {
        	throw new SkipException(NO_XML_SKIP);
        }
        
		Assert.assertTrue(vendorAttributes.xsdFileExists(),
				"Schema could not be loaded from XML 'schemaLocation'.");
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
        // If there is no "Vendor_Attributes.xml", then skip
        if (!this.xmlFileExists()) {
        	throw new SkipException(NO_XML_SKIP);
        }
        
        Assert.assertTrue(vendorAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", vendorAttributes.getXsdFileName()));
        
        String errors = vendorAttributes.schemaValidationErrors();
        Assert.assertEquals(errors, "", vendorAttributes.getXmlFileName() + 
        		" does not validate against its XML Schema file. Errors: " + errors);
    }
}
