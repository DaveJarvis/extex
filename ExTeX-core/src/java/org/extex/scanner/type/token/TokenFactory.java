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
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This is the interface for the token factory.
 * The token factory is the only instance to deliver new tokens.
 * It is up to the implementation of the factory to create new tokens or to
 * cache some of them and deliver the same token several times.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4738 $
 */
public interface TokenFactory {

    /**
     * Get an instance of a token with a given Catcode and value.
     *
     * @param code the catcode
     * @param esc the Unicode character value of the escape character
     * @param value the value
     * @param namespace the name space for the token. This is relevant for
     *  ACTIVE and ESCAPE category codes only.
     *
     * @return the appropriate token
     *
     * @throws CatcodeException in case of an error
     */
    Token createToken(Catcode code, UnicodeChar esc, String value,
            String namespace) throws CatcodeException;

    /**
     * Create a new {@link org.extex.scanner.type.token.Token Token} of the
     * appropriate kind. Tokens are immutable (no setters) thus the factory
     * pattern can be applied.
     *
     * @param code the category code
     * @param c the character value
     * @param namespace the name space to use
     *
     * @return the new token
     *
     * @throws CatcodeException in case of an error
     */
    Token createToken(Catcode code, int c, String namespace)
            throws CatcodeException;

    /**
     * Get an instance of a token with a given Catcode and Unicode character
     * value.
     *
     * @param code the catcode
     * @param c the Unicode character value
     * @param namespace the name space for the token. This is relevant for
     *   ACTIVE and ESCAPE category codes only.
     *
     * @return the appropriate token
     *
     * @throws CatcodeException in case of an error
     */
    Token createToken(Catcode code, UnicodeChar c, String namespace)
            throws CatcodeException;

    /**
     * Convert a character sequence to a list of tokens.
     * <p>
     * Each character of the string is converted into a <code>OtherToken</code>
     * and added to the internal list. An exception is made for spaces which
     * are converted into a <code>SpaceToken</code>.
     * </p>
     *
     * @param s the character sequence to translate to tokens
     *
     * @return the token list
     *
     * @throws CatcodeException in case of an error
     */
    Tokens toTokens(CharSequence s) throws CatcodeException;

    /**
     * Convert a long value into a list of tokens.
     * <p>
     * Each character is converted into a <code>OtherToken</code>
     * and added to the internal list.
     * </p>
     *
     * @param l the value to convert
     *
     * @return the token list
     *
     * @throws CatcodeException in case of an error
     */
    Tokens toTokens(long l) throws CatcodeException;

}
