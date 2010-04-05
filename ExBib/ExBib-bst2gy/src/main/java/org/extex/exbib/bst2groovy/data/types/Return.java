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

package org.extex.exbib.bst2groovy.data.types;

import java.io.IOException;
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.var.DeclareVar;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents a return statement.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Return extends GenericCode {

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    public Return(GCode value) {

        super(value.getType(), "return", value);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize(java.util.List,
     *      int)
     */
    @Override
    public int optimize(List<GCode> list, int index) {

        if (index > 0 && getArg(0) instanceof Var) {
            Var var = (Var) getArg(0);
            GCode code = list.get(index - 1);
            if (code instanceof DeclareVar
                    && var.eq(((DeclareVar) code).getVar())) {
                setArg(0, ((DeclareVar) code).getValue());
                list.remove(index - 1);
                return index;
            }
        }
        return index + 1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#print(org.extex.exbib.bst2groovy.io.CodeWriter,
     *      java.lang.String)
     */
    @Override
    public void print(CodeWriter writer, String prefix) throws IOException {

        writer.write(prefix, "return ");
        getArg(0).print(writer, prefix);
    }

}
