package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-09.
 */
public class MaterialsXmlStructureTests extends CommonFixture {

    @Test
    public void verifyMaterialsXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Materials.xml")),
                "Metadata directory should contain Materials.xml file.");
    }


    @Test
    public void verifyMaterialsXmlAgainstSchema() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Materials.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Base_Material_Table.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XmlUtilities.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }
}
