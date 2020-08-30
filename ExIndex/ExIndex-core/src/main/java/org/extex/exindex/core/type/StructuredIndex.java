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

package org.extex.exindex.core.type;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.type.alphabet.LocationClass;
import org.extex.exindex.core.type.attribute.Attribute;
import org.extex.exindex.core.type.attribute.AttributesContainer;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.core.type.rules.SortRuleContainer;
import org.extex.exindex.core.type.rules.SortRules;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents a structured index as a whole.
 * 
 * <div style="float:right;"> <img src="doc-files/classes.png" alt="classes"> <br>
 * Figure: The structure </div>
 * 
 * <p>
 * This class carries two aspects of the index:
 * </p>
 * <ul>
 * <li>The index content </li>
 * <li>The index parameters </li>
 * </ul>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class StructuredIndex extends LetterGroupContainer
        implements
            AttributesContainer,
            SortRuleContainer,
            LocationClassContainer,
            MarkupContainer {

    /**
     * The field {@code name} contains the name.
     */
    private final String name;

    /**
     * The field {@code fallback} contains the container for fallback values.
     */
    private final FallbackContainer fallback;

    /**
     * The field {@code depth} contains the hierarchy depth; i.e. 0 for flat
     * and Integer.MAX_VALUE for tree, or anything on between.
     */
    private int depth = Integer.MAX_VALUE;

    /**
     * The field {@code sortRulesMap} contains the mapping from names to sort
     * rules.
     */
    private final Map<Integer, SortRules> sortRulesMap =
            new HashMap<Integer, SortRules>();

    /**
     * The field {@code mergeRules} contains the list of merge rules.
     */
    private final List<Rule> mergeRules = new ArrayList<Rule>();

    /**
     * The field {@code attributeMap} contains the attributes.
     */
    private final Map<String, Attribute> attributeMap =
            new HashMap<String, Attribute>();

    /**
     * The field {@code dropped} contains the indicator to drop the index.
     */
    private boolean dropped = false;

    /**
     * The field {@code markup} contains the mapping from name to markup.
     */
    private final Map<String, Markup> markup = new HashMap<String, Markup>();

    /**
     * The field {@code alias} contains the alias for this index. If it is
     * not {@code null} then all entries should be stored there.
     */
    private StructuredIndex alias = null;

    /**
     * The field {@code locationClassMap} contains the mapping from names to
     * location classes.
     */
    private final Map<String, LocationClass> locationClassMap =
            new HashMap<String, LocationClass>();

    /**
     * The field {@code locationClasses} contains the list of classes already
     * defined.
     */
    private List<LocationClass> locationClasses =
            new ArrayList<LocationClass>();

    /**
     * The field {@code suffix} contains the suffix parameter.
     */
    private String suffix = ".";

    /**
     * The field {@code entryIndex} contains the mapping from key to Boolean
     * to check that a key is in use.
     */
    private final Map<String[], Boolean> entryIndex =
            new HashMap<String[], Boolean>();

    /**
     * Creates a new object.
     * 
     * @param name the name of the index; "" for the default; this value is not
     *        {@code null}
     * @param fallback the container for fallback values
     */
    public StructuredIndex(String name, FallbackContainer fallback) {

        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
        this.fallback = fallback;
    }

    /**
*      java.lang.String,
     *      org.extex.exindex.core.type.alphabet.LocationClass)
     */
    public boolean addLocationClass(String className,
            LocationClass locationClass) {

        LocationClass lc = locationClassMap.get(className);
        if (lc != null) {
            locationClassMap.put(className, locationClass);
            locationClasses.remove(lc);
            locationClasses.add(locationClass);
            return false;
        }

        locationClassMap.put(className, locationClass);
        locationClasses.add(locationClass);
        return true;
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
*      Integer, java.util.List)
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
            return (fallback == null 
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
            return (fallback == null 
                    ? text
                    : fallback.applySortRuleFallback(text, level));
        }
        return Rule.apply(rules, text);
    }

    /**
     * Find an index entry with the given key.
     * 
     * @param refs the key
     * 
     * @return Boolean.TRUE iff the key is in use
     */
    public Boolean containsKey(String[] refs) {

        return entryIndex.get(refs);
    }

    /**
     * Define or redefine an attribute.
     * 
     * @param key the name of the attribute
     * @param attribute the description of the attribute
     */
    public void defineAttribute(String key, Attribute attribute) {

        attributeMap.put(key, attribute);
    }

    /**
     * Getter for alias.
     * 
     * @return the alias
     */
    public StructuredIndex getAlias() {

        return alias;
    }

    /**
*      java.lang.String)
     */
    public Markup getMarkup(String markupName) {

        Markup m = markup.get(markupName);
        if (m != null) {
            return m;
        } else if (fallback != null) {
            return fallback.getFallbackMarkup(markupName);
        }
        m = new Markup("EMPTY_" + markupName, "", "", "", "", "");
        markup.put(markupName, m);
        return m;
    }

    /**
     * Getter for the name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Getter for suffix.
     * 
     * @return the suffix
     */
    public String getSuffix() {

        return suffix;
    }

    /**
*      java.lang.String)
     */
    public boolean isAttributeDefined(String attribute) {

        return attributeMap.containsKey(attribute);
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
*      java.lang.String)
     */
    public Attribute lookupAttribute(String attribute) {

        return attributeMap.get(attribute);
    }

    /**
*      java.lang.String)
     */
    public LocationClass lookupLocationClass(String locationClassName) {

        return locationClassMap.get(locationClassName);
    }

    /**
*      Integer)
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
*      Integer)
     */
    public SortRules lookupSortRules(Integer level) {

        return sortRulesMap.get(level);
    }

    /**
*      java.lang.String, String)
     */
    public PageReference makePageReference(String encap, String page) {

        for (LocationClass lc : locationClasses) {
            PageReference pr = lc.match(encap, page);
            if (pr != null) {
                return pr;
            }
        }
        return null;
    }

    /**
*      java.lang.String[])
     */
    public void orderLocationClasses(String[] list) throws LException {

        List<LocationClass> newClasses = new ArrayList<LocationClass>();

        for (String s : list) {
            LocationClass lc = locationClassMap.get(s);
            int i = locationClasses.indexOf(lc);
            if (i < 0) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("UnknownClass", s));
            }
            newClasses.add(lc);
            locationClasses.remove(i);
        }

        newClasses.addAll(locationClasses);
        locationClasses = newClasses;
    }

    /**
     * Define an alias for an index.
     * 
     * @param alias the other index to store the entries instead
     */
    public void setAlias(StructuredIndex alias) {

        this.alias = alias;
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
*      java.lang.String, org.extex.exindex.core.type.markup.Markup)
     */
    public void setMarkup(String markupName, Markup m) {

        markup.put(markupName, m);
    }

    /**
     * Setter for suffix.
     * 
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {

        this.suffix = suffix;
    }

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

        if (dropped) {
            return;
        } else if (alias != null) {
            alias.store(entry);
            return;
        }

        String[] sortKey = entry.getSortKey();
        LetterGroup group =
                (sortKey != null && sortKey.length != 0
                        ? findLetterGroup(sortKey[0])
                        : null);

        if (group == null) {
            group = getLetterGroup("");
            if (group == null) {
                throw new IndexerException(null, LocalizerFactory.getLocalizer(
                    getClass()).format("NoProperLetterGroup", entry.toString()));
            }
        }
        group.store(entry, depth);
        entryIndex.put(entry.getMainKey(), Boolean.TRUE);
    }

@Override
    public String toString() {

        return "index[" + name + "]";
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

        if (dropped || alias != null) {
            return;
        }

        Markup markup = getMarkup("markup-index");
        Markup markupList = getMarkup("markup-letter-group-list");

        boolean first = true;
        if (markup != null) {
            markup.write(writer, this, null, Markup.OPEN, trace);
        }
        markupList.write(writer, this, null, Markup.OPEN, trace);

        for (LetterGroup group : sorted()) {
            if (group.isEmpty()) {
                continue;
            } else if (first) {
                first = false;
            } else {
                markupList.write(writer, this, null, Markup.SEP, trace);
            }
            group.write(writer, interpreter, this, trace);
        }

        markupList.write(writer, this, null, Markup.CLOSE, trace);
        if (markup != null) {
            markup.write(writer, this, null, Markup.CLOSE, trace);
        }
    }

}
