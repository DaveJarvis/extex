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

import org.extex.exbib.bst2groovy.compiler.EqualsCompiler;
import org.extex.exbib.bst2groovy.compiler.GreaterCompiler;
import org.extex.exbib.bst2groovy.compiler.LessCompiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents a negation.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Not extends GenericCode {

    /**
     * Creates a new object.
     * 
     * @param code the code
     */
    public Not(GCode code) {

        super(ReturnType.INT, "!", code);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize()
     */
    @Override
    public GCode optimize() {

        GCode code = getArg(0);
        if (code instanceof GreaterCompiler.Greater) {
            return new GreaterCompiler.GreaterEqual(
                ((GreaterCompiler.Greater) code).getArg(0),
                ((GreaterCompiler.Greater) code).getArg(1));
        } else if (code instanceof LessCompiler.Less) {
            return new LessCompiler.LessEqual(
                //
                ((LessCompiler.Less) code).getArg(0),
                ((LessCompiler.Less) code).getArg(1));
        } else if (code instanceof EqualsCompiler.Equals) {
            return new EqualsCompiler.NotEquals(((EqualsCompiler.Equals) code)
                .getArg(0), ((EqualsCompiler.Equals) code).getArg(1));
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

        writer.write("! ");
        getArg(0).print(writer, prefix);
    }

}
