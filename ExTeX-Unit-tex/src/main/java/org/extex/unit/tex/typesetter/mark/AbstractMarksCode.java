/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.typesetter.mark;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * Thus abstract base class for marks primitives provides the common features.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public abstract class AbstractMarksCode extends AbstractCode
        implements
            ExpandableCode,
            TokensConvertible {

    /**
     * Creates a new object.
     *
     * @param name the name of the primitive
     */
    public AbstractMarksCode(String name) {

        super(name);
    }

    /**
     * This method converts a register into tokens.
     * It might be necessary to read further tokens to determine which value to
     * use. For instance an additional register number might be required. In
     * this case the additional arguments Context and TokenSource can be used.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use for conversion
     *
     * @return the converted value
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.tokens.TokensConvertible#convertTokens(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens convertTokens(Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        Tokens mark = getValue(context, getKey(context, source, typesetter));
        return (mark != null ? mark : Tokens.EMPTY);
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
     *
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        source.push(getValue(context, getKey(context, source, typesetter)));
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

        source.push(getValue(context, getKey(context, source, typesetter)));
    }

    /**
     * Get the key for this mark.
     *
     * <doc type="syntax" name="mark name">
     * <h3>A Mark Name</h3>
     * <p>
     *  A mark name determines under which key a mark can be
     *  addressed. In <logo>TeX</logo> this used to be a positive number only.
     *  This has been extended to allow also a token list in braces.
     * </p>
     * <p>
     *  The alternative is controlled by the count register
     *  <tt>\register.max</tt>. The following interpretation of the value of this
     *   count is used:
     *  <ul>
     *   <li>If the value of this count register is negative
     *    then a arbitrary non-negative number is allowed as register name
     *    as well as any list of tokens enclosed in braces.</li>
     *   <li>If the value of this count register is not-negative
     *    then a only a non-negative number is allowed as register name
     *    which does not exceed the value of the count register.</li>
     *  </ul>
     * </p>
     * <p>
     *  The value of the count register <tt>\register.max</tt> is set differently
     *  for various configurations of <logo>ExTeX</logo>:
     *  <ul>
     *   <li><logo>TeX</logo> uses the value 255.</li>
     *   <li><logo>eTeX</logo> uses the value 32767.</li>
     *   <li><logo>Omega</logo> uses the value 65536.</li>
     *   <li><logo>ExTeX</logo> uses the value -1.</li>
     *  </ul>
     * </p>
     * <p>
     *  Note that the register name <tt>\register.max</tt> contains a period.
     *  Thus it can normally not be entered easily since the catcode of the
     *  period is OTHER but needs to be LETTER. Thus you have to use a
     *  temporarily reassigned category code (see
     *  {@link org.extex.unit.tex.register.CatcodePrimitive <tt>\catcode</tt>}
     *   or use
     *  {@link org.extex.unit.tex.macro.Csname <tt>\csname</tt>}.
     * </p>
     *
     * <h4>Syntax</h4>
     * <pre class="syntax">
     *   &lang;register name&rang;
     *       &rarr; {@linkplain
     *        org.extex.interpreter.TokenSource#scanTokens(Context,boolean,boolean,String)
     *        &lang;tokens&rang;}
     *        | {@linkplain org.extex.core.count.CountParser#scanNumber(Context,TokenSource,Typesetter)
     *        &lang;number&rang;}  </pre>
     *
     * <h4>Examples</h4>
     * <pre class="TeXSample">
     *  123
     *  {abc}
     * </pre>
     *
     * </doc>
     *
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the key for the mark primitive
     *
     * @throws InterpreterException in case of an error
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        return source.scanRegisterName(context, source, typesetter,
            printableControlSequence(context));
    }

    /**
     * Get the value for this mark.
     *
     * @param context the interpreter context
     * @param key the key
     *
     * @return the value
     */
    protected abstract Tokens getValue(Context context, String key);

}