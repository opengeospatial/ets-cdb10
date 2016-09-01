package org.opengis.cite.cdb10;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.opengis.cite.cdb10.CDBStructure.MetadataStructureTests;
import org.testng.ISuite;
import org.testng.ITestContext;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by serene on 2016-09-01.
 */
public abstract class TestFixture<T extends CommonFixture> {
    protected Path cdb_root;
    private static ITestContext testContext;
    private static ISuite suite;
    protected T testSuite;

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
}
