package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by martin on 2016-09-06.
 */
public class XmlValidator {
    public static SchemaValidatorErrorHandler validateXmlFileIsValid(File xmlFile, File xsdFile) throws SAXException, IOException {
        SchemaValidatorErrorHandler errorHandler = new SchemaValidatorErrorHandler();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsdFile);

        Validator validator = schema.newValidator();
        validator.setErrorHandler(errorHandler);

        validator.validate(new StreamSource(xmlFile));

        return errorHandler;
    }
}
