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

/**
 * This class represents the reference to a character in the matched sequence
 * counted from the end of the sequence.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class RefEnd extends ArithExpr {

    /**
     * Creates a new object.
     */
    public RefEnd() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.arith.ArithExpr#eval()
     */
    @Override
    public int eval() {

        // TODO gene: eval unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "\\$";
    }

}
