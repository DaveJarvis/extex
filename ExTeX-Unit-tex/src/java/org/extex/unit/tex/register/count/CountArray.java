/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

import org.extex.core.count.CountParser;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an array of count values. The index is a number.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class CountArray extends CountPrimitive {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 20060512L;

    /**
     * Creates a new object.
     *
     * @param name the name of the primitive
     */
    public CountArray(String name) {

        super(name);
    }

    /**
     * Return the key (the name of the primitive) for the numbered count
     * register.
     *
     * @param context the interpreter context to use
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the key for the current register
     *
     * @throws InterpreterException in case that a derived class need to throw
     *  an Exception this one is declared.
     *
     * @see org.extex.unit.tex.register.count.AbstractCount#getKey(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        long no = CountParser.scanInteger(context, source, typesetter);
        return getName() + Long.toString(no);
    }

}
