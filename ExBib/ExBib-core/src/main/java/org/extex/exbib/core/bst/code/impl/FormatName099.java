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

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function
 * <code>format.name$</code>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top three literals (they are a string, an integer, and a string
 * literal). The last string literal represents a name list (each name
 * corresponding to a person), the integer literal specifies which name to pick
 * from this list, and the first string literal specifies how to format this
 * name, as explained in the next subsection. Finally, this function pushes the
 * formatted name. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X web documentation:</dt>
 * <dd> The <code>built_in</code> function <code>format.name$</code> pops
 * the top three literals (they are a string, an integer, and a string literal,
 * in that order). The last string literal represents a name list (each name
 * corresponding to a person), the integer literal specifies which name to pick
 * from this list, and the first string literal specifies how to format this
 * name, as described in the B<small>IB</small>T<sub>E</sub>X documentation.
 * Finally, this function pushes the formatted name. If any of the types is
 * incorrect, it complains and pushes the null string. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X web documentation:</dt>
 * <dd> Here we output either the <code>.bst</code> given string if it exists,
 * or else the <code>.bib</code> <code>sep_char</code> if it exists, or else
 * the default string. A <code>tie</code> is the default space character
 * between the last two tokens of the name part, and between the first two
 * tokens if the first token is short enough; otherwise, a <code>space</code>
 * is the default.
 * 
 * <pre>
 *     long_token = 3       {a token this length or longer is ``long''}
 *  </pre>
 * 
 * </dd>
 * </dl>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class FormatName099 extends FormatName {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * Create a new FormatName099 object.
     */
    public FormatName099() {

        super();
        setTie("~");
    }

    /**
     * Create a new FormatName099 object.
     * 
     * @param name the name
     */
    public FormatName099(String name) {

        super(name);
        setTie("~");
    }

    /**
     * Find a single letter and skip over the rest.
     * 
     * @param buffer the output buffer
     * @param s the string to scan
     * @param len the length of s
     * @param start the start index
     * 
     * @return the next index
     */
    private int app(StringBuilder buffer, String s, int len, int start) {

        int i = start;
        int level = 0;
        boolean bs = false;

        for (; i < len; i++) {
            char c = s.charAt(i);

            if (Character.isLetter(c)) {
                if (!bs) {
                    buffer.append(c);
                    return skip(s, len, level, i);
                }
            } else if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
            } else if (c == '\\') {
                bs = true;
                i++;
            }
        }

        level = 0;
        boolean letter = false;

        for (i = start; i < len; i++) {
            char c = s.charAt(i);
            buffer.append(c);

            if (Character.isLetter(c)) {
                letter = true;
            } else if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
                if (level <= 0 && letter) {
                    break;
                }
            }
        }

        return skip(s, len, level, i);
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
     * @throws ExBibException in case of an error
     */
    @Override
    protected boolean appendInitial(StringBuilder buffer, String s, String sep,
            Locator locator) throws ExBibException {

        int i = 0;
        int len = s.length();

        // skip spaces
        while (i < len && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        if (i >= len) {
            return true;
        }

        for (i = app(buffer, s, len, i) + 1; i < len; i =
                app(buffer, s, len, i) + 1) {
            buffer.append(sep);
        }

        return false;
    }

    /**
     * This is the post-processor for the name formatting engine.
     * 
     * @param s the string to process
     * 
     * @return the processed string
     */
    @Override
    protected TString process(String s) {

        int i = s.length() - 1;

        if (i < 0) {
            return new TString(s, null);
        }

        StringBuffer sb = new StringBuffer(s);

        if (sb.charAt(i) == '~') {
            sb.setCharAt(i, ' ');
        }

        // boolean space = false; // indicator that the previous char was a
        // space
        // for (i = 0; i < sb.length(); i++) {
        // switch (sb.charAt(i)) {
        // case '\t':
        // sb.setCharAt(i, ' ');
        // // continue as if a space has been seen. NO BREAK!
        // case ' ':
        // if (space) {
        // sb.deleteCharAt(i--);
        // } else {
        // space = true;
        // }
        // break;
        // case '~':
        // if (space) {
        // sb.deleteCharAt(--i);
        // } else {
        // space = true;
        // }
        // break;
        // default:
        // space = false;
        // }
        // }
        // i = 0;
        // while ((i = sb.indexOf(" ", i)) >= 0) {
        // sb.deleteCharAt(i);
        // }
        // i = 0;
        // while ((i = sb.indexOf("~~", i)) >= 0) {
        // sb.deleteCharAt(i);
        // }
        // i = 0;
        // while ((i = sb.indexOf("~ ", i)) >= 0) {
        // sb.deleteCharAt(i);
        // }
        // i = 0;
        // while ((i = sb.indexOf(" ~", i)) >= 0) {
        // sb.deleteCharAt(i + 1);
        // }
        return new TString(sb.toString(), null);
    }

    /**
     * Skip to group level 0 and ignore following spaces.
     * 
     * @param s the string to scan
     * @param len the length of the string
     * @param startLevel the initial brace level
     * @param start the start index
     * 
     * @return the next suitable index
     */
    private int skip(String s, int len, int startLevel, int start) {

        int i = start;
        int level = startLevel;
        for (; i < len; i++) {
            char c = s.charAt(i);
            if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
            } else if (c == '\\') {
                i++;
            } else if (level == 0 && (Character.isWhitespace(c) || c == '-')) {
                break;
            }
        }
        while (i < len && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        return i;
    }
}
