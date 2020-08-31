/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;

/**
 * Test for XMLStreamWriter.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class XMLStreamWriterTest extends TestCase {

    /**
     * The encoding.
     */
    private static final String ENCODING = "ISO-8859-1";

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testBeauty1() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.setBeauty(true);
        writer.setIndent(" ");
        writer.writeStartElement("root");
        writer.writeStartElement("xxx");
        writer.writeAttribute("key", "value");
        writer.writeAttribute("key2", "value2");
        writer.writeAttribute("key3", "value3");
        writer.writeEndElement();
        writer.writeEndElement();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<root>\n"
                + " <xxx key=\"value\" key2=\"value2\" key3=\"value3\"/>\n"
                + "</root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testBeauty2() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.setBeauty(true);
        writer.setIndent(" ");
        writer.writeStartElement("root");
        writer.writeStartElement("xxx");
        writer.writeAttribute("key", "value");
        writer.writeAttribute("key2", "value2");
        writer.writeAttribute("key3", "value3");
        writer.writeEndElement();
        writer.writeStartElement("yyy");
        writer.writeEndElement();
        writer.writeStartElement("yyy");
        writer.writeStartElement("a1");
        writer.writeStartElement("a2");
        writer.writeCharacters("Text");
        writer.writeEndElement();
        writer.writeEndElement();
        writer.writeEndElement();
        writer.writeEndElement();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<root>\n"
                + " <xxx key=\"value\" key2=\"value2\" key3=\"value3\"/>\n"
                + " <yyy/>\n" + " <yyy>\n" + "  <a1>\n" + "   <a2>\n"
                + "    Text\n" + "   </a2>\n" + "  </a1>\n" + " </yyy>\n"
                + "</root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testElement1() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartElement("root");
        writer.writeStartElement("xxx");
        writer.writeAttribute("key", "value");
        writer.writeAttribute("key2", "value2");
        writer.writeAttribute("key3", "value3");
        writer.writeEndElement();
        writer.writeEndElement();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals(
            "<root><xxx key=\"value\" key2=\"value2\" key3=\"value3\"/></root>",
            xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testElement2() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.setDefaultNamespace("svg");
        writer.writeStartElement("root");
        writer.writeEndElement();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<svg:root/>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testElement3() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.setDefaultNamespace("svg");
        writer.writeStartElement("root");
        writer.writeStartElement("xxx");
        writer.writeEndElement();
        writer.writeEndElement();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<svg:root><svg:xxx/></svg:root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testElement4() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.setDefaultNamespace("svg");
        writer.writeStartElement("root");
        writer.writeStartElement("html", "body");
        writer.writeComment("Beispiel");
        writer.writeEndElement();
        writer.writeEndElement();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals(
            "<svg:root><html:body><!-- Beispiel --></html:body></svg:root>",
            xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testElement5() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.setDefaultNamespace("svg");
        writer.writeStartElement("root");
        writer.writeStartElement("html", "body");
        writer.writeCharacters("Beispiel");
        writer.writeEndElement();
        writer.writeEndElement();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<svg:root><html:body>Beispiel</html:body></svg:root>",
            xml);

    }

    /**
     * Test the document header.
     * 
     * @throws IOException if an error occurs.
     */
    public void testHeader() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartDocument();
        writer.writeEndDocument();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();

        assertEquals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>", xml);

    }

    /**
     * Test the document header.
     * 
     * @throws IOException if an error occurs.
     */
    public void testHeaderRoot() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartDocument();
        writer.writeStartElement("root");
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.close();

        String xml = (new String(out.toByteArray(), ENCODING)).trim();

        assertEquals(
            "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<root/>", xml);

    }

    /**
     * Test the document header.
     * 
     * @throws IOException if an error occurs.
     */
    public void testHeaderRootE1() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        try {
            writer.writeStartDocument();
            writer.writeStartElement("root");
            // writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();

        } catch (IOException e) {

          assertTrue( e.getMessage().startsWith( "invalid struktur" ) );
            return;
        }
        assertTrue(false);
    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testText1() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartElement("root");
        writer.writeCharacters("Dies ist ein Text!");
        writer.writeEndElement();
        writer.close();
        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<root>Dies ist ein Text!</root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testText2() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartElement("root");
        writer.writeCharacters("Sonderzeichen <");
        writer.writeEndElement();
        writer.close();
        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<root>Sonderzeichen &lt;</root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testText3() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartElement("root");
        writer.writeCharacters("Sonderzeichen >");
        writer.writeEndElement();
        writer.close();
        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<root>Sonderzeichen &gt;</root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testText4() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartElement("root");
        writer.writeCharacters("Sonderzeichen &");
        writer.writeEndElement();
        writer.close();
        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<root>Sonderzeichen &amp;</root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testText5() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartElement("root");
        writer.writeCharacters("Sonderzeichen \"");
        writer.writeEndElement();
        writer.close();
        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<root>Sonderzeichen &quot;</root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testText6() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartElement("root");
        writer.writeCharacters("Sonderzeichen '");
        writer.writeEndElement();
        writer.close();
        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals("<root>Sonderzeichen &apos;</root>", xml);

    }

    /**
     * Test the element.
     * 
     * @throws IOException if an error occurs.
     */
    public void testText7() throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);

        writer.writeStartElement("root");
        writer
            .writeCharacters("Umlaute: \u00f6\u00e4\u00fc\u00df \u00d6\u00c4\u00dc");
        writer.writeEndElement();
        writer.close();
        String xml = (new String(out.toByteArray(), ENCODING)).trim();
        assertEquals(
            "<root>Umlaute: \u00f6\u00e4\u00fc\u00df \u00d6\u00c4\u00dc</root>",
            xml);

    }

    /**
     * Test read and write.
     * 
     * @throws Exception if an error occurred.
     */
    public void testRead01() throws Exception {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            XMLStreamWriter writer = new XMLStreamWriter(out, ENCODING);
            writer.setBeauty(true);
            writer.setRemoveWhiteSpace(true);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XmlHandler handler = new XmlHandler(writer);
            parser
                .parse(
                    new File(
                        "../ExTeX-Font-API/src/main/resources/org/extex/util/xml/xmltest1.xml"),
                    handler);

            writer.close();
            // String xml = (new String(out.toByteArray(), ENCODING)).trim();
            // System.out.println(xml);

            assertTrue(true);

        } catch (Exception e) {
            assertTrue(false);
        }
    }

}
