/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.latexParser.api;

import java.io.PrintStream;

/**
 * This is a top level interface where implementing class can put in
 * functionality.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface Node {

    /**
     * Getter for the line number.
     * 
     * @return the line number
     */
    int getLineNumber();

    /**
     * Getter for the source.
     * 
     * @return the source
     */
    String getSource();

    /**
     * Print the node to a given stream.
     * 
     * @param stream the output stream
     */
    void print(PrintStream stream);
}
