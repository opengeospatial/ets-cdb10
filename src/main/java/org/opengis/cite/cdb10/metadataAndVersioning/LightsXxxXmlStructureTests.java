package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by martin on 2016-09-12.
 */
public class LightsXxxXmlStructureTests extends CommonFixture {

    private static final List<String> DIRECTIONALITY_VALUES = Arrays.asList("Omnidirectional", "Directional", "Bidirectional");

    @BeforeClass
    public void checkFilesExist() {
        Assert.assertFalse(getCustomLightsXmlFiles().isEmpty(), "No custom Lights xml files.");
    }

    @Test
    public void verifyLights_XxxXmlFileExists() {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", xmlFile.getName())), "Optional file.");
        }
    }

    @Test
    public void verifyLightsXmlFileNameIsValid() {
        ArrayList<String> invalidFileNames = new ArrayList<>();

        for (File xmlFile : getCustomLightsXmlFiles()) {
            if (!xmlFile.getName().matches("^Lights_[a-zA-Z0-9_-]{0,25}.xml$")) {
                invalidFileNames.add(xmlFile.getName());
            }
        }

        if (invalidFileNames.size() > 0) {
            Assert.fail(String.format("%s are not a valid file name(s) the file name must start with 'Lights_', " +
                    "can only be a maximum of 32 characters and contain letters, numbers, " +
                    "underscores and dashes.", invalidFileNames.toString()));
        }
    }

    @Test
    public void verifyLightsTuningXsdFFileExists() {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Schema", "Lights_Tuning.xsd")),
                    "If a custom Lights_xxx.xml exists there should be Lights_Tuning.xsd in the Schema folder.");
        }
    }

    @Test
    public void verifyLightsXxxXmlAgainstSchema() throws IOException, SAXException {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            File xsdFile = Paths.get(path, "Metadata", "Schema", "Lights_Tuning.xsd").toFile();

            SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(xmlFile, xsdFile);

            if (!errorHandler.noErrors()) {
                Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
            }
        }
    }

    @Test
    public void verifyLightsXxxXmlDirectionalityValueIsValid() {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            ArrayList<String> directionalityValues = getDirectionalityValues(xmlFile);

            for (String value : directionalityValues) {
                Assert.assertTrue(DIRECTIONALITY_VALUES.contains(value),
                        String.format("'%s' element Directionality should have a value of 'Omnidirectional', " +
                                "'Directional' or 'Bidirectional'. Value '%s' is not valid.", xmlFile.getName(), value));
            }
        }
    }

    @Test
    public void verifyLightsXxxXmlElementIntensityIsInRange() {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            ArrayList<String> invalidIntensityValues = getInvalidPercentageValues(xmlFile, "//Intensity");

            Assert.assertEquals(invalidIntensityValues.size(), 0,
                    String.format("'%s' Intensity elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidIntensityValues.toString()));
        }
    }

    @Test
    public void verifyLightsXxxXmlElementResidualIntensityIsInRange() {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            ArrayList<String> invalidResidualIntensityValues = getInvalidPercentageValues(xmlFile, "//Residual_Intensity");

            Assert.assertEquals(invalidResidualIntensityValues.size(), 0,
                    String.format("'%s' Residual_Intensity elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidResidualIntensityValues.toString()));
        }
    }

    @Test
    public void verifyLightsXxxXmlElementDuty_CycleIsInRange() {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            ArrayList<String> invalidResidualIntensityValues = getInvalidPercentageValues(xmlFile, "//Duty_Cycle");

            Assert.assertEquals(invalidResidualIntensityValues.size(), 0,
                    String.format("'%s' Duty_Cycle elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidResidualIntensityValues.toString()));
        }
    }

    @Test
    public void verifyLightsXxxXmlFrequencyValueIsValid() {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            ArrayList<String> invalidFrequencyValues = getInvalidFrequencyValues(xmlFile);

            Assert.assertEquals(invalidFrequencyValues.size(), 0,
                    String.format("'%s' Duty_Cycle elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidFrequencyValues.toString()));
        }
    }

    @Test
    public void verifyLightsXxxXmlColorIsInRange() {
        for (File xmlFile : getCustomLightsXmlFiles()) {
            ArrayList<String> invalidColorValues = getInvalidColorValues(xmlFile);

            Assert.assertEquals(invalidColorValues.size(), 0,
                    String.format("'%s' Duty_Cycle elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidColorValues.toString()));
        }
    }

    private List<File> getCustomLightsXmlFiles() {
        String glob = "glob:Lights_*.xml";

        final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);

        List<File> lightsXmlFiles = new ArrayList<>();

        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path, "Metadata"));
            for (Path entry : stream) {
                if (pathMatcher.matches(entry.getFileName())) {
                    lightsXmlFiles.add(entry.toFile());
                }
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lightsXmlFiles;
    }

    private boolean valueIsOutOfRange(Float value) {
        return value < 0.0 || value > 1.0;
    }

    private ArrayList<String> getDirectionalityValues(File xmlFile) {
        NodeList nodeList = XMLUtils.getNodeList("//Directionality", Paths.get(path, "Metadata", xmlFile.getName()));

        ArrayList<String> directionalityValues = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            directionalityValues.add(currentItem.getTextContent());
        }
        return directionalityValues;
    }

    private ArrayList<String> getInvalidColorValues(File xmlFile) {
        NodeList frequencyNodes = XMLUtils.getNodeList("//Color", Paths.get(path, "Metadata", xmlFile.getName()));

        ArrayList<String> invalidColorValues = new ArrayList<>();

        for (int i = 0; i < frequencyNodes.getLength(); i++) {
            Node currentItem = frequencyNodes.item(i);
            String[] values = currentItem.getTextContent().split("\\s+");

            for (String value : values) {
                Float floatValue = Float.parseFloat(value);
                if (valueIsOutOfRange(floatValue)) {
                    invalidColorValues.add(floatValue.toString());
                }
            }
        }
        return invalidColorValues;
    }

    private ArrayList<String> getInvalidPercentageValues(File xmlFile, String nodeToSearchFor) {
        NodeList nodes = XMLUtils.getNodeList(nodeToSearchFor, Paths.get(path, "Metadata", xmlFile.getName()));

        ArrayList<String> invalidValues = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node currentItem = nodes.item(i);
            Float value = Float.parseFloat(currentItem.getTextContent());

            if (valueIsOutOfRange(value)) {
                invalidValues.add(currentItem.getTextContent());
            }
        }
        return invalidValues;
    }

    private ArrayList<String> getInvalidFrequencyValues(File xmlFile) {
        NodeList frequencyNodes = XMLUtils.getNodeList("//Frequency", Paths.get(path, "Metadata", xmlFile.getName()));

        ArrayList<String> invalidFrequencyValues = new ArrayList<>();

        for (int i = 0; i < frequencyNodes.getLength(); i++) {
            Node currentItem = frequencyNodes.item(i);
            Float value = Float.parseFloat(currentItem.getTextContent());

            if (value < 0.0) {
                invalidFrequencyValues.add(currentItem.getTextContent());
            }
        }
        return invalidFrequencyValues;
    }
}
