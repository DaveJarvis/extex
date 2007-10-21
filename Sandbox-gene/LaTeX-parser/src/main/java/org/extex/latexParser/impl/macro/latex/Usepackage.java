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
import org.extex.latexParser.impl.exception.SyntaxError;
import org.extex.latexParser.impl.node.AbstractNode;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.latexParser.impl.node.TokensNode;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;

/**
 * This class represents a \ usepackage instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Usepackage extends AbstractNode implements Macro {

    /**
     * The field <tt>opt</tt> contains the optional arguments.
     */
    private Node opt;

    /**
     * The field <tt>name</tt> contains the name of the packages.
     */
    private String name;

    /**
     * The field <tt>token</tt> contains the token.
     */
    private Token token;

    /**
     * Creates a new object.
     * 
     * @param s the initial name
     */
    public Usepackage(String s) {

        super(null, 0);
    }

    /**
     * Creates a new object.
     * 
     * @param t the initiating token
     * @param opt the optional argument
     * @param name the name of the package
     * @param source the source
     * @param lineNumber the line number
     */
    public Usepackage(Token t, Node opt, String name, String source,
            int lineNumber) {

        super(source, lineNumber);
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
            throw new SyntaxError(parser, "unexpected end of file for {0}",
                token.toText());
        }
        Node o = null;
        if (t.eq(Catcode.OTHER, '[')) {
            o = parser.parseOptionalArgument(token, (OtherToken) t);
        }
        GroupNode a = parser.parseGroup();
        if (a.size() != 1) {
            throw new SyntaxError(parser,
                "package name expected instead of {0}", a.toString());
        }
        Node node = a.get(0);
        if (!(node instanceof TokensNode)) {
            throw new SyntaxError(parser, "document class");
        }
        String name = node.toString();

        for (String s : name.split(",")) {
            parser.load("latex/sty/" + s);
        }

        return new Usepackage(token, o, name, parser.getSource(), parser
            .getLineno());
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
