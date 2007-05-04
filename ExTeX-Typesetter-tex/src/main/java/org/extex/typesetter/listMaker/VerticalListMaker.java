/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.listMaker;

import org.extex.core.Locator;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.ListManager;
import org.extex.typesetter.Mode;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * This class provides a maker for a vertical list.
 *
 * <doc name="baselineskip" type="register">
 * <h3>The Parameter <tt>\baselineskip</tt></h3>
 *
 * <p>
 *  The parameter <tt>\baselineskip</tt> contains the desirable skip between
 *  lines. The depth of the first line is subtracted from this value to
 *  determine the automatic skip value to be inserted. If this value is less
 *  than the value of <tt>\lineskiplimit</tt> then the value of
 *  <tt>\lineskip</tt> is used instead.
 * </p>
 * </doc>
 *
 * <doc name="lineskiplimit" type="register">
 * <h3>The Parameter <tt>\lineskiplimit</tt></h3>
 *
 * <p>
 *  The parameter <tt>\lineskiplimit</tt> contains the limit for the automatic
 *  inter line skip inserted. The automatic inter line skip is defendant on the
 *  value of the parameter <tt>\baselineskip</tt>. It the automatic inter line
 *  skip is less than <tt>\lineskiplimit</tt> the value of <tt>\lineskip</tt>
 *  is used instead.
 * </p>
 * </doc>
 *
 * <doc name="lineskip" type="register">
 * <h3>The Parameter <tt>\lineskip</tt></h3>
 *
 * <p>
 *  The parameter <tt>\lineskip</tt> contains the interline skip which is used
 *  when the adjacent lines would come together too close. This is the case when
 *  the interline glue inserted automatically is less than the value of
 *  <tt>\lineskiplimit</tt>.
 * </p>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class VerticalListMaker extends InnerVerticalListMaker {

    /**
     * Creates a new object.
     *
     * @param manager the manager to ask for global changes
     * @param locator the locator
     */
    public VerticalListMaker(ListManager manager, Locator locator) {

        super(manager, locator);
    }

    /**
     * Add a node list to the current list maker and adjust the spacing between
     * the elements of the list.
     *
     * <i>
     *  <p>
     *   679.  When a box is being appended to the current vertical list,
     *   the baselineskip calculation is handled by the append_to_vlist routine.
     *  </p>
     *  <pre>
     *   procedure append_to_vlist(b:pointer);
     *    var d: scaled;  {deficiency of space between baselines}
     *    p: pointer;  {a new glue specification}
     * begin if prev_depth>ignore_depth then
     *    begin d ? width(baseline_skip)-prev_depth-height( b);
     *    if d&lt;line_skip_limit then p ? new_param_glue(line_skip_code)
     *      else begin p ? new_skip_param(baseline_skip_code); width(temp_ptr) ? d;  {temp_ptr=glue_ptr(p)}
     *      end ;
     *    link(tail) ? p; tail ? p;
     *    end ;
     * link(tail) ? b; tail ? b; prev_depth ? depth(b);
     * end ;
     *  <pre>
     * </i>
     *
     *
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.ListMaker#addAndAdjust(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void addAndAdjust(NodeList nodes,
            TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        FixedDimen prevDepth = getPrevDepth();
        int size = nodes.size();
        FixedGlue baselineSkip = context.getGlueOption("baselineskip");
        FixedDimen lineSkipLimit = context.getDimenOption("lineskiplimit");
        Node node;
        Glue d;

        for (int i = 0; i < size; i++) {
            node = nodes.get(i);
            if (node instanceof HorizontalListNode) {
                if (prevDepth != null) {
                    d = new Glue(baselineSkip);
                    d.subtract(prevDepth);
                    d.subtract(node.getHeight());
                    if (d.lt(lineSkipLimit)) {
                        add(context.getGlueOption("lineskip"));
                    } else {
                        add(d);
                    }
                }

                prevDepth = node.getDepth();
            }
            add(node);
        }
        setPrevDepth(prevDepth);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.listMaker.InnerVerticalListMaker#getMode()
     */
    public Mode getMode() {

        return Mode.VERTICAL;
    }

}
