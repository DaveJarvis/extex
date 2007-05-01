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
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\endcsname</code>.
 *
 * <doc name="endcsname">
 * <h3>The Primitive <tt>\endcsname</tt></h3>
 * <p>
 *  The macro <tt>\endcsname</tt> is used in combination with the macro
 *  {@link org.extex.unit.tex.macro.Csname <tt>\csname</tt>}
 *  only. Whenever a <tt>\endcsname</tt> is seen alone it must be an error.
 *  Thus thus primitive produces an error message in any case.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;endcsname&rang;
 *      &rarr; <tt>\endscsname</tt>  </pre>
 *
 * <h4>Examples</h4>
 * <p>
 *  The following example shows a complicated way to invoke the macro
 *  <tt>abc</tt>. Here the primitive <tt>\endcsname</tt> is legal. It is
 *  consumed by the primitive <tt>\csname</tt> and not expanded on its own.
 * </p>
 * <pre class="TeXSample">
 *   \csname abc\endcsname  </pre>
 * </doc>
 *
 *
 * @see "TTP [1134]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Endcsname extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Endcsname(String name) {

        super(name);
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

        throw new HelpingException(getLocalizer(), "TTP.ExtraEndcsname",
                printableControlSequence(context));
    }

}
