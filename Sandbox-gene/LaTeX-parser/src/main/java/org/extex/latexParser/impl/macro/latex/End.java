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
public class End implements Macro {

    /**
     * Creates a new object.
     */
    public End(String s) {

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

        Map<String, Object> context = parser.getContext();
        List<EnvironmentNode> info =
                (List<EnvironmentNode>) context.get(Begin.ENVIRONMENT);
        if (info == null) {
            throw new SyntaxError("environment " + name
                    + " closed without being opened");
        }
        EnvironmentNode env = info.get(info.size() - 1);
        String iname = env.getName();
        if (!iname.equals(name)) {
            throw new SyntaxError("environment " + iname
                    + " not closed when closing " + name);
        }

        Macro macro = parser.getDefinition("end." + name);
        if (macro == null) {
            return info.remove(info.size() - 1);
        }
        return macro.parse(null, parser);
    }
}
