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

package org.extex.exindex.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.XindyParser;
import org.extex.exindex.core.parser.raw.OpenLocRef;
import org.extex.exindex.core.parser.raw.RawIndexentry;
import org.extex.exindex.core.parser.raw.RefSpec;
import org.extex.exindex.core.parser.raw.XRef;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.core.type.alphabet.AlphaLowercase;
import org.extex.exindex.core.type.alphabet.AlphaUppercase;
import org.extex.exindex.core.type.alphabet.ArabicNumbers;
import org.extex.exindex.core.type.alphabet.Digits;
import org.extex.exindex.core.type.alphabet.RomanNumeralsLowercase;
import org.extex.exindex.core.type.alphabet.RomanNumeralsUppercase;
import org.extex.exindex.core.xindy.LDefineAlphabet;
import org.extex.exindex.core.xindy.LDefineAttributes;
import org.extex.exindex.core.xindy.LDefineCrossrefClass;
import org.extex.exindex.core.xindy.LDefineLetterGroup;
import org.extex.exindex.core.xindy.LDefineLetterGroups;
import org.extex.exindex.core.xindy.LDefineLocationClass;
import org.extex.exindex.core.xindy.LDefineLocationClassOrder;
import org.extex.exindex.core.xindy.LDefineRuleSet;
import org.extex.exindex.core.xindy.LMarkupAttributeGroup;
import org.extex.exindex.core.xindy.LMarkupAttributeGroupList;
import org.extex.exindex.core.xindy.LMarkupCrossrefLayer;
import org.extex.exindex.core.xindy.LMarkupCrossrefLayerList;
import org.extex.exindex.core.xindy.LMarkupCrossrefList;
import org.extex.exindex.core.xindy.LMarkupIndex;
import org.extex.exindex.core.xindy.LMarkupIndexEntry;
import org.extex.exindex.core.xindy.LMarkupIndexEntryList;
import org.extex.exindex.core.xindy.LMarkupKeyword;
import org.extex.exindex.core.xindy.LMarkupKeywordList;
import org.extex.exindex.core.xindy.LMarkupLetterGroup;
import org.extex.exindex.core.xindy.LMarkupLetterGroupList;
import org.extex.exindex.core.xindy.LMarkupLocclassList;
import org.extex.exindex.core.xindy.LMarkupLocref;
import org.extex.exindex.core.xindy.LMarkupLocrefLayer;
import org.extex.exindex.core.xindy.LMarkupLocrefLayerList;
import org.extex.exindex.core.xindy.LMarkupLocrefList;
import org.extex.exindex.core.xindy.LMarkupRange;
import org.extex.exindex.core.xindy.LMarkupTrace;
import org.extex.exindex.core.xindy.LMergeTo;
import org.extex.exindex.core.xindy.LMergeRule;
import org.extex.exindex.core.xindy.LSearchpath;
import org.extex.exindex.core.xindy.LSortRule;
import org.extex.exindex.core.xindy.LUseRuleSet;
import org.extex.exindex.lisp.LEngine;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.parser.LParser;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.framework.i18n.Localizer;
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
     * The field <tt>LOCALIZER</tt> contains the localizer.
     */
    private static final Localizer LOCALIZER =
            LocalizerFactory.getLocalizer(Indexer.class);

    /**
     * The field <tt>entries</tt> contains the raw index.
     */
    private List<RawIndexentry> entries;

    /**
     * The field <tt>index</tt> contains the index.
     */
    private StructuredIndex index;

    /**
     * The field <tt>defineAlphabet</tt> contains the shortcut to the
     * alphabets.
     */
    private LDefineAlphabet alphabets;

    /**
     * The field <tt>mergeRule</tt> contains the shortcut to the merge rule.
     */
    private LMergeRule mergeRule;

    /**
     * The field <tt>sortRule</tt> contains the shortcut to the sort rules.
     */
    private LSortRule sortRule;

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
        index.defineLetterGroup("default");

        entries = new ArrayList<RawIndexentry>();

        alphabets = new LDefineAlphabet("define-alphabet");
        defun("define-alphabet", alphabets);
        defun("define-attributes", //
            new LDefineAttributes("define-attributes"));
        defun("define-crossref-class", //
            new LDefineCrossrefClass("define-crossref-class"));
        LDefineLetterGroup letterGroups =
                new LDefineLetterGroup("define-letter-group", index);
        defun("define-letter-group", letterGroups);
        defun("define-letter-groups", //
            new LDefineLetterGroups("define-letter-groups", index));
        LDefineLocationClass locationClass =
                new LDefineLocationClass("define-location-class");
        defun("define-location-class", //
            locationClass);
        defun("define-location-class-order", //
            new LDefineLocationClassOrder("define-location-class-order"));
        defun("define-rule-set", //
            new LDefineRuleSet("define-rule-set"));
        defun("searchpath", //
            new LSearchpath("searchpath"));
        sortRule = new LSortRule("sort-rule");
        defun("sort-rule", sortRule);
        mergeRule = new LMergeRule("merge-rule");
        defun("merge-rule", mergeRule);
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

        locationClass.add("arabic-numbers", new ArabicNumbers());
        locationClass.add("roman-numbers-uppercase",
            new RomanNumeralsUppercase());
        locationClass.add("roman-numbers-lowercase",
            new RomanNumeralsLowercase());
        locationClass.add("digits", new Digits());
        locationClass.add("alpha", new AlphaLowercase());
        locationClass.add("ALPHA", new AlphaUppercase());

        alphabets.add("arabic-numbers", new ArabicNumbers());
        alphabets.add("roman-numerals-uppercase", new RomanNumeralsUppercase());
        alphabets.add("roman-numerals-lowercase", new RomanNumeralsLowercase());
        alphabets.add("digits", new Digits());
        alphabets.add("alpha", new AlphaLowercase());
        alphabets.add("ALPHA", new AlphaUppercase());

        setq("indexer:charset-raw", new LString("utf-8"));

    }

    /**
     * Validate a crossref entry.
     * 
     * @param entry the entry to check
     * @param logger the logger
     * 
     * @return <code>true</code> if everything is correct
     */
    private boolean checkCrossref(RawIndexentry entry, Logger logger) {

        // TODO gene: checkCrossref unimplemented
        return true;
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
     * Create a parser for the raw index from a resource name.
     * 
     * @param resource the name of the resource
     * @param charset the name of the character set
     * 
     * @return the parser
     * 
     * @throws IOException in case of an I/O error
     */
    protected RawIndexParser makeRawIndexParser(String resource, String charset)
            throws IOException {

        InputStream stream = getResourceFinder().findResource(resource, "raw");
        if (stream == null) {
            return null;
        }
        return new XindyParser(new InputStreamReader(stream, charset), resource);
    }

    /**
     * Perform the markup phase and write the result to the given writer.
     * 
     * @param writer the writer or <code>null</code> to skip this phase
     * @param logger the logger
     * 
     * @throws IOException in case of an I/O error
     * @throws LException in case of an error
     */
    protected void markup(Writer writer, Logger logger)
            throws IOException,
                LException {

        logger.info(LOCALIZER.format("StartMarkup"));
        if (writer == null) {
            return;
        }
        index.write(writer, this);
    }

    /**
     * Compute the keys for an entry.
     * 
     * @param entry the entry to augment with the keys
     * 
     * @return <code>true</code>
     */
    protected boolean preComputeKeys(RawIndexentry entry) {

        String[] mainKey = entry.getMainKey();
        String[] mergeKey = new String[mainKey.length];

        for (int i = 0; i < mainKey.length; i++) {
            mergeKey[i] = mergeRule.apply(mainKey[i]);
        }
        entry.setMergeKey(mergeKey);

        String[] sortKey = new String[mainKey.length];

        for (int i = 0; i < mainKey.length; i++) {
            sortKey[i] = sortRule.apply(mergeKey[i]);
        }
        entry.setSortKey(sortKey);
        return true;
    }

    /**
     * Perform the initial processing step. This step consists of a check of the
     * attributes, the check of the crossrefs and the check of the location
     * references. If all checks are passed then <code>true</code> is
     * returned.
     * 
     * @param entry the entry to store
     * @param attributes the attributes
     * @param openPages the list of open pages
     * @param logger the logger
     * 
     * @return <code>true</code> iff the entry has been stored
     * 
     * @throws LException in case of an error
     */
    private boolean preProcess(RawIndexentry entry,
            LDefineAttributes attributes, List<OpenLocRef> openPages,
            Logger logger) throws LException {

        String attr = entry.getAttr();
        if (attr != null && attributes.lookup(attr) == null) {
            logger.severe(LocalizerFactory.getLocalizer(Indexer.class).format(
                "AttributeUnknown", attr));
            return false;
        }

        RefSpec ref = entry.getRef();
        if (ref != null && !ref.check(openPages, logger)) {
            return false;
        }
        if (ref instanceof XRef) {
            if (!checkCrossref(entry, logger)) {
                // TODO gene: process unimplemented
                throw new RuntimeException("unimplemented");
            }
        } else {
            // TODO
        }

        return preComputeKeys(entry);
    }

    /**
     * Perform the processing phase.
     * 
     * @param resources the list of raw data files or <code>null</code> to
     *        skip this phase
     * @param logger the logger
     * 
     * @throws IndexerException in case of an error
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
                NoSuchMethodException,
                IndexerException {

        if (resources == null || resources.isEmpty()) {
            logger.warning(LOCALIZER.format("NoResources"));
            return;
        }
        logger.info(LOCALIZER.format("StartProcess"));
        LDefineAttributes attributes =
                (LDefineAttributes) getFunction(LSymbol
                    .get("define-attributes"));
        List<OpenLocRef> openPages = new ArrayList<OpenLocRef>();

        for (String resource : resources) {
            logger.info(LOCALIZER.format((resource != null
                    ? "Reading"
                    : "ReadingStdin"), resource));

            RawIndexParser parser = makeRawIndexParser(resource, //
                LString.getString(get("indexer:charset-raw")));
            if (parser == null) {
                logger.info(LOCALIZER.format("ResourceNotFound", resource));
                throw new FileNotFoundException(resource);
            }
            try {
                for (;;) {
                    RawIndexentry entry;
                    try {
                        entry = parser.parse();
                    } catch (IOException e) {
                        logger.warning(e.toString());
                        continue;
                    }
                    if (entry == null) {
                        break;
                    }
                    entries.add(entry);
                }
            } finally {
                parser.close();
            }
            if (!openPages.isEmpty()) {
                // TODO
            }
        }
        logger.info(LOCALIZER.format("StartPreprocess"));

        index.sorted();

        for (RawIndexentry entry : entries) {
            if (preProcess(entry, attributes, openPages, logger)) {
                index.store(entry);
            } else {
                // TODO delete from list?
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
     * @throws IndexerException in case of an error
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
                NoSuchMethodException,
                IndexerException {

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

        logger.info(LOCALIZER.format("Startup"));
        if (styles == null || styles.isEmpty()) {
            logger.warning(LOCALIZER.format("NoStyles"));
            return;
        }

        for (String style : styles) {
            load(style);
        }
    }

}
