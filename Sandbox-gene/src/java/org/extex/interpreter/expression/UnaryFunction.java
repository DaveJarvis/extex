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

package org.extex.interpreter.expression;

import org.extex.interpreter.exception.InterpreterException;

/**
 * This interface describes an operation object with a single argument.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4733 $
 */
public interface UnaryFunction {

    /**
     * Compute the function value for the argument stored in the accumulator
     * and return in the accumulator overwriting the value stored therein.
     *
     * @param accumulator the accumulator to receive the result
     *
     * @return the operation result
     *
     * @throws InterpreterException in case of an error
     */
    EType apply(EType accumulator) throws InterpreterException;

}
