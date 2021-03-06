package org.opengis.cite.cdb10.util.metadataXml;

import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
	}
	
	public String getXmlFileName() {
		return this.xmlFile.getName();
	}
	
	public Path getXmlFilePath() {
		return this.xmlFile.toPath();
	}
	
	public String getXsdFileName() {
		return this.xsdFile.getName();
	}
	
	public Path getXsdFilePath() {
		return this.xsdFile.toPath();
	}
	
	public boolean xmlFileExists() {
		return Files.exists(this.xmlFile.toPath());
	}
	
	public boolean xsdFileExists() {
		return Files.exists(this.xsdFile.toPath());
	}
	
	public String schemaValidationErrors() throws SAXException, IOException {
		SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(this.xmlFile, this.xsdFile);

		if (!errorHandler.noErrors()) {
			return errorHandler.getMessages();
		} else {
			return "";
		}
	}
}
