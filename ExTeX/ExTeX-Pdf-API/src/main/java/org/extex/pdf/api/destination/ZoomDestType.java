/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.pdf.api.destination;

/**
 * This class carries a destination type ZOOM as used in PDF nodes.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ZoomDestType extends DestType {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field {@code zoom} contains the zoom value.
     */
    private final long zoom;

    /**
     * Creates a new object.
     *
     * @param zoom the zoom value
     */
    public ZoomDestType(long zoom) {

        this.zoom = zoom;
    }

    /**
     * Getter for zoom.
     *
     * @return the zoom
     */
    public long getZoom() {

        return this.zoom;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "zoom " + zoom;
    }

    /**
     * This method is the entry point for the visitor pattern.
     *
     * @param visitor the visitor to call back
     *
     * @return an arbitrary return object
     *
     * @see org.extex.pdf.api.destination.DestType#visit(
     *      org.extex.pdf.api.destination.DestinationVisitor)
     */
    @Override
    public Object visit(DestinationVisitor visitor) {

        return visitor.visitZoom(this);
    }

}
