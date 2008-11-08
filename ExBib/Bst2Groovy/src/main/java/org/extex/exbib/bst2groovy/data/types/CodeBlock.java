/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

import org.extex.exbib.bst2groovy.data.VoidGCode;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.core.bst.token.Token;

/**
 * This class wraps a token and acts like void Code.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CodeBlock extends VoidGCode {

    /**
     * The field <tt>t</tt> contains the token.
     */
    private Token t;

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

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
     *      java.lang.String)
     */
    public void print(CodeWriter writer, String prefix) throws IOException {

        writer.write("<<<");
        writer.write(t.toString());
        writer.write(">>>");
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return t.toString();
    }

}
