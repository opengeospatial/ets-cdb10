package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by serene on 2016-09-01.
 */
public class MetadataStructureTests extends CommonFixture {

    @Test
    public void verifyMetaDataFoldersExist() throws IOException {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Schema")), "Metadata should contain Schema folder.");
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Stylesheet")), "Metadata should contain Stylesheet folder.");

        int fileCount = 0;
        for (Path file : Files.newDirectoryStream(Paths.get(path, "Metadata"))) {
            if (file.toFile().isDirectory()) {
                fileCount++;
            }
        }

        Assert.assertEquals(fileCount, 2, "Metadata should only contain 2 folders.");
    }

    /**
     * The test should check if  \CDB\Schema directory contains the following files:
     * Base_Material_Table.xsd
     * Composite_Material_Table.xsd
     * Configuration.xsd
     * Defaults.xsd
     * DIS_Country_Codes.xsd
     * Feature_Data_Dictionary.xsd
     * Lights.xsd
     * Lights_Tuning.xsd
     * Model_Components.xsd
     * Model_Metadata.xsd
     * Moving_Model_Codes.xsd
     * OpenFlight_Model_Extensions.xsd
     * Vector_Attributes.xsd
     * Version.xsd
     */
    @Test
    public void verifyContentsOfSchemaFolder() throws IOException {
        List<String> REQUIRED_FILES = Arrays.asList("Base_Material_Table.xsd",
                "Composite_Material_Table.xsd",
                "Configuration.xsd",
                "Defaults.xsd",
                "DIS_Country_Codes.xsd",
                "Feature_Data_Dictionary.xsd",
                "Lights.xsd",
                "Lights_Tuning.xsd",
                "Model_Components.xsd",
                "Model_Metadata.xsd",
                "Moving_Model_Codes.xsd",
                "OpenFlight_Model_Extensions.xsd",
                "Vector_Attributes.xsd",
                "Version.xsd");

        List<String> missingFiles = new ArrayList<>();
        for (String requiredFile : REQUIRED_FILES) {
            if (!Paths.get(path, "Metadata", "Schema", requiredFile).toFile().exists()) {
                missingFiles.add(requiredFile);
            }
        }

        Assert.assertTrue(missingFiles.isEmpty(), String.format("Schema folder is missing these files: %s", missingFiles.toString()));
    }
}
