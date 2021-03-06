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
import org.extex.exdoc.util.Key;
import org.extex.exdoc.util.Traverser;
import org.xml.sax.SAXException;

/**
 * Extract doc tags from sources and translate them to LaTeX.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ExDocTeX extends ExDocXml {

    /**
     * The field {@code ENTITY_MAP} contains the mapping for entities.
     */
    private static final Map<String, String> ENTITY_MAP =
            new HashMap<String, String>();

    /**
     * The field {@code XSLT} contains the name of the XSLT file to use.
     */
    private static final String XSLT = "org/extex/exdoc/xslt/xml2tex.xsl";

    static {
        ENTITY_MAP.put("amp", "&amp;");
        ENTITY_MAP.put("gt", "&gt;");
        ENTITY_MAP.put("lt", "&lt;");
        ENTITY_MAP.put("apos", "'");
        ENTITY_MAP.put("quod", "''");

        ENTITY_MAP.put("AElig", "\\AE{}");
        ENTITY_MAP.put("Aacute", "\\'A");
        ENTITY_MAP.put("Acirc", "\\^A");
        ENTITY_MAP.put("Agrave", "\\`A");
        ENTITY_MAP.put("Alpha", "\\ensuremath{A}");
        ENTITY_MAP.put("Aring", "\\AA{}");
        ENTITY_MAP.put("Atilde", "\\~A");
        ENTITY_MAP.put("Auml", "\\\"A");
        ENTITY_MAP.put("Beta", "\\ensuremath{B}");
        ENTITY_MAP.put("Ccedil", "\\c C");
        ENTITY_MAP.put("Chi", "\\ensuremath{X}");
        ENTITY_MAP.put("Dagger", "\\ddagger{}");
        ENTITY_MAP.put("Delta", "\\ensuremath{\\Delta}");
        // ENTITY_MAP.put("ETH", "&amp;ETH;");
        ENTITY_MAP.put("Eacute", "\\'E");
        ENTITY_MAP.put("Ecirc", "\\^E");
        ENTITY_MAP.put("Egrave", "\\`E");
        ENTITY_MAP.put("Epsilon", "\\ensuremath{E}");
        ENTITY_MAP.put("Eta", "\\ensuremath{H}");
        ENTITY_MAP.put("Euml", "\\\"E");
        ENTITY_MAP.put("Gamma", "\\ensuremath{\\Gamma}");
        ENTITY_MAP.put("Iacute", "\\'I");
        ENTITY_MAP.put("Icirc", "\\^I");
        ENTITY_MAP.put("Igrave", "\\`I");
        ENTITY_MAP.put("Iota", "\\ensuremath{I}");
        ENTITY_MAP.put("Iuml", "\\\"I");
        ENTITY_MAP.put("Kappa", "\\ensuremath{K}");
        ENTITY_MAP.put("Lambda", "\\ensuremath{\\Lambda}");
        ENTITY_MAP.put("Mu", "\\ensuremath{M}");
        ENTITY_MAP.put("Ntilde", "\\~N");
        ENTITY_MAP.put("Nu", "\\ensuremath{N}");
        ENTITY_MAP.put("Oacute", "\\'O");
        ENTITY_MAP.put("Ocirc", "\\^O");
        ENTITY_MAP.put("Ograve", "\\`O");
        ENTITY_MAP.put("Omega", "\\ensuremath{\\Omega}");
        ENTITY_MAP.put("Omicron", "\\ensuremath{O}");
        ENTITY_MAP.put("Oslash", "\\O{}");
        ENTITY_MAP.put("Otilde", "\\~O");
        ENTITY_MAP.put("Ouml", "\\\"O");
        ENTITY_MAP.put("Phi", "\\ensuremath{\\Phi}");
        ENTITY_MAP.put("Pi", "\\ensuremath{\\Pi}");
        ENTITY_MAP.put("Psi", "\\ensuremath{\\Psi}");
        ENTITY_MAP.put("Rho", "\\ensuremath{\\Rho}");
        ENTITY_MAP.put("Sigma", "\\ensuremath{\\Sigma}");
        // ENTITY_MAP.put("THORN", "&amp;THORN;");
        ENTITY_MAP.put("Tau", "\\ensuremath{\\Tau}");
        ENTITY_MAP.put("Theta", "\\ensuremath{\\Theta}");
        ENTITY_MAP.put("Uacute", "\\'U");
        ENTITY_MAP.put("Ucirc", "\\^U");
        ENTITY_MAP.put("Ugrave", "\\`U");
        ENTITY_MAP.put("Upsilon", "\\ensuremath{Y}");
        ENTITY_MAP.put("Uuml", "\\\"U");
        ENTITY_MAP.put("Xi", "\\ensuremath{\\Xi}");
        ENTITY_MAP.put("Yacute", "\\'Y");
        ENTITY_MAP.put("Zeta", "\\ensuremath{\\Zeta}");
        ENTITY_MAP.put("aacute", "\\'a");
        ENTITY_MAP.put("acirc", "\\^a");
        ENTITY_MAP.put("acute", "\\'a");
        ENTITY_MAP.put("aelig", "\\ae{}");
        ENTITY_MAP.put("agrave", "\\`a");
        ENTITY_MAP.put("alefsym", "\\ensuremath{\\alef}");
        ENTITY_MAP.put("alpha", "\\ensuremath{\\alpha}");
        ENTITY_MAP.put("and", "\\ensuremath{\\land}");
        ENTITY_MAP.put("ang", "\\ensuremath{\\ang}");
        ENTITY_MAP.put("aring", "\\aa{}");
        // ENTITY_MAP.put("asymp", "&amp;asymp;");
        ENTITY_MAP.put("atilde", "\\~a");
        ENTITY_MAP.put("auml", "\\\"a");
        // ENTITY_MAP.put("bdquo", "&amp;bdquo;");
        ENTITY_MAP.put("beta", "\\ensuremath{\\beta}");
        // ENTITY_MAP.put("brvbar", "&amp;brvbar;");
        ENTITY_MAP.put("bull", "\\ensuremath{\\bullet}");
        ENTITY_MAP.put("cap", "\\ensuremath{\\cap}");
        ENTITY_MAP.put("ccedil", "\\c c");
        ENTITY_MAP.put("cedil", "\\C{}");
        ENTITY_MAP.put("cent", "\\textcent{}");
        ENTITY_MAP.put("chi", "\\ensuremath{\\chi}");
        ENTITY_MAP.put("clubs", "\\clubsuit{}");
        // ENTITY_MAP.put("cong", "&amp;cong;");
        ENTITY_MAP.put("copy", "\\copyright{}");
        // ENTITY_MAP.put("crarr", "&amp;crarr;");
        ENTITY_MAP.put("cup", "\\ensuremath{\\cup}");
        ENTITY_MAP.put("curren", "\\textcurrency{}");
        ENTITY_MAP.put("dArr", "\\ensuremath{\\Downarrow}");
        ENTITY_MAP.put("dagger", "\\dagger{}");
        ENTITY_MAP.put("darr", "\\ensuremath{\\downarrow}");
        ENTITY_MAP.put("deg", "\\textdegree{}");
        ENTITY_MAP.put("delta", "\\ensuremath{\\delta}");
        ENTITY_MAP.put("diams", "\\diamondsuit{}");
        ENTITY_MAP.put("divide", "\\ensuremath{\\div}");
        ENTITY_MAP.put("eacute", "\\'e");
        ENTITY_MAP.put("ecirc", "\\^e");
        ENTITY_MAP.put("egrave", "\\`e");
        ENTITY_MAP.put("empty", "\\ensuremath{\\emptyset}");
        ENTITY_MAP.put("emsp", "\\qquad ");
        ENTITY_MAP.put("ensp", "\\quad ");
        ENTITY_MAP.put("epsilon", "\\ensuremath{\\epsilon}");
        ENTITY_MAP.put("equiv", "\\ensuremath{\\equiv}");
        ENTITY_MAP.put("eta", "\\ensuremath{\\eta}");
        // ENTITY_MAP.put("eth", "&amp;eth;");
        ENTITY_MAP.put("euml", "\\\"e");
        ENTITY_MAP.put("euro", "\\texteuro{}");
        ENTITY_MAP.put("exist", "\\ensuremath{\\exists}");
        ENTITY_MAP.put("forall", "\\ensuremath{\\forall}");
        ENTITY_MAP.put("frac12", "\\textonehalf{}");
        ENTITY_MAP.put("frac14", "\\textonequarter{}");
        ENTITY_MAP.put("frac34", "\\textthreequarters{}");
        ENTITY_MAP.put("frasl", "/");
        ENTITY_MAP.put("gamma", "\\ensuremath{\\gamma}");
        ENTITY_MAP.put("ge", "\\ensuremath{\\geq}");
        ENTITY_MAP.put("hArr", "\\ensuremath{\\Leftrightarrow}");
        ENTITY_MAP.put("harr", "\\ensuremath{\\leftrightarrow}");
        ENTITY_MAP.put("hearts", "\\heartsuit{}");
        ENTITY_MAP.put("hellip", "\\dots ");
        ENTITY_MAP.put("iacute", "\\'i");
        ENTITY_MAP.put("icirc", "\\^i");
        ENTITY_MAP.put("iexcl", "!'");
        ENTITY_MAP.put("igrave", "\\`i");
        ENTITY_MAP.put("image", "\\ensuremath{\\Im}");
        ENTITY_MAP.put("infin", "\\ensuremath{\\infty}");
        ENTITY_MAP.put("int", "\\ensuremath{\\int}");
        ENTITY_MAP.put("iota", "\\ensuremath{\\iota}");
        ENTITY_MAP.put("iquest", "?'");
        ENTITY_MAP.put("isin", "\\ensuremath{\\in}");
        ENTITY_MAP.put("iuml", "\\\"i");
        ENTITY_MAP.put("kappa", "\\ensuremath{\\kappa}");
        ENTITY_MAP.put("lArr", "\\ensuremath{\\Leftarrow}");
        ENTITY_MAP.put("lambda", "\\ensuremath{\\lambda}");
        ENTITY_MAP.put("lang", "\\tag{");
        ENTITY_MAP.put("laquo", "\"`"); // babel
        ENTITY_MAP.put("larr", "\\ensuremath{\\leftarrow}");
        ENTITY_MAP.put("lceil", "\\ensuremath{\\lceil}");
        ENTITY_MAP.put("ldquo", "``");
        ENTITY_MAP.put("le", "\\ensuremath{\\leq}");
        ENTITY_MAP.put("lfloor", "\\ensuremath{\\lfloor}");
        ENTITY_MAP.put("lowast", "\\ensuremath{\\ast}");
        // ENTITY_MAP.put("loz", "&amp;loz;");
        // ENTITY_MAP.put("lrm", "&amp;lrm;");
        // ENTITY_MAP.put("lsaquo", "&amp;lsaquo;");
        // ENTITY_MAP.put("lsquo", "&amp;lsquo;");
        ENTITY_MAP.put("macr", "\\textmacron{}");
        ENTITY_MAP.put("mdash", "---");
        ENTITY_MAP.put("micro", "&amp;micro;");
        ENTITY_MAP.put("middot", "\\cdot{}");
        ENTITY_MAP.put("minus", "\\ensuremath{-}");
        ENTITY_MAP.put("mu", "\\ensuremath{\\mu}");
        ENTITY_MAP.put("nabla", "\\ensuremath{\\nabla}");
        ENTITY_MAP.put("nbsp", "~");
        ENTITY_MAP.put("ndash", "--");
        ENTITY_MAP.put("ne", "\\ensuremath{\\neq}");
        ENTITY_MAP.put("ni", "\\ensuremath{\\ni}");
        ENTITY_MAP.put("not", "\\ensuremath{\\neg}");
        ENTITY_MAP.put("notin", "\\ensuremath{\\not\\in}");
        ENTITY_MAP.put("nsub", "\\ensuremath{\\not\\subset}");
        ENTITY_MAP.put("ntilde", "\\~n");
        ENTITY_MAP.put("nu", "\\ensuremath{\\nu}");
        ENTITY_MAP.put("oacute", "\\'o");
        ENTITY_MAP.put("ocirc", "\\^o");
        ENTITY_MAP.put("ograve", "\\`o");
        ENTITY_MAP.put("oline", "\\textmacron{}");
        ENTITY_MAP.put("omega", "\\ensuremath{\\omega}");
        ENTITY_MAP.put("omicron", "\\ensuremath{o}");
        ENTITY_MAP.put("oplus", "\\ensuremath{\\oplus}");
        ENTITY_MAP.put("or", "\\ensuremath{\\lor}");
        ENTITY_MAP.put("ordf", "\\textordfeminine{}");
        ENTITY_MAP.put("ordm", "\\textordmasculine{}");
        ENTITY_MAP.put("oslash", "\\o{}");
        ENTITY_MAP.put("otilde", "\\~o");
        ENTITY_MAP.put("otimes", "\\ensuremath{\\otimes}");
        ENTITY_MAP.put("ouml", "\\\"o");
        ENTITY_MAP.put("para", "\\P{}");
        ENTITY_MAP.put("part", "\\ensuremath{\\partial}");
        ENTITY_MAP.put("permil", "\textperthousand{}");
        // ENTITY_MAP.put("perp", "&amp;perp;");
        ENTITY_MAP.put("phi", "\\ensuremath{\\phi}");
        ENTITY_MAP.put("pi", "\\ensuremath{\\pi}");
        ENTITY_MAP.put("piv", "\\ensuremath{\\varpi}");
        ENTITY_MAP.put("plusmn", "\\ensuremath{\\pm}");
        ENTITY_MAP.put("pound", "\\pounds{}");
        ENTITY_MAP.put("prime", "\\ensuremath{'}");
        ENTITY_MAP.put("prod", "\\ensuremath{prod}");
        // ENTITY_MAP.put("prop", "&amp;prop;");
        ENTITY_MAP.put("psi", "\\ensuremath{\\psi}");
        ENTITY_MAP.put("rArr", "\\ensuremath{\\Rightarrow}");
        ENTITY_MAP.put("radic", "\\ensuremath{\\sqrt{}}");
        ENTITY_MAP.put("rang", "}");
        ENTITY_MAP.put("raquo", "\"'");
        ENTITY_MAP.put("rarr", "\\ensuremath{\\rightarrow}");
        ENTITY_MAP.put("rceil", "\\ensuremath{\\rceil}");
        ENTITY_MAP.put("rdquo", "''");
        ENTITY_MAP.put("real", "\\ensuremath{\\Re}");
        ENTITY_MAP.put("reg", "\\textregistered{}");
        ENTITY_MAP.put("rfloor", "\\ensuremath{\\rfloor}");
        ENTITY_MAP.put("rho", "\\ensuremath{\\rho}");
        // ENTITY_MAP.put("rlm", "&amp;rlm;");
        // ENTITY_MAP.put("rsaquo", "&amp;rsaquo;");
        // ENTITY_MAP.put("rsquo", "&amp;rsquo;");
        // ENTITY_MAP.put("sbquo", "&amp;sbquo;");
        ENTITY_MAP.put("sdot", "\\ensuremath{\\cdot}");
        ENTITY_MAP.put("sect", "\\S{}");
        ENTITY_MAP.put("shy", "\\-");
        ENTITY_MAP.put("sigma", "\\ensuremath{\\sigma}");
        // ENTITY_MAP.put("sigmaf", "&amp;sigmaf;");
        ENTITY_MAP.put("sim", "\\ensuremath{\\sim}");
        ENTITY_MAP.put("spades", "\\spadesuit{}");
        ENTITY_MAP.put("sub", "\\ensuremath{\\subset}");
        ENTITY_MAP.put("sube", "\\ensuremath{\\subseteq}");
        ENTITY_MAP.put("sum", "\\ensuremath{\\sum}");
        ENTITY_MAP.put("sup", "\\ensuremath{\\supset}");
        ENTITY_MAP.put("sup1", "\\ensuremath{^1}");
        ENTITY_MAP.put("sup2", "\\ensuremath{^2}");
        ENTITY_MAP.put("sup3", "\\ensuremath{^3}");
        ENTITY_MAP.put("supe", "\\ensuremath{\\supseteq}");
        ENTITY_MAP.put("szlig", "\\ss{}");
        ENTITY_MAP.put("tau", "\\ensuremath{\\tau}");
        // ENTITY_MAP.put("there4", "&amp;there4;");
        ENTITY_MAP.put("theta", "\\ensuremath{\\theta}");
        ENTITY_MAP.put("thetasym", "\\ensuremath{\\vartheta}");
        ENTITY_MAP.put("thinsp", "\\,");
        // ENTITY_MAP.put("thorn", "&amp;thorn;");
        ENTITY_MAP.put("times", "\\ensuremath{times}");
        ENTITY_MAP.put("tilde", "\\~{}");
        ENTITY_MAP.put("trade", "\\texttrademark");
        ENTITY_MAP.put("uArr", "\\ensuremath{\\Uparrow}");
        ENTITY_MAP.put("uacute", "\\'u");
        ENTITY_MAP.put("uarr", "\\ensuremath{\\uparrow}");
        ENTITY_MAP.put("ucirc", "\\^u");
        ENTITY_MAP.put("ugrave", "\\`u");
        ENTITY_MAP.put("uml", "\\\"{}");
        ENTITY_MAP.put("upsih", "\\ensuremath{\\Upsilon}");
        ENTITY_MAP.put("upsilon", "\\ensuremath{\\upsilon}");
        ENTITY_MAP.put("uuml", "\\\"u");
        ENTITY_MAP.put("weierp", "\\ensuremath{\\wp}");
        ENTITY_MAP.put("xi", "\\ensuremath{\\xi}");
        ENTITY_MAP.put("yacute", "\\'y");
        ENTITY_MAP.put("yen", "\\textyen{}");
        ENTITY_MAP.put("yuml", "\\\"y");
        ENTITY_MAP.put("zeta", "\\ensuremath{\\zeta}");
        ENTITY_MAP.put("zwj", "\\relax ");
        ENTITY_MAP.put("zwnj", "\"\"");
    }

    /**
     * The main program for this class.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger logger = Traverser.createLogger();

        try {
            new ExDocTeX(logger).run(args);
        } catch (Exception e) {
            logger.severe(e.toString());
        }
    }

    /**
     * The field {@code keys} contains the ...
     */
    private List<Key> keys = new ArrayList<Key>();

    /**
     * The field {@code transformer} contains the xslt engine.
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
    public ExDocTeX()
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
    public ExDocTeX(Logger logger)
            throws ParserConfigurationException,
                TransformerConfigurationException,
                TransformerFactoryConfigurationError,
                IOException {

        this();
        setLogger(logger);
    }

    /**
     * Process the list of resources and authors.
     * 
     * @param file the target file or {@code null}
     * @param authors the list of authors
     * 
     * @throws IOException in case of an I/O error
     */
    @Override
    protected void shipoutAuthors(String file, Map<String, Author> authors)
            throws IOException {

        // not for the printed document
    }

    /**
     * Replace all strings of a given form into something new.
     * 
     * @param content the content to transform
     * @param from the string to be replaced
     * @param to the new string to be inserted
     */
    private void replace(StringBuilder content, String from, String to) {

        int length = from.length();

        for (int i = content.indexOf(from); i >= 0; i =
                content.indexOf(from, i + to.length())) {
            content.replace(i, i + length, to);
        }
    }

    /**
     * Replace HTML entities of the form {@code &amp;&lang;name&rang;;} to
     * appropriate values.
     * 
     * @param content the content to transform
     * @param name the name of the resource currently processed
     */
    private void replaceEntities(StringBuilder content, Key name) {

        int j;
        for (int i = content.indexOf("&"); i >= 0; i = content.indexOf("&", j)) {
            j = content.indexOf(";", i);
            String entity = content.substring(i + 1, j);
            String to;
            if (entity.charAt(0) == '#') {
                int c;
                if (entity.charAt(1) == 'x') {
                    c = Integer.parseInt(entity.substring(2), 16);
                } else {
                    c = Integer.parseInt(entity.substring(1));
                }
                to = Character.toString((char) c);
            } else {
                to = ENTITY_MAP.get(entity);
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
    private void replaceTodo(StringBuilder content) {

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
    @Override
    public void run(String[] args) throws Exception {

        super.run(args);

        Collections.sort(keys, new Comparator<Key>() {

            /**
             * Compare two objects.
             * 
             * @param o1 the first object
             * @param o2 the second object
             * 
             * @return -1, 0, 1 depending on the order of the objects
             */
            public int compare(Key o1, Key o2) {

                return o1.toString().compareTo(o2.toString());
            }
        });

        for (Key k : keys) {
            // System.err.println(k.toString());
        }
    }

    /**
     * Ship the given content to the appropriate file.
     * 
     * @param key the name of the resource
     * @param content the output stream in form of a string buffer
     * 
     * @throws IOException in case of an I/O error
     * 
     * @see org.extex.exdoc.ExDocXml#shipout(org.extex.exdoc.util.Key,
     *      java.lang.StringBuilder)
     */
    @Override
    protected void shipout(Key key, StringBuilder content) throws IOException {

        File file = new File(getOutput(), key + ".tex");
        FileOutputStream out = new FileOutputStream(file);
        info("-> " + file.toString());

        replace(content, "{", "\\{");
        replace(content, "}", "\\}");
        replace(content, "\\", "\\textbackslash{}");
        replace(content, "$", "\\$");
        replace(content, "_", "\\_$");
        replace(content, "~", "\\~{}");
        replace(content, "%", "\\%");

        replace(content, "<tt>\\", "<tt class=\"macro\">\\");

        replaceEntities(content, key);
        replaceTodo(content);

        try {
            transformer.transform(new DOMSource(scan(content)),
                new StreamResult(out));

        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        keys.add(key);
    }

}
