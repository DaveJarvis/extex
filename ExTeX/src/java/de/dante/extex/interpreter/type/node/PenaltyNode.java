/*
 * Copyright (C) 2003  Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */
package de.dante.extex.interpreter.type.node;

import de.dante.extex.interpreter.type.Count;
import de.dante.extex.typesetter.Node;
import de.dante.extex.typesetter.NodeVisitor;

import de.dante.util.GeneralException;

/**
 * ...
 *
 * @see "TeX -- The Program [157]"
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PenaltyNode extends AbstractNode implements Node {
    /** ... */
    private long penalty = 0;

    /**
     * Creates a new object.
     *
     * @param penalty the penalty value
     *
     * @see "TeX -- The Program [158]"
     */
    public PenaltyNode(Count penalty) {
        this(penalty.getValue());
    }

    /**
     * Creates a new object.
     *
     * @param penalty the penalty value
     */
    public PenaltyNode(long penalty) {
        super();
        this.penalty = penalty;
    }

    /**
     * ...
     *
     * @return ...
     * @see "TeX -- The Program [194]"
     */
    public String toString() {
        return "penalty " + Long.toString(penalty); //TODO: i18n
    }

    /**
     * @see de.dante.extex.typesetter.Node#toString(java.lang.StringBuffer)
     */
    public void toString(StringBuffer sb) {
        sb.append("penalty ");
        sb.append(Long.toString(penalty));
    }

    /**
     * @see de.dante.extex.typesetter.Node#visit(de.dante.extex.typesetter.NodeVisitor, java.lang.Object, java.lang.Object)
     */
    public Object visit(NodeVisitor visitor, Object value, Object value2)
                 throws GeneralException {
        return visitor.visitPenalty(value, value2);
    }
}
