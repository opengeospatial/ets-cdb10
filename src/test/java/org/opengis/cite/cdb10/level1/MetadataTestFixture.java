package org.opengis.cite.cdb10.level1;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    protected Path metadataFolder;
    protected Path schemaFolder;

    @Before
    public void createDirectories() throws IOException {
        metadataFolder = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
        schemaFolder = Files.createDirectories(cdb_root.resolve(Paths.get(String.valueOf(metadataFolder), "Schema")));
    }
}
