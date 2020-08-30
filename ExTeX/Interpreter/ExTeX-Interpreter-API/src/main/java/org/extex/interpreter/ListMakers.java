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

package org.extex.interpreter;

import org.extex.typesetter.ListMakerType;

/**
 * This is a utility class which provides constants for list maker types.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class ListMakers {


    private ListMakers() {

        // unused
    }

    /**
     * The field {@code HORIZONTAL} contains the constant for the horizontal
     * list maker.
     */
    public static final ListMakerType HORIZONTAL =
            new ListMakerType("horizontal");

    /**
     * The field {@code RESTRICTED_HORIZONTAL} contains the constant for the
     * restricted horizontal list maker.
     */
    public static final ListMakerType RESTRICTED_HORIZONTAL =
            new ListMakerType("restricted horizontal");

    /**
     * The field {@code VERTICAL} contains the constant for the vertical list
     * maker.
     */
    public static final ListMakerType VERTICAL = new ListMakerType("vertical");

    /**
     * The field {@code INNER_VERTICAL} contains the constant for the inner
     * vertical list maker.
     */
    public static final ListMakerType INNER_VERTICAL =
            new ListMakerType("inner vertical");

    /**
     * The field {@code ALIGNMENT} contains the constant for the alignment
     * list maker.
     */
    public static final ListMakerType ALIGNMENT =
            new ListMakerType("alignment");

    /**
     * The field {@code MATH} contains the math list maker.
     */
    public static final ListMakerType MATH = new ListMakerType("math");

}
