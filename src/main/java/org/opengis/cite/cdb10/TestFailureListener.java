package org.opengis.cite.cdb10;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * A listener that augments a test result with diagnostic information in the
 * event that a test method failed. This information will appear in the XML
 * report when the test run is completed.
 */
public class TestFailureListener extends TestListenerAdapter {

    /**
     * Sets the "request" and "response" attributes of a test result. The value
     * of these attributes is a string that contains information about the
     * content of an outgoing or incoming message: target resource, status code,
     * headers, entity (if present). The entity is represented as a String with
     * UTF-8 character encoding.
     *
     * @param result A description of a test result (with a fail verdict).
     */
    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
    }
}
