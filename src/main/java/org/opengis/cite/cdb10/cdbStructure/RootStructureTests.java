package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * File/directory structure tests for the root of the CDB directory
 * @author jpbadger
 *
 */
public class RootStructureTests extends CommonFixture {
	/**
	 * Validate the contents of the root directory of the CDB, checking for stray
	 * files or directories not on the allowed list.
	 */
	@Test
	public void verifyRootContents() throws IOException {
		ArrayList<String> permittedRootDirectories = new ArrayList<String>(
				Arrays.asList("Metadata", "GTModel", "MModel", "Tiles", "Navigation"));
		ArrayList<String> invalidFiles = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(Paths.get(this.path))) {
			String filename = file.getFileName().toString();
			if (!permittedRootDirectories.contains(filename)) {
				invalidFiles.add(filename);
			}
		}

		Assert.assertTrue(invalidFiles.size() == 0,
				"Invalid files in root directory: " + StringUtils.join(invalidFiles, ", "));
	}
}
