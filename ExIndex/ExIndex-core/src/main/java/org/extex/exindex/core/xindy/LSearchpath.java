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

package org.extex.exindex.core.xindy;

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a merge rule.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LSearchpath extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LSearchpath(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QVALUE});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param value the value
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, LValue value)
            throws LNonMatchingTypeException {

        if (value instanceof LString) {
            store(interpreter, ((LString) value).getValue());
        } else if (value instanceof LList) {
            store(interpreter, ((LList) value));
        } else {
            throw new LNonMatchingTypeException("");
        }

        return LList.NIL;
    }

    /**
     * Store the new search path.
     * 
     * @param interpreter the interpreter
     * @param list
     */
    private void store(LInterpreter interpreter, LList list) {

        // TODO gene: store unimplemented

    }

    /**
     * Store the new search path.
     * 
     * @param interpreter the interpreter
     * @param value
     */
    private void store(LInterpreter interpreter, String value) {

        if (value.endsWith(":")) {

        }
        // TODO gene: store unimplemented
    }

}
