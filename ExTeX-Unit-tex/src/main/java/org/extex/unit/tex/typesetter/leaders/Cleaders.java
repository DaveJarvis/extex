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

package org.extex.unit.tex.typesetter.leaders;

import org.extex.core.glue.FixedGlue;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.OrientedNode;
import org.extex.typesetter.type.node.CenteredLeadersNode;

/**
 * This class provides an implementation for the primitive
 * <code>\cleaders</code>.
 * 
 * <doc name="cleaders">
 * <h3>The Primitive <tt>\cleaders</tt></h3>
 * <p>
 * The primitive <tt>\cleaders</tt> is a leaders construction which centers
 * the material if it does not fit perfectly. The primitive takes as the first
 * argument a box or rule. The second argument is a horizontal or vertical skip
 * specification. The two arguments have to agree on the orientation; either
 * both are horizontal or both are vertical.
 * </p>
 * <p>
 * The primitive behaves like a glue node with the given skip characteristics.
 * This means it participates in the distribution of the space in the
 * surrounding box. When the final dimensions of the leader element are
 * determined then the empty space is filled with the given box or rule.
 * </p>
 * <p>
 * The simplest case is a rule which is adjusted to the actual size of the
 * element. This means it either stretches horizontally or vertically according
 * to its orientation.
 * </p>
 * <p>
 * If a box is given then the contents of the box is repeated within the free
 * space until it is filled. If the space can not be filled exactly then the
 * remaining space is distributed evenly at both ends. This means that the box
 * material is replicated and centered within the given rectangle. As a
 * consequence nothing will appear on the page if the available space is smaller
 * than the natural width of the box.
 * </p>
 * <p>
 * Note that there are also the primitives <tt>\xleaders</tt> and
 * <tt>\leaders</tt> which provide essentially the same functionality but
 * adjust the material within the final space differently.
 * <p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;cleaders&rang;
 *      &rarr; <tt>\cleaders</tt> &lang;Box or Rule&rang; &lang;Skip&rang;
 *
 *    &lang;Box or Rule&rang;
 *      &rarr; &lang;Box&rang;
 *       | &lang;Rule&rang;        </pre>
 * 
 * <h4>Examples</h4>
 * 
 * 
 * <pre class="TeXSample">
 *    \cleaders\hrule\hfill  </pre>
 * 
 * <p>
 * This example creates a horizontal rule which fills the space made up by the
 * <tt>\hfill</tt>. The rule stretches horizontally and has its natural
 * dimensions vertically.
 * </p>
 * 
 * <pre class="TeXSample">
 *    \cleaders\vrule\vfil  </pre>
 * 
 * <p>
 * This example demonstrates the same in vertical direction. Since only
 * <tt>\vil</tt> is used it may not appear at all when the other elements in
 * the surrounding box overrule it.
 * </p>
 * 
 * <pre class="TeXSample">
 *    \cleaders\hbox to 2em{\hss .\hss}\hfill  </pre>
 * 
 * <p>
 * This example shows a box of the width 2em which has a centered period in it.
 * The contents of the box is repeated until it fills the space available. If
 * the final width is a multiple of 2em then then this space is filled up. If
 * the available space leaves some freedom then this extra space is distributed
 * equally on the left and right end.
 * </p>
 * </doc>
 * 
 * @see org.extex.typesetter.type.node.CenteredLeadersNode
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Cleaders extends Leaders {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Cleaders(CodeToken token) {

        super(token);
    }

    /**
     * Finally make an appropriate node and add it to the typesetter.
     * 
     * @param typesetter the typesetter
     * @param node the node
     * @param skip the skip amount
     * @throws TypesetterException in case of an error
     */
    @Override
    protected void addNode(Typesetter typesetter, OrientedNode node,
            FixedGlue skip) throws TypesetterException {

        typesetter.add(new CenteredLeadersNode(node, skip));
    }

}
