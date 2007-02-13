/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.muskip;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.muskip.Muskip;
import org.extex.interpreter.type.muskip.MuskipConvertible;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\muexpr</code>.
 *
 * <doc name="muexpr">
 * <h3>The Primitive <tt>\muexpr</tt></h3>
 * <p>
 *  The primitive <tt>\muexpr</tt> ...
 * </p>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;muexpr&rang;
 *      &rarr; <tt>\muexpr</tt> </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \muexpr  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Muexpr extends AbstractCode implements MuskipConvertible {

    /**
     * The field <tt>serialVersionUID</tt> contains the ...
     */
    private static final long serialVersionUID = 20060617L;

    /**
     * Creates a new object.
     *
     * @param codeName the name
     */
    public Muexpr(final String codeName) {

        super(codeName);
    }

    /**
     * @see org.extex.interpreter.type.muskip.MuskipConvertible#convertMuskip(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Muskip convertMuskip(final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        //TODO gene: unimplemented
        throw new RuntimeException("unimplemented");
    }

}
