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

package org.extex.unit.tex.macro;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.macro.LetCode;

/**
 * This class provides an implementation for the primitive <code>\let</code>.
 *
 * <doc name="let">
 * <h3>The Primitive <tt>\let</tt></h3>
 * <p>
 *  The primitive <tt>\let</tt> defined a control sequence or active character.
 *  The value is taken from the meaning of another token. If the token is a
 *  control sequence or active character then the meaning is used. If the other
 *  definition is changed the newly defined binding remains intact.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;let&rang;
 *      &rarr; <tt>\let</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *        &lang;control sequence&rang;} {@linkplain
 *      org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *      &lang;equals&rang;} {@linkplain
 *       org.extex.interpreter.TokenSource#getToken(Context)
 *       &lang;token&rang;}  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \let\a=\b  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Let extends AbstractAssignment {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 28012007L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Let(String name) {

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
     * @throws HelpingException in case of an error
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        source.getOptionalEquals(context);
        Token t = source.getToken(context);

        if (t == null) {
            throw new EofException(printableControlSequence(context));
        }

        let(prefix, context, cs, t);
    }

    /**
     * Assign a new meaning to a control sequence.
     * This is the core of the primitive <code>\let</code>.
     *
     * @param prefix the flags to consider
     * @param context the processor context
     * @param cs the control sequence token to bind
     * @param t the new meaning of the control sequence token. If this
     *  parameter is <code>null</code> then an exception is thrown.
     *
     * @throws HelpingException in case of an error
     */
    public static void let(Flags prefix, Context context,
            CodeToken cs, Token t) throws HelpingException {

        Code code =
                (t instanceof CodeToken
                        ? context.getCode((CodeToken) t)
                        : new LetCode(t));
        context.setCode(cs, code, prefix.clearGlobal());
    }
}
