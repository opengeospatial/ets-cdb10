package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GTModelInteriorTextureStructureTests extends Capability1Tests {
	/**
	 * Validates that GTModelInteriorTexture filenames have valid codes/names.
	 * Test based on Section 3.4.4, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyModelInteriorTextureFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "507_GTModelInteriorTexture");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * Example of valid filename:
		 * D507_S001_T001_L10_AC_1.rgb
		 */
		Pattern filePattern = Pattern.compile(
				"^D507_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC|L\\d{2})_(?<tnam>[^.]+)\\.(?<ext>.+)$"
				);
		Pattern otherPattern = Pattern.compile("^(D509|D513).+");
		
		DirectoryStream<Path> tnamPrefixDirs = Files.newDirectoryStream(gtModelGeomPath);

		for (Path tnamPrefixDir : tnamPrefixDirs) {
			DirectoryStream<Path> secondDirs = Files.newDirectoryStream(tnamPrefixDir);
			String firstDirFilename = tnamPrefixDir.getFileName().toString();

			for (Path secondDir : secondDirs) {
				DirectoryStream<Path> textureNames = Files.newDirectoryStream(secondDir);
				String secondDirFilename = secondDir.getFileName().toString();

				for (Path textureName : textureNames) {
					DirectoryStream<Path> files = Files.newDirectoryStream(textureName);
					String textureFilename = textureName.getFileName().toString();

					for (Path file : files) {
						String filename = file.getFileName().toString();

						// Ignore other valid directories/files that could be
						// in this directory
						Matcher otherMatch = otherPattern.matcher(filename);
						if (otherMatch.find()) {
							return;
						} else if (StringUtils.countMatches(filename, "_") < 4) {
							errors.add("Should be at least four underscore separators: " + filename);
						} else {
							Matcher match = filePattern.matcher(filename);
							if (!match.find()) {
								errors.add("Invalid file name: " + filename);
							} else {
								validateComponentSelectorFormat(match.group("cs1"), 1, filename, errors);
								validateComponentSelectorFormat(match.group("cs2"), 2, filename, errors);
								
								if (!match.group("tnam").equals(textureFilename)) {
									errors.add("Texture Name Code does not match parent directory: "
											+ filename);
								}
								
								if (!match.group("ext").equals("rgb")) {
									errors.add("Invalid file extension for D507: " + filename);
								}
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
