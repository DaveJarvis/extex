/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.core;

import java.io.Serializable;

import com.ibm.icu.lang.UCharacter;

/**
 * This class represents a 32-bit Unicode character.
 *
 * Java 1.4 defines 16-bit characters only. Thus we are forced to roll our own
 * version. As soon as Java supports 32-bit Unicode characters this class is
 * obsolete and might be eliminated.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:5417 $
 */
public class UnicodeChar implements Serializable {

    /**
     * The constant <tt>MAX_VALUE</tt> contains the maximal admissible code
     * point.
     */
    public static final int MAX_VALUE = UCharacter.MAX_VALUE;

    /**
     * The constant <tt>MIN_VALUE</tt> contains the minimal admissible code
     * point.
     */
    public static final int MIN_VALUE = UCharacter.MIN_VALUE;

    /**
     * The field <tt>CACHE_SIZE</tt> contains the size of lower cache segment.
     */
    private static final int CACHE_SIZE = 256;

    /**
     * The field <tt>cache</tt> contains the cache for Unicode characters.
     */
    private static UnicodeChar[] cache = new UnicodeChar[CACHE_SIZE];

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The field <tt>BREAK_PERMITTED_HERE</tt> contains the optional break.
     */
    public static final UnicodeChar BREAK_PERMITTED_HERE =
            UnicodeChar.get(0x82);

    /**
     * The field <tt>NEXT_LINE</tt> contains the next line control character.
     */
    public static final UnicodeChar NEXT_LINE = UnicodeChar.get(0x85);

    /**
     * The field <tt>NO_BREAK_HERE</tt> contains the break inhibitor.
     */
    public static final UnicodeChar NO_BREAK_HERE = UnicodeChar.get(0x83);

    /**
     * The field <tt>NO_BREAK_SPACE</tt> contains the non-breakable space.
     */
    public static final UnicodeChar NO_BREAK_SPACE = UnicodeChar.get(0xa0);

    /**
     * The constant <tt>SHY</tt> contains the soft hyphenation character.
     */
    public static final UnicodeChar SHY = UnicodeChar.get(0xad);

    /**
     * The field <tt>SPACE</tt> contains the space control character.
     */
    public static final UnicodeChar SPACE = UnicodeChar.get(0x20);

    /**
     * Creates a new object from a integer code point.
     * This is a factory method for Unicode characters.
     * If the code point is out of range then <tt>null</tt> is returned.
     *
     * @param code the code point
     *
     * @return the Unicode character
     */
    public static UnicodeChar get(int code) {

        if (code < UCharacter.MIN_VALUE || code > UCharacter.MAX_VALUE) {
            return null;
        }
        if (0 <= code && code < CACHE_SIZE) {
            UnicodeChar uc = cache[code];
            if (uc == null) {
                uc = new UnicodeChar(code);
                cache[code] = uc;
            }
            return uc;
        }
        return new UnicodeChar(code);
    }

    /**
     * Creates a new object from a Unicode name.
     * Factory method for Unicode characters.
     *
     * @param unicodeName the long name of the character
     *
     * @return the Unicode character
     */
    public static UnicodeChar get(String unicodeName) {

        int c = UCharacter.getCharFromName(unicodeName);
        return (c >= 0 ? get(c) : null);
    }

    /**
     * The field <tt>code</tt> contains the code point of the Unicode character
     * (32 bit).
     */
    private int code;

    /**
     * Creates a new object from an integer code point.
     *
     * @param codePoint the 32-bit code point
     */
    protected UnicodeChar(int codePoint) {

        super();
        if (codePoint < UCharacter.MIN_VALUE
                || codePoint > UCharacter.MAX_VALUE) {
            throw new IllegalArgumentException("Codepoint out of bounds");
        }
        this.code = codePoint;
    }

    /**
     * Compares a <code>UnicodeChar</code> character with the value of this
     * object. They are considered equal if the are both UnicodeChars and have
     * the same code.
     * <p>
     * The general signature for comparison to an arbitrary object is required
     * for the implementation of {@link java.util.HashMap HashMap} and friends.
     * </p>
     *
     * @param unicodeChar the character to compare
     *
     * @return <code>true</code> if the characters are equal, otherwise
     *         <code>false</code>
     */
    public boolean equals(Object unicodeChar) {

        return ((unicodeChar instanceof UnicodeChar) && //
        this.code == ((UnicodeChar) unicodeChar).getCodePoint());
    }

    /**
     * Return the Unicode code point.
     *
     * @return the Unicode code point
     */
    public int getCodePoint() {

        return this.code;
    }

    /**
     * Returns the bi-direction property of the character.
     *
     * @return the bi-direction property
     */
    public int getDirection() {

        return UCharacter.getDirection(this.code);
    }

    /**
     * Returns the Unicode name of the code.
     *
     * @return Unicode name of the code
     */
    public String getUnicodeName() {

        return UCharacter.getName(this.code);
    }

    /**
     * Computes the hash code for the character. The hash code of equal objects
     * must be equal, but the hash code of different object need not to be
     * different. This is needed for the implementations of HashMap and friends.
     *
     * @return the hash code
     */
    public int hashCode() {

        return this.code;
    }

    /**
     * Test, if the code is a digit.
     *
     * @return <code>true</code>, if the code is a digit,
     *  otherwise <code>false</code>
     */
    public boolean isDigit() {

        return UCharacter.isDigit(this.code);
    }

    /**
     * Test, if the character is a letter.
     *
     * @return <code>true</code>, if the code is a letter,
     *  otherwise <code>false</code>
     */
    public boolean isLetter() {

        return UCharacter.isLetter(this.code);
    }

    /**
     * Test, if the code is printable.
     *
     * @return <code>true</code>, if the code is printable,
     *  otherwise <code>false</code>
     */
    public boolean isPrintable() {

        return UCharacter.isPrintable(this.code);
    }

    /**
     * Returns the lowercase character of this object.
     * <p>
     * (this method does not use the <logo>TeX</logo> lccode!)
     * </p>
     *
     * @return character in lowercase
     */
    public UnicodeChar lower() {

        int lc = UCharacter.toLowerCase(this.code);
        return (lc == code ? this : UnicodeChar.get(lc));
    }

    /**
     * Returns a String of this object.
     *
     * @return String representation of the stored value.
     */
    public String toString() {

        return UCharacter.toString(this.code);
    }

    /**
     * Returns the uppercase character of this object.
     * <p>
     * (this method does not use the <logo>TeX</logo> uccode!)
     * </p>
     *
     * @return character in uppercase
     */
    public UnicodeChar upper() {

        int lc = UCharacter.toUpperCase(this.code);
        return (lc == code ? this : UnicodeChar.get(lc));
    }

}
