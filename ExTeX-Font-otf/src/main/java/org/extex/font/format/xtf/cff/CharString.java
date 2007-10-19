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

package org.extex.font.format.xtf.cff;

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
 * @version $Revision$
 */
public class CharString implements XMLWriterConvertible {

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
     * The t2 operators.
     */
    private List<T2Operator> t2Ops = new ArrayList<T2Operator>();

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

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param idx The index.
     */
    public CharString(CffFont cffFont, int idx) {

        this.cffFont = cffFont;
        this.idx = idx;
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
     * Check, if the width is set.
     */
    public void checkWidth() {

        if (width == null) {
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
     * Returns the name of the charstring.
     * 
     * @return Returns the name of the charstring.
     */
    public String getName() {

        return cffFont.getNameForPos(idx);
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
     * @return Returns <code>true</code>, if no t2ops exits.
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
     * Setter for width.
     * 
     * @param width the width to set
     */
    public void setWidth(T2Number width) {

        if (width != null) {
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

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer();
        buf.append(getName()).append(" (").append(idx).append(")\n");
        for (int i = 0, n = t2Ops.size(); i < n; i++) {
            buf.append(t2Ops.get(i).toText()).append("\n");
        }
        return buf.toString();

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("chars");
        writer.writeAttribute("id", idx);

        writer.writeAttribute("name", getName());
        writer.writeAttribute("width", width);

        for (int i = 0; i < t2Ops.size(); i++) {
            t2Ops.get(i).writeXML(writer);
        }
        writer.writeEndElement();

    }
}
