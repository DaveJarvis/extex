/*
 * Copyright (C) 2003-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.group;

import de.dante.extex.interpreter.Flags;
import de.dante.extex.interpreter.TokenSource;
import de.dante.extex.interpreter.context.Context;
import de.dante.extex.interpreter.exception.helping.EofException;
import de.dante.extex.interpreter.type.AbstractCode;
import de.dante.extex.scanner.type.Token;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.GeneralException;

/**
 * This class provides an implementation for the primitive
 * <code>\aftergroup</code>.
 *
 * <doc name="aftergroup">
 * <h3>The Primitive <tt>\aftergroup</tt></h3>
 * <p>
 *  This primitive takes the next token and saves it. The saved token will be
 *  inserted after the current group has been closed. If several tokens are
 *  saved then they will be inserted in the same sequence as they are saved.
 * </p>
 * <p>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *   &lang;aftergroup&rang;
 *     &rarr; <tt>\aftergroup</tt> {@linkplain
 *       de.dante.extex.interpreter.TokenSource#getToken()
 *       &lang;token&rang;}   </pre>
 * </p>
 * <p>
 *  Examples:
 *  <pre class="TeXSample">
 *    {\aftergroup~ xyz}  </pre>
 *  <pre class="TeXSample">
 *    {\aftergroup\a\aftergroup\b xyz}  </pre>
 * </p>
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Aftergroup extends AbstractCode {

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Aftergroup(final String name) {

        super(name);
    }

    /**
     * @see de.dante.extex.interpreter.type.Code#execute(
     *      de.dante.extex.interpreter.Flags,
     *      de.dante.extex.interpreter.context.Context,
     *      de.dante.extex.interpreter.TokenSource,
     *      de.dante.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws GeneralException {

        Token t = source.getToken(context);
        if (t == null) {
            throw new EofException(printableControlSequence(context));
        }
        context.afterGroup(t);
    }

}