/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.math.delimiter;

import org.extex.core.UnicodeChar;
import org.extex.core.count.CountConvertible;
import org.extex.core.count.CountParser;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.ArithmeticOverflowException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.arithmetic.Advanceable;
import org.extex.interpreter.type.arithmetic.Divideable;
import org.extex.interpreter.type.arithmetic.Multiplyable;
import org.extex.interpreter.type.math.MathDelimiter;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\odelcode</code>.
 *
 * <doc name="odelcode">
 * <h3>The Math Primitive <tt>\odelcode</tt></h3>
 * <p>
 *  The primitive <tt>\odelcode</tt> can be used to assign and query the
 *  delimiter code for a character. The delimiter code determines, how a
 *  character is typeset in math mode.
 * </p>
 * <p>
 * The <logo>TeX</logo> encoding interprets the number as 27 bit hex number:
 * <tt>"csyylxx</tt>. Here the digits have the following meaning:
 * <dl>
 *  <dt>c</dt>
 *  <dd>the math class of this delimiter. It has a range from 0 to 7.</dd>
 *  <dt>l</dt>
 *  <dd>the family for the large character. It has a range from 0 to 15.</dd>
 *  <dt>xx</dt>
 *  <dd>the character code of the large character.</dd>
 *  <dt>s</dt>
 *  <dd>the family for the small character. It has a range from 0 to 15.</dd>
 *  <dt>yy</dt>
 *  <dd>the character code of the small character.</dd>
 * </dl>
 * </p>
 * <p>
 *  The assigning a new value to a delimiter code acts in a group restricted way
 *  unless declared differently. If the prefix <tt>\global</tt> is given then
 *  the assignment is performed globally. The same effect can be achieved when
 *  the count register <tt>\globaldefs</tt> is greater than 0.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;odelcode&rang;
 *      &rarr; &lang;prefix&rang; <tt>\odelcode</tt> {@linkplain
 *        org.extex.core.count.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;8-bit&nbsp;number&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.core.count.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;8-bit&nbsp;number&rang;}
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *       |  &lang;global&rang; </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \odelcode`x="123456  </pre>
 *  <pre class="TeXSample">
 *    \global\odelcode`x="123456  </pre>
 *
 * <h4>Using as Count Register</h4>
 * <p>
 *  The primitive <tt>\odelcode</tt> can be used like a count register. This
 *  means you can use it wherever a number is expected. In addition the value
 *  can be advanced, multiplied, and divided. In any case the delimiter code
 *  is translated according to the <logo>TeX</logo> encoding and processed as
 *  number.
 * </p>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \count1=\odelcode`x  </pre>
 *  <pre class="TeXSample">
 *    \advance\odelcode`x by 42  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Odelcode extends AbstractAssignment
        implements
            CountConvertible,
            Advanceable,
            Divideable,
            Multiplyable,
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
    public Odelcode(String name) {

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
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.arithmetic.Advanceable#advance(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void advance(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        UnicodeChar charCode =
                source.scanCharacterCode(context, typesetter, getName());
        source.getKeyword(context, "by");

        long value = CountParser.scanInteger(context, source, null);
        MathDelimiter delcode = context.getDelcode(charCode);
        value += AbstractOmegaDelimiter.delimiterToLong(delcode);

        assign(prefix, context, source, charCode, value);
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
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException,
                ConfigurationException {

        UnicodeChar charCode =
                source.scanCharacterCode(context, typesetter, getName());
        source.getOptionalEquals(context);
        MathDelimiter del =
                AbstractOmegaDelimiter.parseDelimiter(context, source,
                    typesetter, getName());
        context.setDelcode(charCode, del, prefix.clearGlobal());
    }

    /**
     * Perform an assignment of a delimiter code.
     *
     * @param prefix the prefix indicator
     * @param context the interpreter context
     * @param source the token source
     * @param charCode the character to assign the delimiter code to
     * @param value the delimiter code in <logo>TeX</logo> encoding
     *
     * @throws InterpreterException in case of an error
     */
    private void assign(Flags prefix, Context context,
            TokenSource source, UnicodeChar charCode,
            long value) throws InterpreterException {

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        context.setDelcode(charCode, AbstractOmegaDelimiter
            .newMathDelimiter(value), //
            prefix.clearGlobal());

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
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

        UnicodeChar charCode =
                source.scanCharacterCode(context, typesetter, getName());
        MathDelimiter delcode = context.getDelcode(charCode);
        return AbstractOmegaDelimiter.delimiterToLong(delcode);
    }

    /**
     * This method is called when the macro <tt>\divide</tt> has been seen.
     * It performs the remaining tasks for the expansion.
     *
     * @param prefix the prefix for the command
     * @param context the processor context
     * @param source the token source to parse
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.arithmetic.Divideable#divide(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void divide(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        UnicodeChar charCode =
                source.scanCharacterCode(context, typesetter, getName());
        source.getKeyword(context, "by");

        long value = CountParser.scanInteger(context, source, null);
        MathDelimiter delcode = context.getDelcode(charCode);
        if (value == 0) {
            throw new ArithmeticOverflowException(
                printableControlSequence(context));
        }

        value = AbstractOmegaDelimiter.delimiterToLong(delcode) / value;
        assign(prefix, context, source, charCode, value);
    }

    /**
     * This method is called when the macro <tt>\multiply</tt> has been seen.
     * It performs the remaining tasks for the expansion.
     *
     * @param prefix the prefix for the command
     * @param context the processor context
     * @param source the token source to parse
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.arithmetic.Multiplyable#multiply(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void multiply(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        UnicodeChar charCode =
                source.scanCharacterCode(context, typesetter, getName());
        source.getKeyword(context, "by");

        long value = CountParser.scanInteger(context, source, null);
        MathDelimiter delcode = context.getDelcode(charCode);
        value *= AbstractOmegaDelimiter.delimiterToLong(delcode);
        assign(prefix, context, source, charCode, value);
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

        UnicodeChar charCode =
                source.scanCharacterCode(context, typesetter, getName());
        MathDelimiter delcode = context.getDelcode(charCode);
        long value = AbstractOmegaDelimiter.delimiterToLong(delcode);
        return context.getTokenFactory().toTokens(value);
    }

}
