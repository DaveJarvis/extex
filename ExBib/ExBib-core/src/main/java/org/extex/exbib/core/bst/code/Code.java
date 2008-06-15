/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configurable;

/**
 * This is the interface for pieces of code to be executed by the processor.
 * This can be either some kind of built-in code which maps to a Java method, or
 * it is macro code which has to to be interpreted.
 * <p>
 * The code has a name which is the {@link String String} under which it is
 * stored in the processor context, i.e. either the name of the function or the
 * name of the macro.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public interface Code extends Configurable {

    /**
     * Execute the given code in the context of a processor and optionally an
     * entry.
     * <p>
     * The parameters for the execution are taken from the stack of the
     * processor. The result is stored on the processor stack.
     * </p>
     * 
     * @param processor the processor context
     * @param entry the entry context or null
     * @param locator the locator
     * 
     * @throws ExBibException in case of an error.
     */
    void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException;

    /**
     * Getter for the name
     * 
     * @return the name
     */
    String getName();

    /**
     * Setter for the name
     * 
     * @param name the new name
     */
    void setName(String name);

    /**
     * Compute the string representation of the code. This representation is not
     * meant to be read in again but purely for informational purposes.
     * 
     * @return the {@link String String} representation
     */
    String toString();

}
