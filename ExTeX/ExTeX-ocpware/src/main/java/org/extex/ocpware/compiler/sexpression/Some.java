/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.ocpware.compiler.sexpression;

import java.io.IOException;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.type.OcpCode;

/**
 * This class represents a prefix which is trimmed on both sides.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Some implements Expr {

    /**
     * The field {@code m} contains the right trim.
     */
    private int m;

    /**
     * The field {@code n} contains the left trim.
     */
    private int n;

    /**
     * Creates a new object.
     * 
     * @param n the left trim
     * @param m the right trim
     */
    public Some(int n, int m) {

        this.n = n;
        this.m = m;
    }

    /**
*      org.extex.ocpware.compiler.parser.CompilerState, boolean)
     */
    public void outRight(CompilerState cs, boolean withOffset)
            throws IOException,
                ArgmentTooBigException {

        cs.putInstruction(withOffset
                ? OcpCode.OP_PBACK_SOME
                : OcpCode.OP_RIGHT_SOME, n, m);
    }

@Override
    public String toString() {

        if (m == 0) {
            if (n == 0) {
                return "\\*";
            }
            return "\\(*+" + Integer.toString(n) + ")";
        } else if (n == 0) {
            return "\\(*-" + Integer.toString(m) + ")";
        }
        return "\\(*+" + Integer.toString(n) + "-" + Integer.toString(m) + ")";
    }

}
