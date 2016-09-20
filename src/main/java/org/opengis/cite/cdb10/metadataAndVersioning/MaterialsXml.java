package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by martin on 2016-09-20.
 */
public class MaterialsXml extends MetadataXmlFile {
    public MaterialsXml(String path) {
        super(path, "Materials.xml", "Base_Material_Table.xsd");
    }

    public void verifyElementNameIsUnique() {
        ArrayList<String> names = getNameValues();
        for (String name : names) {
            Assert.assertEquals(Collections.frequency(names, name), 1,
                    String.format("Materials.xml element Name should be unique. '%s' is not unique.", name));
        }
    }

    public void verifyAllBaseMaterialElementsHaveAChildNodeName() {
        NodeList baseMaterialNodes = XMLUtils.getNodeList("//Base_Material[not(Name)]", xmlFile.toPath());
        Assert.assertEquals(baseMaterialNodes.getLength(), 0, "Materials.xml element Base_Material requires a child element Name.");
    }

    public void verifyBaseMaterialNameIsValid() {
        ArrayList<String> names = getNameValues();

        ArrayList<String> invalidNames = new ArrayList<>();

        for (String name : names) {
            if(!name.matches("^BM_[a-zA-Z0-9_-]{0,29}")) {
                invalidNames.add(name);
            }
        }

        Assert.assertEquals(invalidNames.size(), 0,
                String.format("Materials.xml element Name is always in format \"BM__*\", " +
                        "has a maximum of 32 characters, and can only contain letters, digits, " +
                        "underscores, and hyphens. %s do not conform", invalidNames.toString()));
    }

    private ArrayList<String> getNameValues() {
        NodeList nameNodes = XMLUtils.getNodeList("//Base_Material/Name", xmlFile.toPath());

        ArrayList<String> nameValues = new ArrayList<>();

        for (int i = 0; i < nameNodes.getLength(); i++) {
            Node currentItem = nameNodes.item(i);
            nameValues.add(currentItem.getTextContent());
        }
        return nameValues;
    }
}
