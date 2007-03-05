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
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.extex.exdoc.util.ExdocEntityResolver;
import org.extex.exdoc.util.Key;
import org.extex.exdoc.util.Traverser;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Collect the doc snippets from Java code and store them in XML files.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExDocXml extends Traverser {

    /**
     * The main program for this class.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        try {
            new ExDocXml().run(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * The field <tt>builder</tt> contains the document builder for parsing the
     * XML file.
     */
    private DocumentBuilder builder;

    /**
     * The field <tt>collecting</tt> contains the indicator that the first
     * phase is currently active.
     */
    private boolean collecting;

    /**
     * The field <tt>defaultOutputDirectory</tt> contains the ...
     */
    private String defaultOutputDirectory = "target/xxx";

    /**
     * The field <tt>keys</tt> contains the mapping from short form
     * (package.class_method) to a long form including the name. This is only
     * valid after the collecting pass has been finished.
     */
    private Map keys = new HashMap();

    /**
     * Creates a new object.
     *
     * @throws ParserConfigurationException  ...
     *
     */
    public ExDocXml() throws ParserConfigurationException {

        super();
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        builder.setEntityResolver(new ExdocEntityResolver());
    }

    /**
     * Getter for defaultOutputDirectory.
     *
     * @return the defaultOutputDirectory
     */
    protected String getDefaultOutputDirectory() {

        return defaultOutputDirectory;
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param a the map of attributes
     *
     * @return ...
     */
    private Key makeKey(final Map a) {

        Key key = new Key(a);

        if (collecting) {
            keys.put(key.getLocation(), key);
        }
        return key;
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param key ...
     * @param content ...
     * @throws Exception ...
     *
     * @see org.extex.exdoc.util.Traverser#out(java.lang.String, StringBuffer)
     */
    protected void out(final String key, final StringBuffer content)
            throws Exception {

        Map a = new HashMap();
        try {
            Element root = scan(content);
            a.put("name", "");
            a.put("package", "");
            NamedNodeMap attributes = root.getAttributes();
            int n = attributes.getLength();
            for (int i = 0; i < n; i++) {
                Node item = attributes.item(i);
                a.put(item.getNodeName(), item.getNodeValue());
            }
        } catch (SAXException e) {
            throw new Exception(key + ": " + e.getMessage());
        } catch (IOException e) {
            throw new Exception(key + ": " + e.getMessage());
        }

        Key k = makeKey(a); // don't refactor: this has side effects

        if (!collecting) {
            resolveLink(content, "{@linkplain", "<tt>", "</tt>", k);
            resolveLink(content, "{@link", "", "", k);

            shipout(k, content);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param content the content to resolve the links in
     * @param start the name of the link, i.e. <tt>{@link</tt> or
     *   <tt>{@linkplain</tt>
     * @param startTag the additional tag to insert at the start
     * @param endTag the additional tag to insert at the end
     * @param k the name of resource where the original text came from
     */
    protected void resolveLink(final StringBuffer content, final String start,
            final String startTag, final String endTag, final Key k) {

        for (int i = content.indexOf(start); i >= 0; i = content.indexOf(start)) {
            int j = content.indexOf("}", i);
            String s = content.substring(i + start.length(), j).trim().//
                replaceAll("\\s+", " ");
            int is = s.indexOf(' ');
            int ib = s.indexOf(')');
            String ref;
            String text;
            if (ib >= 0) {
                ref = s.substring(0, ib + 1);
                text = s.substring(ib + 2).trim();
            } else {
                ref = s.substring(0, is);
                text = s.substring(is + 1);
            }
            String x = tuneKey(ref.replaceAll("\\(.*", "").replace('#', '_'));
            String href = (String) keys.get(x);
            if (href != null) {
                content.replace(i, j + 1, startTag + "<a href=\"" + href
                        + "\">" + text + "</a>" + endTag);
            } else {
                content.replace(i, j + 1, startTag + text + endTag);
                warning(k.getLocation() + ": Unable to resolve link "
                        + ref);
            }
        }
    }

    /**
     * Run with the command line arguments.
     *
     * @param args the command line arguments
     *
     * @throws Exception in case of an error
     *
     * @see org.extex.exdoc.util.Traverser#run(java.lang.String[])
     */
    public void run(final String[] args) throws Exception {

        collecting = true;
        super.run(args);
        collecting = false;
        super.run(args);
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param cs ...
     *
     * @return ...
     *
     * @throws IOException ...
     * @throws SAXException ...
     */
    protected Element scan(final CharSequence cs)
            throws SAXException,
                IOException {

        StringBuffer sb =
                new StringBuffer(
                    "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
                            + "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\""
                            + " \"http://www.w3.org/TR/html4/loose.dtd\">");
        sb.append(cs.toString());
        Element root =
                builder.parse(
                    new ByteArrayInputStream(sb.toString().getBytes()))
                    .getDocumentElement();
        return root;
    }

    /**
     * Setter for defaultOutputDirectory.
     *
     * @param defaultOutputDirectory the defaultOutputDirectory to set
     */
    protected void setDefaultOutputDirectory(final String defaultOutputDirectory) {

        this.defaultOutputDirectory = defaultOutputDirectory;
    }

    /**
     * Ship the given content to the appropriate file.
     *
     * @param key the name of the resource
     * @param content the output stream in form of a string buffer
     *
     * @throws IOException in case of an I/O error
     */
    protected void shipout(final Key key, final StringBuffer content)
            throws IOException {

        content.insert(0, "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n");
        String s =
                content.toString().replaceAll("TODO missing documentation",
                    "<img src=\"unimplemented.png\">");

        String outDir = getOutput();
        if (outDir == null) {
            outDir = defaultOutputDirectory;
        }

        File file = new File(outDir, key + ".xml");
        FileWriter os = new FileWriter(file);
        os.write(s);
        os.close();
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param p ...
     *
     * @return ...
     */
    protected String tuneKey(final String p) {

        return p.replaceFirst("^org.extex.", "");
    }

}
