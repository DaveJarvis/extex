/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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
package de.dante.extex.typesetter.paragraphBuilder;

import de.dante.extex.interpreter.type.dimen.Dimen;
import de.dante.extex.interpreter.type.dimen.FixedDimen;


/**
 * This paragraph shape represents a fixed block. It is aliged at 0pt on the
 * left and by the given width on the right.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FixedParagraphShape extends ParagraphShape {

    /**
     * The field <tt>hsize</tt> contains the width of the fixed block.
     */
    private FixedDimen hsize;

    /**
     * Creates a new object.
     *
     * @param theHsize the width
     */
    public FixedParagraphShape(final FixedDimen theHsize) {

        super();
        this.hsize = theHsize;
    }

    /**
     * @see de.dante.extex.typesetter.paragraphBuilder.ParagraphShape#getIndent(int)
     */
    public FixedDimen getIndent(final int index) {

        return Dimen.ZERO_PT;
    }
    /**
     * @see de.dante.extex.typesetter.paragraphBuilder.ParagraphShape#getLength(int)
     */
    public FixedDimen getLength(final int index) {

        return hsize;
    }

    /**
     * Setter for hsize.
     *
     * @param theHsize the hsize to set.
     */
    public void setHsize(final FixedDimen theHsize) {

        this.hsize = theHsize;
    }
}
