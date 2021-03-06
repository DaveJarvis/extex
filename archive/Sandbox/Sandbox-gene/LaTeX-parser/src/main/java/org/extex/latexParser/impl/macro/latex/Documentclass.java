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
 * This class represents a \documentclass instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Documentclass extends AbstractNode implements Macro {

    /**
     * The field {@code opt} contains the optional arguments.
     */
    private Node opt;

    /**
     * The field {@code name} contains the name of the document class.
     */
    private String name;

    /**
     * The field {@code token} contains the token evaluated for this command.
     */
    private Token token;

    /**
     * Creates a new object.
     * 
     * @param s the initial name
     */
    public Documentclass(String s) {

        super(null, 0);
    }

    /**
     * Creates a new object.
     * 
     * @param t the starting token
     * @param opt the optional arguments
     * @param name the name of the document class
     * @param source the source
     * @param lineNumber the line number
     */
    public Documentclass(Token t, Node opt, String name, String source,
            int lineNumber) {

        super(source, lineNumber);
        this.token = t;
        this.opt = opt;
        this.name = name;
    }

    /**
*      org.extex.scanner.type.token.Token,
     *      org.extex.latexParser.impl.Parser)
     */
    public Node parse(Token token, Parser parser)
            throws ScannerException,
                IOException {

        Token t = parser.getToken();
        if (t == null) {
            throw new SyntaxError(parser, "unexpected end of file");
        }
        Node o = null;
        if (t.eq(Catcode.OTHER, '[')) {
            o = parser.parseOptionalArgument(token, (OtherToken) t);
        } else {
            parser.put(t);
        }
        GroupNode a = parser.parseGroup();
        if (a.size() != 1) {
            throw new SyntaxError(parser,
                "environment name expected instead of {0}", a.toString());
        }
        Node node = a.get(0);
        if (!(node instanceof TokensNode)) {
            throw new SyntaxError(parser, "document class");
        }
        String name = node.toString();

        parser.load("latex/cls/" + name);

        return new Documentclass(token, o, name, parser.getSource(), parser
            .getLineno());
    }

public void print(PrintStream stream) {

        stream.print(token.toText());
        if (opt != null) {
            opt.print(stream);
        }
        stream.print("{");
        stream.print(name);
        stream.print("}");
    }

@Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(token.toText());
        if (opt != null) {
            sb.append(opt.toString());
        }
        sb.append("{");
        sb.append(name);
        sb.append("}");
        return sb.toString();
    }

}
