package org.opengis.cite.cdb10.cdbStructure.GSModel;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class VerifyGSModelTextureStructureTests extends GSModelStructureTests<GSModelTextureStructureTests> {
	
	protected final static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
	private final static Path EMPTY_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("valid", "empty.zip"));
	
	protected static final String VALID_ARCHIVE_NAME = "N62W162_D301_S001_T001_L07_U38_R102.zip";
	protected static final String VALID_ENTRY_NAME = "N62W162_D301_S001_T001_L07_U38_R102_AcmeFactory.rgb";
	
	public VerifyGSModelTextureStructureTests() throws IOException {
		this.testSuite = new GSModelTextureStructureTests();
	}
	
	@Before
    public void setupDirs() throws IOException {
    	Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", GSModelTextureStructureTests.DATASET_DIRECTORY)));
    }
	
	/**
	 * Creates a Path for a GSModelTexture archive with a custom filename.
	 * Filename must include file extension. Archive will be placed in:
	 * CDB Root > Tiles > 301_GSModelTexture
	 * 
	 * @param archiveFilename
	 * @return Path for GSModelTexture archive file
	 */
	protected Path createGSModelTextureArchive(String archiveFilename) {
		return this.cdb_root.resolve(Paths.get("Tiles", GSModelTextureStructureTests.DATASET_DIRECTORY, archiveFilename));
	}

	/*
	 * Verify GS Model Texture filenames
	 */
	@Test
	public void verifyGSModelTextureFile_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalid() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("0.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid file name");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidLatitude() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N99W162_D301_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N99)");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidLongitude() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N62W999_D301_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W999)");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidDataset() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N62W162_D000_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidCS1() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N62W162_D301_S000_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (301)");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidCS2() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N62W162_D301_S001_T000_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (301)");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidLod() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N62W162_D301_S001_T001_L99_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidUref() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N62W162_D301_S001_T001_L07_U999_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidRref() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N62W162_D301_S001_T001_L07_U38_R9999.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	@Test
	public void verifyGSModelTextureFile_invalidExt() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive("N62W162_D301_S001_T001_L07_U38_R102.7z");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: 7z");

		// execute
		this.testSuite.verifyGSModelTextureFile();
	}
	
	/*
	 * Verify GS Model Texture archive
	 */
	@Test
	public void verifyGSModelTextureFileArchive_valid() throws IOException {
		// setup
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelTextureFileArchive();
	}
	
	@Test
	public void verifyGSModelTextureFileArchive_zeroZip() throws IOException {
		// setup
		Path archivePath = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[0];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Zero-length ZIP archive");

		// execute
		this.testSuite.verifyGSModelTextureFileArchive();
	}
	
	@Test
	public void verifyGSModelTextureFileArchive_tooBigZip() throws IOException {
		// setup
		Path archivePath = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[32000001];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("ZIP archive exceeds 32 Megabytes");

		// execute
		this.testSuite.verifyGSModelTextureFileArchive();
	}
	
	@Test
	public void verifyGSModelTextureFileArchive_notAZip() throws IOException {
		// setup
		Path archivePath = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[100];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid ZIP archive file");

		// execute
		this.testSuite.verifyGSModelTextureFileArchive();
	}
	
	@Test
	public void verifyGSModelTextureFileArchive_compressedEntry() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithCompressedEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("should not be compressed");

		// execute
		this.testSuite.verifyGSModelTextureFileArchive();
	}
	
	/*
	 * Verify GS Model Texture entries
	 */
	@Test
	public void verifyGSModelTextureEntry_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalid() throws IOException {
		// setup
		String filename = "0.flt";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid entry '0.flt' in ZIP archive");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidLatitude() throws IOException {
		// setup
		String filename = "N92W162_D301_S001_T001_L07_U38_R102_AcmeFactory.rgb";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N92)");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidLongitude() throws IOException {
		// setup
		String filename = "N62W962_D301_S001_T001_L07_U38_R102_AcmeFactory.rgb";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W962)");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidDataset() throws IOException {
		// setup
		String filename = "N62W162_D000_S001_T001_L07_U38_R102_AcmeFactory.rgb";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000 in entry");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidCS1() throws IOException {
		// setup
		String filename = "N62W162_D301_S000_T001_L07_U38_R102_AcmeFactory.rgb";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (301)");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidCS2() throws IOException {
		// setup
		String filename = "N62W162_D301_S001_T000_L07_U38_R102_AcmeFactory.rgb";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (301)");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidLod() throws IOException {
		// setup
		String filename = "N62W162_D301_S001_T001_L99_U38_R102_AcmeFactory.rgb";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidUref() throws IOException {
		// setup
		String filename = "N62W162_D301_S001_T001_L07_U999_R102_AcmeFactory.rgb";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidRref() throws IOException {
		// setup
		String filename = "N62W162_D301_S001_T001_L07_U38_R9999_AcmeFactory.rgb";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
	
	@Test
	public void verifyGSModelTextureEntry_invalidExt() throws IOException {
		// setup
		String filename = "N62W162_D301_S001_T001_L07_U38_R102_AcmeFactory.txt";
		Path archive = createGSModelTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: txt");

		// execute
		this.testSuite.verifyGSModelTextureEntry();
	}
}
