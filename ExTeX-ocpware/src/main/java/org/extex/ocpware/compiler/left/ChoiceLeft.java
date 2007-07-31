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

package org.extex.ocpware.compiler.left;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.State;
import org.extex.ocpware.type.OcpProgram;

/**
 * This class represents a list of alternative left items.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class ChoiceLeft extends ArrayList<List<Left>> implements Left {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     */
    public ChoiceLeft() {

        super();
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

        List<Integer> trueHoles = new ArrayList<Integer>();
        List<Integer> holes = new ArrayList<Integer>();

        for (List<Left> list : this) {

            // p=arg->more_lefts;
            // while (p!=nil) {
            // false_holes = gen_left(p->val);
            // if (p->ptr) {
            // out_int(OTP_GOTO, 0);
            int ptr = state.putInstruction(OcpProgram.GOTO, 0);
            // true_holes=cons(out_ptr-1, true_holes);
            trueHoles.add(Integer.valueOf(ptr - 1));
            // fill_in(false_holes);
            cs.getCurrentState().fillIn(holes);
            // }
            // p=p->ptr;
        }
        cs.getCurrentState().fillIn(trueHoles);

        //TODO gene: genLeft unimplemented
        throw new RuntimeException("unimplemented");
        
        //        return holes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        boolean sep = false;

        for (List<Left> list : this) {
            if (sep) {
                sb.append(" | ");
            } else {
                sep = true;
            }
            for (Left l : list) {
                sb.append(l.toString());
            }
        }
        return sb.toString();
    }

}
