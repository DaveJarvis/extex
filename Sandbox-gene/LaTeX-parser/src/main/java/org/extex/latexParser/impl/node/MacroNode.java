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

import org.extex.latexParser.api.Node;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;

/**
 * This class represents an invocation of a macro or active character.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MacroNode implements Node {

    /**
     * The field <tt>token</tt> contains the command token.
     */
    private Token token;

    /**
     * The field <tt>opt</tt> contains the optional argument. it can be
     * <code>null</code> if none are present.
     */
    private Node opt;

    /**
     * The field <tt>args</tt> contains the arguments.
     */
    private Node[] args;

    /**
     * Creates a new object.
     * 
     * @param token the token to add
     * @param opt the optional arguments
     * @param args the arguments
     */
    public MacroNode(Token token, Node opt, Node[] args) {

        super();
        this.token = token;
        this.opt = opt;
        this.args = args;
    }

    /**
     * Getter for token.
     * 
     * @return the token
     */
    public Token getToken() {

        return token;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.api.Node#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.print(token.toText());
        if (opt != null) {
            opt.print(stream);
        }
        for (Node n : args) {
            n.print(stream);
        }
        if (args.length == 0 && token instanceof ControlSequenceToken) {
            stream.print(' ');
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(token.toText());
        if (opt != null) {
            sb.append(opt.toString());
        }
        for (Node n : args) {
            sb.append(n.toString());
        }
        if (args.length == 0 && token instanceof ControlSequenceToken) {
            sb.append(' ');
        }
        return sb.toString();
    }

}
