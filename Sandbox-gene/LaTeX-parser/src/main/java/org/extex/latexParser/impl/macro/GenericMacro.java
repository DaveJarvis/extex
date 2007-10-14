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
import org.extex.latexParser.impl.node.TokenNode;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
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
            return new TokenNode(token);
        } else if (spec.matches("\\[[1-9]\\]")) {
            int n = spec.charAt(1) - '0';
            Node[] args = new Node[n];
            parseArgs(args, parser);
            return new MacroNode(token, null, args);

        } else if (spec.matches("\\[.*\\]\\[[1-9]\\]")) {
            int n = spec.charAt(spec.length() - 2) - '0';
            Node opt = null;
            Token t = parser.getToken();
            if (t == null) {
                throw new SyntaxError("Unexpected EOF");
            } else if (t.eq(Catcode.OTHER, '[')) {
                // TODO parse optional argument
                throw new RuntimeException("unimplemented");
            }

            Node[] args = new Node[n];
            parseArgs(args, parser);
            return new MacroNode(token, opt, args);
        }

        // TODO gene: parse unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args
     * @param parser
     */
    private void parseArgs(Node[] args, Parser parser) {

        // TODO gene: enclosing_method unimplemented
        throw new RuntimeException("unimplemented");
    }

}
