package org.opengis.cite.cdb10.level1;

import java.io.File;

import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.opengis.cite.cdb10.CDBStructure.Capability1Tests;

/**
 * Verifies the behavior of the Capability1Tests test class. Test stubs replace
 * fixture constituents where appropriate.
 */
public class VerifyCapability1Tests {

    private File cdb_root;
    private Capability1Tests testSuite;

    //    private static final String SUBJ = "testSubject";
//    private static DocumentBuilder docBuilder;
//    private static ITestContext testContext;
//    private static ISuite suite;
//
    public VerifyCapability1Tests() {
        cdb_root = new File(System.getProperty("user.dir") + "\\CDB");
        cdb_root.mkdirs();

        testSuite = new Capability1Tests(cdb_root.getPath());
    }

    @Test
    public void verifyMetaDataFoldersExist_missingSchemaFolder() {
        // setup
        File folders = new File(cdb_root.getPath() + "\\MetaData\\Stylesheets");
        folders.mkdirs();

        // execute
        TestResult result = new TestResult();
        TestSuite.createTest(Capability1Tests.class, "verifyMetaDataFoldersExist").run(result);
        assertFalse(result.wasSuccessful());
    }

//    @BeforeClass
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }
//
//    @Test(expected = AssertionError.class)
//    public void testIsEmpty() {
//        Capability1Tests iut = new Capability1Tests();
//        iut.isEmpty();
//    }
//
//    @Test
//    public void testTrim() {
//        Capability1Tests iut = new Capability1Tests();
//        iut.trim();
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void supplyNullTestSubject() throws SAXException, IOException {
//        Capability1Tests iut = new Capability1Tests();
//        iut.docIsValidAtomFeed();
//    }
//
//    @Test
//    public void supplyValidAtomFeedViaTestContext() throws SAXException,
//            IOException {
//        Document doc = docBuilder.parse(this.getClass().getResourceAsStream(
//                "/atom-feed.xml"));
//        when(suite.getAttribute(SUBJ)).thenReturn(doc);
//        Capability1Tests iut = new Capability1Tests();
//        iut.obtainTestSubject(testContext);
//        iut.docIsValidAtomFeed();
//    }
}
