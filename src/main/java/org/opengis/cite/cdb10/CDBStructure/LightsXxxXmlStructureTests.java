package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
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

    @Test
    public void verifyLights_XxxXmlFileExists() {
        File xmlFile = getCustomLightsXmlFile();

        if (xmlFile != null) {
            Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Schema", xmlFile.getName())), "Optional file.");
        }
    }

    @Test
    public void verifyLightsXmlFileNameIsValid() {
        File xmlFile = getCustomLightsXmlFile();

        if (!xmlFile.getName().matches("^Lights_[a-zA-Z0-9_-]{0,25}.xml$")) {
            Assert.fail(String.format("'%s' is not a valid file name it must start with 'Lights_', " +
                    "can only be a maximum of 32 characters and contain letters, numbers, " +
                    "underscores and dashes.", xmlFile.getName()));
        }
    }

    @Test
    public void verifyLightsTuningXsdFFileExists() {
        File xmlFile = getCustomLightsXmlFile();

        if (xmlFile != null) {
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

    @Test
    public void verifyLightsXxxXmlDirectionalityValueIsValid() {
        File xmlFile = getCustomLightsXmlFile();
        if (xmlFile != null) {
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
        File xmlFile = getCustomLightsXmlFile();
        if (xmlFile != null) {
            ArrayList<String> invalidIntensityValues = getInvalidPercentageValues(xmlFile, "//Intensity");

            Assert.assertEquals(invalidIntensityValues.size(), 0,
                    String.format("'%s' Intensity elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidIntensityValues.toString()));
        }
    }

    @Test
    public void verifyLightsXxxXmlElementResidualIntensityIsInRange() {
        File xmlFile = getCustomLightsXmlFile();
        if (xmlFile != null) {
            ArrayList<String> invalidResidualIntensityValues = getInvalidPercentageValues(xmlFile, "//Residual_Intensity");

            Assert.assertEquals(invalidResidualIntensityValues.size(), 0,
                    String.format("'%s' Residual_Intensity elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidResidualIntensityValues.toString()));
        }
    }

    @Test
    public void verifyLightsXxxXmlElementDuty_CycleIsInRange() {
        File xmlFile = getCustomLightsXmlFile();
        if (xmlFile != null) {
            ArrayList<String> invalidResidualIntensityValues = getInvalidPercentageValues(xmlFile, "//Duty_Cycle");

            Assert.assertEquals(invalidResidualIntensityValues.size(), 0,
                    String.format("'%s' Duty_Cycle elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidResidualIntensityValues.toString()));
        }
    }

    @Test
    public void verifyLightsXxxXmlFrequencyValueIsValid() {
        File xmlFile = getCustomLightsXmlFile();
        if (xmlFile != null) {
            ArrayList<String> invalidFrequencyValues = getInvalidFrequencyValues(xmlFile);

            Assert.assertEquals(invalidFrequencyValues.size(), 0,
                    String.format("'%s' Duty_Cycle elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidFrequencyValues.toString()));
        }
    }

    @Test
    public void verifyLightsXxxXmlColorIsInRange() {
        File xmlFile = getCustomLightsXmlFile();
        if (xmlFile != null) {
            ArrayList<String> invalidColorValues = getInvalidColorValues(xmlFile);

            Assert.assertEquals(invalidColorValues.size(), 0,
                    String.format("'%s' Duty_Cycle elements value can range from 0.0 to 1.0. Values %s are not valid.",
                            xmlFile.getName(), invalidColorValues.toString()));
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

    private boolean valueIsOutOfRange(Float value) {
        return value < 0.0 || value > 1.0;
    }

    private ArrayList<String> getDirectionalityValues(File xmlFile) {
        NodeList nodeList = XmlUtilities.getNodeList("//Directionality", Paths.get(path, "Metadata", xmlFile.getName()));

        ArrayList<String> directionalityValues = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            directionalityValues.add(currentItem.getTextContent());
        }
        return directionalityValues;
    }

    private ArrayList<String> getInvalidColorValues(File xmlFile) {
        NodeList frequencyNodes = XmlUtilities.getNodeList("//Color", Paths.get(path, "Metadata", xmlFile.getName()));

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
        NodeList nodes = XmlUtilities.getNodeList(nodeToSearchFor, Paths.get(path, "Metadata", xmlFile.getName()));

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
        NodeList frequencyNodes = XmlUtilities.getNodeList("//Frequency", Paths.get(path, "Metadata", xmlFile.getName()));

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
