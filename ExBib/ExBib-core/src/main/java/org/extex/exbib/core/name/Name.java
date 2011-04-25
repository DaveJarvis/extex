/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.name;

import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.core.bst.exception.ExBibNoNameCommasException;
import org.extex.exbib.core.bst.exception.ExBibNoNameException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents a single name. It also contains utility methods to deal
 * with name lists.
 * 
 * <h2>Name Components</h2>
 * 
 * <p>
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X uses four components for names. Any name is analyzed and
 * decomposed into the four parts. The following parts of names are considered:
 * </p>
 * <dl>
 * <dt>Last part</dt>
 * <dd>The last name or christian name of a person is usually the last major
 * component of a name. This is the only part which is not optional.</dd>
 * <dt>First parts</dt>
 * <dd>The first name or given name of a person is usually the first component
 * of a name. This part is optional.</dd>
 * <dt>Von part</dt>
 * <dd>The von part of a name usually comes between first name and last name and
 * starts with lowercase letters. It is optional.</dd>
 * <dt>Junior part</dt>
 * <dd>The junior part of a name is an addition appended to the name. This part
 * is optional.</dd>
 * </dl>
 * 
 * <p>
 * The parts are separated by white space. This whitespace is only considered if
 * it occurs at brace level 0. Thus the grouping with braces is honored. It can
 * be used to tie together parts which would be torn apart otherwise.
 * </p>
 * <p>
 * Any part can consist of several words. Commas can be used to structure a
 * name. Thus we will use the number of commas for the analysis as well.
 * </p>
 * 
 * <h3>No Commas</h3>
 * 
 * <p>
 * If a name does not contain a comma then the following pattern is used to
 * determine the parts of the name:
 * </p>
 * 
 * <pre>
 *   &lt;<i>name</i>&gt; +&equiv; &lt;<i>first</i>&gt;* &lt;<i>von</i>&gt;* &lt;<i>last</i>&gt;* &lt;<i>jr</i>&gt;* 
 * </pre>
 * 
 * <p>
 * The name parts <em>first</em> and <em>last</em> consist of words for which
 * the first letter is an uppercase letter. The name parts <em>von</em> and
 * <em>jr</em> consist of words for which the first letter is a lowercase
 * letter.
 * </p>
 * <p>
 * Note that the star operator denotes an arbitrary number of repetitions of the
 * preceding syntactic entity. Especially, none is allowed. The star operator is
 * greedy; i.e. if two conflicting entities adjoin then the first one wins.
 * </p>
 * <p>
 * The following examples show how the names are classified. The last name is
 * printed in bold, the first name is printed in roman, the von part is printed
 * in italics, and the junior part is underlined.
 * </p>
 * <div style="margin-left:2em;"> <b>Aristotle</b><br />
 * <span>Leslie</span> <b>Lamport</b><br />
 * <span>Donald Ervin</span> <b>Knuth</b><br />
 * <span>Johannes Chrysostomus Wolfgangus Theophilus</span> <b>Mozart</b><br />
 * <span>Ludwig</span> <i>van</i> <b>Beethoven</b><br />
 * <span>Otto Eduard Leopold</span> <i>von</i> <b>Bismarck-Sch&ouml;nhausen</b><br />
 * <span>Miguel</span> <i>de</i> <b>Cervantes Saavedra</b><br />
 * <span>Sammy</span> <b>Davis</b> <u>jr.</u><br />
 * <span>Don Quixote</span> <i>de la</i> <b>Mancha</b> </div>
 * 
 * <h3>One Comma</h3>
 * <p>
 * If a name does not contain a comma then the following pattern is used to
 * determine the parts of the name:
 * </p>
 * 
 * <pre>
 *   &lt;<i>name</i>&gt; +&equiv; &lt;<i>von</i>&gt;* &lt;<i>last</i>&gt; <tt>,</tt> &lt;<i>first</i>&gt;*
 *          |  &lt;<i>first</i>&gt;* &lt;<i>von</i>&gt;* &lt;<i>last</i>&gt; <tt>,</tt> &lt;<i>jr</i>&gt;* 
 * </pre>
 * 
 * <p>
 * The name parts <em>first</em> and <em>last</em> consist of words for which
 * the first letter is an uppercase letter. The name parts <em>von</em> and
 * <em>jr</em> consist of words for which the first letter is a lowercase
 * letter.
 * </p>
 * <p>
 * The following examples show how the names are classified. The last name is
 * printed in bold, the first name is printed in roman, the von part is printed
 * in italics, and the junior part is underlined.
 * </p>
 * <div style="margin-left:2em;"> <b>Lamport</b>, <span>Leslie</span><br />
 * <b>Knuth</b>, <span>Donald Ervin</span> <br />
 * <b>Mozart</b>, <span>Johannes Chrysostomus Wolfgangus Theophilus</span> <br />
 * <b>Beethoven</b>, <span>Ludwig</span> <i>van</i> <br />
 * <i>von</i> <b>Bismarck-Sch&ouml;nhausen</b>, <span>Otto Eduard Leopold</span> <br />
 * <i>de</i> <b>Cervantes Saavedra</b>, <span>Miguel</span> <br />
 * <span>Sammy</span> <b>Davis</b>, <u>Jr.</u> </div>
 * 
 * <h3>Two Commas</h3>
 * <p>
 * If a name does contain two commas then the following pattern is used to
 * determine the parts of the name:
 * </p>
 * 
 * <pre>
 *   &lt;<i>name</i>&gt; +&equiv; &lt;<i>last</i>&gt;* &lt;<i
 *   >von</i>&gt;* <tt>,</tt> &lt;<i>jr</i>&gt;* <tt>,</tt> &lt;<i>first</i>&gt;* 
 * </pre>
 * 
 * <p>
 * The name parts <em>first</em> and <em>last</em> consist of words for which
 * the first letter is an uppercase letter. The name parts <em>von</em> and
 * <em>jr</em> consist of words for which the first letter is a lowercase
 * letter.
 * </p>
 * <p>
 * The following examples show how the names are classified. The last name is
 * printed in bold, the first name is printed in roman, the von part is printed
 * in italics, and the junior part is underlined.
 * </p>
 * <div style="margin-left:2em;"> <b>Davis</b>, <u>Jr.</u>, <span>Sammy</span>
 * </div>
 * 
 * <h3>More Commas</h3>
 * <p>
 * More than two commas are not understood by the name parsing in
 * <logo>&epsilon;&chi;Bib</logo>.
 * </p>
 * 
 * <h2>Name Lists</h2>
 * <p>
 * Names usually come in bibliographies as single names or as lists of names.
 * Thus we have to take care of lists of names. Those lists are made up of
 * single names separated by the word <tt>and</tt> surrounded by whitespace.
 * This separator is only considered at brace level 0. Thus it is possible to
 * protect embedded words `and'. Those might be parts of company names &ndash;
 * e.g. acting an author.
 * </p>
 * <p>
 * The following example is recognizes as two names:
 * </p>
 * <div style="margin-left:2em;"> <b>Barnes</b> <tt>and</tt> <b>Noble</b> </div>
 * <p>
 * The following example is recognized as a single name:
 * </p>
 * <div style="margin-left:2em;"> <b>{Barnes and Noble}</b> </div>
 * <p>
 * It there are more names in a name list than feasible then the remaining names
 * after some initial ones can be abbreviated. For this purpose simply write
 * <tt>and others</tt> at the end.
 * </p>
 * <p>
 * The formal syntax is as follows:
 * </p>
 * 
 * <pre>
 *   &lt;<i>name list</i>&gt; &equiv; &lt;<i>name</i>&gt;
 *               | &lt;<i>name list</i>&gt; <tt> and </tt> &lt;<i>name</i>&gt; 
 *               | &lt;<i>name list</i>&gt; <tt> and others</tt> 
 * </pre>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
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
             * @see org.extex.exbib.core.name.Name.Type#store(java.lang.String,
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
             * @see org.extex.exbib.core.name.Name.Type#store(java.lang.String,
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
             * @see org.extex.exbib.core.name.Name.Type#store(java.lang.String,
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
             * @see org.extex.exbib.core.name.Name.Type#store(java.lang.String,
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
             * @see org.extex.exbib.core.name.Name.Type#store(java.lang.String,
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
     * Creates a new object and fill the last name with the given string.
     * 
     * @param name the complete string representation of the name
     */
    private Name(String name) {

        last.add(name);
    }

    /**
     * Creates a new object and fills it with the attributes from a string. For
     * this purpose the string is parsed to determine its constituents.
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
     * @param type the buffer containing the type information of the
     *        constituents
     * @param locator the locator
     * @param name the name for error messages
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     */
    private void classify0(List<Type> type, Locator locator, String name)
            throws ExBibNoNameException {

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
            if (type.get(i - 1) != Type.LAST || type.indexOf(Type.LAST) < 0) {
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
     * @param type the buffer containing the type information of the
     *        constituents
     * @param locator the locator
     * @param name the name for error messages
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     * @throws ExBibImpossibleException in case the comma is not found
     */
    private void classify1(List<Type> type, Locator locator, String name)
            throws ExBibNoNameException,
                ExBibImpossibleException {

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
            if (type.get(index - 1) != Type.FIRST) {
                throw new ExBibNoNameException(name, locator);
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
     * @param type the buffer containing the type information of the
     *        constituents
     * @param locator the locator
     * @param name the name for error messages
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     * @throws ExBibImpossibleException in case the two commas are not found
     */
    private void classify2(List<Type> type, Locator locator, String name)
            throws ExBibNoNameException,
                ExBibImpossibleException {

        int previous = type.indexOf(Type.COMMA);

        if (previous < 0) {
            throw new ExBibImpossibleException(LocalizerFactory.getLocalizer(
                getClass()).format("Comma.not.found"));
        } else if (type.get(type.size() - 1) == Type.COMMA) {
            throw new ExBibNoNameException(name, locator);
        }

        for (int i = 0; i < previous; i++) {
            Type t = type.get(i);
            type.set(i, t == Type.FIRST ? Type.LAST : Type.VON);
        }

        for (int i = previous + 1; type.get(i) != Type.COMMA; i++) {
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
     * Search for the first letter in the argument and determine whether this
     * character is in upper case. If no letter is found then <code>false</code>
     * is returned.
     * 
     * @param s the {@link String} to examine
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

        while (start < name.length()
                && Character.isWhitespace(name.charAt(start))) {
            start++; // skip spaces;
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

                    do {
                        i++;
                    } while (i < name.length()
                            && Character.isWhitespace(name.charAt(i)));

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
                classify0(type, locator, name);
                break;
            case 1:
                classify1(type, locator, name);
                break;
            case 2:
                classify2(type, locator, name);
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
