package org.opengis.cite.cdb10;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.testng.ISuite;
import org.testng.ITestContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void initTestFixture() {
		testContext = mock(ITestContext.class);
		suite = mock(ISuite.class);
		when(testContext.getSuite()).thenReturn(suite);
	}

	@Before
	public void createTestDatabaseFolder() throws IOException {
		this.cdb_root = Files.createTempDirectory("cdbTest-");

		when(suite.getAttribute(SuiteAttribute.TEST_SUBJECT.getName())).thenReturn(this.cdb_root.toFile());
		this.testSuite.initCommonFixture(testContext);
		this.testSuite.obtainTestSubject(testContext);
	}

	@After
	public void cleanupTestCDB() throws IOException {
		deleteRecursive(this.cdb_root.toFile());
	}

	public boolean deleteRecursive(File path) {
	    if (path.isDirectory()) {
	        for (File file : path.listFiles()) {
	            if (!deleteRecursive(file)) {
	                return false;
	            }
	        }
	    }
	    return path.delete();
	}
}
