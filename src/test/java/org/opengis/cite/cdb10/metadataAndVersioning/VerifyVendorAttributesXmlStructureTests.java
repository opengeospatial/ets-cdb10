package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.testng.SkipException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-09.
 */
public class VerifyVendorAttributesXmlStructureTests extends MetadataTestFixture<VendorAttributesXmlStructureTests> {
	
	private static final String VENDOR_ATTRIBUTES_XSD = "Vendor_Attributes.xsd";
	private static final String VENDOR_ATTRIBUTES_XML = "Vendor_Attributes.xml";
    
	private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", VENDOR_ATTRIBUTES_XSD));
    private final static Path XML_LOCAL_XSD = SOURCE_DIRECTORY.resolve(Paths.get("valid", VENDOR_ATTRIBUTES_XML));
    private final static Path XML_REMOTE_XSD_MISSING = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Vendor_AttributesRemoteMissing.xml"));
    private final static Path XML_INVALID = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Vendor_AttributesInvalid.xml"));

    public VerifyVendorAttributesXmlStructureTests() { testSuite = new VendorAttributesXmlStructureTests(); }
    
    @Test
    public void verifyVendorAttributesSchemaExists_XmlFileDoesNotExist() {
    	// setup: No Vendor_Attributes.xml
    	expectedException.expect(SkipException.class);
        expectedException.expectMessage("Will not check for Vendor Attributes Schema file as no Vendor Attributes XML file exists.");
        
    	// execute
    	testSuite.verifyVendorAttributesSchemaExists();
    }

    @Test
    public void verifyVendorAttributesSchemaExists_XsdDoesNotExist() throws IOException {
        // setup
        Files.copy(XML_LOCAL_XSD, metadataFolder.resolve(VENDOR_ATTRIBUTES_XML), REPLACE_EXISTING);
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Schema could not be loaded from XML 'schemaLocation'.");

        // execute
        testSuite.verifyVendorAttributesSchemaExists();
    }
    
    @Test
    public void verifyVendorAttributesSchemaExists_RemoteXsdDoesNotExist() throws IOException {
        // setup
        Files.copy(XML_REMOTE_XSD_MISSING, metadataFolder.resolve(VENDOR_ATTRIBUTES_XML), REPLACE_EXISTING);
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Schema could not be loaded from XML 'schemaLocation'.");

        // execute
        testSuite.verifyVendorAttributesSchemaExists();
    }
    
    @Test
    public void verifyVendorAttributesSchemaExists_XsdDoesExist() throws IOException {
        // setup
        Files.copy(XML_LOCAL_XSD, metadataFolder.resolve(VENDOR_ATTRIBUTES_XML), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve(VENDOR_ATTRIBUTES_XSD), REPLACE_EXISTING);

        // execute
        testSuite.verifyVendorAttributesSchemaExists();
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(XML_LOCAL_XSD, metadataFolder.resolve(VENDOR_ATTRIBUTES_XML), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve(VENDOR_ATTRIBUTES_XSD), REPLACE_EXISTING);

        // execute
        testSuite.verifyVendorAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(XML_INVALID, metadataFolder.resolve(VENDOR_ATTRIBUTES_XML), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve(VENDOR_ATTRIBUTES_XSD), REPLACE_EXISTING);

        String expectedMessage = "Vendor_Attributes.xml does not validate against its XML Schema file. " +
                "Errors: cvc-complex-type.2.4.b: The content of element 'Vendor_Attributes' is not complete. " +
                "One of '{\"http://www.CDB-Spec.org/Schema/Version/3.2\":Example_Element_2}' is expected.";


        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyVendorAttributesXmlAgainstSchema();
    }
}
