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

package org.extex.font.format.dvi;

/**
 * Values for dvi.
 * 
 * <p>
 * The values of <code>h</code>, <code>v</code>, <code>w</code>, <code>x</code>,
 * <code>y</code>, and <code>z</code> are signed integers having up to 32 bits,
 * including the sign.
 * </p>
 * 
 * <p>
 * Since they represent physical distances, there is a small unit of measurement
 * such that increasing <code>h</code> by 1 means moving a certain tiny distance
 * to the right. The actual unit of measurement is variable.
 * </p>
 * 
 * <p>
 * The current font <code>f</code> is an integer; this value is changed only by
 * <code>fnt</code> and <code>fnt_num</code> commands.
 * <p>
 * 
 * <p>
 * The current position on the page is given by two numbers called the
 * horizontal and vertical coordinates, <code>h</code> and <code>v</code>.
 * </p>
 * 
 * <p>
 * Both coordinates are zero at the upper left corner of the page; moving to the
 * right corresponds to increasing the horizontal coordinate, and moving down
 * corresponds to increasing the vertical coordinate. Thus, the coordinates are
 * essentially Cartesian, except that vertical directions are flipped; the
 * Cartesian version of <code>(h,v)</code> would be <code>(h,-v)</code>.
 * </p>
 * 
 * <p>
 * The current spacing amounts are given by four numbers <code>w</code>,
 * <code>x</code>, <code>y</code>, and <code>z</code>, where <code>w</code> and
 * <code>x</code> are used for horizontal spacing and where <code>y</code> and
 * <code>z</code> are used for vertical spacing.
 * </p>
 * 
 * <p>
 * There is a stack containing <code>(h,v,w,x,y,z)</code> values; the DVI
 * commands <code>push</code> and <code>pop</code> are used to change the
 * current level of operation. Note that the current font <code>f</code> is not
 * pushed and popped; the stack contains only information about positioning.
 * <p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class DviValues {

    /**
     * f: the font number
     */
    private int f;

    /**
     * h: horizontal
     */
    private int h;

    /**
     * v: vertical
     */
    private int v;

    /**
     * w: horizontal spacing
     */
    private int w;

    /**
     * x: horizontal spacing
     */
    private int x;

    /**
     * y: vertical spacing
     */
    private int y;

    /**
     * z: vertical spacing
     */
    private int z;

    /**
     * Create a new object.
     */
    public DviValues() {

        clear();
    }

    /**
     * Create a new object.
     * 
     * @param val the new values.
     */
    public DviValues(final DviValues val) {

        h = val.h;
        v = val.v;
        w = val.w;
        x = val.x;
        y = val.y;
        z = val.z;
    }

    /**
     * @param ah The h to add.
     */
    public void addH(final int ah) {

        h += ah;
    }

    /**
     * @param av The v to add.
     */
    public void addV(final int av) {

        v += av;
    }

    /**
     * clear all values (without f!).
     */
    public void clear() {

        h = 0;
        v = 0;
        w = 0;
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * Returns the f.
     * 
     * @return Returns the f.
     */
    public int getF() {

        return f;
    }

    /**
     * Returns the h.
     * 
     * @return Returns the h.
     */
    public int getH() {

        return h;
    }

    /**
     * Returns the v.
     * 
     * @return Returns the v.
     */
    public int getV() {

        return v;
    }

    /**
     * Returns the w.
     * 
     * @return Returns the w.
     */
    public int getW() {

        return w;
    }

    /**
     * Returns the x.
     * 
     * @return Returns the x.
     */
    public int getX() {

        return x;
    }

    /**
     * Returns the y.
     * 
     * @return Returns the y.
     */
    public int getY() {

        return y;
    }

    /**
     * Returns the z.
     * 
     * @return Returns the z.
     */
    public int getZ() {

        return z;
    }

    /**
     * Check, if the values (h,v,w,x,z,z) all zero.
     * 
     * @return Returns <code>true</code>, if all values are zero.
     */
    public boolean isClear() {

        if (h == 0 && v == 0 && w == 0 && x == 0 && y == 0 && z == 0) {
            return true;
        }
        return false;
    }

    /**
     * @param af The f to set.
     */
    public void setF(final int af) {

        f = af;
    }

    /**
     * @param ah The h to set.
     */
    public void setH(final int ah) {

        h = ah;
    }

    /**
     * @param av The v to set.
     */
    public void setV(final int av) {

        v = av;
    }

    /**
     * set the values (without f!)
     * 
     * @param val the new values
     */
    public void setValues(final DviValues val) {

        h = val.h;
        v = val.v;
        w = val.w;
        x = val.x;
        y = val.y;
        z = val.z;
    }

    /**
     * @param aw The w to set.
     */
    public void setW(final int aw) {

        w = aw;
    }

    /**
     * @param ax The x to set.
     */
    public void setX(final int ax) {

        x = ax;
    }

    /**
     * @param ay The y to set.
     */
    public void setY(final int ay) {

        y = ay;
    }

    /**
     * @param az The z to set.
     */
    public void setZ(final int az) {

        z = az;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder buf = new StringBuilder();
        buf.append("f=").append(f);
        buf.append(" h=").append(h);
        buf.append(" v=").append(v);
        buf.append(" w=").append(w);
        buf.append(" x=").append(x);
        buf.append(" y=").append(y);
        buf.append(" z=").append(z);
        return buf.toString();
    }
}
