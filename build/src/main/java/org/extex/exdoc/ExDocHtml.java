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
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
 * Extract doc tags from sources and translate them to HTML.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExDocHtml extends ExDocXml {

    /**
     * The field <tt>XSLT</tt> contains the name of the XSLT file to use.
     */
    private static final String XSLT = "org/extex/exdoc/xslt/xml2tml.xsl";

    /**
     * The main program for this class.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        try {
            new ExDocHtml().run(args);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * The field <tt>keys</tt> contains the ...
     */
    private List keys = new ArrayList();

    /**
     * Creates a new object.
     *
     * @throws ParserConfigurationException in case of an error
     */
    public ExDocHtml() throws ParserConfigurationException {

        super();
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
            content.replace(j, j + 1, "/>\n");
            content.replace(i, i + 5, "<img src=\"unimplemented.png\"");
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
        File file = new File(getOutput(),
                            "primitives.html");
        PrintStream prim =
                new PrintStream(new FileOutputStream(file));
        try {
            for (int i = 0; i < size; i++) {
                Key key = (Key) keys.get(i);
                if ("".equals(key.getTheClass())) {
                    prim.print("<a href=\"");
                    prim.print(key.getLocation());
                    prim.print("\">");
                    prim.print(key.getTheName());
                    prim.println("</a>");
                }
            }
        } finally {
            prim.close();
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param name the name of the resource currently processed
     * @param content the content to be shipped
     *
     * @throws IOException in case of an I/O error
     *
     * @see org.extex.exdoc.Exdoc#shipout(java.lang.String, java.lang.StringBuffer)
     */
    protected void shipout(final Key name, final StringBuffer content)
            throws IOException {

        FileOutputStream out =
                new FileOutputStream(new File(getOutput(), name + ".html"));

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
