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

package org.extex.unit.tex.group;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\endgroup</code>.
 * 
 * <doc name="endgroup">
 * <h3>The Primitive <tt>\endgroup</tt></h3>
 * <p>
 * The primitive <tt>\endgroup</tt> closes the current group all properties
 * are reset to the values they had before the group had been entered. A group
 * is usually opened with
 * {@link org.extex.unit.tex.group.Begingroup <tt>\begingroup</tt>}.
 * </p>
 * <p>
 * If no group has been opened then an error is raised.
 * </p>
 * 
 * <h4>Syntax</h4>
 * <p>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;endgroup&rang;
 *      &rarr; <tt>\endgroup</tt>  </pre>
 * 
 * </p>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \begingroup 123 \endgroup  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Endgroup extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Endgroup(CodeToken token) {

        super(token);
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
            Typesetter typesetter) throws HelpingException {

        context.closeGroup(typesetter, source);
    }

}
