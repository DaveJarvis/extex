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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.extex.exindex.core.exception.InconsistentLetterGroupException;
import org.extex.exindex.core.exception.LetterGroupCycleException;
import org.extex.exindex.core.exception.LetterGroupsClosedException;
import org.extex.exindex.lisp.exception.LException;

/**
 * This is the container for letter groups
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6649 $
 */
public class LetterGroupContainer {

    /**
     * The constant <tt>COMPERATOR</tt> contains the comparator to get the
     * long strings first.
     */
    private static final Comparator<String> COMPERATOR =
            new Comparator<String>() {

                public int compare(String o1, String o2) {

                    return -o1.compareTo(o2);
                }
            };

    /**
     * The field <tt>groups</tt> contains the mapping from names to letter
     * groups.
     */
    private Map<String, LetterGroup> groups =
            new HashMap<String, LetterGroup>();

    /**
     * The field <tt>index</tt> contains the index for accessing the prefixes.
     */
    private Map<Character, SortedSet<String>> index =
            new HashMap<Character, SortedSet<String>>();

    /**
     * The field <tt>sorted</tt> contains the collected letter groups. This
     * list is cleared during sorting.
     */
    private List<LetterGroup> letterGroups = new ArrayList<LetterGroup>();

    /**
     * The field <tt>prefixMap</tt> contains the mapping from prefixes to
     * letter groups.
     */
    private Map<String, LetterGroup> prefixMap =
            new HashMap<String, LetterGroup>();

    /**
     * The field <tt>sorted</tt> contains the sorted letter groups. When this
     * field is not <code>null</code> then no further letter groups can be
     * added.
     */
    private LetterGroup[] sorted;

    /**
     * Add a prefix to the index.
     * 
     * @param prefix the prefix
     */
    private void addToIndex(String prefix) {

        Character c =
                (prefix.equals("") ? null : Character.valueOf(prefix.charAt(0)));
        SortedSet<String> set = index.get(c);
        if (set == null) {
            set = new TreeSet<String>(COMPERATOR);
            index.put(c, set);
        }
        set.add(prefix);
    }

    /**
     * Search for a letter group and define it if it is not defined already. Add
     * links to all prefixes and return the equivalence class used. The
     * equivalence class is determined from the name and the prefixes. If for
     * one a letter group is known then this one is used. Otherwise a new one is
     * created.
     * <p>
     * If a prefix has already assigned a different letter group then this is
     * reported as error.
     * </p>
     * 
     * @param name the name
     * @param prefixes the prefixes
     * 
     * @return the letter group for the whole set
     * 
     * @throws LetterGroupsClosedException in case the method {@link #sorted()}
     *         has been invoked before
     * @throws InconsistentLetterGroupException in case of an inconsistency in
     *         the definition of prefixes
     */
    public LetterGroup defineLetterGroup(String name, String... prefixes)
            throws LetterGroupsClosedException,
                InconsistentLetterGroupException {

        if (sorted != null) {
            throw new LetterGroupsClosedException(name);
        }

        LetterGroup group = groups.get(name);

        if (group == null) {
            group = findPrefixGroup(prefixes);
            if (group == null) {
                group = new LetterGroup(name);
                letterGroups.add(group);
                addToIndex(name);
            }
            groups.put(name, group);
        }

        for (String prefix : prefixes) {
            LetterGroup g = prefixMap.get(prefix);
            if (g == null) {
                prefixMap.put(prefix, group);
                addToIndex(prefix);
            } else if (group != g) {
                throw new InconsistentLetterGroupException(name, g.getName());
            }
        }
        return group;
    }

    /**
     * Find a letter group for a given key. The letter group is found by
     * inspecting the prefixes and choosing the larges one with which the key is
     * started.
     * 
     * @param key the key
     * 
     * @return the associated letter group or <code>null</code> if none is
     *         found
     */
    public LetterGroup findLetterGroup(String key) {

        SortedSet<String> list = index.get(key.equals("")
                ? null
                : Character.valueOf(key.charAt(0)));

        if (list != null) {
            for (String prefix : list) {
                if (key.startsWith(prefix)) {
                    return prefixMap.get(prefix);
                }
            }
        }
        return null;
    }

    /**
     * Find a group for one of the prefixes. The prefixes are tried in turn
     * until one has a group assigned.
     * 
     * @param prefixes the list of prefixes
     * 
     * @return a group or <code>null</code>
     */
    private LetterGroup findPrefixGroup(String[] prefixes) {

        for (String prefix : prefixes) {
            LetterGroup group = groups.get(prefix);
            if (group != null) {
                return group;
            }
        }
        return null;
    }

    /**
     * Search for a letter group and return.
     * 
     * @param name the name of the letter group to get
     * 
     * @return the letter group for name or <code>null</code> if none was
     *         found
     */
    public LetterGroup getLetterGroup(String name) {

        return groups.get(name);
    }

    /**
     * Get the letter groups contained in a sorted list. Afterwards no more
     * letter groups can be added. Nevertheless this method can be invoked
     * several times returning the same list after each invocation.
     * 
     * @return the sorted list of letter groups
     * 
     * @throws LException in case of an error
     */
    public LetterGroup[] sorted() throws LException {

        if (sorted != null) {
            return sorted;
        }
        int size = letterGroups.size();
        LetterGroup last = null;
        sorted = new LetterGroup[size];

        for (int ptr = 0; ptr < size; ptr++) {
            LetterGroup group = (last == null ? null : last.uniqueAfter());
            if (group == null) {
                group = sortFindTop(letterGroups.get(0), size);
            }
            sorted[ptr] = group;
            for (int i = 0; i < size; i++) {
                if (letterGroups.get(i) == group) {
                    letterGroups.remove(i);
                    break;
                }
            }
            group.clearAfterBefore();
            last = group;
        }

        return sorted;
    }

    /**
     * Find a topmost element in the current order. This is accomplished by
     * following the "after" pointer until the first one is found. If the
     * underlying graph has a cycle then this would result in an endless loop.
     * This loop is terminated when a limit of steps is reached. This limit is
     * the number of nodes left in the graph.
     * 
     * @param letterGroup the letter group
     * @param limit the limit for cycle detection
     * 
     * @return the letter group which is currently top
     * 
     * @throws LetterGroupCycleException in case of a loop
     */
    private LetterGroup sortFindTop(LetterGroup letterGroup, int limit)
            throws LetterGroupCycleException {

        LetterGroup group = letterGroup;

        for (int n = limit; n > 0; n--) {
            LetterGroup after = group.getSomeAfter();
            if (after == null) {
                return group;
            }
            group = after;
        }

        // Error reporting

        StringBuilder sb = new StringBuilder(group.getName());
        List<LetterGroup> history = new ArrayList<LetterGroup>();
        group = letterGroup;

        for (int n = limit; n > 0; n--) {
            if (history.contains(group)) {
                break;
            }
            history.add(group);
            group = group.getSomeAfter();
            sb.append(" > ").append(group.getName());
        }

        throw new LetterGroupCycleException(sb.toString());
    }

}
