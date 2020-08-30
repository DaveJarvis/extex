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

package org.extex.latexParser.impl.macro.tex;

import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.exception.SyntaxError;
import org.extex.latexParser.impl.macro.GenericMacro;
import org.extex.latexParser.impl.node.TokensNode;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;

/**
 * This class represents a macro which consumes the first following token
 * uninterpreted.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Let implements Macro {

    /**
     * Creates a new object.
     * 
     * @param s the initial name
     */
    public Let(String s) {

        super();
    }

    /**
*      org.extex.scanner.type.token.Token,
     *      org.extex.latexParser.impl.Parser)
     */
    public Node parse(Token token, Parser parser) throws ScannerException {

        TokensNode tokens =
                new TokensNode(token, parser.getSource(), parser.getLineno());
        Token name = parser.getToken();
        if (name == null) {
            throw new SyntaxError(parser, "unexpected end of file for {0}",
                token.toText());
        }
        tokens.add(name);
        if (name instanceof ControlSequenceToken) {
            parser.def(((ControlSequenceToken) name).getName(),
                new GenericMacro(""));
        } else {
            parser.def((char) name.getChar().getCodePoint(), new GenericMacro(
                ""));
        }

        return tokens;
    }
}
