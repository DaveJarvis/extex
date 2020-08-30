/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \endcsname}.
 * 
 * <p>The Primitive {@code \endcsname}</p>
 * <p>
 * The macro {@code \endcsname} is used in combination with the macro
 * {@link org.extex.unit.tex.macro.Csname {@code \csname}} only. Whenever a
 * {@code \endcsname} is seen alone it must be an error. Thus thus primitive
 * produces an error message in any case.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;endcsname&rang;
 *      &rarr; {@code \endscsname}  </pre>
 * 
 * <p>Examples</p>

 * <p>
 * The following example shows a complicated way to invoke the macro
 * {@code abc}. Here the primitive {@code \endcsname} is legal. It is
 * consumed by the primitive {@code \csname} and not expanded on its own.
 * </p>
 * 
 * <pre class="TeXSample">
 *   \csname abc\endcsname  </pre>
 * 
 *
 * 
 * @see "TTP [1134]"
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Endcsname extends AbstractCode {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Endcsname(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        throw new HelpingException(getLocalizer(), "TTP.ExtraEndcsname",
            toText(context));
    }

}
