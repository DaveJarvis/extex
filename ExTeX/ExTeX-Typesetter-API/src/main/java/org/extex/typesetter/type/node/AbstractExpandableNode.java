/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.node;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.FixedGlueComponent;
import org.extex.core.glue.Glue;
import org.extex.core.glue.WideGlue;
import org.extex.typesetter.type.OrientedNode;

/**
 * This node represents a <logo>TeX</logo> "glue" node.
 * <p>
 * For the document writer it acts like a kerning node. The width contains the
 * distance to add.
 * </p>
 * <p>
 * The stretchability is adjusted by the typesetter and the width is adjusted
 * accordingly.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public abstract class AbstractExpandableNode extends AbstractNode
        implements
            OrientedNode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>horizontal</tt> contains the indicator that the expansion
     * takes place in horizontal direction. Otherwise the expansion occurs in
     * vertical direction.
     */
    private boolean horizontal;

    /**
     * The field <tt>size</tt> contains the glue specification for this node.
     * The natural size of the glue is the initial width of this node.
     */
    private Glue size;

    /**
     * Creates a new object. The size is used to determine the width in
     * horizontal mode and the height in vertical mode.
     * 
     * @param size the actual size
     * @param horizontal indicator that the glue is used in horizontal mode
     */
    public AbstractExpandableNode(FixedDimen size, boolean horizontal) {

        super(Dimen.ZERO_PT);
        this.size = new Glue(size);
        this.horizontal = horizontal;

        if (horizontal) {
            setWidth(this.size.getLength());
        } else {
            setHeight(this.size.getLength());
        }
    }

    /**
     * Creates a new object. The size is used to determine the width in
     * horizontal mode and the height in vertical mode.
     * 
     * @param size the actual size
     * @param horizontal indicator that the glue is used in horizontal mode
     */
    public AbstractExpandableNode(FixedGlue size, boolean horizontal) {

        super(Dimen.ZERO_PT);
        this.size = new Glue(size);
        this.horizontal = horizontal;

        if (horizontal) {
            setWidth(this.size.getLength());
        } else {
            setHeight(this.size.getLength());
        }
    }

    /**
     * Add the flexible depth of the current node to the given glue.
     * 
     * @param glue the glue to add to.
     * 
     * @see org.extex.typesetter.type.Node#addDepthTo(
     *      org.extex.core.glue.WideGlue)
     */
    @Override
    public void addDepthTo(WideGlue glue) {

        if (horizontal) {
            glue.add(getDepth());
        } else {
            glue.add(this.size);
        }
    }

    /**
     * Add the flexible height of the current node to the given glue.
     * 
     * @param glue the glue to add to.
     * 
     * @see org.extex.typesetter.type.Node#addHeightTo(
     *      org.extex.core.glue.WideGlue)
     */
    @Override
    public void addHeightTo(WideGlue glue) {

        if (horizontal) {
            glue.add(getHeight());
        } else {
            glue.add(this.size);
        }
    }

    /**
     * Add the flexible width of the current node to the given glue.
     * 
     * @param glue the glue to add to.
     * 
     * @see org.extex.typesetter.type.Node#addWidthTo(
     *      org.extex.core.glue.WideGlue)
     */
    @Override
    public void addWidthTo(WideGlue glue) {

        if (horizontal) {
            glue.add(this.size);
        } else {
            glue.add(getWidth());
        }
    }

    /**
     * Getter for size.
     * 
     * @return the size
     */
    public FixedGlue getSize() {

        return this.size;
    }

    /**
     * Getter for horizontal.
     * 
     * @return the horizontal
     */
    public boolean isHorizontal() {

        return horizontal;
    }

    /**
     * Setter for the size
     * 
     * @param skip the new value
     */
    public void setSize(FixedGlue skip) {

        size.set(skip);
    }

    /**
     * Adjust the height of a flexible node.
     * 
     * @param height the desired height
     * @param sum the total sum of the glues
     * 
     * @see org.extex.typesetter.type.node.AbstractNode#spreadHeight(
     *      org.extex.core.dimen.FixedDimen, FixedGlueComponent)
     */
    @Override
    public void spreadHeight(FixedDimen height, FixedGlueComponent sum) {

        if (horizontal) {
            return;
        }

        long adjust = computeAdjustment(height.getValue(), this.size, sum);
        if (adjust != 0) {
            Dimen w = new Dimen(getHeight());
            w.add(adjust);
            setHeight(w);
        }
    }

    /**
     * Adjust the width of the flexible node.
     * 
     * @param width the desired with
     * @param sum the total sum of the glues
     * 
     * @see org.extex.typesetter.type.Node#spreadWidth(
     *      org.extex.core.dimen.FixedDimen,
     *      org.extex.core.glue.FixedGlueComponent)
     */
    @Override
    public void spreadWidth(FixedDimen width, FixedGlueComponent sum) {

        if (!horizontal) {
            return;
        }

        long adjust = computeAdjustment(width.getValue(), this.size, sum);
        if (adjust != 0) {
            Dimen w = new Dimen(getWidth());
            w.add(adjust);
            setWidth(w);
        }
    }

}
