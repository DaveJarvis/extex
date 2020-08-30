/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.prefix;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive {@code \global}.
 * It does simply nothing, but as a side effect the prefix <i>GLOBAL</i> is
 * added to the prefixes.
 * 
 * <p>The Prefix Primitive {@code \global}</p>
 * <p>
 * The primitive {@code \global} is a prefix macro. It does not do anything
 * by its own but works in combination with a following primitive token only. If
 * the following token constitutes an assignment then the assignment is not
 * restricted to the current group but acts globally in all groups.
 * </p>
 * <p>
 * If the following command token does not happen to be an operation for which
 * the global modifier is applicable then a warning might be raised.
 * </p>
 * <p>
 * Multiple {@code \global} prefixes act identical to a single one.
 * </p>
 * 
 * <p>Syntax</p>

 * <p>
 * The formal description of this primitive is the following:
 * </p>
 * 
 * <pre class="syntax">
 *   &lang;global&rang;
 *     &rarr; {@code \global}  </pre>
 * 
 * <p>Examples</p>

 * <p>
 * The following example shows that two macros defined in a group. The first
 * macro falls back to its previous binding when the group is closed. The second
 * macro has the same binding in all groups. defined.
 * </p>
 * 
 * <pre class="TeXSample">
 *   \begingroup
 *     \def\a{123}
 *     \global\def\b{123}
 *   \endgroup  </pre>
 * 
 * <p>
 * The following example shows that two count registers are set in a group. The
 * first count register keeps its value until the group is closed and falls
 * back to the value it had when the group has been entered. The second count
 * register keeps its value even when the group is closed.
 * </p>
 * 
 * <pre class="TeXSample">
 *   \begingroup
 *     \count1=123
 *     \global\count2=45
 *   \endgroup  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Global extends AbstractCode implements PrefixCode {

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
    public Global(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) {

        prefix.setGlobal();
    }

}
