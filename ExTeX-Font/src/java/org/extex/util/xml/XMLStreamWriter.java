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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;

/**
 * A writer, which write xml-elements, attributes and so on to an output stream.
 * <p>
 * only xml version 1.0
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */

public class XMLStreamWriter {

    /**
     * The stack for the elements.
     */
    private class Stack {

        /**
         * The values.
         */
        public class Values {

            /**
             * append.
             */
            private boolean append;

            /**
             * The element.
             */
            private String element;

            /**
             * The name space.
             */
            private String namespace;

            /**
             * Create a new object.
             * 
             * @param el the element
             */
            public Values(String el) {

                element = el.trim();
                append = false;
                namespace = null;
            }

            /**
             * Create a new object.
             * 
             * @param ns the name space
             * @param el the element
             */
            public Values(String ns, String el) {

                element = el.trim();
                append = false;
                namespace = ns;
            }

            /**
             * Returns the element.
             * 
             * @return Returns the element.
             */
            public String getElement() {

                return element;
            }

            /**
             * Returns the name space.
             * 
             * @return Returns the name space.
             */
            public String getNameSpace() {

                return namespace;
            }

            /**
             * Returns the append.
             * 
             * @return Returns the append.
             */
            public boolean isAppend() {

                return append;
            }

            /**
             * Set the append.
             * 
             * @param app The append to set.
             */
            public void setAppend(boolean app) {

                append = app;
            }
        }

        /**
         * The stack.
         */
        private LinkedList istack = new LinkedList();

        /**
         * Add a element.
         * 
         * @param element the element
         */
        public void add(String element) {

            istack.addLast(new Values(element));
        }

        /**
         * Add a element.
         * 
         * @param ns the namespace
         * @param element the element
         */
        public void add(String ns, String element) {

            istack.addLast(new Values(ns, element));
        }

        /**
         * Get a element name.
         * 
         * @return Returns the name of the element.
         */
        public String get() {

            return ((Values) istack.getLast()).getElement();
        }

        /**
         * Get a element namespace.
         * 
         * @return Returns the name of the element.
         */
        public String getNameSpace() {

            return ((Values) istack.getLast()).getNameSpace();
        }

        /**
         * Check, if the element is in append mode.
         * 
         * @return Returns <code>true</code>, if the elements have children
         *         or text.
         */
        public boolean isAppend() {

            if (size() > 0) {
                return ((Values) istack.getLast()).isAppend();
            }
            return false;
        }

        /**
         * Remove a element.
         * 
         * @return Returns the name of the element.
         */
        public String remove() {

            return ((Values) istack.removeLast()).getElement();
        }

        /**
         * Set the append value.
         */
        public void setAppend() {

            if (size() > 0) {
                ((Values) istack.getLast()).setAppend(true);
            }
        }

        /**
         * Returns the size of the stack.
         * 
         * @return Returns the size of the stack.
         */
        public int size() {

            return istack.size();
        }
    }

    /**
     * The buffer size.
     */
    private static final int BUFFERSIZE = 0xffff;

    /**
     * The length of one entry.
     */
    private static final int ENTRY_LENGTH = 7;

    /**
     * Entries each line.
     */
    private static final int ENTRY_LINES = 8;

    /**
     * The length of a short value.
     */
    private static final int SHORT_LENGTH = 4;

    /**
     * Beautify the output?
     */
    private boolean beauty = false;

    /**
     * The default name space.
     */
    private String defaultns = null;

    /**
     * Document open?
     */
    private boolean docopen = false;

    /**
     * Element are opened?
     */
    private boolean elementopen = false;

    /**
     * The encoding.
     */
    private String encoding;

    /**
     * Indent string.
     */
    private String indent = "   ";

    /**
     * is the last operation a newline?
     */
    private boolean nlset = false;

    /**
     * Formatter for the double attribute value.
     */
    private NumberFormat numberformat;

    /**
     * The writer for the output.
     */
    private Writer out;

    /**
     * The public id (doctype).
     */
    private String publicID;

    /**
     * The name of the root element (for doctype).
     */
    private String rootName;

    /**
     * The stack for the elements.
     */
    private Stack stack = new Stack();

    /**
     * The system id (doctpye).
     */
    private String systemID;

