/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
import org.extex.ocpware.type.OcpCode;

/**
 * This class represents an arithmetic expression for a table reference.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TableRef extends ArithExpr {

    /**
     * The field {@code index} contains the offset.
     */
    private final ArithExpr index;

    /**
     * The field {@code table} contains the name of the table.
     */
    private final String table;

    /**
     * Creates a new object.
     * 
     * @param id the table name
     * @param n the index
     */
    public TableRef(String id, ArithExpr n) {

        this.table = id;
        this.index = n;
    }

@Override
    void outExpr(CompilerState cs)
            throws IOException,
    ArgmentTooBigException {

        cs.putInstruction(OcpCode.OP_PUSH_NUM, cs.lookupTable(table));
        index.outExpr(cs);
        cs.putInstruction(OcpCode.OP_LOOKUP);
    }

@Override
    public String toString() {

        return table + "[" + index.toString() + "]";
    }

}
