package org.opengis.cite.cdb10.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

/**
 * Provides a collection of utility methods for manipulating or resolving URI references.
 */
public class URIUtils {

	private static final String FIXUP_BASE_URI = "http://apache.org/xml/features/xinclude/fixup-base-uris";

	private static final Logger LOGGER = Logger.getLogger(URIUtils.class.getName());

	/**
	 * Parses the content of the given URI as an XML document and returns a new DOM
	 * Document object. Entity reference nodes will not be expanded. XML inclusions
	 * (xi:include elements) will be processed if present.
	 * @param uriRef An absolute URI specifying the location of an XML resource.
	 * @return A DOM Document node representing an XML resource.
	 * @throws org.xml.sax.SAXException If the resource cannot be parsed.
	 * @throws java.io.IOException If the resource is not accessible.
	 */
	public static Document parseURI(URI uriRef) throws SAXException, IOException {
		if ((null == uriRef) || !uriRef.isAbsolute()) {
			throw new IllegalArgumentException("Absolute URI is required, but received " + uriRef);
		}
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(true);
		docFactory.setExpandEntityReferences(false);
		docFactory.setXIncludeAware(true);
		Document doc = null;
		try {
			// XInclude processor will not add xml:base attributes
			docFactory.setFeature(FIXUP_BASE_URI, false);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(uriRef.toString());
		}
		catch (ParserConfigurationException x) {
			TestSuiteLogger.log(Level.WARNING, "Failed to create DocumentBuilder." + x);
		}
		if (null != doc) {
			doc.setDocumentURI(uriRef.toString());
		}
		return doc;
	}

	/**
	 * Dereferences the given URI and stores the resulting resource representation in a
	 * local file. The file will be located in the default temporary file directory.
	 * @param uriRef An absolute URI specifying the location of some resource.
	 * @return A File containing the content of the resource; it may be empty if
	 * resolution failed for any reason.
	 * @param uriRef An absolute URI specifying the location of some resource.
	 * @return A File containing the content of the resource; it may be empty if
	 * resolution failed for any reason.
	 * @throws java.io.IOException If an IO error occurred.
	 */
	public static File dereferenceURI(URI uriRef) throws IOException {

		if ((null == uriRef) || !uriRef.isAbsolute()) {
			throw new IllegalArgumentException("Absolute URI is required, but received " + uriRef);
		}
		if (uriRef.getScheme().equalsIgnoreCase("file")) {
			if (uriRef.getRawSchemeSpecificPart().endsWith(".zip")) {
				File destFile = new File(uriRef);

				File destDir = new File(destFile.getParent() + "/CDB" + System.currentTimeMillis());
				destDir.mkdir();

				unzipFile(destFile, destDir);

				return destDir;
			}
			else {
				return new File(uriRef);
			}
		}
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(uriRef);
		Builder reqBuilder = target.request();
		Invocation req = reqBuilder.buildGet();
		Response rsp = req.invoke();

		String suffix = null;
		Object contentTypeHeaderObject = rsp.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
		if (contentTypeHeaderObject != null && contentTypeHeaderObject instanceof String) {
			if (((String) contentTypeHeaderObject).endsWith("xml")) {
				suffix = ".xml";
			}

		}
		else {
			LOGGER.info("Content-Type header is not instanceof String, is: " + contentTypeHeaderObject.getClass());
		}

		File destFile = File.createTempFile("entity-", suffix);

		if (rsp.hasEntity()) {
			InputStream is = rsp.readEntity(InputStream.class);
			OutputStream os = new FileOutputStream(destFile);
			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			is.close();
			os.flush();
			os.close();
		}

		File destDir = new File(destFile.getParent() + "/CDB" + System.currentTimeMillis());
		destDir.mkdir();

		unzipFile(destFile, destDir);

		TestSuiteLogger.log(Level.FINE,
				"Wrote " + destFile.length() + " bytes to file at " + destFile.getAbsolutePath());

		return destDir;
	}

	/**
	 * Unzips a file to a directory.
	 * @param zippedFile The zipped file
	 * @param destDir The destination directory
	 * @throws java.io.IOException If an IO error occurred.
	 */
	public static void unzipFile(File zippedFile, File destDir) throws IOException {

		byte[] buffer = new byte[1024];
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zippedFile));
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		while (zipEntry != null) {
			File outFile = new File(destDir, zipEntry.getName());
			if (zipEntry.isDirectory()) {
				outFile.mkdir();
			}
			else {
				FileOutputStream fileOutputStream = new FileOutputStream(outFile);
				int len;
				while ((len = zipInputStream.read(buffer)) > 0) {
					fileOutputStream.write(buffer, 0, len);
				}
				fileOutputStream.close();
			}
			zipEntry = zipInputStream.getNextEntry();
		}
		zipInputStream.closeEntry();
		zipInputStream.close();
	}

	/**
	 * Constructs an absolute URI from the given URI reference and a base URI.
	 *
	 * @see <a href="http://tools.ietf.org/html/rfc3986#section-5.2">RFC 3986, 5.2</a>
	 * @param baseURI The base URI; if present, it must be an absolute URI.
	 * @param uriRef A URI reference that may be relative to the given base URI.
	 * @return The resulting URI.
	 */
	public static URI resolveRelativeURI(String baseURI, String uriRef) {
		URI uri = (null != baseURI) ? URI.create(baseURI) : URI.create("");
		if (null != baseURI && null == uri.getScheme()) {
			throw new IllegalArgumentException("Base URI has no scheme component: " + baseURI);
		}
		return uri.resolve(uriRef);
	}

}
