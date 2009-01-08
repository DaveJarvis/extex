/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents a conjunction.
 * 
 */
public final class And extends GenericCode {

    /**
     * Creates a new object.
     * 
     * @param code1 the left side
     * @param code2 the right side
     */
    public And(GCode code1, GCode code2) {

        super(ReturnType.INT, "", code1, code2);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize()
     */
    @Override
    public GCode optimize() {

        GCode arg = getArg(0);
        if (arg instanceof GIntegerConstant
                && ((GIntegerConstant) arg).getValue() != 0) {
            return getArg(1);
        }
        arg = getArg(1);
        if (arg instanceof GIntegerConstant
                && ((GIntegerConstant) arg).getValue() != 0) {
            return getArg(0);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#print(org.extex.exbib.bst2groovy.io.CodeWriter,
     *      java.lang.String)
     */
    @Override
    public void print(CodeWriter writer, String prefix) throws IOException {

        getArg(0).print(writer, prefix);
        writer.write(" && ");
        getArg(1).print(writer, prefix);
    }

}
