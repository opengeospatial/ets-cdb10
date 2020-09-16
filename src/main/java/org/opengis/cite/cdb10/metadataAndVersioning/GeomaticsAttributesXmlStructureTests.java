package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.metadataXml.GeomaticsAttributesXml;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-10.
 */
public class GeomaticsAttributesXmlStructureTests extends Capability2Tests {
	
	private final static String NO_XML_SKIP = "Will not check for Geomatics Attributes Schema file as no Geomatics Attributes XML file exists.";
	
	private GeomaticsAttributesXml geomaticsAttributes;

	/**
	 * Initialize the GeomaticsAttributesXml validation class.
	 */
	private void loadXmlFile() {
		this.geomaticsAttributes = new GeomaticsAttributesXml(path);
	}
	
	private Boolean xmlFileExists() {
		return this.geomaticsAttributes.xmlFileExists();
	}

    @Test(description = "OGC 15-113r5, Section 3.1.1")
    public void verifyGeomaticsAttributesSchemaExists() {
        this.loadXmlFile();
        // If there is no "Geomatics_Attributes.xml", then skip
        if (!this.xmlFileExists()) {
        	throw new SkipException(NO_XML_SKIP);
        }
        
		Assert.assertTrue(geomaticsAttributes.xsdFileExists(),
				"Schema could not be loaded from XML 'schemaLocation'.");
    }

    @Test(description = "OGC 15-113r5, A.1.19, Test 77")
    public void verifyGeomaticsAttributesAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        // If there is no "Geomatics_Attributes.xml", then skip
        if (!this.xmlFileExists()) {
        	throw new SkipException(NO_XML_SKIP);
        }
        
        Assert.assertTrue(geomaticsAttributes.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", geomaticsAttributes.getXmlFileName()));
        Assert.assertTrue(geomaticsAttributes.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", geomaticsAttributes.getXsdFileName()));
        
        String errors = geomaticsAttributes.schemaValidationErrors();
        Assert.assertEquals(errors, "", geomaticsAttributes.getXmlFileName() + 
        		" does not validate against its XML Schema file. Errors: " + errors);
    }
}
