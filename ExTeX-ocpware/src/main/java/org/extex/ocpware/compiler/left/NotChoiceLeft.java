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

/**
 * This class represents a negated list of left items.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class NotChoiceLeft implements Left {

    /**
     * The field <tt>list</tt> contains the list of left items contained.
     */
    private List<Left> list;

    /**
     * Creates a new object.
     * 
     * @param list the left list
     */
    public NotChoiceLeft(ChoiceLeft list) {

        super();
        this.list = list;
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

        // for (Left l : list) {
        //            
        // }

//        true_holes=nil;
//        p=arg->more_lefts;
//        while (p!=nil) {
//            false_holes = gen_left(p->val);
//            out_int(OTP_GOTO, 0);
//            true_holes=cons(out_ptr-1, true_holes);
//            fill_in(false_holes);
//            p=p->ptr;
//        }
//        return true_holes;

        
        // TODO gene: genLeft unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer("^(");
        boolean first = true;
        for (Left l : list) {
            if (first) {
                first = false;
            } else {
                sb.append(" | ");
            }
            sb.append(l.toString());
        }
        sb.append(")");
        return sb.toString();
    }
}
