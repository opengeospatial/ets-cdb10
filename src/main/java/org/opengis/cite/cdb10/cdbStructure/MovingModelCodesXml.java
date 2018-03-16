package org.opengis.cite.cdb10.cdbStructure;

import org.opengis.cite.cdb10.metadataAndVersioning.MetadataXmlFile;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MovingModelCodesXml extends MetadataXmlFile {
	public MovingModelCodesXml(String path) {
		super(path, "Moving_Model_Codes.xml", "Moving_Model_Codes.xsd");
	}

	public boolean isValidCategoryCode(Integer code) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind/Domain/Category[@code = \"" +
				code.toString() + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidDomainCode(Integer code) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind/Domain[@code = \"" +
				code.toString() + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidKindCode(Integer code) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind[@code = \"" + code.toString() +
				"\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidCategoryName(String name) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind/Domain/Category[@name = \"" +
				name + "\"]", this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidDomainName(String name) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind/Domain[@name = \"" + name + "\"]",
				this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public boolean isValidKindName(String name) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind[@name = \"" + name + "\"]",
				this.xmlFile.toPath());

		return (matches.getLength() > 0);
	}

	public String categoryNameForCode(Integer code) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind/Domain/Category[@code = \"" +
				code.toString() + "\"]", this.xmlFile.toPath());
		Node currentItem = matches.item(0);
		String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
		return name;
	}

	public String domainNameForCode(Integer code) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind/Domain[@code = \"" +
				code.toString() + "\"]", this.xmlFile.toPath());
		Node currentItem = matches.item(0);
		String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
		return name;
	}

	public String kindNameForCode(Integer code) {
		NodeList matches = XMLUtils.getNodeList("/Moving_Model_Codes/Kind[@code = \"" + code.toString() +
				"\"]", this.xmlFile.toPath());
		Node currentItem = matches.item(0);
		String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
		return name;
	}

}
