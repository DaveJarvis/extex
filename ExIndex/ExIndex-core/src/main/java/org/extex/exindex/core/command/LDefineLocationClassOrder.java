/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exindex.core.command;

import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define the location class order.
 * 
*
 * <p>The Command {@code define-location-class-order}</p>
 * 
 * <p>
 * The command {@code define-location-class-order} can be used to define the
 * order of the location classes.
 * </p>
 * 
 * <pre>
 *  (define-location-class-order
 *     <i>location-class-list</i>)   </pre>
 * 
 * <p>
 * The command has an argument which is a list of location class names. The
 * location classes need to be defined before the order can be specified.
 * </p>
 * 
 * <pre>
 *  (define-location-class-order ("pages" "appendix"))   </pre>
 * 
 * <p>
 * The ordering is determined by the order of the location class names in the
 * argument list. Each element must be a defined location class name. Doubles
 * are not allowed.
 * </p>
 * 
 * <p>
 * If location classes are not mentioned in the list then the order is
 * undefined. Thus all location classes should be specified if an order is
 * requested.
 * </p>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LDefineLocationClassOrder extends LFunction {

    /**
     * The field {@code container} contains the container to store the order
     * in.
     */
    private final LocationClassContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container to store the order in
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineLocationClassOrder(String name,
            LocationClassContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QLIST});
        this.container = container;
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param list the list of location classes determining the order
     * 
     * @return {@code nil}
     * 
     * @throws LException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, LList list)
            throws LException {

        String[] classes = new String[list.size()];
        int i = 0;
        for (LValue value : list) {
            classes[i++] = LString.stringValue(value);
        }
        container.orderLocationClasses(classes);

        return LList.NIL;
    }

}
