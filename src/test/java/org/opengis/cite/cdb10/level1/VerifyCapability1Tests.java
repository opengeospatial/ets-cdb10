package org.opengis.cite.cdb10.level1;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.opengis.cite.cdb10.CDBStructure.Capability1Tests;
import org.opengis.cite.cdb10.SuiteAttribute;
import org.testng.ISuite;
import org.testng.ITestContext;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Verifies the behavior of the Capability1Tests test class. Test stubs replace
 * fixture constituents where appropriate.
 */
public class VerifyCapability1Tests {

    private Path cdb_root;
    private Capability1Tests testSuite;
    private static ITestContext testContext;
    private static ISuite suite;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void initTestFixture() {
        testContext = mock(ITestContext.class);
        suite = mock(ISuite.class);
        when(testContext.getSuite()).thenReturn(suite);
    }

    @Before
    public void createTestDatabaseFolder() throws IOException {
        cdb_root = Files.createTempDirectory("cdbTest-");

        when(suite.getAttribute(SuiteAttribute.TEST_SUBJECT.getName())).thenReturn(cdb_root.toFile());
        testSuite.initCommonFixture(testContext);
        testSuite.obtainTestSubject(testContext);
    }

    @After
    public void cleanupTestCDB() throws IOException {
        Files.walkFileTree(cdb_root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public VerifyCapability1Tests() {
        testSuite = new Capability1Tests();
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

//    @Test(expected = AssertionError.class)
//    public void verifyMetadataFilesExist_missingOneFile() {
//        ArrayList<String> metadata_files = new ArrayList<>(Arrays.asList("Lights.xml", "Model_Components.xml",
//                "Materials.xml", "Defaults.xml", "Version.xml", "CDB_Attributes.xml", "Geomatics_Attributes.xml",
//                "Vendor_Attributes.xml", "Configuration.xml"));
//
//        for (String file : metadata_files) {
//
//        }
//    }
}
