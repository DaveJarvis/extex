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

package org.extex.interpreter.primitives.register.box;

import java.io.Serializable;

import javax.xml.transform.Source;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.typesetter.Typesetter;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive <code>\box</code>.
 *
 * <doc name="box">
 * <h3>The Primitive <tt>\box</tt></h3>
 * <p>
 *  The primitive <tt>\box</tt> inserts the contents of the named box register
 *  at the current position. In addition the box register is cleared.
 * </p>
 * <p>
 *  If the primitive is used on the right hand side of a box assignment then
 *  the box is cleared and the former contents is used for the assignment.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;box&rang;
 *      &rarr; <tt>\box</tt> {@linkplain
 *        org.extex.interpreter.primitives.register.box.AbstractBox#getKey(Context,Source,Typesetter,String)
 *        &lang;box register name&rang;} </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \box42  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class BoxPrimitive extends AbstractBox implements Boxable, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public BoxPrimitive(final String name) {

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

        String key = getKey(context, source, typesetter, getName());
        Box box = context.getBox(key);
        if (box != null) {
            try {
                typesetter.add(box.getNodes());
            } catch (ConfigurationException e) {
                throw new InterpreterException(e);
            }
            box.clear();
        }
    }

    /**
     * @see org.extex.interpreter.type.box.Boxable#getBox(
     *       org.extex.interpreter.context.Context,
     *       org.extex.interpreter.TokenSource,
     *       org.extex.typesetter.Typesetter)
     */
    public Box getBox(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        String key = getKey(context, source, typesetter, getName());
        Box b = context.getBox(key);
        Box box = new Box(b);
        if (b != null) {
            b.clear();
        }
        return box;
    }

}
