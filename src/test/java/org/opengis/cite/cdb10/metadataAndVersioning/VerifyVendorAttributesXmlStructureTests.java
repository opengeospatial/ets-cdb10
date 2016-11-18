package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
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

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Vendor_Attributes.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Vendor_Attributes.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Vendor_AttributesInvalid.xml"));

    public VerifyVendorAttributesXmlStructureTests() { testSuite = new VendorAttributesXmlStructureTests(); }

    @Test
    public void verifyVendorAttributesXsdFileExists_DoesNotExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Vendor_Attributes.xml")));

        String expectedMessage = "Metadata directory should contain Vendor_Attributes.xsd file.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyVendorAttributesXsdFileExists();
    }

    @Test
    public void verifyVendorAttributesXsdFileExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Vendor_Attributes.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Vendor_Attributes.xsd")));

        // execute
        testSuite.verifyVendorAttributesXsdFileExists();
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Vendor_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vendor_Attributes.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyVendorAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Vendor_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vendor_Attributes.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Vendor_Attributes.xml does not contain valid XML. " +
                "Errors: cvc-complex-type.2.4.b: The content of element 'Vendor_Attributes' is not complete. " +
                "One of '{\"http://www.CDB-Spec.org/Schema/Version/3.2\":Example_Element_2}' is expected.";


        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyVendorAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema_VendorAttributesXmlFileDoesNotExist() throws IOException, SAXException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Vendor_Attributes.xsd")));

        String expectedMessage = "Metadata directory should contain Vendor_Attributes.xml file.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyVendorAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema_VendorAttributesXsdFileDoesNotExist() throws IOException, SAXException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Vendor_Attributes.xml")));

        String expectedMessage = "Metadata directory should contain Vendor_Attributes.xsd file.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyVendorAttributesXmlAgainstSchema(); // will not return an assertion error
    }
}
