package org.opengis.cite.cdb10.cdbStructure.GSModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.cdbStructure.StructureTestFixture;

/**
 * Parent class with shared methods for GSModel verification tests.
 *
 * @param <T> CommonFixture
 */
public class GSModelStructureTests<T extends CommonFixture> extends StructureTestFixture<T> {
	/* Valid sample values inherited by child classes */
	protected static final Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
	protected static final Path EMPTY_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("valid", "empty.zip"));
	protected static final String VALID_LOD = "L07";
	protected static final String VALID_UREF = "U38";
	protected static final String VALID_LAT_CELL = "N99";
	protected static final String VALID_LON_CELL = "W162";

	/**
	 * Copy an empty ZIP archive onto "archivePath", then create a 1-byte length
	 * file entry in the ZIP archive named "entryName". Will replace any
	 * existing entries with the same name.
	 * @param archivePath Path to ZIP archive
	 * @param entryName Name of entry to create in ZIP archive
	 * @throws IOException
	 */
	protected void createArchiveWithEntryNamed(Path archivePath, String entryName) throws IOException {
		byte[] bytes = new byte[1];
		CRC32 crc32 = new CRC32();
		crc32.update(bytes);
		
		FileOutputStream outputStream = new FileOutputStream(archivePath.toFile());
		ZipOutputStream zipStream = new ZipOutputStream(outputStream);
		
		// Force-set STORED (no compression)
		zipStream.setMethod(ZipOutputStream.STORED);
		zipStream.setLevel(Deflater.NO_COMPRESSION);
		
		ZipEntry entry = new ZipEntry(entryName);
		entry.setSize(bytes.length);
		entry.setCrc(crc32.getValue());
		entry.setCompressedSize(bytes.length);
		zipStream.putNextEntry(entry);
		zipStream.write(bytes);
		zipStream.closeEntry();
		
		zipStream.finish();
		zipStream.close();
	}
	
	/**
	 * Copy an empty ZIP archive onto "archivePath", then create a compressed
	 * 1000-byte length file entry in the ZIP archive named "entryName". Will
	 * replace any existing entries with the same name.
	 * @param archivePath Path to ZIP archive
	 * @param entryName Name of entry to create in ZIP archive
	 * @throws IOException
	 */
	protected void createArchiveWithCompressedEntryNamed(Path archivePath, String entryName) throws IOException {
		byte[] bytes = new byte[1000];
		
		FileOutputStream outputStream = new FileOutputStream(archivePath.toFile());
		ZipOutputStream zipStream = new ZipOutputStream(outputStream);
		
		// Force-enable DEFLATE compression
		zipStream.setMethod(ZipOutputStream.DEFLATED);
		zipStream.setLevel(Deflater.BEST_COMPRESSION);
		
		ZipEntry entry = new ZipEntry(entryName);
		zipStream.putNextEntry(entry);
		zipStream.write(bytes);
		zipStream.closeEntry();
		
		zipStream.finish();
		zipStream.close();
	}
	
	/**
	 * Creates a Path for a GSModel archive with a custom filename.
	 * Filename must include file extension. Archive will be placed in:
	 * CDB Root > Tiles > N99 > W162 > datasetDirectory > Lod > Uref
	 * 
	 * @param datasetDirectory
	 * @param archiveFilename
	 * @return Path for archive file
	 * @throws IOException 
	 */
	protected Path createGSModelArchive(String datasetDirectory, String archiveFilename) throws IOException {
		Path parentDir = Paths.get("Tiles", VALID_LAT_CELL, VALID_LON_CELL,
				datasetDirectory, VALID_LOD, VALID_UREF);
		
		Files.createDirectories(this.cdb_root.resolve(parentDir));
		
		return this.cdb_root.resolve(Paths.get(parentDir.toString(), archiveFilename));
	}
}
