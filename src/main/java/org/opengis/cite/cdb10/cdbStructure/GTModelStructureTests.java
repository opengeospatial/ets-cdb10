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

public class GTModelStructureTests extends CommonFixture {
	/**
	 * Validates that GTModel directories have valid dataset code formatting.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDatasetCodePrefix() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(Paths.get(this.path, "GTModel"))) {
			String filename = file.getFileName().toString();
			String prefix = null;
			Integer prefixID = null;
			try {
				prefix = filename.substring(0, 3);
				prefixID = Integer.parseInt(prefix);
			}
			catch (StringIndexOutOfBoundsException e) {
				errors.add("Invalid prefix length: " + filename);
			}
			catch (NumberFormatException e) {
				errors.add("Invalid number format: " + filename);
			}

			if ((prefixID != null) && (prefixID < 1)) {
				errors.add("Invalid prefix cannot be below 001: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
