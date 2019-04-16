package org.opengis.cite.cdb10.util.metadataXml;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DISCountryCodesXml extends MetadataXmlFile {
	public DISCountryCodesXml(String path) {
		super(path, "DIS_Country_Codes.xml", "DIS_Country_Codes.xsd");
	}

	public boolean isValidCountryCode(Integer code) {
		NodeList matches = XMLUtils.getNodeList("/DIS_Country_Codes/Country[@code = \"" + code.toString() +
				"\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidCountryName(String name) {
		NodeList matches = XMLUtils.getNodeList("/DIS_Country_Codes/Country[@name = \"" + name + "\"]",
				this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public String countryNameForCode(Integer code) {
		NodeList matches = XMLUtils.getNodeList("/DIS_Country_Codes/Country[@code = \"" + code.toString() +
				"\"]", this.xmlFile.toPath());
		Node currentItem = matches.item(0);
		String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
		return name;
	}

}
