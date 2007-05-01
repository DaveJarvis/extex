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

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;

/**
 * This paragraph shape represents a fixed block. It is aligned at 0pt on the
 * left and by the given width on the right.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class FixedParagraphShape extends ParagraphShape {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>hsize</tt> contains the width of the fixed block.
     */
    private FixedDimen hsize;

    /**
     * Creates a new object.
     *
     * @param theHsize the width
     */
    public FixedParagraphShape(FixedDimen theHsize) {

        super();
        this.hsize = theHsize;
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
     *
     * @see org.extex.typesetter.paragraphBuilder.ParagraphShape#getIndent(int)
     */
    public FixedDimen getIndent(int index) {

        return Dimen.ZERO_PT;
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
     *
     * @see org.extex.typesetter.paragraphBuilder.ParagraphShape#getLength(int)
     */
    public FixedDimen getLength(int index) {

        return hsize;
    }

    /**
     * Setter for hsize.
     *
     * @param theHsize the hsize to set.
     */
    public void setHsize(FixedDimen theHsize) {

        this.hsize = theHsize;
    }

}
