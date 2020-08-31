/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.pageFilter.selector;

/**
 * This rule checks that a number has a given remainder when divided by a
 * certain divider.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
class ModuloRule implements Rule {

    /**
     * The field {@code mod} contains the divider.
     */
    private final int mod;

    /**
     * The field {@code rem} contains the remainder.
     */
    private final int rem;

    /**
     * Creates a new object.
     *
     * @param mod the divider
     * @param rem the remainder
     */
    public ModuloRule(int mod, int rem) {

        this.mod = mod;
        this.rem = rem;
    }

    /**
     * Check that a given page is covered by this rule.
     *
     * @param value the number to check
     *
     * @return {@code true} iff the number is covered
     *
     * @see org.extex.backend.pageFilter.selector.Rule#check(int)
     */
    public boolean check(int value) {

        return value % mod == rem;
    }

}
