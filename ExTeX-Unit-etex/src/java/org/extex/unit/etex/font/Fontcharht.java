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

package org.extex.unit.etex.font;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.DimenConvertible;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.type.CatcodeException;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;
import org.extex.util.exception.GeneralException;

/**
 * This class provides an implementation for the primitive
 * <code>\fontcharht</code>.
 *
 * <doc name="fontcharht">
 * <h3>The Primitive <tt>\fontcharht</tt></h3>
 * <p>
 *  The primitive <tt>\fontcharht</tt> is a read-only dimen register which
 *  corresponds to the height of a character in a given font. If the character
 *  is not defined in the font then 0pt is returned.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;fontcharht&rang;
 *       &rarr; <tt>\fontcharht</tt> {@linkplain
 *          org.extex.interpreter.TokenSource#getFont(Context,String)
 *          &lang;font&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#scanCharacterCode(Context,Typesetter,String)
 *          &lang;character code&rang;}  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \fontcharht\tenrm `a  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Fontcharht extends AbstractCode
        implements
            ExpandableCode,
            CountConvertible,
            DimenConvertible,
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
    public Fontcharht(final String name) {

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
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
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
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertDimen(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return get(context, source, typesetter).getValue();
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
     * Get the dimen value of the height. If the character is not defined in the
     * font then ZERO_PT is returned.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the dimen value of the height
     *
     * @throws InterpreterException in case of an error
     */
    private FixedDimen get(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Font fnt = source.getFont(context, getName());
        try {
            UnicodeChar uc =
                    source.scanCharacterCode(context, typesetter, null);
            FixedGlue d = fnt.getHeight(uc);
            return (d != null ? d.getLength() : Dimen.ZERO_PT);

        } catch (EofException e) {
            throw new EofException(printableControlSequence(context));
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
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        try {
            FixedDimen height = get(context, source, typesetter);
            return height.toToks(context.getTokenFactory());
        } catch (CatcodeException e) {
            throw new InterpreterException(e);
        } catch (InterpreterException e) {
            throw e;
        } catch (GeneralException e) {
            throw new InterpreterException(e);
        }
    }

}
