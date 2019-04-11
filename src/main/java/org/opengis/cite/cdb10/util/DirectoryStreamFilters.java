package org.opengis.cite.cdb10.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common directory stream filters that can be re-used in tests.
 */
public class DirectoryStreamFilters {
	
	/**
	 * Filter for paths that look like LOD directories
	 * @return DirectoryStream Filter
	 */
	public static DirectoryStream.Filter<Path> lodFilter() {
		final Pattern lodPattern = Pattern.compile("^LC|L\\d{2}");
		return new DirectoryStream.Filter<Path>() {
			public boolean accept(Path file) throws IOException {
				String filename = file.getFileName().toString();
				Matcher match = lodPattern.matcher(filename);
				return match.find();
			}
		};
	}
}
