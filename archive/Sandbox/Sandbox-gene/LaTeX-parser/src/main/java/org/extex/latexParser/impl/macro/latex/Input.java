/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.latexParser.impl.macro.latex;

import java.io.IOException;

import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.exception.SyntaxError;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.latexParser.impl.node.TokensNode;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;

/**
 * This class represents an \include instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 7667 $
 */
public class Input implements Macro {

    /**
     * Creates a new object.
     * 
     * @param s the initial name
     */
    public Input(String s) {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Macro#parse(org.extex.scanner.type.token.Token,
     *      org.extex.latexParser.impl.Parser)
     */
    public Node parse(Token token, Parser parser)
            throws ScannerException,
                IOException {

        String name;
        Token t;
        do {
            t = parser.getToken();
        } while (t != null && t.isa(Catcode.SPACE));

        if (t.isa(Catcode.LEFTBRACE)) {
            GroupNode group = parser.parseGroup();
            if (group.size() != 1) {
                throw new SyntaxError(parser, "resource expected");
            }
            Node node = group.get(0);
            if (!(node instanceof TokensNode)) {
                throw new SyntaxError(parser, "resource expected");
            }
            name = node.toString();
        } else if (t.isa(Catcode.LETTER) || t.isa(Catcode.OTHER)) {

            StringBuilder buffer = new StringBuilder();
            do {
                buffer.append((char) t.getChar().getCodePoint());
                t = parser.getToken();
                if (t == null) {
                    // TODO gene: parse unimplemented
                    throw new RuntimeException("unimplemented");
                }
            } while (t.isa(Catcode.LETTER) || t.isa(Catcode.OTHER));
            parser.put(t);

            name = buffer.toString();
        } else {

            // TODO gene: enclosing_method unimplemented
            throw new RuntimeException("unimplemented");
        }

        return parser.parse(name);
    }

}
