package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.metadataXml.ModelComponentsXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-02.
 */
public class ModelComponentsXmlStructureTests extends Capability2Tests {
	
	private ModelComponentsXml modelComponents;

	private void loadXmlFile() {
		this.modelComponents = new ModelComponentsXml(path);
	}

    @Test
    public void verifyModelComponentsXmlFileExists() {
        this.loadXmlFile();
    	Assert.assertTrue(modelComponents.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", modelComponents.getXmlFileName()));
		Assert.assertTrue(modelComponents.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", modelComponents.getXsdFileName()));
    }

    @Test
    public void verifyModelComponentsXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        String errors = modelComponents.schemaValidationErrors();
        Assert.assertEquals(errors, "", modelComponents.getXmlFileName() + 
        		" does not contain valid XML. Errors: " + errors);
    }
}
