package org.opengis.cite.cdb10.cdbStructure.GSModel;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class VerifyGSModelInteriorMaterialStructureTests extends GSModelStructureTests<GSModelInteriorMaterialStructureTests> {
	protected static final String VALID_ARCHIVE_NAME = "N62W162_D308_S001_T001_L07_U38_R102.zip";
	protected static final String VALID_ENTRY_NAME = "N62W162_D308_S001_T001_L07_U38_R102_AcmeFactory.tif";
	
	public VerifyGSModelInteriorMaterialStructureTests() throws IOException {
		this.testSuite = new GSModelInteriorMaterialStructureTests();
	}
	
	/**
	 * Creates a Path for a GSModelInteriorMaterial archive with a custom filename.
	 * Filename must include file extension. Archive will be placed in:
	 * CDB Root > Tiles > N99 > W162 > 308_GSModelInteriorMaterial
	 * 
	 * @param archiveFilename
	 * @return Path for GSModelInteriorMaterial archive file
	 * @throws IOException 
	 */
	protected Path createGSModelInteriorMaterialArchive(String archiveFilename) throws IOException {
		return createGSModelArchive(GSModelInteriorMaterialStructureTests.DATASET_DIRECTORY, archiveFilename);
	}

	/*
	 * Verify GS Model Interior Material filenames
	 */
	@Test
	public void verifyGSModelInteriorMaterialFile_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalid() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("0.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid file name");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidLatitude() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N99W162_D308_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N99)");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidLongitude() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N62W999_D308_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W999)");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidDataset() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N62W162_D000_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidCS1() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N62W162_D308_S000_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (308)");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidCS2() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N62W162_D308_S001_T000_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (308)");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidLod() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N62W162_D308_S001_T001_L99_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidUref() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N62W162_D308_S001_T001_L07_U999_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidRref() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N62W162_D308_S001_T001_L07_U38_R9999.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFile_invalidExt() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive("N62W162_D308_S001_T001_L07_U38_R102.7z");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: 7z");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFile();
	}
	
	/*
	 * Verify GS Model Interior Material archive
	 */
	@Test
	public void verifyGSModelInteriorMaterialFileArchive_valid() throws IOException {
		// setup
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFileArchive_zeroZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[0];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Zero-length ZIP archive");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFileArchive_tooBigZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[32000001];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("ZIP archive exceeds 32 Megabytes");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFileArchive_notAZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[100];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid ZIP archive file");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialFileArchive_compressedEntry() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithCompressedEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("should not be compressed");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialFileArchive();
	}
	
	/*
	 * Verify GS Model Interior Material entries
	 */
	@Test
	public void verifyGSModelInteriorMaterialEntry_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalid() throws IOException {
		// setup
		String filename = "0.flt";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid entry '0.flt' in ZIP archive");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidLatitude() throws IOException {
		// setup
		String filename = "N92W162_D308_S001_T001_L07_U38_R102_AcmeFactory.tif";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N92)");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidLongitude() throws IOException {
		// setup
		String filename = "N62W962_D308_S001_T001_L07_U38_R102_AcmeFactory.tif";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W962)");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidDataset() throws IOException {
		// setup
		String filename = "N62W162_D000_S001_T001_L07_U38_R102_AcmeFactory.tif";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000 in entry");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidCS1() throws IOException {
		// setup
		String filename = "N62W162_D308_S000_T001_L07_U38_R102_AcmeFactory.tif";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (308)");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidCS2() throws IOException {
		// setup
		String filename = "N62W162_D308_S001_T000_L07_U38_R102_AcmeFactory.tif";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (308)");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidLod() throws IOException {
		// setup
		String filename = "N62W162_D308_S001_T001_L99_U38_R102_AcmeFactory.tif";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidUref() throws IOException {
		// setup
		String filename = "N62W162_D308_S001_T001_L07_U999_R102_AcmeFactory.tif";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidRref() throws IOException {
		// setup
		String filename = "N62W162_D308_S001_T001_L07_U38_R9999_AcmeFactory.tif";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
	
	@Test
	public void verifyGSModelInteriorMaterialEntry_invalidExt() throws IOException {
		// setup
		String filename = "N62W162_D308_S001_T001_L07_U38_R102_AcmeFactory.txt";
		Path archive = createGSModelInteriorMaterialArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: txt");

		// execute
		this.testSuite.verifyGSModelInteriorMaterialEntry();
	}
}
