/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.command;

import java.io.IOException;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This interface represents the top-level executable Commands of a BST file.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public interface Command {

    /**
     * The commands actions are performed.
     * 
     * @param processor the processor context
     * @param locator the locator
     * 
     * @throws ExBibException in case that something goes wrong
     */
    void execute(BstProcessor processor, Locator locator) throws ExBibException;

    /**
     * Getter for the locator of the command. Each command may have associated
     * an locator pointing to the source where this command has been read from.
     * 
     * @return the locator or <code>null</code>
     */
    Locator getLocator();

    /**
     * Getter for the Token value of the Command. Each Command may encapsulate a
     * Token value. This method provides access to it. If a Command does not
     * have a value then <code>null</code> is returned.
     * 
     * @return the Token value or <code>null</code>
     */
    Token getValue();

    /**
     * Return the string representation of this command.
     * 
     * @return the string representation
     */
    String toString();

    /**
     * The command is visited by the means of this method. This method is used
     * as part of the visitor pattern.
     * 
     * @param visitor the visitor
     * @param args the additional arguments
     * 
     * @throws IOException in case of an I/O error
     */
    void visit(CommandVisitor visitor, Object... args) throws IOException;

}
