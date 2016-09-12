package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 2016-09-12.
 */
public class LightsXxxXmlStructureTests extends CommonFixture {

    @Test
    public void verifyLightsTuningXsdFFileExists() {
        File xmlFile = getCustomLightsXmlFile();

        if (xmlFile != null) {
            System.out.println("here");
            Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Schema", "Lights_Tuning.xsd")),
                    "If a custom Lights_xxx.xml exists there should be Lights_Tuning.xsd in the Schema folder.");
        }
    }

    @Test
    public void verifyLightsXxxXmlAgainstSchema() throws IOException, SAXException {
        File xmlFile = getCustomLightsXmlFile();
        if (xmlFile != null) {
            File xsdFile = Paths.get(path, "Metadata", "Schema", "Lights_Tuning.xsd").toFile();

            SchemaValidatorErrorHandler errorHandler = XmlUtilities.validateXmlFileIsValid(xmlFile, xsdFile);

            if (!errorHandler.noErrors()) {
                Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
            }
        }
    }

    private File getCustomLightsXmlFile() {
        String glob = "glob:Lights_*.xml";

        final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);

        List<String> files = new ArrayList<>();

        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path, "Metadata"));
            for (Path entry : stream) {
                if (pathMatcher.matches(entry.getFileName())) {
                    files.add(String.valueOf(entry.getFileName()));
                }
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (files.size() > 0) {
            return Paths.get(path, "Metadata", files.get(0)).toFile();
        } else {
            return null;
        }
    }
}
