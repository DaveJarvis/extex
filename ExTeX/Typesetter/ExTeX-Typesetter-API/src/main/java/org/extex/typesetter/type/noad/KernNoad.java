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

package org.extex.typesetter.type.noad;

import java.util.logging.Logger;

import org.extex.core.muskip.Mudimen;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.node.ExplicitKernNode;

/**
 * This Noad carries a kerning value in math units.
 * This value is translated into a KernNode
 * with the translated kerning value.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class KernNoad extends AbstractNoad {

    /**
     * The field <tt>kern</tt> contains the kerning. A positive value means a
     * right shift.
     */
    private Mudimen kern;

    /**
     * Creates a new object.
     *
     * @param kern the glue
     */
    public KernNoad(Mudimen kern) {

        this.kern = kern;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.noad.Noad#typeset(
     *      org.extex.typesetter.type.noad.Noad,
     *      org.extex.typesetter.type.noad.NoadList,
     *      int,
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.type.noad.util.MathContext,
     *      java.util.logging.Logger)
     */
    public void typeset(Noad previousNoad, NoadList noads,
            int index, NodeList list,
            MathContext mathContext, Logger logger)
            throws TypesetterException,
                ConfigurationException {

        if (previousNoad instanceof GlueNoad
                && ((GlueNoad) previousNoad).isKill()) {
            StyleNoad style = mathContext.getStyle();
            if (style == StyleNoad.SCRIPTSTYLE
                    || style == StyleNoad.SCRIPTSCRIPTSTYLE) {
                return;
            }
        }

        list.add(new ExplicitKernNode(mathContext.convert(kern), true));
    }

    /**
     * Add some information in the middle of the default toString method.
     *
     * @param sb the target string buffer
     * @param depth the recursion depth
     */
    @Override
    protected void toStringAdd(StringBuffer sb, int depth) {

        sb.append("kern");
        kern.toString(sb);
    }

}
