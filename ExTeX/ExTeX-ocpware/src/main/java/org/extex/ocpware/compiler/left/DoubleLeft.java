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

package org.extex.ocpware.compiler.left;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.State;
import org.extex.ocpware.type.OcpCode;

/**
 * This class represents a range of characters as left item.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class DoubleLeft implements Left {

    /**
     * Format a number into a target string buffer.
     * 
     * @param sb the target buffer
     * @param n the number
     */
    private static void toString(StringBuilder sb, int n) {

        if (n >= ' ' && n < 126) {
            sb.append("`");
            sb.append((char) n);
            sb.append("'");
        } else {
            sb.append("@\"");
            sb.append(Integer.toHexString(n));
        }

    }

    /**
     * The field <tt>from</tt> contains the lower bound.
     */
    private int from;

    /**
     * The field <tt>to</tt> contains the upper bound.
     */
    private int to;

    /**
     * Creates a new object.
     * 
     * @param from the minimum
     * @param to the maximum
     */
    public DoubleLeft(int from, int to) {

        this.from = from;
        this.to = to;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.left.Left#genLeft(
     *      org.extex.ocpware.compiler.parser.State, CompilerState)
     */
    public List<Integer> genLeft(State state, CompilerState cs)
            throws IOException,
                ArgmentTooBigException {

        List<Integer> holes = new ArrayList<Integer>();
        int ptr = state.putInstruction(OcpCode.OP_GOTO_LT, from, 0);
        holes.add(Integer.valueOf(ptr - 1));
        ptr = state.putInstruction(OcpCode.OP_GOTO_GT, to, 0);
        holes.add(Integer.valueOf(ptr - 1));
        return holes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        toString(sb, from);
        sb.append("-");
        toString(sb, to);
        return sb.toString();
    }

}
