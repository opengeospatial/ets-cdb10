package org.opengis.cite.cdb10.metadataAndVersioning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.opengis.cite.cdb10.util.metadataXml.MaterialsXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by martin on 2016-09-09.
 */
public class MaterialsXmlStructureTests extends Capability2Tests {
	
	private MaterialsXml materials;

	private void loadXmlFile() {
		this.materials = new MaterialsXml(path);
	}
	
	private ArrayList<String> getNameValues() {
        NodeList nameNodes = XMLUtils.getNodeList("//Base_Material/Name", materials.getXmlFilePath());

        ArrayList<String> nameValues = new ArrayList<>();

        for (int i = 0; i < nameNodes.getLength(); i++) {
            Node currentItem = nameNodes.item(i);
            nameValues.add(currentItem.getTextContent());
        }
        return nameValues;
    }

    @Test
    public void verifyMaterialsXmlFileExists() {
        this.loadXmlFile();
    	Assert.assertTrue(materials.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", materials.getXmlFileName()));
		Assert.assertTrue(materials.xsdFileExists(),
				String.format("Metadata directory should contain %s file.", materials.getXsdFileName()));
    }

    @Test
    public void verifyMaterialsXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        String errors = materials.schemaValidationErrors();
        Assert.assertEquals(errors, "", materials.getXmlFileName() + 
        		" does not contain valid XML. Errors: " + errors);
    }

    @Test
    public void verifyMaterialsXmlElementNameIsUnique() {
        this.loadXmlFile();
        ArrayList<String> names = getNameValues();
        
        for (String name : names) {
            Assert.assertEquals(Collections.frequency(names, name), 1,
                    String.format("Materials.xml element Name should be unique. '%s' is not unique.", name));
        }
    }

    @Test
    public void verifyMaterialsXmlAllBaseMaterialElementsHaveAChildNodeName() {
    	this.loadXmlFile();
        NodeList baseMaterialNodes = XMLUtils.getNodeList("//Base_Material[not(Name)]", materials.getXmlFilePath());
        Assert.assertEquals(baseMaterialNodes.getLength(), 0, "Materials.xml element Base_Material requires a child element Name.");
    }

    @Test
    public void verifyMaterialsXmlBaseMaterialNameIsValid() {
        this.loadXmlFile();
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
}
