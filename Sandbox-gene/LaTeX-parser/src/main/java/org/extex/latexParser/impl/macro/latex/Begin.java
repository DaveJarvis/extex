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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.SyntaxError;
import org.extex.latexParser.impl.node.EnvironmentNode;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.latexParser.impl.node.TokensNode;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.token.Token;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Begin implements Macro {

    /**
     * The field <tt>ENVIRONMENT</tt> contains the key for the environment
     * list.
     */
    public final static String ENVIRONMENT = "environment";

    /**
     * Creates a new object.
     */
    public Begin(String s) {

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

        GroupNode x = parser.parseGroup();
        if (x.size() != 1) {
            throw new SyntaxError("environment expected");
        }
        Node node = x.get(0);
        if (!(node instanceof TokensNode)) {
            throw new SyntaxError("environment expected");
        }
        String name = node.toString();
        Macro macro = parser.getDefinition("begin." + name);
        if (macro == null) {
            throw new SyntaxError("environment " + name + " undefined");
        }

        Map<String, Object> context = parser.getContext();
        List<EnvironmentNode> stack =
                (List<EnvironmentNode>) context.get(ENVIRONMENT);
        if (stack == null) {
            stack = new ArrayList<EnvironmentNode>();
            context.put(ENVIRONMENT, stack);
        }
        stack.add(new EnvironmentNode(null, name, //
            parser.getSource(), parser.getLineno()));

        return macro.parse(null, parser);
    }
}
