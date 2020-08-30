/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.typesetter.box;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.VerticalListNode;

/**
 * This class provides an implementation for the primitive {@code \vtop}.
 *
 * <p>The Primitive {@code \vtop}</p>
 * <p>
 *  TODO missing documentation
 * </p>
 * <p>
 *  The contents of the toks register {@code \everyvbox} is inserted at the
 *  beginning of the vertical material of the box.
 * </p>
 *
 * <p>Syntax</p>

 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;vtop&rang;
 *      &rarr; {@code \vtop} &lang;box specification&rang; {@code {} &lang;vertical material&rang; {@code }}
 *
 *    &lang;box specification&rang;
 *      &rarr;
 *         | {@code to} &lang;rule dimension&rang;
 *         | {@code spread} &lang;rule dimension&rang;  </pre>
 *
 * <p>Examples</p>

 *  <pre class="TeXSample">
 *    \vtop{abc}  </pre>
 *  <pre class="TeXSample">
 *    \vtop to 120pt{abc}  </pre>
 *  <pre class="TeXSample">
 *    \vtop spread 12pt{abc}  </pre>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Vtop extends Vbox {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     *
     * @param token the initial token for the primitive
     */
    public Vtop(CodeToken token) {

        super(token);
    }

    /**
     * Acquire a Box and adjust its height and depth according to the rules
     * required.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param startToken the token which started the group
     * @param insert the token to insert at the beginning or {@code null}
     *
     * @return the complete Box
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     *
     * @see org.extex.unit.tex.typesetter.box.Vbox#constructBox(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    protected Box constructBox(Context context, TokenSource source,
            Typesetter typesetter, Token startToken,
            Token insert) throws HelpingException, TypesetterException {

        Box box =
                acquireBox(context, source, typesetter, GroupType.VTOP_GROUP,
                    startToken, insert);
        VerticalListNode nodes = (VerticalListNode) box.getNodes();
        Dimen depth = new Dimen(box.getDepth());
        depth.add(box.getHeight());
        if (nodes != null && nodes.size() > 0) {
            nodes.setTop(true);
            Node top = nodes.get(0);
            FixedDimen height = top.getHeight();
            box.setHeight(height);
            depth.subtract(height);
            box.setDepth(depth);
            //nodes.vpack()
        }
        return box;
    }

}
