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

package org.extex.exindex.core.type;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.extex.exindex.core.command.type.LMarkup;
import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.parser.raw.CrossReference;
import org.extex.exindex.core.parser.raw.LocRef;
import org.extex.exindex.core.parser.raw.RawIndexentry;
import org.extex.exindex.core.parser.raw.RefSpec;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents an index entry in the structured index.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IndexEntry {

    /**
     * The field <tt>map</tt> contains the mapping from name to the associated
     * entry.
     */
    private Map<String, IndexEntry> map = new HashMap<String, IndexEntry>();

    /**
     * The field <tt>group</tt> contains the mapping from name to location
     * class groups.
     */
    private Map<String, LocationClassGroup> groupMap =
            new HashMap<String, LocationClassGroup>();

    /**
     * The field <tt>keywords</tt> contains the keywords.
     */
    private String[] keywords;

    /**
     * The field <tt>sortKey</tt> contains the sort key.
     */
    private String[] sortKey;

    /**
     * Creates a new object.
     * 
     * @param keywords the keywords
     */
    public IndexEntry(String... keywords) {

        super();
        this.keywords = keywords;
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
                entry = new IndexEntry(sortKey[i]);
                map.put(sortKey[i], entry);
            }
            entry.store(sortKey, i + 1, depth, raw);
            return;
        }

        RefSpec ref = raw.getRef();
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
        } else if (ref instanceof LocRef) {

            String clazz = ref.getLayer();
            LocationClassGroup locationClassGroup = groupMap.get(clazz);
            if (locationClassGroup == null) {
                locationClassGroup = new LocrefList(clazz);
                groupMap.put(clazz, locationClassGroup);
            } else if (!(locationClassGroup instanceof LocrefList)) {
                throw new IndexerException(null, LocalizerFactory.getLocalizer(
                    IndexEntry.class).format("LocrefNoMatchingClass", clazz));
            }

            ((LocrefList) locationClassGroup).store((LocRef) ref);
        } else {
            throw new IndexerException(null, LocalizerFactory.getLocalizer(
                IndexEntry.class).format("UnsupportedReference"));
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
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
     * @param trace the trace indicator
     * @param level the current level
     * 
     * @throws IOException in case of an I/O error
     * @throws LNonMatchingTypeException in case of an error
     */
    public void write(Writer writer, LInterpreter interpreter, boolean trace,
            int level) throws IOException, LNonMatchingTypeException {

        LMarkup markupIndexentry =
                (LMarkup) interpreter.get("markup-indexentry");
        LMarkup markupKeywordList =
                (LMarkup) interpreter.get("markup-keyword-list");
        LMarkup markupKeyword = (LMarkup) interpreter.get("markup-keyword");

        String hier = Integer.toString(level);
        int level1 = level + 1;
        boolean first = true;
        markupIndexentry.write(writer, interpreter, hier, LMarkup.OPEN, trace);
        markupKeywordList.write(writer, interpreter, hier, LMarkup.OPEN, trace);

        for (String key : keywords) {
            if (first) {
                first = false;
            } else {
                markupKeywordList.write(writer, interpreter, hier, LMarkup.SEP,
                    trace);
            }
            markupKeyword.write(writer, interpreter, hier, LMarkup.OPEN, trace);
            writer.write(key);
            markupKeyword
                .write(writer, interpreter, hier, LMarkup.CLOSE, trace);
        }

        first = true;

        for (LocationClassGroup group : groupMap.values()) {
            if (first) {
                first = false;
            } else {
                markupIndexentry.write(writer, interpreter, hier, LMarkup.SEP,
                    trace);
            }
            group.write(writer, interpreter, trace);
        }

        markupKeywordList
            .write(writer, interpreter, hier, LMarkup.CLOSE, trace);
        markupIndexentry.write(writer, interpreter, hier, LMarkup.CLOSE, trace);

        SortedSet<String> sortedKeys = sortedKeys();
        first = true;

        for (String key : sortedKeys) {
            IndexEntry entry = map.get(key);
            if (first) {
                first = false;
            } else {
                markupKeywordList.write(writer, interpreter, hier, LMarkup.SEP,
                    trace);
            }
            entry.write(writer, interpreter, trace, level1);
        }

    }

}
