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

package org.extex.interpreter.type.count;

/**
 * This class provides an implementation of a Count where all methods modifying
 * the contents are redefined to produce an exception.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ImmutableCount extends Count {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param value the value to be stored
     */
    public ImmutableCount(final long value) {

        super(value);
    }

    /**
     * Creates a new object.
     *
     * @param value the value to be stored
     */
    public ImmutableCount(final FixedCount value) {

        super(value);
    }

    /**
     * This operation is unsupported and leads to an exception.
     *
     * @param val the value to add to
     *
     * @see org.extex.interpreter.type.count.Count#add(long)
     */
    public void add(final long val) {

        throw new UnsupportedOperationException(
            "Unable to set an immutable object");
    }

    /**
     * This operation is unsupported and leads to an exception.
     *
     * @param val the value to divide by
     *
     * @see org.extex.interpreter.type.count.Count#divide(long)
     */
    public void divide(final long val) {

        throw new UnsupportedOperationException(
            "Unable to set an immutable object");
    }

    /**
     * This operation is unsupported and leads to an exception.
     *
     * @param val the value to multiply with
     *
     * @see org.extex.interpreter.type.count.Count#multiply(long)
     */
    public void multiply(final long val) {

        throw new UnsupportedOperationException(
            "Unable to set an immutable object");
    }

}
