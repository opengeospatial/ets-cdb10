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

	/**
	 * <p>
	 * Constructor for MetadataXmlFile.
	 * </p>
	 * @param path a {@link java.lang.String} object
	 * @param xmlFileName a {@link java.lang.String} object
	 * @param xsdFileName a {@link java.lang.String} object
	 */
	public MetadataXmlFile(String path, String xmlFileName, String xsdFileName) {
		Assert.assertTrue(Files.exists(Paths.get(path, "Metadata")),
				String.format("Metadata directory is missing, needed for " + xmlFileName));

		this.xmlFile = Paths.get(path, "Metadata", xmlFileName).toFile();
		this.xsdFile = Paths.get(path, "Metadata", "Schema", xsdFileName).toFile();
	}

	/**
	 * <p>
	 * getXmlFileName.
	 * </p>
	 * @return a {@link java.lang.String} object
	 */
	public String getXmlFileName() {
		return this.xmlFile.getName();
	}

	/**
	 * <p>
	 * getXmlFilePath.
	 * </p>
	 * @return a {@link java.nio.file.Path} object
	 */
	public Path getXmlFilePath() {
		return this.xmlFile.toPath();
	}

	/**
	 * <p>
	 * getXsdFileName.
	 * </p>
	 * @return a {@link java.lang.String} object
	 */
	public String getXsdFileName() {
		return this.xsdFile.getName();
	}

	/**
	 * <p>
	 * getXsdFilePath.
	 * </p>
	 * @return a {@link java.nio.file.Path} object
	 */
	public Path getXsdFilePath() {
		return this.xsdFile.toPath();
	}

	/**
	 * <p>
	 * xmlFileExists.
	 * </p>
	 * @return a boolean
	 */
	public boolean xmlFileExists() {
		return Files.exists(this.xmlFile.toPath());
	}

	/**
	 * <p>
	 * xsdFileExists.
	 * </p>
	 * @return a boolean
	 */
	public boolean xsdFileExists() {
		return Files.exists(this.xsdFile.toPath());
	}

	/**
	 * <p>
	 * schemaValidationErrors.
	 * </p>
	 * @return a {@link java.lang.String} object
	 * @throws org.xml.sax.SAXException if any.
	 * @throws java.io.IOException if any.
	 */
	public String schemaValidationErrors() throws SAXException, IOException {
		SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(this.xmlFile, this.xsdFile);

		if (!errorHandler.noErrors()) {
			return errorHandler.getMessages();
		}
		else {
			return "";
		}
	}

}
