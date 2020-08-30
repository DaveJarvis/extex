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

package org.extex.exbib.bst2groovy.data.bool;

import java.io.IOException;
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents a boolean expression which is casted to int by need.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class GBoolean implements GCode {

    /**
     * The field {@code code} contains the code.
     */
    private GCode code;

    /**
     * Creates a new object.
     * 
     * @param code the code
     */
    public GBoolean(GCode code) {

        this.code = code;
    }

    /**
     * Getter for the code.
     * 
     * @return the code
     */
    public GCode getCode() {

        return code;
    }

public ReturnType getType() {

        return ReturnType.INT;
    }

@Override
    public boolean hasSideEffect() {

        return code.hasSideEffect();
    }

public GCode optimize() {

        code = code.optimize();
        return this;
    }

public int optimize(List<GCode> list, int index) {

        return index + 1;
    }

    /**
*      java.lang.String)
     */
    public void print(CodeWriter writer, String prefix) throws IOException {

        code.print(writer, prefix);
        writer.write(" ? 1 : 0");
    }

@Override
    public String toString() {

        return "(Boolean) " + code.toString();
    }

public boolean unify(GCode other) {

        if (other instanceof Var) {
            return other.unify(this);
        } else if (!(other instanceof GBoolean)) {
            return false;
        }
        return code.unify(((GBoolean) other).getCode());
    }

}
