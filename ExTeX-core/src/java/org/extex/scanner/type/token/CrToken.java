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

package org.extex.scanner.type.token;

import org.extex.scanner.type.Catcode;
import org.extex.type.UnicodeChar;

/**
 * This class represents a carriage return token.
 * <p>
 * This class has a protected constructor only. Use the factory
 * {@link org.extex.scanner.type.token.TokenFactory TokenFactory}
 * to get an instance of this class.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4738 $
 */
public class CrToken extends AbstractToken implements Token {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param value the string value
     */
    protected CrToken(final UnicodeChar value) {

        super(value);
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
     * @see org.extex.scanner.type.token.AbstractToken#equals(
     *      org.extex.scanner.type.Catcode,
     *      char)
     */
    public boolean equals(final Catcode cc, final char c) {

        return cc == getCatcode();
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
     * @see org.extex.scanner.type.token.AbstractToken#equals(
     *      org.extex.scanner.type.Catcode, java.lang.String)
     */
    public boolean equals(final Catcode cc, final String s) {

        return cc == getCatcode();
    }

    /**
     * Compare the current token with a character value. They are the same if
     * the values are the same.
     *
     * @param c the value
     *
     * @return <code>true</code> iff the tokens are equal
     *
     * @see org.extex.scanner.type.token.AbstractToken#equals(char)
     */
    public boolean equals(final char c) {

        return false;
    }

    /**
     * Getter for the catcode.
     *
     * @return the catcode
     *
     * @see org.extex.scanner.type.token.Token#getCatcode()
     */
    public Catcode getCatcode() {

        return Catcode.CR;
    }

    /**
     * Get the string representation of this object for debugging purposes.
     *
     * @return the string representation
     *
     * @see "<logo>TeX</logo> &ndash; The Program [298]"
     */
    public String toString() {

        return getLocalizer().format("CrToken.Text", super.toString());
    }

    /**
     * Print the token into a StringBuffer.
     *
     * @param sb the target string buffer
     *
     * @see org.extex.scanner.type.token.Token#toString(java.lang.StringBuffer)
     */
    public void toString(final StringBuffer sb) {

        sb.append(getLocalizer().format("CrToken.Text", super.toString()));
    }

    /**
     * This method returns the textual representation for the Token.
     * This textual representation might not contain the full information but
     * can be used as an abbreviated form to be shown to the end user.
     * A representation with more complete information can be received with the
     * method {@link java.lang.Object#toString() toString()}.
     *
     * @return the textual representation
     *
     * @see org.extex.scanner.type.token.Token#toText()
     */
    public String toText() {

        return "[]";
    }

    /**
     * Invoke the appropriate visit method for the current class.
     * @param visitor the calling visitor
     * @param arg1 the first argument to pass
     *
     * @return the result object
     *
     * @throws Exception in case of an error
     *
     * @see org.extex.scanner.type.token.Token#visit(
     *      org.extex.scanner.type.token.TokenVisitor,
     *      java.lang.Object)
     */
    public Object visit(final TokenVisitor visitor, final Object arg1)
            throws Exception {

        return visitor.visitCr(this, arg1);
    }

}
