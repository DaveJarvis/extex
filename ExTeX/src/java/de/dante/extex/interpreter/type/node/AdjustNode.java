/*
 * Copyright (C) 2003-2004 Gerd Neugebauer, Michael Niedermair
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

import de.dante.extex.typesetter.Node;
import de.dante.extex.typesetter.NodeVisitor;
import de.dante.util.GeneralException;

/**
 * ...
 *
 * @see "TeX -- The Program [142]"
 *
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class AdjustNode extends AbstractNode implements Node {

	/**
	 * The field <tt>adjust</tt> ...
	 */
	private VerticalListNode adjust = null;

	/**
	 * Creates a new object.
	 *
	 */
	public AdjustNode() {
		super();
	}

	/**
	 * ...
	 *
	 * @return ...
	 * @see "TeX -- The Program [197]"
	 */
	public String toString() {
		return "vadjust "; //TODO incomplete
	}

	/**
	 * @see de.dante.extex.typesetter.Node#toString(java.lang.StringBuffer)
	 */
	public void toString(final StringBuffer sb, String prefix) {
		sb.append("vadjust "); //TODO unimplemented
	}

	/**
	 * @see de.dante.extex.typesetter.Node#visit(de.dante.extex.typesetter.NodeVisitor,
	 *      java.lang.Object, java.lang.Object)
	 */
	public Object visit(final NodeVisitor visitor, final Object value, final Object value2) throws GeneralException {
		return visitor.visitAdjust(value, value2);
	}

	/**
	 * @see de.dante.extex.typesetter.Node#getType()
	 */
	public String getType() {
		return "adjust";
	}
}
