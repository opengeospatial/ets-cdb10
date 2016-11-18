package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.SuiteAttribute;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Includes various tests of capability 2.
 */
public class Capability2Tests extends CommonFixture {

    /**
     * Obtains the test subject from the ISuite context. The suite attribute
     * {@link org.opengis.cite.cdb10.SuiteAttribute#TEST_SUBJECT} should
     * evaluate to a DOM Document node.
     *
     * @param testContext The test (group) context.
     */
    @BeforeClass
    public void obtainTestSubject(ITestContext testContext) {
        Object obj = testContext.getSuite().getAttribute(SuiteAttribute.LEVEL.getName());
        if ((null != obj)) {
            Integer level = Integer.class.cast(obj);
            Assert.assertTrue(level > 1,
                    "Conformance level " + "2 will not be checked since ics = " + level);
        }
        super.obtainTestSubject(testContext);
    }
}
