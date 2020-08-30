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

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents the initialization of a variable in the target
 * language.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class DeclareVar extends GenericCode {

    /**
     * The field {@code var} contains the variable.
     */
    private Var var;

    /**
     * The field {@code value} contains the value.
     */
    private GCode value;

    /**
     * Creates a new object.
     * 
     * @param var the variable
     */
    public DeclareVar(Var var) {

        this(var, null);
    }

    /**
     * Creates a new object.
     * 
     * @param var the variable
     * @param value the new value
     */
    public DeclareVar(Var var, GCode value) {

        super(ReturnType.VOID, "");
        this.var = var;
        this.value = value;
        if (value != null) {
            var.setType(value.getType());
        }
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
*      java.lang.String)
     */
    @Override
    public void print(CodeWriter writer, String prefix) throws IOException {

        writer.write(prefix);

        // if (!var.isBound()) {
        var.printType(writer, prefix);
        writer.write(" ");
        // }
        var.print(writer, prefix);
        if (value != null) {
            writer.write(" = ");
            value.print(writer, prefix);
        }
    }

    /**
     * Setter for the value.
     * 
     * @param value the value to set
     */
    public void setValue(GCode value) {

        this.value = value;
        if (value != null) {
            var.setType(value.getType());
        }
    }
}
