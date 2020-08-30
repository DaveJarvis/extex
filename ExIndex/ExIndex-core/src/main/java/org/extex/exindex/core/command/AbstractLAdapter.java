/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.command;

import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;

/**
 * This abstract base class provides common functionality for the adapter to the
 * L system.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AbstractLAdapter extends LFunction {

    /**
     * The field {@code container} contains the container for indices.
     */
    private final IndexContainer container;

    /**
     * Creates a new function object.
     * 
     * @param name the name of the function
     * @param container the index container
     * @param args the argument descriptors
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public AbstractLAdapter(String name, IndexContainer container, Arg[] args)
            throws SecurityException,
                NoSuchMethodException {

        super(name, args);
        this.container = container;
    }

    /**
     * Getter for container.
     * 
     * @return the container
     */
    public IndexContainer getContainer() {

        return container;
    }

    /**
     * Getter for the markup for the current index.
     * 
     * @param interpreter the interpreter
     * 
     * @return the markup
     * 
     * @throws LNonMatchingTypeException in case of an error
     */
    protected Markup getMarkup(LInterpreter interpreter)
            throws LNonMatchingTypeException {

        return container.getMarkup(getName());
    }

    /**
     * Assign a new current index.
     * 
     * @param name the name of the index
     * 
     * @return {@code true} if the index is found and the set is
     *         successful
     */
    protected boolean setCurrentIndex(String name) {

        return container.setCurrentIndex(name);
    }

}
