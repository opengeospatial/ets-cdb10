package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.XMLUtils;
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

/**
 * Created by martin on 2016-09-09.
 */
public class MaterialsXmlStructureTests extends CommonFixture {

    @Test
    public void verifyMaterialsXmlFileExists() {
        new MaterialsXml(path);
    }

    @Test
    public void verifyMaterialsXmlAgainstSchema() throws IOException, SAXException {
        new MaterialsXml(path).verifyXmlAgainstSchema();
    }

    @Test
    public void verifyMaterialsXmlElementNameIsUnique() {
        new MaterialsXml(path).verifyElementNameIsUnique();
    }

    @Test
    public void verifyMaterialsXmlAllBaseMaterialElementsHaveAChildNodeName() {
        new MaterialsXml(path).verifyAllBaseMaterialElementsHaveAChildNodeName();
    }

    @Test
    public void verifyMaterialsXmlBaseMaterialNameIsValid() {
        new MaterialsXml(path).verifyBaseMaterialNameIsValid();
    }
}
