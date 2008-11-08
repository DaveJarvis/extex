/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst;

import java.util.List;

import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.exceptions.ExBibFunctionExistsException;
import org.extex.exbib.core.exceptions.ExBibFunctionUndefinedException;
import org.extex.exbib.core.io.Locator;

/**
 * This interface describes a container for functions.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface FunctionContainer {

    /**
     * Define a new function if not already defined in the processor context. If
     * the function has been defined already then a exception is thrown. The add
     * function observers are informed about the new function.
     * 
     * @param name name of the function; it can not be null or empty
     * @param body code to be executed in case it is called
     * @param locator the locator
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
     */
    void addFunction(String name, Code body, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException;

    /**
     * Change the meaning of a function definition. If the function does not
     * exist then an exception is thrown.
     * 
     * @param name the name of the function to change
     * @param body the new code for the function
     * @param locator the locator
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionUndefinedException in case that the function isn't
     *         defined yet
     */
    void changeFunction(String name, Code body, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionUndefinedException;

    /**
     * Getter for function code.
     * 
     * @param name the name of the function to retrieve
     * 
     * @return the code for the function or null if none is found
     */
    Code getFunction(String name);

    /**
     * Getter for the function names.
     * 
     * @return the list of function names
     */
    List<String> getFunctionNames();

}
