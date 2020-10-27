package org.opengis.cite.cdb10.cdbStructure.GSModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.cdbStructure.Capability1Tests;
import org.opengis.cite.cdb10.util.FilenamePatterns;
import org.opengis.cite.cdb10.util.reference.CdbReference;
import org.opengis.cite.cdb10.util.reference.DatasetsValidator;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class GSModelTextureStructureTests extends Capability1Tests {

	public static final String DATASET_CODE = "301";
	public static final String DATASET_DIRECTORY = "301_GSModelTexture";
	
	/**
	 * A String Array holds the allowed file extensions for archives, and the
	 * Set is used for searching/matching.
	 */
	protected static final String[] ALLOWED_ARCHIVE_EXT = new String[] { "zip" };
	protected static final Set<String> ALLOWED_ARCHIVE_EXT_SET = new HashSet<String>(Arrays.asList(ALLOWED_ARCHIVE_EXT));
	
	/**
	 * A String Array holds the allowed file extensions for archive entry files,
	 * and the Set is used for searching/matching.
	 */
	protected static final String[] ALLOWED_ENTRY_EXT = new String[] { "rgb" };
	protected static final Set<String> ALLOWED_ENTRY_EXT_SET = new HashSet<String>(Arrays.asList(ALLOWED_ENTRY_EXT));
	
	/**
	 * Validates that GSModelTexture filenames have valid codes/names.
	 * 
	 * @throws IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.13, Test 40")
	public void verifyGSModelTextureFile() throws IOException {
		// Collect all matching datasets in the Tiles directory
		ArrayList<Path> datasetPaths = getDatasetPaths(this.path, DATASET_DIRECTORY);
		
		// Skip test if CDB does not have a GSModelTexture directory.
		if (datasetPaths.isEmpty()) {
			throw new SkipException("No GSModelTexture present; test skipped.");
		}
		
		CdbReference references = new CdbReference();
		DatasetsValidator datasetsValidator = references.buildDatasetsValidator();
		
		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GSModelTexture);
		
		iterateDatasets(datasetPaths, (archive -> {
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
				validateComponentSelector1(cs1, DATASET_CODE, errors);
				validateComponentSelectorFormat(cs2, 2, filename, errors);
				validateComponentSelector2(cs2, cs1, DATASET_CODE, errors);
				
				String lod = match.group("lod");
				validateLod(lod, errors);
				
				Integer lodLevel = parseLOD(lod);
				Integer uref = Integer.parseInt(match.group("uref").substring(1));
				
				validateUref(uref, lodLevel, errors);
				
				validateRref(Integer.parseInt(match.group("rref").substring(1)), lodLevel, errors);
				
				String ext = match.group("ext");
				if (!ALLOWED_ARCHIVE_EXT_SET.contains(ext)) {
					errors.add("Invalid archive extension: " + ext);
				}
			}
		}));
		
		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
	
	/**
	 * Validates that GSModelTexture files are ZIP archive files with no
	 * compression, and limited to 32 MB.
	 * 
	 * @throws IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, Section 3.6.3.2")
	public void verifyGSModelTextureFileArchive() throws IOException {
		// Collect all matching datasets in the Tiles directory
		ArrayList<Path> datasetPaths = getDatasetPaths(this.path, DATASET_DIRECTORY);
		
		// Skip test if CDB does not have a GSModelTexture directory.
		if (datasetPaths.isEmpty()) {
			throw new SkipException("No GSModelTexture present; test skipped.");
		}
		
		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GSModelTexture);
		
		iterateDatasets(datasetPaths, (archive -> {
			String filename = archive.getFileName().toString();
			Matcher match = filePattern.matcher(filename);
			// Any files that do not match the GSModelTexture file pattern will
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
		}));
		
		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
	
	/**
	 * Validates that GSModelTexture entries inside ZIP archive follow naming
	 * conventions.
	 * 
	 * @throws IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.13, Test 70")
	public void verifyGSModelTextureEntry() throws IOException {
		// Collect all matching datasets in the Tiles directory
		ArrayList<Path> datasetPaths = getDatasetPaths(this.path, DATASET_DIRECTORY);
		
		// Skip test if CDB does not have a GSModelTexture directory.
		if (datasetPaths.isEmpty()) {
			throw new SkipException("No GSModelTexture present; test skipped.");
		}
		
		CdbReference references = new CdbReference();
		DatasetsValidator datasetsValidator = references.buildDatasetsValidator();
		
		ArrayList<String> errors = new ArrayList<String>();
		Pattern archiveNamePattern = Pattern.compile(FilenamePatterns.GSModelTexture);
		Pattern archiveEntryPattern = Pattern.compile(FilenamePatterns.GSModelTextureEntry);
		
		iterateDatasets(datasetPaths, (archive -> {
			String filename = archive.getFileName().toString();
			Matcher match = archiveNamePattern.matcher(filename);
			// Any files that do not match the GSModelTexture file pattern will
			// be ignored, and will fail "verifyGSModelTextureFile()" instead.
			if (match.find()) {
				File archiveFile = archive.toFile();
								
				try {
					ZipFile zip = new ZipFile(archiveFile);
					Enumeration<? extends ZipEntry> entries = zip.entries();
					
					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						String entryFilename = entry.getName();
						
						Matcher entryMatch = archiveEntryPattern.matcher(entryFilename);
						
						if (!entryMatch.find()) {
							errors.add(String.format("Invalid entry '%s' in ZIP archive '%s'", entryFilename, filename));
						} else {
							// groups: lat, lon, datasetCode, cs1, cs2, lod, uref, rref, featureCode, fsc, modl, ext
							validateLatitude(entryMatch.group("lat"), errors);
							validateLongitude(entryMatch.group("lon"), errors);
							
							int datasetCode = Integer.parseInt(entryMatch.group("datasetCode"));
							if (!datasetsValidator.isValidCode(datasetCode)) {
								errors.add(String.format("Invalid code %s in entry '%s'", entryMatch.group("datasetCode"), entryFilename));
							}
							
							String cs1 = entryMatch.group("cs1");
							String cs2 = entryMatch.group("cs2");

							validateComponentSelectorFormat(cs1, 1, entryFilename, errors);
							validateComponentSelector1(cs1, DATASET_CODE, errors);
							validateComponentSelectorFormat(cs2, 2, entryFilename, errors);
							validateComponentSelector2(cs2, cs1, DATASET_CODE, errors);

							String lod = entryMatch.group("lod");
							validateLod(lod, errors);

							Integer lodLevel = parseLOD(lod);
							Integer uref = Integer.parseInt(entryMatch.group("uref").substring(1));

							validateUref(uref, lodLevel, errors);

							validateRref(Integer.parseInt(entryMatch.group("rref").substring(1)), lodLevel, errors);

							String ext = entryMatch.group("ext");
							if (!ALLOWED_ENTRY_EXT_SET.contains(ext)) {
								errors.add("Invalid archive extension: " + ext);
							}
						}
					}
					
					zip.close();
					
				} catch (ZipException e) {
					errors.add("Invalid ZIP archive file: " + filename);
				} catch (IOException e) {
					errors.add("Could not open file: " + filename);
				}
				
			}
		}));
		
		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
