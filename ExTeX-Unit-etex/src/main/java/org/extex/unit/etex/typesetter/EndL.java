/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.typesetter;

import org.extex.core.count.Count;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.Direction;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.ExtensionDisabledException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\endL</code>.
 *
 * <doc name="endL">
 * <h3>The Primitive <tt>\endL</tt></h3>
 * <p>
 *  The primitive <tt>\endL</tt> indicates that the following text is typeset
 *  in the way it was before the matching
 *  {@link org.extex.unit.etex.typesetter.BeginL <tt>\beginL</tt>}
 *  has been encountered.
 * </p>
 * <p>
 *  This primitive is deactivated unless the count register <tt>\TeXXeTstate</tt>
 *  has a positive value.
 * </p>
 *
 * <h4>Syntax</h4>
 *  <pre class="syntax">
 *    &lang;endL&rang;
 *     &rarr; <tt>\endL</tt> </pre>
 *
 * <h4>Example:</h4>
 *  <pre class="TeXSample">
 *   \beginL ... \endL  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class EndL extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public EndL(String name) {

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

        if (context.getCount("TeXXeTstate").le(Count.ZERO)) {
            throw new ExtensionDisabledException(
                printableControlSequence(context));
        }
        Direction dir = context.popDirection();
        if (dir == null) {
            throw new HelpingException(getLocalizer(), "Problem");
        }
        context.set(dir, false);
    }

}