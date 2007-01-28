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

package org.extex.unit.tex.register.count;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive <code>\badness</code>.
 *
 * <doc name="badness">
 * <h3>The Primitive <tt>\badness</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * <pre class="syntax">
 *   &lang;badness&rang;
 *     &rarr; <tt>\badness</tt> {@linkplain
 *       org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *       &lang;equals&rang;} {@linkplain
 *       org.extex.interpreter.TokenSource#scanNumber(Context)
 *       &lang;number&rang;} </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \badness=999  </pre>
 * <pre class="TeXSample">
 *   \count1=\badness  </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Badness extends AbstractCode implements CountConvertible, Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Badness(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertCount(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        String key = getName();
        Count c = context.getCount(key);
        return (c != null ? c.getValue() : 0);
    }

    /**
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        String key = getName();
        return new Tokens(context, context.getCount(key).toString());
    }

}
