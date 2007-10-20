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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.SyntaxError;
import org.extex.latexParser.impl.SystemException;
import org.extex.latexParser.impl.node.EndNode;
import org.extex.latexParser.impl.node.EnvironmentNode;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.latexParser.impl.node.MacroNode;
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
     * 
     * @param s the initial name
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
    public Node parse(Token token, Parser parser)
            throws ScannerException,
                IOException {

        GroupNode group = parser.parseGroup();
        if (group.size() != 1) {
            throw new SyntaxError("environment expected");
        }
        Node node = group.get(0);
        if (!(node instanceof TokensNode)) {
            throw new SyntaxError("environment expected");
        }
        String name = node.toString();
        Macro macro = parser.getDefinition("begin." + name);
        if (macro == null) {
            throw new SyntaxError("environment " + name + " undefined");
        }

        Map<String, Object> context = parser.getContext();
        Object envStack = context.get(Begin.ENVIRONMENT);
        List<EnvironmentNode> stack = (List<EnvironmentNode>) envStack;
        if (stack == null) {
            stack = new ArrayList<EnvironmentNode>();
            context.put(ENVIRONMENT, stack);
        }
        String source = parser.getSource();
        int lineno = parser.getLineno();

        Node mac = macro.parse(null, parser);
        if (!(mac instanceof MacroNode)) {
            throw new SystemException("type mismatch", null);
        }
        MacroNode m = (MacroNode) mac;

        EnvironmentNode environmentNode =
                new EnvironmentNode(name, m.getOpt(), m.getArgs(), source,
                    lineno);
        stack.add(environmentNode);

        for (;;) {
            Node n = parser.parseNode();
            if (n == null) {
                throw new SyntaxError("unexpected EOF in environment " + name);
            } else if (n instanceof EndNode) {
                break;
            }
            environmentNode.add(n);
        }
        return environmentNode;
    }
}
