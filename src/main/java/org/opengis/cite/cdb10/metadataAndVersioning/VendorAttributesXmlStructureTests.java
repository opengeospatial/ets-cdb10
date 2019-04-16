package org.opengis.cite.cdb10.metadataAndVersioning;

import java.io.IOException;

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

    @Test
    public void verifyVendorAttributesXmlFileExists() {
        this.loadXmlFile();
    	Assert.assertTrue(vendorAttributes.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", vendorAttributes.getXmlFileName()));
    }

    @Test
    public void verifyVendorAttributesXsdFileExists() {
        this.loadXmlFile();
		Assert.assertTrue(vendorAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", vendorAttributes.getXsdFileName()));
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        
        Assert.assertTrue(vendorAttributes.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", vendorAttributes.getXmlFileName()));
        Assert.assertTrue(vendorAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", vendorAttributes.getXsdFileName()));
        
        String errors = vendorAttributes.schemaValidationErrors();
        Assert.assertEquals(errors, "", vendorAttributes.getXmlFileName() + 
        		" does not contain valid XML. Errors: " + errors);
    }
}
