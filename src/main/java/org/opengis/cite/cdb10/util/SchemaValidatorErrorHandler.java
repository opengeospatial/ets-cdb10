package org.opengis.cite.cdb10.util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Created by martin on 2016-09-03.
 */
public class SchemaValidatorErrorHandler implements ErrorHandler {

    private Exception exception;

    @Override
    public void warning(SAXParseException e) throws SAXException {
        exception = e;
        throw e;
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        exception = e;
        throw e;
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        exception = e;
        throw e;
    }

    public Exception getException() {
        return exception;
    }

    public boolean noErrors() {
        return exception == null;
    }
}
