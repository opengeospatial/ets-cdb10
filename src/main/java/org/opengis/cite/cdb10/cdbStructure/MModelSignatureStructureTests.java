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

public class MModelSignatureStructureTests extends CommonFixture {
	/**
	 * Validates that MModelSignature DIS Entity Kind directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDISEntityKind() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		MovingModelCodesXml mmcDefs = new MovingModelCodesXml("src/test/resources/CDB");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
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
				if (codeID < 0) {
					errors.add("Invalid prefix cannot be below 0: " + filename);
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

	/**
	 * Validates that MModelSignature DIS Domain directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDISDomain() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		MovingModelCodesXml mmcDefs = new MovingModelCodesXml("src/test/resources/CDB");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				String filename = domainDir.getFileName().toString();
				String code = null;
				Integer codeID = null;
				String domainName = null;
				try {
					code = filename.split("_")[0];
					codeID = Integer.parseInt(code);
					domainName = filename.split("_")[1];
				}
				catch (NumberFormatException e) {
					errors.add("Invalid number format: " + filename);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					errors.add("Missing kind name: " + filename);
				}

				if (codeID != null) {
					if (codeID < 0) {
						errors.add("Invalid prefix cannot be below 0: " + filename);
					} else if (!mmcDefs.isValidDomainCode(codeID)) {
						errors.add("Invalid DIS Domain code: " + filename);
					} else if (!mmcDefs.isValidDomainName(domainName)) {
						errors.add("Invalid DIS Domain name: " + filename);
					} else if (!mmcDefs.domainNameForCode(codeID).equals(domainName)) {
						errors.add("Invalid DIS Domain code/name combination: " + filename);
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
