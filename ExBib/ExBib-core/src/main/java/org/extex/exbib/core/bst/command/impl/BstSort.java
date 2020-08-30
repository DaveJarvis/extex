/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.command.impl;

import java.io.IOException;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.command.AbstractCommand;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a {@code SORT} command.
 * <p>
 * The {@code sort} command sorts the entries of the database according to its
 * sort key lexicographically increasing. The sort key is taken from the entry
 * variable {@code sort.key\$}.
 * </p>
 * 
 * <pre>
 *   SORT
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BstSort extends AbstractCommand {

    /**
     * Creates a new object.
     * 
     * @param locator the locator
     */
    public BstSort(Locator locator) {

        super(null, locator);
    }

    /**
*      org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Locator locator)
            throws ExBibException {

        processor.getDB().sort();
    }

    /**
     * Compute a printable string representation for this object.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {

        return "SORT";
    }

    /**
*      java.lang.Object[])
     */
    public void visit(CommandVisitor visitor, Object... args)
            throws IOException,
                ExBibException {

        visitor.visitSort(this, args);
    }

}
