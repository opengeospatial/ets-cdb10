package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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

    @Test
    public void verifyMaterialsXmlElementNameIsUnique() {
        ArrayList<String> names = getNameValues();
        for (String name : names) {
            Assert.assertEquals(Collections.frequency(names, name), 1,
                    String.format("Materials.xml element Name should be unique. '%s' is not unique.", name));
        }
    }

    @Test
    public void verifyMaterialsXmlAllBaseMaterialElementsHaveAChildNodeName() {
        NodeList baseMaterialNodes = XmlUtilities.getNodeList("//Base_Material[not(Name)]", Paths.get(path, "Metadata", "Materials.xml"));
        Assert.assertEquals(baseMaterialNodes.getLength(), 0, "Materials.xml element Base_Material requires a child element Name.");
    }

    private ArrayList<String> getNameValues() {
        NodeList nameNodes = XmlUtilities.getNodeList("//Base_Material/Name", Paths.get(path, "Metadata", "Materials.xml"));

        ArrayList<String> nameValues = new ArrayList<>();

        for (int i = 0; i < nameNodes.getLength(); i++) {
            Node currentItem = nameNodes.item(i);
            nameValues.add(currentItem.getTextContent());
        }
        return nameValues;
    }
}
