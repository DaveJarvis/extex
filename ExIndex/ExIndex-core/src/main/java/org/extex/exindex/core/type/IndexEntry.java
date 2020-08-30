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
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.type.raw.CrossReference;
import org.extex.exindex.core.type.raw.LocationReference;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.core.type.raw.Reference;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents an index entry in the structured index.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class IndexEntry {

    /**
     * The field {@code map} contains the mapping from name to the associated
     * entry.
     */
    private final Map<String, IndexEntry> map = new HashMap<String, IndexEntry>();

    /**
     * The field {@code group} contains the mapping from name to location
     * class groups.
     */
    private final Map<String, LocationClassGroup> groupMap =
            new HashMap<String, LocationClassGroup>();

    /**
     * The field {@code keywords} contains the keywords.
     */
    private final String[] keywords;

    /**
     * The field {@code raw} contains the raw index entry.
     */
    private final RawIndexentry raw;

    /**
     * Creates a new object.
     * 
     * @param raw the raw index entry
     * @param keywords the keywords
     */
    public IndexEntry(RawIndexentry raw, String... keywords) {

        this.keywords = keywords;
        this.raw = raw;
    }

    /**
     * Retrieve the sorted set of keys.
     * 
     * @return the sorted list of index entries
     */
    private SortedSet<String> sortedKeys() {

        SortedSet<String> set = new TreeSet<String>();
        set.addAll(map.keySet());

        return set;
    }

    /**
     * Store an index entry.
     * 
     * @param sortKey the sort key
     * @param i the index to start with
     * @param depth the hierarchy depth
     * @param raw the raw index entry to store
     * 
     * @throws IndexerException in case of an error
     */
    public void store(String[] sortKey, int i, int depth, RawIndexentry raw)
            throws IndexerException {

        if (i < sortKey.length && i <= depth) {
            IndexEntry entry = map.get(sortKey[i]);
            if (entry == null) {
                entry = new IndexEntry(raw, sortKey[i]);
                map.put(sortKey[i], entry);
            }
            entry.store(sortKey, i + 1, depth, raw);
            return;
        }

        Reference ref = raw.getRef();
        if (ref instanceof CrossReference) {
            CrossReference xref = (CrossReference) ref;
            String[] keys = xref.getKeys();
            String clazz = ref.getLayer();
            LocationClassGroup locationClassGroup = groupMap.get(clazz);
            if (locationClassGroup == null) {
                locationClassGroup = new CrossrefGroup(clazz);
                groupMap.put(clazz, locationClassGroup);
            } else if (!(locationClassGroup instanceof CrossrefGroup)) {
                throw new IndexerException(null, LocalizerFactory.getLocalizer(
                    IndexEntry.class).format("XrefNoMatchingClass", clazz));
            }

            ((CrossrefGroup) locationClassGroup).store(keys, clazz);
        } else if (ref instanceof LocationReference) {

            String clazz = ref.getLayer();
            LocationClassGroup locationClassGroup = groupMap.get(clazz);
            if (locationClassGroup == null) {
                locationClassGroup = new LocationReferenceList(clazz);
                groupMap.put(clazz, locationClassGroup);
            } else if (!(locationClassGroup instanceof LocationReferenceList)) {
                throw new IndexerException(null, LocalizerFactory.getLocalizer(
                    IndexEntry.class).format("LocrefNoMatchingClass", clazz));
            }

            ((LocationReferenceList) locationClassGroup)
                .store((LocationReference) ref);
        } else {
            throw new IndexerException(null, LocalizerFactory.getLocalizer(
                IndexEntry.class).format("UnsupportedReference"));
        }
    }

@Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("<");
        boolean first = true;
        for (String s : keywords) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(s);
        }
        sb.append(">");
        sb.append(map.size());
        sb.append("/");
        sb.append(groupMap.size());
        return sb.toString();
    }

    /**
     * Write an index entry to a writer.
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * @param markupContainer the container for markup information
     * @param trace the trace indicator
     * @param level the current level
     * 
     * @throws IOException in case of an I/O error
     * @throws LNonMatchingTypeException in case of an error
     */
    public void write(Writer writer, LInterpreter interpreter,
            MarkupContainer markupContainer, boolean trace, int level)
            throws IOException,
                LNonMatchingTypeException {

        Markup markupIndexentry =
                markupContainer.getMarkup("markup-indexentry");
        Markup markupKeywordList =
                markupContainer.getMarkup("markup-keyword-list");
        Markup markupKeyword = markupContainer.getMarkup("markup-keyword");

        String hier = Integer.toString(level);
        int level1 = level + 1;
        boolean first = true;
        markupIndexentry.write(writer, markupContainer, hier, Markup.OPEN,
            trace);
        markupKeywordList.write(writer, markupContainer, hier, Markup.OPEN,
            trace);

        for (String printKey : raw.getPrintKey()) {
            if (first) {
                first = false;
            } else {
                markupKeywordList.write(writer, markupContainer, hier,
                    Markup.SEP, trace);
            }
            markupKeyword.write(writer, markupContainer, hier, Markup.OPEN,
                trace);
            writer.write(printKey);
            markupKeyword.write(writer, markupContainer, hier, Markup.CLOSE,
                trace);
        }

        first = true;

        for (LocationClassGroup group : groupMap.values()) {
            if (first) {
                first = false;
            } else {
                markupIndexentry.write(writer, markupContainer, hier,
                    Markup.SEP, trace);
            }
            group.write(writer, interpreter, markupContainer, trace);
        }

        markupKeywordList.write(writer, markupContainer, hier, Markup.CLOSE,
            trace);
        markupIndexentry.write(writer, markupContainer, hier, Markup.CLOSE,
            trace);

        first = true;

        for (String key : sortedKeys()) {
            IndexEntry entry = map.get(key);
            if (first) {
                first = false;
            } else {
                markupKeywordList.write(writer, markupContainer, hier,
                    Markup.SEP, trace);
            }
            entry.write(writer, interpreter, markupContainer, trace, level1);
        }
    }

}
