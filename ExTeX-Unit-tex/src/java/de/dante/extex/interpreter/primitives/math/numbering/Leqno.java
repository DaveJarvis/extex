/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.math.numbering;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.CantUseInException;

import de.dante.extex.interpreter.primitives.math.AbstractMathCode;
import de.dante.extex.typesetter.ListMaker;
import de.dante.extex.typesetter.Typesetter;
import de.dante.extex.typesetter.listMaker.math.EqConsumer;

/**
 * This class provides an implementation for the primitive <code>\leqno</code>.
 *
 * <doc name="leqno">
 * <h3>The Math Primitive <tt>\leqno</tt></h3>
 * <p>
 *  The math primitive <tt>\leqno</tt> arranges that the following material is
 *  typeset in math mode and placed on the left side of the preceding material.
 * </p>
 * <p>
 *  The primitive can be used in display math mode only. If used in another mode
 *  an error is raised. An error is also raised when more than one invocations
 *  appear in one display math list or <tt>\leqno</tt> appears together with
 *  <tt>\eqno</tt> in a display math list.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;span&rang;
 *       &rarr; <tt>\leqno</tt>  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    $$ 12 \leqno 34 $$ </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Leqno extends AbstractMathCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Leqno(final String name) {

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
            throws CantUseInException {

        ListMaker lm = typesetter.getListMaker();

        try {

            if (lm instanceof EqConsumer) {
                ((EqConsumer) lm).switchToNumber(true);
                return;
            }

        } catch (CantUseInException e) {
            throw new CantUseInException(printableControlSequence(context),
                    "math mode");
        }
        throw new CantUseInException(printableControlSequence(context), //
                typesetter.getMode().toString());
    }

}
