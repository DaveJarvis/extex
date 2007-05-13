/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.register.real;

import org.extex.base.parser.CountConvertible;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterExtensionException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.arithmetic.Advanceable;
import org.extex.interpreter.type.arithmetic.Divideable;
import org.extex.interpreter.type.arithmetic.Multiplyable;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

import de.dante.extex.interpreter.context.ContextExtension;
import de.dante.extex.interpreter.type.real.Real;
import de.dante.extex.interpreter.type.real.RealConvertible;

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
 * @version $Revision$
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
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public NamedReal(String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
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
            contextextex.setReal(key, value, prefix.isGlobal());
            prefix.clearGlobal();

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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Theable#the(org.extex.interpreter.context.Context,
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
     * @param typesetter TODO
     * 
     * @return the key
     *
     * @throws HelpingException in case of an error.
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return getName();
    }

    /**
     * @see org.extex.interpreter.type.arithmetic.Advanceable#advance(
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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.arithmetic.Multiplyable#multiply(org.extex.interpreter.Flags,
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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.arithmetic.Divideable#divide(org.extex.interpreter.Flags,
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
     * @see de.dante.extex.interpreter.type.real.RealConvertible#convertReal(
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
     * {@inheritDoc}
     * 
     * @see org.extex.base.parser.CountConvertible#convertCount(org.extex.interpreter.context.Context,
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
