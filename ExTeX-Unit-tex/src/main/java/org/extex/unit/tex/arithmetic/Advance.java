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

import org.extex.base.type.arithmetic.Advanceable;
import org.extex.core.exception.helping.CantUseAfterException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\advance</code>.
 * 
 * <doc name="advance">
 * <h3>The Primitive <tt>\advance</tt></h3>
 * <p>
 * This primitive implements an assignment. The variable given as next tokens is
 * incremented by the quantity given after the optional <tt>by</tt>.
 * </p>
 * <p>
 * The exact operation of <tt>\advance</tt> is determined by the quantity
 * following the <tt>\advance</tt> keyword.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *   &lang;advance&rang;
 *     &rarr; &lang;optional prefix&rang; <tt>\advance</tt> &lang;advancable&rang;
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang;
 *
 *   &lang;advancable&rang;
 *     &rarr; &lang;integer variable&rang; &lang;optional <tt>by</tt>&rang; {@linkplain
 *      org.extex.base.parser.ConstantCountParser#scanInteger(Context,TokenSource,Typesetter)
 *      &lang;number&rang;}
 *      |  &lang;dimen variable&rang; &lang;optional <tt>by</tt>&rang; {@linkplain
 *        org.extex.core.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;}
 *      |  &lang;glue variable&rang; &lang;optional <tt>by</tt>&rang; &lang;glue&rang;
 *      |  &lang;muglue variable&rang; &lang;optional <tt>by</tt>&rang; &lang;muglue&rang;
 *
 *   &lang;optional <tt>by</tt>&rang;
 *     &rarr; [by]
 *      |  {@linkplain org.extex.interpreter.TokenSource#skipSpace()
 *            &lang;optional spaces&rang;}
 *   </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \advance\count12 345  </pre>
 *  <pre class="TeXSample">
 *    \advance\count12 by -345  </pre>
 * 
 * </doc>
 * 
 * 
 * @see org.extex.base.type.arithmetic.Advanceable
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4408 $
 */
public class Advance extends AbstractAssignment {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Advance(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token cs = source.getToken(context);

        if (cs instanceof CodeToken) {
            Code code = context.getCode((CodeToken) cs);

            if (code instanceof Advanceable) {

                ((Advanceable) code).advance(prefix, context, source,
                    typesetter);
                return;

            } else if (code == null) {
                throw new UndefinedControlSequenceException(printable(context,
                    cs));
            }
        } else if (cs == null) {
            throw new EofException(printableControlSequence(context));
        }
        throw new CantUseAfterException(cs.toText(),
            printableControlSequence(context));
    }

}
