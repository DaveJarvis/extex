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

package org.extex.unit.tex.arithmetic;

import org.extex.core.exception.helping.CantUseAfterException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.arithmetic.Divideable;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\divide</code>.
 *
 * <doc name="divide">
 * <h3>The Primitive <tt>\divide</tt></h3>
 * <p>
 *  This primitive implements an assignment. The variable given as next tokens
 *  is divided by the quantity given after the optional <tt>by</tt>.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *   &lang;divide&rang;
 *     &rarr; &lang;optional prefix&rang; <tt>\divide</tt> &lang;dividable&rang;
 *
 *   &lang;dividable&rang;
 *     &rarr; &lang;integer variable&rang; &lang;optional <tt>by</tt>&rang; {@linkplain
 *      org.extex.scanner.CountParser#scanInteger(Context,TokenSource,Typesetter)
 *      &lang;8-bit&nbsp;number&rang;}
 *      |  &lang;dimen variable&rang; &lang;optional <tt>by</tt>&rang; {@linkplain
 *      org.extex.scanner.CountParser#scanInteger(Context,TokenSource,Typesetter)
 *      &lang;8-bit&nbsp;number&rang;}
 *      |  &lang;glue variable&rang; &lang;optional <tt>by</tt>&rang; {@linkplain
 *      org.extex.scanner.CountParser#scanInteger(Context,TokenSource,Typesetter)
 *      &lang;8-bit&nbsp;number&rang;}
 *      |  &lang;muglue variable&rang; &lang;optional <tt>by</tt>&rang; {@linkplain
 *      org.extex.scanner.CountParser#scanInteger(Context,TokenSource,Typesetter)
 *      &lang;8-bit&nbsp;number&rang;}
 *
 *   &lang;optional prefix&rang;
 *     &rarr;
 *      |  <tt>\global</tt> &lang;optional prefix&rang;
 *
 *   &lang;optional <tt>by</tt>&rang;
 *     &rarr; [by]
 *      |  {@linkplain org.extex.interpreter.TokenSource#skipSpace()
*            &lang;optional spaces&rang;}
 *   </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \divide\count12 345  </pre>
 *  <pre class="TeXSample">
 *    \divide\count12 by -345  </pre>
 *
 * </doc>
 *
 *
 * @see org.extex.interpreter.type.arithmetic.Divideable
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4408 $
 */
public class Divide extends AbstractAssignment {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Divide(String name) {

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
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        Token cs = source.getToken(context);

        if (cs instanceof CodeToken) {
            Code code = context.getCode((CodeToken) cs);

            if (code instanceof Divideable) {

                ((Divideable) code).divide(prefix, context, source, typesetter);
                return;

            } else if (code == null) {
                throw new UndefinedControlSequenceException(//
                        printable(context, cs));
            }
        } else if (cs == null) {
            throw new EofException(printableControlSequence(context));
        }
        throw new CantUseAfterException(cs.toText(),
                printableControlSequence(context));
    }

}
