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

package org.extex.exbib.core.bst.command;

import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.exceptions.ExBibException;

/**
 * This interface implements the visitor pattern for commands. The visitor
 * pattern shows how to avoid explicit switches over the class of an object.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface CommandVisitor extends TokenVisitor {

    /**
     * Visitor method invoked by {@code execute} commands.
     * 
     * @param command the {@code Execute} Command which is visited
     * @param args the arguments
     * 
     * @throws ExBibException in case of an error
     */
    void visitExecute(Command command, Object... args) throws ExBibException;

    /**
     * Visitor method invoked by {@code iterate} commands.
     * 
     * @param command the {@code Iterate} Command which is visited
     * @param args the arguments
     * 
     * @throws ExBibException in case of an error
     */
    void visitIterate(Command command, Object... args) throws ExBibException;

    /**
     * Visitor method invoked by {@code read} commands.
     * 
     * @param command the {@code Read} Command which is visited
     * @param args the arguments
     * 
     * @throws ExBibException in case of an error
     */
    void visitRead(Command command, Object... args) throws ExBibException;

    /**
     * Visitor method invoked by {@code reverse} commands.
     * 
     * @param command the {@code Reverse} Command which is visited
     * @param args the arguments
     * 
     * @throws ExBibException in case of an error
     */
    void visitReverse(Command command, Object... args) throws ExBibException;

    /**
     * Visitor method invoked by {@code sort} commands.
     * 
     * @param command the {@code Sort} Command which is visited
     * @param args the arguments
     * 
     * @throws ExBibException in case of an error
     */
    void visitSort(Command command, Object... args) throws ExBibException;

}
