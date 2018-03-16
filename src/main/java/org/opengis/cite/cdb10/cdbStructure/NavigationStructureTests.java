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

public class NavigationStructureTests extends CommonFixture {
	/**
	 * Validate the Navigation datasets.
	 */
	@Test
	public void verifyDatasets() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(Paths.get(this.path, "Navigation"))) {
			String filename = file.getFileName().toString();
			if (!filename.equals("400_NavDat")) {
				errors.add("Invalid dataset: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
