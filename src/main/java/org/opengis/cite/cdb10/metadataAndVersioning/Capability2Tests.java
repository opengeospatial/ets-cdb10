package org.opengis.cite.cdb10.metadataAndVersioning;

import java.util.ArrayList;
import java.util.Arrays;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.SuiteAttribute;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

/**
 * Includes various tests of capability 2.
 */
public class Capability2Tests extends CommonFixture {

	/**
	 * Obtains the test subject from the ISuite context. The suite attribute
	 * {@link org.opengis.cite.cdb10.SuiteAttribute#TEST_SUBJECT} should evaluate to
	 * a DOM Document node.
	 *
	 * @param testContext
	 *            The test (group) context.
	 */
	@Override
	@BeforeClass
	public void obtainTestSubject(ITestContext testContext) {
		Object obj = testContext.getSuite().getAttribute(SuiteAttribute.LEVELS.getName());
		if ((null != obj)) {
			ArrayList<Integer> levels = new ArrayList<Integer>(Arrays.asList(Integer[].class.cast(obj)));
			Assert.assertTrue(levels.contains(2),
					"Conformance level 2 will not be checked as it is not included in ics");
		}
		super.obtainTestSubject(testContext);
	}
}
