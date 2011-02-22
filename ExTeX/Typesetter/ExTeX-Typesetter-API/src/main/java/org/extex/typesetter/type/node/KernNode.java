/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.node;

import org.extex.typesetter.type.Node;

/**
 * This interface describes a horizontal or vertical adjustment of the current
 * position on the page. In fact the dimensions width, height, and depth simply
 * act as for each node without the necessity to place some toner on the paper.
 * 
 * From The TeXbook
 * <p>
 * A kern_node has a width field to specify a (normally negative) amount of
 * spacing. This spacing correction appears in horizontal lists between letters
 * like A and V when the font designer said that it looks better to move them
 * closer together or further apart. A kerning node can also appear in a
 * vertical list, when its `width' denotes additional spacing in the vertical
 * direction.
 * </p>
 * 
 * @see "<logo>T<span style=
 *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 *      >e</span>X</logo> &ndash; The Program [155]"
 * @see org.extex.typesetter.type.node.ImplicitKernNode
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public interface KernNode extends Node {

    // nothing else
}
