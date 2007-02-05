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

package org.extex.unit.tex.macro;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\expandafter</code>.
 *
 * <doc name="expandafter">
 * <h3>The Primitive <tt>\expandafter</tt></h3>
 * <p>
 *  The primitive <tt>\expandafter</tt> reverses the expansion of the following
 *  two tokens. For this purpose the next token following the invocation is read
 *  unexpanded and put aside. Then the next token is expanded in whatever way
 *  the token requires expansion. This may involve the reading and expansion of
 *  further tokens. When this expansion is finished then the saved token is put
 *  back into the input stream.
 * </p>
 * <p>
 *  Any prefix argument is saved for the expansion of the following token.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;expandafter&rang;
 *     &rarr; <tt>\expandafter</tt> {@linkplain
 *         org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *         &lang;control sequence&rang;} ...  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \expandafter ab  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Expandafter extends AbstractCode implements ExpandableCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060415L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Expandafter(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        Token t = source.getToken(context);
        Token token = source.getToken(context);
        if (t == null || token == null) {
            throw new EofException(printableControlSequence(context));
        }
        source.execute(token, context, typesetter);
        source.push(t);
    }

    /**
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        Token t = source.getToken(context);
        if (t == null) {
            throw new EofException(printableControlSequence(context));
        }
        Token token = source.getToken(context);
        if (token == null) {
            throw new EofException(printableControlSequence(context));
        }
        source.execute(token, context, typesetter);
        source.push(t);
    }

}
