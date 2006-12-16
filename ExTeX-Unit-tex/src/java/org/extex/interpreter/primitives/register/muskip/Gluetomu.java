/*
 * Copyright (C) 2006 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.primitives.register.muskip;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.CantUseInException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.glue.Glue;
import org.extex.interpreter.type.muskip.Muskip;
import org.extex.interpreter.type.muskip.MuskipConvertible;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\gluetomu</code>.
 *
 * <doc name="gluetomu">
 * <h3>The Primitive <tt>\gluetomu</tt></h3>
 * <p>
 *  The primitive <tt>\gluetomu</tt> converts a glue specification to a muglue
 *  specification. For this conversion 1mu=1pt is assumed. This primitive can be
 *  used wherever a muskip is expected.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;gluetomu&rang;
 *      &rarr; <tt>\gluetomu</tt> &lang;glue&rang;  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \muskip0=\gluetomu1pt  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Gluetomu extends AbstractCode implements MuskipConvertible {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060513L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Gluetomu(final String name) {

        super(name);
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

        Glue glue = Glue.parse(source, context, typesetter);

        return new Muskip(glue.getLength(), glue.getStretch(), glue.getShrink());
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

        throw new CantUseInException(printableControlSequence(context),
                typesetter.getMode().toString());
    }

}
