/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.interpreter.primitives.register.real;

import org.extex.baseext.exception.InterpreterExtensionException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.ContextExtension;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the real valued primitives. It sets
 * the named real register to the value given, and as a side effect all prefixes
 * are zeroed.
 * 
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * \pi=3.1415
 * </pre>
 * 
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
*/
public class NamedReal extends AbstractAssignment
        implements
            Theable,
            Advanceable,
            Multiplyable,
            Divideable,
            RealConvertible,
            CountConvertible {

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field {@code key} contains the reference key.
     */
    private String key;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     * @param key the reference key
     */
    public NamedReal(CodeToken token, String key) {

        super(token);
        this.key = key;
    }

    /**
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        if (context instanceof ContextExtension) {

            ContextExtension contextextex = (ContextExtension) context;

            String key = getKey(context, source, typesetter);
            source.getOptionalEquals(context);
            Real value = new Real(context, source, typesetter);
            contextextex.setReal(key, value, prefix.clearGlobal());

        } else {
            throw new InterpreterExtensionException();
        }
    }

    /**
     * set the value
     * 
     * @param context the interpreter context
     * @param value the new value as Real
     * 
     * @throws HelpingException if no extension is configured
     */
    public void set(Context context, Real value) throws HelpingException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            contextextex.setReal(getName(), value);
        } else {
            throw new InterpreterExtensionException();
        }
    }

    /**
     * Set the value
     * 
     * @param context the interpreter context
     * @param value the new value as String
     * 
     * @throws HelpingException if no extension is configured
     */
    public void set(Context context, String value) throws HelpingException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            contextextex.setReal(getName(), new Real(value));
        } else {
            throw new InterpreterExtensionException();
        }
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            String key = getKey(context, source, typesetter);
            String s = contextextex.getReal(key).toString();
            return context.getTokenFactory().toTokens(s);
        }
        throw new InterpreterExtensionException();
    }

    /**
     * Return the key (the name of the primitive) for the register.
     * 
     * @param context the context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @return the key
     * 
     * @throws HelpingException in case of an error.
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return key;
    }

    /**
     * @see org.extex.interpreter.type.code.Advanceable#advance(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        if (context instanceof ContextExtension) {

            ContextExtension contextextex = (ContextExtension) context;
            String key = getKey(context, source, typesetter);
            Real real = contextextex.getReal(key);
            source.getKeyword(context, "by");
            Real value = new Real(context, source, typesetter);
            real.add(value);
            if (prefix.isGlobal()) {
                contextextex.setReal(key, real, true);
            }
        } else {
            throw new InterpreterExtensionException();
        }
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;

            String key = getKey(context, source, typesetter);
            Real real = contextextex.getReal(key);
            source.getKeyword(context, "by");
            Real value = new Real(context, source, typesetter);
            real.multiply(value);
            if (prefix.isGlobal()) {
                contextextex.setReal(key, real, true);
            }
        } else {
            throw new InterpreterExtensionException();
        }
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            String key = getKey(context, source, typesetter);
            Real real = contextextex.getReal(key);
            source.getKeyword(context, "by");
            Real value = new Real(context, source, typesetter);
            real.divide(value);
            if (prefix.isGlobal()) {
                contextextex.setReal(key, real, true);
            }
        } else {
            throw new InterpreterExtensionException();
        }
    }

    /**
     * @see org.extex.interpreter.primitives.register.real.RealConvertible#convertReal(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Real convertReal(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            String key = getKey(context, source, typesetter);
            Real r = contextextex.getReal(key);
            return (r != null ? r : Real.ZERO);
        }
        throw new InterpreterExtensionException();
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            String key = getKey(context, source, typesetter);
            Real r = contextextex.getReal(key);
            return (r != null ? r.getLong() : 0);
        }
        throw new InterpreterExtensionException();
    }
}
