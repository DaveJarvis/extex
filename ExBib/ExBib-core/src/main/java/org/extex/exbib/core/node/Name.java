/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.node;

import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.core.bst.exception.ExBibNoNameCommasException;
import org.extex.exbib.core.bst.exception.ExBibNoNameException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents a single name.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class Name {

    /**
     * Classify the name constituents.
     */
    private enum Type {
        /**
         * The field <tt>FIRST</tt> contains the type for the first name.
         */
        FIRST {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.node.Name.Type#store(java.lang.String,
             *      java.util.List, java.util.List, java.util.List,
             *      java.util.List)
             */
            @Override
            void store(String s, List<String> f, List<String> v,
                    List<String> l, List<String> j) {

                f.add(s);
            }
        },
        /**
         * The field <tt>VON</tt> contains the type for the von part.
         */
        VON {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.node.Name.Type#store(java.lang.String,
             *      java.util.List, java.util.List, java.util.List,
             *      java.util.List)
             */
            @Override
            void store(String s, List<String> f, List<String> v,
                    List<String> l, List<String> j) {

                v.add(s);
            }
        },
        /**
         * The field <tt>LAST</tt> contains the type for the last name.
         */
        LAST {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.node.Name.Type#store(java.lang.String,
             *      java.util.List, java.util.List, java.util.List,
             *      java.util.List)
             */
            @Override
            void store(String s, List<String> f, List<String> v,
                    List<String> l, List<String> j) {

                l.add(s);
            }
        },
        /**
         * The field <tt>JR</tt> contains the type for the jr part.
         */
        JR {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.node.Name.Type#store(java.lang.String,
             *      java.util.List, java.util.List, java.util.List,
             *      java.util.List)
             */
            @Override
            void store(String s, List<String> f, List<String> v,
                    List<String> l, List<String> j) {

                j.add(s);
            }
        },
        /**
         * The field <tt>COMMA</tt> contains the type for the comma.
         */
        COMMA {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.node.Name.Type#store(java.lang.String,
             *      java.util.List, java.util.List, java.util.List,
             *      java.util.List)
             */
            @Override
            void store(String s, List<String> f, List<String> v,
                    List<String> l, List<String> j) {

                // ignore
            }
        };

        /**
         * Store a value in one of the given lists.
         * 
         * @param s the value to store
         * @param firstPart the list of first parts
         * @param vonPart the list of von parts
         * @param lastPart the list of last parts
         * @param jrPart the list of jr parts
         */
        abstract void store(String s, List<String> firstPart,
                List<String> vonPart, List<String> lastPart, List<String> jrPart);
    };

    /**
     * Parses a string of names separated by the string "and" enclosed in
     * whitespace at brace level 0. The last name can be the string "others"
     * which is otherwise rejected as name.
     * 
     * @param names the string of names to parse
     * @param locator the locator
     * 
     * @return the list of names found
     * 
     * @throws ExBibSyntaxException in case that there are too many commas
     * @throws ExBibNoNameException in case that a substring which should be a
     *         name could not be parsed as such
     * @throws ExBibImpossibleException in case that a programming error is
     *         detected
     */
    public static List<Name> parse(String names, Locator locator)
            throws ExBibSyntaxException,
                ExBibNoNameException,
                ExBibImpossibleException {

        List<Name> result = new ArrayList<Name>();
        int start = 0;
        int level = 0;
        int len = names.length();

        while (start < len && Character.isWhitespace(names.charAt(start))) {
            start++;
        }

        for (int i = start; i < len; i++) {
            char c = names.charAt(i);

            if (Character.isWhitespace(c) && level == 0) {
                if (i < names.length() - 4
                        && (names.charAt(i + 1) == 'a' || names.charAt(i + 1) == 'A')
                        && (names.charAt(i + 2) == 'n' || names.charAt(i + 2) == 'N')
                        && (names.charAt(i + 3) == 'd' || names.charAt(i + 3) == 'D')
                        && Character.isWhitespace(names.charAt(i + 4))) {
                    result.add(new Name(names.substring(start, i), locator));
                    i += 4;
                    start = i + 1;
                }
            } else if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
            }
        }

        if (start < len) {
            String s = names.substring(start);
            if ("others".equals(s)) {
                result.add(new Name(s));
            } else {
                result.add(new Name(s, locator));
            }
        }
        return result;
    }

    /**
     * The field <tt>first</tt> contains the list of first name parts.
     */
    private List<String> first = new ArrayList<String>();

    /**
     * The field <tt>jr</tt> contains the list of jr name parts.
     */
    private List<String> jr = new ArrayList<String>();

    /**
     * The field <tt>last</tt> contains the list of last name parts.
     */
    private List<String> last = new ArrayList<String>();

    /**
     * The field <tt>von</tt> contains the list of von name parts.
     */
    private List<String> von = new ArrayList<String>();

    /**
     * Creates a new object.
     * 
     * @param name the complete string representation of the name
     */
    private Name(String name) {

        super();
        last.add(name);
    }

    /**
     * Creates a new object.
     * 
     * @param name the complete string representation of the name
     * @param locator the locator
     * 
     * @throws ExBibNoNameException in case that no name could be identified
     * @throws ExBibSyntaxException in case of a syntax error while parsing a
     *         name: there are too many commas
     * @throws ExBibImpossibleException in case that a programming error is
     *         detected
     */
    public Name(String name, Locator locator)
            throws ExBibNoNameException,
                ExBibImpossibleException,
                ExBibSyntaxException {

        super();
        scan(name, locator);
    }

    /**
     * Parse a name containing no separating commas. Those follow the following
     * pattern:
     * 
     * <pre>
     *   f*v*l+j*
     * </pre>
     * 
     * @param list the list of name constituents
     * @param type the buffer containing the type information of the
     *        constituents
     * @param locator the locator
     * @param name the name for error messages
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     */
    private void classify0(List<String> list, List<Type> type, Locator locator,
            String name) throws ExBibNoNameException {

        int len = type.size();
        int i;

        if (len == 1) {
            if (type.get(0) == Type.VON) {
                throw new ExBibNoNameException(name, locator);
            }
            type.set(0, Type.LAST);
            return;
        }

        for (i = len - 1; i >= 0 && type.get(i) == Type.VON; i--) {
            type.set(i, Type.JR);
        }

        i = type.lastIndexOf(Type.VON);

        if (i >= 0) {
            for (i++; i < len && type.get(i) == Type.FIRST; i++) {
                type.set(i, Type.LAST);
            }
            if (type.get(i - 1) != Type.LAST) {
                throw new ExBibNoNameException(name, locator);
            }
        } else {
            i = type.lastIndexOf(Type.FIRST);

            if (i < 0) {
                throw new ExBibNoNameException(name, locator);
            }

            type.set(i, Type.LAST);
        }
    }

    /**
     * Parse a name containing one separating comma. Those follow the following
     * patterns:
     * 
     * <pre>
     * v*l+,f*
     * f*v*l+,j*
     * </pre>
     * 
     * @param list the list of name constituents
     * @param type the buffer containing the type information of the
     *        constituents
     * @param locator the locator
     * @param name the name for error messages
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     * @throws ExBibImpossibleException in case the comma is not found
     */
    private void classify1(List<String> list, List<Type> type, Locator locator,
            String name) throws ExBibNoNameException, ExBibImpossibleException {

        int index = type.lastIndexOf(Type.COMMA);
        int len = type.size();

        if (index < 0) {
            throw new ExBibImpossibleException(LocalizerFactory.getLocalizer(
                getClass()).format("Comma.not.found"));
        } else if (index == len - 1) {
            throw new ExBibNoNameException(name, locator);

        }

        boolean j = false;
        boolean l = false;

        for (int i = index + 1; i < len; i++) {
            if (type.get(i) == Type.VON) {
                j = true;
            } else {
                l = true;
            }
        }

        if (j && !l) {
            for (int i = index + 1; i < len; i++) {
                type.set(i, Type.JR);
            }
            type.set(index - 1, Type.LAST);
        } else {
            for (int i = 0; i < index; i++) {
                type.set(i, type.get(i) == Type.FIRST ? Type.LAST : Type.VON);
            }
        }
    }

    /**
     * Parse a name containing two separating commas. Those follow the following
     * pattern:
     * 
     * <pre>
     * v*l+,j+,f*
     * </pre>
     * 
     * @param list the list of name constituents
     * @param type the buffer containing the type information of the
     *        constituents
     * @param locator the locator
     * @param name the name for error messages
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     * @throws ExBibImpossibleException in case the two commas are not found
     */
    private void classify2(List<String> list, List<Type> type, Locator locator,
            String name) throws ExBibNoNameException, ExBibImpossibleException {

        int last = type.indexOf(Type.COMMA);

        if (last < 0) {
            throw new ExBibImpossibleException(LocalizerFactory.getLocalizer(
                getClass()).format("Comma.not.found"));
        } else if (type.get(type.size() - 1) == Type.COMMA) {
            throw new ExBibNoNameException(name, locator);
        }

        for (int i = 0; i < last; i++) {
            Type t = type.get(i);
            type.set(i, t == Type.FIRST ? Type.LAST : Type.VON);
        }

        for (int i = last + 1; type.get(i) != Type.COMMA; i++) {
            type.set(i, Type.JR);
        }
    }

    /**
     * Getter for the <i>first</i> components.
     * 
     * @return the list of the <i>first</i> components
     */
    public List<String> getFirst() {

        return first;
    }

    /**
     * Getter for the <i>junior</i> components.
     * 
     * @return the list of the <i>j</i> components
     */
    public List<String> getJr() {

        return jr;
    }

    /**
     * Getter for the <i>last</i> components.
     * 
     * @return the list of the <i>last</i> components
     */
    public List<String> getLast() {

        return last;
    }

    /**
     * Getter for the <i>von</i> components.
     * 
     * @return the list of the <i>von</i> components
     */
    public List<String> getVon() {

        return von;
    }

    /**
     * Search for the first letter in the String and determine whether this
     * character is in upper case. If no letter is found then <code>false</code>
     * is returned.
     * 
     * @param s the String to examine
     * 
     * @return <code>true</code> iff the first letter is upper case
     */
    private boolean isUpper(String s) {

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isUpperCase(c)) {
                return true;
            } else if (Character.isLowerCase(c)) {
                return false;
            }
        }

        return false;
    }

    /**
     * Parses a name and store the parts in the appropriate internal lists.
     * 
     * @param name the name to parse
     * @param locator the locator
     * 
     * @throws ExBibNoNameException in case that no name could be identified
     * @throws ExBibSyntaxException in case of a syntax error while parsing a
     *         name: there are too many commas
     * @throws ExBibImpossibleException in case that a programming error is
     *         detected
     */
    private void scan(String name, Locator locator)
            throws ExBibNoNameException,
                ExBibImpossibleException,
                ExBibSyntaxException {

        List<String> list = new ArrayList<String>();
        List<Type> type = new ArrayList<Type>();
        int start = 0;
        int level = 0;
        int commas = 0;

        for (start = 0; start < name.length()
                && Character.isWhitespace(name.charAt(start)); start++) {
            // skip spaces;
        }

        for (int i = start; i < name.length(); i++) {
            char c = name.charAt(i);

            if (c == '{') {
                level++;
            } else if (level == 0) {
                if (c == ',') {
                    if (type.size() == 0 && start == i) {
                        throw new ExBibNoNameException(name, locator);
                    } else if (start < i) {
                        String s = name.substring(start, i);
                        list.add(s);
                        type.add(isUpper(s) ? Type.FIRST : Type.VON);
                    }

                    list.add(",");
                    type.add(Type.COMMA);
                    start = i + 1;
                    commas++;
                } else if (Character.isWhitespace(c)) {
                    if (start < i) {
                        String s = name.substring(start, i);
                        list.add(s);
                        type.add(isUpper(s) ? Type.FIRST : Type.VON);
                    }

                    for (i++; i < name.length()
                            && Character.isWhitespace(name.charAt(i)); i++) {
                        // skip spaces;
                    }

                    start = i--;
                }
            } else if (c == '}') {
                level--;
            }
        }

        if (start < name.length()) {
            String s = name.substring(start);
            list.add(s);
            type.add(isUpper(s) ? Type.FIRST : Type.VON);
        }

        switch (commas) {
            case 0:
                classify0(list, type, locator, name);
                break;
            case 1:
                classify1(list, type, locator, name);
                break;
            case 2:
                classify2(list, type, locator, name);
                break;
            default:
                throw new ExBibNoNameCommasException(name, locator);
        }

        for (int i = 0; i < type.size(); i++) {
            type.get(i).store(list.get(i), first, von, last, jr);
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

        for (String s : first) {
            sb.append(s);
            sb.append(' ');
        }
        for (String s : von) {
            sb.append(s);
            sb.append(' ');
        }
        for (String s : last) {
            sb.append(s);
            sb.append(' ');
        }
        for (String s : jr) {
            sb.append(s);
            sb.append(' ');
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
