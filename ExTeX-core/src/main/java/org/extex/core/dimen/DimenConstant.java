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

package org.extex.core.dimen;

import java.io.Serializable;

import org.extex.core.glue.FixedGlueComponent;

/**
 * This class provides objects of type {@link org.extex.core.dimen.Dimen Dimen}
 * where all assignment methods are redefined to produce a run-time exception.
 * Thus the object is in fact immutable.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4399 $
 */
public class DimenConstant extends Dimen implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param value the value to be stored
     */
    public DimenConstant(Dimen value) {

        super(value);
    }

    /**
     * Creates a new object.
     * 
     * @param value the value to be stored
     */
    public DimenConstant(long value) {

        super(value);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.dimen.Dimen#abs(org.extex.core.dimen.FixedDimen)
     */
    @Override
    public void abs(FixedDimen d) {

        if (d.lt(ZERO)) {
            throw new UnsupportedOperationException(
                "Unable to modify to an immutable object");
        }
    }

    /**
     * Add the value of the argument to the current value. This operation is not
     * supported.
     * 
     * @param d the Dimen to add
     * 
     * @see org.extex.core.dimen.Dimen#add( org.extex.core.dimen.FixedDimen)
     */
    @Override
    public void add(FixedDimen d) {

        if (d.ne(ZERO)) {
            throw new UnsupportedOperationException(
                "Unable to add to an immutable object");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.core.glue.GlueComponent#add(org.extex.core.glue.FixedGlueComponent)
     */
    @Override
    public void add(FixedGlueComponent g) {

        throw new UnsupportedOperationException(
            "Unable to modify to an immutable object");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.dimen.Dimen#add(long)
     */
    @Override
    public void add(long d) {

        if (d != 0) {
            throw new UnsupportedOperationException(
                "Unable to modify to an immutable object");
        }
    }

    /**
     * Divide the current value with a given number. This operation is not
     * supported.
     * 
     * @param denom denominator to divide by
     * 
     * @see org.extex.core.dimen.Dimen#divide(long)
     */
    @Override
    public void divide(long denom) {

        if (denom != 1) {
            throw new UnsupportedOperationException(
                "Unable to modify an immutable object");
        }
    }

    /**
     * Sets the value of the dimen to the maximum of the value already stored
     * and a given argument. This operation is not supported.
     * 
     * @param d the other dimen
     * 
     * @see org.extex.core.dimen.Dimen#max( org.extex.core.dimen.FixedDimen)
     */
    @Override
    public void max(FixedDimen d) {

        throw new UnsupportedOperationException(
            "Unable to modify an immutable object");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.dimen.Dimen#min(org.extex.core.dimen.FixedDimen)
     */
    @Override
    public void min(FixedDimen d) {

        throw new UnsupportedOperationException(
            "Unable to modify to an immutable object");
    }

    /**
     * Multiply the current value with a given number. This operation is not
     * supported.
     * 
     * @param factor the factor to multiply with
     * 
     * @see org.extex.core.dimen.Dimen#multiply(long)
     */
    @Override
    public void multiply(long factor) {

        if (factor != 1) {
            throw new UnsupportedOperationException(
                "Unable to add to an immutable object");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.core.glue.GlueComponent#multiply(long, long)
     */
    @Override
    public void multiply(long nom, long denom) {

        throw new UnsupportedOperationException(
            "Unable to modify to an immutable object");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.dimen.Dimen#negate()
     */
    @Override
    public void negate() {

        if (this.ne(ZERO)) {
            throw new UnsupportedOperationException(
                "Unable to modify to an immutable object");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.dimen.Dimen#set(org.extex.core.glue.FixedGlueComponent)
     */
    @Override
    public void set(FixedGlueComponent d) {

        if (d.ne(this)) {
            throw new UnsupportedOperationException(
                "Unable to modify to an immutable object");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.core.glue.GlueComponent#set(long)
     */
    @Override
    public void set(long theValue) {

        throw new UnsupportedOperationException(
            "Unable to modify to an immutable object");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.core.glue.GlueComponent#setValue(long)
     */
    @Override
    public void setValue(long val) {

        throw new UnsupportedOperationException(
            "Unable to modify to an immutable object");
    }

    /**
     * Subtract the value of the argument from the current value. This operation
     * is not supported.
     * 
     * @param d the Dimen to subtract
     * 
     * @see org.extex.core.dimen.Dimen#subtract(
     *      org.extex.core.dimen.FixedDimen)
     */
    @Override
    public void subtract(FixedDimen d) {

        if (d.ne(ZERO)) {
            throw new UnsupportedOperationException(
                "Unable to modify an immutable object");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.core.glue.GlueComponent#subtract(org.extex.core.glue.FixedGlueComponent)
     */
    @Override
    public void subtract(FixedGlueComponent g) {

        throw new UnsupportedOperationException(
            "Unable to modify to an immutable object");
    }

}
