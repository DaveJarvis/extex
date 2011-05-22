/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.core.glue;

import java.io.Serializable;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;

/**
 * This class provides the basic data type of a stretchable and shrinkable
 * quantity of length.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4399 $
 */
public class Glue implements Serializable, FixedGlue {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>length</tt> contains the natural length of the glue.
     */
    private Dimen length;

    /**
     * The field <tt>shrink</tt> contains the shrink specification.
     */
    private GlueComponent shrink;

    /**
     * The field <tt>stretch</tt> contains the stretch specification.
     */
    private GlueComponent stretch;

    /**
     * Creates a new object with a zero length.
     */
    public Glue() {

        this.length = new Dimen(0);
        this.stretch = new GlueComponent(0);
        this.shrink = new GlueComponent(0);
    }

    /**
     * Creates a new object with a fixed length.
     * 
     * @param theLength the natural length
     */
    public Glue(FixedDimen theLength) {

        this.length = new Dimen(theLength);
        this.stretch = new GlueComponent(0);
        this.shrink = new GlueComponent(0);
    }

    /**
     * Creates a new object from the three components.
     * 
     * @param theLength the natural length
     * @param theStretch the stretch specification
     * @param theShrink the shrink specification
     */
    public Glue(FixedDimen theLength, FixedDimen theStretch,
            FixedDimen theShrink) {

        this.length = new Dimen(theLength);
        this.stretch = new GlueComponent(theStretch.getValue());
        this.shrink = new GlueComponent(theShrink.getValue());
    }

    /**
     * Creates a new object from the three components.
     * 
     * @param theLength the natural length
     * @param theStretch the stretch specification
     * @param theShrink the shrink specification
     */
    public Glue(FixedDimen theLength, FixedGlueComponent theStretch,
            FixedGlueComponent theShrink) {

        this.length = new Dimen(theLength);
        this.stretch = new GlueComponent(theStretch);
        this.shrink = new GlueComponent(theShrink);
    }

    /**
     * Creates a new object as copy of another glue. if the given glue is
     * <code>null</code> then the new glue is initialized to the glue with
     * length 0 and no strechability and shrinkability.
     * 
     * @param glue the glue to clone
     */
    public Glue(FixedGlue glue) {

        if (glue != null) {
            this.length = new Dimen(glue.getLength());
            this.stretch = new GlueComponent(glue.getStretch());
            this.shrink = new GlueComponent(glue.getShrink());
        } else {
            this.length = new Dimen(0);
            this.stretch = new GlueComponent(0);
            this.shrink = new GlueComponent(0);
        }
    }

    /**
     * Creates a new object from a fixed length.
     * 
     * @param theLength the natural length in scaled point
     */
    public Glue(long theLength) {

        this.length = new Dimen(theLength);
        this.stretch = new GlueComponent(0);
        this.shrink = new GlueComponent(0);
    }

    /**
     * Add a dimen to this one glue. The addition is performed independently on
     * the components.
     * 
     * @param g the glue to add
     */
    public void add(FixedDimen g) {

        this.length.add(g);
    }

    /**
     * Add another glue to this one. The addition is performed independently on
     * the components.
     * 
     * @param g the glue to add
     */
    public void add(FixedGlue g) {

        this.length.add(g.getLength());
        this.stretch.add(g.getStretch());
        this.shrink.add(g.getShrink());
    }

    /**
     * Make a copy of this object.
     * 
     * @return a new instance with the same internal values
     */
    @Override
    public Glue copy() {

        return new Glue(length.copy(), stretch.copy(), shrink.copy());
    }

    /**
     * Test that the given Glue is equal to a given one. This comparison
     * involves the comparisons of the length, the stretch component, and the
     * shrink component.
     * 
     * @param glue the glue to compare with
     * 
     * @return <code>true</code> iff they are the same
     * 
     * @see org.extex.core.glue.FixedGlue#eq(org.extex.core.glue.FixedGlue)
     */
    @Override
    public boolean eq(FixedGlue glue) {

        return length.eq(glue.getLength()) && stretch.eq(glue.getStretch())
                && shrink.eq(glue.getShrink());
    }

    /**
     * Compare this value with a given glue and return <code>true</code> iff the
     * current length is greater or equal than the given length.
     * 
     * @param x the value to compare to
     * 
     * @return <code>true</code> iff the current length is greater or equal than
     *         the given one
     */
    public boolean ge(FixedDimen x) {

        return this.length.ge(x);
    }

    /**
     * Getter for the length. Note that the value returned is independent from
     * the original object. Changing its value does not affect the length of the
     * glue.
     * 
     * @return the natural length
     * 
     * @see org.extex.core.glue.FixedGlue#getLength()
     */
    @Override
    public FixedDimen getLength() {

        return new Dimen(length.getValue());
    }

    /**
     * Getter for shrink. Note that the value returned is independent from the
     * original object. Changing its value does not affect the shrink of the
     * glue.
     * 
     * @return the shrink.
     * 
     * @see org.extex.core.glue.FixedGlue#getShrink()
     */
    @Override
    public FixedGlueComponent getShrink() {

        return shrink;
    }

