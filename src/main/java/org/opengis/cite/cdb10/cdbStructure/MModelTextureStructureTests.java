package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MModelTextureStructureTests extends CommonFixture {
	/**
	 * Validates that MModelTexture Texture Name Prefix directories have valid codes.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyTNAMPrefix() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();

		for (Path tnamPrefixDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "601_MModelTexture"))) {
			String filename = tnamPrefixDir.getFileName().toString();

			if (filename.length() != 1) {
				errors.add("Invalid length on texture name prefix directory: " + filename);
			}

			if (!filename.toUpperCase().equals(filename)) {
				errors.add("Texture name prefix directory should be uppercase: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
