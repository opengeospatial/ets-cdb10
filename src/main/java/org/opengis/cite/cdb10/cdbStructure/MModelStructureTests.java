package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.util.reference.CdbReference;
import org.opengis.cite.cdb10.util.reference.DatasetsValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MModelStructureTests extends Capability1Tests {
	/**
	 * Validates that MModel directories have valid codes/names.
	 *
	 * @throws IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.15, Test 57 - based on Section 3.5.1")
	public void verifyDataset() throws IOException {
		Path mmPath = Paths.get(this.path, "MModel");

		if (Files.notExists(mmPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		CdbReference references = new CdbReference();
		DatasetsValidator validator = references.buildDatasetsValidator();

		for (Path file : Files.newDirectoryStream(mmPath)) {
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

			if (prefixID != null && !validator.isExtendedCode(prefixID)) {
				if (prefixID < 1) {
					errors.add("Invalid prefix cannot be below 001: " + filename);
				} else if (!validator.isValidCode(prefixID)) {
					errors.add("Invalid dataset code: " + filename);
				} else if (!validator.isValidName(datasetName)) {
					errors.add("Invalid dataset name: " + filename);
				} else if (!validator.datasetNameForCode(prefixID).equals(datasetName)) {
					errors.add("Invalid dataset code/name combination: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
