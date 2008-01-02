/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.exindex.core.command.LDefineAlphabet;
import org.extex.exindex.core.command.LDefineAttributes;
import org.extex.exindex.core.command.LDefineCrossrefClass;
import org.extex.exindex.core.command.LDefineIndex;
import org.extex.exindex.core.command.LDefineLetterGroup;
import org.extex.exindex.core.command.LDefineLetterGroups;
import org.extex.exindex.core.command.LDefineLocationClass;
import org.extex.exindex.core.command.LDefineLocationClassOrder;
import org.extex.exindex.core.command.LDefineRuleSet;
import org.extex.exindex.core.command.LDefineSortRuleOrientations;
import org.extex.exindex.core.command.LForIndex;
import org.extex.exindex.core.command.LMarkupAttributeGroup;
import org.extex.exindex.core.command.LMarkupAttributeGroupList;
import org.extex.exindex.core.command.LMarkupCrossrefLayer;
import org.extex.exindex.core.command.LMarkupCrossrefLayerList;
import org.extex.exindex.core.command.LMarkupCrossrefList;
import org.extex.exindex.core.command.LMarkupIndex;
import org.extex.exindex.core.command.LMarkupIndexEntry;
import org.extex.exindex.core.command.LMarkupIndexEntryList;
import org.extex.exindex.core.command.LMarkupKeyword;
import org.extex.exindex.core.command.LMarkupKeywordList;
import org.extex.exindex.core.command.LMarkupLetterGroup;
import org.extex.exindex.core.command.LMarkupLetterGroupList;
import org.extex.exindex.core.command.LMarkupLocclassList;
import org.extex.exindex.core.command.LMarkupLocref;
import org.extex.exindex.core.command.LMarkupLocrefLayer;
import org.extex.exindex.core.command.LMarkupLocrefLayerList;
import org.extex.exindex.core.command.LMarkupLocrefList;
import org.extex.exindex.core.command.LMarkupRange;
import org.extex.exindex.core.command.LMarkupTrace;
import org.extex.exindex.core.command.LMergeRule;
import org.extex.exindex.core.command.LMergeTo;
import org.extex.exindex.core.command.LSearchpath;
import org.extex.exindex.core.command.LSortRule;
import org.extex.exindex.core.command.LUseRuleSet;
import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.UnknownAttributeException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.XindyParser;
import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.core.type.alphabet.AlphaLowercase;
import org.extex.exindex.core.type.alphabet.AlphaUppercase;
import org.extex.exindex.core.type.alphabet.ArabicNumbers;
import org.extex.exindex.core.type.alphabet.Digits;
import org.extex.exindex.core.type.alphabet.RomanNumeralsLowercase;
import org.extex.exindex.core.type.alphabet.RomanNumeralsUppercase;
import org.extex.exindex.core.type.attribute.AttributesContainer;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.type.markup.MarkupNum;
import org.extex.exindex.core.type.markup.MarkupTransform;
import org.extex.exindex.core.type.raw.OpenLocRef;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.core.type.raw.RefSpec;
import org.extex.exindex.lisp.LEngine;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.parser.LParser;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LBoolean;
import org.extex.exindex.lisp.type.value.LString;
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
     * The field <tt>entryIndex</tt> contains the mapping from key to Boolean
     * to check that a key is in use.
     */
    private Map<String[], Boolean> entryIndex =
            new HashMap<String[], Boolean>();

    /**
     * The field <tt>container</tt> contains the container for all indices.
     */
    private IndexContainer container = new IndexContainer();

    /**
     * Creates a new object and initialized it.
     * 
     * @throws NoSuchMethodException in case of an undefined method in a
     *         function definition
     * @throws SecurityException in case of an security problem
     * @throws LSettingConstantException should not happen
     * @throws InvocationTargetException in case of an error
     * @throws IllegalAccessException in case of an error
     * @throws InstantiationException in case of an error
     * @throws IllegalArgumentException in case of an error
     */
    public Indexer()
            throws SecurityException,
                NoSuchMethodException,
                LSettingConstantException,
                IllegalArgumentException,
                InstantiationException,
                IllegalAccessException,
                InvocationTargetException {

        super();
        init();
    }

    /**
     * Define a function to set the markup and initialize the markup in the
     * markup container.
     * 
     * @param name the name in the L system
     * @param clazz the class
     * @param markup the markup object
     * 
     * @throws SecurityException in case of an error
     * @throws NoSuchMethodException in case of an error
     * @throws IllegalArgumentException in case of an error
     * @throws InstantiationException in case of an error
     * @throws IllegalAccessException in case of an error
     * @throws InvocationTargetException in case of an error
     */
    protected void defMarkup(String name, Class<?> clazz, Markup markup)
            throws SecurityException,
                NoSuchMethodException,
                IllegalArgumentException,
                InstantiationException,
                IllegalAccessException,
                InvocationTargetException {

        defun(name, //
            (LFunction) clazz.getConstructor(String.class, //
                IndexContainer.class).newInstance(name, container));
        container.defineMarkup(name, markup);

    }

    /**
     * Getter for container.
     * 
     * @return the container
     */
    public IndexContainer getContainer() {

        return container;
    }

    /**
     * Initialize the L system.
     * 
     * @throws NoSuchMethodException in case of an undefined method in a
     *         function definition
     * @throws SecurityException in case of an security problem
     * @throws LSettingConstantException should not happen
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    private void init()
            throws NoSuchMethodException,
                LSettingConstantException,
                SecurityException,
                IllegalArgumentException,
                InstantiationException,
                IllegalAccessException,
                InvocationTargetException {

        container.defineLetterGroup("default");

        defun("define-alphabet", new LDefineAlphabet("define-alphabet",
            container));
        defun("define-attributes", new LDefineAttributes("define-attributes",
            container));
        defun("define-crossref-class", new LDefineCrossrefClass(
            "define-crossref-class", container));
        defun("define-index", new LDefineIndex("define-index", container));
        defun("define-letter-group", //
            new LDefineLetterGroup("define-letter-group", container));
        defun("define-letter-groups", //
            new LDefineLetterGroups("define-letter-groups", container));
        defun("define-location-class", //
            new LDefineLocationClass("define-location-class", container));
        defun("define-location-class-order", //
            new LDefineLocationClassOrder("define-location-class-order",
                container));
        defun("define-rule-set", new LDefineRuleSet("define-rule-set",
            container));
        defun("define-sort-rule-orientations", new LDefineSortRuleOrientations(
            "define-sort-rule-orientations", container));
        defun("for-index", new LForIndex("for-index", container));
        defun("searchpath", new LSearchpath("searchpath"));
        defun("sort-rule", new LSortRule("sort-rule", container));
        defun("merge-rule", new LMergeRule("merge-rule", container));
        defun("merge-to", new LMergeTo("merge-to", container));
        defun("use-rule-set", new LUseRuleSet("use-rule-set", container));

        defMarkup("markup-attribute-group-list",
            LMarkupAttributeGroupList.class, new Markup("ATTRIBUTE-GROUP-LIST"));
        defMarkup("markup-attribute-group", LMarkupAttributeGroup.class,
            new Markup("ATTRIBUTE-GROUP"));
        defMarkup("markup-crossref-layer", LMarkupCrossrefLayer.class,
            new Markup("CROSSREF-LAYER"));
        defMarkup("markup-crossref-layer-list", LMarkupCrossrefLayerList.class,
            new Markup("CROSSREF-LAYER-LIST"));
        defMarkup("markup-crossref-list", LMarkupCrossrefList.class,
            new Markup("CROSSREF-LIST"));
        defMarkup("markup-index", LMarkupIndex.class, //
            new Markup("INDEX"));
        defMarkup("markup-indexentry", LMarkupIndexEntry.class, //
            new Markup("INDEXENTRY"));
        defMarkup("markup-indexentry-list", LMarkupIndexEntryList.class,
            new Markup("INDEXENTRY-LIST"));
        defMarkup("markup-keyword", LMarkupKeyword.class, new Markup("KEYWORD"));
        defMarkup("markup-keyword-list", LMarkupKeywordList.class, //
            new Markup("KEYWORD-LIST"));
        defMarkup("markup-letter-group", LMarkupLetterGroup.class,
            new MarkupTransform("LETTER-GROUP"));
        defMarkup("markup-letter-group-list", LMarkupLetterGroupList.class,
            new Markup("LETTER-GROUP-LIST"));
        defMarkup("markup-locclass-list", LMarkupLocclassList.class,
            new Markup("LOCCLASS-LIST"));
        defMarkup("markup-locref-list", LMarkupLocrefList.class, //
            new MarkupNum("LOCREF-LIST"));
        defMarkup("markup-locref", LMarkupLocref.class, //
            new MarkupNum("LOCREF"));
        defMarkup("markup-locref-layer", LMarkupLocrefLayer.class, //
            new MarkupNum("LOCREF-LAYER"));
        defMarkup("markup-locref-layer-list", LMarkupLocrefLayerList.class,
            new MarkupNum("LOCREF-LAYER-LIST"));
        defMarkup("markup-range", LMarkupRange.class, //
            new MarkupNum("RANGE"));
        defMarkup("markup-trace", LMarkupTrace.class, //
            new Markup("TRACE", "<", ">\n"));

        container.addLocationClass("arabic-numbers", new ArabicNumbers());
        container.addLocationClass("roman-numbers-uppercase",
            new RomanNumeralsUppercase());
        container.addLocationClass("roman-numbers-lowercase",
            new RomanNumeralsLowercase());
        container.addLocationClass("digits", new Digits());
        container.addLocationClass("alpha", new AlphaLowercase());
        container.addLocationClass("ALPHA", new AlphaUppercase());

        container.addAlphabet("arabic-numbers", new ArabicNumbers());
        container.addAlphabet("roman-numerals-uppercase",
            new RomanNumeralsUppercase());
        container.addAlphabet("roman-numerals-lowercase",
            new RomanNumeralsLowercase());
        container.addAlphabet("digits", new Digits());
        container.addAlphabet("alpha", new AlphaLowercase());
        container.addAlphabet("ALPHA", new AlphaUppercase());

        setq("indexer:charset-raw", new LString("utf-8"));
    }

    /**
     * Find an index entry with the given key.
     * 
     * @param refs the key
     * 
     * @return Boolean.TRUE iff the key is in use
     */
    public Object lookup(String[] refs) {

        // TODO lift this to separate indices
        return entryIndex.get(refs);
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
     * @param charset the name of the character set; a value of
     *        <code>null</code> or the empty string used the platform default
     * 
     * @return the parser
     * 
     * @throws RawIndexException in case of an error
     * @throws IOException in case of an I/O error
     */
    protected RawIndexParser makeRawIndexParser(String resource, String charset)
            throws RawIndexException,
                IOException {

        InputStream stream = getResourceFinder().findResource(resource, "raw");
        if (stream == null) {
            return null;
        }

        Reader reader =
                (charset == null || charset.equals("") //
                        ? new InputStreamReader(stream, charset)
                        : new InputStreamReader(stream));
        return new XindyParser(reader, resource);
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
        container.getCurrentIndex().write(writer, this,
            get("markup:trace") == LBoolean.TRUE);
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
            AttributesContainer attributes, List<OpenLocRef> openPages,
            Logger logger) throws LException {

        String indexName = entry.getIndex();
        StructuredIndex index = container.get(indexName);

        RefSpec ref = entry.getRef();
        if (ref == null) {
            // TODO gene: error handling unimplemented
            throw new RuntimeException("unimplemented");
        } else if (!ref.check(logger, entry, this, container, openPages,
            attributes)) {
            return false;
        }

        String[] mainKey = entry.getMainKey();
        String[] sortKey = new String[mainKey.length];

        for (int level = 0; level < mainKey.length; level++) {
            String mergeKey = index.applyMergeRule(mainKey[level]);
            sortKey[level] = index.applySortRule(mergeKey, level);
        }
        entry.setSortKey(sortKey);

        return true;
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
        AttributesContainer attributes = container;
        List<OpenLocRef> openPages = new ArrayList<OpenLocRef>();
        List<RawIndexentry> entries = new ArrayList<RawIndexentry>();

        for (String resource : resources) {
            logger.info(LOCALIZER.format((resource != null
                    ? "Reading"
                    : "ReadingStdin"), resource));

            RawIndexParser parser = makeRawIndexParser(resource, //
                LString.stringValue(get("indexer:charset-raw")));
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
                    entryIndex.put(entry.getMainKey(), Boolean.TRUE);
                }
            } finally {
                parser.close();
            }
            if (!openPages.isEmpty()) {
                // TODO gene: error handling unimplemented
                throw new RuntimeException("unimplemented");
            }
        }
        logger.info(LOCALIZER.format("StartPreprocess"));
        // preprocessing afterwards to resolve forward cross-references

        for (RawIndexentry entry : entries) {
            if (preProcess(entry, attributes, openPages, logger)) {
                container.store(entry, logger);
            }
        }
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
     * @throws UnknownAttributeException in case of an error
     */
    protected void startup(List<String> styles, Logger logger)
            throws IOException,
                LException,
                UnknownAttributeException {

        logger.info(LOCALIZER.format("Startup"));
        if (styles == null || styles.isEmpty()) {
            logger.warning(LOCALIZER.format("NoStyles"));
        } else {
            for (String style : styles) {
                load(style);
            }
        }

        logger.finer(LOCALIZER.format("PreparingIndex"));
        container.getCurrentIndex().sorted();
    }

}
