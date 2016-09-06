package org.opengis.cite.cdb10.level1;

import org.opengis.cite.cdb10.CDBStructure.VersionXmlStructureTests;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-06.
 */
public class VerifyVersionXmlStructureTests extends MetadataTestFixture<VersionXmlStructureTests> {

    private static Path validVersionXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Defaults.xml"));
    private static Path versionXsdFile = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Defaults.xsd"));

    public VerifyVersionXmlStructureTests() {testSuite = new VersionXmlStructureTests(); }

}
