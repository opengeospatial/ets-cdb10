package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-02.
 */
public class ModelComponentsXmlStructureTests extends CommonFixture {

    @Test
    public void verifyModelComponentsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Model_Components.xml")),
                "Metadata directory should contain Model_Components.xml file.");
    }

    @Test
    public void verifyModelComponentsXmlFileIsValid() {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(Paths.get(path, "Metadata", "Schema", "Model_Components.xsd").toFile());
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(Paths.get(path, "Metadata", "Model_Components.xml").toFile()));
        } catch ( SAXException | IOException | NullPointerException | IllegalArgumentException ex) {
            Assert.fail("Model_Components.xml does not contain valid XML.");
        }
    }
}
