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

package org.extex.exindex.core.type;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exindex.core.command.type.Attribute;
import org.extex.exindex.core.command.type.FallbackContainer;
import org.extex.exindex.core.command.type.SortRuleContainer;
import org.extex.exindex.core.command.type.SortRules;
import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.parser.raw.RawIndexentry;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents a structured index as a whole.
 * 
 * <div style="float:right;"> <img src="doc-files/classes.png"/> <br />
 * Figure: The structure </div>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StructuredIndex extends LetterGroupContainer
        implements
            SortRuleContainer,
            MarkupContainer {

    /**
     * The field <tt>depth</tt> contains the hierarchy depth; i.e. 0 for flat
     * and Integer.MAX_VALUE for tree.
     */
    private int depth = Integer.MAX_VALUE;

    /**
     * The field <tt>sortRulesMap</tt> contains the mapping from names to sort
     * rules.
     */
    private Map<Integer, SortRules> sortRulesMap =
            new HashMap<Integer, SortRules>();

    /**
     * The field <tt>mergeRules</tt> contains the list of merge rules.
     */
    private List<Rule> mergeRules = new ArrayList<Rule>();

    /**
     * The field <tt>fallback</tt> contains the container for fallback values.
     */
    private FallbackContainer fallback;

    /**
     * The field <tt>attributeMap</tt> contains the attributes.
     */
    private Map<String, Attribute> attributeMap =
            new HashMap<String, Attribute>();

    /**
     * The field <tt>dropped</tt> contains the indicator to drop the index.
     */
    private boolean dropped = false;

    /**
     * The field <tt>markup</tt> contains the mapping from name to markup.
     */
    private Map<String, Markup> markup = new HashMap<String, Markup>();

    /**
     * Creates a new object.
     * 
     * @param fallback the container for fallback values
     */
    public StructuredIndex(FallbackContainer fallback) {

        super();
        this.fallback = fallback;
    }

    /**
     * Add a rule to the merge rules.
     * 
     * @param rule the rule to add
     */
    public void addMergeRule(Rule rule) {

        mergeRules.add(rule);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.command.type.SortRuleContainer#addSortRule(
     *      Integer, org.extex.exindex.core.type.rules.Rule)
     */
    public void addSortRule(Integer level, Rule rule) {

        SortRules sortRules = sortRulesMap.get(level);
        if (sortRules == null) {
            sortRules = new SortRules();
            sortRulesMap.put(level, sortRules);
        }
        sortRules.add(rule);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.command.type.SortRuleContainer#addSortRules(Integer,
     *      java.util.List)
     */
    public void addSortRules(Integer level, List<Rule> ruleList) {

        SortRules sortRules = sortRulesMap.get(level);
        if (sortRules == null) {
            sortRules = new SortRules();
            sortRulesMap.put(level, sortRules);
        }
        sortRules.addAll(ruleList);
    }

    /**
     * Apply the merge rules.
     * 
     * @param text the text to apply the merge rules to
     * 
     * @return the transformed text
     */
    public String applyMergeRule(String text) {

        if (mergeRules.isEmpty()) {
            return (fallback == null //
                    ? text
                    : fallback.applyMergeRuleFallback(text));
        }
        return Rule.apply(mergeRules, text);
    }

    /**
     * Apply the sort rules.
     * 
     * @param text the text to apply the sort rules to
     * @param level the current level
     * 
     * @return the transformed text
     */
    public String applySortRule(String text, int level) {

        List<Rule> rules = sortRulesMap.get(Integer.valueOf(level));

        if (rules == null || rules.isEmpty()) {
            return (fallback == null //
                    ? text
                    : fallback.applySortRuleFallback(text, level));
        }
        return Rule.apply(rules, text);
    }

    /**
     * Define or redefine an attribute.
     * 
     * @param key the name of the attribute
     * @param attribute the description of the attribue
     */
    public void defineAttribute(String key, Attribute attribute) {

        attributeMap.put(key, attribute);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.MarkupContainer#getMarkup(java.lang.String)
     */
    public Markup getMarkup(String name) {

        Markup m = markup.get(name);
        if (m == null && fallback != null) {
            m = fallback.getFallbackMarkup(name);
        }
        return m;
    }

    /**
     * Getter for the dropped indicator.
     * 
     * @return the dropped indicator
     */
    public boolean isDropped() {

        return dropped;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.command.type.SortRuleContainer#lookupOrCreateSortRule(Integer)
     */
    public SortRules lookupOrCreateSortRule(Integer level) {

        SortRules sortRules = sortRulesMap.get(level);
        if (sortRules == null) {
            sortRules = new SortRules();
            sortRulesMap.put(level, sortRules);
        }
        return sortRules;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.command.type.SortRuleContainer#lookupSortRules(Integer)
     */
    public SortRules lookupSortRules(Integer level) {

        return sortRulesMap.get(level);
    }

    /**
     * Setter for depth.
     * 
     * @param depth the depth to set
     */
    public void setDepth(int depth) {

        this.depth = depth;
    }

    /**
     * Setter for the dropped indicator.
     * 
     * @param dropped the dropped indicator's new value
     */
    public void setDropped(boolean dropped) {

        this.dropped = dropped;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.MarkupContainer#setMarkup(java.lang.String,
     *      org.extex.exindex.core.type.markup.Markup)
     */
    public void setMarkup(String name, Markup m) {

        markup.put(name, m);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.command.type.SortRuleContainer#sortRuleSize()
     */
    public int sortRuleSize() {

        return sortRulesMap.size();
    }

    /**
     * Store an index entry.
     * 
     * @param entry the entry to store
     * 
     * @throws IndexerException in case of an error
     */
    public void store(RawIndexentry entry) throws IndexerException {

        String[] sortKey = entry.getSortKey();
        LetterGroup group =
                (sortKey != null && sortKey.length != 0
                        ? findLetterGroup(sortKey[0])
                        : null);

        if (group == null) {
            group = getLetterGroup("default");
            if (group == null) {
                throw new IndexerException(null, LocalizerFactory.getLocalizer(
                    getClass()).format("NoPropoperLetterGroup",
                    entry.toString()));
            }
        }
        group.store(entry, depth);
    }

    /**
     * Write the structured index according to the definition of the parameters.
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * @param trace the trace indicator
     * 
     * @throws IOException in case of an I/O error
     * @throws LException in case of an error
     */
    public void write(Writer writer, LInterpreter interpreter, boolean trace)
            throws IOException,
                LException {

        Markup markup = getMarkup("markup-index");
        Markup markupList = getMarkup("markup-letter-group-list");

        boolean first = true;
        markup.write(writer, this, null, Markup.OPEN, trace);
        markupList.write(writer, this, null, Markup.OPEN, trace);

        for (LetterGroup group : sorted()) {
            if (group.isEmpty()) {
                // skip
            } else if (first) {
                first = false;
            } else {
                markupList.write(writer, this, null, Markup.SEP, trace);
            }
            group.write(writer, interpreter, this, trace);
        }

        markupList.write(writer, this, null, Markup.CLOSE, trace);
        markup.write(writer, this, null, Markup.CLOSE, trace);
    }

}
