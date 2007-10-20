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
import org.extex.latexParser.impl.SyntaxError;
import org.extex.latexParser.impl.node.MacroNode;
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
public class GenericMacro implements Macro {

    /**
     * The field <tt>spec</tt> contains the ...
     */
    private String spec;

    /**
     * Creates a new object.
     * 
     * @param spec
     */
    public GenericMacro(String spec) {

        super();
        this.spec = spec;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Macro#parse( Token, Parser)
     */
    public Node parse(Token token, Parser parser) throws ScannerException {

        if (spec == null || "".equals(spec)) {
            return new MacroNode(token, null, new Node[]{});
        } else if (spec.matches("\\[[0-9]\\]")) {
            int n = spec.charAt(1) - '0';
            Node[] args = new Node[n];
            parseArgs(args, parser);
            return new MacroNode(token, null, args);

        } else if (spec.matches("\\[.*\\]\\[[0-9]\\]")) {
            int n = spec.charAt(spec.length() - 2) - '0';
            Node opt = null;
            Token t = parser.getToken();
            if (t == null) {
                throw new SyntaxError("Unexpected EOF");
            } else if (t.eq(Catcode.OTHER, '[')) {
                opt = parser.parseOptionalArgument(token, (OtherToken) t);
            } else {
                parser.put(t);
            }

            Node[] args = new Node[n];
            parseArgs(args, parser);
            return new MacroNode(token, opt, args);
        }

        throw new RuntimeException("unknown spec encountered");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args
     * @param parser
     * 
     * @throws ScannerException in case of an error
     */
    private void parseArgs(Node[] args, Parser parser) throws ScannerException {

        for (int i = 0; i < args.length; i++) {
            args[i] = parser.parseTokenOrGroup();
        }
    }

}
