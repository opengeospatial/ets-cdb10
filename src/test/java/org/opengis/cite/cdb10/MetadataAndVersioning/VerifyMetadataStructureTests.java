package org.opengis.cite.cdb10.MetadataAndVersioning;

import org.junit.Test;
import org.opengis.cite.cdb10.TestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Verifies the behavior of the Capability1Tests test class. Test stubs replace
 * fixture constituents where appropriate.
 */
public class VerifyMetadataStructureTests extends TestFixture<MetadataStructureTests> {
    public VerifyMetadataStructureTests() {
        testSuite = new MetadataStructureTests();
    }

    @Test(expected = AssertionError.class)
    public void verifyMetaDataFoldersExist_missingSchemaFolder() throws IOException {
        // setup
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Stylesheet")));

        // execute
        testSuite.verifyMetaDataFoldersExist();
    }

    @Test(expected = AssertionError.class)
    public void verifyMetaDataFoldersExist_missingStylesheetFolder() throws IOException {
        // setup
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Schema")));

        // execute
        testSuite.verifyMetaDataFoldersExist();
    }

    @Test(expected = AssertionError.class)
    public void verifyMetaDataFoldersExist_noOtherFolders() throws IOException {
        // setup
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Schema")));
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Stylesheet")));
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "OtherFolder")));

        // execute
        testSuite.verifyMetaDataFoldersExist();
    }

    @Test
    public void verifyMetaDataFoldersExist_allFoldersExist() throws IOException {
        // setup
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Schema")));
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Stylesheet")));

        // execute
        testSuite.verifyMetaDataFoldersExist();
    }

    @Test
    public void verifyMetaDataFoldersExist_OtherFilesExist() throws IOException {
        // setup
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Schema")));
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Stylesheet")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Lights.xml")));

        // execute
        testSuite.verifyMetaDataFoldersExist();
    }

    @Test
    public void verifyContentsOfSchemaFolder_AllRequiredFilesExist() throws IOException {
        //setup
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Schema")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Base_Material_Table.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Composite_Material_Table.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Configuration.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Defaults.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "DIS_Country_Codes.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Feature_Data_Dictionary.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Lights.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Lights_Tuning.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Model_Components.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Model_Metadata.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Moving_Model_Codes.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "OpenFlight_Model_Extensions.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Vector_Attributes.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Version.xsd")));

        //execute
        testSuite.verifyContentsOfSchemaFolder();
    }

    @Test
    public void verifyContentsOfSchemaFolder_AllRequiredFilesDoNotExist() throws IOException {
        //setup - missing 2 files Vector_Attributes.xsd & Version.xsd
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Schema")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Base_Material_Table.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Composite_Material_Table.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Configuration.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Defaults.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "DIS_Country_Codes.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Feature_Data_Dictionary.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Lights.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Lights_Tuning.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Model_Components.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Model_Metadata.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "Moving_Model_Codes.xsd")));
        Files.createFile(cdb_root.resolve(Paths.get("Metadata", "Schema", "OpenFlight_Model_Extensions.xsd")));

        String expectedMessage = "Schema folder is missing these files: [Vector_Attributes.xsd, Version.xsd] expected [true] but found [false]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        //execute
        testSuite.verifyContentsOfSchemaFolder();
    }
}
