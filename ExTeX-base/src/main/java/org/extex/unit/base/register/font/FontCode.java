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

package org.extex.unit.base.register.font;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ComparableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.font.FontConvertible;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for a font primitive.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4431 $
 */
public class FontCode extends AbstractCode
        implements
            FontConvertible,
            Theable,
            ComparableCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>font</tt> contains the font enclosed in this code.
     */
    private Font font;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     * @param fnt the font for this primitive
     */
    public FontCode(String name, Font fnt) {

        super(name);
        this.font = fnt;
    }

    /**
     * Compare the code with some other code.
     * 
     * @param token the token to compare to
     * @param context the interpreter context
     * 
     * @return <code>true</code> iff the code is equivalent according to the
     *         semantics of <code>\ifx</code>
     * 
     * @see org.extex.interpreter.type.ComparableCode#compare(
     *      org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.context.Context)
     */
    public boolean compare(Token token, Context context)
            throws HelpingException {

        if (!(token instanceof CodeToken)) {
            return false;
        }

        Code code = context.getCode((CodeToken) token);

        if (!(code instanceof FontCode)) {
            return false;
        }
        return font == ((FontCode) code).font;
    }

    /**
     * Convert some primitive value into a font.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the converted value
     * 
     * @see org.extex.interpreter.type.font.FontConvertible#convertFont(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Font convertFont(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return font;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        context.set(font, prefix.clearGlobal());
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     * 
     * @return the description of the primitive as list of Tokens
     * @throws CatcodeException in case of an error in token creation
     * @throws ConfigurationException in case of an configuration error
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException, TypesetterException {

        return context.getTokenFactory().toTokens(font.getFontName());
    }

}
