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

package org.extex.interpreter.primitives.register.pair;

import org.extex.baseext.exception.InterpreterExtensionException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.ContextExtension;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the pair valued primitives. It sets
 * the named pair register to the value given, and as a side effect all prefixes
 * are zeroed.
 * 
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * \xy=1.234 3.45
 * </pre>
 * 
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class NamedPair extends AbstractAssignment
        implements
            Theable,
            PairConvertible {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public NamedPair(String name) {

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
            Pair value = new Pair(context, source, typesetter);
            contextextex.setPair(key, value, prefix.isGlobal());
            prefix.clearGlobal();

        } else {
            throw new InterpreterExtensionException();
        }
    }

    /**
     * set the value
     * 
     * @param context the interpreter context
     * @param value the new value
     * 
     * @throws HelpingException if no extension is configured.
     */
    public void set(Context context, Pair value) throws HelpingException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            contextextex.setPair(getName(), value);
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
            contextextex.setPair(getName(), new Pair(value));
        } else {
            throw new InterpreterExtensionException();
        }
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
                HelpingException,
                TypesetterException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            String key = getKey(context, source, typesetter);
            String s = contextextex.getPair(key).toString();
            return context.getTokenFactory().toTokens(s);
        }
        throw new InterpreterExtensionException();
    }

    /**
     * Return the key (the name of the primitive) for the register.
     * 
     * @param context the context
     * @param source the source
     * @param typesetter the typesetter
     * @return the key
     * @throws HelpingException in case of an error.
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.primitives.register.pair.PairConvertible#convertPair(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Pair convertPair(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (context instanceof ContextExtension) {
            ContextExtension contextextex = (ContextExtension) context;
            String key = getKey(context, source, typesetter);
            Pair val = contextextex.getPair(key);
            return (val != null ? val : new Pair());
        }
        throw new InterpreterExtensionException();
    }
}
