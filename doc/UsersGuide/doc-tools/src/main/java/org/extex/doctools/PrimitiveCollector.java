/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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
import java.io.FileOutputStream;
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
import java.util.Comparator;
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
import org.extex.doctools.exceptions.MissingArgumentException;
import org.extex.doctools.exceptions.MissingEndTagException;
import org.extex.doctools.exceptions.SyntaxException;
import org.extex.doctools.exceptions.UnknownArgumentException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class contains the main program to collect the POMs is a set of base
 * directories.
 * <br>
* <p>
 * Usage: {@code java org.extex.doctools.PrimitiveCollector }<i>&lt;options&gt;
 * bases</i>
 * </p>
 * <p>
 * The following options are supported:
 * </p>
 * <dl>
 * <dt>{@code - &lt;base&gt;}</dt>
 * <dd>Use this argument as base name &ndash; even when it looks like an option.
 * </dd>
 * <dt>{@code -o[utput] &lt;output directory&gt;}</dt>
 * <dd>Use this argument as output directory name.</dd>
 * <dt>{@code -om[it] &lt;name&gt;}</dt>
 * <dd>Add the argument to the list of omitted files and directories.</dd>
 * <dt>{@code -x[sl] &lt;xsl file&gt;}</dt>
 * <dd>Name the XSL resource for processing the collected POMs. The XSL file is
 * sought on the classpath.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PrimitiveCollector {

    /**
     * This class is a container for a named DOM.
     */
    private class Info {

        /**
         * The field {@code name} contains the name.
         */
        private File name;

        /**
         * The field {@code root} contains the root element.
         */
        private Element root;

        /**
         * Creates a new object.
         * 
         * @param name the name
         * @param root the root element
         */
        public Info(File name, Element root) {

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
     * The field {@code DOC_PATTERN} contains the pattern to recognize a doc
     * tag.
     */
    private static final Pattern DOC_PATTERN = Pattern.compile("[ *]*<doc.*");

    /**
     * The field {@code DIR_FILTER} contains the filter to select only
     * directories.
     */
    private static final FileFilter DIR_FILTER = new FileFilter() {

    public boolean accept(File f) {

            return f.isDirectory();
        }
    };

    /**
     * The field {@code XML_FILTER} contains the filter to select only XML
     * files.
     */
    private static final FileFilter XML_FILTER = new FileFilter() {

    public boolean accept(File f) {

            return f.isFile() && f.getName().endsWith(".xml");
        }
    };

    /**
     * The constant {@code XML_INIT} contains the doctype for XML parsing.
     */
    private static final String XML_INIT = "<!DOCTYPE ent ["
            + "<!ENTITY quot \"&#34;\">"
            // + "<!ENTITY amp \"&#38;\">"
            // + "<!ENTITY lt \"&#60;\">"
            // + "<!ENTITY gt \"&#62;\">"
            + "<!ENTITY nbsp \"&#160;\">"
            + "<!ENTITY iexcl \"&#161;\">"
            + "<!ENTITY cent \"&#162;\">"
            + "<!ENTITY pound \"&#163;\">"
            + "<!ENTITY curren \"&#164;\">"
            + "<!ENTITY yen \"&#165;\">"
            + "<!ENTITY brvbar \"&#166;\">"
            + "<!ENTITY sect \"&#167;\">"
            + "<!ENTITY uml \"&#168;\">"
            + "<!ENTITY copy \"&#169;\">"
            + "<!ENTITY ordf \"&#170;\">"
            + "<!ENTITY laquo \"&#171;\">"
            + "<!ENTITY not \"&#172;\">"
            + "<!ENTITY shy \"&#173;\">"
            + "<!ENTITY reg \"&#174;\">"
            + "<!ENTITY macr \"&#175;\">"
            + "<!ENTITY deg \"&#176;\">"
            + "<!ENTITY plusmn \"&#177;\">"
            + "<!ENTITY sup2 \"&#178;\">"
            + "<!ENTITY sup3 \"&#179;\">"
            + "<!ENTITY acute \"&#180;\">"
            + "<!ENTITY micro \"&#181;\">"
            + "<!ENTITY para \"&#182;\">"
            + "<!ENTITY middot \"&#183;\">"
            + "<!ENTITY cedil \"&#184;\">"
            + "<!ENTITY sup1 \"&#185;\">"
            + "<!ENTITY ordm \"&#186;\">"
            + "<!ENTITY raquo \"&#187;\">"
            + "<!ENTITY frac14 \"&#188;\">"
            + "<!ENTITY frac12 \"&#189;\">"
            + "<!ENTITY frac34 \"&#190;\">"
            + "<!ENTITY iquest \"&#191;\">"
            + "<!ENTITY Agrave \"&#192;\">"
            + "<!ENTITY Aacute \"&#193;\">"
            + "<!ENTITY Acirc \"&#194;\">"
            + "<!ENTITY Atilde \"&#195;\">"
            + "<!ENTITY Auml \"&#196;\">"
            + "<!ENTITY Aring \"&#197;\">"
            + "<!ENTITY AElig \"&#198;\">"
            + "<!ENTITY Ccedil \"&#199;\">"
            + "<!ENTITY Egrave \"&#200;\">"
            + "<!ENTITY Eacute \"&#201;\">"
            + "<!ENTITY Ecirc \"&#202;\">"
            + "<!ENTITY Euml \"&#203;\">"
            + "<!ENTITY Igrave \"&#204;\">"
            + "<!ENTITY Iacute \"&#205;\">"
            + "<!ENTITY Icirc \"&#206;\">"
            + "<!ENTITY Iuml \"&#207;\">"
            + "<!ENTITY ETH \"&#208;\">"
            + "<!ENTITY Ntilde \"&#209;\">"
            + "<!ENTITY Ograve \"&#210;\">"
            + "<!ENTITY Oacute \"&#211;\">"
            + "<!ENTITY Ocirc \"&#212;\">"
            + "<!ENTITY Otilde \"&#213;\">"
            + "<!ENTITY Ouml \"&#214;\">"
            + "<!ENTITY times \"&#215;\">"
            + "<!ENTITY Oslash \"&#216;\">"
            + "<!ENTITY Ugrave \"&#217;\">"
            + "<!ENTITY Uacute \"&#218;\">"
            + "<!ENTITY Ucirc \"&#219;\">"
            + "<!ENTITY Uuml \"&#220;\">"
            + "<!ENTITY Yacute \"&#221;\">"
            + "<!ENTITY THORN \"&#222;\">"
            + "<!ENTITY szlig \"&#223;\">"
            + "<!ENTITY agrave \"&#224;\">"
            + "<!ENTITY aacute \"&#225;\">"
            + "<!ENTITY acirc \"&#226;\">"
            + "<!ENTITY atilde \"&#227;\">"
            + "<!ENTITY auml \"&#228;\">"
            + "<!ENTITY aring \"&#229;\">"
            + "<!ENTITY aelig \"&#230;\">"
            + "<!ENTITY ccedil \"&#231;\">"
            + "<!ENTITY egrave \"&#232;\">"
            + "<!ENTITY eacute \"&#233;\">"
            + "<!ENTITY ecirc \"&#234;\">"
            + "<!ENTITY euml \"&#235;\">"
            + "<!ENTITY igrave \"&#236;\">"
            + "<!ENTITY iacute \"&#237;\">"
            + "<!ENTITY icirc \"&#238;\">"
            + "<!ENTITY iuml \"&#239;\">"
            + "<!ENTITY eth \"&#240;\">"
            + "<!ENTITY ntilde \"&#241;\">"
            + "<!ENTITY ograve \"&#242;\">"
            + "<!ENTITY oacute \"&#243;\">"
            + "<!ENTITY ocirc \"&#244;\">"
            + "<!ENTITY otilde \"&#245;\">"
            + "<!ENTITY ouml \"&#246;\">"
            + "<!ENTITY divide \"&#247;\">"
            + "<!ENTITY oslash \"&#248;\">"
            + "<!ENTITY ugrave \"&#249;\">"
            + "<!ENTITY uacute \"&#250;\">"
            + "<!ENTITY ucirc \"&#251;\">"
            + "<!ENTITY uuml \"&#252;\">"
            + "<!ENTITY yacute \"&#253;\">"
            + "<!ENTITY thorn \"&#254;\">"
            + "<!ENTITY yuml \"&#255;\">"
            + "<!ENTITY Alpha \"&#913;\">"
            + "<!ENTITY alpha \"&#945;\">"
            + "<!ENTITY Beta \"&#914;\">"
            + "<!ENTITY beta \"&#946;\">"
            + "<!ENTITY Gamma \"&#915;\">"
            + "<!ENTITY gamma \"&#947;\">"
            + "<!ENTITY Delta \"&#916;\">"
            + "<!ENTITY delta \"&#948;\">"
            + "<!ENTITY Epsilon \"&#917;\">"
            + "<!ENTITY epsilon \"&#949;\">"
            + "<!ENTITY Zeta \"&#918;\">"
            + "<!ENTITY zeta \"&#950;\">"
            + "<!ENTITY Eta \"&#919;\">"
            + "<!ENTITY eta \"&#951;\">"
            + "<!ENTITY Theta \"&#920;\">"
            + "<!ENTITY theta \"&#952;\">"
            + "<!ENTITY Iota \"&#921;\">"
            + "<!ENTITY iota \"&#953;\">"
            + "<!ENTITY Kappa \"&#922;\">"
            + "<!ENTITY kappa \"&#954;\">"
            + "<!ENTITY Lambda \"&#923;\">"
            + "<!ENTITY lambda \"&#955;\">"
            + "<!ENTITY Mu \"&#924;\">"
            + "<!ENTITY mu \"&#956;\">"
            + "<!ENTITY Nu \"&#925;\">"
            + "<!ENTITY nu \"&#957;\">"
            + "<!ENTITY Xi \"&#926;\">"
            + "<!ENTITY xi \"&#958;\">"
            + "<!ENTITY Omicron \"&#927;\">"
            + "<!ENTITY omicron \"&#959;\">"
            + "<!ENTITY Pi \"&#928;\">"
            + "<!ENTITY pi \"&#960;\">"
            + "<!ENTITY Rho \"&#929;\">"
            + "<!ENTITY rho \"&#961;\">"
            + "<!ENTITY Sigma \"&#931;\">"
            + "<!ENTITY sigmaf \"&#962;\">"
            + "<!ENTITY sigma \"&#963;\">"
            + "<!ENTITY Tau \"&#932;\">"
            + "<!ENTITY tau \"&#964;\">"
            + "<!ENTITY Upsilon \"&#933;\">"
            + "<!ENTITY upsilon \"&#965;\">"
            + "<!ENTITY Phi \"&#934;\">"
            + "<!ENTITY phi \"&#966;\">"
            + "<!ENTITY Chi \"&#935;\">"
            + "<!ENTITY chi \"&#967;\">"
            + "<!ENTITY Psi \"&#936;\">"
            + "<!ENTITY psi \"&#968;\">"
            + "<!ENTITY Omega \"&#937;\">"
            + "<!ENTITY omega \"&#969;\">"
            + "<!ENTITY thetasym \"&#977;\">"
            + "<!ENTITY upsih \"&#978;\">"
            + "<!ENTITY piv \"&#982;\">"
            + "<!ENTITY forall \"&#8704;\">"
            + "<!ENTITY part \"&#8706;\">"
            + "<!ENTITY exist \"&#8707;\">"
            + "<!ENTITY empty \"&#8709;\">"
            + "<!ENTITY nabla \"&#8711;\">"
            + "<!ENTITY isin \"&#8712;\">"
            + "<!ENTITY notin \"&#8713;\">"
            + "<!ENTITY ni \"&#8715;\">"
            + "<!ENTITY prod \"&#8719;\">"
            + "<!ENTITY sum \"&#8721;\">"
            + "<!ENTITY minus \"&#8722;\">"
            + "<!ENTITY lowast \"&#8727;\">"
            + "<!ENTITY radic \"&#8730;\">"
            + "<!ENTITY prop \"&#8733;\">"
            + "<!ENTITY infin \"&#8734;\">"
            + "<!ENTITY ang \"&#8736;\">"
            + "<!ENTITY and \"&#8743;\">"
            + "<!ENTITY or \"&#8744;\">"
            + "<!ENTITY cap \"&#8745;\">"
            + "<!ENTITY cup \"&#8746;\">"
            + "<!ENTITY int \"&#8747;\">"
            + "<!ENTITY there4 \"&#8756;\">"
            + "<!ENTITY sim \"&#8764;\">"
            + "<!ENTITY cong \"&#8773;\">"
            + "<!ENTITY asymp \"&#8776;\">"
            + "<!ENTITY ne \"&#8800;\">"
            + "<!ENTITY equiv \"&#8801;\">"
            + "<!ENTITY le \"&#8804;\">"
            + "<!ENTITY ge \"&#8805;\">"
            + "<!ENTITY sub \"&#8834;\">"
            + "<!ENTITY sup \"&#8835;\">"
            + "<!ENTITY nsub \"&#8836;\">"
            + "<!ENTITY sube \"&#8838;\">"
            + "<!ENTITY supe \"&#8839;\">"
            + "<!ENTITY oplus \"&#8853;\">"
            + "<!ENTITY otimes \"&#8855;\">"
            + "<!ENTITY perp \"&#8869;\">"
            + "<!ENTITY sdot \"&#8901;\">"
            + "<!ENTITY loz \"&#9674;\">"
            + "<!ENTITY lceil \"&#8968;\">"
            + "<!ENTITY rceil \"&#8969;\">"
            + "<!ENTITY lfloor \"&#8970;\">"
            + "<!ENTITY rfloor \"&#8971;\">"
            + "<!ENTITY lang \"&#9001;\">"
            + "<!ENTITY rang \"&#9002;\">"
            + "<!ENTITY larr \"&#8592;\">"
            + "<!ENTITY uarr \"&#8593;\">"
            + "<!ENTITY rarr \"&#8594;\">"
            + "<!ENTITY darr \"&#8595;\">"
            + "<!ENTITY harr \"&#8596;\">"
            + "<!ENTITY crarr \"&#8629;\">"
            + "<!ENTITY lArr \"&#8656;\">"
            + "<!ENTITY uArr \"&#8657;\">"
            + "<!ENTITY rArr \"&#8658;\">"
            + "<!ENTITY dArr \"&#8659;\">"
            + "<!ENTITY hArr \"&#8660;\">"
            + "<!ENTITY bull \"&#8226;\">"
            + "<!ENTITY prime \"&#8242;\">"
            + "<!ENTITY oline \"&#8254;\">"
            + "<!ENTITY frasl \"&#8260;\">"
            + "<!ENTITY weierp \"&#8472;\">"
            + "<!ENTITY image \"&#8465;\">"
            + "<!ENTITY real \"&#8476;\">"
            + "<!ENTITY trade \"&#8482;\">"
            + "<!ENTITY euro \"&#8364;\">"
            + "<!ENTITY alefsym \"&#8501;\">"
            + "<!ENTITY spades \"&#9824;\">"
            + "<!ENTITY clubs \"&#9827;\">"
            + "<!ENTITY hearts \"&#9829;\">"
            + "<!ENTITY diams \"&#9830;\">"
            + "<!ENTITY ensp \"&#8194;\">"
            + "<!ENTITY emsp \"&#8195;\">"
            + "<!ENTITY thinsp \"&#8201;\">"
            + "<!ENTITY zwnj \"&#8204;\">"
            + "<!ENTITY zwj \"&#8205;\">"
            + "<!ENTITY lrm \"&#8206;\">"
            + "<!ENTITY rlm \"&#8207;\">"
            + "<!ENTITY ndash \"&#8211;\">"
            + "<!ENTITY mdash \"&#8212;\">"
            + "<!ENTITY lsquo \"&#8216;\">"
            + "<!ENTITY rsquo \"&#8217;\">"
            + "<!ENTITY sbquo \"&#8218;\">"
            + "<!ENTITY ldquo \"&#8220;\">"
            + "<!ENTITY rdquo \"&#8221;\">"
            + "<!ENTITY bdquo \"&#8222;\">"
            + "<!ENTITY dagger \"&#8224;\">"
            + "<!ENTITY Dagger \"&#8225;\">"
            + "<!ENTITY hellip \"&#8230;\">"
            + "<!ENTITY permil \"&#8240;\">"
            + "<!ENTITY lsaquo \"&#8249;\">"
            + "<!ENTITY rsaquo \"&#8250;\">"
            + "]>\n";

    /**
     * The field {@code TODO_PATTERN} contains the pattern to recognize todos.
     */
    private static final Pattern TODO_PATTERN = Pattern
        .compile(".* TODO[: ].*");

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
            e.printStackTrace();
            System.err.println(e.toString());
            return -2;
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
                buffer.append("&");
                buffer.append(name);
                buffer.append(";");
                break;
            case Node.PROCESSING_INSTRUCTION_NODE:
            case Node.COMMENT_NODE:
            case Node.DOCUMENT_NODE:
            case Node.DOCUMENT_TYPE_NODE:
            case Node.DOCUMENT_FRAGMENT_NODE:
            case Node.NOTATION_NODE:
            default:

        }
    }

    /**
     * The field {@code bases} contains the list of base directories to
     * consider.
     */
    private List<File> bases = new ArrayList<File>();

    /**
     * The field {@code output} contains the output file name or
     * {@code null} for stdout.
     */
    private String output = null;

    /**
     * The field {@code omit} contains the list of omitted files and
     * directories.
     */
    private List<String> omit = new ArrayList<String>();

    /**
     * The field {@code xslt} contains the name of the xslt resource.
     */
    private String xslt = PrimitiveCollector.class.getName().replace('.', '/');

    /**
     * The field {@code configs} contains the collected configurations.
     */
    private Map<String, Info> configs = null;

    /**
     * The field {@code units} contains the collected configurations.
     */
    private Map<String, Info> units = null;

    /**
     * The field {@code builder} contains the encapsulated document builder.
     */
    private DocumentBuilder builder;

    /**
     * The field {@code docs} contains the collected docs.
     */
    private Map<String, Info> docs = null;

    /**
     * The field {@code verbose} contains the verbosity indicator.
     */
    private boolean verbose = false;

    /**
     * The field {@code defaultType} contains the default type.
     */
    private String defaultType = "primitive";

    /**
     * The field {@code configurationsFileName} contains the name of the file i
     * which the configurations are written.
     */
    private String configurationsFileName = "configurations.tex";

    /**
     * The field {@code unitsFileName} contains the name of the file on which
     * the units are written.
     */
    private String unitsFileName = "units.tex";

    /**
     * The field {@code primitivesFileName} contains the file name foe the
     * primitives.
     */
    private String primitivesFileName = "primitives.tex";

    /**
     * The field {@code syntaxFileName} contains the file name for the syntax
     * file.
     */
    private String syntaxFileName = "syntax.tex";

    /**
     * Creates a new object.
     * 
     * @throws ParserConfigurationException in case of an error
     */
    public PrimitiveCollector() throws ParserConfigurationException {

        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * Add a base directory.
     * 
     * @param base the base directory
     * 
     * @throws FileNotFoundException in case the given base is not a directory
     */
    public void addBase(String base) throws FileNotFoundException {

        File dir = new File(base);
        if (!dir.isDirectory()) {
            throw new FileNotFoundException(base);
        }
        bases.add(dir);
    }

    /**
     * Traverse a directory tree and search for Java and package.html files.
     * 
     * @return the docs encountered
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public Map<String, Info> collectDocs() throws IOException, SyntaxException {

        if (docs != null) {
            return docs;
        }

        docs = new HashMap<String, Info>();

        for (File base : bases) {
            File dir = new File(base, "src/main/java");
            if (dir.isDirectory()) {
                traverseSource(dir);
            }
        }
        return docs;
    }

    /**
     * Traverse a directory hierarchy and collect whatever is needed.
     * 
     * @return the units encountered
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public Map<String, Info> collectXml() throws IOException, SyntaxException {

        if (units != null) {
            return units;
        }

        configs = new HashMap<String, Info>();
        units = new HashMap<String, Info>();
        docs = new HashMap<String, Info>();

        for (File dir : bases) {
            if (dir.isDirectory()) {
                traverse(dir);
            }
        }

        return units;
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
     * Post-process a buffer to translate it to XML.
     * 
     * @param buffer the buffer; it is modified in the course of translation
     * 
     * @return the XML form
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
     * @param inJava indicator, whether the JavaDoc prefix needs to be removed
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void processDoc(File f, boolean inJava)
            throws IOException,
                SyntaxException {

        LineNumberReader reader = new LineNumberReader(new FileReader(f));
        String clazz = "";

        try {
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                int i = s.indexOf("<doc");
                if (i >= 0 && DOC_PATTERN.matcher(s).matches()) {
                    processDoc(reader, f, clazz, s.substring(i + 4), inJava);
                } else if (inJava && s.matches("package .*;")) {
                    clazz =
                            s.substring(7, s.length() - 1).trim()
                                    + "."
                                    + f.getName().replaceAll(
                                        "\\.[a-zA-Z0-9]*$", "");
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
     * @param clazz the name of the class
     * @param start the start string
     * @param inJava indicator, whether the JavaDoc prefix needs to be removed
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void processDoc(LineNumberReader reader, File f, String clazz,
            String start, boolean inJava) throws IOException, SyntaxException {

        if (verbose) {
            System.err.print("--- ");
            System.err.println(f.toString());
        }

        StringBuilder buffer = new StringBuilder(XML_INIT);
        buffer.append("<doc class=\"").append(clazz).append("\"");
        if (!start.contains(" type=")) {
            buffer.append(" type=\"" + defaultType + "\"");
        }
        buffer.append(start);
        buffer.append('\n');

        for (String s = reader.readLine(); s != null; s = reader.readLine()) {
            if (inJava) {
                s = s.replaceAll("^ *\\*[ ]?", "");
            }
            int i = s.indexOf("</doc>");
            if (i >= 0) {
                buffer.append(s.substring(0, i + 6));
                s = buffer.toString();
                s =
                        s.replaceAll(
                            "<logo>L<span[^<>]*>a</span><span[^<>]*>T</span><span[^<>]*>e</span>X</logo>",
                            "<logo>LaTeX</logo>");
                s =
                        s.replaceAll(
                            "<logo>&epsilon;&chi;T<span[^<>]*>e</span>X</logo>",
                            "<logo>ExTeX</logo>");
                s =
                        s.replaceAll(
                            "<logo>&epsilon;-T<span[^<>]*>e</span>X</logo>",
                            "<logo>eTeX</logo>");
                s =
                        s.replaceAll("<logo>T<span[^<>]*>e</span>X</logo>",
                            "<logo>TeX</logo>");
                try {
                    Element root = builder.parse(new StringInputStream(s))
                        .getDocumentElement();
                    String name = root.getAttribute("name");
                    if (!"".equals(name)) {
                        docs.put(name + " " + clazz, new Info(f, root));
                    }
                } catch (SAXException e) {
                    throw new SyntaxException(f.toString(),
                        e.getLocalizedMessage());
                }

                return;
            } else if (TODO_PATTERN.matcher(s).matches()) {
                buffer.append("<todo>");
                buffer.append(s.substring(s.indexOf("TODO") + 5));
                buffer.append("</todo>");
            } else {
                buffer.append(s);
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
            try {
                Element root = builder.parse(f).getDocumentElement();
                configs.put(f.getName().replaceAll(".xml$", ""),
                    new Info(f, root));
            } catch (SAXException e) {
                throw new SyntaxException(f.toString(), e.getLocalizedMessage());
            }
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
     * Process a unit.
     * 
     * @param f the file
     * @param name the name
     * @param root the root node
     * @param dir the base directory
     */
    private void processUnit(File f, String name, Element root, File dir) {

        units.put(name.replaceAll(".xml$", ""), new Info(f, root));
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

        Writer writer;

        if (output != null) {
            File outDir = output == null ? null : new File(output);
            outDir.mkdirs();
            writer =
                    new OutputStreamWriter(new FileOutputStream(new File(
                        outDir, configurationsFileName)), "ISO-8859-1");
        } else {
            writer = new OutputStreamWriter(System.out);
        }
        try {
            writeConfigs(writer, "config");
        } finally {
            writer.flush();
            if (output != null) {
                writer.close();
            }
        }

        writer =
                output == null || unitsFileName == null
                        ? new OutputStreamWriter(System.out)
                        : new FileWriter(new File(output, unitsFileName));
        try {
            writeUnits(writer);
        } finally {
            writer.flush();
            if (output != null) {
                writer.close();
            }
        }

        writer =
                output == null || syntaxFileName == null
                        ? new OutputStreamWriter(System.out)
                        : new FileWriter(new File(output, syntaxFileName));
        try {
            writeDocs(writer, "syntax");
        } finally {
            writer.flush();
            if (output != null) {
                writer.close();
            }
        }

        writer =
                output == null || primitivesFileName == null
                        ? new OutputStreamWriter(System.out)
                        : new OutputStreamWriter(new FileOutputStream(new File(
                            output, primitivesFileName)), "ISO-8859-1");
        try {
            writeDocs(writer, "primitive");
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
                    addBase(nextArgument(args, ++i));
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
                addBase(arg);
            }
        }

        run();
    }

    /**
     * Setter for the output. The empty string and the string containing a '-'
     * only are treated as {@code null}.
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
     * @param dir the file to consider
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void traverse(File dir) throws IOException, SyntaxException {

        if (omit.contains(dir.getName())) {
            return;
        }

        File pom = new File(dir, "pom.xml");
        if (pom.exists()) {
            File config =
                    new File(new File(new File(new File(dir, "src"), "main"),
                        "resources"), "config");
            if (config.isDirectory()) {
                processModule(pom, config);
            }

            File src = new File(new File(new File(dir, "src"), "main"), "java");
            if (src.isDirectory()) {
                traverseSource(src);
            }

            for (File f : dir.listFiles(DIR_FILTER)) {
                traverse(f);
            }
        }
    }

    /**
     * Traverse a directory tree and process the java files.
     * 
     * @param dir the directory to start with
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private void traverseSource(File dir) throws IOException, SyntaxException {

        for (File f : dir.listFiles()) {
            if (f.isFile()) {
                String name = f.getName();
                if (name.endsWith(".java")) {
                    processDoc(f, true);
                } else if (name.endsWith(".html")) {
                    processDoc(f, false);
                }
            } else if (f.isDirectory()) {
                traverseSource(f);
            }
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

        String tags = tag + (tag.matches(".*[aeijklmnopqrtuvwy]") ? "s" : "es");

        StringBuilder buffer = new StringBuilder();
        buffer.append('<');
        buffer.append(tags);
        buffer.append('>');

        Object[] ks = map.keySet().toArray();
        Arrays.sort(ks, new Comparator<Object>() {

            public int compare(Object o1, Object o2) {

                return o1.toString().compareToIgnoreCase(o2.toString());
            }
        });
        for (Object key : ks) {
            Info config = map.get(key);
            buffer.append('<');
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
        buffer.append(tags);
        buffer.append(">\n");

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

        transformer.transform(new StreamSource(new StringReader(
            javadoc2xml(buffer))), new StreamResult(writer));
    }

    /**
     * Traverse a set of directory trees collecting POMs and write the
     * transformed result to the given writer.
     * 
     * @param writer the writer for the output
     * @param tagName name of the outer XML tag
     * 
     * @throws IOException in case of an I/O error
     * @throws TransformerException in case of an error in the transformer
     * @throws SyntaxException in case of a syntax error
     */
    public void writeConfigs(Writer writer, String tagName)
            throws IOException,
                TransformerException,
                SyntaxException {

        if (configs == null) {
            collectXml();
        }
        write(writer, configs, tagName);
    }

    /**
     * Traverse a set of directory trees collecting POMs and write the
     * transformed result to the given writer.
     * 
     * @param writer the writer for the output
     * @param docTag name of the outer doc tag
     * 
     * @throws IOException in case of an I/O error
     * @throws TransformerException in case of an error in the transformer
     * @throws SyntaxException in case of a syntax error
     */
    public void writeDocs(Writer writer, String docTag)
            throws IOException,
                TransformerException,
                SyntaxException {

        write(writer, collectDocs(), docTag);
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

        write(writer, collectXml(), "unit");
    }

}
