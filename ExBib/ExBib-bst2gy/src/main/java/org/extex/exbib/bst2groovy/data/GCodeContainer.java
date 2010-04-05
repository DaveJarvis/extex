/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents a container of target code which exposes the methods
 * for target code itself.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GCodeContainer extends ArrayList<GCode> implements GCode {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2009L;

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public ReturnType getType() {

        return ReturnType.VOID;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#optimize()
     */
    public GCode optimize() {

        for (int i = 0; i < size();) {
            GCode a = get(i);
            GCode ao = a.optimize();
            if (a != ao) {
                if (ao instanceof GCodeContainer) {
                    int j = i;
                    for (GCode code : (GCodeContainer) ao) {
                        add(j++, code);
                    }
                    remove(j);
                } else {
                    set(i, ao);
                    i = ao.optimize(this, i);
                }
            } else {
                i = ao.optimize(this, i);
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#optimize(java.util.List, int)
     */
    public int optimize(List<GCode> list, int index) {

        optimize();
        if (size() == 0) {
            list.remove(index);
            return index;
        }
        return index + 1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
     *      java.lang.String)
     */
    public void print(CodeWriter writer, String prefix) throws IOException {

        for (GCode c : this) {
            c.print(writer, prefix);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        for (GCode c : this) {
            buffer.append(c.toString());
            buffer.append('\n');
        }
        return buffer.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#unify(org.extex.exbib.bst2groovy.data.GCode)
     */
    public boolean unify(GCode other) {

        int size = size();
        if (other instanceof Var) {
            return other.unify(this);
        } else if (!(other instanceof GCodeContainer)
                || ((GCodeContainer) other).size() != size) {
            return false;
        }
        for (int i = 0; i < size;) {
            if (!get(i).unify(((GCodeContainer) other).get(i))) {
                return false;
            }
        }
        return true;
    }

}
