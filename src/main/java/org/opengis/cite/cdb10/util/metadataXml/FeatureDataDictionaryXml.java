package org.opengis.cite.cdb10.util.metadataXml;

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

	public boolean isValidSubcategoryCode(String code) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Subcategory[@code = \"" +
				code + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidFeatureTypeCode(String code) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Subcategory/Feature_Type[@code = \"" +
				code + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidCategoryLabel(String label) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Label[. = \"" + label +
				"\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidSubcategoryLabel(String label) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Subcategory/Label[. = \""
				+ label + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidFeatureTypeLabel(String label) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Subcategory/Feature_Type/Label[. = \""
				+ label + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isCategoryLabelinCategoryCode(String label, String code) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category[@code=\"" + code +
				"\"]/Label[. = \"" + label + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isSubcategoryLabelinSubcategoryCode(String label, String code) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Subcategory[@code=\"" +
				code + "\"]/Label[. = \"" + label + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isFeatureTypeLabelinFeatureTypeCode(String label, String code) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Subcategory/Feature_Type[@code=\"" +
				code + "\"]/Label[. = \"" + label + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}
}
