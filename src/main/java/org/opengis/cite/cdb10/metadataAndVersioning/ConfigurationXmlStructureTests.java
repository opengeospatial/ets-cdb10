package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.metadataXml.ConfigurationXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-07.
 */
public class ConfigurationXmlStructureTests extends Capability2Tests {
	
	private ConfigurationXml configuration;

	private void loadXmlFile() {
		this.configuration = new ConfigurationXml(path);
	}

    @Test
    public void verifyConfigurationXmlFileExists() {
    	this.loadXmlFile();
    	Assert.assertTrue(configuration.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", configuration.getXmlFileName()));
		Assert.assertTrue(configuration.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", configuration.getXsdFileName()));
    }

    @Test
    public void verifyConfigurationXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        String errors = configuration.schemaValidationErrors();
        Assert.assertEquals(errors, "", configuration.getXmlFileName() + 
        		" does not contain valid XML. Errors: " + errors);
    }
}
