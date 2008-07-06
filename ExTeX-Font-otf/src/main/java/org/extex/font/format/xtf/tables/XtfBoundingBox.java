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

package org.extex.font.format.xtf.tables;

/**
 * Bounding box.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfBoundingBox {

    /**
     * The x-Max.
     */
    private short xMax;

    /**
     * The x-Min.
     */
    private short xMin;

    /**
     * The y-Max.
     */
    private short yMax;

    /**
     * The y-Min.
     */
    private short yMin;

    /**
     * Creates a new object.
     * 
     * @param xmin The x-min.
     * @param ymin The y-min.
     * @param xmax The x-max.
     * @param ymax The y-max.
     */
    public XtfBoundingBox(short xmin, short ymin, short xmax, short ymax) {

        xMin = xmin;
        yMin = ymin;
        xMax = xmax;
        yMax = ymax;

    }

    /**
     * Returns <code>true</code>, if the values are equals.
     * 
     * @param xmin The x-min.
     * @param ymin The y-min.
     * @param xmax The x-max.
     * @param ymax The y-max.
     * @return <code>true</code>, if the values are equals.
     */
    public boolean eq(int xmin, int ymin, int xmax, int ymax) {

        return eq((short) xmin, (short) ymin, (short) xmax, (short) ymax);
    }

    /**
     * Returns <code>true</code>, if the values are equals.
     * 
     * @param xmin The x-min.
     * @param ymin The y-min.
     * @param xmax The x-max.
     * @param ymax The y-max.
     * @return <code>true</code>, if the values are equals.
     */
    public boolean eq(short xmin, short ymin, short xmax, short ymax) {

        if (xMin == xmin && yMin == ymin && xMax == xmax && yMax == ymax) {
            return true;
        }
        return false;
    }

    /**
     * Returns the depth of the bounding box.
     * 
     * @return Returns the depth of the bounding box.
     */
    public int getDepth() {

        if (yMin < 0) {
            return -yMin;
        }
        return 0;
    }

    /**
     * Returns the height of the bounding box.
     * 
     * @return Returns the height of the bounding box.
     */
    public int getHeight() {

        return yMax;
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
     * Getter for xMin.
     * 
     * @return Returns the xMin.
     */
    public short getXMin() {

        return xMin;
    }

    /**
     * Getter for yMax.
     * 
     * @return Returns the yMax.
     */
    public short getYMax() {

        return yMax;
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
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer();
        buf.append("(").append(xMin).append(" ").append(yMin).append(") ");
        buf.append("(").append(xMax).append(" ").append(yMax).append(")");
        return buf.toString();
    }
}
