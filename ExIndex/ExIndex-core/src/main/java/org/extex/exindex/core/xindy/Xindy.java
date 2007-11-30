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
import java.util.logging.Logger;

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
import org.extex.exindex.lisp.exception.LSettingConstantException;
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
     * @throws LSettingConstantException should not happen
     */
    public Xindy()
            throws SecurityException,
                NoSuchMethodException,
                LSettingConstantException {

        super();
        index = new Index();
        defun("define-alphabet", //
            new LDefineAlphabet("define-alphabet"));
        defun("define-attributes", //
            new LDefineAttributes("define-attributes"));
        defun("define-crossref-class", //
            new LDefineCrossrefClass("define-crossref-class"));
        defun("define-letter-group", //
            new LDefineLetterGroup("define-letter-group"));
        defun("define-letter-groups", //
            new LDefineLetterGroups("define-letter-groups"));
        defun("define-location-class", //
            new LDefineLocationClass("define-location-class"));
        defun("define-location-class-order", //
            new LDefineLocationClassOrder("define-location-class-order"));
        defun("define-rule-set", //
            new LDefineRuleSet("define-rule-set"));
        defun("searchpath", //
            new LSearchpath("searchpath"));
        defun("sort-rule", //
            new LSortRule("sort-rule"));
        defun("merge-rule", //
            new LMergeRule("merge-rule"));
        defun("merge-to", //
            new LMergeTo("merge-to"));
        defun("use-rule-set", //
            new LUseRuleSet("use-rule-set"));

        defun("markup-attribute-group-list", //
            new LMarkupAttributeGroupList("markup-attribute-group-list"));
        defun("markup-attribute-group", //
            new LMarkupAttributeGroup("markup-attribute-group"));
        defun("markup-crossref-layer", //
            new LMarkupCrossrefLayer("markup-crossref-layer"));
        defun("markup-crossref-layer-list", //
            new LMarkupCrossrefLayerList("markup-crossref-layer-list"));
        defun("markup-crossref-list", //
            new LMarkupCrossrefList("markup-crossref-list"));
        defun("markup-index", //
            new LMarkupIndex("markup-index"));
        defun("markup-indexentry", //
            new LMarkupIndexEntry("markup-indexentry"));
        defun("markup-indexentry-list", //
            new LMarkupIndexEntryList("markup-indexentry-list"));
        defun("markup-keyword", //
            new LMarkupKeyword("markup-keyword"));
        defun("markup-keyword-list", //
            new LMarkupKeywordList("markup-keyword-list"));
        defun("markup-letter-group", //
            new LMarkupLetterGroup("markup-letter-group"));
        defun("markup-letter-group-list", //
            new LMarkupLetterGroupList("markup-letter-group-list"));
        defun("markup-locclass-list", //
            new LMarkupLocclassList("markup-locclass-list"));
        defun("markup-locref-list", //
            new LMarkupLocrefList("markup-locref-list"));
        defun("markup-locref", //
            new LMarkupLocref("markup-locref"));
        defun("markup-locref-layer", //
            new LMarkupLocrefLayer("markup-locref-layer"));
        defun("markup-locref-layer-list", //
            new LMarkupLocrefLayerList("markup-locref-layer-list"));
        defun("markup-range", //
            new LMarkupRange("markup-range"));
        defun("markup-trace", //
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
     * Perform the markup phase and write the result to the given writer.
     * 
     * @param writer the writer
     * @param logger the logger
     */
    protected void markup(Writer writer, Logger logger) {

        // TODO gene: markup unimplemented

    }

    /**
     * Perform the processing phase.
     * 
     * @param data the list of raw data files
     * @param logger the logger
     * 
     * @throws IOException in case of an I/O error
     * @throws FileNotFoundException if an input file could not be found
     */
    protected void process(List<String> data, Logger logger)
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
     * Perform all phases; initializing from a list of styles, loading a list of
     * data resources, and writing the result to a writer.
     * 
     * @param styles the list of styles to use
     * @param data the list of raw data files
     * @param writer the writer for output
     * @param logger the logger
     * 
     * @throws LException in case of an error in the L system
     * @throws IOException in case of an I/O error
     */
    public void run(List<String> styles, List<String> data, Writer writer,
            Logger logger) throws IOException, LException {

        startup(styles, logger);
        process(data, logger);
        markup(writer, logger);
    }

    /**
     * Load the style files and prepare the engine to get started.
     * 
     * @param styles the list of styles to use
     * @param logger the logger
     * 
     * @throws LException in case of an error in the L system
     * @throws IOException in case of an I/O error
     */
    protected void startup(List<String> styles, Logger logger)
            throws IOException,
                LException {

        for (String style : styles) {
            load(style);
        }
    }

}
