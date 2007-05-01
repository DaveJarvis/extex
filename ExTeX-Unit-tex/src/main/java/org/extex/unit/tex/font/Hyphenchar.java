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

package org.extex.unit.tex.font;

import org.extex.core.UnicodeChar;
import org.extex.core.count.CountConvertible;
import org.extex.core.count.CountParser;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.font.Font;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\hyphenchar</code>.
 *
 * <doc name="hyphenchar">
 * <h3>The Primitive <tt>\hyphenchar</tt></h3>
 * <p>
 * The primitive <tt>\hyphenchar</tt> can be used to set the hyphenation
 * character for a given font. The undefined character &ndash; represented by a
 * negative value &ndash; indicates that no hyphenation should be applied.
 * Otherwise the given character will be used when hyphenating words.
 * </p>
 *
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *     &amp;langhyphenchar&amp;rang
 *       &amp;rarr
 * <tt>
 * \hyphenchar
 * </tt>
 *  &amp;langfont&amp;rang {@linkplain
 *         org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *         &amp;langequals&amp;rang} {@linkplain
 *         org.extex.core.count.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *         &amp;lang8-bit number&amp;rang}
 * </pre>
 *
 * <h4>Examples</h4>
 *
 * <pre class="TeXSample">
 *     \hyphenchar\font=132
 * </pre>
 *
 * <h4>Incompatibility</h4>
 * <p>
 * The TeXbook gives no indication ow the primitive should react for negative
 * values &ndash; except -1. The implementation of <logo>TeX</logo> allows to
 * store and retrieve arbitrary negative values. This behavior of <logo>TeX</logo>
 * is not preserved in <logo>ExTeX</logo>.
 * </p>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Hyphenchar extends AbstractAssignment
        implements
            CountConvertible,
            ExpandableCode,
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
    public Hyphenchar(String name) {

        super(name);
    }

    /**
     * The method <tt>assign</tt> is the core of the functionality of
     * {@link #execute(Flags, Context, TokenSource, Typesetter) execute()}.
     * This method is preferable to <tt>execute()</tt> since the
     * <tt>execute()</tt> method provided in this class takes care of
     * <tt>\afterassignment</tt> and <tt>\globaldefs</tt> as well.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        try {
            Font font = source.getFont(context, getName());
            source.getOptionalEquals(context);
            long c = CountParser.scanInteger(context, source, typesetter);
            font.setHyphenChar(UnicodeChar.get((int) c));
        } catch (EofException e) {
            throw new EofException(printableControlSequence(context));
        }
    }

    /**
     * This method converts a register into a count. It might be necessary to
     * read further tokens to determine which value to use. For instance an
     * additional register number might be required. In this case the additional
     * arguments Context and TokenSource can be used.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use for conversion
     *
     * @return the converted value
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.core.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        UnicodeChar uc;
        try {
            Font font = source.getFont(context, getName());
            uc = font.getHyphenChar();
        } catch (EofException e) {
            throw new EofException(printableControlSequence(context));
        }
        return (uc == null ? -1 : uc.getCodePoint());
    }

    /**
     * This method takes the first token and expands it. The result is placed
     * on the stack.
     * This means that expandable code does one step of expansion and puts the
     * result on the stack. To expand a token it might be necessary to consume
     * further tokens.
     *
     * @param prefix the prefix flags controlling the expansion
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        try {
            source.push(the(context, source, typesetter));
        } catch (CatcodeException e) {
            throw new InterpreterException(e);
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
     *
     * @throws InterpreterException in case of an error
     * @throws CatcodeException in case of an error in token creation
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source,
            Typesetter typesetter)
            throws InterpreterException,
                CatcodeException {

        Font font = source.getFont(context, getName());
        UnicodeChar uc = font.getHyphenChar();
        return context.getTokenFactory().toTokens( //
            uc == null ? "-1" : Integer.toString(uc.getCodePoint()));
    }

}
