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

import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This is the container for letter groups
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6649 $
 */
public class LetterGroupContainer {

    /**
     * The field <tt>index</tt> contains the index for accessing the prefixes.
     */
    private Map<Character, SortedSet<String>> index =
            new HashMap<Character, SortedSet<String>>();

    /**
     * The field <tt>groups</tt> contains the mapping from names to letter
     * groups.
     */
    private Map<String, LetterGroup> groups =
            new HashMap<String, LetterGroup>();

    /**
     * The field <tt>prefixMap</tt> contains the mapping from prefixes to
     * letter groups.
     */
    private Map<String, LetterGroup> prefixMap =
            new HashMap<String, LetterGroup>();

    /**
     * The field <tt>sorted</tt> contains the collected letter groups. This
     * list is cleared during sorting.
     */
    private List<LetterGroup> letterGroups = new ArrayList<LetterGroup>();

    /**
     * The field <tt>sorted</tt> contains the sorted letter groups. When this
     * field is not <code>null</code> then no further letter groups can be
     * added.
     */
    private List<LetterGroup> sorted;

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
            set = new TreeSet<String>(new Comparator<String>() {

                public int compare(String o1, String o2) {

                    return -o1.compareTo(o2);
                }
            });
            index.put(c, set);
        }
        set.add(prefix);
    }

    /**
     * Search for a letter group and define it if it is not defined already.
     * 
     * @param name the name of the letter group to get
     * 
     * @return the letter group for name
     */
    public LetterGroup defineLetterGroup(String name) {

        if (sorted != null) {
            throw new RuntimeException(LocalizerFactory.getLocalizer(
                LetterGroupContainer.class).format("TooLate"));
        }
        LetterGroup group = groups.get(name);
        if (group == null) {
            group = new LetterGroup(name);
            groups.put(name, group);
            letterGroups.add(group);
        }
        return group;
    }

    /**
     * Search for a letter group and define it if it is not defined already.
     * 
     * @param name the name of the letter group to get
     * 
     * @return the letter group for name
     */
    public LetterGroup defineLetterGroupAndIndex(String name) {

        if (sorted != null) {
            throw new RuntimeException(LocalizerFactory.getLocalizer(
                LetterGroupContainer.class).format("TooLate"));
        }
        LetterGroup group = groups.get(name);
        if (group == null) {
            group = new LetterGroup(name);
            groups.put(name, group);
            addToIndex(name);
            letterGroups.add(group);
        }
        return group;
    }

    /**
     * Find a letter group for a given key. The letter group is found by
     * inspecting the prefixes and choosing the larges one by which the key is
     * started.
     * 
     * @param key the key
     * 
     * @return the associated letter group or <code>null</code> if none is
     *         found
     */
    public LetterGroup findLetterGroup(String key) {

        SortedSet<String> list = index.get(key.equals("") //
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
     * @throws LException in case of a loop
     */
    private LetterGroup findTop(LetterGroup letterGroup, int limit)
            throws LException {

        LetterGroup g = letterGroup;

        for (int n = limit; n > 0; n--) {
            LetterGroup after = g.getSomeAfter();
            if (after == null) {
                return g;
            }
            g = after;
        }
        StringBuilder sb = new StringBuilder(g.getName());
        g = letterGroup;
        for (int n = limit; n > 0; n--) {
            g = g.getSomeAfter();
            sb.append(" > ").append(g.getName());
        }

        throw new LException(LocalizerFactory.getLocalizer(
            LetterGroupContainer.class).format("CycleDetected", sb.toString()));
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
     * Add links to all prefixes and return the equivalence class used. The
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
     * @throws LException in case of an error
     */
    public LetterGroup linkPrefixes(String name, LList prefixes)
            throws LException {

        if (sorted != null) {
            throw new RuntimeException(LocalizerFactory.getLocalizer(
                LetterGroupContainer.class).format("TooLate"));
        }

        LetterGroup g = null;

        for (LValue val : prefixes) {
            g = groups.get(LString.stringValue(val));
            if (g != null) {
                break;
            }
        }
        if (g == null) {
            g = defineLetterGroup(name);
        } else {
            LetterGroup gn = groups.get(name);
            if (gn == null) {
                groups.put(name, g);
            } else if (g != gn) {
                throw new LException(LocalizerFactory.getLocalizer(
                    LetterGroupContainer.class).format("InconsistenzPrefix",
                    name, gn.getName()));
            }
        }
        for (LValue val : prefixes) {
            String prefix = LString.stringValue(val);
            LetterGroup gn = groups.get(prefix);
            if (gn == null) {
                groups.put(prefix, g);
                addToIndex(prefix);
            } else if (g != gn) {
                throw new LException(LocalizerFactory.getLocalizer(
                    LetterGroupContainer.class).format("InconsistenzPrefix",
                    name, gn.getName()));
            }
        }
        return g;
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
    public List<LetterGroup> sorted() throws LException {

        if (sorted != null) {
            return sorted;
        }
        sorted = new ArrayList<LetterGroup>();

        for (int size = letterGroups.size(); size > 0; size--) {
            LetterGroup g = findTop(letterGroups.get(0), size);
            sorted.add(g);
            for (int i = 0; i < size; i++) {
                if (letterGroups.get(i) == g) {
                    letterGroups.remove(i);
                    break;
                }
            }
            g.clearAfterBefore();
        }

        return sorted;
    }

}
