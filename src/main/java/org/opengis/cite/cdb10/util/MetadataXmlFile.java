package org.opengis.cite.cdb10.util;

import org.testng.Assert;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-20.
 */
public abstract class MetadataXmlFile {
	protected File xmlFile;
	protected File xsdFile;

	public MetadataXmlFile(String path, String xmlFileName, String xsdFileName) {
		Assert.assertTrue(Files.exists(Paths.get(path, "Metadata")), String.format("Metadata directory is missing, needed for " + xmlFileName));

		this.xmlFile = Paths.get(path, "Metadata", xmlFileName).toFile();
		this.xsdFile = Paths.get(path, "Metadata", "Schema", xsdFileName).toFile();

		this.verifyXmlFileExists();
	}

	public void verifyXmlAgainstSchema() throws IOException, SAXException {
		SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(this.xmlFile, this.xsdFile);

		if (!errorHandler.noErrors()) {
			Assert.fail(this.xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
		}
	}

	private void verifyXmlFileExists() {
		Assert.assertTrue(Files.exists(this.xmlFile.toPath()), String.format("Metadata directory should contain %s file.", this.xmlFile.getName()));
		Assert.assertTrue(Files.exists(this.xsdFile.toPath()), String.format("Metadata directory should contain %s file.", this.xsdFile.getName()));
	}

}
