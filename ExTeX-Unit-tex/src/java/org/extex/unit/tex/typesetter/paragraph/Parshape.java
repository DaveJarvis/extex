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

package org.extex.unit.tex.typesetter.paragraph;

import org.extex.core.count.CountConvertible;
import org.extex.core.count.CountParser;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.DimenParser;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;

/**
 * This class provides an implementation for the primitive <code>\relax</code>.
 *
 * <doc name="parshape">
 * <h3>The Primitive <tt>\parshape</tt></h3>
 * <p>
 *  The primitive <tt>\parshape</tt> is a declaration of the shape of the
 *  paragraph. With its help it is possible to control the left and right margin
 *  of the current paragraph.
 * </p>
 * <p>
 *  The shape of the paragraph is controlled on a line base. For each line the
 *  left indentation and the width are given. The first argument of
 *  <tt>\parshape</tt> determines the number of such pairs to follow.
 * </p>
 * <p>
 *  When the paragraph is typeset the lines are indented and adjusted according
 *  to the specification given. If there are more lines specified as actually
 *  present in the current paragraph then the remaining specifications are
 *  discarded at the end of the paragraph. If there are less lines then the last
 *  specification is repeated.
 * </p>
 * <p>
 *  If several <tt>\parshape</tt> declarations are given in one paragraph then
 *  the one is used which is in effect at the end of the paragraph. This means
 *  that later declarations overrule earlier ones.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;parshape&rang;
 *        &rarr; <tt>\parshape</tt> {@linkplain
 *        org.extex.core.count.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;8-bit&nbsp;number&rang;} ... </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \parshape 3 20pt \linewidth
 *                20pt \linewidth
 *                0pt \linewidth </pre>
 *  <pre class="TeXSample">
 *    \parshape 0 </pre>
 *
 * <h3><tt>\parshape</tt> as special integer</h3>
 * <p>
 *  <tt>\parshape</tt> acts as special count register which can be queried.
 *  It returns the size of the current parshape specification or 0 if none is
 *  present.
 * </p>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \count1=\parshape  </pre>
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Parshape extends AbstractCode implements CountConvertible, Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Parshape(String name) {

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
     * @see org.extex.core.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        ParagraphShape parshape = context.getParshape();
        return (parshape != null ? parshape.getSize() / 2 : 0);
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
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        long n = CountParser.scanInteger(context, source, typesetter);
        if (n <= 0) {
            context.setParshape(null);
        } else {
            ParagraphShape parshape = new ParagraphShape();
            while (n-- > 0) {
                Dimen left = DimenParser.parse(context, source, typesetter);
                Dimen right = DimenParser.parse(context, source, typesetter);
                parshape.add(left, right);
            }
            context.setParshape(parshape);
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
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(Context context, TokenSource source,
            Typesetter typesetter)
            throws InterpreterException,
                CatcodeException {

        ParagraphShape parshape = context.getParshape();
        return context.getTokenFactory().toTokens( //
            parshape != null ? parshape.getSize() / 2 : 0);
    }

}
