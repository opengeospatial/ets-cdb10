package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MModelGeometryStructureTests extends CommonFixture {
	/**
	 * Validates that MModelGeometry DIS Entity Kind directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDISEntityKind() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		MovingModelCodesXml mmcDefs = new MovingModelCodesXml("src/test/resources/CDB");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "600_MModelGeometry"))) {
			String filename = kindDir.getFileName().toString();
			String code = null;
			Integer codeID = null;
			String kindName = null;
			try {
				code = filename.split("_")[0];
				codeID = Integer.parseInt(code);
				kindName = filename.split("_")[1];
			}
			catch (NumberFormatException e) {
				errors.add("Invalid number format: " + filename);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				errors.add("Missing kind name: " + filename);
			}

			if (codeID != null) {
				if (codeID < 1) {
					errors.add("Invalid prefix cannot be below 1: " + filename);
				} else if (!mmcDefs.isValidKindCode(codeID)) {
					errors.add("Invalid DIS Entity Kind code: " + filename);
				} else if (!mmcDefs.isValidKindName(kindName)) {
					errors.add("Invalid DIS Entity Kind name: " + filename);
				} else if (!mmcDefs.kindNameForCode(codeID).equals(kindName)) {
					errors.add("Invalid DIS Entity Kind code/name combination: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
