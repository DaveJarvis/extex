/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.cff;

import java.util.HashMap;
import java.util.Map;

/**
 * Predefined Charset.
 * <p>
 * ISOAdobe 0 - 228
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public final class T2PredefinedCharsetISOAdobe {

    /**
     * Predefined Charset
     */
    public static final String[] DATA = {// ISOAdobe
            ".notdef", // 0
                    "space", // 1
                    "exclam", // 2
                    "quotedbl", // 3
                    "numbersign", // 4
                    "dollar", // 5
                    "percent", // 6
                    "ampersand", // 7
                    "quoteright", // 8
                    "parenleft", // 9
                    "parenright", // 10
                    "asterisk", // 11
                    "plus", // 12
                    "comma", // 13
                    "hyphen", // 14
                    "period", // 15
                    "slash", // 16
                    "zero", // 17
                    "one", // 18
                    "two", // 19
                    "three", // 20
                    "four", // 21
                    "five", // 22
                    "six", // 23
                    "seven", // 24
                    "eight", // 25
                    "nine", // 26
                    "colon", // 27
                    "semicolon", // 28
                    "less", // 29
                    "equal", // 30
                    "greater", // 31
                    "question", // 32
                    "at", // 33
                    "A", // 34
                    "B", // 35
                    "C", // 36
                    "D", // 37
                    "E", // 38
                    "F", // 39
                    "G", // 40
                    "H", // 41
                    "I", // 42
                    "J", // 43
                    "K", // 44
                    "L", // 45
                    "M", // 46
                    "N", // 47
                    "O", // 48
                    "P", // 49
                    "Q", // 50
                    "R", // 51
                    "S", // 52
                    "T", // 53
                    "U", // 54
                    "V", // 55
                    "W", // 56
                    "X", // 57
                    "Y", // 58
                    "Z", // 59
                    "bracketleft", // 60
                    "backslash", // 61
                    "bracketright", // 62
                    "asciicircum", // 63
                    "underscore", // 64
                    "quoteleft", // 65
                    "a", // 66
                    "b", // 67
                    "c", // 68
                    "d", // 69
                    "e", // 70
                    "f", // 71
                    "g", // 72
                    "h", // 73
                    "i", // 74
                    "j", // 75
                    "k", // 76
                    "l", // 77
                    "m", // 78
                    "n", // 79
                    "o", // 80
                    "p", // 81
                    "q", // 82
                    "r", // 83
                    "s", // 84
                    "t", // 85
                    "u", // 86
                    "v", // 87
                    "w", // 88
                    "x", // 89
                    "y", // 90
                    "z", // 91
                    "braceleft", // 92
                    "bar", // 93
                    "braceright", // 94
                    "asciitilde", // 95
                    "exclamdown", // 96
                    "cent", // 97
                    "sterling", // 98
                    "fraction", // 99
                    "yen", // 100
                    "florin", // 101
                    "section", // 102
                    "currency", // 103
                    "quotesingle", // 104
                    "quotedblleft", // 105
                    "guillemotleft", // 106
                    "guilsinglleft", // 107
                    "guilsinglright", // 108
                    "fi", // 109
                    "fl", // 110
                    "endash", // 111
                    "dagger", // 112
                    "daggerdbl", // 113
                    "periodcentered", // 114
                    "paragraph", // 115
                    "bullet", // 116
                    "quotesinglbase", // 117
                    "quotedblbase", // 118
                    "quotedblright", // 119
                    "guillemotright", // 120
                    "ellipsis", // 121
                    "perthousand", // 122
                    "questiondown", // 123
                    "grave", // 124
                    "acute", // 125
                    "circumflex", // 126
                    "tilde", // 127
                    "macron", // 128
                    "breve", // 129
                    "dotaccent", // 130
                    "dieresis", // 131
                    "ring", // 132
                    "cedilla", // 133
                    "hungarumlaut", // 134
                    "ogonek", // 135
                    "caron", // 136
                    "emdash", // 137
                    "AE", // 138
                    "ordfeminine", // 139
                    "Lslash", // 140
                    "Oslash", // 141
                    "OE", // 142
                    "ordmasculine", // 143
                    "ae", // 144
                    "dotlessi", // 145
                    "lslash", // 146
                    "oslash", // 147
                    "oe", // 148
                    "germandbls", // 149
                    "onesuperior", // 150
                    "logicalnot", // 151
                    "mu", // 152
                    "trademark", // 153
                    "Eth", // 154
                    "onehalf", // 155
                    "plusminus", // 156
                    "Thorn", // 157
                    "onequarter", // 158
                    "divide", // 159
                    "brokenbar", // 160
                    "degree", // 161
                    "thorn", // 162
                    "threequarters", // 163
                    "twosuperior", // 164
                    "registered", // 165
                    "minus", // 166
                    "eth", // 167
                    "multiply", // 168
                    "threesuperior", // 169
                    "copyright", // 170
                    "Aacute", // 171
                    "Acircumflex", // 172
                    "Adieresis", // 173
                    "Agrave", // 174
                    "Aring", // 175
                    "Atilde", // 176
                    "Ccedilla", // 177
                    "Eacute", // 178
                    "Ecircumflex", // 179
                    "Edieresis", // 180
                    "Egrave", // 181
                    "Iacute", // 182
                    "Icircumflex", // 183
                    "Idieresis", // 184
                    "Igrave", // 185
                    "Ntilde", // 186
                    "Oacute", // 187
                    "Ocircumflex", // 188
                    "Odieresis", // 189
                    "Ograve", // 190
                    "Otilde", // 191
                    "Scaron", // 192
                    "Uacute", // 193
                    "Ucircumflex", // 194
                    "Udieresis", // 195
                    "Ugrave", // 196
                    "Yacute", // 197
                    "Ydieresis", // 198
                    "Zcaron", // 199
                    "aacute", // 200
                    "acircumflex", // 201
                    "adieresis", // 202
                    "agrave", // 203
                    "aring", // 204
                    "atilde", // 205
                    "ccedilla", // 206
                    "eacute", // 207
                    "ecircumflex", // 208
                    "edieresis", // 209
                    "egrave", // 210
                    "iacute", // 211
                    "icircumflex", // 212
                    "idieresis", // 213
                    "igrave", // 214
                    "ntilde", // 215
                    "oacute", // 216
                    "ocircumflex", // 217
                    "odieresis", // 218
                    "ograve", // 219
                    "otilde", // 220
                    "scaron", // 221
                    "uacute", // 222
                    "ucircumflex", // 223
                    "udieresis", // 224
                    "ugrave", // 225
                    "yacute", // 226
                    "ydieresis", // 227
                    "zcaron" // 228
            };

    /**
     * The map for the names.
     */
    private static Map<String, Integer> names = null;

    /**
     * Returns the name or '.notdef' if number out of range.
     * 
     * @param sid the sid for the name
     * @return Returns the name or '.notdef' if number out of range.
     */
    public static String getName(int sid) {

        if (sid >= 0 && sid < DATA.length) {
            return DATA[sid];
        }
        return ".notdef";
    }

    /**
     * Returns the SID for a name or -1 if not found.
     * 
     * @param name the name
     * @return Returns the SID for a name or -1 if not found.
     */
    public static int getSID(String name) {

        if (names == null) {
            names = new HashMap<String, Integer>(DATA.length);
            for (int i = 0; i < DATA.length; i++) {
                String key = DATA[i];
                names.put(key, new Integer(i));
            }
        }
        Integer ii = names.get(name);
        if (ii != null) {
            return ii.intValue();
        }

        return -1;
    };

    /**
     * no instance
     */
    private T2PredefinedCharsetISOAdobe() {

        
    }

}
