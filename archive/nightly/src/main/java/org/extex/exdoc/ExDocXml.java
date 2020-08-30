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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.extex.exdoc.util.Author;
import org.extex.exdoc.util.Key;
import org.extex.exdoc.util.PrimitiveInfo;
import org.extex.exdoc.util.Traverser;
import org.extex.exdoc.util.UnitInfo;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Collect the doc snippets from Java code and store them in XML files.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ExDocXml extends Traverser {

    /**
     * The main program for this class.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger logger = Traverser.createLogger();

        try {
            new ExDocXml(logger).run(args);
        } catch (Exception e) {
            logger.severe(e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

    /**
     * The field {@code authorsFile} contains the file for the primitives.
     */
    private String authorsFile = "authors";

    /**
     * The field {@code collecting} contains the indicator that the first
     * phase is currently active.
     */
    private boolean collecting;

    /**
     * The field {@code keys} contains the mapping from short form
     * (package.class_method) to a long form including the name. This is only
     * valid after the collecting pass has been finished.
     */
    private Map<String, Key> keys = new HashMap<String, Key>();

    /**
     * The field {@code primitives} contains the ...
     */
    private List<PrimitiveInfo> primitives = new ArrayList<PrimitiveInfo>();

    /**
     * The field {@code primitivesFile} contains the file for the primitives.
     */
    private String primitivesFile = "primitives";

    /**
     * The field {@code units} contains the ...
     */
    private List<UnitInfo> units = new ArrayList<UnitInfo>();

    /**
     * Creates a new object.
     * 
     * @throws ParserConfigurationException in case of an error
     */
    public ExDocXml() throws ParserConfigurationException {

    }

    /**
     * Creates a new object.
     * 
     * @param logger the logger
     * 
     * @throws ParserConfigurationException in case of an error
     */
    public ExDocXml(Logger logger) throws ParserConfigurationException {

        this();
        setLogger(logger);
    }

    /**
     * Getter for authorsFile.
     * 
     * @return the authorsFile
     */
    public String getAuthorsFile() {

        return authorsFile;
    }

    /**
     * Getter for primitivesFile.
     * 
     * @return the primitivesFile
     */
    public String getPrimitivesFile() {

        return primitivesFile;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param a the map of attributes
     * 
     * @return ...
     */
    private Key makeKey(Map<String, String> a) {

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
     * 
     * @throws Exception in case of an error
     */
    protected void out(String key, StringBuilder content) throws Exception {

        Map<String, String> a = new HashMap<String, String>();
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
            return;
        }

        resolveLink(content, "{@linkplain", "<tt>", "</tt>", k);
        resolveLink(content, "{@link", "", "", k);

        shipout(k, content);

    }

    /**
     * Process an XML file.
     * 
     * @param file the file
     * 
     * @throws IOException in case of an I/O error
     */
    @Override
    protected void processXml(File file) throws IOException {

        if (!collecting) {
            return;
        }
        InputStream stream = new FileInputStream(file);
        Element root;
        try {
            root = getBuilder().parse(stream).getDocumentElement();
        } catch (SAXException e) {
            warning(file.toString() + ": " + e.getMessage());
            return;
        } finally {
            stream.close();
        }

        String name = root.getNodeName();

        if ("ExTeX".equals(name)) {
            scanConfig(file, root);
        } else if ("unit".equals(name)) {
            scanUnit(file, root);
        } else {
            info("Ignoring unknown XML: " + name);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param content the content to resolve the links in
     * @param start the name of the link, i.e. {@code {&#64;link} or
     *        {@code {&#64;linkplain}
     * @param startTag the additional tag to insert at the start
     * @param endTag the additional tag to insert at the end
     * @param k the name of resource where the original text came from
     */
    protected void resolveLink(StringBuilder content, String start,
            String startTag, String endTag, Key k) {

        for (int i = content.indexOf(start); i >= 0; i = content.indexOf(start)) {
            int j = content.indexOf("}", i);
            String s = content.substring(i + start.length(), j).trim().
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
            Key key = keys.get(x);
            if (key != null) {
                String href = key.toString();
                content.replace(i, j + 1, startTag + "<a href=\"" + href
                        + "\">" + text + "</a>" + endTag);
            } else {
                content.replace(i, j + 1, startTag + text + endTag);
                warning(k.getLocation() + ": Unable to resolve link " + ref);
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
    @Override
    public void run(String[] args) throws Exception {

        collecting = true;
        super.run(args);
        collecting = false;
        super.run(args);
        shipoutAuthors(authorsFile, getAuthors());

        Collections.sort(primitives, new Comparator<PrimitiveInfo>() {

            public int compare(PrimitiveInfo o1, PrimitiveInfo o2) {

                int ct = o1.getName().compareTo(o2.getName());
                if (ct != 0) {
                    return ct;
                }
                return o1.getImplementer().compareTo(o2.getImplementer());
            }

        });
        shipoutPrimitives(primitivesFile, primitives);

    }

    /**
*      java.lang.String[], int)
     */
    @Override
    protected int runOption(String arg, String[] args, int i) throws Exception {

        int idx = i;
        if ("-authors".startsWith(arg)) {
            authorsFile = args[++idx];
        } else if ("-primitives".startsWith(arg)) {
            primitivesFile = args[++idx];
        }

        return super.runOption(arg, args, i);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param cs the input to parse
     * 
     * @return the root element of the DOM
     * 
     * @throws IOException in case of an I/O error
     * @throws SAXException in case of a parse error
     */
    protected Element scan(CharSequence cs) throws SAXException, IOException {

        StringBuilder sb =
                new StringBuilder(
                    "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
                            + "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\""
                            + " \"http://www.w3.org/TR/html4/loose.dtd\">");
        sb.append(cs.toString());
        return getBuilder().parse(
            new ByteArrayInputStream(sb.toString().getBytes()))
            .getDocumentElement();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param file the file read
     * @param root the root element
     */
    private void scanConfig(File file, Element root) {

        root.getElementsByTagName("unit");

        // TODO gene: scanConfig unimplemented
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param file the file read
     * @param root the root element
     */
    private void scanUnit(File file, Element root) {

        UnitInfo unitInfo = new UnitInfo(root.getAttribute("name"));
        units.add(unitInfo);
        NodeList defs = root.getElementsByTagName("define");

        int len = defs.getLength();
        for (int i = 0; i < len; i++) {
            Node item = defs.item(i);
            NamedNodeMap attributes = item.getAttributes();
            Node nameNode = attributes.getNamedItem("name");
            Node classNode = attributes.getNamedItem("class");
            Node namespaceNode = attributes.getNamedItem("namespace");

            PrimitiveInfo pi =
                    new PrimitiveInfo(nameNode.getNodeValue(),
                        namespaceNode == null ? null : namespaceNode
                            .getNodeValue(), classNode.getNodeValue());
            unitInfo.add(pi);
            primitives.add(pi);
        }
    }

    /**
     * Setter for authorsFile.
     * 
     * @param authorsFile the authorsFile to set
     */
    public void setAuthorsFile(String authorsFile) {

        this.authorsFile = authorsFile;
    }

    /**
     * Setter for primitivesFile.
     * 
     * @param primitivesFile the primitivesFile to set
     */
    public void setPrimitivesFile(String primitivesFile) {

        this.primitivesFile = primitivesFile;
    }

    /**
     * Ship the given content to the appropriate file.
     * 
     * @param key the name of the resource
     * @param content the output stream in form of a string buffer
     * 
     * @throws IOException in case of an I/O error
     * @throws SAXException in case of a transformation error
     */
    protected void shipout(Key key, StringBuilder content)
            throws IOException,
                SAXException {

        content.insert(0, "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n");
        String s =
                content.toString().replaceAll("TODO missing documentation",
                    "<img src=\"unimplemented.png\">");

        String outDir = getOutput();

        File file = new File(outDir, key + ".xml");
        FileWriter os = new FileWriter(file);
        os.write(s);
        os.close();
    }

    /**
     * Process the list of resources and authors.
     * 
     * @param file the target file base name or {@code null}
     * @param authors the list of authors
     * 
     * @throws IOException in case of an I/O error
     */
    protected void shipoutAuthors(String file, Map<String, Author> authors)
            throws IOException {

        if (file == null) {
            return;
        }

        File f = new File(getOutput(), file + ".xml");
        PrintStream out =
                new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(f)));

        out.print("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
        out.print("<authors>\n");
        for (String x : authors.keySet()) {
            out.print("  <resource name=\"");
            out.print(x);
            out.print("\">\n    <author email=\"");
            Author author = authors.get(x);
            out.print(author.getEmail()
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;"));
            out.print("\">");
            out.print(author.getName()
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;"));
            out.print("</author>\n  </resource>\n");
        }
        out.print("</authors>\n");
        out.close();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param file the target file base name or {@code null}
     * @param list the list of primitives
     * 
     * @throws IOException in case of an I/O error
     */
    protected void shipoutPrimitives(String file, List<PrimitiveInfo> list)
            throws IOException {

        if (file == null) {
            return;
        }
        File f = new File(getOutput(), file + ".xml");
        PrintStream out =
                new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(f)));
        out.print("<primitives>\n");
        PrimitiveInfo po = null;

        for (PrimitiveInfo pi : list) {
            if (pi.equals(po)) {
                continue;
            }
            po = pi;
            out.print("  <primitive name=\"\\");
            out.print(pi.getName());
            String namespace = pi.getNamespace();
            if (namespace != null) {
                out.print("\" namespace=\"");
                out.print(namespace);
            }
            out.print("\" class=\"");
            out.print(pi.getImplementer());
            out.print("\" />\n");
        }

        out.print("</primitives>\n");
        out.close();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param p ...
     * 
     * @return ...
     */
    protected String tuneKey(String p) {

        return p.replaceFirst("^org.extex.", "");
    }

}
