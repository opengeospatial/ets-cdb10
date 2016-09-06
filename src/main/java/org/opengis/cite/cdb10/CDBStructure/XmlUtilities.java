package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by martin on 2016-09-06.
 */
public class XmlUtilities {
    public static SchemaValidatorErrorHandler validateXmlFileIsValid(File xmlFile, File xsdFile) throws SAXException, IOException {
        SchemaValidatorErrorHandler errorHandler = new SchemaValidatorErrorHandler();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsdFile);

        Validator validator = schema.newValidator();
        validator.setErrorHandler(errorHandler);

        validator.validate(new StreamSource(xmlFile));

        return errorHandler;
    }

    public static NodeList getNodeList(String xpathQuery, Path xmlFile) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(String.valueOf(xmlFile));
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression exp = xPath.compile(xpathQuery);
            return (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            return null;
        }
    }
}
