package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * File/directory structure tests for the root of the CDB directory
 * @author jpbadger
 *
 */
public class RootStructureTests extends Capability1Tests {
	/**
	 * Validate the contents of the root directory of the CDB, checking for stray
	 * files or directories not on the allowed list.
	 * Test based on Section 3.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 */
	@Test
	public void verifyRootContents() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> permittedRootDirectories = new ArrayList<String>(
				Arrays.asList("Metadata", "GTModel", "MModel", "Tiles", "Navigation"));

		for (Path file : Files.newDirectoryStream(Paths.get(this.path))) {
			String filename = file.getFileName().toString();
			if (!permittedRootDirectories.contains(filename)) {
				errors.add("Invalid file in root directory: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
