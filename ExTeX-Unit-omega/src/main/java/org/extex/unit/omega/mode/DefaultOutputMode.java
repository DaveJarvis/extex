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

package org.extex.unit.omega.mode;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.OmegaExtension;

/**
 * This class provides an implementation for the primitive
 * <code>\DefaultOutputMode</code>.
 * 
 * <doc name="DefaultOutputMode">
 * <h3>The Primitive <tt>\DefaultOutputMode</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;DefaultOutputMode&rang;
 *      &rarr; <tt>\DefaultOutputMode</tt> &lang;mode&rang;
 *
 *    &lang;mode&rang;
 *      &rarr; <tt>onebyte</tt>
 *       |  <tt>ebcdic</tt>
 *       |  <tt>twobyte</tt>
 *       |  <tt>twobyteLE</tt>     </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 * \DefaultOutputMode onebyte </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4411 $
 */
public class DefaultOutputMode extends AbstractModeCode {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public DefaultOutputMode(String name) {

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
            Typesetter typesetter) throws TypesetterException, HelpingException {

        OmegaMode mode = scanOutputMode(context, source);
        context.set(OmegaExtension.NAME, DEFAULT_OUTPUT_MODE, mode, prefix
            .clearGlobal());
    }

}
