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

package org.extex.unit.tex.register.toks;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.register.toks.AbstractToks;
import org.extex.util.exception.GeneralException;

/**
 * This class provides an implementation for the primitive <code>\toks</code>.
 * It sets the named tokens register to the value given, and as a side effect
 * all prefixes are zeroed.
 *
 * <doc name="toks">
 * <h3>The Primitive <tt>\toks</tt></h3>
 * <p>
 *  The primitive <tt>\toks</tt> provides access to the named tokens register.
 *  token sequences can be stored in them for later use. This means that the
 *  tokens do not have to be parsed again. Even a change in the catcode
 *  settings does not alter the tokens build once.
 * </p>
 * <p>
 *  The primitive <tt>\toks</tt> also respects the count register
 *  <tt>\globaldefs</tt> to enable general global assignment.
 * </p>
 * <p>
 *  Since the primitive is classified as assignment the value of
 *  <tt>\afterassignment</tt> is applied.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;toks&rang;
 *      &rarr; <tt>\toks</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context,TokenSource,Typesetter,String)
 *        &lang;register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *        &lang;tokens&rang;}  </pre>
 * <p>
 *  In <logo>TeX</logo> the register name is a number in the range 0 to 255.
 *  Extensions to this are defined in <logo>eTeX</logo> and <logo>Omega</logo>
 *  where the limitation of the range is raised. In <logo>ExTeX</logo> this
 *  limit can be configured. In addition tokens can be used to address named
 *  token registers.
 * </p>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \toks2={UTF-8}  </pre>
 *  <pre class="TeXSample">
 *    \toks42={UTF-8}  </pre>
 *  <pre class="TeXSample">
 *    \toks42=\toks0  </pre>
 *  <pre class="TeXSample">
 *    \toks{abc}={Hello world}  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 * @version $Revision:4431 $
 */
public class ToksPrimitive extends AbstractToks
        implements
            TokensConvertible,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public ToksPrimitive(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        source.getOptionalEquals(context);
        Tokens toks = source.getTokens(context, source, typesetter);
        context.setToks(key, toks, prefix.clearGlobal());
    }

    /**
     * @see org.extex.interpreter.type.tokens.TokensConvertible#convertTokens(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens convertTokens(final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        return context.getToks(key);
    }

    /**
     * Scan the tokens between <code>{</code> and <code>}</code> and store
     * them in the named tokens register.
     *
     * @param prefix the prefix flags
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     * @param key the key
     *
     * @throws GeneralException in case of an error
     */
    protected void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter,
            final String key) throws GeneralException {

        Tokens toks = source.getTokens(context, source, typesetter);
        context.setToks(key, toks, prefix.clearGlobal());
    }

    /**
     * Return the register value as <code>Tokens</code> for <code>\the</code>.
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return context.getToks(getKey(context, source, typesetter));
    }

}
