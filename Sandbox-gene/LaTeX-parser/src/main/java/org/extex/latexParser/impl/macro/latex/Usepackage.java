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

package org.extex.latexParser.impl.macro.latex;

import java.io.IOException;
import java.io.PrintStream;

import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.SyntaxError;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.latexParser.impl.node.TokensNode;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Usepackage implements Macro, Node {

    /**
     * The field <tt>opt</tt> contains the optional arguments.
     */
    private Node opt;

    /**
     * The field <tt>name</tt> contains the name of the packages.
     */
    private String name;

    /**
     * The field <tt>token</tt> contains the ...
     */
    private Token token;

    /**
     * Creates a new object.
     * 
     * @param s the initial name
     */
    public Usepackage(String s) {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param t
     * @param opt
     * @param name
     */
    public Usepackage(Token t, Node opt, String name) {

        super();
        this.token = t;
        this.opt = opt;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Macro#parse(
     *      org.extex.scanner.type.token.Token,
     *      org.extex.latexParser.impl.Parser)
     */
    public Node parse(Token token, Parser parser)
            throws ScannerException,
                IOException {

        Token t = parser.getToken();
        if (t == null) {
            throw new SyntaxError("unexpected EOF");
        }
        Node o = null;
        if (t.eq(Catcode.OTHER, '[')) {
            o = parser.parseOptionalArgument(token, (OtherToken) t);
        }
        GroupNode a = parser.parseGroup();
        if (a.size() != 1) {
            throw new SyntaxError("package expected");
        }
        Node node = a.get(0);
        if (!(node instanceof TokensNode)) {
            throw new SyntaxError("document class");
        }
        String name = node.toString();

        for (String s : name.split(",")) {
            parser.load("latex/sty/" + s);
        }

        return new Usepackage(token, o, name);
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
        stream.print("{");
        stream.print(name);
        stream.print("}");
    }
}
