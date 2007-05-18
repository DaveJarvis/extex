/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.count;

import org.extex.base.type.arithmetic.Advanceable;
import org.extex.base.type.arithmetic.Divideable;
import org.extex.base.type.arithmetic.Multiplyable;
import org.extex.core.count.Count;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\count</code>.
 * It sets the named count register to the value given, and as a side effect all
 * prefixes are zeroed.
 * 
 * <doc name="count">
 * <h3>The Primitive <tt>\count</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;count&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\count</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context,TokenSource,Typesetter,String)
 *        &lang;register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}
 *
 *   &lang;optional prefix&rang;
 *     &rarr;
 *      |  <tt>\global</tt> &lang;optional prefix&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \count23=-456  </pre>
 * 
 * </doc>
 * 
 * 
 * @see org.extex.base.type.arithmetic.Advanceable
 * @see org.extex.base.type.arithmetic.Divideable
 * @see org.extex.base.type.arithmetic.Multiplyable
 * @see org.extex.interpreter.type.Theable
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4732 $
 */
public class CountPrimitive extends AbstractCount
        implements
            ExpandableCode,
            Advanceable,
            Multiplyable,
            Divideable,
            Theable,
            CountConvertible {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public CountPrimitive(String name) {

        super(name);
    }

    /**
     * This method is called when the macro <tt>\advance</tt> has been seen.
     * It performs the remaining tasks for the expansion.
     * 
     * @param prefix the prefix for the command
     * @param context the processor context
     * @param source the token source to parse
     * @param typesetter the typesetter
     * 
     * @see org.extex.base.type.arithmetic.Advanceable#advance(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");

        long value = source.parseInteger(context, source, typesetter);
        value += context.getCount(key).getValue();

        context.setCount(key, value, prefix.clearGlobal());
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

        long value = source.parseInteger(context, source, typesetter);
        context.setCount(key, value, prefix.clearGlobal());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        Count c = context.getCount(key);
        return (c != null ? c.getValue() : 0);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.type.arithmetic.Divideable#divide(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");

        long value = source.parseInteger(context, source, typesetter);

        if (value == 0) {
            throw new ArithmeticOverflowException(
                printableControlSequence(context));
        }

        value = context.getCount(key).getValue() / value;
        context.setCount(key, value, prefix.clearGlobal());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        try {
            source.push(context.getTokenFactory().toTokens(
                context.getCount(key).getValue()));
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.type.arithmetic.Multiplyable#multiply(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");

        long value = source.parseInteger(context, source, typesetter);
        value *= context.getCount(key).getValue();
        context.setCount(key, value, prefix.clearGlobal());
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
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        String key = getKey(context, source, typesetter);
        return context.getTokenFactory().toTokens( //
            context.getCount(key).getValue());
    }

}
