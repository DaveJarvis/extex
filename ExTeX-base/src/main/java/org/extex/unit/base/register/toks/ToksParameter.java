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

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.InitializableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

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
    public static String getKey(String name, Context context) {

        if (Namespace.SUPPORT_NAMESPACE_TOKS) {
            return context.getNamespace() + "\b" + name;
        }
        return name;
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
    public ToksParameter(String name) {

        super(name);
        key = name;
    }

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     * @param key the key
     */
    public ToksParameter(String name, String key) {

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
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) {

        String k = config.getAttribute("key");
        if (k != null) {
            key = k;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.base.register.toks.AbstractToks#getKey(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws TypesetterException {

        if (Namespace.SUPPORT_NAMESPACE_TOKS) {
            return context.getNamespace() + "\b" + key;
        }
        return key;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.InitializableCode#init(
     *      org.extex.interpreter.context.Context, TokenSource, Typesetter)
     */
    public void init(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.tokens.TokensConvertible#convertTokens(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens convertTokens(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

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
    protected void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter, String key) throws GeneralException {

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
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        return context.getToks(getKey(context, source, typesetter));
    }

}
