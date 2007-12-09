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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.core.type.alphabet.AlphaLowercase;
import org.extex.exindex.core.type.alphabet.AlphaUppercase;
import org.extex.exindex.core.type.alphabet.ArabicNumbers;
import org.extex.exindex.core.type.alphabet.Digits;
import org.extex.exindex.core.type.alphabet.RomanNumeralsLowercase;
import org.extex.exindex.core.type.alphabet.RomanNumeralsUppercase;
import org.extex.exindex.core.xparser.RawIndexParser;
import org.extex.exindex.core.xparser.XindyParser;
import org.extex.exindex.core.xparser.raw.Indexentry;
import org.extex.exindex.lisp.LEngine;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.parser.LParser;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class provides an LInterpreter with the functions needed by Xindy
 * defined.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Indexer extends LEngine {

    /**
     * The field <tt>index</tt> contains the index.
     */
    private StructuredIndex index;

    /**
     * The field <tt>entries</tt> contains the raw index.
     */
    private List<Indexentry> entries;

    /**
     * Creates a new object.
     * 
     * @throws NoSuchMethodException in case of an undefined method in a
     *         function definition
     * @throws SecurityException in case of an security problem
     * @throws LSettingConstantException should not happen
     */
    public Indexer()
            throws SecurityException,
                NoSuchMethodException,
                LSettingConstantException {

        super();
        index = new StructuredIndex();
        entries = new ArrayList<Indexentry>();

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
     * @see org.extex.exindex.lisp.LInterpreter#makeParser(java.io.Reader,
     *      java.lang.String)
     */
    @Override
    protected LParser makeParser(Reader reader, String name) {

        LParser parser = super.makeParser(reader, name);
        parser.setEscape('~');
        return parser;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param resource the name of the resource
     * 
     * @return the parser
     * 
     * @throws IOException in case of an I/O error
     */
    protected RawIndexParser makeRawIndexParser(String resource)
            throws IOException {

        InputStream stream = getResourceFinder().findResource(resource, "raw");
        if (stream == null) {
            return null;
        }

        return new XindyParser(new InputStreamReader(stream), resource);
    }

    /**
     * Perform the markup phase and write the result to the given writer.
     * 
     * @param writer the writer or <code>null</code> to skip this phase
     * @param logger the logger
     */
    protected void markup(Writer writer, Logger logger) {

        if (writer == null) {
            return;
        }

        // TODO gene: markup unimplemented

    }

    /**
     * Perform the processing phase.
     * 
     * @param resources the list of raw data files or <code>null</code> to
     *        skip this phase
     * @param logger the logger
     * 
     * @throws IOException in case of an I/O error
     * @throws FileNotFoundException if an input file could not be found
     * @throws LException in case of an error
     * @throws NoSuchMethodException in case of an undefined method
     * @throws SecurityException in case of a security problem
     */
    protected void process(List<String> resources, Logger logger)
            throws FileNotFoundException,
                IOException,
                LException,
                SecurityException,
                NoSuchMethodException {

        if (resources == null) {
            return;
        }
        LDefineAttributes attributes =
                (LDefineAttributes) getFunction(LSymbol
                    .get("define-attributes"));

        for (String resource : resources) {
            RawIndexParser parser = makeRawIndexParser(resource);
            if (parser == null) {
                throw new FileNotFoundException(resource);
            }
            try {
                for (Indexentry entry = parser.parse(); entry != null; entry =
                        parser.parse()) {
                    storeEntry(entry, attributes);
                }
            } finally {
                parser.close();
            }
        }
        // TODO
    }

    /**
     * Perform all phases; initializing from a list of styles, loading a list of
     * data resources, and writing the result to a writer.
     * 
     * @param styles the list of styles to use or <code>null</code> to skip
     *        this phase
     * @param resources the list of raw data files or <code>null</code> to
     *        skip this phase
     * @param writer the writer for output or <code>null</code> to skip this
     *        phase
     * @param logger the logger
     * 
     * @throws LException in case of an error in the L system
     * @throws IOException in case of an I/O error
     * @throws NoSuchMethodException in case of an undefined method
     * @throws SecurityException in case of a security problem
     */
    public void run(List<String> styles, List<String> resources, Writer writer,
            Logger logger)
            throws IOException,
                LException,
                SecurityException,
                NoSuchMethodException {

        startup(styles, logger);
        process(resources, logger);
        markup(writer, logger);
    }

    /**
     * Load the style files and prepare the engine to get started.
     * 
     * @param styles the list of styles to use or <code>null</code> to skip
     *        this phase
     * @param logger the logger
     * 
     * @throws LException in case of an error in the L system
     * @throws IOException in case of an I/O error
     */
    protected void startup(List<String> styles, Logger logger)
            throws IOException,
                LException {

        if (styles == null) {
            return;
        }

        for (String style : styles) {
            load(style);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param entry the entry to store
     * @param attributes the attributes
     * 
     * @throws LException in case of an error
     */
    private void storeEntry(Indexentry entry, LDefineAttributes attributes)
            throws LException {

        String attr = entry.getAttr();
        if (attr != null && attributes.lookup(attr) == null) {
            throw new LException(LocalizerFactory.getLocalizer(Indexer.class)
                .format("AttributeUnknown", attr));
        }
        entries.add(entry);
    }

}
