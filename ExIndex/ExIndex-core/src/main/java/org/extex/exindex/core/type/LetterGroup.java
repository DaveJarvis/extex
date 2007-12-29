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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.extex.exindex.core.command.type.LMarkup;
import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.parser.raw.RawIndexentry;
import org.extex.exindex.core.type.transform.Transform;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This class represents a letter group.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LetterGroup {

    /**
     * The field <tt>map</tt> contains the mapping from name to index entry.
     */
    private Map<String, IndexEntry> map = new HashMap<String, IndexEntry>();

    /**
     * The field <tt>after</tt> contains the the letter groups preceding this
     * one.
     */
    private List<LetterGroup> after = new ArrayList<LetterGroup>();

    /**
     * The field <tt>before</tt> contains the letter groups following this
     * one. This value is cleared after the collecting phase.
     */
    private List<LetterGroup> before = new ArrayList<LetterGroup>();

    /**
     * The field <tt>name</tt> contains the name of the letter group.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public LetterGroup(String name) {

        super();
        this.name = name;
    }

    /**
     * Place the constraint on this letter group to ge after another one.
     * 
     * @param g the letter group to go before
     */
    public void after(LetterGroup g) {

        after.add(g);
        g.before.add(this);
    }

    /**
     * Remove a letter group from the after list.
     * 
     * @param letterGroup the letter group to remove
     */
    protected void clearAfter(LetterGroup letterGroup) {

        after.remove(letterGroup);
    }

    /**
     * Clear all after groups of the before groups. This method is meant for
     * internal purposes. Use it on your own risk.
     */
    public void clearAfterBefore() {

        for (LetterGroup g : before) {
            g.clearAfter(this);
        }
    }

    /**
     * Getter for name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Get an arbitrary element from the before list or <code>null</code> if
     * this list is empty.
     * 
     * @return an arbitrary element from the before list or <code>null</code>
     *         if this list is empty
     */
    public LetterGroup getSomeAfter() {

        if (after.isEmpty()) {
            return null;
        }
        return after.get(0);
    }

    /**
     * Get an arbitrary element from the before list or <code>null</code> if
     * this list is empty.
     * 
     * @return an arbitrary element from the before list or <code>null</code>
     *         if this list is empty
     */
    public LetterGroup getSomeBefore() {

        if (before.isEmpty()) {
            return null;
        }
        return before.get(0);
    }

    /**
     * Check whether some elements are contained.
     * 
     * @return <code>true</code> iff the letter group does not contain
     *         elements
     * @see java.util.List#isEmpty()
     */
    public boolean isEmpty() {

        return map.isEmpty();
    }

    /**
     * Getter for the sorted list of keys of index entries.
     * 
     * @return the sorted list of keys of index entries
     */
    private SortedSet<String> sortedKeys() {

        SortedSet<String> set = new TreeSet<String>();
        set.addAll(map.keySet());

        return set;
    }

    /**
     * Store a raw entry.
     * 
     * @param raw the raw entry to store
     * @param depth the hierarchy depth
     * 
     * @throws IndexerException in case of an error
     */
    public void store(RawIndexentry raw, int depth) throws IndexerException {

        String[] sortKey = raw.getSortKey();
        IndexEntry entry = map.get(sortKey[0]);
        if (entry == null) {
            entry = new IndexEntry(sortKey[0]);
            map.put(sortKey[0], entry);
        }
        entry.store(sortKey, 1, depth, raw);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {

        return name + ": " + super.toString();
    }

    /**
     * Write the letter group to a writer.
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * @param trace the indicator for tracing
     * 
     * @throws IOException in case of an I/O error
     * @throws LNonMatchingTypeException in case of an error
     */
    public void write(Writer writer, LInterpreter interpreter, boolean trace)
            throws IOException,
                LNonMatchingTypeException {

        SortedSet<String> sortedKeys = sortedKeys();
        if (sortedKeys.isEmpty()) {
            return;
        }

        LMarkup markupLetterGroup =
                (LMarkup) interpreter.get("markup-letter-group");

        boolean first = true;
        markupLetterGroup.write(writer, interpreter, name, LMarkup.OPEN, trace);
        String openHead = "markup:letter-group-" + name + "-open-head";
        String closeHead = "markup:letter-group-" + name + "-close-head";
        if (interpreter.get(LSymbol.get(openHead)) != null
                || interpreter.get(LSymbol.get(closeHead)) != null) {
            markupLetterGroup.write(writer, interpreter, name,
                LMarkup.OPEN_HEAD, trace);
            LValue trans =
                    interpreter.get(LSymbol.get("markup:letter-group-" + name
                            + "-transform"));
            if (trans == null || !(trans instanceof Transform)) {
                writer.write(name);
            } else {
                writer.write(((Transform) trans).transform(name));
            }
            markupLetterGroup.write(writer, interpreter, name,
                LMarkup.CLOSE_HEAD, trace);
        }

        for (String key : sortedKeys) {
            IndexEntry entry = map.get(key);
            if (first) {
                first = false;
            } else {
                markupLetterGroup.write(writer, interpreter, name, LMarkup.SEP,
                    trace);
            }

            entry.write(writer, interpreter, trace, 0);
        }
        markupLetterGroup
            .write(writer, interpreter, name, LMarkup.CLOSE, trace);
    }

}
