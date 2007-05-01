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

package org.extex.unit.tex.typesetter.mark;

import org.extex.interpreter.context.Context;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This class provides an implementation for the primitive
 * <code>\splitfirstmarks</code>.
 *
 * <doc name="splitfirstmarks">
 * <h3>The Primitive <tt>\splitfirstmarks</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;splitfirstmarks&rang;
 *      &rarr; <tt>\splitfirstmarks</tt> {@linkplain
 *        org.extex.unit.tex.typesetter.mark.AbstractMarksCode#getKey(Context,TokenSource,Typesetter)
 *        &lang;mark name&rang;}  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \splitfirstmarks42  </pre>
 *  <pre class="TeXSample">
 *    \splitfirstmarks\count0  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Splitfirstmarks extends AbstractMarksCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Splitfirstmarks(String name) {

        super(name);
    }

    /**
     * Get the value for this mark.
     *
     * @param context the interpreter context
     * @param key the key
     *
     * @return the value
     *
     * @see org.extex.unit.tex.typesetter.mark.AbstractMarksCode#getValue(
     *      org.extex.interpreter.context.Context, java.lang.String)
     */
    protected Tokens getValue(Context context, String key) {

        return context.getSplitBottomMark(key);
    }

}
