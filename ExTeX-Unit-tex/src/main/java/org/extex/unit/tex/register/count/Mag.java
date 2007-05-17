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

import org.extex.base.parser.CountConvertible;
import org.extex.base.parser.CountParser;
import org.extex.base.type.arithmetic.Advanceable;
import org.extex.base.type.arithmetic.Divideable;
import org.extex.base.type.arithmetic.Multiplyable;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\mag</code>.
 * It sets the named count register to the value given, and as a side effect all
 * prefixes are zeroed.
 * 
 * <doc name="mag">
 * <h3>The Primitive <tt>\mag</tt></h3>
 * <p>
 * The primitive <tt>\mag</tt> provides a means to set the magnification
 * factor for the current document. The primitive acts like a normal count
 * register. The magnification factor is given in multiples of 1000. This means
 * that the default value 1000 corresponds to an unmagnified output.
 * </p>
 * <p>
 * The effect of the setting of the magnification factor is that all length
 * values are multiplied with the magnification factor (divided by 1000). An
 * exception are the <i>true</i> length values. This means a length of
 * 1&nbsp;pt at a magnification of 1200 is in effect 1.2&nbsp;pt long. Whereas a
 * length of 1&nbsp;true&nbsp;pt remains unaffected by the magnification.
 * </p>
 * <p>
 * The magnification can only changed once at the beginning of a run.
 * </p>
 * <p>
 * An attempt to assign a non-positive number to <tt>\mag</tt> leads to an
 * error.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;mag&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\mag</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt>
 *       |  <tt>\immediate</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \mag=1600  </pre>
 * 
 * </doc>
 * 
 * @see org.extex.base.type.arithmetic.Advanceable
 * @see org.extex.base.type.arithmetic.Divideable
 * @see org.extex.base.type.arithmetic.Multiplyable
 * @see org.extex.interpreter.type.Theable
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Mag extends AbstractCount
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
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Mag(String name) {

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

        source.getKeyword(context, "by");

        long value = CountParser.scanInteger(context, source, null);
        value += context.getMagnification();

        context.setMagnification(value, true);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getOptionalEquals(context);

        long value = CountParser.scanInteger(context, source, typesetter);
        context.setMagnification(value, true);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.parser.CountConvertible#convertCount(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return context.getMagnification();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.type.arithmetic.Divideable#divide(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getKeyword(context, "by");

        long value = CountParser.scanInteger(context, source, null);

        if (value == 0) {
            throw new ArithmeticOverflowException(
                printableControlSequence(context));
        }

        value = context.getMagnification() / value;
        context.setMagnification(value, true);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        try {
            source.push(context.getTokenFactory().toTokens( //
                context.getMagnification()));
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * Initialize the Code with some value coming from a String.
     * 
     * @param context the interpreter context
     * @param source the source of information for the initialization
     * @param typesetter the typesetter
     * 
     * @see org.extex.interpreter.type.InitializableCode#init(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void init(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        if (source == null) {
            return;
        }
        Token t = source.getNonSpace(context);
        if (t == null) {
            return;
        }
        source.push(t);
        long value = CountParser.scanInteger(context, source, typesetter);
        context.setMagnification(value, false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.type.arithmetic.Multiplyable#multiply(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getKeyword(context, "by");

        long value = CountParser.scanInteger(context, source, null);
        value *= context.getMagnification();
        context.setMagnification(value, true);
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
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException, TypesetterException {

        return context.getTokenFactory().toTokens( //
            context.getMagnification());
    }

}
