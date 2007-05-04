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

package org.extex.color.model;

import org.extex.color.Color;
import org.extex.color.ColorUtil;
import org.extex.color.ColorVisitor;
import org.extex.core.exception.GeneralException;

/**
 * This class implements a color specification in grayscale mode with an alpha
 * channel.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4446 $
 */
public class GrayscaleColor implements Color {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The field <tt>alpha</tt> contains the alpha channel of the color.
     * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
     */
    private int alpha;

    /**
     * The field <tt>gray</tt> contains the gray value of the color.
     * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
     */
    private int gray;

    /**
     * Creates a new object.
     *
     * @param thegray the gray channel
     * @param theAlpha the alpha channel
     */
    protected GrayscaleColor(int thegray, int theAlpha) {

        super();
        this.gray = (thegray < 0 ? 0 : thegray < MAX_VALUE
                ? thegray
                : MAX_VALUE);
        this.alpha = (theAlpha < 0 ? 0 : theAlpha < MAX_VALUE
                ? theAlpha
                : MAX_VALUE);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param   obj   the reference object with which to compare.
     * @return  <code>true</code> if this object is the same as the obj
     *          argument; <code>false</code> otherwise.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {

        if (!(obj instanceof GrayscaleColor)) {
            return false;
        }
        GrayscaleColor other = (GrayscaleColor) obj;
        return gray == other.getGray() && alpha == other.getAlpha();
    }

    /**
     * Getter for the alpha channel.
     * The range of the value is 0x00 to 0xffff.
     *
     * @return the alpha channel
     *
     * @see org.extex.color.Color#getAlpha()
     */
    public int getAlpha() {

        return alpha;
    }

    /**
     * Getter for the gray value.
     * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
     *
     * @return the gray value.
     */
    public int getGray() {

        return gray;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return  a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        return (gray << 2) | (alpha << 3);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object.
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        ColorUtil.formatAlpha(sb, alpha);
        sb.append("gray {");
        ColorUtil.formatComponent(sb, gray);
        sb.append("}");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.color.Color#visit(
     *      org.extex.color.ColorVisitor,
     *      java.lang.Object)
     */
    public Object visit(ColorVisitor visitor, Object argument)
            throws GeneralException {

        return visitor.visitGray(this, argument);
    }

}
