package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.CDBAttributesXmlStructureTests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-08.
 */
public class VerifyCDBAttributesXmlStructureTests extends MetadataTestFixture<CDBAttributesXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "CDB_Attributes.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "CDB_Attributes.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "CDB_AttributesInvalid.xml"));

    public VerifyCDBAttributesXmlStructureTests() {
        testSuite = new CDBAttributesXmlStructureTests();
    }

    @Test
    public void verifyCDBAttributesXmlExists_DoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain CDB_Attributes.xml file.");

        // execute
        testSuite.verifyCDBAttributesXmlFileExists();
    }

    @Test
    public void verifyConfigurationXmlExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("CDB_Attributes.xml")));

        // execute
        testSuite.verifyCDBAttributesXmlFileExists();
    }
}
