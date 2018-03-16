package org.opengis.cite.cdb10.cdbStructure;

import org.opengis.cite.cdb10.metadataAndVersioning.MetadataXmlFile;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.w3c.dom.NodeList;

public class FeatureDataDictionaryXml extends MetadataXmlFile {
	public FeatureDataDictionaryXml(String path) {
		super(path, "Feature_Data_Dictionary.xml", "Feature_Data_Dictionary.xsd");
	}

	public boolean isValidCategoryCode(String code) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category[@code = \"" + code +
				"\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidCategoryLabel(String label) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Label[. = \"" + label +
				"\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isCategoryLabelinCategoryCode(String label, String code) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category[@code=\"" + code +
				"\"]/Label[. = \"" + label + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}
}
