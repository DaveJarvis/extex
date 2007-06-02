/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.util.xml;

import java.io.IOException;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Generic Handler.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XmlHandler extends DefaultHandler {

    /**
     * The child level in the xml document.
     */
    private int level = 0;

    /**
     * The list for the current element.
     */
    private LinkedList<String> listElement = new LinkedList<String>();

    /**
     * The list for the current text value.
     */
    private LinkedList<String> listTextValue = new LinkedList<String>();

    /**
     * Use the writer for the output.
     */
    private boolean useWriter = true;

    /**
     * The {@link XMLStreamWriter} for the output.
     */
    protected XMLStreamWriter writer = null;

    /**
     * Creates a new object.
     */
    public XmlHandler() {

        // add a empty string (for characters for the root-element)
        listTextValue.add("");

    }

    /**
     * Creates a new object.
     * 
     * @param writer The {@link XMLStreamWriter} for the output.
     */
    public XmlHandler(XMLStreamWriter writer) {

        this();
        this.writer = writer;

    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        listTextValue.addLast(listTextValue.removeLast()
                + new String(ch, start, length).trim());

        try {
            if (useWriter) {
                writer.writeCharacters(ch, start, length);
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }

    }

    /**
     * Check, if a writer exists.
     * 
     * @throws SAXException if not writer is set.
     */
    private void checkWriter() throws SAXException {

        if (useWriter && writer == null) {
            throw new SAXException("writer not set!");
        }
    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    public void endDocument() throws SAXException {

        checkWriter();
        try {
            if (useWriter) {
                writer.writeEndDocument();
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {

        checkWriter();
        try {
            if (useWriter) {
                writer.writeEndElement();
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
        level--;
        if (listElement.size() > 0) {
            listElement.removeLast();
        }
        if (listTextValue.size() > 0) {
            listTextValue.removeLast();
        }

    }

    /**
     * Returns the name of the current element. If no name in the list, a empty
     * string is returned.
     * 
     * @return Returns the name of the current element.
     */
    public String getCurrentElementName() {

        if (listElement.size() > 0) {
            return listElement.getLast();
        }
        return "";
    }

    /**
     * Returns the text of the current element. If no text in the list, a empty
     * string is returned.
     * 
     * @return Returns the name of the current element.
     */
    public String getCurrentTextValue() {

        if (listTextValue.size() > 0) {
            return listTextValue.getLast();
        }
        return "";
    }

    /**
     * Returns the full name of the current element.
     * 
     * @return Returns the full name of the current element.
     */
    public String getFullName() {

        StringBuffer buf = new StringBuffer();

        for (int i = 0, n = listElement.size(); i < n; i++) {
            buf.append("/");
            buf.append(listElement.get(i));
        }

        return buf.toString();
    }

    /**
     * Getter for level.
     * 
     * @return the level
     */
    public int getLevel() {

        return level;
    }

    /**
     * Check, if the element is parent of the current entry.
     * 
     * @param name The name of the parent.
     * @return Returns <code>true</code>, if the element is parent of the
     *         current element, otherwise <code>false</code>.
     */
    public boolean hasParentElement(String name) {

        for (int i = listElement.size() - 1; i >= 0; i--) {
            if (listElement.get(i).equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check, if the parser is in the element.
     * 
     * @param name The name of the element.
     * @return Returns <code>true</code>, if the parser parse the named
     *         element.
     */
    public boolean isInElement(String name) {

        return getCurrentElementName().equals(name);
    }

    /**
     * Getter for useWriter.
     * 
     * @return the useWriter
     */
    public boolean isUseWriter() {

        return useWriter;
    }

    /**
     * Setter for useWriter.
     * 
     * @param useWriter the useWriter to set
     */
    public void setUseWriter(boolean useWriter) {

        this.useWriter = useWriter;
    }

    /**
     * Setter for writer.
     * 
     * @param writer the writer to set
     */
    public void setWriter(XMLStreamWriter writer) {

        this.writer = writer;
    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    public void startDocument() throws SAXException {

        checkWriter();
        try {
            if (useWriter) {
                writer.writeStartDocument();
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }

    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

        checkWriter();
        level++;
        listElement.addLast(qName);
        listTextValue.addLast("");
        try {
            if (useWriter) {
                writer.writeStartElement(qName);
                writeAttr(attributes);
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    /**
     * Write the attributes.
     * 
     * @param attributes The attributes.
     * @throws IOException if a io-error occurred.
     */
    protected void writeAttr(Attributes attributes) throws IOException {

        if (attributes != null) {
            for (int i = 0, cnt = attributes.getLength(); i < cnt; i++) {
                writer.writeAttribute(attributes.getQName(i), attributes
                    .getValue(i));
            }
        }

    }

}
