package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-07.
 */
public class ConfigurationXmlStructureTests extends CommonFixture {

    @Test
    public void verifyConfigurationXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Configuration.xml")),
                "Metadata directory should contain Configuration.xml file.");
    }
}
