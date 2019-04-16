package org.opengis.cite.cdb10.util.metadataXml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DatasetsXml extends MetadataXmlFile {
	public DatasetsXml(String path) {
		super(path, "Datasets.xml", "Datasets.xsd");
	}

	/**
	 * Check if an integer code is valid according to the embedded Datasets.xml
	 * @param code Integer for Dataset code (no leading zeros)
	 * @return true/false
	 */
	public boolean isValidCode(Integer code) {
		NodeList nodeList = XMLUtils.getNodeList("//Dataset", this.xmlFile.toPath());

		ArrayList<Integer> codes = new ArrayList<Integer>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			Integer intCode = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());
			codes.add(intCode);
		}

		return codes.contains(code);
	}

	/**
	 * Check if a dataset name is valid according to the embedded Datasets.xml
	 * @param datasetName String name of the dataset
	 * @return true/false
	 */
	public boolean isValidName(String datasetName) {
		NodeList nodeList = XMLUtils.getNodeList("//Dataset", this.xmlFile.toPath());

		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			names.add(currentItem.getAttributes().getNamedItem("name").getNodeValue());
		}

		return names.contains(datasetName);
	}

	/**
	 * Lookup a String name dataset for an Integer code according to the 
	 * embedded Datasets.xml
	 * @param code Integer for Dataset code (no leading zeros)
	 * @return String name for Dataset
	 */
	public String datasetNameForCode(Integer code) {
		NodeList nodeList = XMLUtils.getNodeList("//Dataset", this.xmlFile.toPath());

		Map<Integer, String> codeMap = new HashMap<Integer, String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentItem = nodeList.item(i);
			Integer intCode = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());
			String name = currentItem.getAttributes().getNamedItem("name").getNodeValue();
			codeMap.put(intCode, name);
		}
		return codeMap.get(code);
	}
}
