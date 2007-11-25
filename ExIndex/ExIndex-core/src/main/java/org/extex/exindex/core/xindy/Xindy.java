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

package org.extex.exindex.core.xindy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.extex.exindex.core.normalizer.Collator;
import org.extex.exindex.core.parser.Parser;
import org.extex.exindex.core.type.Index;
import org.extex.exindex.core.type.alphabet.AlphaLowercase;
import org.extex.exindex.core.type.alphabet.AlphaUppercase;
import org.extex.exindex.core.type.alphabet.ArabicNumbers;
import org.extex.exindex.core.type.alphabet.Digits;
import org.extex.exindex.core.type.alphabet.RomanNumeralsLowercase;
import org.extex.exindex.core.type.alphabet.RomanNumeralsUppercase;
import org.extex.exindex.lisp.LEngine;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.parser.LParser;

/**
 * This class provides an LInterpreter with the functions needed by Xindy
 * defined.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Xindy extends LEngine {

    /**
     * The field <tt>index</tt> contains the index.
     */
    private Index index;

    /**
     * The field <tt>parser</tt> contains the parser.
     */
    private Parser parser;

    /**
     * Creates a new object.
     * 
     * @throws NoSuchMethodException in case of an undefined method in a
     *         function definition
     * @throws SecurityException in case of an security problem
     */
    public Xindy() throws SecurityException, NoSuchMethodException {

        super();
        index = new Index();
        register("define-alphabet", //
            new LDefineAlphabet("define-alphabet"));
        register("define-attributes", //
            new LDefineAttributes("define-attributes"));
        register("define-letter-group", //
            new LDefineLetterGroup("define-letter-group"));
        register("define-letter-groups", //
            new LDefineLetterGroups("define-letter-groups"));
        register("define-location-class", //
            new LDefineLocationClass("define-location-class"));
        register("define-location-class-order", //
            new LDefineLocationClassOrder("define-location-class-order"));
        register("define-crossref-class", //
            new LDefineCrossrefClass("define-crossref-class"));
        register("searchpath", //
            new LSearchpath("searchpath"));
        register("sort-rule", //
            new LSortRule("sort-rule"));
        register("merge-rule", //
            new LMergeRule("merge-rule"));
        register("merge-to", //
            new LMergeTo("merge-to"));
        register("use-rule-set", //
            new LUseRuleSet("use-rule-set"));

        register("markup-attribute-group-list", //
            new LMarkupAttributeGroupList("markup-attribute-group-list"));
        register("markup-attribute-group", //
            new LMarkupAttributeGroup("markup-attribute-group"));
        register("markup-crossref-layer", //
            new LMarkupCrossrefLayer("markup-crossref-layer"));
        register("markup-crossref-layer-list", //
            new LMarkupCrossrefLayerList("markup-crossref-layer-list"));
        register("markup-crossref-list", //
            new LMarkupCrossrefList("markup-crossref-list"));
        register("markup-index", //
            new LMarkupIndex("markup-index"));
        register("markup-indexentry", //
            new LMarkupIndexEntry("markup-indexentry"));
        register("markup-indexentry-list", //
            new LMarkupIndexEntryList("markup-indexentry-list"));
        register("markup-keyword", //
            new LMarkupKeyword("markup-keyword"));
        register("markup-keyword-list", //
            new LMarkupKeywordList("markup-keyword-list"));
        register("markup-letter-group", //
            new LMarkupLetterGroup("markup-letter-group"));
        register("markup-letter-group-list", //
            new LMarkupLetterGroupList("markup-letter-group-list"));
        register("markup-locclass-list", //
            new LMarkupLocclassList("markup-locclass-list"));
        register("markup-locref-list", //
            new LMarkupLocrefList("markup-locref-list"));
        register("markup-locref", //
            new LMarkupLocref("markup-locref"));
        register("markup-locref-layer", //
            new LMarkupLocrefLayer("markup-locref-layer"));
        register("markup-locref-layer-list", //
            new LMarkupLocrefLayerList("markup-locref-layer-list"));
        register("markup-range", //
            new LMarkupRange("markup-range"));
        register("markup-trace", //
            new LMarkupTrace("markup-trace"));

        setq("alphabet:arabic-numbers", new ArabicNumbers());
        setq("alphabet:roman-numerals-uppercase", new RomanNumeralsUppercase());
        setq("alphabet:roman-numerals-lowercase", new RomanNumeralsLowercase());
        setq("alphabet:digits", new Digits());
        setq("alphabet:alpha", new AlphaLowercase());
        setq("alphabet:ALPHA", new AlphaUppercase());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.LInterpreter#makeParser(java.lang.String,
     *      java.io.Reader)
     */
    @Override
    protected LParser makeParser(String name, Reader reader) {

        LParser parser = super.makeParser(name, reader);
        parser.setEscape('~');
        return parser;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    protected void markup(Writer writer) {

        // TODO gene: markup unimplemented

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param data the list of raw data files
     * 
     * @throws IOException in case of an I/O error
     * @throws FileNotFoundException if an input file could not be found
     */
    protected void process(List<String> data)
            throws FileNotFoundException,
                IOException {

        Collator collator = null; // TODO
        for (String file : data) {
            FileReader reader = new FileReader(file);
            try {
                parser.load(reader, file, index, collator);
            } finally {
                reader.close();
            }
        }
        // TODO
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param styles the list of styles to use
     * @param data the list of raw data files
     * @param writer the writer for output
     * 
     * @throws LException in case of an error in the L system
     * @throws IOException in case of an I/O error
     */
    public void run(List<String> styles, List<String> data, Writer writer)
            throws IOException,
                LException {

        startup(styles);
        process(data);
        markup(writer);
    }

    /**
     * Load the style files and prepare the engine to get started.
     * 
     * @param styles the list of styles to use
     * 
     * @throws LException in case of an error in the L system
     * @throws IOException in case of an I/O error
     */
    protected void startup(List<String> styles) throws IOException, LException {

        for (String style : styles) {
            load(style);
        }
    }

}
