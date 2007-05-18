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

package org.extex.unit.tex.typesetter.spacing;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.ExplicitKernNode;

/**
 * This class provides an implementation for the primitive <code>\kern</code>.
 * 
 * <doc name="kern">
 * <h3>The Primitive <tt>\kern</tt></h3>
 * <p>
 * This primitive produces a horizontal or vertical kerning. This is a (minor)
 * adjustment of the position. The meaning depends on the current mode of the
 * typesetter. In vertical modes it means a vertical adjustment. Otherwise it
 * means a horizontal adjustment.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;kern&rang;
 *      &rarr; <tt>\kern</tt> {@linkplain org.extex.core.dimen#Dimen(Context,TokenSource)
 *      &lang;dimen&rang;}   </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \kern 12pt  </pre>
 *  <pre class="TeXSample">
 *    \kern -3mm  </pre>
 *  <pre class="TeXSample">
 *    \kern -\dimen123  </pre>
 * 
 * </doc>
 * 
 * <p>
 * The effect of the primitive is that a
 * {@link org.extex.typesetter.type.node.KernNode KernNode} is is sent to the
 * typesetter.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Kern extends AbstractCode {

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
    public Kern(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Dimen kern = source.parseDimen(context, source, typesetter);
        typesetter.add(new ExplicitKernNode(kern, true));
    }

}
