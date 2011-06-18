/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\righthyphenmin</code>.
 * 
 * <doc name="righthyphenmin"> <h3>The Primitive <tt>\righthyphenmin</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * 
 * <pre class="syntax">
 *    &lang;righthyphenmin&rang;
 *      &rarr; <tt>\righthyphenmin</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}  </pre>
 * 
 * <h4>Example:</h4>
 * 
 * <pre class="TeXSample">
 *   \righthyphenmin=3 </pre>
 * 
 * </doc>
 * 
 * 
 * The value is stored in the <code>HyphernationTable</code>. Each
 * <code>HyphernationTable</code> is based on <code>\language</code> and has its
 * own <code>\righthyphenmin</code> value (other than the original <logo>T<span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo>).
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
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public RightHyphenmin(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.code.Advanceable#advance(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef > 0) {
            prefix.setGlobal(true);
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long righthyphenmin;
        try {
            righthyphenmin = table.getRightHyphenMin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
        righthyphenmin += source.parseInteger(context, source, typesetter);

        try {
            table.setRightHyphenMin(righthyphenmin);
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
    @Override
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        try {
            return getHyphenationTable(context).getRightHyphenMin();
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
    @Override
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        try {
            return getHyphenationTable(context).getRightHyphenMin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.code.Divideable#divide(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef > 0) {
            prefix.setGlobal(true);
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long righthyphenmin;
        try {
            righthyphenmin = table.getRightHyphenMin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
        long arg = source.parseInteger(context, source, typesetter);
        if (arg == 0) {
            throw new ArithmeticOverflowException(toText(context));
        }
        righthyphenmin /= arg;

        try {
            table.setRightHyphenMin(righthyphenmin);
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
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef > 0) {
            prefix.setGlobal(true);
        }

        Language table = getHyphenationTable(context);
        source.getOptionalEquals(context);
        long righthyphenmin = source.parseInteger(context, source, typesetter);

        try {
            table.setRightHyphenMin(righthyphenmin);
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
     * @see org.extex.interpreter.type.code.Multiplyable#multiply(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef > 0) {
            prefix.setGlobal(true);
        }

        Language table = getHyphenationTable(context);
        source.getKeyword(context, "by");
        long righthyphenmin;
        try {
            righthyphenmin = table.getRightHyphenMin();
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }
        righthyphenmin *= source.parseInteger(context, source, typesetter);

        try {
            table.setRightHyphenMin(righthyphenmin);
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
     * @see org.extex.interpreter.type.Theable#the(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        Language table = getHyphenationTable(context);
        try {
            return context.getTokenFactory().toTokens(
                String.valueOf(table.getRightHyphenMin()));
        } catch (HyphenationException e) {
            if (e.getCause() instanceof ConfigurationException) {
                throw (ConfigurationException) e.getCause();
            }
            throw new NoHelpException(e);
        }
    }

}
