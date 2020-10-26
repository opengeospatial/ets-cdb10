package org.opengis.cite.cdb10.cdbStructure.GSModel;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGSModelDescriptorStructureTests extends GSModelStructureTests<GSModelDescriptorStructureTests> {
	
	protected final static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
	private final static Path EMPTY_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("valid", "empty.zip"));
	
	protected static final String VALID_ARCHIVE_NAME = "N62W162_D303_S001_T001_L07_U38_R102.zip";
	protected static final String VALID_ENTRY_NAME = "N62W162_D303_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.xml";
	protected static final String VALID_LOD = "L07";
	protected static final String VALID_UREF = "U38";
	protected static final String VALID_LAT_CELL = "N99";
	protected static final String VALID_LON_CELL = "W162";
	
	public VerifyGSModelDescriptorStructureTests() throws IOException {
		this.testSuite = new GSModelDescriptorStructureTests();
	}

	/**
	 * Creates a Path for a GSModelDescriptor archive with a custom filename.
	 * Filename must include file extension. Archive will be placed in:
	 * CDB Root > Tiles > N99 > W162 > 303_GSModelDescriptor > Lod > Uref
	 * 
	 * @param archiveFilename
	 * @return Path for GSModelDescriptor archive file
	 * @throws IOException 
	 */
	protected Path createGSModelDescriptorArchive(String archiveFilename) throws IOException {
		Path parentDir = Paths.get("Tiles", VALID_LAT_CELL, VALID_LON_CELL,
				GSModelDescriptorStructureTests.DATASET_DIRECTORY, VALID_LOD, VALID_UREF);
		
		Files.createDirectories(this.cdb_root.resolve(parentDir));
		
		return this.cdb_root.resolve(Paths.get(parentDir.toString(), archiveFilename));
	}

	/*
	 * Verify GS Model Descriptor filenames
	 */
	@Test
	public void verifyGSModelDescriptorFile_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalid() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("0.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid file name");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidLatitude() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N99W162_D303_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N99)");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidLongitude() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N62W999_D303_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W999)");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidDataset() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N62W162_D000_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidCS1() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N62W162_D303_S000_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (303)");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidCS2() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N62W162_D303_S001_T000_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (303)");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidLod() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N62W162_D303_S001_T001_L99_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidUref() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N62W162_D303_S001_T001_L07_U999_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidRref() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N62W162_D303_S001_T001_L07_U38_R9999.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	@Test
	public void verifyGSModelDescriptorFile_invalidExt() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive("N62W162_D303_S001_T001_L07_U38_R102.7z");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: 7z");

		// execute
		this.testSuite.verifyGSModelDescriptorFile();
	}
	
	/*
	 * Verify GS Model Descriptor archive
	 */
	@Test
	public void verifyGSModelDescriptorFileArchive_valid() throws IOException {
		// setup
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelDescriptorFileArchive();
	}
	
	@Test
	public void verifyGSModelDescriptorFileArchive_zeroZip() throws IOException {
		// setup
		Path archivePath = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[0];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Zero-length ZIP archive");

		// execute
		this.testSuite.verifyGSModelDescriptorFileArchive();
	}
	
	@Test
	public void verifyGSModelDescriptorFileArchive_tooBigZip() throws IOException {
		// setup
		Path archivePath = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[32000001];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("ZIP archive exceeds 32 Megabytes");

		// execute
		this.testSuite.verifyGSModelDescriptorFileArchive();
	}
	
	@Test
	public void verifyGSModelDescriptorFileArchive_notAZip() throws IOException {
		// setup
		Path archivePath = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[100];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid ZIP archive file");

		// execute
		this.testSuite.verifyGSModelDescriptorFileArchive();
	}
	
	@Test
	public void verifyGSModelDescriptorFileArchive_compressedEntry() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithCompressedEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("should not be compressed");

		// execute
		this.testSuite.verifyGSModelDescriptorFileArchive();
	}
	
	/*
	 * Verify GS Model Descriptor entries
	 */
	@Test
	public void verifyGSModelDescriptorEntry_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalid() throws IOException {
		// setup
		String filename = "0.flt";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid entry '0.flt' in ZIP archive");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidLatitude() throws IOException {
		// setup
		String filename = "N92W162_D303_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.xml";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N92)");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidLongitude() throws IOException {
		// setup
		String filename = "N62W962_D303_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.xml";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W962)");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidDataset() throws IOException {
		// setup
		String filename = "N62W162_D000_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.xml";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000 in entry");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidCS1() throws IOException {
		// setup
		String filename = "N62W162_D303_S000_T001_L07_U38_R102_AL015_116_AcmeFactory.xml";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (303)");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidCS2() throws IOException {
		// setup
		String filename = "N62W162_D303_S001_T000_L07_U38_R102_AL015_116_AcmeFactory.xml";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (303)");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidLod() throws IOException {
		// setup
		String filename = "N62W162_D303_S001_T001_L99_U38_R102_AL015_116_AcmeFactory.xml";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidUref() throws IOException {
		// setup
		String filename = "N62W162_D303_S001_T001_L07_U999_R102_AL015_116_AcmeFactory.xml";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidRref() throws IOException {
		// setup
		String filename = "N62W162_D303_S001_T001_L07_U38_R9999_AL015_116_AcmeFactory.xml";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
	
	@Test
	public void verifyGSModelDescriptorEntry_invalidExt() throws IOException {
		// setup
		String filename = "N62W162_D303_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.txt";
		Path archive = createGSModelDescriptorArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: txt");

		// execute
		this.testSuite.verifyGSModelDescriptorEntry();
	}
}
