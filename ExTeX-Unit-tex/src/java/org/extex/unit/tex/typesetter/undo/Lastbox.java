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

package org.extex.unit.tex.typesetter.undo;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;

/**
 * This class provides an implementation for the primitive <code>\lastbox</code>.
 *
 * <doc name="lastbox">
 * <h3>The Primitive <tt>\lastbox</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;lastbox&rang;
 *    &rarr; <tt>\lastbox</tt>  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \lastbox  </pre>
 *  <pre class="TeXSample">
 *    \box1=\lastbox  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Lastbox extends AbstractCode implements Boxable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Lastbox(final String name) {

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
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        Mode mode = typesetter.getMode();
        if (mode.isMath() || mode == Mode.VERTICAL) {
            throw new HelpingException(getLocalizer(), "TTP.LastBoxIn", //
                context.esc(getName()), mode.toString());
        }

        //TODO gene: what's to do?
        //throw new RuntimeException("unimplemented");
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
     * @see org.extex.interpreter.type.box.Boxable#getBox(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter, Token)
     */
    public Box getBox(final Context context, final TokenSource source,
            final Typesetter typesetter, final Token insert)
            throws InterpreterException {

//        Mode mode = typesetter.getMode();
//        if (mode.isMath() || mode == Mode.VERTICAL) {
//            throw new HelpingException(getLocalizer(), "TTP.LastBoxIn", //
//                context.esc(getName()), mode.toString());
//        }

        Node nodes = typesetter.getLastNode();
        Box box = null;

        if (nodes instanceof NodeList) {
            typesetter.removeLastNode();
            box = new Box((NodeList) nodes);
        }

        if (insert != null) {
            source.push(insert);
        }

        return box;
    }
}
