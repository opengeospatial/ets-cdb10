package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-08.
 */
public class CDBAttributesXmlStructureTests extends CommonFixture {

    @Test
    public void verifyCDBAttributesXmlFileExists() {
        new CDBAttributesXml(path);
    }

    @Test
    public void verifyCDBAttributesXmlAgainstSchema() throws IOException, SAXException {
        new CDBAttributesXml(path).verifyXmlAgainstSchema();
    }

    @Test
    public void verifyCDBAttributesXmlCodeIsAnInteger() {
        new CDBAttributesXml(path).verifyCodeIsAnInteger();
    }

    @Test
    public void verifyCDBAttributesXmlSymbolIsUnique() {
        new CDBAttributesXml(path).verifySymbolIsUnique();
    }

    @Test
    public void verifyCDBAttributesXmlValueHasAValidType() {
        new CDBAttributesXml(path).verifyValueHasAValidType();
    }

    @Test
    public void verifyCDBAttributesXmlScalerCodeIsValid() {
        new CDBAttributesXml(path).verifyScalerCodeIsValid();
    }

    @Test
    public void verifyCDBAttributesXmlUnitCodeIsValid() {
        new CDBAttributesXml(path).verifyUnitCodeIsValid();
    }
}
