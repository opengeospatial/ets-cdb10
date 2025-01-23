package org.opengis.cite.cdb10.util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by martin on 2016-09-03.
 */
public class SchemaValidatorErrorHandler implements ErrorHandler {

	private final List<SAXParseException> exceptionList = new LinkedList<>();

	/** {@inheritDoc} */
	@Override
	public void warning(SAXParseException e) throws SAXException {
		exceptionList.add(e);
	}

	/** {@inheritDoc} */
	@Override
	public void error(SAXParseException e) throws SAXException {
		exceptionList.add(e);
	}

	/** {@inheritDoc} */
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		System.out.println("here");
		exceptionList.add(e);
	}

	/**
	 * <p>
	 * getMessages.
	 * </p>
	 * @return a {@link java.lang.String} object
	 */
	public String getMessages() {
		Iterator<SAXParseException> exceptionIterator = exceptionList.iterator();

		String messages = "";
		while (exceptionIterator.hasNext()) {
			messages += String.valueOf(exceptionIterator.next().getMessage());
			if (exceptionIterator.hasNext()) {
				messages += ", ";
			}
		}
		return messages;
	}

	/**
	 * <p>
	 * noErrors.
	 * </p>
	 * @return a boolean
	 */
	public boolean noErrors() {
		return exceptionList.isEmpty();
	}

}
