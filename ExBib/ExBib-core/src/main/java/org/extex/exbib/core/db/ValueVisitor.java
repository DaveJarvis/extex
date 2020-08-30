/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db;

import java.io.IOException;

/**
 * This interface provides methods for the different possible subtypes of a
 * {@link Value Value}. This interface is used as part of the visitor pattern.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface ValueVisitor {

    /**
     * This is a method which is invoked when a block is visited.
     * 
     * @param value the block encountered
     * @param db the database context
     * 
     * @throws IOException just in case
     */
    void visitBlock(VBlock value, DB db) throws IOException;

    /**
     * This is a method which is invoked when a macro is visited.
     * 
     * @param value the macro encountered
     * @param db the database context
     * 
     * @throws IOException just in case
     */
    void visitMacro(VMacro value, DB db) throws IOException;

    /**
     * This is a method which is invoked when a number is visited.
     * 
     * @param value the number encountered
     * @param db the database context
     * 
     * @throws IOException just in case
     */
    void visitNumber(VNumber value, DB db) throws IOException;

    /**
     * This is a method which is invoked when a string is visited.
     * 
     * @param value the string encountered
     * @param db the database context
     * 
     * @throws IOException just in case
     */
    void visitString(VString value, DB db) throws IOException;

    /**
     * This is a method which is invoked when a value is visited.
     * 
     * @param value the value encountered
     * @param db the database context
     * 
     * @throws IOException just in case
     */
    void visitValue(Value value, DB db) throws IOException;

}
