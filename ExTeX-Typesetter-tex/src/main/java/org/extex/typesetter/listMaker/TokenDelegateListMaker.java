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

package org.extex.typesetter.listMaker;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This interface describes the capabilities of a list maker.
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [211]"
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface TokenDelegateListMaker extends ListMaker {

    /**
     * Process a carriage return.
     * 
     * @param context the interpreter context
     * @param tc the typesetting context
     * @param uc the character
     * 
     * @throws TypesetterException in case of an error
     */
    void cr(Context context, TypesettingContext tc, UnicodeChar uc)
            throws TypesetterException;

    /**
     * Add a letter to the current list or treat it in some other appropriate
     * way.
     * 
     * @param tc the typesetting context
     * @param uc the character
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param locator the locator
     * 
     * @return <code>true</code> iff the character has been discarded because
     *         it is not defined in the current font.
     * 
     * @throws TypesetterException in case of an error
     */
    boolean letter(UnicodeChar uc, TypesettingContext tc, Context context,
            TokenSource source, Locator locator) throws TypesetterException;

    /**
     * Treat a math shift character. Usually this leads to entering or leaving
     * math mode &ndash; maybe after inspection of a following token.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param t the actual math shift character token
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     */
    void mathShift(Context context, TokenSource source, Token t)
            throws TypesetterException,
                ConfigurationException,
                HelpingException;

    /**
     * Treat a subscript mark. This might be meaningful in math mode only.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param token the actual sub mark token
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error
     */
    void subscriptMark(Context context, TokenSource source,
            Typesetter typesetter, Token token)
            throws TypesetterException,
                HelpingException;

    /**
     * Treat a superscript mark. This might be meaningful in math mode only.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param token the actual super mark token
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error
     */
    void superscriptMark(Context context, TokenSource source,
            Typesetter typesetter, Token token)
            throws TypesetterException,
                HelpingException;

    /**
     * Treat a alignment tab character.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param t the actual tab token
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     */
    void tab(Context context, TokenSource source, Token t)
            throws TypesetterException,
                ConfigurationException;

}
