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

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * Set the binding of a symbol.
 * 
 * <doc command="setq">
 * <h3>The Command <tt>setq</tt></h3>
 * 
 * <p>
 * The command <tt>setq</tt> can be used to assign a value to a symbol.
 * </p>
 * 
 * <pre>
 *  (setq
 *     <i>symbol</i>
 *     <i>value</i>
 *  )  </pre>
 * 
 * <p>
 * The command has as arguments a symbol and a value. The binding of the symbol
 * is set to the value.
 * </p>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6531 $
 */
public class Setq extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public Setq(String name) throws SecurityException, NoSuchMethodException {

        super(name, new Arg[]{Arg.QSYMBOL, Arg.QVALUE});
    }

    /**
     * Apply setq.
     * 
     * @param interpreter the interpreter
     * @param name the quoted name
     * @param value the value
     * 
     * @return the quoted term
     * 
     * @throws LSettingConstantException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, LSymbol name, LValue value)
            throws LSettingConstantException {

        interpreter.setq(name, value);
        return value;
    }
}
