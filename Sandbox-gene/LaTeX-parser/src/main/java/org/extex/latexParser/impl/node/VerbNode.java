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

package org.extex.latexParser.impl.node;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.extex.latexParser.api.Node;
import org.extex.scanner.type.token.Token;

/**
 * This class represents a \verb macro invocation.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VerbNode implements Node {

    /**
     * The field <tt>list</tt> contains the contents.
     */
    private List<Token> list = new ArrayList<Token>();

    /**
     * The field <tt>openToken</tt> contains the opening and closing token.
     */
    private Token openToken;

    /**
     * The field <tt>cmd</tt> contains the command.
     */
    private Token cmd;

    /**
     * Creates a new object.
     * 
     * @param cmd the command
     * @param t the token to add
     */
    public VerbNode(Token cmd, Token t) {

        super();
        this.cmd = cmd;
        openToken = t;
    }

    /**
     * Add a token to the list.
     * 
     * @param n the node to add
     * 
     * @return <code>true</code>
     * 
     * @see java.util.List#add(java.lang.Object)
     */
    public boolean add(Token n) {

        return list.add(n);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.api.Node#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.print(cmd.toText());
        stream.print(openToken.toText());
        for (Token n : list) {
            stream.print(n.toText());
        }
        stream.print(openToken.toText());
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(cmd.toText());
        sb.append(openToken.toText());
        for (Token n : list) {
            sb.append(n.toText());
        }
        sb.append(openToken.toText());
        return super.toString();
    }

}
