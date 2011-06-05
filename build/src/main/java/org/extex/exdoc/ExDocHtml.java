/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import java.io.BufferedOutputStream;
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
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.extex.exdoc.util.Author;
import org.extex.exdoc.util.Copy;
import org.extex.exdoc.util.Key;
import org.extex.exdoc.util.Traverser;
import org.xml.sax.SAXException;

/**
 * Extract doc tags from sources and translate them to HTML.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5413 $
 */
public class ExDocHtml extends ExDocXml {

    /**
     * The field <tt>XSLT</tt> contains the name of the XSLT file to use.
     */
    private static final String XSLT = "org/extex/exdoc/xslt/xml2html.xsl";

    /**
     * The main program for this class.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger logger = Traverser.createLogger();

        try {
            new ExDocHtml(logger).run(args);
        } catch (Exception e) {
            logger.severe(e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

    /**
     * The field <tt>keys</tt> contains the list of keys.
     */
    private List<Key> keys = new ArrayList<Key>();

    /**
     * The field <tt>transformer</tt> contains the xslt engine.
     */
    private Transformer transformer;

    /**
     * Creates a new object.
     * 
     * @throws ParserConfigurationException in case of an error
     * @throws TransformerFactoryConfigurationError in case of an error
     * @throws TransformerConfigurationException in case of an error
     * @throws IOException in case of an error
     */
    public ExDocHtml()
            throws ParserConfigurationException,
                TransformerConfigurationException,
                TransformerFactoryConfigurationError,
                IOException {

        InputStream xslt =
                getClass().getClassLoader().getResourceAsStream(XSLT);
        if (xslt == null) {
            throw new FileNotFoundException(XSLT);
        }
        transformer =
                TransformerFactory.newInstance().newTransformer(
                    new StreamSource(xslt));
        xslt.close();
    }

    /**
     * Creates a new object.
     * 
     * @param logger the logger
     * 
     * @throws ParserConfigurationException in case of an error
     * @throws TransformerFactoryConfigurationError in case of an error
     * @throws TransformerConfigurationException in case of an error
     * @throws IOException in case of an error
     */
    public ExDocHtml(Logger logger)
            throws ParserConfigurationException,
                TransformerConfigurationException,
                TransformerFactoryConfigurationError,
                IOException {

        this();
        setLogger(logger);
    }

    /**
     * Replace a text line marked as "to do".
     * 
     * @param content the content to transform
     */
    private void replaceTodo(StringBuilder content) {

        for (int i = content.indexOf("TODO "); i >= 0; i =
                content.indexOf("TODO ", i + 5)) {
            int j = content.indexOf("\n", i);
            content.replace(j, j + 1, "/>\n");
            content.replace(i, i + 5, "<img src=\"unimplemented.png\"");
        }
    }

    /**
     * Process the list of resources and authors.
     * 
     * @param file the target file or <code>null</code>
     * @param authors the list of authors
     * 
     * @throws IOException in case of an I/O error
     */
    @Override
    protected void shipoutAuthors(String file, Map<String, Author> authors)
            throws IOException {

        if (file == null) {
            return;
        }

        File f = new File(getOutput(), file + ".html");
        PrintStream out =
                new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(f)));

        out.print("<authors>\n");
        for (String x : authors.keySet()) {
            out.print("  <resource name=\"");
            out.print(x);
            out.print("\">\n    <author email=\"");
            Author author = authors.get(x);
            out.print(author.getEmail()//
                .replaceAll("&", "&amp;")//
                .replaceAll("<", "&lt;")//
                .replaceAll(">", "&gt;"));
            out.print("\">");
            out.print(author.getName()//
                .replaceAll("&", "&amp;")//
                .replaceAll("<", "&lt;")//
                .replaceAll(">", "&gt;"));
            out.print("</author>\n  </resource>\n");
        }
        out.print("</authors>\n");
        out.close();
    }

    /**
     * Run with the command line arguments.
     * 
     * @param args the command line arguments
     * 
     * @throws Exception in case of an error
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.exdoc.ExDocXml#run(java.lang.String[])
     */
    @Override
    @SuppressWarnings("unchecked")
    public void run(String[] args) throws Exception {

        super.run(args);

        Collections.sort(keys, new Comparator<Key>() {

            /**
             * {@inheritDoc}
             * 
             * @see java.util.Comparator#compare(java.lang.Object,
             *      java.lang.Object)
             */
            public int compare(Key o1, Key o2) {

                return o1.toString().compareTo(o2.toString());
            }
        });
        int size = keys.size();
        File file = new File(getOutput(), "primitives.html");
        PrintStream prim = new PrintStream(new FileOutputStream(file));
        try {
            for (int i = 0; i < size; i++) {
                Key key = keys.get(i);
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

        Copy.mapped("org/extex/exdoc/html/index.html", //
            new File(getOutput(), "index.html"), //
            Collections.EMPTY_MAP);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exdoc.ExDocXml#shipout( org.extex.exdoc.util.Key,
     *      java.lang.StringBuilder)
     */
    @Override
    protected void shipout(Key name, StringBuilder content)
            throws IOException,
                SAXException {

        FileOutputStream out =
                new FileOutputStream(new File(getOutput(), name + ".html"));

        try {
            transformer.transform(new DOMSource(scan(content)),
                new StreamResult(out));

        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        // keys.add(name);
    }

}
