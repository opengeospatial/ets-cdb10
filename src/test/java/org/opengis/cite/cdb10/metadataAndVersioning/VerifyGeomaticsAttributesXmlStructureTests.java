package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-10.
 */
public class VerifyGeomaticsAttributesXmlStructureTests extends MetadataTestFixture<GeomaticsAttributesXmlStructureTests> {

    private static final String GEOMATICS_ATTRIBUTES_XSD = "Geomatics_Attributes.xsd";
	private static final String GEOMATICS_ATTRIBUTES_XML = "Geomatics_Attributes.xml";
    
	private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", GEOMATICS_ATTRIBUTES_XSD));
    private final static Path XML_LOCAL_XSD = SOURCE_DIRECTORY.resolve(Paths.get("valid", GEOMATICS_ATTRIBUTES_XML));
    private final static Path XML_REMOTE_XSD = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Geomatics_Attributes_remote.xml"));
    private final static Path XML_REMOTE_XSD_MISSING = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Geomatics_Attributes_remote.xml"));
    private final static Path XML_INVALID = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Geomatics_AttributesInvalid.xml"));

    public VerifyGeomaticsAttributesXmlStructureTests() {
        testSuite = new GeomaticsAttributesXmlStructureTests();
    }
    
    @Test
    public void verifyGeomaticsAttributesXsdFileExists_XmlFileDoesNotExist() {
    	// setup: No Geomatics_Attributes.xml
    	// execute
    	testSuite.verifyGeomaticsAttributesXsdFileExists();
    }

    @Test
    public void verifyGeomaticsAttributesXsdFileExists_DoesNotExist() throws IOException {
        // setup
        Files.copy(XML_LOCAL_XSD, metadataFolder.resolve(GEOMATICS_ATTRIBUTES_XML), REPLACE_EXISTING);
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Schema could not be loaded from XML 'schemaLocation'.");

        // execute
        testSuite.verifyGeomaticsAttributesXsdFileExists();
    }
    
    @Test
    public void verifyGeomaticsAttributesXsdFileExists_RemoteXsdDoesNotExist() throws IOException {
        // setup
        Files.copy(XML_REMOTE_XSD_MISSING, metadataFolder.resolve(GEOMATICS_ATTRIBUTES_XML), REPLACE_EXISTING);
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Schema could not be loaded from XML 'schemaLocation'.");

        // execute
        testSuite.verifyGeomaticsAttributesXsdFileExists();
    }

    @Test
    public void verifyGeomaticsAttributesXsdFileExists_DoesExist() throws IOException {
        // setup
        Files.copy(XML_LOCAL_XSD, metadataFolder.resolve(GEOMATICS_ATTRIBUTES_XML), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve(GEOMATICS_ATTRIBUTES_XSD), REPLACE_EXISTING);

        // execute
        testSuite.verifyGeomaticsAttributesXsdFileExists();
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(XML_LOCAL_XSD, metadataFolder.resolve(GEOMATICS_ATTRIBUTES_XML), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve(GEOMATICS_ATTRIBUTES_XSD), REPLACE_EXISTING);

        // execute
        testSuite.verifyGeomaticsAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(XML_INVALID, metadataFolder.resolve(GEOMATICS_ATTRIBUTES_XML), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve(GEOMATICS_ATTRIBUTES_XSD), REPLACE_EXISTING);

        String expectedMessage = "Geomatics_Attributes.xml does not validate against its XML Schema file. " +
                "Errors: cvc-complex-type.2.4.b: The content of element 'Geomatics_Attributes' is not complete. " +
                "One of '{\"http://www.CDB-Spec.org/Schema/Version/3.2\":Example_Element_2}' is expected.";


        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyGeomaticsAttributesXmlAgainstSchema();
    }
}
