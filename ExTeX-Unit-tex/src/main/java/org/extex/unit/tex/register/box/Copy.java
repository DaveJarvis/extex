/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.unit.tex.register.box;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive <code>\copy</code>.
 *
 * <doc name="copy">
 * <h3>The Primitive <tt>\copy</tt></h3>
 * <p>
 *  The primitive <tt>\copy</tt> inserts the contents of the named box register
 *  at the current position. In contrast to the
 *  {@link org.extex.unit.tex.register.box.BoxPrimitive <tt>\box</tt>}
 *  primitive the box register is not cleared.
 * </p>
 * <p>
 *  If the primitive is used on the right hand side of a box assignment then
 *  the box contents is used for the assignment.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;copy&rang;
 *      &rarr; <tt>\copy</tt> {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context,TokenSource,Typesetter,String)
 *        &lang;box register name&rang;} </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \copy42  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Copy extends AbstractCode implements Boxable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 16022007L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Copy(String name) {

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
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException,
                ConfigurationException {

        String key = Setbox.getKey(context, source, typesetter, getName());
        Box box = context.getBox(key);
        if (box != null) {
            typesetter.add(box.getNodes());
        }
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

        String key = Setbox.getKey(context, source, typesetter, getName());
        Box b = context.getBox(key);
        if (insert != null) {
            source.push(insert);
        }
        return new Box(b);
    }

}
