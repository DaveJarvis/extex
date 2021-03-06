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

import org.extex.latexParser.api.NodeList;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.RightBraceToken;

/**
 * This class represents a group.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class GroupNode extends NodeList {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field {@code openToken} contains the opening token.
     */
    private LeftBraceToken openToken;

    /**
     * The field {@code closeToken} contains the closing token.
     */
    private RightBraceToken closeToken;


    public GroupNode() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param t the token to add
     * @param source the source
     * @param lineNumber the line number
     */
    public GroupNode(LeftBraceToken t, String source, int lineNumber) {

        super(source, lineNumber);
        openToken = t;
    }

    /**
     * Close the group.
     * 
     * @param t the closing token
     */
    public void close(RightBraceToken t) {

        closeToken = t;
    }

    /**
     * Getter for closeToken.
     * 
     * @return the closeToken
     */
    public RightBraceToken getCloseToken() {

        return closeToken;
    }

    /**
     * Getter for the name of the group. Plain groups do not have a name and
     * thus will return {@code null}.
     * 
     * @return the name of the group
     */
    public String getName() {

        return null;
    }

    /**
     * Getter for openToken.
     * 
     * @return the openToken
     */
    public LeftBraceToken getOpenToken() {

        return openToken;
    }

@Override
    public void print(PrintStream stream) {

        if (openToken != null) {
            stream.print(openToken.toText());
        }
        super.print(stream);
        if (closeToken != null) {
            stream.print(closeToken.toText());
        }
    }

@Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        if (openToken != null) {
            sb.append(openToken.toText());
        }
        super.toString(sb);
        if (closeToken != null) {
            sb.append(closeToken.toText());
        }
        return sb.toString();
    }

}
