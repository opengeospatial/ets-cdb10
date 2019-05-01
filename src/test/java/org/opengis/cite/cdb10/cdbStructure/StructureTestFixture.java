package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.TestFixture;

public class StructureTestFixture<T extends CommonFixture> extends TestFixture<T> {

	/**
	 * Import sample Metadata that can be used by the suite as sources for lookups.
	 * @throws IOException Could not load metadata from filesystem
	 */
	@Before
	public void loadMetadata() throws IOException {
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Metadata")));
		FileUtils.copyDirectory(Paths.get("src/test/resources/CDB/Metadata").toFile(),
				this.cdb_root.resolve(Paths.get("Metadata")).toFile());
	}

}
