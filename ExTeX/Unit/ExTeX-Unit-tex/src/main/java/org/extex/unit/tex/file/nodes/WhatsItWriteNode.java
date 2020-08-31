/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.file.nodes;

import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.TokensWriter;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.WhatsItNode;

/**
 * This WhatsIt node writes some expanded tokens to an out file on shipping.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class WhatsItWriteNode extends WhatsItNode {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code key} contains the key of the output file to write to.
     */
    private final String key;

    /**
     * The field {@code tokens} contains the tokens to expand and write.
     */
    private final Tokens tokens;

    /**
     * The field {@code writer} contains the writer used as target when the
     * node is shipped out.
     */
    private final transient TokensWriter writer;

    /**
     * The field {@code source} contains the token source for expansion.
     */
    private final transient TokenSource source;

    /**
     * Creates a new object.
     * 
     * @param key the key for the OutFile
     * @param tokens the tokens to write (after expansion)
     * @param source the interpreter for expansion
     * @param writer the target writer
     */
    public WhatsItWriteNode(String key, Tokens tokens, TokenSource source,
            TokensWriter writer) {

        this.key = key;
        this.tokens = tokens;
        this.source = source;
        this.writer = writer;
    }

    /**
*      org.extex.typesetter.PageContext, org.extex.typesetter.Typesetter,
     *      org.extex.core.dimen.FixedDimen, org.extex.core.dimen.FixedDimen)
     */
    @Override
    public Node atShipping(PageContext context, Typesetter typesetter,
            FixedDimen posX, FixedDimen posY) throws GeneralException {

        Tokens toks = source.expand(tokens, typesetter);
        if (!(context instanceof Context)) {
            // TODO gene: unimplemented
            throw new RuntimeException("unimplemented");
        }
        writer.write(key, toks, (Context) context);

        return null;
    }

}
