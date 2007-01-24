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

import org.extex.scanner.type.Catcode;
import org.extex.type.UnicodeChar;
import org.extex.util.framework.i18n.Localizer;
import org.extex.util.framework.i18n.LocalizerFactory;

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
    private UnicodeChar character = null;

    /**
     * Creates a new object for a Unicode character.
     *
     * @param uc the value of the token
     */
    protected AbstractToken(final UnicodeChar uc) {

        super();
        this.character = uc;
    }

    /**
     * @see org.extex.scanner.type.token.Token#equals(
     *      org.extex.scanner.type.Catcode,
     *      char)
     */
    public boolean equals(final Catcode cc, final char c) {

        return getCatcode() == cc && equals(c);
    }

    /**
     * @see org.extex.scanner.type.token.Token#equals(
     *      org.extex.scanner.type.Catcode,
     *      java.lang.String)
     */
    public boolean equals(final Catcode cc, final String s) {

        return getCatcode() == cc && s.length() == 1
                && character.getCodePoint() == s.charAt(0);
    }

    /**
     * @see org.extex.scanner.type.token.Token#equals(char)
     */
    public boolean equals(final char c) {

        UnicodeChar uc = getChar();

        return (uc != null && uc.getCodePoint() == c);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object t) {

        return this == t
                || (t instanceof Token
                        && getCatcode() == ((Token) t).getCatcode() && getChar()
                    .equals(((Token) t).getChar()));
    }

    /**
     * This is the getter for the catcode of this token.
     *
     * @see org.extex.scanner.type.token.Token#getCatcode()
     */
    public abstract Catcode getCatcode();

    /**
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
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        return getCatcode().hashCode() + HASH_FACTOR * character.hashCode();
    }

    /**
     * @see org.extex.scanner.type.token.Token#isa(
     *      org.extex.scanner.type.Catcode)
     */
    public boolean isa(final Catcode cc) {

        return getCatcode() == cc;
    }

    /**
     * Return the printable representation of this object.
     *
     * @return the printable representation
     */
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
     * @see org.extex.scanner.type.token.Token#toText(
     *      org.extex.type.UnicodeChar)
     */
    public String toText(final UnicodeChar esc) {

        return character.toString();
    }

}
