/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.bst.code.impl;

import java.util.HashMap;
import java.util.Map;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * BibT<sub>E</sub>X built-in function <code>width$</code>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top (string) literal and pushes the integer that represents its
 * width in some relative units (currently, hundredths of a point, as specified
 * by the June 1987 version of the <i>cmr10</i> font; the only white-space
 * character with nonzero width is the space). This function takes the literal
 * literally; that is, it assumes each character in the string is to be printed
 * as is, regardless of whether the character has a special meaning to T<sub>E</sub>X,
 * except that ``special characters'' (even without their right braces) are
 * handled specially. This is meant to be used for comparing widths of label
 * strings. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X web documentation:</dt>
 * <dd> ... </dd>
 * </dl>
 * 
 * 
 * The sizes of the characters are taken from a SPECIAL internal list of width.
 * The following control sequences are known: \ss, \ae, \AE, \oe, \OE. They have
 * width of their own. All other control sequences have the width of the first
 * character after the initial backslash.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class Width extends AbstractCode {

    /**
     * The constant <tt>WIDTH</tt> contains the static array of character
     * widths taken from cmr10.
     */
    private static final int[] WIDTH = {0, // 0
            0, // 001
            0, // 002
            0, // 003
            0, // 004
            0, // 005
            0, // 006
            0, // 007
            0, // 010
            0, // 011
            0, // 012
            0, // 013
            0, // 014
            0, // 015
            0, // 016
            0, // 017
            0, // 020
            0, // 021
            0, // 022
            0, // 023
            0, // 024
            0, // 025
            0, // 026
            0, // 027
            0, // 030
            0, // 031
            0, // 032
            0, // 033
            0, // 034
            0, // 035
            0, // 036
            0, // 037
            278, // 040
            278, // 041
            500, // 042
            833, // 043
            500, // 044
            833, // 045
            778, // 046
            278, // 047
            389, // 050
            389, // 051
            500, // 052
            778, // 053
            278, // 054
            333, // 055
            278, // 056
            500, // 057
            500, // 060
            500, // 061
            500, // 062
            500, // 063
            500, // 064
            500, // 065
            500, // 066
            500, // 067
            500, // 070
            500, // 071
            278, // 072
            278, // 073
            278, // 074
            778, // 075
            472, // 076
            472, // 077
            778, // 100
            750, // 101
            708, // 102
            722, // 103
            764, // 104
            681, // 105
            653, // 106
            785, // 107
            750, // 110
            361, // 111
            514, // 112
            778, // 113
            625, // 114
            917, // 115
            750, // 116
            778, // 117
            681, // 120
            778, // 121
            736, // 122
            556, // 123
            722, // 124
            750, // 125
            750, // 126
            1028, // 127
            750, // 130
            750, // 131
            611, // 132
            278, // 133
            500, // 134
            278, // 135
            500, // 136
            278, // 137
            278, // 140
            500, // 141
            556, // 142
            444, // 143
            556, // 144
            444, // 145
            306, // 146
            500, // 147
            556, // 150
            278, // 151
            306, // 152
            528, // 153
            278, // 154
            833, // 155
            556, // 156
            500, // 157
            556, // 160
            528, // 161
            392, // 162
            394, // 163
            389, // 164
            556, // 165
            528, // 166
            722, // 167
            528, // 170
            528, // 171
            444, // 172
            500, // 173
            1000, // 174
            500, // 175
            500 // 176
            };

    /**
     * The field <tt>SPECIAL</tt> contains the mapping of the special T<sub>E</sub>X
     * sequences with positive width.
     */
    private static Map<String, Integer> SPECIAL = newSpecials();

    /** Indicate that debugging is activated */
    private static final boolean debug = false;

    /**
     * Return the width map for the control sequences.
     * 
     * @return the width map
     */
    private static Map<String, Integer> newSpecials() {

        // TODO: move to config
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("ae", new Integer(944));
        map.put("AE", new Integer(1931));
        map.put("aa", new Integer(WIDTH['a']));
        map.put("AA", new Integer(WIDTH['A']));
        map.put("oe", new Integer(944));
        map.put("OE", new Integer(1959));
        map.put("ss", new Integer(1288));
        map.put("l", new Integer(WIDTH['l']));
        map.put("L", new Integer(WIDTH['L']));
        map.put("o", new Integer(WIDTH['o']));
        map.put("O", new Integer(WIDTH['O']));
        map.put("i", new Integer(WIDTH['i']));
        map.put("j", new Integer(WIDTH['j']));

        return map;
    }

    /**
     * Create a new object.
     */
    public Width() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Width(String name) {

        super();
        setName(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
     *      org.extex.exbib.core.bst.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        String s = processor.popString(locator).getValue();
        int w = 0;
        int length = s.length();
        int level = 0;
        char c;

        if (debug) {
            System.err.println();
            System.err.println(s);
        }

        for (int ptr = 0; ptr < length; ptr++) {
            c = s.charAt(ptr);
            if (debug) {
                System.err.print(c);
            }

            if (c == '{') {
                if (++level == 1 && ptr + 1 < length) {
                    if (debug) {
                        System.err.print(" [" + level + "]");
                    }

                    if (s.charAt(ptr + 1) == '\\') {
                        if (debug) {
                            System.err.print(" \\\\");
                        }

                        StringBuffer control = new StringBuffer();
                        ptr += 2;

                        if (ptr >= length) {
                            //
                        } else if (Character.isLetter(s.charAt(ptr))) {
                            while (ptr < length
                                    && Character.isLetter(s.charAt(ptr))) {
                                control.append(s.charAt(ptr));
                                ptr++;
                            }
                        } else {
                            ptr++;
                        }

                        while (ptr < length
                                && Character.isWhitespace(s.charAt(ptr))) {
                            ptr++;
                        }

                        ptr--;

                        Integer wd = (SPECIAL.get(control.toString()));

                        if (wd != null) {
                            w += wd.intValue();
                        }
                    } else {
                        w += WIDTH['{'];
                    }
                } else if (level == 1) {
                    w += WIDTH[c];
                }
            } else if (c == '}') {
                if (level-- == 1) {
                    w += WIDTH[c];
                }
            } else {
                w += (c > '\176' ? 0 : WIDTH[c]);
            }

            if (debug) {
                System.err.println("\t" + w);
            }
        }

        processor.push(new TInteger(w));
    }

}