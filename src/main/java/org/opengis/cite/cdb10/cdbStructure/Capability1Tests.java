package org.opengis.cite.cdb10.cdbStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.SuiteAttribute;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Includes various tests of capability 1.
 */
public class Capability1Tests extends CommonFixture {

	public Capability1Tests() {
	}

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
			Assert.assertTrue(levels.contains(1),
					"Conformance level 1 will not be checked as it is not included in ics");
		}
		super.obtainTestSubject(testContext);
	}
	
	/**
	 * Validate that a Component Selector is a valid format
	 * @param cs The Component Selector substring
	 * @param index Integer for "1" (CS1) or "2" (CS2)
	 * @param filename The filename being tested, used for errors
	 * @param errors ArrayList<String> of errors, will be modified in-place
	 */
	protected void validateComponentSelectorFormat(String cs, Integer index, String filename, ArrayList<String> errors) {
		if (cs.length() != 3) {
			errors.add(String.format("Component Selector %d should be 3 characters: %s", index, filename));
		}
		
		try {
			Integer csInt = Integer.parseInt(cs);

			if (((csInt < 10) && !cs.substring(0,2).equals("00")) ||
					((csInt < 100) && !cs.substring(0,1).equals("0"))) {
				errors.add(String.format("Invalid padding on CS%d: %s", index, filename));
			}
		}
		catch (NumberFormatException e) {
			errors.add(String.format("Invalid CS%d number format: %s", index, filename));
		}
		catch (StringIndexOutOfBoundsException e) {
			errors.add(String.format("Invalid CS%d length: %s", index, filename));
		}
	}
}
