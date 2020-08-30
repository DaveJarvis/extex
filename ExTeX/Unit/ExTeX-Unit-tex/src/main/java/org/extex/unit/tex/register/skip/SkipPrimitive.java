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

package org.extex.unit.tex.register.skip;

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.glue.Glue;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.GlueConvertible;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \skip}.
 * It sets the named skip register to the value given, and as a side effect all
 * prefixes are zeroed.
 * 
 * <p>The Primitive {@code \skip}</p>
 * <p>
 * The primitive {@code \skip} provides access to a skip register. In a skip
 * register some glue value can be stored. A glue value consists of a natural
 * length and optionally some stretchability and shrinkability components.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;skip&rang;
 *        &rarr; &lang;optional prefix&rang; {@code \skip} {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context,TokenSource,Typesetter,CodeToken)
 *        &lang;register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.parser.GlueParser#parseGlue(
 *        org.extex.interpreter.context.Context,
 *        org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
 *        &lang;glue&rang;}
 *
 *   &lang;optional prefix&rang;
 *     &rarr;
 *      |  {@code \global} &lang;optional prefix&rang;  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \skip 1em plus 1pt minus 1pt  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SkipPrimitive extends AbstractSkip
        implements
            Advanceable,
            Multiplyable,
            Divideable,
            GlueConvertible,
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
    public SkipPrimitive(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        Glue glue = source.parseGlue(context, source, typesetter);
        glue.add(context.getGlue(key));
        context.setGlue(key, glue, prefix.clearGlobal());
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getOptionalEquals(context);
        Glue glue = source.parseGlue(context, source, typesetter);
        context.setGlue(key, glue, prefix.clearGlobal());
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Glue convertGlue(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        return context.getGlue(key);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        long value = source.parseInteger(context, source, null);

        if (value == 0) {
            throw new ArithmeticOverflowException(toText(context));
        }

        Glue g = new Glue(context.getGlue(key));
        g.multiplyAll(1, value);
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        long value = source.parseInteger(context, source, null);

        Glue g = new Glue(context.getGlue(key));
        g.multiplyAll(value, 1);
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        String key = getKey(context, source, typesetter);
        try {
            return context.getTokenFactory().toTokens(
                context.getGlue(key).toString());
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        }
    }

}
