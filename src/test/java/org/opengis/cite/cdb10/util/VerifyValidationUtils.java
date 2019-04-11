package org.opengis.cite.cdb10.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opengis.cite.validation.SchematronValidator;

/**
 * Verifies the behavior of the ValidationUtils class.
 */
public class VerifyValidationUtils {

    public VerifyValidationUtils() {
    }

    @Test
    public void testBuildSchematronValidator() {
        String schemaRef = "http://schemas.opengis.net/gml/3.2.1/SchematronConstraints.xml";
        String phase = "";
        SchematronValidator result = ValidationUtils.buildSchematronValidator(
                schemaRef, phase);
        assertNotNull(result);
    }
}
