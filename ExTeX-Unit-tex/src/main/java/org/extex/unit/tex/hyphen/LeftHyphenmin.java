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

package org.extex.unit.tex.hyphen;

import org.extex.base.type.arithmetic.Advanceable;
import org.extex.base.type.arithmetic.Divideable;
import org.extex.base.type.arithmetic.Multiplyable;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.Theable;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\lefthyphenmin</code>.
 * 
 * <doc name="lefthyphenmin">
 * <h3>The Primitive <tt>\lefthyphenmin</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * 
 * <pre class="syntax">
 *    &lang;lefthyphenmin&rang;
 *      &rarr; <tt>\lefthyphenmin</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}  </pre>
 * 
 * <h4>Example:</h4>
 * 
 * <pre class="TeXSample">
 *   \lefthyphenmin=3 </pre>
 * 
 * </doc>
 * 
 * 
 * The value are stored in the <code>HyphernationTable</code>. Each
 * <code>HyphernationTable</code> are based on <code>\language</code> and
 * have its own <code>\lefthyphenmin</code> value (different to original
 * <logo>TeX</logo>).
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4770 $
 */
public class LeftHyphenmin extends AbstractHyphenationCode
        implements
            CountConvertible,
            DimenConvertible,
            Advanceable,
            Multiplyable,
            Divideable,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public LeftHyphenmin(String name) {

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

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long lefthyphenmin;
        try {
            lefthyphenmin = table.getLeftHyphenmin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
        lefthyphenmin += source.parseInteger(context, source, typesetter);

        try {
            table.setLeftHyphenmin(lefthyphenmin);
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw (ConfigurationException) e.getCause();
            }
            throw new NoHelpException(e);
        }

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
        prefix.clearGlobal(); // gene: not really useful but a little bit of
        // compatibility
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountConvertible#convertCount(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        try {
            return getHyphenationTable(context).getLeftHyphenmin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.DimenConvertible#convertDimen(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        try {
            return getHyphenationTable(context).getLeftHyphenmin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
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

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long lefthyphenmin;
        try {
            lefthyphenmin = table.getLeftHyphenmin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
        long arg = source.parseInteger(context, source, typesetter);
        if (arg == 0) {
            throw new ArithmeticOverflowException(
                printableControlSequence(context));
        }
        lefthyphenmin /= arg;

        try {
            table.setLeftHyphenmin(lefthyphenmin);
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw (ConfigurationException) e.getCause();
            }
            throw new NoHelpException(e);
        }

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
        prefix.clearGlobal(); // gene: not really useful but a little bit of
        // compatibility
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        Language table = getHyphenationTable(context);
        source.getOptionalEquals(context);
        long lefthyphenmin = source.parseInteger(context, source, typesetter);

        try {
            table.setLeftHyphenmin(lefthyphenmin);
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw (ConfigurationException) e.getCause();
            }
            throw new NoHelpException(e);
        }

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
        prefix.clearGlobal(); // gene: not really useful but a little bit of
        // compatibility
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

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long lefthyphenmin;
        try {
            lefthyphenmin = table.getLeftHyphenmin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
        lefthyphenmin *= source.parseInteger(context, source, typesetter);

        try {
            table.setLeftHyphenmin(lefthyphenmin);
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw (ConfigurationException) e.getCause();
            }
            throw new NoHelpException(e);
        }

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
        prefix.clearGlobal(); // gene: not really useful but a little bit of
        // compatibility
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

        Language table = getHyphenationTable(context);
        try {
            return context.getTokenFactory().toTokens( //
                table.getLeftHyphenmin());
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw (ConfigurationException) e.getCause();
            }
            throw new NoHelpException(e);
        }
    }

}
