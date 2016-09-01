package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-01.
 */
public class LightsXmlStructureTests extends CommonFixture {
    public void verifyLightsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Lights.xml")), "Metadata should contain Lights.xml file.");
    }
}
