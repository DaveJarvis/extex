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

package org.extex.latexParser.impl.macro;

import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.exception.SyntaxError;
import org.extex.latexParser.impl.node.MacroNode;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;

/**
 * This class represents a generic macro.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class GenericMacro implements Macro {

    /**
     * The field {@code spec} contains the specification for parsing of
     * arguments.
     */
    private String spec;

    /**
     * Creates a new object.
     * 
     * @param spec the specification for parsing the arguments
     */
    public GenericMacro(String spec) {

        super();
        this.spec = spec;
    }

public Node parse(Token token, Parser parser) throws ScannerException {

        if (spec == null || "".equals(spec)) {
            return new MacroNode(token, null, new Node[]{}, parser.getSource(),
                parser.getLineno());
        } else if (spec.matches("\\[[0-9]\\]")) {
            int n = spec.charAt(1) - '0';
            Node[] args = new Node[n];
            parseArgs(args, parser);
            return new MacroNode(token, null, args, parser.getSource(), parser
                .getLineno());

        } else if (spec.matches("\\[.*\\]\\[[0-9]\\]")) {
            int n = spec.charAt(spec.length() - 2) - '0';
            Node opt = null;
            Token t = parser.getToken();
            if (t == null) {
                throw new SyntaxError(parser, "unexpected end of file");
            } else if (t.eq(Catcode.OTHER, '[')) {
                opt = parser.parseOptionalArgument(token, (OtherToken) t);
            } else {
                parser.put(t);
            }

            Node[] args = new Node[n];
            parseArgs(args, parser);
            return new MacroNode(token, opt, args, parser.getSource(), parser
                .getLineno());
        }

        throw new RuntimeException("unknown spec encountered");
    }

    /**
     * Fill the arguments array.
     * 
     * @param args the array or arguments to be filled
     * @param parser the parser
     * 
     * @throws ScannerException in case of an error
     */
    private void parseArgs(Node[] args, Parser parser) throws ScannerException {

        for (int i = 0; i < args.length; i++) {
            args[i] = parser.parseTokenOrGroup();
        }
    }

}
