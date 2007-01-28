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

package org.extex.unit.base.register.font;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.count.Count;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.noad.util.MathFontParameter;

/**
 * This class provides an implementation for a font stored under a name and a
 * number in the context.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class NumberedFont extends NamedFont {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public NumberedFont(final String name) {

        super(name);
    }

    /**
     * Return the key (the name of the primitive) for the numbered font
     * register.
     *
     * @param context the interpreter context to use
     * @param source the source for new tokens
     *
     * @return the key for the current register
     *
     * @throws InterpreterException in case that a derived class need to throw an
     *  Exception this one is declared.
     *
     * @see org.extex.unit.base.register.font.NamedFont#getKey(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    protected String getKey(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return MathFontParameter.key(context, getName(), //
            Long.toString(Count.scanNumber(context, source, typesetter)));
    }

}
