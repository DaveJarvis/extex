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

package org.extex.ocpware.compiler.arith;

import java.io.IOException;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.type.OcpProgram;

/**
 * This class represents an arithmetic expression for a table reference.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TableRef extends ArithExpr {

    /**
     * The field <tt>n</tt> contains the offset.
     */
    private ArithExpr n;

    /**
     * The field <tt>table</tt> contains the name of the table.
     */
    private String table;

    /**
     * Creates a new object.
     * 
     * @param id the table name
     * @param n the index
     */
    public TableRef(String id, ArithExpr n) {

        super();
        this.table = id;
        this.n = n;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.arith.ArithExpr#outExpr( CompilerState)
     */
    @Override
    void outExpr(CompilerState cs)
            throws IOException,
                TableNotDefinedException,
                ArgmentTooBigException {

        cs.putInstruction(OcpProgram.PUSH_NUM, cs.lookupTable(table));
        n.outExpr(cs);
        cs.putInstruction(OcpProgram.LOOKUP);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return table + "[" + n.toString() + "]";
    }

}