    /**
     * Getter for stretch. Note that the value returned is independent from the
     * original object. Changing its value does not affect the stretch of the
     * glue.
     * 
     * @return the stretch.
     * 
     * @see org.extex.core.glue.FixedGlue#getStretch()
     */
    @Override
    public FixedGlueComponent getStretch() {

        return stretch;
    }

    /**
     * Compare this value with a given glue and return <code>true</code> iff the
     * current length is greater than the given length.
     * 
     * @param x the value to compare to
     * 
     * @return <code>true</code> iff the current length is greater than the
     *         given one
     */
    public boolean gt(FixedDimen x) {

        return this.length.gt(x);
    }

    /**
     * Compare this value with a given glue and return <code>true</code> iff the
     * current length is less or equal than the given length.
     * 
     * @param x the value to compare to
     * 
     * @return <code>true</code> iff the current length is less or equal than
     *         the given one
     */
    public boolean le(FixedDimen x) {

        return this.length.le(x);
    }

    /**
     * Compare this value with a given glue and return <code>true</code> iff the
     * current length is less than the given length.
     * 
     * @param x the value to compare to
     * 
     * @return <code>true</code> iff the current length is less than the given
     *         one
     */
    public boolean lt(FixedDimen x) {

        return this.length.lt(x);
    }

    /**
     * Multiply the normal size by an integer fraction.
     * <p>
     * <i>length</i> = <i>length</i> * <i>nom</i> / <i>denom</i>
     * </p>
     * 
     * @param nom nominator
     * @param denom denominator
     */
    public void multiply(long nom, long denom) {

        this.length.multiply(nom, denom);
    }

    /**
     * Multiply all components by an integer fraction.
     * 
     * @param nom nominator
     * @param denom denominator
     */
    public void multiplyAll(long nom, long denom) {

        this.length.multiply(nom, denom);
        this.shrink.multiply(nom, denom);
        this.stretch.multiply(nom, denom);
    }

    /**
     * Multiply the shrink component by an integer fraction.
     * <p>
     * <i>shrink</i> = <i>shrink</i> * <i>nom</i> / <i>denom</i>
     * </p>
     * 
     * @param nom nominator
     * @param denom denominator
     */
    public void multiplyShrink(long nom, long denom) {

        this.shrink.multiply(nom, denom);
    }

    /**
     * Multiply the stretch component by an integer fraction.
     * <p>
     * <i>stretch</i> = <i>stretch</i> * <i>nom</i> / <i>denom</i>
     * </p>
     * 
     * @param nom nominator
     * @param denom denominator
     */
    public void multiplyStretch(long nom, long denom) {

        this.stretch.multiply(nom, denom);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.glue.FixedGlue#ne(org.extex.core.glue.FixedGlue)
     */
    @Override
    public boolean ne(FixedGlue glue) {

        return length.ne(glue.getLength()) || stretch.ne(glue.getStretch())
                || shrink.ne(glue.getShrink());
    }

    /**
     * Negate the value. This is the same as multiplying with -1.
     * 
     */
    public void negateLength() {

        this.length.negate();
    }

    /**
     * Set the glue value to a non-stretchable and non-shrinkable length.
     * 
     * @param theLength the new length
     */
    public void set(FixedDimen theLength) {

        this.length.set(theLength);
        this.shrink.set(0);
        this.stretch.set(0);
    }

    /**
     * Set the glue value.
     * 
     * @param theLength the new length
     */
    public void set(FixedGlue theLength) {

        this.length.set(theLength.getLength());
        this.shrink.set(theLength.getShrink());
        this.stretch.set(theLength.getStretch());
    }

    /**
     * Setter for the length component
     * 
     * @param x the new length component
     */
    public void setLength(FixedDimen x) {

        length.set(x);
    }

    /**
     * Setter for the shrink component
     * 
     * @param x the new shrink component
     */
    public void setShrink(FixedDimen x) {

        shrink.set(x);
    }

    /**
     * Setter for the stretch component
     * 
     * @param x the new stretch component
     */
    public void setStretch(FixedDimen x) {

        stretch.set(x);
    }

    /**
     * Subtract a Glue component from this glue. The subtraction is performed on
     * the length only.
     * 
     * @param g the glue to subtract
     */
    public void subtract(FixedDimen g) {

        this.length.subtract(g);
    }

    /**
     * Subtract another glue to this one. The subtraction is performed
     * independently on the components.
     * 
     * @param g the glue to add
     */
    public void subtract(FixedGlue g) {

        this.length.add(g.getLength());
        this.stretch.add(g.getStretch());
        this.shrink.add(g.getShrink());
    }

    /**
     * Determine the printable representation of the object. The value returned
     * is exactly the string which would be produced by <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> to print the skip register.
     * 
     * @return the string representation of this glue
     * @see "<logo>T<span style=
     *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     *      >e</span>X</logo> &ndash; The Program [178,177]"
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(length.toString());
        if (stretch.getValue() != 0) {
            sb.append(" plus ");
            sb.append(stretch.toString());
        }
        if (shrink.getValue() != 0) {
            sb.append(" minus ");
            sb.append(shrink.toString());
        }
        return sb.toString();
    }

}
