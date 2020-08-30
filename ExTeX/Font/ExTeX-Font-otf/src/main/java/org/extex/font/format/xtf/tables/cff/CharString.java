/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.cff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Container for each char string.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class CharString implements XMLWriterConvertible {

    /**
     * Is the bounding box calculate?
     */
    private boolean calculate = false;

    /**
     * The cff font.
     */
    private CffFont cffFont;

    /**
     * The hints.
     */
    private int hints = 0;

    /**
     * The index.
     */
    protected int idx;

    /**
     * The maximum x.
     */
    private int maxX = 0;

    /**
     * The maximum y.
     */
    private int maxY = 0;

    /**
     * The minimum x.
     */
    private int minX = 0;

    /**
     * The minimum y.
     */
    private int minY = 0;

    /**
     * Do not use char names (e.g. for subrs)
     */
    private boolean noname;

    /**
     * The t2 operators.
     */
    private final List<T2Operator> t2Ops = new ArrayList<T2Operator>();

    /**
     * The width.
     */
    protected Integer width = null;

    /**
     * Creates a new object.
     * 
     * Only for sub classes.
     */
    protected CharString() {

    }

    /**
     * Creates a new object.
     * 
     * @param cffFont TODO
     * @param idx The index.
     */
    public CharString(CffFont cffFont, int idx) {

        this(cffFont, idx, false);
    }

    /**
     * Creates a new object.
     * 
     * @param cffFont TODO
     * @param idx The index.
     * @param noname TODO
     */
    public CharString(CffFont cffFont, int idx, boolean noname) {

        this.cffFont = cffFont;
        this.idx = idx;
        this.noname = noname;
    }

    /**
     * Add a {@link T2Operator}.
     * 
     * @param op The t2 operator to add.
     */
    public void add(T2Operator op) {

        t2Ops.add(op);
    }

    /**
     * Add a hint count.
     */
    public void addHint() {

        hints++;
    }

    /**
     * Add hints.
     * 
     * @param cnt The count of hints to add.
     */
    public void addHints(int cnt) {

        hints += cnt;
    }

    /**
     * Calculate the bounding box.
     */
    private void calculate() {

        if (!calculate) {

            for (int i = 0, n = t2Ops.size(); i < n; i++) {
                T2Operator op = t2Ops.get(i);
                if (op instanceof CharStringCalc) {
                    CharStringCalc csc = (CharStringCalc) op;
                    csc.calculate(this);
                }
            }
            calculate = true;
        }
    }

    /**
     * Check, if the width is set.
     */
    public void checkWidth() {

        if (width == null && cffFont != null) {
            width = new Integer(cffFont.getDefaultWidthX());
        }
    }

    /**
     * @param index The index.
     * @return Returns the t2operator.
     * @see java.util.List#get(int)
     */
    public T2Operator get(int index) {

        return t2Ops.get(index);
    }

    /**
     * Returns the hints in the commands.
     * 
     * @return Returns the hints in the commands.
     */
    public int getActualHints() {

        return hints;

    }

    /**
     * Getter for cffFont.
     * 
     * @return the cffFont
     */
    public CffFont getCffFont() {

        return cffFont;
    }

    /**
     * Getter for idx.
     * 
     * @return the idx
     */
    public int getIdx() {

        return idx;
    }

    /**
     * Getter for maxX.
     * 
     * @return the maxX
     */
    public int getMaxX() {

        calculate();
        return maxX;
    }

    /**
     * Getter for maxY.
     * 
     * @return the maxY
     */
    public int getMaxY() {

        calculate();
        return maxY;
    }

    /**
     * Getter for minX.
     * 
     * @return the minX
     */
    public int getMinX() {

        calculate();
        return minX;
    }

    /**
     * Getter for minY.
     * 
     * @return the minY
     */
    public int getMinY() {

        calculate();
        return minY;
    }

    /**
     * Returns the name of the charstring.
     * 
     * @return Returns the name of the charstring.
     */
    public String getName() {

        if (cffFont != null) {
            return cffFont.getNameForPos(idx);
        }
        return "???";
    }

    /**
     * Getter for t2Ops.
     * 
     * @return the t2Ops
     */
    public List<T2Operator> getT2Ops() {

        return t2Ops;
    }

    /**
     * Getter for width.
     * 
     * @return the width
     */
    public Integer getWidth() {

        return width;
    }

    /**
     * @return Returns {@code true}, if no t2ops exits.
     * @see java.util.List#isEmpty()
     */
    public boolean isEmpty() {

        return t2Ops.isEmpty();
    }

    /**
     * @return Returns the t2ops iterator.
     * @see java.util.List#iterator()
     */
    public Iterator<T2Operator> iterator() {

        return t2Ops.iterator();
    }

    /**
     * Reset the hint counter.
     */
    public void resetHints() {

        hints = 0;
    }

    /**
     * Set the hints.
     * 
     * @param cnt The new hints.
     */
    public void setHints(int cnt) {

        hints = cnt;
    }

    /**
     * Set the maximum x.
     * 
     * @param x The x value.
     */
    public void setMX(int x) {

        if (x > maxX) {
            maxX = x;
        }
        if (x < minX) {
            minX = x;
        }
    }

    /**
     * Set the maximum y.
     * 
     * @param y The y value.
     */
    public void setMY(int y) {

        if (y > maxY) {
            maxY = y;
        }
        if (y < minY) {
            minY = y;
        }
    }

    /**
     * Setter for width.
     * 
     * @param width the width to set
     */
    public void setWidth(T2Number width) {

        if (width != null && cffFont != null) {
            this.width =
                    new Integer(cffFont.getNominalWidthX() + width.getInteger());
        }
    }

    /**
     * @return Returns the size of the t2ops.
     * @see java.util.List#size()
     */
    public int size() {

        return t2Ops.size();
    }

@Override
    public String toString() {

        StringBuilder buf = new StringBuilder();
        buf.append(getName()).append(" (").append(idx).append(")\n");
        for (int i = 0, n = t2Ops.size(); i < n; i++) {
            buf.append(t2Ops.get(i).toText()).append("\n");
        }
        return buf.toString();

    }

@Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("chars");
        writer.writeAttribute("id", idx);

        if (!noname) {
            writer.writeAttribute("name", getName());
        }
        writer.writeAttribute("width", width);

        for (int i = 0; i < t2Ops.size(); i++) {
            t2Ops.get(i).writeXML(writer);
        }
        writer.writeEndElement();

    }
}
