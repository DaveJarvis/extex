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

package org.extex.unit.tex.register.count;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.ArithmeticOverflowException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.count.Count;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive <code>\prevgraf</code>.
 *
 * <doc name="prevgraf">
 * <h3>The Primitive <tt>\prevgraf</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 * <p>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;prevgraf&rang;
 *       &rarr; <tt>\prevgraf</tt>  </pre>
 * </p>
 * <p>
 *  Examples:
 *  <pre class="TeXSample">
 *    \prevgraf  </pre>
 * </p>
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Prevgraf extends CountPrimitive {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Prevgraf(final String name) {

        super(name);
    }

    /**
     * @see org.extex.unit.tex.register.count.AbstractCount#getKey(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    protected String getKey(final Context context, final TokenSource source,
            final Typesetter typesetter) {

        return getName();
    }

    /**
     * @see org.extex.interpreter.type.arithmetic.Advanceable#advance(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void advance(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");

        long value = Count.scanInteger(context, source, null)
                + context.getCount(key).getValue();

        if (value < 0) {
            throw new HelpingException(getLocalizer(), "TTP.BadPrevGraf",
                    printableControlSequence(context), Long.toString(value));
        }
        context.setCount(key, value, prefix.clearGlobal());
    }

    /**
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
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

        long value = Count.scanInteger(context, source, typesetter);
        if (value < 0) {
            throw new HelpingException(getLocalizer(), "TTP.BadPrevGraf",
                    printableControlSequence(context), Long.toString(value));
        }
        context.setCount(key, value, prefix.clearGlobal());
    }

    /**
     * @see org.extex.interpreter.type.arithmetic.Divideable#divide(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void divide(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");

        long value = Count.scanInteger(context, source, null);

        if (value == 0) {
            throw new ArithmeticOverflowException(
                    printableControlSequence(context));
        }

        value = context.getCount(key).getValue() / value;
        if (value < 0) {
            throw new HelpingException(getLocalizer(), "TTP.BadPrevGraf",
                    printableControlSequence(context), Long.toString(value));
        }
        context.setCount(key, value, prefix.clearGlobal());
    }

    /**
     * @see org.extex.interpreter.type.arithmetic.Multiplyable#multiply(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void multiply(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");

        long value = Count.scanInteger(context, source, null);
        value *= context.getCount(key).getValue();
        if (value < 0) {
            throw new HelpingException(getLocalizer(), "TTP.BadPrevGraf",
                    printableControlSequence(context), Long.toString(value));
        }
        context.setCount(key, value, prefix.clearGlobal());
    }

}
