/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
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

package org.extex.scanner.type.token;

import java.io.Serializable;

import org.extex.core.UnicodeChar;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.scanner.type.Catcode;

/**
 * This is the abstract base class for all Tokens.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4738 $
 */
public abstract class AbstractToken implements Token, Serializable {

    /**
     * The constant <tt>HASH_FACTOR</tt> contains the factor used to construct
     * the hash code.
     */
    private static final int HASH_FACTOR = 17;

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 24012007L;

    /**
     * The field <tt>uniCode</tt> contains the Unicode character assigned to
     * this token. Note that <code>null</code> is a legal value.
     */
    private UnicodeChar character;

    /**
     * Creates a new object for a Unicode character.
     *
     * @param uc the value of the token
     */
    protected AbstractToken(UnicodeChar uc) {

        super();
        this.character = uc;
    }

    /**
     * Compare the current token with a pair of catcode and character value.
     * This pair constitutes a virtual token. They are the same if the catcode
     * and the value are the same.
     *
     * @param cc the catcode
     * @param c the value
     *
     * @return <code>true</code> iff the tokens are equal
     *
     * @see org.extex.scanner.type.token.Token#eq(
     *      org.extex.scanner.type.Catcode,
     *      char)
     */
    public boolean eq(Catcode cc, char c) {

        return getCatcode() == cc && eq(c);
    }

    /**
     * Compare the current token with a pair of catcode and String value. This
     * pair constitutes a virtual token. They are the same if the catcode and
     * the value are the same.
     *
     * @param cc the catcode
     * @param s the value
     *
     * @return <code>true</code> iff the tokens are equal
     *
     * @see org.extex.scanner.type.token.Token#eq(
     *      org.extex.scanner.type.Catcode,
     *      java.lang.String)
     */
    public boolean eq(Catcode cc, String s) {

        return getCatcode() == cc && s.length() == 1
                && character.getCodePoint() == s.charAt(0);
    }

    /**
     * Compare the current token with a character value. They are the same if
     * the values are the same.
     *
     * @param c the value
     *
     * @return <code>true</code> iff the tokens are equal
     *
     * @see org.extex.scanner.type.token.Token#eq(char)
     */
    public boolean eq(char c) {

        UnicodeChar uc = getChar();

        return (uc != null && uc.getCodePoint() == c);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param   t the reference token with which to compare.
     * @return  <code>true</code> if this object is the same as the obj
     *          argument; <code>false</code> otherwise.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object t) {

        return this == t
                || (t instanceof Token
                        && getCatcode() == ((Token) t).getCatcode() && getChar()
                    .equals(((Token) t).getChar()));
    }

    /**
     * Getter for the catcode.
     *
     * @return the catcode
     *
     * @see org.extex.scanner.type.token.Token#getCatcode()
     */
    public abstract Catcode getCatcode();

    /**
     * Getter for the value.
     *
     * @return the value
     *
     * @see org.extex.scanner.type.token.Token#getChar()
     */
    public final UnicodeChar getChar() {

        return character;
    }

    /**
     * Getter for localizer.
     *
     * @return the localizer.
     */
    protected Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(Token.class);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return  a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return getCatcode().hashCode() + HASH_FACTOR * character.hashCode();
    }

    /**
     * Check if the current token has a specified catcode.
     *
     * @param cc the catcode to compare against
     *
     * @return <code>true</code> iff the catcodes coincide
     *
     * @see org.extex.scanner.type.token.Token#isa(
     *      org.extex.scanner.type.Catcode)
     */
    public boolean isa(Catcode cc) {

        return getCatcode() == cc;
    }

    /**
     * Return the printable representation of this object.
     *
     * @return the printable representation
     */
    @Override
    public String toString() {

        if (character == null) {
            return "";
        } else if (character.isPrintable()) {
            return character.toString();
        }
        return "^^" + Integer.toHexString(character.getCodePoint());
    }

    /**
     * Return the text representation of this object.
     *
     * @return the text representation
     */
    public String toText() {

        return character.toString();
    }

    /**
     * Return the printable representation of this token as it can be read back
     * in.
     *
     * @param esc the escape character
     *
     * @return the printable representation
     *
     * @see org.extex.scanner.type.token.Token#toText(
     *      org.extex.core.UnicodeChar)
     */
    public String toText(UnicodeChar esc) {

        return character.toString();
    }

}
