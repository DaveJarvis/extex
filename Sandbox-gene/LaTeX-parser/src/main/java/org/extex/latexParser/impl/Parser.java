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

package org.extex.latexParser.impl;

import java.io.IOException;
import java.util.Map;

import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface Parser {

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
     * Getter for the context. The context can be used to store arbitrary data.
     * 
     * @return the context
     */
    Map<String, Object> getContext();

    /**
     * Get a token from the input stream.
     * 
     * @return the token read or <code>null</code> at EOF
     * 
     * @throws ScannerException in case of an error
     */
    Token getToken() throws ScannerException;

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param c
     * @return
     */
    boolean isDefined(char c);

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @return
     */
    boolean isDefined(String name);

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error
     */
    void load(String name) throws IOException, ScannerException;

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     * 
     * @throws ScannerException in case of an error
     */
    GroupNode parseGroup() throws ScannerException;

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param t the starting left bracket
     * 
     * @return
     * 
     * @throws ScannerException in case of an error
     */
    Node parseOptionalArgument(OtherToken t) throws ScannerException;

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     * 
     * @throws ScannerException in case of an error
     */
    Node parseTokenOrGroup() throws ScannerException;
}
