package org.opengis.cite.cdb10.util.metadataXml;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.URIUtils;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Provides validation methods compatible with Geomatics Attributes and Vendor Attributes
 * metadata files. See GeomaticsAttributesXml and VendorAttributesXml for more
 * information.
 */
public class AttributesXml {

	/**
	 * xmlFile
	 */
	protected File xmlFile;

	/**
	 * xsdFile
	 */
	protected File xsdFile;

	/**
	 * <p>
	 * Constructor for AttributesXml.
	 * </p>
	 * @param path a {@link java.lang.String} object
	 * @param filename a {@link java.lang.String} object
	 * @param schemaXPath a {@link java.lang.String} object
	 */
	public AttributesXml(String path, String filename, String schemaXPath) {
		this.xmlFile = Paths.get(path, "Metadata", filename).toFile();
		this.xsdFile = null;

		// Parse XML for schema location
		String schemaLocation = this.loadSchemaLocation(schemaXPath);

		// Get local copy of schema, if necessary
		if (schemaLocation.isEmpty()) {
			// Do nothing, leave xsdFile as null
		}
		else if (schemaLocation.toLowerCase().startsWith("http")) {
			// Schema starts with HTTP, download it
			this.loadRemoteSchema(schemaLocation);
		}
		else {
			// Try to load it locally
			this.xsdFile = Paths.get(path, "Metadata", schemaLocation).toFile();
		}
	}

	/**
	 * For this instance, download the schema from schemaLocation and set it as the schema
	 * file.
	 * @param schemaLocation HTTP/HTTPS URI pointing to schema
	 */
	protected void loadRemoteSchema(String schemaLocation) {
		URI schemaURI = null;
		try {
			schemaURI = new URI(schemaLocation);
		}
		catch (URISyntaxException e) {
			// Invalid schema URI
		}

		try {
			this.xsdFile = URIUtils.dereferenceURI(schemaURI);

			// If nothing is downloaded, then an empty directory is created
			// and we need to catch that.
			if (!this.xsdFile.isFile()) {
				this.xsdFile = null;
			}
		}
		catch (IOException e) {
			// Cannot retrieve schema
		}
	}

	/**
	 * Attempt to load the XML file and extract the schemaLocation as a String.
	 * "schemaLocation" may be a relative file path or a URL. If there are errors then an
	 * empty string is returned.
	 * @return String XML schema location
	 */
	private String loadSchemaLocation(String schemaXPath) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbFactory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e1) {
			return "";
		}

		Document xmlDoc = null;
		try {
			xmlDoc = db.parse(this.xmlFile);
		}
		catch (SAXException | IOException e1) {
			return "";
		}

		// We use an empty namespace mapping as we do not need one for
		// schemaLocation.
		Map<String, String> mappings = new HashMap<String, String>();
		NodeList schemaInfo = null;
		try {
			schemaInfo = XMLUtils.evaluateXPath(xmlDoc, schemaXPath, mappings);
		}
		catch (XPathExpressionException e) {
			return "";
		}

		// Check for only a single schemaLocation match
		if (schemaInfo.getLength() != 1) {
			return "";
		}
		else {
			return schemaInfo.item(0).getTextContent();
		}
	}

	/**
	 * <p>
	 * getXmlFileName.
	 * </p>
	 * @return a {@link java.lang.String} object
	 */
	public String getXmlFileName() {
		if (this.xmlFile == null) {
			return null;
		}
		else {
			return this.xmlFile.getName();
		}
	}

	/**
	 * <p>
	 * getXmlFilePath.
	 * </p>
	 * @return a {@link java.nio.file.Path} object
	 */
	public Path getXmlFilePath() {
		if (this.xmlFile == null) {
			return null;
		}
		else {
			return this.xmlFile.toPath();
		}
	}

	/**
	 * <p>
	 * getXsdFileName.
	 * </p>
	 * @return a {@link java.lang.String} object
	 */
	public String getXsdFileName() {
		if (this.xsdFile == null) {
			return null;
		}
		else {
			return this.xsdFile.getName();
		}
	}

	/**
	 * <p>
	 * getXsdFilePath.
	 * </p>
	 * @return a {@link java.nio.file.Path} object
	 */
	public Path getXsdFilePath() {
		if (this.xsdFile == null) {
			return null;
		}
		else {
			return this.xsdFile.toPath();
		}
	}

	/**
	 * <p>
	 * xmlFileExists.
	 * </p>
	 * @return a boolean
	 */
	public boolean xmlFileExists() {
		if (this.xmlFile == null) {
			return false;
		}
		else {
			return Files.exists(this.xmlFile.toPath());
		}
	}

	/**
	 * <p>
	 * xsdFileExists.
	 * </p>
	 * @return a boolean
	 */
	public boolean xsdFileExists() {
		if (this.xsdFile == null) {
			return false;
		}
		else {
			return Files.exists(this.xsdFile.toPath());
		}
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
