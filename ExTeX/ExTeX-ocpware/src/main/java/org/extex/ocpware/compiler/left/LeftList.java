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

import org.extex.ocpware.compiler.exception.AliasNotDefinedException;
import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.IllegalOpcodeException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.State;

/**
 * This class represents a list of left items.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LeftList implements Left {

    /**
     * The field {@code list} contains the list.
     */
    private List<Left> list;

    /**
     * Creates a new object.
     * 
     * @param list the left list
     */
    public LeftList(List<Left> list) {

        this.list = list;
    }

    /**
*      org.extex.ocpware.compiler.parser.State, CompilerState)
     */
    public List<Integer> genLeft(State state, CompilerState cs)
            throws AliasNotDefinedException,
                ArgmentTooBigException,
                IOException,
                IllegalOpcodeException {

        List<Integer> holes = new ArrayList<Integer>();

        for (Left l : list) {
            holes.addAll(l.genLeft(state, cs));
        }
        return holes;
    }

@Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Left l : list) {
            sb.append(l.toString());
        }
        return sb.toString();
    }

}
