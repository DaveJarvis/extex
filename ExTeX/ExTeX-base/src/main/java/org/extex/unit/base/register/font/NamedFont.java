/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.font.FontConvertible;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This class provides an implementation for a font stored under a name in the
 * context.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NamedFont extends AbstractAssignment
        implements
            FontConvertible,
            Theable {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     *
     * @param token the initial token for the primitive
     */
    public NamedFont(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getOptionalEquals(context);

        Font font = source.getFont(context, getToken());
        context.setFont(key, font, prefix.clearGlobal());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.font.FontConvertible#convertFont(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Font convertFont(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return context.getFont(getKey(context, source, typesetter));
    }

    /**
     * Return the key (the number) for the font register.
     *
     * @param context the interpreter context to use
     * @param source the source for the next tokens &ndash; if required
     * @param typesetter the typesetter
     *
     * @return the key for the font register
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (Namespace.SUPPORT_NAMESPACE_FONT) {
            return context.getNamespace() + "\b" + getName();
        }
        return getName();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source,
            Typesetter typesetter)
            throws CatcodeException, HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        Font font = context.getFont(key);
        return context.getTokenFactory().toTokens(
            context.esc(font.getFontName()));
    }

}
