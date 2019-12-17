package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.metadataXml.GeomaticsAttributesXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-10.
 */
public class GeomaticsAttributesXmlStructureTests extends Capability2Tests {
	
	private GeomaticsAttributesXml geomaticsAttributes;

	private void loadXmlFile() {
		this.geomaticsAttributes = new GeomaticsAttributesXml(path);
	}

    @Test
    public void verifyGeomaticsAttributesXmlFileExists() {
        this.loadXmlFile();
    	Assert.assertTrue(geomaticsAttributes.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", geomaticsAttributes.getXmlFileName()));
    }

    @Test
    public void verifyGeomaticsAttributesXsdFileExists() {
        this.loadXmlFile();
		Assert.assertTrue(geomaticsAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", geomaticsAttributes.getXsdFileName()));
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        
        Assert.assertTrue(geomaticsAttributes.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", geomaticsAttributes.getXmlFileName()));
        Assert.assertTrue(geomaticsAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", geomaticsAttributes.getXsdFileName()));
        
        String errors = geomaticsAttributes.schemaValidationErrors();
        Assert.assertEquals(errors, "", geomaticsAttributes.getXmlFileName() + 
        		" does not validate against its XML Schema file. Errors: " + errors);
    }
}
