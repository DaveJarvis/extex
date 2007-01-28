/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.hyphen;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.ArithmeticOverflowException;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.arithmetic.Advanceable;
import org.extex.interpreter.type.arithmetic.Divideable;
import org.extex.interpreter.type.arithmetic.Multiplyable;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.interpreter.type.dimen.DimenConvertible;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive
 * <code>\righthyphenmin</code>.
 *
 * <doc name="righthyphenmin">
 * <h3>The Primitive <tt>\righthyphenmin</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  <pre class="syntax">
 *    &lang;righthyphenmin&rang;
 *      &rarr; <tt>\righthyphenmin</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#scanInteger(Context,Typesetter)
 *        &lang;number&rang;}  </pre>
 *
 * <h4>Example:</h4>
 *  <pre class="TeXSample">
 *   \righthyphenmin=3 </pre>
 *
 * </doc>
 *
 *
 * The value is stored in the <code>HyphernationTable</code>.
 * Each <code>HyphernationTable</code> is based on <code>\language</code>
 * and has its own <code>\righthyphenmin</code> value (other than the original
 * <logo>TeX</logo>).
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4770 $
 */
public class RightHyphenmin extends AbstractHyphenationCode
        implements
            CountConvertible,
            DimenConvertible,
            Advanceable,
            Multiplyable,
            Divideable,
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
    public RightHyphenmin(final String name) {

        super(name);
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

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long righthyphenmin = table.getRightHyphenmin();
        righthyphenmin += Count.scanInteger(context, source, typesetter);

        try {
            table.setRightHyphenmin(righthyphenmin);
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw new InterpreterException(e.getCause());
            }
            throw new InterpreterException(e);
        }

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
        prefix.clearGlobal(); //gene: not really useful but a little bit of compatibility
    }

    /**
     * @see org.extex.interpreter.type.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertCount(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return getHyphenationTable(context).getRightHyphenmin();
    }

    /**
     * @see org.extex.interpreter.type.dimen.DimenConvertible#convertDimen(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertDimen(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return getHyphenationTable(context).getRightHyphenmin();
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

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long righthyphenmin = table.getRightHyphenmin();
        long arg = Count.scanInteger(context, source, typesetter);
        if (arg == 0) {
            throw new ArithmeticOverflowException(
                    printableControlSequence(context));
        }
        righthyphenmin /= arg;

        try {
            table.setRightHyphenmin(righthyphenmin);
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw new InterpreterException(e.getCause());
            }
            throw new InterpreterException(e);
        }

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
        prefix.clearGlobal(); //gene: not really useful but a little bit of compatibility
    }

    /**
     * Scan for righthyphenmin value and store it in the
     * <code>HyphernationTable</code> with the language-number.
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        Language table = getHyphenationTable(context);
        source.getOptionalEquals(context);
        long righthyphenmin = Count.scanInteger(context, source, typesetter);

        try {
            table.setRightHyphenmin(righthyphenmin);
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw new InterpreterException(e.getCause());
            }
            throw new InterpreterException(e);
        }

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
        prefix.clearGlobal(); //gene: not really useful but a little bit of compatibility
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

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long righthyphenmin = table.getRightHyphenmin();
        righthyphenmin *= Count.scanInteger(context, source, typesetter);

        try {
            table.setRightHyphenmin(righthyphenmin);
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw new InterpreterException(e.getCause());
            }
            throw new InterpreterException(e);
        }

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
        prefix.clearGlobal(); //gene: not really useful but a little bit of compatibility
    }

    /**
     * Return the <code>Tokens</code> to show the content with <code>\the</code>.
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Language table = getHyphenationTable(context);
        try {
            return new Tokens(context, String
                    .valueOf(table.getRightHyphenmin()));
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw new InterpreterException(e.getCause());
            }
            throw new InterpreterException(e);
        }
    }

}
