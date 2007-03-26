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

package org.extex.core.glue;

import org.extex.core.exception.GeneralException;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This interface describes the features of a
 * {@link org.extex.core.glue.GlueComponent GlueComponent}
 * which do not modify the value.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4726 $
 */
public interface FixedGlueComponent {

    /**
     * Create a copy of this instance with the same order and value.
     *
     * @return a new copy of this instance
     */
    GlueComponent copy();

    /**
     * Compares the current instance with another GlueComponent for equality.
     *
     * @param d the other GlueComponent to compare to. If this parameter is
     * <code>null</code> then the comparison fails.
     *
     * @return <code>true</code> iff <i>|this| == |d| and ord(this) == ord(d)</i>
     */
    boolean eq(FixedGlueComponent d);

    /**
     * Compares the current instance with another GlueComponent.
     *
     * @param d the other GlueComponent to compare to
     *
     * @return <code>true</code> iff this is greater or equal to d
     */
    boolean ge(FixedGlueComponent d);

    /**
     * Getter for order.
     *
     * @return the order.
     */
    int getOrder();

    /**
     * Getter for the value in scaled points (sp).
     *
     * @return the value in internal units of scaled points (sp)
     */
    long getValue();

    /**
     * Compares the current instance with another GlueComponent.
     *
     * @param d the other GlueComponent to compare to
     *
     * @return <code>true</code> iff <i>ord(this) == ord(d) && |this| &gt; |d|</i>
     * or <i>ord(this) &gt; ord(d)</i>
     */
    boolean gt(FixedGlueComponent d);

    /**
     * Compares the current instance with another GlueComponent.
     *
     * @param d the other GlueComponent to compare to
     *
     * @return <code>true</code> iff this is less or equal to d
     */
    boolean le(FixedGlueComponent d);

    /**
     * Compares the current instance with another GlueComponent.
     *
     * @param d the other GlueComponent to compare to
     *
     * @return <code>true</code> iff <i>ord(this) == ord(d) && |this| &lt; |d|</i>
     * or <i>ord(this) &lt; ord(d)</i>
     */
    boolean lt(FixedGlueComponent d);

    /**
     * Compares the current instance with another GlueComponent for equality.
     *
     * @param d the other GlueComponent to compare to. If this parameter is
     * <code>null</code> then the comparison fails.
     *
     * @return <code>false</code> iff <i>|this| == |d| and ord(this) == ord(d)</i>
     */
    boolean ne(FixedGlueComponent d);

    /**
     * Determine the printable representation of the object.
     *
     * @return the printable representation
     *
     * @see #toString(StringBuffer)
     * @see #toToks(TokenFactory)
     */
    String toString();

    /**
     * Determine the printable representation of the object and append it to
     * the given StringBuffer.
     *
     * @param sb the output string buffer
     *
     * @see #toString()
     */
    void toString(StringBuffer sb);

    /**
     * Determine the printable representation of the object and return it as a
     * list of Tokens.
     * The value returned is exactly the string which would be produced by
     * <logo>TeX</logo> to print the Dimen. This means the result is expressed
     * in pt and properly rounded to be read back in again without loss of
     * information.
     *
     * @param factory the token factory to get the required tokens from
     *
     * @return the printable representation
     *
     * @throws GeneralException in case of an error
     *
     * @see "<logo>TeX</logo> &ndash; The Program [103]"
     * @see #toToks(org.extex.scanner.type.tokens.Tokens,
     *              org.extex.scanner.type.token.TokenFactory, char, char)
     * @see #toString()
     */
    Tokens toToks(TokenFactory factory) throws GeneralException;

    /**
     * Determine the printable representation of the object and return it as a
     * list of Tokens.
     * The value returned is exactly the string which would be produced by
     * <logo>TeX</logo> to print the Dimen. This means the result is expressed
     * in pt and properly rounded to be read back in again without loss of
     * information.
     *
     * @param toks the tokens to append to
     * @param factory the token factory to get the required tokens from
     * @param c1 the first character for the order 0 value
     * @param c2 the second character for the order 0 value
     *
     * @throws GeneralException in case of an error
     *
     * @see "<logo>TeX</logo> &ndash; The Program [103]"
     * @see #toToks(TokenFactory)
     * @see #toString()
     * @see #toString(StringBuffer)
     */
    void toToks(Tokens toks, TokenFactory factory, char c1, char c2)
            throws GeneralException;

}
