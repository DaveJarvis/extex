/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.doctools;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.tools.ant.filters.StringInputStream;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class contains the main program to collect the POMs is a set of base
 * directories.
 * <p>
 * </p>
 * <p>
 * Usage: <tt>java org.extex.doctools.PrimitiveCollector </tt><i>&lt;options&gt;
 * bases</i>
 * </p>
 * <p>
 * The following options are supported:
 * </p>
 * <dl>
 * <dt><tt>- &lt;base&gt;</tt></dt>
 * <dd>Use this argument as base name &ndash; even when it looks like an option.
 * </dd>
 * <dt><tt>-o[utput] &lt;output directory&gt;</tt></dt>
 * <dd>Use this argument as output directory name.</dd>
 * <dt><tt>-om[it] &lt;name&gt;</tt></dt>
 * <dd>Add the argument to the list of omitted files and directories.</dd>
 * <dt><tt>-x[sl] &lt;xsl file&gt;</tt></dt>
 * <dd>Name the XSL resource for processing the collected POMs. The XSL file is
 * sought on the classpath.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PrimitiveCollector {

    /**
     * This class is a container for a named DOM.
     */
    private class Info {

        /**
         * The field <tt>name</tt> contains the name.
         */
        private File name;

        /**
         * The field <tt>root</tt> contains the root element.
         */
        private Element root;

        /**
         * Creates a new object.
         * 
         * @param name the name
         * @param root the root element
         */
        public Info(File name, Element root) {

            super();
            this.name = name;
            this.root = root;
        }

        /**
         * Getter for the name.
         * 
         * @return the name
         */
        public File getName() {

            return name;
        }

        /**
         * Getter for the root.
         * 
         * @return the root
         */
        public Element getRoot() {

            return root;
        }

    }

    /**
     * The field <tt>DOC_PATTERN</tt> contains the pattern to recognize a doc
     * tag.
     */
    private static final Pattern DOC_PATTERN = Pattern.compile("[ *]*<doc.*");

    /**
     * The field <tt>DIR_FILTER</tt> contains the filter to select only
     * directories.
     */
    private static final FileFilter DIR_FILTER = new FileFilter() {

        /**
         * {@inheritDoc}
         * 
         * @see java.io.FileFilter#accept(java.io.File)
         */
        public boolean accept(File f) {

            return f.isDirectory();
        }
    };

    /**
     * The field <tt>JAVA_FILTER</tt> contains the filter to select only
     * directories.
     */
    private static final FileFilter JAVA_FILTER = new FileFilter() {

        /**
         * {@inheritDoc}
         * 
         * @see java.io.FileFilter#accept(java.io.File)
         */
        public boolean accept(File f) {

            return f.isFile() && f.getName().endsWith(".java");
        }
    };

    /**
     * The field <tt>XML_FILTER</tt> contains the filter to select only XML
     * files.
     */
    private static final FileFilter XML_FILTER = new FileFilter() {

        /**
         * {@inheritDoc}
         * 
         * @see java.io.FileFilter#accept(java.io.File)
         */
        public boolean accept(File f) {

            return f.isFile() && f.getName().endsWith(".xml");
        }
    };

    /**
     * The constant <tt>XML_INIT</tt> contains the doctype for XML parsing.
     */
    private static final String XML_INIT = "<!DOCTYPE ent [" //
            + "<!ENTITY quot \"&#34;\">" //
            // + "<!ENTITY amp \"&#38;\">" //
            // + "<!ENTITY lt \"&#60;\">" //
            // + "<!ENTITY gt \"&#62;\">" //
            + "<!ENTITY nbsp \"&#160;\">" //
            + "<!ENTITY iexcl \"&#161;\">" //
            + "<!ENTITY cent \"&#162;\">" //
            + "<!ENTITY pound \"&#163;\">" //
            + "<!ENTITY curren \"&#164;\">" //
            + "<!ENTITY yen \"&#165;\">" //
            + "<!ENTITY brvbar \"&#166;\">" //
            + "<!ENTITY sect \"&#167;\">" //
            + "<!ENTITY uml \"&#168;\">" //
            + "<!ENTITY copy \"&#169;\">" //
            + "<!ENTITY ordf \"&#170;\">" //
            + "<!ENTITY laquo \"&#171;\">" //
            + "<!ENTITY not \"&#172;\">" //
            + "<!ENTITY shy \"&#173;\">" //
            + "<!ENTITY reg \"&#174;\">" //
            + "<!ENTITY macr \"&#175;\">" //
            + "<!ENTITY deg \"&#176;\">" //
            + "<!ENTITY plusmn \"&#177;\">" //
            + "<!ENTITY sup2 \"&#178;\">" //
            + "<!ENTITY sup3 \"&#179;\">" //
            + "<!ENTITY acute \"&#180;\">" //
            + "<!ENTITY micro \"&#181;\">" //
            + "<!ENTITY para \"&#182;\">" //
            + "<!ENTITY middot \"&#183;\">" //
            + "<!ENTITY cedil \"&#184;\">" //
            + "<!ENTITY sup1 \"&#185;\">" //
            + "<!ENTITY ordm \"&#186;\">" //
            + "<!ENTITY raquo \"&#187;\">" //
            + "<!ENTITY frac14 \"&#188;\">" //
            + "<!ENTITY frac12 \"&#189;\">" //
            + "<!ENTITY frac34 \"&#190;\">" //
            + "<!ENTITY iquest \"&#191;\">" //
            + "<!ENTITY Agrave \"&#192;\">" //
            + "<!ENTITY Aacute \"&#193;\">" //
            + "<!ENTITY Acirc \"&#194;\">" //
            + "<!ENTITY Atilde \"&#195;\">" //
            + "<!ENTITY Auml \"&#196;\">" //
            + "<!ENTITY Aring \"&#197;\">" //
            + "<!ENTITY AElig \"&#198;\">" //
            + "<!ENTITY Ccedil \"&#199;\">" //
            + "<!ENTITY Egrave \"&#200;\">" //
            + "<!ENTITY Eacute \"&#201;\">" //
            + "<!ENTITY Ecirc \"&#202;\">" //
            + "<!ENTITY Euml \"&#203;\">" //
            + "<!ENTITY Igrave \"&#204;\">" //
            + "<!ENTITY Iacute \"&#205;\">" //
            + "<!ENTITY Icirc \"&#206;\">" //
            + "<!ENTITY Iuml \"&#207;\">" //
            + "<!ENTITY ETH \"&#208;\">" //
            + "<!ENTITY Ntilde \"&#209;\">" //
            + "<!ENTITY Ograve \"&#210;\">" //
            + "<!ENTITY Oacute \"&#211;\">" //
            + "<!ENTITY Ocirc \"&#212;\">" //
            + "<!ENTITY Otilde \"&#213;\">" //
            + "<!ENTITY Ouml \"&#214;\">" //
            + "<!ENTITY times \"&#215;\">" //
            + "<!ENTITY Oslash \"&#216;\">" //
            + "<!ENTITY Ugrave \"&#217;\">" //
            + "<!ENTITY Uacute \"&#218;\">" //
            + "<!ENTITY Ucirc \"&#219;\">" //
            + "<!ENTITY Uuml \"&#220;\">" //
            + "<!ENTITY Yacute \"&#221;\">" //
            + "<!ENTITY THORN \"&#222;\">" //
            + "<!ENTITY szlig \"&#223;\">" //
            + "<!ENTITY agrave \"&#224;\">" //
            + "<!ENTITY aacute \"&#225;\">" //
            + "<!ENTITY acirc \"&#226;\">" //
            + "<!ENTITY atilde \"&#227;\">" //
            + "<!ENTITY auml \"&#228;\">" //
            + "<!ENTITY aring \"&#229;\">" //
            + "<!ENTITY aelig \"&#230;\">" //
            + "<!ENTITY ccedil \"&#231;\">" //
            + "<!ENTITY egrave \"&#232;\">" //
            + "<!ENTITY eacute \"&#233;\">" //
            + "<!ENTITY ecirc \"&#234;\">" //
            + "<!ENTITY euml \"&#235;\">" //
            + "<!ENTITY igrave \"&#236;\">" //
            + "<!ENTITY iacute \"&#237;\">" //
            + "<!ENTITY icirc \"&#238;\">" //
            + "<!ENTITY iuml \"&#239;\">" //
            + "<!ENTITY eth \"&#240;\">" //
            + "<!ENTITY ntilde \"&#241;\">" //
            + "<!ENTITY ograve \"&#242;\">" //
            + "<!ENTITY oacute \"&#243;\">" //
            + "<!ENTITY ocirc \"&#244;\">" //
            + "<!ENTITY otilde \"&#245;\">" //
            + "<!ENTITY ouml \"&#246;\">" //
            + "<!ENTITY divide \"&#247;\">" //
            + "<!ENTITY oslash \"&#248;\">" //
            + "<!ENTITY ugrave \"&#249;\">" //
            + "<!ENTITY uacute \"&#250;\">" //
            + "<!ENTITY ucirc \"&#251;\">" //
            + "<!ENTITY uuml \"&#252;\">" //
            + "<!ENTITY yacute \"&#253;\">" //
            + "<!ENTITY thorn \"&#254;\">" //
            + "<!ENTITY yuml \"&#255;\">" //
            + "<!ENTITY Alpha \"&#913;\">" //
            + "<!ENTITY alpha \"&#945;\">" //
            + "<!ENTITY Beta \"&#914;\">" //
            + "<!ENTITY beta \"&#946;\">" //
            + "<!ENTITY Gamma \"&#915;\">" //
            + "<!ENTITY gamma \"&#947;\">" //
            + "<!ENTITY Delta \"&#916;\">" //
            + "<!ENTITY delta \"&#948;\">" //
            + "<!ENTITY Epsilon \"&#917;\">" //
            + "<!ENTITY epsilon \"&#949;\">" //
            + "<!ENTITY Zeta \"&#918;\">" //
            + "<!ENTITY zeta \"&#950;\">" //
            + "<!ENTITY Eta \"&#919;\">" //
            + "<!ENTITY eta \"&#951;\">" //
            + "<!ENTITY Theta \"&#920;\">" //
            + "<!ENTITY theta \"&#952;\">" //
            + "<!ENTITY Iota \"&#921;\">" //
            + "<!ENTITY iota \"&#953;\">" //
            + "<!ENTITY Kappa \"&#922;\">" //
            + "<!ENTITY kappa \"&#954;\">" //
            + "<!ENTITY Lambda \"&#923;\">" //
            + "<!ENTITY lambda \"&#955;\">" //
            + "<!ENTITY Mu \"&#924;\">" //
            + "<!ENTITY mu \"&#956;\">" //
            + "<!ENTITY Nu \"&#925;\">" //
            + "<!ENTITY nu \"&#957;\">" //
            + "<!ENTITY Xi \"&#926;\">" //
            + "<!ENTITY xi \"&#958;\">" //
            + "<!ENTITY Omicron \"&#927;\">" //
            + "<!ENTITY omicron \"&#959;\">" //
            + "<!ENTITY Pi \"&#928;\">" //
            + "<!ENTITY pi \"&#960;\">" //
            + "<!ENTITY Rho \"&#929;\">" //
            + "<!ENTITY rho \"&#961;\">" //
            + "<!ENTITY Sigma \"&#931;\">" //
            + "<!ENTITY sigmaf \"&#962;\">" //
            + "<!ENTITY sigma \"&#963;\">" //
            + "<!ENTITY Tau \"&#932;\">" //
            + "<!ENTITY tau \"&#964;\">" //
            + "<!ENTITY Upsilon \"&#933;\">" //
            + "<!ENTITY upsilon \"&#965;\">" //
            + "<!ENTITY Phi \"&#934;\">" //
            + "<!ENTITY phi \"&#966;\">" //
            + "<!ENTITY Chi \"&#935;\">" //
            + "<!ENTITY chi \"&#967;\">" //
            + "<!ENTITY Psi \"&#936;\">" //
            + "<!ENTITY psi \"&#968;\">" //
            + "<!ENTITY Omega \"&#937;\">" //
            + "<!ENTITY omega \"&#969;\">" //
            + "<!ENTITY thetasym \"&#977;\">" //
            + "<!ENTITY upsih \"&#978;\">" //
            + "<!ENTITY piv \"&#982;\">" //
            + "<!ENTITY forall \"&#8704;\">" //
            + "<!ENTITY part \"&#8706;\">" //
            + "<!ENTITY exist \"&#8707;\">" //
            + "<!ENTITY empty \"&#8709;\">" //
            + "<!ENTITY nabla \"&#8711;\">" //
            + "<!ENTITY isin \"&#8712;\">" //
            + "<!ENTITY notin \"&#8713;\">" //
            + "<!ENTITY ni \"&#8715;\">" //
            + "<!ENTITY prod \"&#8719;\">" //
            + "<!ENTITY sum \"&#8721;\">" //
            + "<!ENTITY minus \"&#8722;\">" //
            + "<!ENTITY lowast \"&#8727;\">" //
            + "<!ENTITY radic \"&#8730;\">" //
            + "<!ENTITY prop \"&#8733;\">" //
            + "<!ENTITY infin \"&#8734;\">" //
            + "<!ENTITY ang \"&#8736;\">" //
            + "<!ENTITY and \"&#8743;\">" //
            + "<!ENTITY or \"&#8744;\">" //
            + "<!ENTITY cap \"&#8745;\">" //
            + "<!ENTITY cup \"&#8746;\">" //
            + "<!ENTITY int \"&#8747;\">" //
            + "<!ENTITY there4 \"&#8756;\">" //
            + "<!ENTITY sim \"&#8764;\">" //
            + "<!ENTITY cong \"&#8773;\">" //
            + "<!ENTITY asymp \"&#8776;\">" //
            + "<!ENTITY ne \"&#8800;\">" //
            + "<!ENTITY equiv \"&#8801;\">" //
            + "<!ENTITY le \"&#8804;\">" //
            + "<!ENTITY ge \"&#8805;\">" //
            + "<!ENTITY sub \"&#8834;\">" //
            + "<!ENTITY sup \"&#8835;\">" //
            + "<!ENTITY nsub \"&#8836;\">" //
            + "<!ENTITY sube \"&#8838;\">" //
            + "<!ENTITY supe \"&#8839;\">" //
            + "<!ENTITY oplus \"&#8853;\">" //
            + "<!ENTITY otimes \"&#8855;\">" //
            + "<!ENTITY perp \"&#8869;\">" //
            + "<!ENTITY sdot \"&#8901;\">" //
            + "<!ENTITY loz \"&#9674;\">" //
            + "<!ENTITY lceil \"&#8968;\">" //
            + "<!ENTITY rceil \"&#8969;\">" //
            + "<!ENTITY lfloor \"&#8970;\">" //
            + "<!ENTITY rfloor \"&#8971;\">" //
            + "<!ENTITY lang \"&#9001;\">" //
            + "<!ENTITY rang \"&#9002;\">" //
            + "<!ENTITY larr \"&#8592;\">" //
            + "<!ENTITY uarr \"&#8593;\">" //
            + "<!ENTITY rarr \"&#8594;\">" //
            + "<!ENTITY darr \"&#8595;\">" //
            + "<!ENTITY harr \"&#8596;\">" //
            + "<!ENTITY crarr \"&#8629;\">" //
            + "<!ENTITY lArr \"&#8656;\">" //
            + "<!ENTITY uArr \"&#8657;\">" //
            + "<!ENTITY rArr \"&#8658;\">" //
            + "<!ENTITY dArr \"&#8659;\">" //
            + "<!ENTITY hArr \"&#8660;\">" //
            + "<!ENTITY bull \"&#8226;\">" //
            + "<!ENTITY prime \"&#8242;\">" //
            + "<!ENTITY oline \"&#8254;\">" //
            + "<!ENTITY frasl \"&#8260;\">" //
            + "<!ENTITY weierp \"&#8472;\">" //
            + "<!ENTITY image \"&#8465;\">" //
            + "<!ENTITY real \"&#8476;\">" //
            + "<!ENTITY trade \"&#8482;\">" //
            + "<!ENTITY euro \"&#8364;\">" //
            + "<!ENTITY alefsym \"&#8501;\">" //
            + "<!ENTITY spades \"&#9824;\">" //
            + "<!ENTITY clubs \"&#9827;\">" //
            + "<!ENTITY hearts \"&#9829;\">" //
            + "<!ENTITY diams \"&#9830;\">" //
            + "<!ENTITY ensp \"&#8194;\">" //
            + "<!ENTITY emsp \"&#8195;\">" //
            + "<!ENTITY thinsp \"&#8201;\">" //
            + "<!ENTITY zwnj \"&#8204;\">" //
            + "<!ENTITY zwj \"&#8205;\">" //
            + "<!ENTITY lrm \"&#8206;\">" //
            + "<!ENTITY rlm \"&#8207;\">" //
            + "<!ENTITY ndash \"&#8211;\">" //
            + "<!ENTITY mdash \"&#8212;\">" //
            + "<!ENTITY lsquo \"&#8216;\">" //
            + "<!ENTITY rsquo \"&#8217;\">" //
            + "<!ENTITY sbquo \"&#8218;\">" //
            + "<!ENTITY ldquo \"&#8220;\">" //
            + "<!ENTITY rdquo \"&#8221;\">" //
            + "<!ENTITY bdquo \"&#8222;\">" //
            + "<!ENTITY dagger \"&#8224;\">" //
            + "<!ENTITY Dagger \"&#8225;\">" //
            + "<!ENTITY hellip \"&#8230;\">" //
            + "<!ENTITY permil \"&#8240;\">" //
            + "<!ENTITY lsaquo \"&#8249;\">" //
            + "<!ENTITY rsaquo \"&#8250;\">" //
            + "]>";

    /**
     * The command line interface. The command line is processed. Finally
     * {@link System#exit(int)} is invoked.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(mainFacade(args));
    }

    /**
     * The command line interface facade. Any exception is caught and a message
     * written to {@link System#err}. Then an exit code is returned.
     * 
     * @param args the command line arguments
     * 
     * @return the exit code; i.e. 0 for success and -1 for failure
     */
    public static int mainFacade(String[] args) {

        try {
            new PrimitiveCollector().run(args);
            return 0;
        } catch (Exception e) {
            System.err.println(e.toString());
            return -1;
        }
    }

    /**
     * Print a DOM to a buffer.
     * 
     * @param buffer the writer
     * @param node the node
     * 
     * @throws IOException in case of an I/O error
     */
    public static void printXml(StringBuilder buffer, Node node)
            throws IOException {

        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                NodeList children = node.getChildNodes();
                int len = children.getLength();
                buffer.append("<");
                buffer.append(node.getNodeName());

                NamedNodeMap att = node.getAttributes();
                int alen = att.getLength();
                for (int i = 0; i < alen; i++) {
                    printXml(buffer, att.item(i));
                }
                if (len == 0) {
                    buffer.append("/>");
                } else {
                    buffer.append(">");
                    for (int i = 0; i < len; i++) {
                        printXml(buffer, children.item(i));
                    }
                    buffer.append("</");
                    buffer.append(node.getNodeName());
                    buffer.append(">");
                }
                break;
            case Node.ATTRIBUTE_NODE:
                buffer.append(" ");
                buffer.append(node.getNodeName());
                buffer.append("=\"");
                buffer.append(node.getNodeValue());
                buffer.append("\"");
                break;
            case Node.TEXT_NODE:
                String text =
                        node.getNodeValue().replaceAll("&", "&amp;")
                            .replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                buffer.append(text);
                break;
            case Node.CDATA_SECTION_NODE:
                buffer.append("<![CDATA[");
                buffer.append(node.getNodeValue());
                buffer.append("]]>");
                break;
            case Node.ENTITY_REFERENCE_NODE:
            case Node.ENTITY_NODE:
                String name = node.getNodeName();
                if ("amp".equals(name) || "lt".equals(name)
                        || "gt".equals(name)) {
                    buffer.append("&");
                    buffer.append(name);
                    buffer.append(";");
                } else {
                    buffer.append("<");
                    buffer.append(name);
                    buffer.append("/>");
                }
                break;
            case Node.PROCESSING_INSTRUCTION_NODE:
            case Node.COMMENT_NODE:
            case Node.DOCUMENT_NODE:
            case Node.DOCUMENT_TYPE_NODE:
            case Node.DOCUMENT_FRAGMENT_NODE:
            case Node.NOTATION_NODE:
            default:
                //
        }
    }

    /**
     * The field <tt>bases</tt> contains the list of base directories to
     * consider.
     */
    private List<String> bases = new ArrayList<String>();

    /**
     * The field <tt>output</tt> contains the output file name or
     * <code>null</code> for stdout.
     */
    private String output = null;

    /**
     * The field <tt>omit</tt> contains the list of omitted files and
     * directories.
     */
    private List<String> omit = new ArrayList<String>();

    /**
     * The field <tt>xslt</tt> contains the name of the xslt resource.
     */
    private String xslt = PrimitiveCollector.class.getName().replace('.', '/');

    /**
     * The field <tt>configs</tt> contains the collected configurations.
     */
    private Map<String, Info> configs = null;

    /**
     * The field <tt>units</tt> contains the collected configurations.
     */
    private Map<String, Info> units = null;

    /**
     * The field <tt>builder</tt> contains the encapsulated document builder.
     */
    private DocumentBuilder builder;

    /**
     * The field <tt>primitives</tt> contains the collected primitives.
     */
    private Map<String, Info> primitives = null;

    /**
     * The field <tt>verbose</tt> contains the verbosity indicator.
     */
    private boolean verbose = false;

    /**
     * Creates a new object.
     * 
     * @throws ParserConfigurationException in case of an error
     */
    public PrimitiveCollector() throws ParserConfigurationException {

        super();
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * Add a base directory.
     * 
     * @param base the base directory
     */
    public void addBase(String base) {

        bases.add(base);
    };

    /**
     * Traverse a directory hierarchy and collect whatever is needed.
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public void collect() throws IOException, SyntaxException {

        units = new HashMap<String, Info>();
        configs = new HashMap<String, Info>();
        primitives = new HashMap<String, Info>();

        for (String base : bases) {
            File dir = new File(base);
            if (dir.isDirectory()) {
                traverse(dir);
            }
        }
    };

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public void collectPrimitives() throws IOException, SyntaxException {

        for (String base : bases) {
            File dir = new File(base, "src/main/java");
            if (dir.isDirectory()) {
                traverseSource(dir);
            }
        }
    }

    /**
     * Getter for the output.
     * 
     * @return the output
     */
    public String getOutput() {

        return output;
    }

    /**
     * Getter for the xslt.
     * 
     * @return the xslt
     */
    public String getXslt() {

        return xslt;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param buffer
     * @return
     */
    private String javadoc2xml(StringBuilder buffer) {

        String tag = "link";
        int last = 0;
        for (int i = buffer.indexOf("{@link", last); i >= 0; i =
                buffer.indexOf("{@link", last)) {
            int beg = i;
            i += 6;
            if (buffer.substring(i, i + 5).equals("plain")) {
                i += 5;
                tag = "linkplain";
            }
            char c;
            for (c = buffer.charAt(i); Character.isWhitespace(c); c =
                    buffer.charAt(i)) {
                i++;
            }
            int start = i;
            boolean paren = false;
            for (c = buffer.charAt(i++); !Character.isWhitespace(c) || paren; c =
                    buffer.charAt(i++)) {
                if (c == '(') {
                    paren = true;
                } else if (c == ')') {
                    paren = false;
                }
            }
            String ref = buffer.substring(start, i - 1).replaceAll(" ", "");
            for (c = buffer.charAt(i); Character.isWhitespace(c); c =
                    buffer.charAt(i)) {
                i++;
            }
            start = i;
            for (c = buffer.charAt(i); c != '}'; c = buffer.charAt(i)) {
                i++;
            }
            String text = buffer.substring(start, i);

            buffer.replace(beg, i + 1, "<" + tag + " href=\"" + ref + "\">"
                    + text + "</" + tag + ">");
        }

        // String s =
        // buffer
        // .toString()
        // .replaceAll(
        // "\\{@linkplain \\([a-zA-Z.0-9 (\n]*)\\)[ \t\n]+\\([^{}]*\\)\\}",
        // "<linkplain href=\"$1\">$2</linkplain>")
        // .replaceAll("\\{@link ([^}]*)\\}",
        // "<link href=\"\">$1</linkplain>");
        return buffer.toString();
    }

    /**
     * Get the next argument and complain if none is found.
     * 
     * @param args the array of arguments
     * @param i the index of the next argument
     * 
     * @return the next argument
     * 
     * @throws MissingArgumentException in case of a missing argument
     */
    private String nextArgument(String[] args, int i)
            throws MissingArgumentException {

        if (i >= args.length) {
            throw new MissingArgumentException(args[i - 1]);
        }
        return args[i];
    }

    /**
     * Add a file to be omitted.
     * 
     * @param name the name
     */
    public void omit(String name) {

        omit.add(name);
    }

    /**
     * Process a Java file.
     * 
     * @param f the java file
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void processJava(File f) throws IOException, SyntaxException {

        LineNumberReader reader = new LineNumberReader(new FileReader(f));

        try {
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                int i = s.indexOf("<doc");
                if (i >= 0 && DOC_PATTERN.matcher(s).matches()) {
                    processJava(reader, f, s.substring(i));
                }
            }
        } finally {
            reader.close();
        }

    }

    /**
     * Process a Java file.
     * 
     * @param reader the reader
     * @param f the java file
     * @param start the start string
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void processJava(LineNumberReader reader, File f, String start)
            throws IOException,
                SyntaxException {

        if (verbose) {
            System.err.print("--- ");
            System.err.println(f.toString());
        }

        StringBuilder buffer = new StringBuilder(XML_INIT);
        buffer.append(start);
        buffer.append('\n');

        for (String s = reader.readLine(); s != null; s = reader.readLine()) {
            s.replaceAll("^ *\\*", "");
            int i = s.indexOf("</doc>");
            if (i >= 0) {
                s = s.substring(0, i + 6).replaceAll("^[ ]*\\*", "");
                buffer.append(s);
                Element root;
                try {
                    root =
                            builder.parse(
                                new StringInputStream(buffer.toString()))
                                .getDocumentElement();
                } catch (SAXException e) {
                    throw new SyntaxException(f.toString(), //
                        e.getLocalizedMessage());
                }

                String name = root.getAttribute("name");
                if ("".equals(name)) {
                    return;
                }
                // String type = root.getAttribute("type");

                primitives.put(name + " " + f.toString(), new Info(f, root));

                return;
            } else if (s.matches("^[ ]*TODO\\(.*\\)")) {
                buffer.append("<todo");
                buffer.append(s.trim().substring(5));
                buffer.append("</todo");
            } else {
                buffer.append(s.replaceAll("^[ ]*\\*", ""));
            }
            buffer.append('\n');
        }
        throw new MissingEndTagException(f.toString(), "doc");
    }

    /**
     * Process the configs and units of a module.
     * 
     * @param pom the location of the POM
     * @param config the config directory
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void processModule(File pom, File config)
            throws SyntaxException,
                IOException {

        for (File f : config.listFiles(XML_FILTER)) {
            String name = f.getName();
            Element root;
            try {
                root = builder.parse(f).getDocumentElement();
            } catch (SAXException e) {
                throw new SyntaxException(f.toString(), e.getLocalizedMessage());
            }
            configs.put(name.replaceAll(".xml$", ""), new Info(f, root));
        }

        File unit = new File(config, "unit");
        if (!unit.isDirectory()) {
            return;
        }

        for (File f : unit.listFiles(XML_FILTER)) {
            try {
                processUnit(f, f.getName(), builder.parse(f)
                    .getDocumentElement(), pom.getParentFile());
            } catch (SAXException e) {
                throw new SyntaxException(f.getName(), e.getLocalizedMessage());
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param f
     * @param name
     * @param root
     * @param dir the base directory
     */
    private void processUnit(File f, String name, Element root, File dir) {

        units.put(name.replaceAll(".xml$", ""), new Info(f, root));

        // NodeList nl = root.getElementsByTagName("define");
        // int len = nl.getLength();
        // for (int i = 0; i < len; i++) {
        // Node n = nl.item(i);
        // File clazz =
        // new File(dir, "src/main/java/"
        // + n.getAttributes().getNamedItem("class")
        // .getNodeValue().replaceAll("\\.", "/")
        // + ".java");
        // System.err.print(clazz.exists());
        // System.err.print("\t");
        // System.err.println(clazz.toString());
        // }
    }

    /**
     * Traverse a set of directory hierarchies collecting POMs and store the
     * transformed result in a given output file. If no output file has been
     * given then the result is written to stdout.
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     * @throws TransformerFactoryConfigurationError in case of a transformer
     *         factory configuration exception
     * @throws TransformerException in case of a transformer exception
     */
    public void run()
            throws IOException,
                SyntaxException,
                TransformerFactoryConfigurationError,
                TransformerException {

        Writer writer =
                output == null
                        ? new OutputStreamWriter(System.out)
                        : new FileWriter(new File(output, "configs.tex"));
        try {
            writeConfigs(writer);
        } finally {
            writer.flush();
            if (output != null) {
                writer.close();
            }
        }

        writer =
                output == null
                        ? new OutputStreamWriter(System.out)
                        : new FileWriter(new File(output, "units.tex"));
        try {
            writeUnits(writer);
        } finally {
            writer.flush();
            if (output != null) {
                writer.close();
            }
        }

        writer =
                output == null
                        ? new OutputStreamWriter(System.out)
                        : new FileWriter(new File(output, "primitives.tex"));
        try {
            writePrimitives(writer);
        } finally {
            writer.flush();
            if (output != null) {
                writer.close();
            }
        }
    }

    /**
     * Process a list of strings like the command line options.
     * 
     * @param args the arguments
     * 
     * @throws IOException in case of an I/O error
     * @throws TransformerFactoryConfigurationError in case of an error
     * @throws TransformerException in case of a transformer error
     * @throws SyntaxException in case of a syntax error
     * @throws MissingArgumentException in case of an error
     * @throws UnknownArgumentException in case of an error
     */
    public void run(String[] args)
            throws IOException,
                TransformerFactoryConfigurationError,
                SyntaxException,
                TransformerException,
                MissingArgumentException,
                UnknownArgumentException {

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                if ("-".equals(arg)) {
                    bases.add(nextArgument(args, ++i));
                } else if ("-output".startsWith(arg)) {
                    setOutput(nextArgument(args, ++i));
                } else if ("-omit".startsWith(arg)) {
                    omit.add(nextArgument(args, ++i));
                } else if ("-xsl".startsWith(arg)) {
                    xslt = nextArgument(args, ++i);
                } else if ("-verbose".startsWith(arg)) {
                    verbose = true;
                } else {
                    throw new UnknownArgumentException(arg);
                }
            } else {
                bases.add(arg);
            }
        }

        run();
    }

    /**
     * Setter for the output. The empty string and the string containing a '-'
     * only are treated as <code>null</code>.
     * 
     * @param output the output to set
     */
    public void setOutput(String output) {

        this.output = "".equals(output) || "-".equals(output) ? null : output;
    }

    /**
     * Setter for the xslt.
     * 
     * @param xslt the xslt to set
     */
    public void setXslt(String xslt) {

        this.xslt = xslt;
    }

    /**
     * Traverse a directory tree and collect the POMs in a buffer.
     * 
     * @param file the file to consider
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void traverse(File file) throws IOException, SyntaxException {

        if (omit.contains(file.getName())) {
            return;
        }

        File pom = new File(file, "pom.xml");
        if (pom.exists()) {
            File config =
                    new File(new File(new File(new File(file, "src"), "main"),
                        "resources"), "config");
            if (config.isDirectory()) {
                processModule(pom, config);
            }

            File dir = new File(file, "src/main/java");
            if (dir.isDirectory()) {
                traverseSource(dir);
            }

            for (File f : file.listFiles(DIR_FILTER)) {
                traverse(f);
            }
        }
    }

    /**
     * Traverse a directory tree and process the java files.
     * 
     * @param file the file
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void traverseSource(File file) throws IOException, SyntaxException {

        for (File f : file.listFiles(JAVA_FILTER)) {
            processJava(f);
        }
        for (File f : file.listFiles(DIR_FILTER)) {
            traverseSource(f);
        }
    }

    /**
     * Process a map and write it to the given writer.
     * 
     * @param writer the writer
     * @param map the map
     * @param tag the tag name
     * 
     * @throws IOException in case of an I/O error
     * @throws TransformerFactoryConfigurationError in case of an error
     * @throws TransformerException in case of a transformer error
     */
    protected void write(Writer writer, Map<String, Info> map, String tag)
            throws IOException,
                TransformerFactoryConfigurationError,
                TransformerException {

        StringBuilder buffer = new StringBuilder();
        buffer.append("<");
        buffer.append(tag);
        buffer.append("s>");

        Object[] ks = map.keySet().toArray();
        Arrays.sort(ks);
        for (Object key : ks) {
            Info config = map.get(key);
            buffer.append("<");
            buffer.append(tag);
            buffer.append(" name=\"");
            buffer.append(key.toString());
            buffer.append("\">");
            printXml(buffer, config.getRoot());
            buffer.append("</");
            buffer.append(tag);
            buffer.append(">\n");
        }
        buffer.append("</");
        buffer.append(tag);
        buffer.append("s>\n");

        InputStream in =
                getClass().getClassLoader().getResourceAsStream(
                    xslt + "-" + tag + ".xsl");
        if (in == null) {
            throw new FileNotFoundException(xslt);
        }
        Transformer transformer;
        try {
            transformer =
                    TransformerFactory.newInstance().newTransformer(
                        new StreamSource(in));
        } finally {
            in.close();
        }

        String s = javadoc2xml(buffer);
        try {
            transformer.transform(new StreamSource(new StringReader(s)),
                new StreamResult(writer));
        } catch (TransformerException e) {

            // FileWriter w = new FileWriter("err.xml");
            // try {
            // w.write(buffer.toString());
            // } finally {
            // w.close();
            // }
            throw e;
        }

    }

    /**
     * Traverse a set of directory trees collecting POMs and write the
     * transformed result to the given writer.
     * 
     * @param writer the writer for the output
     * 
     * @throws IOException in case of an I/O error
     * @throws TransformerException in case of an error in the transformer
     * @throws SyntaxException in case of a syntax error
     */
    public void writeConfigs(Writer writer)
            throws IOException,
                TransformerException,
                SyntaxException {

        if (configs == null) {
            collect();
        }
        write(writer, configs, "config");
    }

    /**
     * Traverse a set of directory trees collecting POMs and write the
     * transformed result to the given writer.
     * 
     * @param writer the writer for the output
     * 
     * @throws IOException in case of an I/O error
     * @throws TransformerException in case of an error in the transformer
     * @throws SyntaxException in case of a syntax error
     */
    public void writePrimitives(Writer writer)
            throws IOException,
                TransformerException,
                SyntaxException {

        if (primitives == null) {
            collectPrimitives();
        }
        write(writer, primitives, "primitive");
    }

    /**
     * Traverse a set of directory trees collecting POMs and write the
     * transformed result to the given writer.
     * 
     * @param writer the writer for the output
     * 
     * @throws IOException in case of an I/O error
     * @throws TransformerException in case of an error in the transformer
     * @throws SyntaxException in case of a syntax error
     */
    public void writeUnits(Writer writer)
            throws IOException,
                TransformerException,
                SyntaxException {

        if (units == null) {
            collect();
        }
        write(writer, units, "unit");
    }

}
