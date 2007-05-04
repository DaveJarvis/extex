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

package org.extex.unit.tex.prefix;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive <code>\global</code>.
 * It does simply nothing, but as a side effect the prefix <i>GLOBAL</i> is
 * added to the prefixes.
 * 
 * <doc name="global">
 * <h3>The Prefix Primitive <tt>\global</tt></h3>
 * <p>
 * The primitive <tt>\global</tt> is a prefix macro. It does not do anything
 * by its own but works in combination with a following primitive token only. If
 * the following token constitutes an assignment then the assignment is not
 * restricted to the current group but acts globally in all groups.
 * </p>
 * <p>
 * If the following command token does not happen to be an operation for which
 * the global modifier is applicable then a warning might be raised.
 * </p>
 * <p>
 * Multiple <tt>\global</tt> prefixes act identical to a single one.
 * </p>
 * 
 * <h4>Syntax</h4>
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 * 
 * <pre class="syntax">
 *   &lang;global&rang;
 *     &rarr; <tt>\global</tt>  </pre>
 * 
 * <h4>Examples</h4>
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
 * first count register keeps its value untile the group is closed and falls
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
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Global extends AbstractCode implements PrefixCode {

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
    public Global(String name) {

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
            Typesetter typesetter) {

        prefix.setGlobal();
    }

}
