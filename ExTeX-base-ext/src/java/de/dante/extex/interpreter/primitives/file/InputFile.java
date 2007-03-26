/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.file;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.unit.tex.file.Input;

/**
 * This class provides an implementation for the primitive
 * <code>\inputfile</code>.
 * The filename can have space in his name.
 *
 * Example:
 * <pre>
 * \inputfile{file.name}
 * </pre>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class InputFile extends Input {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public InputFile(String name) {

        super(name);
    }

    /**
     * scan the filename between <code>{</code> and <code>}</code>.
     * @param context   the context
     * @param source    the source for new tokens
     *
     * @return the file name as string
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.unit.base.file.AbstractFileCode#scanFileName(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource)
     */
    protected String scanFileName(Context context,
            TokenSource source) throws InterpreterException {

        return source.scanTokensAsString(context, getName());
    }
}