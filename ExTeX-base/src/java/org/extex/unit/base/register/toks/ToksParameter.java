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

package org.extex.unit.base.register.toks;

import org.extex.interpreter.Flags;
import org.extex.interpreter.Namespace;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.EofInToksException;
import org.extex.interpreter.type.InitializableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.util.exception.GeneralException;
import org.extex.util.framework.configuration.Configurable;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive <code>\toks</code>.
 * It sets the numbered toks register to the value given, and as a side effect
 * all prefixes are zeroed.
 *
 * Example:
 *
 * <pre>
 *     \toks12{123}
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 * @version $Revision:4431 $
 */
public class ToksParameter extends AbstractToks
        implements
            TokensConvertible,
            Theable,
            InitializableCode,
            Configurable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Return the key for a named toks register.
     *
     * @param name the name of the register
     * @param context the interpreter context to use
     *
     * @return the key for the toks register
     */
    public static String getKey(final String name, final Context context) {

        if (Namespace.SUPPORT_NAMESPACE_TOKS) {
            return context.getNamespace() + "\b" + name;
        } else {
            return name;
        }
    }

    /**
     * The field <tt>key</tt> contains the key.
     */
    private String key;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public ToksParameter(final String name) {

        super(name);
        key = name;
    }

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     * @param key the key
     */
    public ToksParameter(final String name, final String key) {

        super(name);
        this.key = key;
    }

    /**
     * Configure an object according to a given Configuration.
     *
     * @param config the configuration object to consider
     *
     * @throws ConfigurationException in case that something went wrong
     *
     * @see org.extex.util.framework.configuration.Configurable#configure(
     *      org.extex.util.framework.configuration.Configuration)
     */
    public void configure(final Configuration config) {

        String k = config.getAttribute("key");
        if (k != null) {
            key = k;
        }
    }

    /**
     * Return the key (the number) for the tokens register.
     *
     * @param source the source for the next tokens &ndash; if required
     * @param context the interpreter context to use
     * @param typesetter the typesetter
     *
     * @return the key for the tokens register
     *
     * @see org.extex.unit.base.register.toks.AbstractToks#getKey(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    protected String getKey(final Context context, final TokenSource source,
            final Typesetter typesetter) {

        if (Namespace.SUPPORT_NAMESPACE_TOKS) {
            return context.getNamespace() + "\b" + key;
        } else {
            return key;
        }
    }

    /**
     * Initialize the Code with some value coming from a String.
     *
     * @param context the interpreter context
     * @param source the source of information for the initialization
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.InitializableCode#init(
     *      org.extex.interpreter.context.Context, TokenSource, Typesetter)
     */
    public void init(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        if (source != null) {
            Tokens toks = new Tokens();
            for (Token t = source.getToken(context); t != null; t =
                    source.getToken(context)) {
                toks.add(t);
            }
            context.setToks(getKey(context, (TokenSource) null, typesetter),
                toks, true);
        }
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     *
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
        Tokens tokens;
        try {
            tokens = source.getTokens(context, source, typesetter);
        } catch (EofException e) {
            throw new EofInToksException(printableControlSequence(context));
        }
        context.setToks(key, tokens, prefix.clearGlobal());
    }

    /**
     * This method converts a register into tokens.
     * It might be necessary to read further tokens to determine which value to
     * use. For instance an additional register number might be required. In
     * this case the additional arguments Context and TokenSource can be used.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use for conversion
     *
     * @return the converted value
     *
     * @throws InterpreterException in case of an error
     *
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
