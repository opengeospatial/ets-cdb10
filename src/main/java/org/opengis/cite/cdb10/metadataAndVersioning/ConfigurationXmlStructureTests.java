package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.metadataXml.ConfigurationXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by martin on 2016-09-07.
 */
public class ConfigurationXmlStructureTests extends Capability2Tests {

	private ConfigurationXml configuration;

	private void loadXmlFile() {
		this.configuration = new ConfigurationXml(path);
	}

	private Boolean xmlFileExists() {
		return Files.exists(this.configuration.getXmlFilePath());
	}

	/**
	 * <p>
	 * verifyConfigurationXmlAgainstSchema.
	 * </p>
	 * @throws java.io.IOException if any.
	 * @throws org.xml.sax.SAXException if any.
	 */
	@Test(description = "OGC 15-113r3, A.1.19, Test 76")
	public void verifyConfigurationXmlAgainstSchema() throws IOException, SAXException {
		this.loadXmlFile();
		if (!this.xmlFileExists()) {
			return;
		}

		if (configuration.xmlFileExists()) {
			String errors = configuration.schemaValidationErrors();
			Assert.assertEquals(errors, "", configuration.getXmlFileName()
					+ " does not validate against its XML Schema file. Errors: " + errors);
		}
	}

}
