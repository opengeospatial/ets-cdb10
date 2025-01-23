package org.opengis.cite.cdb10.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Common directory stream filters that can be re-used in tests.
 */
public class DirectoryStreamFilters {

	/**
	 * Filter for paths that look like LOD directories
	 * @return DirectoryStream Filter
	 */
	public static DirectoryStream.Filter<Path> lodFilter() {
		return new DirectoryStream.Filter<Path>() {
			public boolean accept(Path file) throws IOException {
				return Files.isDirectory(file);
			}
		};
	}

}
