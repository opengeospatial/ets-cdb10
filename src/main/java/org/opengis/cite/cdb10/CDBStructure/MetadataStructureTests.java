package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
