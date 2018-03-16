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
	 * Validates that GTModel directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDataset() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		DatasetsXml datasetDefs = new DatasetsXml("src/test/resources/CDB");

		for (Path file : Files.newDirectoryStream(Paths.get(this.path, "GTModel"))) {
			String filename = file.getFileName().toString();
			String prefix = null;
			Integer prefixID = null;
			String datasetName = null;
			try {
				prefix = filename.substring(0, 3);
				prefixID = Integer.parseInt(prefix);
				datasetName = filename.split("_")[1];
			}
			catch (StringIndexOutOfBoundsException e) {
				errors.add("Invalid prefix length: " + filename);
			}
			catch (NumberFormatException e) {
				errors.add("Invalid number format: " + filename);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				errors.add("Missing dataset name: " + filename);
			}

			if (prefixID != null) {
				if (prefixID < 1) {
					errors.add("Invalid prefix cannot be below 001: " + filename);
				} else if (!datasetDefs.isValidCode(prefixID)) {
					errors.add("Invalid dataset code: " + filename);
				} else if (!datasetDefs.isValidName(datasetName)) {
					errors.add("Invalid dataset name: " + filename);
				} else if (!datasetDefs.datasetNameForCode(prefixID).equals(datasetName)) {
					errors.add("Invalid dataset code/name combination: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
