package org.opengis.cite.cdb10.cdbStructure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.util.FilenamePatterns;
import org.opengis.cite.cdb10.util.reference.CdbReference;
import org.opengis.cite.cdb10.util.reference.DatasetsValidator;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class GSModelGeometryStructureTests extends Capability1Tests {

	/**
	 * Validates that GSModelGeometry filenames have valid codes/names.
	 * @throws IOException DirectoryStream error 
	 */
	@Test(description = "OGC 15-113r5, A.1.13, Test 40")
	public void verifyGSModelFile() throws IOException {
		Path gsModelGeomPath = Paths.get(this.path, "Tiles", "300_GSModelGeometry");
		
		// Skip test if CDB does not have a GSModelGeometry directory.
		if (Files.notExists(gsModelGeomPath)) {
			throw new SkipException("No GSModelGeometry present; test skipped.");
		}
		
		CdbReference references = new CdbReference();
		DatasetsValidator datasetsValidator = references.buildDatasetsValidator();
		
		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GSModelGeometry);
		
		for (Path archive : Files.newDirectoryStream(gsModelGeomPath)) {
			String filename = archive.getFileName().toString();
			Matcher match = filePattern.matcher(filename);
			if (!match.find()) {
				errors.add("Invalid file name: " + filename);
			} else {
				// groups: lat, lon, datasetCode, cs1, cs2, lod, uref, rref, ext
				validateLatitude(match.group("lat"), errors);
				validateLongitude(match.group("lon"), errors);
				
				int datasetCode = Integer.parseInt(match.group("datasetCode"));
				if (!datasetsValidator.isValidCode(datasetCode)) {
					errors.add(String.format("Invalid code %s", match.group("datasetCode")));
				}
				
				String cs1 = match.group("cs1");
				String cs2 = match.group("cs2");
				
				validateComponentSelectorFormat(cs1, 1, filename, errors);
				validateComponentSelector1(cs1, "300", errors);
				validateComponentSelectorFormat(cs2, 2, filename, errors);
				validateComponentSelector2(cs2, cs1, "300", errors);
				
				String lod = match.group("lod");
				validateLod(lod, errors);
				
				Integer lodLevel = null;
				if (!lod.equals("LC")) {
					lodLevel = Integer.parseInt(lod.substring(1));
				}
				Integer uref = Integer.parseInt(match.group("uref").substring(1));
				
				validateUref(uref, lodLevel, errors);
				
				validateRref(Integer.parseInt(match.group("rref").substring(1)), lodLevel, errors);
				
				String ext = match.group("ext");
				if (!ext.equalsIgnoreCase("zip")) {
					errors.add("Invalid archive extension: " + ext);
				}
			}
		}
		
		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
	
	/**
	 * Validates that GSModelGeometry files are ZIP archive files with no
	 * compression, and limited to 32 MB.
	 * @throws IOException DirectoryStream error 
	 */
	@Test(description = "OGC 15-113r5, Section 3.6.3.2")
	public void verifyGSModelFileArchive() throws IOException {
		Path gsModelGeomPath = Paths.get(this.path, "Tiles", "300_GSModelGeometry");
		
		// Skip test if CDB does not have a GSModelGeometry directory.
		if (Files.notExists(gsModelGeomPath)) {
			throw new SkipException("No GSModelGeometry present; test skipped.");
		}
		
		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GSModelGeometry);
		
		for (Path archive : Files.newDirectoryStream(gsModelGeomPath)) {
			String filename = archive.getFileName().toString();
			Matcher match = filePattern.matcher(filename);
			// Any files that do not match the GSModelGeometry file pattern will
			// be ignored, and will fail "verifyGSModelFile()" instead.
			if (match.find()) {
				File archiveFile = archive.toFile();
				long archiveLength = archiveFile.length();
				
				if (archiveLength == 0) {
					errors.add("Zero-length ZIP archive: " + filename);
				} else if (archiveLength > 32000000) {
					errors.add("ZIP archive exceeds 32 Megabytes: " + filename);
				}
				
				try {
					ZipFile zip = new ZipFile(archiveFile);
					Enumeration<? extends ZipEntry> entries = zip.entries();
					
					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						if (entry.getMethod() != ZipEntry.STORED) {
							errors.add(
								String.format("Entry '%s' in ZIP archive '%s' should not be compressed",
										entry.getName(), filename)
							);
						}
					}
					
					zip.close();
					
				} catch (ZipException e) {
					errors.add("Invalid ZIP archive file: " + filename);
				} catch (IOException e) {
					errors.add("Could not open file: " + filename);
				}
				
			}
		}
		
		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
