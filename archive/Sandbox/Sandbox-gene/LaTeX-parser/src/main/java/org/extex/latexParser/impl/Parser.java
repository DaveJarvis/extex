/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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
import java.util.Map;

import org.extex.latexParser.api.Node;
import org.extex.latexParser.api.NodeList;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;

/**
 * This interface describes a parser and central memory for the LaTeX processor.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface Parser extends Locator, Memory {

    /**
     * Close the file stream currently read from.
     */
    void closeFileStream();

    /**
     * Getter for the context. The context can be used to store arbitrary data.
     * 
     * @return the context
     */
    Map<String, Object> getContext();

    /**
     * Get a token from the input stream.
     * 
     * @return the token read or {@code null} at EOF
     * 
     * @throws ScannerException in case of an error
     */
    Token getToken() throws ScannerException;

    /**
     * Getter for the token factory.
     * 
     * @return the token factory
     */
    TokenFactory getTokenFactory();

    /**
     * Write a formatted log message.
     * 
     * @param format the format
     * @param args the arguments
     */
    void log(String format, Object... args);

    /**
     * Parse a resource and return the nodes found.
     * 
     * @param name the name of the resource
     * 
     * @return the nodes
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error
     */
    NodeList parse(String name) throws IOException, ScannerException;

    /**
     * Parse a group enclosed in braces.
     * 
     * @return the groups as node
     * 
     * @throws ScannerException in case of an error
     */
    GroupNode parseGroup() throws ScannerException;

    /**
     * Parse a node.
     * 
     * @param end the optional end token
     * 
     * @return the node found or {@code null} at EOF
     * 
     * @throws ScannerException in case of an error
     */
    Node parseNode(Token end) throws ScannerException;

    /**
     * Parse an optional argument enclosed in brackets. The opening bracket has
     * already been read and is passed in as argument.
     * 
     * @param token the control sequence we are active for
     * @param t the starting left bracket
     * 
     * @return the optional argument as node
     * 
     * @throws ScannerException in case of an error
     */
    Node parseOptionalArgument(Token token, OtherToken t)
            throws ScannerException;

    /**
     * parse a token or a group if the first token is a left brace.
     * 
     * @return the node of the tokens read
     * 
     * @throws ScannerException in case of an error
     */
    Node parseTokenOrGroup() throws ScannerException;

    /**
     * Look at the stack without modifying it.
     * 
     * @return the top of stack or {@code null} if the stack is empty
     */
    GroupNode peek();

    /**
     * Take an element from the stack.
     * 
     * @return the top of stack or {@code null} if the stack is empty
     */
    GroupNode pop();

    /**
     * Put an element onto the stack.
     * 
     * @param env the node to push
     */
    void push(GroupNode env);

    /**
     * Push a token back for the next get();
     * 
     * @param t the token
     */
    void put(Token t);

    /**
     * Setter for the tokenizer.
     * 
     * @param tokenizer the new tokenizer
     * 
     * @return the old tokenizer
     */
    Tokenizer setTokenizer(Tokenizer tokenizer);

}