    /**
     * Create a new object.
     * 
     * @param sout The output.
     * @param enc The encoding
     * @throws IOException if an error occurs.
     */
    public XMLStreamWriter(OutputStream sout, String enc) throws IOException {

        super();
        numberformat = NumberFormat.getInstance(Locale.US);
        numberformat.setGroupingUsed(false);
        numberformat.setMinimumFractionDigits(0);
        numberformat.setMinimumFractionDigits(4);
        if (numberformat instanceof DecimalFormat) {
            DecimalFormat f = (DecimalFormat) numberformat;
            f.applyLocalizedPattern("0.#");
            f.setDecimalSeparatorAlwaysShown(false);
        }
        out = new BufferedWriter(new OutputStreamWriter(sout, enc), BUFFERSIZE);
        encoding = enc;

    }

    /**
     * Close the output.
     * 
     * @throws IOException if an error occurs.
     */
    public void close() throws IOException {

        out.close();
        if (stack.size() != 0) {
            throw new IOException("invalid struktur: depth=" + stack.size());
        }

    }

    /**
     * Close the element.
     * 
     * @throws IOException if an error occurs.
     */
    private void closeElement() throws IOException {

        if (elementopen) {
            out.write(">");
            printNewLine();
        }
        elementopen = false;
    }

    /**
     * Create a new text with entities.
     * 
     * @param text The text.
     * @return Returns the text with entities.
     */
    private String createEntity(String text) {

        StringBuffer buf = new StringBuffer(text.length());

        for (int i = 0, n = text.length(); i < n; i++) {
            char c = text.charAt(i);
            switch (c) {
                case '<':
                    buf.append("&lt;");
                    break;
                case '>':
                    buf.append("&gt;");
                    break;
                case '&':
                    buf.append("&amp;");
                    break;
                case '"':
                    buf.append("&quot;");
                    break;
                case '\'':
                    buf.append("&apos;");
                    break;
                default:
                    buf.append(c);
                    break;
            }
        }

        return buf.toString();
    }

    /**
     * Flush the buffer to the output.
     * 
     * @throws IOException if an error occurs.
     */
    public void flush() throws IOException {

        out.flush();
    }

    // --------------------------------------------------
    /**
     * Returns the default namespace.
     * 
     * @return Returns the defaultns.
     */
    public String getDefaultNamespace() {

        return defaultns;
    }

    /**
     * Write a newline to the output.
     * 
     * @throws IOException if an error occurs.
     */
    public void newLine() throws IOException {

        out.write('\n');
    }

    /**
     * Print a indent, if beauty is set.
     * 
     * @throws IOException if an error occurs.
     */
    private void printIndent() throws IOException {

        printIndent(stack.size());
    }

    /**
     * Print a indent, if beauty is set.
     * 
     * @param cnt the count of the depth
     * @throws IOException if an error occurs.
     */
    private void printIndent(int cnt) throws IOException {

        if (beauty) {
            for (int i = 0; i < cnt; i++) {
                out.write(indent);
            }
        }
    }

    /**
     * Print a indent in a string buffer, if beauty is set.
     * 
     * @param buf The string buffer.
     * @throws IOException if an error occurs.
     */
    private void printIndent(StringBuffer buf) throws IOException {

        if (beauty) {
            for (int i = 0, cnt = stack.size(); i < cnt; i++) {
                buf.append(indent);
            }
        }
    }

    /**
     * Print a newline, if beauty is set.
     * 
     * @throws IOException if an error occurs.
     */
    private void printNewLine() throws IOException {

        if (beauty) {
            out.write("\n");
            nlset = true;
        }
    }

    /**
     * Set the beauty.
     * 
     * @param b The beauty to set.
     * @throws IOException if an error occurs.
     */
    public void setBeauty(boolean b) throws IOException {

        if (!docopen) {
            beauty = b;
        } else {
            throw new IOException("only before writeStartDocument()!");
        }
    }

    /**
     * Set the default namespace for elements.
     * 
     * @param ns The defaultns to set.
     * @throws IOException if an error occurs.
     */
    public void setDefaultNamespace(String ns) throws IOException {

        if (!docopen) {
            defaultns = ns;
        } else {
            throw new IOException("only before writeStartDocument()!");
        }
    }

    /**
     * Set the doctpye definition.
     * 
     * @param rootn The root element.
     * @param publicId The public id.
     * @param systemId The system id.
     * @throws IOException if an IO-error occurred.
     */
    public void setDocType(String rootn, String publicId, String systemId)
            throws IOException {

        if (!docopen) {
            rootName = rootn;
            publicID = publicId;
            systemID = systemId;
        } else {
            throw new IOException("only before writeStartDocument()!");
        }

    }

