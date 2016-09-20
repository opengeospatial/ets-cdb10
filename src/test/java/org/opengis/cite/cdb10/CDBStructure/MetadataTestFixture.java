package org.opengis.cite.cdb10.CDBStructure;

import org.junit.Before;
import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.TestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by serene on 2016-09-06.
 */
public class MetadataTestFixture<T extends CommonFixture> extends TestFixture<T> {

    protected final static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
    protected Path metadataFolder;
    protected Path schemaFolder;

    @Before
    public void createDirectories() throws IOException {
        metadataFolder = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
        schemaFolder = Files.createDirectories(cdb_root.resolve(Paths.get(String.valueOf(metadataFolder), "Schema")));
    }
}
