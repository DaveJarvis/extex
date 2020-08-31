/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.paragraphBuilder.impl;

/**
 * This class provides a finite enumeration for fitness values.
 *
 * @see "TTP [817]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class Fitness {

    /**
     * The field {@code TIGHT} contains the fitness of class 3.
     */
    public static final Fitness TIGHT = new Fitness(3);

    /**
     * The field {@code LOOSE} contains the fitness of class 1.
     */
    public static final Fitness LOOSE = new Fitness(1);

    /**
     * The field {@code VERY_LOOSE} contains the fitness of class 0.
     */
    public static final Fitness VERY_LOOSE = new Fitness(0);

    /**
     * The field {@code DESCENT} contains the fitness of class 2.
     */
    public static final Fitness DECENT = new Fitness(2);

    /**
     * The field {@code order} contains the order of the fitness.
     */
    private final int order;

    /**
     * Creates a new object.
     * The constructor is private since only the static instances defined
     * above are allowed.
     *
     * @param theOrder the fitness class
     */
    private Fitness(int theOrder) {

        this.order = theOrder;
    }

    /**
     * Determine whether the given fitness has the same order or a class which
     * is one less or one more than the own order.
     *
     * @param fitness the fitness to compare to
     *
     * @return {@code true} iff the order of the given fitness is equal
     *  or adjacent to the given one.
     */
    public boolean adjacent(Fitness fitness) {

        return (fitness.order == order
                || fitness.order + 1 == order
                || fitness.order - 1 == order);
    }
}