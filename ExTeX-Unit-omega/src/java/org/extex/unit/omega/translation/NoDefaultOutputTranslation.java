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

package org.extex.unit.omega.translation;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Typesetter;
import org.extex.unit.omega.OmegaExtension;
import org.extex.unit.omega.mode.AbstractModeCode;
import org.extex.unit.omega.mode.OmegaMode;

/**
 * This class provides an implementation for the primitive
 * <code>\noDefaultOutputTranslation</code>.
 *
 * <doc name="noDefaultOutputTranslation">
 * <h3>The Primitive <tt>\noDefaultOutputTranslation</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;noDefaultOutputTranslation&rang;
 *      &rarr; <tt>\noDefaultOutputTranslation</tt> &lang;mode&rang;
 *
 *    &lang;mode&rang;
 *      &rarr; <tt>onebyte</tt>
 *        |  <tt>ebcdic</tt>
 *        |  <tt>twobyte</tt>
 *        |  <tt>twobyteLE</tt>    </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \noDefaultOutputTranslation onebyte  </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4411 $
 */
public class NoDefaultOutputTranslation extends AbstractModeCode {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public NoDefaultOutputTranslation(String name) {

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
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        OmegaMode mode = scanInputMode(context, source);

        context.set(OmegaExtension.NAME, //
            DEFAULT_OUTPUT_TRANSLATION + mode.toString(), //
            null, prefix.clearGlobal());
    }

}
