/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst.code.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibIndexOutOfBoundsException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.name.Name;
import org.extex.exbib.core.name.NameFactory;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function <code>format.name$</code>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:
 * <dt>
 * <dd>Pops the top three literals (they are a string, an integer, and a string
 * literal). The last string literal represents a name list (each name
 * corresponding to a person), the integer literal specifies which name to pick
 * from this list, and the first string literal specifies how to format this
 * name, as explained in the next subsection. Finally, this function pushes the
 * formatted name.</dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X web documentation:</dt>
 * <dd>The <code>built_in</code> function <code>format.name$</code> pops the top
 * three literals (they are a string, an integer, and a string literal, in that
 * order). The last string literal represents a name list (each name
 * corresponding to a person), the integer literal specifies which name to pick
 * from this list, and the first string literal specifies how to format this
 * name, as described in the B<small>IB</small>T<sub>E</sub>X documentation.
 * Finally, this function pushes the formatted name. If any of the types is
 * incorrect, it complains and pushes the null string.</dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X web documentation:</dt>
 * <dd>Here we output either the <code>.bst</code> given string if it exists, or
 * else the <code>.bib</code> <code>sep_char</code> if it exists, or else the
 * default string. A <code>tie</code> is the default space character between the
 * last two tokens of the name part, and between the first two tokens if the
 * first token is short enough; otherwise, a <code>space</code> is the default.
 * 
 * <pre>
 *     long_token = 3       {a token this length or longer is ``long''}
 *  </pre>
 * 
 * </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class FormatName extends AbstractCode implements Serializable {

    /**
     * The class Format is a container for a list of FormatItems.
     */
    private class Format extends ArrayList<FormatItem> {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2008L;

        /**
         * Creates a new object.
         * 
         * @param format the format string to parse
         * @param locator the locator for the format
         * 
         * @throws ExBibSyntaxException in case that the syntax is not correct
         * @throws ExBibImpossibleException this case should never happen
         * @throws ExBibException in case that something else went wrong
         */
        public Format(String format, Locator locator) throws ExBibException {

            super();
            parse(format, locator);
        }

        /**
         * Check if a buffer contains an ending.
         * 
         * @param sb the buffer
         * @param s the string to compare to
         * @param index the last character position in buffer to start
         *        comparison with; anything behind is treated as not present
         * 
         * @return <code>true</code> if the buffer ends in the string
         */
        private boolean endsIn(StringBuilder sb, String s, int index) {

            int i = index;
            for (int ti = s.length() - 1; ti >= 0; ti--) {
                char c = sb.charAt(i);
                char tc = s.charAt(ti);
                if (i < 0 || c != tc) {
                    return false;
                }
                i--;
            }

            return true;
        }

        /**
         * Count the number of text characters in a buffer and compare it
         * against a given limit.
         * 
         * <p>
         * This function reimplements the function 418 of the
         * B<small>IB</small>T<sub>E</sub>X web sources.
         * </p>
         * 
         * @param buffer the buffer to analyze
         * @param enoughChars the threshold
         * @param start the starting position
         * @param end the ending position
         * 
         * @return <code>true</code> iff the number of text characters is at
         *         least the limit
         */
        protected boolean enoughTextChars(StringBuilder buffer,
                int enoughChars, int start, int end) {

            int count = 0;
            int brace_level = 0;
            int ptr = start;
            while (ptr < end && count < enoughChars) {
                ++ptr;
                if (buffer.charAt(ptr - 1) == '{') {
                    ++brace_level;
                    if (brace_level == 1 && ptr < end
                            && buffer.charAt(ptr) == '\\') {
                        for (ptr++; ptr < end && brace_level > 0; ptr++) {
                            char c = buffer.charAt(ptr);
                            if (c == '}') {
                                --brace_level;
                            } else if (c == '{') {
                                ++brace_level;
                            }
                        }
                    }
                } else if (buffer.charAt(ptr - 1) == '}') {
                    --brace_level;
                }
                ++count;
            }
            return count >= enoughChars;
        }

        /**
         * Format all names in the list.
         * 
         * @param name the name to format
         * @param locator the locator
         * 
         * @return the formatted names
         * 
         * @throws ExBibException in case that something happens
         */
        public String format(Name name, Locator locator) throws ExBibException {

            StringBuilder sb = new StringBuilder();
            int tielen = tie.length();

            for (FormatItem fi : this) {
                int start = sb.length();
                if (!fi.format(sb, name, locator)) {
                    continue;
                }
                int len = sb.length();

                if (endsIn(sb, tie, len - 1)) {
                    if (endsIn(sb, tie, len - 1 - tielen)) {
                        sb.delete(len - tielen, len);
                        // } else if (sb.charAt(len - 1 - tielen) == '}'
                        // || enoughText(sb)) {
                    } else if (enoughTextChars(sb, 4, start, len)) {
                        sb.replace(len - tielen, len, " ");
                    }
                }
            }

            return sb.toString();
        }

        /**
         * Parse a format string and generate an internal representation of it.
         * 
         * @param format the format string to parse
         * @param locator the locator
         * 
         * @throws ExBibSyntaxException in case of an syntax error
         * @throws ExBibImpossibleException in case of an programming error
         */
        private void parse(String format, Locator locator)
                throws ExBibSyntaxException,
                    ExBibImpossibleException {

            int length = format.length();

            for (int i = 0; i < length;) {
                i = (format.charAt(i) == '{' //
                        ? parseItem(i, format, locator)
                        : parseToBrace(i, format));
            }
        };

        /**
         * Parses from a start position in a string which follows an opening
         * brace till the matching closing brace is found.
         * 
         * @param start start position in the format
         * @param item the item to store the result in
         * @param format the format string to parse
         * @param midp if <code>true</code> then the result is stored in the Mid
         *        attribute of item, otherwise it is stored in the Post
         *        attribute
         * @param locator the locator for the format string
         * 
         * @return the position of the first unprocessed character in format
         * 
         * @throws ExBibSyntaxException in case that the matching end brace
         *         could not be found before the end of format
         */
        private int parseEndOfBlock(int start, FormatItem item, String format,
                boolean midp, Locator locator) throws ExBibSyntaxException {

            int level = 1;

            for (int i = start; i < format.length(); i++) {
                switch (format.charAt(i)) {
                    case '{':
                        level++;
                        break;
                    case '}':

                        if (--level < 1) {
                            if (midp) {
                                item.setMid(format.substring(start, i));
                            } else {
                                item.setPost(format.substring(start, i));
                            }

                            return i + 1;
                        }

                        break;
                }
            }

            Localizer localizer = LocalizerFactory.getLocalizer(getClass());
            throw new ExBibSyntaxException(localizer
                .format("Missing.end.of.group"), locator);
        }

        /**
         * Parse a format item. I.e. a brace group at level 1 with a predefined
         * content structure.
         * 
         * @param start the index in <code>format</code> for the initial '{'
         * @param format the format string to parse
         * @param locator the locator for the format string
         * 
         * @return the index in <code>format</code> containing the first
         *         character after the group parsed
         * 
         * @throws ExBibSyntaxException in case of an syntax error
         * @throws ExBibImpossibleException in case of an programming error
         */
        private int parseItem(int start, String format, Locator locator)
                throws ExBibSyntaxException,
                    ExBibImpossibleException {

            int level = 1;
            char letter = ' ';
            int beg = start;

            for (int i = ++beg; i < format.length(); i++) {
                switch (format.charAt(i)) {
                    case '{':
                        level++;
                        break;
                    case '}':
                        level--;
                        break;
                    case 'f':
                    case 'l':
                    case 'v':
                    case 'j':

                        if (level == 1) {
                            String pre = format.substring(beg, i);
                            letter = format.charAt(i);

                            if (++i >= format.length()) {
                                throw new ExBibSyntaxException(LocalizerFactory
                                    .getLocalizer(getClass()).format(
                                        "Missing.end.of.item"), locator);
                            }

                            char c = format.charAt(i);

                            if (c == letter) {
                                letter = Character.toUpperCase(letter);

                                if (++i >= format.length()) {
                                    throw new ExBibSyntaxException(
                                        LocalizerFactory.getLocalizer(
                                            getClass()).format(
                                            "Missing.end.of.item"), locator);
                                }

                                c = format.charAt(i);
                            }

                            FormatItem item = null;

                            switch (letter) {
                                case 'f':
                                    item = new FormatF();
                                    break;
                                case 'l':
                                    item = new FormatL();
                                    break;
                                case 'v':
                                    item = new FormatV();
                                    break;
                                case 'j':
                                    item = new FormatJ();
                                    break;
                                case 'F':
                                    item = new FormatFF();
                                    break;
                                case 'L':
                                    item = new FormatLL();
                                    break;
                                case 'V':
                                    item = new FormatVV();
                                    break;
                                case 'J':
                                    item = new FormatJJ();
                                    break;
                                default:
                                    throw new ExBibImpossibleException(
                                        LocalizerFactory.getLocalizer(
                                            getClass()).format(
                                            "desaster.format",
                                            Character.toString(letter)),
                                        locator);
                            }

                            item.setPre(pre);
                            add(item);

                            if (c == '{') {
                                i = parseEndOfBlock(i + 1, item, format, true,//
                                    locator);
                            }

                            return parseEndOfBlock(i, item, format, false,
                                locator);
                        }

                        break;
                }
            }

            Localizer localizer = LocalizerFactory.getLocalizer(getClass());
            throw new ExBibSyntaxException(localizer
                .format("Missing.end.of.item="), locator);
        }

        /**
         * Find the matching closing brace in a String. The starting position
         * <tt>start</tt> points to the first character after the opening brace
         * in the format string <tt>fmt</tt>.
         * 
         * @param start the starting position to consider
         * @param format the format string to parse
         * 
         * @return the position of the closing brace
         */
        private int parseToBrace(int start, String format) {

            int brace = format.indexOf('{', start);

            if (start == brace) {
                return brace;
            }

            FormatItem item = new FormatItem(null);
            add(item);

            if (brace < 0) {
                item.setPre(format.substring(start));
                return format.length();
            }

            item.setPre(format.substring(start, brace));
            return brace;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();
            for (FormatItem fi : this) {
                sb.append(fi.toString());
            }
            return sb.toString();
        }
    }

    /**
     * This is a format item for an abbreviated first name.
     */
    private class FormatF extends FormatItem {

        /**
         * Creates a new object.
         */
        public FormatF() {

            super("f");
        }

        /**
         * Format the first names in its short form.
         * 
         * @param sb the target StringBuilder
         * @param name the name to format
         * @param locator the locator from the users perspective
         * 
         * @throws ExBibException in case that no initial is found
         */
        @Override
        public boolean format(StringBuilder sb, Name name, Locator locator)
                throws ExBibException {

            return fmtInitials(sb, name.getFirst(), getMid(), ". ", getPre(),
                getPost(), 1, locator);
        }
    }

    /**
     * This object represents a FormatItem meant to be used for formatting a
     * first name in its long form.
     */
    private class FormatFF extends FormatItem {

        /**
         * Creates a new object.
         */
        public FormatFF() {

            super("ff");
        }

        /**
         * Format the first names in its long form.
         * 
         * @param sb the target StringBuilder
         * @param name the name to format
         * @param locator the locator from the users perspective
         */
        @Override
        public boolean format(StringBuilder sb, Name name, Locator locator) {

            List<String> part = name.getFirst();
            return fmtFull(sb, part, getMid(), " ", getPre(), getPost(), part
                .size() - 1);
        }
    }

    /**
     * This object represents a FormatItem meant to be used for formatting a
     * constant string. It is also used as a base class for other format items.
     */
    private class FormatItem {

        /**
         * The field <tt>id</tt> contains the id for printing.
         */
        private String id;

        /**
         * The <i>mid</i> string is inserted between multi-part fragments of a
         * name.
         */
        private String mid = null;

        /**
         * The <i>post</i> string is inserted after multi-part fragments of a
         * name if those fragments are not empty.
         */
        private String post = "";

        /**
         * The <i>pre</i> string is inserted before multi-part fragments of a
         * name if those fragments are not empty.
         */
        private String pre = "";

        /**
         * Creates a new object.
         * 
         * @param id the id for printing
         */
        public FormatItem(String id) {

            super();
            this.id = id;
        }

        /**
         * Format a part of the name. In this case the constant stored in the
         * object is used and the name is ignored completely.
         * 
         * @param sb the string buffer to store the result in
         * @param name the (ignored) name to format
         * @param locator the locator
         * 
         * @return <code>true</code> iff the buffer has been modified
         * 
         * @throws ExBibException never; just for the subclasses
         */
        public boolean format(StringBuilder sb, Name name, Locator locator)
                throws ExBibException {

            sb.append(pre);
            return !"".equals(pre);
        }

        /**
         * Getter for the <i>mid</i> string.
         * 
         * @return the current value
         */
        public String getMid() {

            return mid;
        }

        /**
         * Getter for the <i>post</i> string.
         * 
         * @return the current value
         */
        public String getPost() {

            return post;
        }

        /**
         * Getter for the <i>pre</i> string.
         * 
         * @return the current value
         */
        public String getPre() {

            return pre;
        }

        /**
         * Setter for the <i>mid</i> string. The <i>mid</i> string is inserted
         * between multi-part fragments of a name.
         * 
         * @param string the new value
         */
        public void setMid(String string) {

            mid = string;
        }

        /**
         * Setter for the <i>post</i> string. The <i>post</i> string is inserted
         * after multi-part fragments of a name if those fragments are not
         * empty.
         * 
         * @param string the new value
         */
        public void setPost(String string) {

            post = string;
        }

        /**
         * Setter for the <i>pre</i> string. The <i>pre</i> string is inserted
         * before multi-part fragments of a name if those fragments are not
         * empty.
         * 
         * @param string the new value
         */
        public void setPre(String string) {

            pre = string;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            if (id == null) {
                return pre + (mid == null ? "" : mid) + post;
            }
            return "{" + pre + id + (mid == null ? "" : mid) + post + "}";
        }
    }

    /**
     * This object represents a FormatItem meant to be used for formatting a jr
     * part in its short form.
     */
    private class FormatJ extends FormatItem {

        /**
         * Creates a new object.
         */
        public FormatJ() {

            super("j");
        }

        /**
         * Format the junior part in its short form.
         * 
         * @param buffer the target StringBuilder
         * @param name the name to format
         * @param locator the locator from the users perspective
         * 
         * @throws ExBibException in case that no initial is found
         */
        @Override
        public boolean format(StringBuilder buffer, Name name, Locator locator)
                throws ExBibException {

            return fmtInitials(buffer, name.getJr(), getMid(), ". ", getPre(),
                getPost(), 2, locator);
        }
    }

    /**
     * This object represents a FormatItem meant to be used for formatting a
     * junior part in its long form.
     */
    private class FormatJJ extends FormatItem {

        /**
         * Creates a new object.
         */
        public FormatJJ() {

            super("jj");
        }

        /**
         * Format the junior part in its long form.
         * 
         * @param buffer the target StringBuilder
         * @param name the name to format
         * @param locator the locator from the users perspective
         */
        @Override
        public boolean format(StringBuilder buffer, Name name, Locator locator) {

            return fmtFull(buffer, name.getJr(), getMid(), " ", getPre(),
                getPost(), 0);
        }
    }

    /**
     * This object represents a FormatItem meant to be used for formatting a
     * last name in its short form.
     */
    private class FormatL extends FormatItem {

        /**
         * Creates a new object.
         */
        public FormatL() {

            super("l");
        }

        /**
         * Format the last names in its short form.
         * 
         * @param buffer the target StringBuilder
         * @param name the name to format
         * @param locator the locator from the users perspective
         * 
         * @throws ExBibException in case that no initial is found
         */
        @Override
        public boolean format(StringBuilder buffer, Name name, Locator locator)
                throws ExBibException {

            List<String> part = name.getLast();
            return fmtInitials(buffer, part, getMid(), ". ", getPre(),
                getPost(), part.size() - 1, locator);
        }
    }

    /**
     * This object represents a FormatItem meant to be used for formatting a
     * last name in its long form.
     */
    private class FormatLL extends FormatItem {

        /**
         * Creates a new object.
         */
        public FormatLL() {

            super("ll");
        }

        /**
         * Format the last names in its long form.
         * 
         * @param buffer the target StringBuilder
         * @param name the name to format
         * @param locator the locator from the users perspective
         */
        @Override
        public boolean format(StringBuilder buffer, Name name, Locator locator) {

            List<String> part = name.getLast();
            return fmtFull(buffer, part, getMid(), " ", getPre(), getPost(),
                part.size() - 1);
        }
    }

    /**
     * This object represents a FormatItem meant to be used for formatting a von
     * part in its short form.
     */
    private class FormatV extends FormatItem {

        /**
         * Creates a new object.
         */
        public FormatV() {

            super("v");
        }

        /**
         * Format a von part in its short form.
         * 
         * @param buffer the target StringBuilder
         * @param name the name to format
         * @param locator the locator from the users perspective
         * 
         * @throws ExBibException in case that no initial is found
         */
        @Override
        public boolean format(StringBuilder buffer, Name name, Locator locator)
                throws ExBibException {

            List<String> part = name.getVon();
            int n = (part.size() > 1 && part.get(0).length() < 3 ? 1 : -99);
            return fmtInitials(buffer, part, getMid(), ". ", getPre(),
                getPost(), n, locator);
        }
    }

    /**
     * This object represents a FormatItem meant to be used for formatting a von
     * part in its long form.
     */
    private class FormatVV extends FormatItem {

        /**
         * Creates a new object.
         */
        public FormatVV() {

            super("vv");
        }

        /**
         * Format a von part in its long form.
         * 
         * @param buffer the target StringBuilder
         * @param name the name to format
         * @param locator the locator from the users perspective
         */
        @Override
        public boolean format(StringBuilder buffer, Name name, Locator locator) {

            List<String> part = name.getVon();
            int n = (part.size() > 1 && part.get(0).length() < 3 ? 1 : 0);
            return fmtFull(buffer, part, getMid(), " ", getPre(), getPost(), n);
        }
    }

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * The field <tt>formatCache</tt> contains the format cache. To avoid the
     * re-parsing of a format string the result of the parsing is stored in the
     * format cache. The key is the string representation. Thus it is possible
     * to get the Format from the cache.
     */
    private Map<String, Format> formatCache = new HashMap<String, Format>();

    /**
     * The field <tt>tie</tt> contains the string used as tie.
     */
    protected String tie = "~";

    /**
     * The field <tt>instance</tt> contains the one instance used for the static
     * method.
     */
    private static FormatName instance = null;

    /**
     * Format a name like the B<small>IB</small>T<sub>E</sub>X built-in function
     * <code>format.name$</code>.
     * 
     * @param names the names string
     * @param index the index of the name
     * @param fmt the format
     * 
     * @return the formatted name
     * 
     * @throws ExBibException in case that no initial is found
     */
    public static String formatName(String names, int index, String fmt)
            throws ExBibException {

        if (instance == null) {
            instance = new FormatName();
        }

        return instance.format(names, index, fmt);
    }

    /**
     * Create a new object.
     */
    public FormatName() {

        this("");
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public FormatName(String name) {

        super(name);
    }

    /**
     * Extract the initials from a String and append it to the given
     * {@link StringBuilder}.
     * 
     * @param buffer the string buffer to append to
     * @param s the string to analyze
     * @param sep the separator
     * @param locator the locator
     * 
     * @return <code>true</code> if the last non-brace was a dot
     * 
     * @throws ExBibException in case of an error
     */
    protected boolean appendInitial(StringBuilder buffer, String s, String sep,
            Locator locator) throws ExBibException {

        int len = s.length();
        int level = 0;
        int i = 0;
        boolean dot = true;

        for (;;) {
            // skip spaces
            while (i < len && Character.isWhitespace(s.charAt(i))) {
                i++;
            }
            if (i >= len) {
                break;
            }

            for (; i < len; i++) {
                char c = s.charAt(i);
                buffer.append(c);

                if (c == '{') {
                    level++;
                } else if (c == '}') {
                    level--;
                } else if (c == '.') {
                    dot = true;
                } else if (c == '\\') {
                    if (++i >= s.length()) {
                        break;
                    }
                    dot = false;
                    c = s.charAt(i);
                    buffer.append(c);
                    if (Character.isLetter(c)) {
                        while (++i < len) {
                            c = s.charAt(i);
                            if (!Character.isLetter(c)) {
                                break;
                            }
                            buffer.append(c);
                        }
                        i--;
                        break;
                    }
                } else if (Character.isLetter(c)) {
                    dot = false;
                    break;
                }
            }

            for (int subLevel = 0; ++i < len;) {
                char c = s.charAt(i);

                if (c == '{') {
                    subLevel++;
                } else if (c == '}') {
                    subLevel--;
                } else if (subLevel == 0) {
                    if (c == '-') {
                        buffer.append(sep);
                        dot = true;
                        i++;
                        break;
                    } else if (c == '~') {
                        buffer.append('.');
                        buffer.append(tie);
                        i++;
                        dot = true;
                        break;
                    } else if (Character.isWhitespace(c)) {
                        break;
                    }
                }
            }
        }

        while (level-- > 0) {
            buffer.append('}');
        }
        return dot;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.Code#execute(BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        Token tformat = processor.popString(locator);
        TInteger tint = processor.popInteger(locator);
        String names = processor.popString(locator).getValue();
        int index = tint.getInt();
        String fmt = tformat.getValue();

        List<Name> namelist = NameFactory.getFactory().getNames(names, locator);

        if (index < 1 || index > namelist.size()) {
            throw new ExBibIndexOutOfBoundsException(Integer.toString(index),
                tint.getLocator());
        }

        Format format = formatCache.get(fmt);

        if (format == null) {
            format = new Format(fmt, tformat.getLocator());
            formatCache.put(fmt, format);
        }

        String s = format.format(namelist.get(index - 1), locator);
        processor.push(process(s));
    }

    /**
     * Format a part of a name part in its full form.
     * 
     * @param buffer the target StringBuilder
     * @param list the list of constituents of the name part
     * @param mid the mid parameter
     * @param midDefault the default value if the attribute mid is not set
     * @param pre the pre parameter
     * @param post the post parameter
     * @param lineLength the line length
     * @return the indicator that the buffer has been modified.
     */
    protected boolean fmtFull(StringBuilder buffer, List<String> list,
            String mid, String midDefault, String pre, String post,
            int lineLength) {

        int len = buffer.length();
        Iterator<String> iterator = list.iterator();

        if (iterator.hasNext()) {
            String m = (mid == null ? midDefault : mid);
            String t = (mid == null ? tie : mid);
            int i = 1;
            buffer.append(pre);
            buffer.append(iterator.next());

            while (iterator.hasNext()) {
                buffer.append(i++ == lineLength ? t : m);
                buffer.append(iterator.next());
            }

            buffer.append(post);
        }
        return len != buffer.length();
    }

    /**
     * Format a part of a name part in its short form; only the initials are
     * used.
     * 
     * @param buffer the target StringBuilder
     * @param list the list of constituents of the name part
     * @param mid the mid parameter
     * @param midDefault the default value if the attribute mid is not set
     * @param pre the pre parameter
     * @param post the post parameter
     * @param n the line length?
     * @param locator the locator from the users perspective
     * @return <code>true</code> iff the buffer has been modified
     * 
     * @throws ExBibException in case that no initial is found
     */
    protected boolean fmtInitials(StringBuilder buffer, List<String> list,
            String mid, String midDefault, String pre, String post, int n,
            Locator locator) throws ExBibException {

        int len = buffer.length();
        Iterator<String> iterator = list.iterator();

        if (iterator.hasNext()) {
            String m = (mid == null ? midDefault : mid);
            String t = (mid == null ? "." + tie : mid);
            String sep = (mid == null ? ".-" : mid);
            int i = 1;
            buffer.append(pre);
            appendInitial(buffer, iterator.next(), sep, locator);

            while (iterator.hasNext()) {
                buffer.append((i++) % 2 == n ? t : m);
                appendInitial(buffer, iterator.next(), sep, locator);
            }

            if (post.length() != 0 && buffer.length() != 0
                    && post.charAt(0) == buffer.charAt(buffer.length() - 1)) {
                buffer.deleteCharAt(buffer.length() - 1);
            }
            buffer.append(post);
        }
        return len != buffer.length();
    }

    /**
     * Format a name.
     * 
     * @param names the names field
     * @param index the number of the name to format
     * @param fmt the format specification
     * 
     * @return the formatted name
     * 
     * @throws ExBibException in case of an error
     */
    protected String format(String names, int index, String fmt)
            throws ExBibException {

        Locator locator = new Locator("", 0);
        List<Name> namelist = NameFactory.getFactory().getNames(names, locator);

        if (index < 1 || index > namelist.size()) {
            throw new ExBibIndexOutOfBoundsException(Integer.toString(index),
                locator);
        }

        Format format = formatCache.get(fmt);

        if (format == null) {
            format = new Format(fmt, locator);
            formatCache.put(fmt, format);
        }

        return format.format(namelist.get(index - 1), locator);

    }

    /**
     * Getter for the tie
     * 
     * @return the tie string
     */
    protected String getTie() {

        return tie;
    }

    /**
     * Post-processing method for massaging the result before it is pushed to
     * the stack. In this base implementation this is a dummy method which does
     * nothing. In derived classes this can be overwritten.
     * 
     * @param s the string to post-process
     * 
     * @return the token to push to the stack
     */
    protected TString process(String s) {

        return new TString(s, null);
    }

    /**
     * Setter for the tie.
     * 
     * @param tie the tie string
     */
    protected void setTie(String tie) {

        this.tie = tie;
    }

}
