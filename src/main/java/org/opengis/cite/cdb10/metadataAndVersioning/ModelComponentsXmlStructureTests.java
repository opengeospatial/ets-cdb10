package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.metadataXml.ModelComponentsXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by martin on 2016-09-02.
 */
public class ModelComponentsXmlStructureTests extends Capability2Tests {
	
	private ModelComponentsXml modelComponents;

	private void loadXmlFile() {
		this.modelComponents = new ModelComponentsXml(path);
	}
	
	private Boolean xmlFileExists() {
		return Files.exists(this.modelComponents.getXmlFilePath());
	}

    @Test
    public void verifyModelComponentsXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        if (!this.xmlFileExists()) { return; }
        
        String errors = modelComponents.schemaValidationErrors();
        Assert.assertEquals(errors, "", modelComponents.getXmlFileName() + 
        		" does not validate against its XML Schema file. Errors: " + errors);
    }
}
