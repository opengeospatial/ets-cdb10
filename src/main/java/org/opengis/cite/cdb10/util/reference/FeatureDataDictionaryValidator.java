package org.opengis.cite.cdb10.util.reference;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * <p>
 * FeatureDataDictionaryValidator class.
 * </p>
 *
 */
public class FeatureDataDictionaryValidator extends Validator {

	/**
	 * Document from Feature_Data_Dictionary.xml
	 */
	private Document doc;

	/**
	 * Initialize this validator with the contents of an XML Document.
	 * @param doc XML Document from Feature_Data_Dictionary.xml
	 */
	public FeatureDataDictionaryValidator(Document doc) {
		this.doc = doc;
	}

	/**
	 * <p>
	 * isValidCategoryCode.
	 * </p>
	 * @param code a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidCategoryCode(String code) {
		String query = "/Feature_Data_Dictionary/Category[@code = \"" + code + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidSubcategoryCode.
	 * </p>
	 * @param code a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidSubcategoryCode(String code) {
		String query = "/Feature_Data_Dictionary/Category/Subcategory[@code = \"" + code + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidFeatureTypeCode.
	 * </p>
	 * @param code a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidFeatureTypeCode(String code) {
		String query = "/Feature_Data_Dictionary/Category/Subcategory/Feature_Type[@code = \"" + code + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidCategoryLabel.
	 * </p>
	 * @param label a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidCategoryLabel(String label) {
		String query = "/Feature_Data_Dictionary/Category/Label[. = \"" + label + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidSubcategoryLabel.
	 * </p>
	 * @param label a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidSubcategoryLabel(String label) {
		String query = "/Feature_Data_Dictionary/Category/Subcategory/Label[. = \"" + label + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isValidFeatureTypeLabel.
	 * </p>
	 * @param label a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isValidFeatureTypeLabel(String label) {
		String query = "/Feature_Data_Dictionary/Category/Subcategory/Feature_Type/Label[. = \"" + label + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isCategoryLabelinCategoryCode.
	 * </p>
	 * @param label a {@link java.lang.String} object
	 * @param code a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isCategoryLabelinCategoryCode(String label, String code) {
		String query = "/Feature_Data_Dictionary/Category[@code=\"" + code + "\"]/Label[. = \"" + label + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isSubcategoryLabelinSubcategoryCode.
	 * </p>
	 * @param label a {@link java.lang.String} object
	 * @param code a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isSubcategoryLabelinSubcategoryCode(String label, String code) {
		String query = "/Feature_Data_Dictionary/Category/Subcategory[@code=\"" + code + "\"]/Label[. = \"" + label
				+ "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

	/**
	 * <p>
	 * isFeatureTypeLabelinFeatureTypeCode.
	 * </p>
	 * @param label a {@link java.lang.String} object
	 * @param code a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean isFeatureTypeLabelinFeatureTypeCode(String label, String code) {
		String query = "/Feature_Data_Dictionary/Category/Subcategory/Feature_Type[@code=\"" + code + "\"]/Label[. = \""
				+ label + "\"]";
		NodeList matches;
		try {
			matches = compileAndEvaluate(query, doc);
		}
		catch (XPathExpressionException e) {
			return false;
		}

		return (matches.getLength() > 0);
	}

}
