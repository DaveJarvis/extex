/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

import org.extex.core.dimen.Dimen;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.math.MathDelimiter;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * This Noad carries a delimiter which is set in the middle between math
 * material surrounding it. This delimiter adjusts its height to the height of
 * the surrounding material.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class MiddleNoad extends LeftNoad {

    /**
     * The field <tt>delimiter</tt> contains the middle delimiter.
     */
    private MathDelimiter delimiter;

    /**
     * The field <tt>noad</tt> contains the material before this noad.
     */
    private LeftNoad noadPre;

    /**
     * The field <tt>noadPost</tt> contains the material after this noad.
     */
    private Noad noadPost;

    /**
     * Creates a new object.
     *
     * @param noadPre the material before this noad
     * @param delimiter the delimiter
     * @param noadPost the material after this noad
     */
    public MiddleNoad(LeftNoad noadPre, MathDelimiter delimiter,
            Noad noadPost) {

        super(noadPre, delimiter);
        this.delimiter = delimiter;
        this.noadPre = noadPre;
        this.noadPost = noadPost;
    }

    /**
     * Add some information in the middle of the default toString method.
     *
     * @param sb the target string buffer
     * @param depth the recursion depth
     *
     * @see org.extex.typesetter.type.noad.AbstractNoad#toStringAdd(
     *      java.lang.StringBuffer,
     *      int)
     */
    @Override
    public void toStringAdd(StringBuffer sb, int depth) {

        sb.append("middle");
        delimiter.toString(sb);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.noad.LeftNoad#typeset(
     *      org.extex.typesetter.type.noad.Noad,
     *      org.extex.typesetter.type.noad.NoadList,
     *      int,
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.type.noad.util.MathContext,
     *      java.util.logging.Logger,
     *      org.extex.core.dimen.Dimen,
     *      org.extex.core.dimen.Dimen)
     */
    @Override
    public void typeset(Noad previousNoad, NoadList noads,
            int index, NodeList list,
            MathContext mathContext, Logger logger,
            Dimen height, Dimen depth)
            throws TypesetterException {

        getSpacingClass().addClearance(
            (previousNoad != null ? previousNoad.getSpacingClass() : null),
            list, mathContext);

        HorizontalListNode hlist = new HorizontalListNode();

        noadPost
            .typeset(previousNoad, noads, index, hlist, mathContext, logger);
        height.max(hlist.getHeight());
        depth.max(hlist.getDepth());

        noadPre.typeset(previousNoad, noads, index, list, mathContext, logger,
            height, depth);

        delimiter
            .typeset(previousNoad, noads, index, list, mathContext, logger);
        list.add(hlist);
    }

}
