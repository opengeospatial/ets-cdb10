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

    @Test
    public void verifyVendorAttributesXsdFileExists() {
        this.loadXmlFile();
        if (!this.xmlFileExists()) { return; }
        
		Assert.assertTrue(vendorAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", vendorAttributes.getXsdFileName()));
    }

    @Test
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
