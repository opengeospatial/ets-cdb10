package org.opengis.cite.cdb10.cdbStructure;

import java.util.ArrayList;
import org.opengis.cite.cdb10.metadataAndVersioning.MetadataXmlFile;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FeatureDataDictionaryXml extends MetadataXmlFile {
	public FeatureDataDictionaryXml(String path) {
		super(path, "Feature_Data_Dictionary.xml", "Feature_Data_Dictionary.xsd");
	}

	public boolean isValidCategoryCode(String code) {
		NodeList nodeList = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category", this.xmlFile.toPath());

		ArrayList<String> codes = new ArrayList<String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			codes.add(currentItem.getAttributes().getNamedItem("code").getNodeValue());
		}

		return codes.contains(code);
	}

	public boolean isValidCategoryLabel(String label) {
		NodeList nodeList = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category/Label",
				this.xmlFile.toPath());

		ArrayList<String> labels = new ArrayList<String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			labels.add(currentItem.getTextContent());
		}

		return labels.contains(label);
	}

	public boolean isCategoryLabelinCategoryCode(String label, String code) {
		NodeList matches = XMLUtils.getNodeList("/Feature_Data_Dictionary/Category[@code=\"" + code +
				"\"]/Label[. = \"" + label + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}
}