    /**
     * Set the indent.
     * 
     * @param i The indent to set.
     * @throws IOException if an error occurs.
     */
    public void setIndent(String i) throws IOException {

        if (!docopen) {
            if (indent != null) {
                indent = i;
            }
        } else {
            throw new IOException("only before writeStartDocument()!");
        }

    }

    /**
     * Setter for numberformat.
     * 
     * @param nformat The numberformat to set.
     */
    public void setNumberformat(NumberFormat nformat) {

        numberformat = nformat;
    }

    /**
     * Convert a long value to a hex string with the specified length.
     * 
     * @param val The vlaue.
     * @param length The length of the hex string.
     * @return Returns the hex value of the long value.
     */
    private String toHex(long val, int length) {

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append("0");
        }
        buf.append(Long.toHexString(val));
        StringBuffer hex = new StringBuffer();
        hex.append("0x");
        hex.append(buf.substring(buf.length() - length));

        return hex.toString();
    }

    /**
     * Convert a int-value to a hex string with x digits.
     * 
     * @param value The int value.
     * @param digits The digits.
     * @return Returns a hex string.
     */
    private String toHexString(int value, int digits) {

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            buf.append("0");
        }
        buf.append(Integer.toHexString(value));
        return buf.substring(buf.length() - digits);
    }

    /**
     * Write a attribute to the element.
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @throws IOException if an error occurs.
     */
    public void writeAttribute(String name, boolean value) throws IOException {

        writeAttribute(name, String.valueOf(value));
    }

    /**
     * Write a attribute to the element.
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @throws IOException if an error occurs.
     */
    public void writeAttribute(String name, double value) throws IOException {

        writeAttribute(name, String.valueOf(value));
    }

    /**
     * Write a attribute to the element.
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @throws IOException if an error occurs.
     */
    public void writeAttribute(String name, long value) throws IOException {

        writeAttribute(name, String.valueOf(value));
    }

    /**
     * Write a attribute to the element. The long-value is print a a hex string.
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @param length The length of the hex string.
     * @throws IOException if an error occurs.
     */
    public void writeAttribute(String name, long value, int length)
            throws IOException {

        writeAttribute(name, toHex(value, length));
    }

    /**
     * Write a attribute to the element.
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @throws IOException if an error occurs.
     */
    public void writeAttribute(String name, Object value) throws IOException {

        writeAttribute(name, value.toString());
    }

    /**
     * Write a attribute to the element.
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @throws IOException if an error occurs.
     */
    public void writeAttribute(String name, String value) throws IOException {

        writeAttribute(null, name, value);
    }

    /**
     * Write a attribute (with name space) to the element.
     * 
     * @param ns The name space.
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @throws IOException if an error occurs.
     */
    public void writeAttribute(String ns, String name, String value)
            throws IOException {

        if (elementopen) {
            out.write(" ");
            if (ns != null && ns.length() > 0) {
                out.write(ns + ":");
            }
            out.write(name.trim());
            out.write("=\"");
            out.write(createEntity(value));
            out.write("\"");
        } else {
            throw new IOException("Only after writeStartElement()!");
        }
    }

    /**
     * Write a byte array with hex values to the output.
     * 
     * @param bytes The byte array.
     * @throws IOException if an error occurs.
     */
    public void writeByteArray(byte[] bytes) throws IOException {

        StringBuffer buf = new StringBuffer(bytes.length * ENTRY_LENGTH);
        for (int i = 0; i < bytes.length; i++) {
            buf.append("0x").append(toHexString(bytes[i], 2)).append(" ");
            if (beauty && i % ENTRY_LINES == ENTRY_LINES - 1) {
                buf.append("\n");
                printIndent(buf);
            }
        }
        writeCharacters(buf.toString());
    }

    /**
     * Write a byte array as entries (hex).
     * 
     * @param bytes The byte array.
     * @throws IOException if an error occurs.
     */
    public void writeByteArrayAsEntries(byte[] bytes) throws IOException {

        for (int i = 0; i < bytes.length; i++) {
            writeStartElement("entry");
            writeAttribute("id", i);
            writeAttribute("value", "0x" + toHexString(bytes[i], 2));
            writeEndElement();
        }
    }

    /**
     * Write a CDATA string.
     * 
     * @param array The CDATA as array.
     * @throws IOException if an error occurs.
     */
    public void writeCDATA(byte[] array) throws IOException {

        writeCDATA(new String(array));
    }

    /**
     * Write a cdata string.
     * 
     * @param text The cdata string.
     * @throws IOException if an error occurs.
     */
    public void writeCDATA(String text) throws IOException {

        closeElement();
        stack.setAppend();
        printIndent();
        out.write("<![CDATA[\n");
        out.write(text);
        out.write("\n]]>");
        printNewLine();
    }

    /**
     * Write characters to the output.
     * 
     * @param text The text
     * @throws IOException if an error occurs.
     */
    public void writeCharacters(String text) throws IOException {

        closeElement();
        stack.setAppend();
        if (nlset) {
            printIndent();
        }
        out.write(createEntity(text));
        nlset = false;
    }

    /**
     * Write a comment.
     * 
     * @param text The comment.
     * @throws IOException if an error occurs.
     */
    public void writeComment(String text) throws IOException {

        closeElement();
        stack.setAppend();
        printIndent();
        out.write("<!-- ");
        out.write(text);
        out.write(" -->");
        printNewLine();
    }

    /**
     * Write the end of the document.
     * <p>
     * It makes a flush()
     * </p>
     * 
     * @throws IOException if an error occurs.
     */
    public void writeEndDocument() throws IOException {

        closeElement();
        out.flush();
        if (stack.size() != 0) {
            throw new IOException("invalid struktur: depth=" + stack.size());
        }
    }

    /**
     * Write the end element to the output.
     * 
     * @throws IOException if an error occurs.
     */
    public void writeEndElement() throws IOException {

        if (stack.size() > 0) {
            if (stack.isAppend()) {
                closeElement();
                if (!nlset) {
                    printNewLine();
                }
                printIndent(stack.size() - 1);
                out.write("</");
                String ns = stack.getNameSpace();
                if (ns != null) {
                    out.write(ns);
                    out.write(':');
                }
                out.write(stack.get());
                out.write(">");
                printNewLine();
            } else {
                out.write("/>");
                elementopen = false;
                nlset = false;
            }
            stack.remove();

        } else {
            throw new IOException("no start element!");
        }
    }

    /**
     * Write a attribute to the element and use the formatter.
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @throws IOException if an error occurs.
     */
    public void writeFormatAttribute(String name, double value)
            throws IOException {

        writeAttribute(name, numberformat.format(value));
    }

    /**
     * Write a int array as entries (hex).
     * 
     * @param array The int array.
     * @throws IOException if an error occurs.
     */
    public void writeIntArrayAsEntries(int[] array) throws IOException {

        for (int i = 0; i < array.length; i++) {
            writeStartElement("entry");
            writeAttribute("id", i);
            writeAttribute("value", "0x" + toHexString(array[i], 8));
            writeEndElement();
        }
    }

    /**
     * Write a short array with hex values to the output.
     * 
     * @param shorts The short array.
     * @throws IOException if an error occurs.
     */
    public void writeShortArray(short[] shorts) throws IOException {

        StringBuffer buf = new StringBuffer(shorts.length * ENTRY_LENGTH);

        for (int i = 0; i < shorts.length; i++) {
            buf.append("0x").append(toHexString(shorts[i], SHORT_LENGTH))
                .append(" ");
            if (beauty && i % ENTRY_LINES == ENTRY_LINES - 1) {
                buf.append("\n");
                printIndent(buf);
            }
        }
        writeCharacters(buf.toString());
    }

    /**
     * Write the start of the document.
     * <p>
     * A newline is write after the header, without attend the beauty-flag.
     * </p>
     * 
     * @throws IOException if an error occurs.
     */
    public void writeStartDocument() throws IOException {

        out.write("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n");
        if (rootName != null && publicID != null && systemID != null) {
            out.write("<!DOCTYPE ");
            out.write(rootName);
            out.write(" PUBLIC \"");
            out.write(publicID);
            out.write("\" \"");
            out.write(systemID);
            out.write("\">\n");
        }
        docopen = true;
    }

    /**
     * Write the start element to the output.
     * 
     * @param element the element
     * @throws IOException if an error occurs.
     */
    public void writeStartElement(String element) throws IOException {

        writeStartElement(defaultns, element);
    }

    /**
     * Write the start element to the output.
     * 
     * @param ns the namespace
     * @param element the element
     * @throws IOException if an error occurs.
     */
    public void writeStartElement(String ns, String element) throws IOException {

        closeElement();
        if (!nlset) {
            printNewLine();
        }
        printIndent();
        stack.setAppend();
        stack.add(ns, element);
        out.write("<");
        if (ns != null) {
            out.write(ns);
            out.write(':');
        }
        out.write(stack.get());
        elementopen = true;
    }

}
