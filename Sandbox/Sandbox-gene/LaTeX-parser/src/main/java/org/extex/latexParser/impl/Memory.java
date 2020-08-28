/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.latexParser.impl;

import java.io.IOException;

import org.extex.scanner.api.exception.ScannerException;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision$
 */
public interface Memory {

    /**
     * Define an active character.
     * 
     * @param c the letter
     * @param macro the code
     */
    void def(char c, Macro macro);

    /**
     * Define a macro.
     * 
     * @param name the name without leading \
     * @param macro the code
     */
    void def(String name, Macro macro);

    /**
     * Getter for the definition of an active character.
     * 
     * @param c the character
     * 
     * @return the definition or <code>null</code>
     */
    Macro getDefinition(char c);

    /**
     * Getter for macros definition.
     * 
     * @param name the name of the macro
     * 
     * @return the definition or <code>null</code>
     */
    Macro getDefinition(String name);

    /**
     * Check whether an active character is already defined.
     * 
     * @param c the active character
     * 
     * @return <code>true</code> iff the active character is defined
     */
    boolean isDefined(char c);

    /**
     * Check whether a macro is already defined.
     * 
     * @param name the name of the macro
     * 
     * @return <code>true</code> iff the macro is defined
     */
    boolean isDefined(String name);

    /**
     * Load some definitions from an external resource.
     * 
     * @param name the name of the resource
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error
     */
    void load(String name) throws IOException, ScannerException;

}
