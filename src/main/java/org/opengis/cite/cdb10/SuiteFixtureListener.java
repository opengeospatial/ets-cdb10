package org.opengis.cite.cdb10;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.logging.Level;

import org.opengis.cite.cdb10.util.TestSuiteLogger;
import org.opengis.cite.cdb10.util.URIUtils;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/**
 * A listener that performs various tasks before and after a test suite is run,
 * usually concerned with maintaining a shared test suite fixture. Since this
 * listener is loaded using the ServiceLoader mechanism, its methods will be
 * called before those of other suite listeners listed in the test suite
 * definition and before any annotated configuration methods.
 *
 * Attributes set on an ISuite instance are not inherited by constituent test
 * group contexts (ITestContext). However, suite attributes are still accessible
 * from lower contexts.
 *
 * @see org.testng.ISuite ISuite interface
 */
public class SuiteFixtureListener implements ISuiteListener {

	@Override
	public void onStart(ISuite suite) {
		this.processSuiteParameters(suite);
	}

	@Override
	public void onFinish(ISuite suite) {
		this.deleteTempFiles(suite);
	}

	/**
	 * Processes test suite arguments and sets suite attributes accordingly. The
	 * entity referenced by the {@link TestRunArg#IUT iut} argument is parsed
	 * and the resulting Document is set as the value of the "testSubject"
	 * attribute.
	 *
	 * @param suite
	 *            An ISuite object representing a TestNG test suite.
	 */
	void processSuiteParameters(ISuite suite) {
		Map<String, String> params = suite.getXmlSuite().getParameters();
		TestSuiteLogger.log(Level.CONFIG,
				"Suite parameters\n" + params.toString());

		// By default, use conformance level 1
		Integer[] levels = {1};
		if (null != params.get(TestRunArg.ICS.toString())) {
			String[] levelStrings = params.get(TestRunArg.ICS.toString()).split(",");
			levels = new Integer[levelStrings.length];

			for (int i = 0; i < levelStrings.length; i++) {
				levels[i] = Integer.parseInt(levelStrings[i]);
			}
		}

		suite.setAttribute(SuiteAttribute.LEVELS.getName(), levels);

		String iutParam = params.get(TestRunArg.IUT.toString());
		
		// Process ZIP file for IUT, if present
        File iutFile = null;
        URI iutRef = URI.create(iutParam.trim());
        
        if (iutParam.trim().endsWith(".zip")) {
        	try {
	            iutFile = URIUtils.dereferenceURI(iutRef);
	        } catch (IOException iox) {
	            throw new RuntimeException("Failed to dereference resource located at " + iutRef, iox);
	        }
	        
	        iutParam = iutFile.getAbsolutePath();
        }
	    
		suite.setAttribute(SuiteAttribute.TEST_SUBJECT.getName(), iutParam);
		
		if (TestSuiteLogger.isLoggable(Level.FINE)) {
			TestSuiteLogger.log(Level.FINE, String.format("Parsed resource retrieved from %s\n", TestRunArg.IUT));
		}
	}
	
	

	/**
	 * Deletes temporary files created during the test run if TestSuiteLogger is
	 * enabled at the INFO level or higher (they are left intact at the CONFIG
	 * level or lower).
	 *
	 * @param suite
	 *            The test suite.
	 */
	void deleteTempFiles(ISuite suite) {
		if (TestSuiteLogger.isLoggable(Level.CONFIG)) {
			return;
		}
	}
}
