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
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\immediate</code>. It does simply nothing, but as a side effect the
 * prefix <i>IMMEDIATE</i> is added to the prefixes.
 * 
 * <doc name="immediate">
 * <h3>The Prefix Primitive <tt>\immediate</tt></h3>
 * <p>
 * The primitive <tt>\immediate</tt> is a prefix modifying the operation of a
 * following primitive. If the immediately following token denotes another
 * prefix primitives then the functionality is accumulated. This means that the
 * next non-prefix primitive is modified by any directly preceding prefix
 * primitives.
 * </p>
 * <p>
 * Multiple <tt>\immediate</tt> prefixes act identical to a single one.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;immediate&rang;
 *      &rarr; <tt>\immediate</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \immediate\write1{abc}  </pre>
 *  <pre class="TeXSample">
 *    \immediate\immediate\write1{abc}  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Immediate extends AbstractCode implements PrefixCode {

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
    public Immediate(CodeToken token) {

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
            Typesetter typesetter) {

        prefix.setImmediate();
    }

}
