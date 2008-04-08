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

package org.extex.exbib.core.bst.node;

import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.core.bst.exception.ExBibNoNameException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents a single name.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class Name {

    /**
     * Parses a string of names separated by the string "and" enclosed in
     * whitespace at brace level 0.
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

        while (start < names.length()
                && Character.isWhitespace(names.charAt(start))) {
            start++;
        }

        for (int i = start; i < names.length(); i++) {
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

        if (start < names.length()) {
            result.add(new Name(names.substring(start), locator));
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
     * @param sl the list of name constituents
     * @param sb the buffer containing the type information of the constituents
     * @param locator the locator
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     */
    private void classify0(List<String> sl, StringBuffer sb, Locator locator)
            throws ExBibNoNameException {

        int last;

        for (last = sb.length() - 1; last >= 0 && sb.charAt(last) == 'v'; last--) {
            sb.setCharAt(last, 'j');
        }

        if (sl.size() == 1) {
            sb.setCharAt(0, 'l');
            return;
        }

        last = sb.lastIndexOf("v");

        if (last >= 0) {
            for (last++; last < sb.length() && sb.charAt(last) == 'f'; last++) {
                sb.setCharAt(last, 'l');
            }

            if (sb.charAt(last - 1) != 'l') {
                throw new ExBibNoNameException(sl.toString(), locator);
            }
        } else {
            last = sb.lastIndexOf("f");

            if (last < 0) {
                throw new ExBibNoNameException(sl.toString(), locator);
            }

            sb.setCharAt(last, 'l');
        }
    }

    /**
     * Parse a name containing one separating comma. Those follow the following
     * pattern:
     * 
     * <pre>
     * v*l+,f*
     * </pre>
     * 
     * @param sl the list of name constituents
     * @param sb the buffer containing the type information of the constituents
     * @param locator the locator
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     * @throws ExBibImpossibleException in case the comma is not found
     */
    private void classify1(List<String> sl, StringBuffer sb, Locator locator)
            throws ExBibNoNameException,
                ExBibImpossibleException {

        int last = sb.lastIndexOf(",");

        if (last < 0) {
            Localizer localizer = LocalizerFactory.getLocalizer(getClass());
            throw new ExBibImpossibleException(localizer
                .format("Comma.not.found"));
        }

        if (last == 0) {
            throw new ExBibNoNameException(sl.toString(), locator);
        }

        for (int i = 0; i < last; i++) {
            char c = sb.charAt(i);

            if (c == 'f') {
                sb.setCharAt(i, 'l');
            } else {
                sb.setCharAt(i, 'v');
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
     * @param sl the list of name constituents
     * @param sb the buffer containing the type information of the constituents
     * @param locator the locator
     * 
     * @throws ExBibNoNameException in case that the given string list does not
     *         constitute a name
     * @throws ExBibImpossibleException in case the two commas are not found
     */
    private void classify2(List<String> sl, StringBuffer sb, Locator locator)
            throws ExBibNoNameException,
                ExBibImpossibleException {

        int last = sb.indexOf(",");

        if (last < 0) {
            Localizer localizer = LocalizerFactory.getLocalizer(getClass());
            throw new ExBibImpossibleException(localizer
                .format("Comma.not.found"));
        }

        if (last == 0) {
            throw new ExBibNoNameException(sl.toString(), locator);
        }

        for (int i = 0; i < last; i++) {
            char c = sb.charAt(i);

            if (c == 'f') {
                sb.setCharAt(i, 'l');
            } else {
                sb.setCharAt(i, 'v');
            }
        }

        for (int i = last + 1; sb.charAt(i) != ','; i++) {
            sb.setCharAt(i, 'j');
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

        List<String> sl = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        int start = 0;
        int level = 0;
        int commas = 0;

        // skip spaces;
        for (start = 0; start < name.length()
                && Character.isWhitespace(name.charAt(start)); start++) {
            //
        }

        for (int i = start; i < name.length(); i++) {
            char c = name.charAt(i);

            if (level == 0) {
                if (c == ',') {
                    if (start < i) {
                        String s = name.substring(start, i);
                        sl.add(s);
                        sb.append(isUpper(s) ? 'f' : 'v');
                    }

                    sl.add(",");
                    sb.append(",");
                    start = i + 1;
                    commas++;
                } else if (Character.isWhitespace(c)) {
                    if (start < i) {
                        String s = name.substring(start, i);
                        sl.add(s);
                        sb.append(isUpper(s) ? 'f' : 'v');
                    }

                    // skip spaces;
                    for (i++; i < name.length()
                            && Character.isWhitespace(name.charAt(i)); i++) {
                        // skip
                    }

                    start = i--;
                }
            } else if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
            }
        }

        if (start < name.length()) {
            String s = name.substring(start);
            sl.add(s);
            sb.append(isUpper(s) ? 'f' : 'v');
        }

        switch (commas) {
            case 0:
                classify0(sl, sb, locator);
                break;
            case 1:
                classify1(sl, sb, locator);
                break;
            case 2:
                classify2(sl, sb, locator);
                break;
            default:
                Localizer localizer = LocalizerFactory.getLocalizer(getClass());
                throw new ExBibImpossibleException(localizer
                    .format("Too.many.commas"));
        }

        for (int i = 0; i < sb.length(); i++) {
            switch (sb.charAt(i)) {
                case 'f':
                    first.add(sl.get(i));
                    break;
                case 'l':
                    last.add(sl.get(i));
                    break;
                case 'v':
                    von.add(sl.get(i));
                    break;
                case 'j':
                    jr.add(sl.get(i));
                    break;
                case ',':
                    break;
                default:
                    Localizer localizer =
                            LocalizerFactory.getLocalizer(getClass());
                    throw new ExBibImpossibleException(localizer.format(
                        "Strange.classifier", sb.substring(i, i + 1)));
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return first + " " + von + " " + last + " " + jr;
    }

}
