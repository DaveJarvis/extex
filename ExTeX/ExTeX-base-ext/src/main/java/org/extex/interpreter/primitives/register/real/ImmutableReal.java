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

package org.extex.interpreter.primitives.register.real;

/**
 * An immutable Real.
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
*/
public class ImmutableReal extends Real {

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     * 
     * @param val init with double-value
     */
    public ImmutableReal(double val) {

        super(val);
    }

    /**
     * Creates a new object.
     * 
     * @param l the value as long
     */
    public ImmutableReal(long l) {

        super(l);
    }

@Override
    public void add(double val) {

        throw new RuntimeException("Unable to set an immutable object");
    }

    /**
*      org.extex.interpreter.primitives.register.real.Real)
     */
    @Override
    public void add(Real real) {

        throw new RuntimeException("Unable to set an immutable object");
    }

@Override
    public void divide(double val) {

        throw new RuntimeException("Unable to set an immutable object");
    }

    /**
*      org.extex.interpreter.primitives.register.real.Real)
     */
    @Override
    public void divide(Real val) {

        throw new RuntimeException("Unable to set an immutable object");
    }

@Override
    public void multiply(double val) {

        throw new RuntimeException("Unable to set an immutable object");
    }

    /**
*      org.extex.interpreter.primitives.register.real.Real)
     */
    @Override
    public void multiply(Real val) {

        throw new RuntimeException("Unable to set an immutable object");
    }

    /**
     * Setter for the value.
     * 
     * @param d the new value
     */
    @Override
    public void setValue(double d) {

        throw new RuntimeException("Unable to set an immutable object");
    }

}
