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

package org.extex.unit.tex.typesetter.displace;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.DimenParser;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.unit.tex.typesetter.box.AbstractBoxPrimitive;

/**
 * This class provides an implementation for the primitive <code>\moveright</code>.
 *
 * <doc name="moveright">
 * <h3>The Primitive <tt>\moveright</tt></h3>
 * <p>
 *  The primitive <tt>\moderight</tt> takes a box and a length and moves it
 *  rightwards by the amount specified by the length.
 *  If the length is negative then the move is done leftwards.
 * </p>
 * <p>
 *  The primitive <tt>\moveright</tt> is the counterpart to
 *  {@link org.extex.unit.tex.typesetter.displace.Moveleft <tt>\moveleft</tt>}.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;moveright&rang;
 *      &rarr; <tt>\moveright</tt> {@linkplain
 *        org.extex.core.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getBox(org.extex.interpreter.Flags,Context,Typesetter,Token)
 *        &lang;box&rang;}  </pre>
 *
 * <p>
 *  The color from the typographic context is taken as foregroud color for the
 *  rule. The default color is black.
 * </p>
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \moveright 2em \hbox{abc}  </pre>
 *  <pre class="TeXSample">
 *    \moveright -1pt \hbox to 120pt {abc}  </pre>
 *  <pre class="TeXSample">
 *    \moveright 2mm \hbox spread 12pt {abc}  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Moveright extends AbstractBoxPrimitive {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Moveright(String name) {

        super(name);
    }

    /**
     * Getter for the content as Box.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use
     * @param insert the token to insert either at the beginning of the box or
     *   after the box has been gathered. If it is <code>null</code> then
     *   nothing is inserted
     *
     * @return an appropriate Box
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.unit.tex.register.box.BoxPrimitive#getBox(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    public Box getBox(Context context, TokenSource source,
            Typesetter typesetter, Token insert)
            throws InterpreterException {

        Dimen move = DimenParser.parse(context, source, typesetter);
        Box box = source.getBox(null, context, typesetter, insert);
        if (box != null && !box.isVoid()) {
            move.add(box.getMove());
            box.setMove(move);
        }
        return box;
    }

}
