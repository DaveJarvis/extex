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

package org.extex.typesetter.paragraphBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;

/**
 * This class defines an arbitrary paragraph shape.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class ParagraphShape implements Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>points</tt> contains the list of reference points for
     * the parshape. There are always two consecutive points for the left and
     * the right margin.
     */
    private List<FixedDimen> points = new ArrayList<FixedDimen>();

    /**
     * Creates a new object.
     */
    public ParagraphShape() {

        super();
    }

    /**
     * Add another pair of points.
     *
     * @param left the left margin
     * @param right the right margin
     */
    public void add(Dimen left, Dimen right) {

        points.add(left);
        points.add(right);
    }

    /**
     * Getter for the left hand margin of a certain position.
     * The position is given by an index into the list.
     * If the index points past the end of th list then the last entry is
     * repeated. If the index is negative then 0pt is returned.
     *
     * @param index the index of the position
     *
     * @return the left hand margin
     */
    public FixedDimen getIndent(int index) {

        if (index < 0) {
            return Dimen.ZERO_PT;
        }
        int i = (points.size() - 1) / 2;
        return points.get((index > i ? i : index) * 2);
    }

    /**
     * Getter for the right hand margin of a certain position.
     * The position is given by an index into the list.
     * If the index points past the end of th list then the last entry is
     * repeated. If the index is negative then 0pt is returned.
     *
     * @param index the index of the position
     *
     * @return the right hand margin
     */
    public FixedDimen getLength(int index) {

        if (index < 0) {
            return Dimen.ZERO_PT;
        }
        int i = (points.size() - 1) / 2;
        return points.get((index > i ? i : index) * 2 + 1);
    }

    /**
     * Getter for the number of points.
     *
     * @return the number of points stored in this instance
     */
    public long getSize() {

        return points.size();
    }

}