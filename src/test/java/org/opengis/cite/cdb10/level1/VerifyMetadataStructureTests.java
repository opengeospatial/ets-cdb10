package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.MetadataStructureTests;
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
}
