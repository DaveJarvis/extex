/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.dvi.command;

/**
 * DVI: right
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class DviRight extends DviCommand {

    /**
     * right1
     */
    private static final int RIGHT1 = 143;

    /**
     * the value
     */
    private int value;

    /**
     * Create a new object.
     * 
     * @param opc the opcode
     * @param sp the start pointer
     * @param v the value
     */
    public DviRight(final int opc, final int sp, final int v) {

        super(opc, sp);
        value = v;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviCommand#getName()
     */
    @Override
    public String getName() {

        return new StringBuilder("right").append(getOpcode() - RIGHT1 + 1)
            .toString();
    }

    /**
     * Returns the value.
     * 
     * @return Returns the value.
     */
    public int getValue() {

        return value;
    }
}
