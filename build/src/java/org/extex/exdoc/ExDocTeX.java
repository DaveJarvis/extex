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

package org.extex.exdoc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.extex.exdoc.util.Key;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExDocTeX extends ExDocXml {

    /**
     * The field <tt>ENTITY_MAP</tt> contains the ...
     */
    private static final Map ENTITY_MAP = new HashMap();

    /**
     * The field <tt>XSLT</tt> contains the ...
     */
    private static final String XSLT = "org/extex/exdoc/xslt/xml2tex.xsl";

    static {
        ENTITY_MAP.put("amp", "&amp;");
        ENTITY_MAP.put("gt", "&gt;");
        ENTITY_MAP.put("lt", "&lt;");
        ENTITY_MAP.put("nbsp", "~");
        ENTITY_MAP.put("lang", "\\tag{");
        ENTITY_MAP.put("rang", "}");
        ENTITY_MAP.put("larr", "\\ensuremath{\\leftarrow}");
        ENTITY_MAP.put("rarr", "\\ensuremath{\\rightarrow}");
        ENTITY_MAP.put("tilde", "\\~{}");
        ENTITY_MAP.put("ndash", "--");
        ENTITY_MAP.put("#x5c", "\\");
    }

    /**
     * The main program for this class.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        try {
            new ExDocTeX().run(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String primitveFile = null;

    private String syntaxFile = null;

    /**
     * The field <tt>keys</tt> contains the ...
     */
    private List keys = new ArrayList();

    /**
     * Creates a new object.
     *
     * @throws ParserConfigurationException in case of an error
     */
    public ExDocTeX() throws ParserConfigurationException {

        super();
    }

    /**
     * Replace all strings of a given form into something new.
     *
     * @param content the content to transform
     * @param from the string to be replaced
     * @param to the new sring to be inserted
     */
    private void replace(final StringBuffer content, final String from,
            final String to) {

        int length = from.length();

        for (int i = content.indexOf(from); i >= 0; i =
                content.indexOf(from, i + to.length())) {
            content.replace(i, i + length, to);
        }
    }

    /**
     * Replace HTML entities of the form <tt>&amp;&lang;name&rang;;</tt> to
     * appropriate values.
     *
     * @param content the content to transform
     * @param name the name of te resource currently processed
     */
    private void replaceEntities(final StringBuffer content, final Key name) {

        int j;
        for (int i = content.indexOf("&"); i >= 0; i = content.indexOf("&", j)) {
            j = content.indexOf(";", i);
            String entity = content.substring(i + 1, j);
            String to;
            if (entity.charAt(0) == '#') {
                int c = Integer.parseInt("0x" + entity.substring(1));
                to = Character.toString((char) c);
            } else {
                to = (String) ENTITY_MAP.get(entity);
                if (to == null) {
                    warning(name.getLocation() + ": Unknown entity " + entity);
                    to = "???";
                }
            }
            content.replace(i, j + 1, to);
            j = i + to.length();
        }

    }

    /**
     * Replace a text line marked as "to do".
     *
     * @param content the content to transform
     */
    private void replaceTodo(final StringBuffer content) {

        for (int i = content.indexOf("TODO "); i >= 0; i =
                content.indexOf("TODO ", i + 5)) {
            int j = content.indexOf("\n", i);
            content.replace(j, j + 1, "}\n");
            content.replace(i, i + 5, "\\ToDo{");
        }
    }

    /**
     * Run with the command line arguments.
     *
     * @param args the command line arguments
     *
     * @throws Exception in case of an error
     *
     * @see org.extex.exdoc.Exdoc#run(java.lang.String[])
     */
    public void run(final String[] args) throws Exception {

        super.run(args);

        Collections.sort(keys, new Comparator() {

            /**
             * Compare two objects.
             *
             * @param o1 the first object
             * @param o2 the second object
             *
             * @return ...
             *
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare(final Object o1, final Object o2) {

                if (o1 instanceof Key && o2 instanceof Key) {
                    String s1 = ((Key) o1).toString();
                    String s2 = ((Key) o2).toString();
                    return s1.compareTo(s2);
                }
                throw new IllegalArgumentException();
            }
        });
        int size = keys.size();
        for (int i = 0; i < size; i++) {
            Key key = (Key) keys.get(i);
            System.err.println(key.toString());
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param name th name of the resource currently processed
     * @param content the content to be shipped
     *
     * @throws IOException in case of an I/O error
     *
     * @see org.extex.exdoc.Exdoc#shipout(java.lang.String, java.lang.StringBuffer)
     */
    protected void shipout(final Key name, final StringBuffer content)
            throws IOException {

        FileOutputStream out =
                new FileOutputStream(new File(getOutput(), name + ".tex"));

        replace(content, "\\", "\\textbackslash{}");
        replace(content, "$", "\\$");
        replace(content, "_", "\\_$");
        replace(content, "~", "\\~{}");
        replace(content, "%", "\\%");

        replace(content, "<tt>\\", "<tt class=\"macro\">\\");

        replaceEntities(content, name);
        replaceTodo(content);

        try {
            InputStream xslt =
                    getClass().getClassLoader().getResourceAsStream(XSLT);
            if (xslt == null) {
                throw new FileNotFoundException(XSLT);
            }
            Transformer t =
                    TransformerFactory.newInstance().newTransformer(
                        new StreamSource(xslt));
            xslt.close();

            InputStream in =
                    new ByteArrayInputStream(content.toString().getBytes());

            t.transform(new StreamSource(in), new StreamResult(out));

        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        keys.add(name);
    }

}
