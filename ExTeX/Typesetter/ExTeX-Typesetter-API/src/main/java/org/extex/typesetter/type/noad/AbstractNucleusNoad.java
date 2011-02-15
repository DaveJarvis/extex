/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.noad;

import org.extex.color.Color;
import org.extex.typesetter.tc.TypesettingContext;

/**
 * This abstract noad represents a Noad with a nucleus.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public abstract class AbstractNucleusNoad extends AbstractNoad {

    /**
     * The field <tt>nucleus</tt> contains the nucleus.
     */
    private Noad nucleus;

    /**
     * The field <tt>tc</tt> contains the typesetting context.
     */
    private TypesettingContext tc;

    /**
     * Creates a new object.
     *
     * @param nucleus the nucleus of inner noads
     * @param tc the typesetting context for the color
     */
    public AbstractNucleusNoad(Noad nucleus, TypesettingContext tc) {

        this.nucleus = nucleus;
        this.tc = tc;
    }

    /**
     * Getter for color.
     *
     * @return the color
     */
    public Color getColor() {

        return this.tc.getColor();
    }

    /**
     * Getter for nucleus.
     *
     * @return the nucleus
     */
    public Noad getNucleus() {

        return this.nucleus;
    }

    /**
     * Getter for the typesetting context.
     *
     * @return the typesetting context
     */
    protected TypesettingContext getTypesettingContext() {

        return this.tc;
    }

    /**
     * Produce a printable representation to a certain depth of the noad.
     *
     * @param sb the string buffer
     * @param depth the depth to which the full information should be given
     *
     * @see "TTP [696]"
     * @see org.extex.typesetter.type.noad.Noad#toString(
     *      java.lang.StringBuffer, int)
     */
    @Override
    public void toString(StringBuffer sb, int depth) {

        if (depth < 0) {
            sb.append(" {}");
        } else {
            sb.append('\\');
            toStringAdd(sb, depth);
            toStringSubsidiaray(sb, nucleus, depth, ".");
            toStringSubsidiaray(sb, getSuperscript(), depth, "^");
            toStringSubsidiaray(sb, getSubscript(), depth, "_");
        }
    }

}
