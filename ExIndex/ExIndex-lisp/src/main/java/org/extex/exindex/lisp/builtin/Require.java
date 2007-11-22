/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.lisp.builtin;

import java.io.IOException;

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.LFunction;
import org.extex.exindex.lisp.type.LValue;
import org.extex.exindex.lisp.type.LString;
import org.extex.exindex.lisp.type.LSymbol;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Require implements LFunction {

    /**
     * Creates a new object.
     */
    public Require() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.LFunction#eval(
     *      org.extex.exindex.lisp.LInterpreter,
     *      org.extex.exindex.lisp.type.LValue[])
     */
    public LValue eval(LInterpreter interpreter, LValue... args)
            throws IOException {

        if (args.length != 1) {
            // TODO gene: eval unimplemented
            throw new RuntimeException("unimplemented");
        }

        LValue arg = args[0];
        if (arg instanceof LSymbol) {
            return interpreter.load(((LSymbol) arg).getValue());
        } else if (arg instanceof LString) {
            return interpreter.load(((LString) arg).getValue());
        }

        // TODO gene: enclosing_method unimplemented
        throw new RuntimeException("unimplemented");
    }
}
