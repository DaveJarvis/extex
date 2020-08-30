/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function <code>change.case</code>
 * <p>
 * This function performs case conversion. It takes two arguments from the
 * stack. The first argument is the format and the second argument is the string
 * to apply it to. The result is pushed back to the stack.
 * </p>
 * <p>
 * The format controls the operation to be performed. It is a single letter
 * string. The following descriptions show the possibilities and functions
 * associated with the different format strings.
 * </p>
 * <ul>
 * <li>If the format is <tt>"l"</tt> or <tt>"L"</tt> then the string is
 * converted to lower case. This means that each letter is translated to its
 * lowercase counterpart &ndash; even the letters in TeX macros.</li>
 * <li>If the format is <tt>"u"</tt> or <tt>"U"</tt> then the string is
 * converted to upper case. This means that each letter is translated to its
 * lowercase counterpart &ndash; even the letters in TeX macros.</li>
 * <li>If the format is <tt>"t"</tt> or <tt>"T"</tt> then the string is
 * converted to title case. This means that the first `letter' is translated to
 * upper case and the other letters are left unchanged.
 * <p>
 * When a control sequence occurs then it is translated completely. This mean
 * that all letters of the control sequence are translated. For instance
 * <tt>\AA</tt> is translates to <tt>\aa</tt> at the beginning.
 * </p>
 * </li>
 * <li>If the format is not one of the legal values then a message is written to
 * the log stream and the input string pushed to the stack as the result.</li>
 * </ul>
 * <p>
 * If the stack does not contain enough elements or the types do not match then
 * an error is raised.
 * </p>
 * <img src="doc-files/change.case.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *   edition "l" change.case$
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X documentation:</dt>
 * <dd>Pops the top two (string) literals; it changes the case of the second
 * according to the specifications of the first, as follows. (Note: The word
 * `letters' in the next sentence refers only to those at brace-level 0, the
 * top-most brace level; no other characters are changed, except perhaps for
 * `special characters'', described in Section 4.) <br>
 * If the first literal is the string `<code>t</code>', it converts to lower
 * case all letters except the very first character in the string, which it
 * leaves alone, and except the first character following any colon and then
 * nonnull white space, which it also leaves alone; if it's the string `
 * <code>l</code>', it converts all letters to lower case; and if it's the
 * string `<code>u</code>', it converts all letters to upper case. It then
 * pushes this resulting string. If either type is incorrect, it complains and
 * pushes the null string; however, if both types are correct but the
 * specification string (i.e., the first string) isn't one of the legal ones, it
 * merely pushes the second back onto the stack, after complaining. (Another
 * note: It ignores case differences in the specification string; for example,
 * the strings <code>t</code> and <code>T</code> are equivalent for the purposes
 * of this built-in function.)</dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X web documentation:</dt>
 * <dd>
 * The <code>built_in</code> function <code>change.case$</code> pops the top two
 * (string) literals; it changes the case of the second according to the
 * specifications of the first, as follows. (Note: The word `letters' in the
 * next sentence refers only to those at brace-level 0, the top-most brace
 * level; no other characters are changed, except perhaps for special
 * characters, described shortly.) If the first literal is the string
 * <code>t</code>, it converts to lower case all letters except the very first
 * character in the string, which it leaves alone, and except the first
 * character following any <code>colon</code> and then nonnull
 * <code>white_space</code>, which it also leaves alone; if it's the string
 * <code>l</code>, it converts all letters to lower case; if it's the string
 * <code>u</code>, it converts all letters to upper case; and if it's anything
 * else, it complains and does no conversion. It then pushes this resulting
 * string. If either type is incorrect, it complains and pushes the null string;
 * however, if both types are correct but the specification string (i.e., the
 * first string) isn't one of the legal ones, it merely pushes the second back
 * onto the stack, after complaining. (Another note: It ignores case differences
 * in the specification string; for example, the strings <code>t</code> and
 * <code>T</code> are equivalent for the purposes of this <code>built_in</code>
 * function.)</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ChangeCase extends AbstractCode {

    /**
     * Perform the case conversion like <tt>change.case$</tt>.
     * 
     * @param fmt the format; i.e. one of the following:
     *        <ul>
     *        <li>"u" or "u" for upper case</li>
     *        <li>"l" or "L" for lower case</li>
     *        <li>"t" or "T" for title case</li>
     *        </ul>
     * @param input the initial string
     * 
     * @return the changed string
     * 
     * @throws IllegalArgumentException in case of illegal format
     */
    public static String changeCase(String fmt, String input)
            throws IllegalArgumentException {

        StringBuilder sb = new StringBuilder(input);
        int level = 0;

        if (fmt == null || fmt.length() != 1) {
            throw new IllegalArgumentException("change.case$");
        }

        switch (fmt.charAt(0)) {
            case 't':
            case 'T':
                boolean sc = true;

                for (int i = 0; i < sb.length(); i++) {
                    char c = sb.charAt(i);

                    if (c == '{') {
                        level++;
                        sc = false;
                    } else if (c == '}') {
                        level--;
                    } else if (level > 0) {

                    } else if (c == ':') {
                        if (i == sb.length()) {
                            break;
                        }

                        c = sb.charAt(++i);

                        if (Character.isWhitespace(c)) {
                            i++;
                        }
                    } else if (sc && Character.isLetter(c)) {
                        sc = false;
                    } else {
                        char lc = Character.toLowerCase(c);

                        if (lc != c) {
                            sb.setCharAt(i, lc);
                        }
                    }
                }
                break;
            case 'l':
            case 'L':
                for (int i = 0; i < sb.length(); i++) {
                    char c = sb.charAt(i);

                    if (c == '{') {
                        level++;
                    } else if (c == '}') {
                        level--;
                    } else if (level < 1) {
                        char lc = Character.toLowerCase(c);

                        if (lc != c) {
                            sb.setCharAt(i, lc);
                        }
                    }
                }
                break;
            case 'u':
            case 'U':
                for (int i = 0; i < sb.length(); i++) {
                    char c = sb.charAt(i);

                    if (c == '{') {
                        level++;
                    } else if (c == '}') {
                        level--;
                    } else if (level == 0) {
                        char lc = Character.toUpperCase(c);

                        if (lc != c) {
                            sb.setCharAt(i, lc);
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException();
        }

        return sb.toString();
    }

    /**
     * Create a new object.
     */
    public ChangeCase() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public ChangeCase(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        String fmt = processor.popString(locator).getValue();
        TString tvalue = processor.popString(locator);
        String value = tvalue.getValue();
        String result;
        try {
            result = changeCase(fmt, value);
        } catch (IllegalArgumentException e) {
            Localizer localizer = LocalizerFactory.getLocalizer(getClass());
            throw new ExBibIllegalValueException(localizer.format(
                "invalid.spec", fmt), locator);
        }
        processor.push(!value.equals(result)
                ? new TString(result, locator)
                : tvalue);
    }

}
