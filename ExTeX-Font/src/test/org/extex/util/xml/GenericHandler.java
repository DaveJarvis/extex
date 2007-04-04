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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.lowagie.text.xml.XmlWriter;

/**
 * Generic Handler.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 0001 $
 */
public class GenericHandler extends DefaultHandler {

    /**
     * The {@link XmlWriter} for the output.
     */
    private XMLStreamWriter writer;

    /**
     * Creates a new object.
     * 
     * @param writer The {@link XmlWriter} for the output.
     */
    public GenericHandler(XMLStreamWriter writer) {

        this.writer = writer;

    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        try {
            writer.writeCharacters(ch, start, length);
        } catch (IOException e) {
            throw new SAXException(e);
        }

    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    public void endDocument() throws SAXException {

        try {
            writer.writeEndDocument();
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

        try {
            writer.writeEndElement();
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    public void startDocument() throws SAXException {

        try {
            writer.writeStartDocument();
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

        try {
            writer.writeStartElement(qName);
            writeAttr(attributes);
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
    private void writeAttr(Attributes attributes) throws IOException {

        if (attributes != null) {
            int cnt = attributes.getLength();
            for (int i = 0; i < cnt; i++) {
                String name = attributes.getQName(i);
                String value = attributes.getValue(i);
                writer.writeAttribute(name, value);
            }
        }

    }

}
