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
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.State;
import org.extex.ocpware.compiler.sexpression.Expr;
import org.extex.ocpware.type.OcpCode;

/**
 * This class represents a string of characters as left item.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class StringItem implements Left, Expr {

    /**
     * The field {@code s} contains the string.
     */
    private String s;

    /**
     * Creates a new object.
     * 
     * @param s the string
     */
    public StringItem(String s) {

        this.s = s;
    }

public List<Integer> genLeft(State state, CompilerState cs)
            throws IOException,
                ArgmentTooBigException {

        List<Integer> holes = new ArrayList<Integer>();
        int length = s.length();
        char c = s.charAt(0);
        for (int i = 0; i < length;) {

            int ptr = state.putInstruction(OcpCode.OP_GOTO_NE, c, 0);
            holes.add(Integer.valueOf(ptr - 1));

            if (++i < length) {
                c = s.charAt(i);
                ptr = state.putInstruction(OcpCode.OP_GOTO_NO_ADVANCE);
                holes.add(Integer.valueOf(ptr - 1));
            }
        }

        return holes;
    }

    /**
*      org.extex.ocpware.compiler.parser.CompilerState, boolean)
     */
    public void outRight(CompilerState cs, boolean withOffset)
            throws ArgmentTooBigException,
                IOException,
                TableNotDefinedException {

        int len = s.length();
        for (int i = 0; i < len; i++) {
            cs.putInstruction(withOffset
                    ? OcpCode.OP_PBACK_NUM
                    : OcpCode.OP_RIGHT_NUM, s.charAt(i));
        }
    }

@Override
    public String toString() {

        return "\"" + s.replaceAll("\"", "\"\"") + '"';
    }

}
