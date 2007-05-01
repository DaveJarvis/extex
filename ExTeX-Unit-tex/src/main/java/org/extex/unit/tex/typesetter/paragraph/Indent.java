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

package org.extex.unit.tex.typesetter.paragraph;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * This class provides an implementation for the primitive <code>\indent</code>.
 *
 * <doc name="indent">
 * <h3>The Primitive <tt>\indent</tt></h3>
 * <p>
 *  The primitive <tt>\indent</tt> ensures that the further processing appears
 *  in horizontal mode and inserts horizontal spacing in the width of
 *  the dimen register <tt>\parindent</tt>.
 * </p>
 * <p>
 *  Note that the spacing is inserted in any case. Thus several successive
 *  invocations lead to more spacing. This can even happen in the middle of a
 *  paragraph.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;indent&rang;
 *       &rarr; <tt>\indent</tt>  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \indent  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Indent extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060402L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Indent(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
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

        typesetter.ensureHorizontalMode(source.getLocator());
        typesetter.add(new HorizontalListNode(context.getDimen("parindent")));
    }

}
