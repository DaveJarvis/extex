/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf;

/**
 * Bounding box.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfBoundingBox {

    /**
     * The x-Min.
     */
    private short xMin;

    /**
     * The y-Min.
     */
    private short yMin;

    /**
     * The x-Max.
     */
    private short xMax;

    /**
     * The y-Max.
     */
    private short yMax;

    /**
     * Creates a new object.
     *
     * @param xmin  The x-min.
     * @param ymin  The y-min.
     * @param xmax  The x-max.
     * @param ymax  The y-max. 
     */
    public XtfBoundingBox(final short xmin, final short ymin, final short xmax,
            final short ymax) {

        xMin = xmin;
        yMin = ymin;
        xMax = xmax;
        yMax = ymax;

    }

    /**
     * Returns <code>true</code>, if the values are equals.
     *
     * @param xmin  The x-min.
     * @param ymin  The y-min.
     * @param xmax  The x-max.
     * @param ymax  The y-max. 
     * @return <code>true</code>, if the values are equals.
     */
    public boolean eq(final short xmin, final short ymin, final short xmax,
            final short ymax) {

        if (xMin == xmin && yMin == ymin && xMax == xmax && yMax == ymax) {
            return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code>, if the values are equals.
     *
     * @param xmin  The x-min.
     * @param ymin  The y-min.
     * @param xmax  The x-max.
     * @param ymax  The y-max. 
     * @return <code>true</code>, if the values are equals.
     */
    public boolean eq(final int xmin, final int ymin, final int xmax,
            final int ymax) {

        return eq((short) xmin, (short) ymin, (short) xmax, (short) ymax);
    }

    /**
     * Getter for xMin.
     *
     * @return Returns the xMin.
     */
    public short getXMin() {

        return xMin;
    }

    /**
     * Getter for yMin.
     *
     * @return Returns the yMin.
     */
    public short getYMin() {

        return yMin;
    }

    /**
     * Getter for xMax.
     *
     * @return Returns the xMax.
     */
    public short getXMax() {

        return xMax;
    }

    /**
     * Getter for yMax.
     *
     * @return Returns the yMax.
     */
    public short getYMax() {

        return yMax;
    }

}
