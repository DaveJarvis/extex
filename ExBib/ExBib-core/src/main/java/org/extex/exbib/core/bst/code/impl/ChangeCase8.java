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

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.csf.CsfSorter;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function <code>change.case</code>
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
public class ChangeCase8 extends ChangeCase {

    /**
     * Create a new object.
     */
    public ChangeCase8() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public ChangeCase8(String name) {

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

        Sorter so = processor.getDB().getSorter();
        if (!(so instanceof CsfSorter)) {
            super.execute(processor, entry, locator);
            return;
        }
        CsfSorter sorter = (CsfSorter) so;

        String fmt = processor.popString(locator).getValue();
        TString ts = processor.popString(locator);
        String s = ts.getValue();
        StringBuilder sb = new StringBuilder(s);
        boolean modified = false;
        int level = 0;

        switch (fmt.length() == 1 ? fmt.charAt(0) : '\0') {
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
                        //
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
                        char lc = sorter.toLowerCase(c);

                        if (lc != c) {
                            sb.setCharAt(i, lc);
                            modified = true;
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
                        char lc = sorter.toLowerCase(c);

                        if (lc != c) {
                            sb.setCharAt(i, lc);
                            modified = true;
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
                        char lc = sorter.toUpperCase(c);

                        if (lc != c) {
                            sb.setCharAt(i, lc);
                            modified = true;
                        }
                    }
                }
                break;
            default:
                Localizer localizer = LocalizerFactory.getLocalizer(getClass());
                throw new ExBibIllegalValueException(localizer.format(
                    "invalid.spec", fmt), locator);
        }

        processor.push(modified ? new TString(sb.toString(), locator) : ts);
    }
}
