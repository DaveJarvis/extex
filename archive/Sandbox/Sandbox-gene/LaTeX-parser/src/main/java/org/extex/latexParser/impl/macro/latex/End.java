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
import org.extex.scanner.type.token.Token;

/**
 * This class represents an \end instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class End extends AbstractNode implements Macro {

    /**
     * Creates a new object.
     * 
     * @param s the initial name
     */
    public End(String s) {

        super(null, 0);
    }

    /**
     * Creates a new object.
     * 
     * @param source the source
     * @param lineNumber the line number
     */
    public End(String source, int lineNumber) {

        super(source, lineNumber);
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

        GroupNode group = parser.parseGroup();
        if (group.size() != 1) {
            throw new SyntaxError(parser,
                "environment name expected instead of {0}", group.toString());
        }
        Node node = group.get(0);
        if (!(node instanceof TokensNode)) {
            throw new SyntaxError(parser,
                "environment name expected instead of {0}", node.toString());
        }
        String name = node.toString();

        GroupNode env = parser.pop();
        if (env == null) {
            throw new SyntaxError(parser,
                "end of environemnt {0} has been found without begin", name);
        }
        String iname = env.getName();
        if (!iname.equals(name)) {
            parser.push(env);
            throw new SyntaxError(parser,
                "end of environemnt {0} has been found without begin", name);
        }

        Macro macro = parser.getDefinition("end{" + name + "}");
        if (macro != null) {
            macro.parse(null, parser);
        }
        return new End(parser.getSource(), parser.getLineno());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.api.Node#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {


    }
}
