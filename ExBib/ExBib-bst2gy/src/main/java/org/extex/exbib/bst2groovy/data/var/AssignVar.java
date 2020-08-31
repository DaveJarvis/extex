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

package org.extex.exbib.bst2groovy.data.var;

import java.io.IOException;
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents the setter for a local variable in the target language.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AssignVar extends GenericCode {

    /**
     * The field {@code var} contains the name of the local variable.
     */
    private final Var var;

    /**
     * The field {@code value} contains the the new value.
     */
    private GCode value;

    /**
     * Creates a new object.
     * 
     * @param var the name of the filed
     * @param value the new value
     */
    public AssignVar(Var var, GCode value) {

        super(ReturnType.VOID, ":=");
        this.var = var;
        this.value = value;
        var.setType(value.getType());
    }

    /**
     * Getter for the value.
     * 
     * @return the value
     */
    public GCode getValue() {

        return value;
    }

    /**
     * Getter for the var.
     * 
     * @return the var
     */
    public Var getVar() {

        return var;
    }

    /**
*      int)
     */
    @Override
    public int optimize(List<GCode> list, int index) {

        int size = list.size();
        if (size >= 2 && index == size - 1 && value instanceof Var) {
            GCode code = list.get(index - 1);
            if (code instanceof DeclareVar
                    && ((Var) value).eq(((DeclareVar) code).getVar())) {
                value = ((DeclareVar) code).getValue();
                list.remove(index - 1);
                return index;
            }
        } else if (index > 0) {
            GCode code = list.get(index - 1);
            if (code instanceof DeclareVar
                    && var.eq(((DeclareVar) code).getVar())) {
                ((DeclareVar) code).setValue(value);
                list.remove(index);
                return index - 1;
            }
        }
        return super.optimize(list, index);
    }

    /**
*      java.lang.String)
     */
    @Override
    public void print(CodeWriter writer, String prefix) throws IOException {

        writer.write(prefix);
        var.print(writer, prefix);
        writer.write(" = ");
        value.print(writer, prefix);
    }

    /**
     * Setter for the value.
     * 
     * @param value the value to set
     */
    public void setValue(GCode value) {

        this.value = value;
    }
}
