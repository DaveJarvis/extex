/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.muskip;

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.muskip.Muskip;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.MuskipConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \muskip}.
 * It sets the named dimen register to the value given, and as a side effect all
 * prefixes are zeroed.
 * 
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * \muskip=345pt plus 123em
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MuskipParameter extends AbstractAssignment
        implements
            MuskipConvertible,
            Advanceable,
            Multiplyable,
            Divideable,
            Theable {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code key} contains the reference key.
     */
    private final String key;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public MuskipParameter(CodeToken token) {

        super(token);
        this.key = token.getName();
    }

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     * @param key the reference key
     */
    public MuskipParameter(CodeToken token, String key) {

        super(token);
        this.key = key;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String k = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        Muskip muskip = (Muskip) source.parse(Muskip.class, 
            context, source, typesetter);
        muskip.add(context.getMuskip(k));
        context.setMuskip(k, muskip, prefix.clearGlobal());
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String k = getKey(context, source, typesetter);
        source.getOptionalEquals(context);
        Muskip muskip = (Muskip) source.parse(Muskip.class, 
            context, source, typesetter);
        context.setMuskip(k, muskip, prefix.clearGlobal());
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Muskip convertMuskip(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String k = getKey(context, source, typesetter);
        return context.getMuskip(k);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String k = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        long value = source.parseInteger(context, source, null);

        if (value == 0) {
            throw new ArithmeticOverflowException(toText(context));
        }

        Muskip ms = new Muskip(context.getMuskip(k));
        ms.multiply(1, value);
        context.setMuskip(k, ms, prefix.clearGlobal());
    }

    /**
     * Return the key (the number) for the skip register.
     * 
     * @param context the interpreter context to use
     * @param source the source for the next tokens &ndash; if required
     * @param typesetter the typesetter
     * 
     * @return the key for the skip register
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (Namespace.SUPPORT_NAMESPACE_MUSKIP) {
            return context.getNamespace() + "\b" + key;
        }
        return key;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String k = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        long value = source.parseInteger(context, source, null);

        Muskip ms = new Muskip(context.getMuskip(k));
        ms.multiply(value, 1);
        context.setMuskip(k, ms, prefix.clearGlobal());
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        String k = getKey(context, source, typesetter);
        try {
            return context.getTokenFactory().toTokens(
                context.getMuskip(k).toString());
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        }
    }

}
