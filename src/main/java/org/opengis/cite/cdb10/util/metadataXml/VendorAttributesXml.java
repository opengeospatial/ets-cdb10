package org.opengis.cite.cdb10.util.metadataXml;

/**
 * Provides validation methods for "Vendor_Attributes.xml". Initialize with a path to a
 * CDB Root and the appropriate XML metadata file will be detected.
 *
 * This class has been separated from "MetadataXmlFile" to handle different logic for
 * optional XML files, and for support for remote XSD files.
 */
public class VendorAttributesXml extends AttributesXml {

	private static final String METADATA_FILENAME = "Vendor_Attributes.xml";

	/**
	 * This selects the user-defined schemaLocation so we can load it.
	 */
	private static final String SCHEMA_XPATH = "/Vendor_Attributes/@schemaLocation";

	/**
	 * <p>
	 * Constructor for VendorAttributesXml.
	 * </p>
	 * @param path a {@link java.lang.String} object
	 */
	public VendorAttributesXml(String path) {
		super(path, METADATA_FILENAME, SCHEMA_XPATH);
	}

}
