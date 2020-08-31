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
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.exception.ImpossibleException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.core.bst.token.Token;

/**
 * This class wraps a token and acts like void code.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CodeBlock implements GCode {

    /**
     * The field {@code t} contains the token.
     */
    private final Token t;

    /**
     * Creates a new object.
     * 
     * @param t the token
     */
    public CodeBlock(Token t) {

        this.t = t;
    }

    /**
     * Getter for the token.
     * 
     * @return the token
     */
    public Token getToken() {

        return t;
    }

public ReturnType getType() {

        return ReturnType.VOID;
    }

@Override
    public boolean hasSideEffect() {

        return false;
    }

public GCode optimize() {

        return this;
    }

public int optimize(List<GCode> list, int index) {

        return index + 1;
    }

    /**
*      java.lang.String)
     */
    public void print(CodeWriter writer, String prefix) throws IOException {

        throw new ImpossibleException(t.toString());
    }

@Override
    public String toString() {

        return t.toString();
    }

public boolean unify(GCode other) {

        if (other instanceof Var) {
            return other.unify(this);
        } else if (!(other instanceof CodeBlock)) {
            return false;
        }
        return ((CodeBlock) other).getToken() == t;
    }

}
