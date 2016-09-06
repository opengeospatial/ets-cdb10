package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-06.
 */
public class VersionXmlStructureTests extends CommonFixture {
    public void verifyVersionXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Version.xml")),
                "Metadata directory should contain Version.xml file.");
    }
}
