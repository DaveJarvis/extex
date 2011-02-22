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

package org.extex.core.muskip;

import java.io.Serializable;

import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlueComponent;
import org.extex.core.glue.GlueComponent;

/**
 * This class provides a skip value with a variable length of order 0. The
 * actual length is a multiple of math units (mu).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public class Muskip extends Mudimen implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>kill</tt> contains the indicator that the following glue
     * might be killed.
     */
    private boolean kill;

    /**
     * The field <tt>shrink</tt> contains the shrinkability specification.
     */
    private GlueComponent shrink = new GlueComponent(0);

    /**
     * The field <tt>stretch</tt> contains the stretchability specification.
     */
    private GlueComponent stretch = new GlueComponent(0);

    /**
     * Creates a new object. All components are 0.
     */
    public Muskip() {

        kill = false;
    }

    /**
     * Creates a new object. All components are 0.
     * 
     * @param kill the kill indicator
     */
    public Muskip(boolean kill) {

        this.kill = kill;
    }

    /**
     * Creates a new object. Strechablity and shrinkability are 0.
     * 
     * @param theLength the natural length
     */
    public Muskip(FixedDimen theLength) {

        super(theLength.getValue());
        this.kill = false;
    }

    /**
     * Creates a new object. Strechablity and shrinkability are 0.
     * 
     * @param theLength the natural length
     */
    public Muskip(long theLength) {

        super(theLength);
        this.kill = false;
    }

    /**
     * Creates a new object.
     * 
     * @param theLength the natural length
     * @param theStretch the stretchability
     * @param theShrink the shrinkability
     */
    public Muskip(FixedDimen theLength, FixedGlueComponent theStretch,
            FixedGlueComponent theShrink) {

        super(theLength.getValue());
        if (theStretch != null) {
            this.stretch = new GlueComponent(theStretch);
        }
        if (theShrink != null) {
            this.shrink = new GlueComponent(theShrink);
        }
        this.kill = false;
    }

    /**
     * Creates a new object.
     * 
     * @param theLength the natural length
     * @param theStretch the stretchability
     * @param theShrink the shrinkability
     * @param kill the indicator for the killing behavior
     */
    public Muskip(FixedGlueComponent theLength, FixedGlueComponent theStretch,
            FixedGlueComponent theShrink, boolean kill) {

        super(theLength.getValue());
        this.stretch = new GlueComponent(theStretch);
        this.shrink = new GlueComponent(theShrink);
        this.kill = kill;
    }

    /**
     * Creates a new object.
     * 
     * @param x the other muskip
     */
    public Muskip(Muskip x) {

        super(x.getLength().getValue());
        this.stretch = new GlueComponent(x.stretch);
        this.shrink = new GlueComponent(x.shrink);
        this.kill = false;
    }

    /**
     * Add another muglue to this one. The addition is performed independently
     * on the components.
     * 
     * @param ms the muglue to add
     */
    public void add(Muskip ms) {

        super.add(ms.getLength().getValue());
        this.stretch.add(ms.getStretch());
        this.shrink.add(ms.getShrink());
    }

    /**
     * Getter for shrink.
     * 
     * @return the shrink
     */
    public GlueComponent getShrink() {

        return this.shrink;
    }

    /**
     * Getter for stretch.
     * 
     * @return the stretch
     */
    public GlueComponent getStretch() {

        return this.stretch;
    }

    /**
     * Getter for kill.
     * 
     * @return the kill
     */
    public boolean isKill() {

        return this.kill;
    }

    /**
     * Check that the muskip has natural length zero and no stretch and shrink
     * component.
     * 
     * @return <code>true</code> iff the register is zero
     */
    @Override
    public boolean isZero() {

        return super.isZero() && stretch.eq(GlueComponent.ZERO)
                && shrink.eq(GlueComponent.ZERO);
    }

    /**
     * Multiply all components by an integer fraction.
     * 
     * @param nom nominator
     * @param denom denominator
     */
    @Override
    public void multiply(long nom, long denom) {

        super.multiply(nom, denom);
        this.shrink.multiply(nom, denom);
        this.stretch.multiply(nom, denom);
    }

    /**
     * Setter for kill.
     * 
     * @param kill the kill to set
     */
    public void setKill(boolean kill) {

        this.kill = kill;
    }

    /**
     * Return the string representation of the instance.
     * 
     * @return the string representation of this glue
     * @see "<logo>T<span style=
     *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     *      >e</span>X</logo> &ndash; The Program [???]"
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        toString(sb);
        return sb.toString();
    }

    /**
     * Append the string representation of the instance to a string buffer.
     * 
     * @param sb the target string buffer
     */
    @Override
    public void toString(StringBuffer sb) {

        super.toString(sb);
        if (stretch.ne(GlueComponent.ZERO)) {
            sb.append(" plus ");
            stretch.toString(sb, 'm', 'u');
        }
        if (shrink.ne(GlueComponent.ZERO)) {
            sb.append(" minus ");
            shrink.toString(sb, 'm', 'u');
        }
    }

}
