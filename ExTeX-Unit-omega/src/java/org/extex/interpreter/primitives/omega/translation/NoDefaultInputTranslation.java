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

package org.extex.interpreter.primitives.omega.translation;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.primitives.omega.OmegaExtension;
import org.extex.interpreter.primitives.omega.mode.AbstractModeCode;
import org.extex.interpreter.primitives.omega.mode.OmegaMode;
import org.extex.typesetter.Typesetter;


/**
 * This class provides an implementation for the primitive
 * <code>\noDefaultInputTranslation</code>.
 *
 * <doc name="noDefaultInputTranslation">
 * <h3>The Primitive <tt>\noDefaultInputTranslation</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;noDefaultInputTranslation&rang;
 *      &rarr; <tt>\noDefaultInputTranslation</tt> &lang;mode&rang;
 *
 *    &lang;mode&rang;
 *      &rarr; <tt>onebyte</tt>
 *        |  <tt>ebcdic</tt>
 *        |  <tt>twobyte</tt>
 *        |  <tt>twobyteLE</tt>    </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \noDefaultInputTranslation onebyte </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4411 $
 */
public class NoDefaultInputTranslation extends AbstractModeCode {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2006L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public NoDefaultInputTranslation(final String name) {

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

        OmegaMode mode = scanInputMode(context, source);

        context.set(OmegaExtension.NAME, //
                DEFAULT_INPUT_TRANSLATION + mode.toString(), //
                null, prefix.clearGlobal());
    }
}
