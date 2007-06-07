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

package org.extex.backend.documentWriter.rtf;

/**
 * This class provides a color with 8-bit RGB channels but without alpha
 * channel.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Rgb8Color {

    /**
     * The field <tt>EIGHT_BIT_MASK</tt> contains the bit mask for the lower
     * eight bits.
     */
    private static final int EIGHT_BIT_MASK = 0xff;

    /**
     * The field <tt>value</tt> contains the condensed value.
     */
    private int value;

    /**
     * Creates a new object.
     * 
     * @param blue the blue component
     * @param green the green component
     * @param red the red component
     */
    public Rgb8Color(int red, int green, int blue) {

        super();
        this.value = (red & EIGHT_BIT_MASK);
        this.value |= (green & EIGHT_BIT_MASK) << 8;
        this.value |= (blue & EIGHT_BIT_MASK) << 16;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj the reference object with which to compare.
     * 
     * @return <code>true</code> if this object is the same as the obj
     *         argument; <code>false</code> otherwise.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        return (obj instanceof Rgb8Color) && ((Rgb8Color) obj).value == value;
    }

    /**
     * Getter for blue.
     * 
     * @return the blue
     */
    public int getBlue() {

        return (this.value >> 16) & EIGHT_BIT_MASK;
    }

    /**
     * Getter for green.
     * 
     * @return the green
     */
    public int getGreen() {

        return (this.value >> 8) & EIGHT_BIT_MASK;
    }

    /**
     * Getter for red.
     * 
     * @return the red
     */
    public int getRed() {

        return this.value & EIGHT_BIT_MASK;
    }

    /**
     * Compute the has code for this object.
     * 
     * @return the hash code
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return value;
    }

}
