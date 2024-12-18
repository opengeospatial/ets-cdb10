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
 */
public class RootStructureTests extends Capability1Tests {

	/**
	 * Validate the contents of the root directory of the CDB, checking for stray files or
	 * directories not on the allowed list.
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.18, Test 29 - based on Section 3.1")
	public void verifyRootContents() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> permittedRootDirectories = new ArrayList<String>(
				Arrays.asList("Metadata", "GTModel", "MModel", "Tiles", "Navigation", "ExtMetadata"));

		for (Path file : Files.newDirectoryStream(Paths.get(this.path))) {
			String filename = file.getFileName().toString();
			if (!permittedRootDirectories.contains(filename)) {
				errors.add("Invalid file in root directory: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
