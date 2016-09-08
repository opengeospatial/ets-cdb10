package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-08.
 */
public class CDBAttributesXmlStructureTests extends CommonFixture {

    @Test
    public void verifyCDBAttributesXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "CDB_Attributes.xml")),
                "Metadata directory should contain CDB_Attributes.xml file.");
    }

}
