/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.context;

import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.file.OutFile;

/**
 * This interface describes the container for all data of an interpreter
 * context.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface ContextFile {

    /**
     * Getter for a input file register. In the case that the named descriptor
     * doe not exist yet a new one is returned. Especially if the name is
     * {@code null} then the default input stream is used.
     * 
     * @param name the name or the number of the file register
     * 
     * @return the input file descriptor
     * 
     * @see #setInFile(String, InFile, boolean)
     */
    InFile getInFile(String name);

    /**
     * Getter for an output file descriptor.
     * 
     * @param name the name or the number of the file register
     * 
     * @return the output file descriptor
     * 
     * @see #setOutFile(String, OutFile, boolean)
     */
    OutFile getOutFile(String name);

    /**
     * Setter for the {@link org.extex.scanner.type.file.InFile InFile} register
     * in all requested groups. InFile registers are named, either with a number
     * or an arbitrary string. The numbered registers where limited to 16 in
     * TeX. This restriction does no longer hold for
     * εχTeX.
     * 
     * @param name the name or the number of the file register
     * @param file the input file descriptor
     * @param global the indicator for the scope; {@code true} means all
     *        groups; otherwise the current group is affected only
     * 
     * @see #getInFile(String)
     */
    void setInFile(String name, InFile file, boolean global);

    /**
     * Setter for a outfile descriptor.
     * 
     * @param name the name or the number of the file register
     * @param file the descriptor of the output file
     * @param global the indicator for the scope; {@code true} means all
     *        groups; otherwise the current group is affected only
     * 
     * @see #getOutFile(String)
     */
    void setOutFile(String name, OutFile file, boolean global);

}
