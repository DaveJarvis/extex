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
import org.extex.core.exception.helping.ExtensionDisabledException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.Direction;

/**
 * This class provides an implementation for the primitive <code>\endR</code>.
 * 
 * <doc name="endR">
 * <h3>The Primitive <tt>\endR</tt></h3>
 * <p>
 * The primitive <tt>\endR</tt> indicates that the following text is typeset
 * in the way it was before the matching
 * {@link org.extex.unit.etex.typesetter.BeginR <tt>\beginR</tt>} has been
 * encountered.
 * </p>
 * <p>
 * This primitive is deactivated unless the count register <tt>\TeXXeTstate</tt>
 * has a positive value.
 * </p>
 * 
 * <h4>Syntax</h4>
 * 
 * <pre class="syntax">
 *    &lang;endR&rang;
 *     &rarr; <tt>\endR</tt> </pre>
 * 
 * <h4>Example:</h4>
 * 
 * <pre class="TeXSample">
 *   \beginR ... \endR  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class EndR extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public EndR(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

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
