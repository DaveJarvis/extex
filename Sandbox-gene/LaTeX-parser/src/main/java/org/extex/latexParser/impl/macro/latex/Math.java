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

import org.extex.core.UnicodeChar;
import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.exception.SyntaxError;
import org.extex.latexParser.impl.exception.SystemException;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.latexParser.impl.node.MathEnvironment;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;

/**
 * This class represents a \[ or \( instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Math implements Macro {

    /**
     * Parse a bunch of mathematical material.
     * 
     * @param parser the parser
     * @param start the initial token
     * @param end the final token
     * 
     * @return the math nodes
     * 
     * @throws ScannerException in case of an error
     */
    public static Node collectMath(Parser parser, Token start, Token end)
            throws ScannerException {

        GroupNode env = parser.peek();
        if (env instanceof MathEnvironment) {
            throw new SyntaxError(
                parser,
                "trying to open math when already in math mode started at {0}:{1}",
                env.getSource(), Integer.toString(env.getLineNumber()));
        }

        env = new MathEnvironment(start, null, end, null, //
            parser.getSource(), parser.getLineno());
        parser.push(env);
        for (Node n = parser.parseNode(end); n != null; n =
                parser.parseNode(end)) {
            env.add(n);
        }
        GroupNode pop = parser.pop();
        if (pop != env) {
            parser.push(pop);
            throw new SyntaxError(parser,
                "closing math is missing for math opened at {0}:{1}", //
                env.getSource(), Integer.toString(env.getLineNumber()));
        }
        return env;
    }

    /**
     * Creates a new object.
     * 
     * @param s the initial name
     */
    public Math(String s) {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Macro#parse(
     *      org.extex.scanner.type.token.Token,
     *      org.extex.latexParser.impl.Parser)
     */
    public Node parse(Token token, Parser parser) throws ScannerException {

        String cl;
        if (token.eq(Catcode.ESCAPE, "(")) {
            cl = ")";
        } else if (token.eq(Catcode.ESCAPE, "[")) {
            cl = "]";
        } else {
            throw new SystemException("unknown start for math", null);
        }

        Token end;
        try {
            end = parser.getTokenFactory().createToken(Catcode.ESCAPE, //
                UnicodeChar.get('\\'), cl, "");
        } catch (CatcodeException e) {
            throw new SystemException("unknown", e);
        }
        return collectMath(parser, token, end);
    }

}
