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
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * Load a file if not loaded already.
 * 
 * <doc command="require">
 * <h3>The Command <tt>require</tt></h3>
 * 
 * <p>
 * The command <tt>require</tt> can be used to load additional resources.
 * </p>
 * 
 * <pre>
 *  (require
 *     <i>resource-name</i>
 *  )  </pre>
 * 
 * <p>
 * The command has as argument the name of a resource. Thus resource is loaded
 * if it is found. Otherwise an error is raised.
 * </p>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Require extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public Require(String name) throws SecurityException, NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING});
    }

    /**
     * Load the requested files.
     * 
     * @param interpreter the interpreter
     * @param arg the resource name
     * 
     * @return the last item read or <code>nil</code>
     * 
     * @throws LException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String arg)
            throws LException {

        try {
            return interpreter.load(arg);
        } catch (IOException e) {
            throw new LException(e);
        }
    }
}
