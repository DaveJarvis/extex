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
import java.util.ArrayList;

/**
 * This class represents a list of nodes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NodeList extends ArrayList<Node> implements Node {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field {@code source} contains the source the tokens are read from.
     */
    private String source;

    /**
     * The field {@code line} contains the line number of the start.
     */
    private int line;


    public NodeList() {

        super();
        this.source = "";
        this.line = -1;
    }

    /**
     * Creates a new object.
     * 
     * @param source the source of the nodes
     * @param line the line number
     */
    public NodeList(String source, int line) {

        super();
        this.source = source;
        this.line = line;
    }

public int getLineNumber() {

        return line;
    }

    /**
     * Getter for source.
     * 
     * @return the source
     */
    public String getSource() {

        return source;
    }

public void print(PrintStream stream) {

        for (Node n : this) {
            n.print(stream);
        }
    }

@Override
    public String toString() {

        return toString(new StringBuilder()).toString();
    }

    /**
     * Print the contents into a buffer.
     * 
     * @param sb the target
     * 
     * @return the string builder
     */
    public StringBuilder toString(StringBuilder sb) {

        for (Node n : this) {
            sb.append(n.toString());
        }
        return sb;
    }

}
