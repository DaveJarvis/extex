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

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;

/**
 * This class represents a letter token.
 * <p>
 * This class has a protected constructor only. Use the factory
 * {@link org.extex.scanner.type.token.TokenFactory TokenFactory}
 * to get an instance of this class.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4738 $
 */
public class LetterToken extends AbstractToken implements Token {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param uc the letter as a single character string
     */
    protected LetterToken(UnicodeChar uc) {

        super(uc);
    }

    /**
     * Getter for the catcode.
     *
     * @return the catcode
     *
     * @see org.extex.scanner.type.token.Token#getCatcode()
     */
    public Catcode getCatcode() {

        return Catcode.LETTER;
    }

    /**
     * Get the string representation of this object for debugging purposes.
     *
     * @return the string representation
     *
     * @see "<logo>TeX</logo> &ndash; The Program [298]"
     */
    public String toString() {

        return getLocalizer().format("LetterToken.Text", super.toString());
    }

    /**
     * Print the token into a StringBuffer.
     *
     * @param sb the target string buffer
     *
     * @see org.extex.scanner.type.token.Token#toString(java.lang.StringBuffer)
     */
    public void toString(StringBuffer sb) {

        sb.append(getLocalizer().format("LetterToken.Text", super.toString()));
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
    public Object visit(TokenVisitor visitor, Object arg1)
            throws Exception {

        return visitor.visitLetter(this, arg1);
    }

}