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
 * This paragraph shape represents a block with hanging indentation.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class HangingParagraphShape extends ParagraphShape {

    /**
     * The field <tt>hangafter</tt> contains the number of lines to start or
     * end hanging.
     */
    private int hangafter;

    /**
     * The field <tt>hangindent</tt> contains the amount of indentation.
     */
    private FixedDimen hangindent;

    /**
     * The field <tt>hsize</tt> contains the width of the line.
     */
    private FixedDimen hsize;

    /**
     * Creates a new object.
     *
     * @param theHangafter the number of lines to start or end hanging
     * @param theHangindent the amount of indentation
     * @param theHsize the width of the line
     */
    public HangingParagraphShape(final int theHangafter,
            final FixedDimen theHangindent, final FixedDimen theHsize) {

        super();
        this.hsize = theHsize;
        this.hangafter = theHangafter;
        this.hangindent = theHangindent;
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
    public FixedDimen getIndent(final int index) {

        if (hangafter > 0) {
            if (index >= hangafter) {
                return hangindent;
            }
        } else if (index < -hangafter) {
            return hangindent;
        }

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
    public FixedDimen getLength(final int index) {

        return hsize;
    }

    /**
     * Setter for hangafter.
     *
     * @param hangafter the hangafter to set.
     */
    public void setHangafter(final int hangafter) {

        this.hangafter = hangafter;
    }

    /**
     * Setter for hangindent.
     *
     * @param hangindent the hangindent to set.
     */
    public void setHangindent(final FixedDimen hangindent) {

        this.hangindent = hangindent;
    }

    /**
     * Setter for hsize.
     *
     * @param hsize the hsize to set.
     */
    public void setHsize(final FixedDimen hsize) {

        this.hsize = hsize;
    }

}
