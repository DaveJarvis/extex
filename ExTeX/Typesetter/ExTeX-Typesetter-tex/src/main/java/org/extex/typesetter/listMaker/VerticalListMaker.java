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
import org.extex.typesetter.common.Parameter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * This class provides a maker for a vertical list.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
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
     * <p>
     * 679. When a box is being appended to the current vertical list, the
     * baselineskip calculation is handled by the append_to_vlist routine.
     * </p>
     * 
     * <pre>
     *   procedure append_to_vlist(b:pointer);
     *    var d: scaled;  {deficiency of space between baselines}
     *    p: pointer;  {a new glue specification}
     * begin if prev_depth&gt;ignore_depth then
     *    begin d ? width(baseline_skip)-prev_depth-height( b);
     *    if d&lt;line_skip_limit then p ? new_param_glue(line_skip_code)
     *      else begin p ? new_skip_param(baseline_skip_code); width(temp_ptr) ? d;  {temp_ptr=glue_ptr(p)}
     *      end ;
     *    link(tail) ? p; tail ? p;
     *    end ;
     * link(tail) ? b; tail ? b; prev_depth ? depth(b);
     * end ;
     * </pre>
     *
     * {@inheritDoc}
     */
    @Override
    public void addAndAdjust(NodeList nodes, TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        FixedDimen prevDepth = getPrevDepth();
        FixedGlue baselineSkip = context.getGlueOption(Parameter.BASELINESKIP);
        FixedGlue lineskip = context.getGlueOption(Parameter.LINESKIP);
        FixedDimen lineSkipLimit =
                context.getDimenOption(Parameter.LINESKIPLIMIT);
        Glue d;

        for (Node node : nodes) {
            if (node instanceof HorizontalListNode) {
                if (prevDepth != null) {
                    d = new Glue(baselineSkip);
                    d.subtract(prevDepth);
                    d.subtract(node.getHeight());
                    if (d.lt(lineSkipLimit)) {
                        add(lineskip);
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

    @Override
    public Mode getMode() {

        return Mode.VERTICAL;
    }

}
