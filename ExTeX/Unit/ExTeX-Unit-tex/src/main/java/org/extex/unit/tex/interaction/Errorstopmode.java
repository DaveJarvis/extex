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

package org.extex.unit.tex.interaction;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\errorstopmode</code>. It does simply nothing, but as a side
 * effect all prefixes are zeroed and the interaction mode is set to
 * <tt>errorstopmode</tt>.
 *
 * <doc name="errorstopmode">
 * <h3>The Primitive <tt>\errorstopmode</tt></h3>
 * <p>
 *  This primitive sets the interaction mode to error stop mode.
 *  In error stop mode the processing is interrupted and the error handler is
 *  invoked when an error occurs.
 * </p>
 * <p>
 *  The setting of the interaction mode is an assignment. The mode is always
 *  processed globally. This means it does not interact with the group concept.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;errorstopmode&rang;
 *      &rarr; <tt>\errorstopmode</tt>  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \errorstopmode  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4408 $
 */
public class Errorstopmode extends AbstractAssignment {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     *
     * @param token the initial token for the primitive
     */
    public Errorstopmode(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        context.setInteraction(Interaction.ERRORSTOPMODE);
    }

}