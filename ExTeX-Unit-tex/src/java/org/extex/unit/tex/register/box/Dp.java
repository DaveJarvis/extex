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

package org.extex.unit.tex.register.box;

import java.io.Serializable;

import javax.xml.transform.Source;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.DimenConvertible;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.util.exception.GeneralException;

/**
 * This class provides an implementation for the primitive <code>\dp</code>.
 *
 * <doc name="dp">
 * <h3>The Primitive <tt>\dp</tt></h3>
 * <p>
 *  The primitive <tt>\dp</tt> refers to the depth of a box register.
 *  It can be used in various contexts.
 * </p>
 *
 * <h4>Execution of the Primitive</h4>
 * <p>
 *  If the primitive is used in a context it initiated an assignment to the
 *  actual depth of the box register. This has an effect only in  the case that
 *  the box register is not void.
 * </p>
 * <p>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;dp&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\dp</tt> {@linkplain
 *        org.extex.unit.tex.register.box.AbstractBox#getKey(Context,Source,Typesetter,String)
 *        &lang;box register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.type.dimen#Dimen(org.extex.interpreter.context.Context,org.extex.interpreter.TokenSource)
 *        &lang;dimen&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang;  </pre>
 * </p>
 *
 * <h4>Examples</h4>
 * <p>
 *  <pre class="TeXSample">
 *    \dp42 = 12mm  </pre>
 *  <pre class="TeXSample">
 *    \dp42 = \dimen3  </pre>
 * </p>
 *
 * <h4>Expansion of the Primitive</h4>
 * <p>
 *  In an expansion context the primitive results in the the current depth of
 *  the given box register. In case that the box register is empty the result
 *  is 0&nbsp;pt.
 * </p>
 *
 * <h4>Syntax</h4>
 * <p>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    <tt>\dp</tt> {@linkplain
 *      org.extex.interpreter.TokenSource#scanNumber(Context)
 *      &lang;8-bit&nbsp;number&rang;} </pre>
 * </p>
 *
 * <h4>Examples</h4>
 * <p>
 *  <pre class="TeXSample">
 *    \dimen0 = \dp42  </pre>
 *
 *  <pre class="TeXSample">
 *    \the\dp42  </pre>
 * </p>
 *
 * <h4>Conversion to a Count</h4>
 * <p>
 *  The primitive is convertible into a count. It can be used wherever an
 *  integer quantity is expected.
 * </p>
 *
 * <h4>Interaction with <tt>\the</tt></h4>
 * <p>
 *  The primitive <tt>\the</tt> can be applied to this primitive. In this case
 *  it results in a token list representing the value in points.
 * </p>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Dp extends Setbox
        implements
            Serializable,
            ExpandableCode,
            Theable,
            CountConvertible,
            DimenConvertible {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Dp(final String name) {

        super(name);
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
     * @see org.extex.interpreter.type.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public long convertCount(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return convertDimen(context, source, typesetter);
    }

    /**
     * This method converts a register into a dimen.
     * It might be necessary to read further tokens to determine which value to
     * use. For instance an additional register number might be required. In
     * this case the additional arguments Context and TokenSource can be used.
     *
     * The return value is the length in scaled points.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use for conversion
     *
     * @return the converted value in sp
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.dimen.DimenConvertible#convertDimen(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public long convertDimen(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Box b = context.getBox(getKey(context, source, typesetter, getName()));
        return (b == null ? 0 : b.getDepth().getValue());
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
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

        Box box = context.getBox(getKey(context, source, typesetter, getName()));
        source.getOptionalEquals(context);
        Dimen d = Dimen.parse(context, source, typesetter);

        if (box != null) {
            box.setDepth(d);
        }
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
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        source.push(the(context, source, typesetter));
    }

    /**
     * This method is the getter for the description of the primitive.
     *
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     *
     * @return the description of the primitive as list of Tokens
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        try {
            Box box = context.getBox(getKey(context, source, typesetter, getName()));
            FixedDimen d = (box == null ? Dimen.ZERO_PT : box.getDepth());
            return d.toToks(context.getTokenFactory());
        } catch (GeneralException e) {
            throw new InterpreterException(e);
        }
    }

}
