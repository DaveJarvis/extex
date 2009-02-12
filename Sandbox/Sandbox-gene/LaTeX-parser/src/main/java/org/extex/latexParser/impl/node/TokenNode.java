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
import org.extex.scanner.type.token.Token;

/**
 * This class represents a single token.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TokenNode extends AbstractNode implements Node {

    /**
     * The field <tt>token</tt> contains the token contained.
     */
    private Token token;

    /**
     * Creates a new object.
     * 
     * @param t the token to add
     * @param source the source
     * @param lineNumber the line number
     */
    public TokenNode(Token t, String source, int lineNumber) {

        super(source, lineNumber);
        token = t;
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

        if (token != null) {
            stream.print(token.toText());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        if (token != null) {
            return token.toText();
        }
        return "";
    }

}
