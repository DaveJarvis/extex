/*
 * Copyright (C) 2004-2008 The ExTeX Group and individual authors listed below
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

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.core.glue.FixedGlue;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.interpreter.type.box.RuleConvertible;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.OrientedNode;
import org.extex.typesetter.type.node.AlignedLeadersNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.unit.tex.typesetter.spacing.HorizontalSkip;
import org.extex.unit.tex.typesetter.spacing.VerticalSkip;

/**
 * This class provides an implementation for the primitive <code>\leaders</code>.
 * 
 * <doc name="leaders">
 * <h3>The Primitive <tt>\leaders</tt></h3>
 * <p>
 * The primitive <tt>\leaders</tt> is a leaders construction which aligns the
 * material on an imaginary grid on the page. The primitive takes as the first
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
 * space until it is filled. The repeated boxes are aligned on an imaginary grid
 * on the page which guarantees that the boxes fit together tightly. A box is
 * only shown if it fits entirely into the space available. The leaders
 * construction provides something like a window onto an infinite grid filled
 * with the given boxes.
 * </p>
 * <p>
 * For a horizontal alignment nothing may appear unless the final width of the
 * alignment is at least two times the width of the box to be inserted.
 * </p>
 * <p>
 * As a consequence of the alignment on the grid several invocations of
 * <tt>\leaders</tt> with the same box will align those boxes. This can be
 * used in a table of figures to align the dos connecting the title and the page
 * number.
 * </p>
 * <p>
 * Note that there are also the primitives <tt>\cleaders</tt> and
 * <tt>\xleaders</tt> which provide essentially the same functionality but
 * adjust the material within the final space differently.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;leaders&rang;
 *      &rarr; <tt>\leaders</tt> &lang;Box or Rule&rang; &lang;Skip&rang;
 *
 *    &lang;Box or Rule&rang;
 *      &rarr; &lang;Box&rang;
 *       | &lang;Rule&rang;        </pre>
 * 
 * <h4>Examples</h4>
 * 
 * 
 * <pre class="TeXSample">
 *    \leaders\hrule\hfill  </pre>
 * 
 * <p>
 * This example creates a horizontal rule which fills the space made up by the
 * <tt>\hfill</tt>. The rule stretches horizontally and has its natural
 * dimensions vertically.
 * </p>
 * 
 * <pre class="TeXSample">
 *    \leaders\vrule\vfil  </pre>
 * 
 * <p>
 * This example demonstrates the same in vertical direction. Since only
 * <tt>\vil</tt> is used it may not appear at all when the other elements in
 * the surrounding box overrule it.
 * </p>
 * 
 * <pre class="TeXSample">
 *    \leaders\hbox to 2em{\hss .\hss}\hfill  </pre>
 * 
 * <p>
 * This example shows a box of the width 2em which has a centered period in it.
 * The contents of the box is repeated until it fills the space available.
 * </p>
 * </doc>
 * 
 * @see org.extex.typesetter.type.node.AlignedLeadersNode
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Leaders extends AbstractCode {

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
    public Leaders(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        Code code = context.getCode(cs);

        if (code == null) {
            throw new UndefinedControlSequenceException(cs.toText());
        }

        boolean horizontal;

        OrientedNode node = null;
        if (code instanceof Boxable) {
            Box b = ((Boxable) code).getBox(context, source, typesetter, null);
            NodeList nl = b.getNodes();
            if (!(nl instanceof OrientedNode)) {
                // TODO gene: execute unimplemented
                throw new RuntimeException("unimplemented");
            }
            node = (OrientedNode) nl;
            horizontal = b.isHbox();
        } else if (code instanceof RuleConvertible) {
            node =
                    ((RuleConvertible) code).getRule(context, source,
                        typesetter);
            horizontal = ((RuleNode) node).isHorizontal();
        } else {
            throw new HelpingException(getLocalizer(), "TTP.BoxExpected");
        }

        CodeToken vskip = source.getControlSequence(context, typesetter);
        code = context.getCode(vskip);

        if (code == null) {
            throw new UndefinedControlSequenceException(
                context.esc(vskip.getName()));
        }

        FixedGlue skip;

        if (horizontal) {
            if (!(code instanceof HorizontalSkip)) {
                throw new HelpingException(getLocalizer(),
                    "TTP.BadGlueAfterLeaders");
            }
            skip = ((HorizontalSkip) code).getGlue(context, source, typesetter);
        } else {
            if (!(code instanceof VerticalSkip)) {
                throw new HelpingException(getLocalizer(),
                    "TTP.BadGlueAfterLeaders");
            }
            skip = ((VerticalSkip) code).getGlue(context, source, typesetter);
        }

        addNode(typesetter, node, skip);
    }

    /**
     * Finally make an appropriate node and add it to the typesetter.
     * 
     * @param typesetter the typesetter
     * @param node the node
     * @param skip the skip amount
     * @throws TypesetterException in case of an error
     */
    protected void addNode(Typesetter typesetter, OrientedNode node,
            FixedGlue skip) throws TypesetterException {

        typesetter.add(new AlignedLeadersNode(node, skip));
    }

}
