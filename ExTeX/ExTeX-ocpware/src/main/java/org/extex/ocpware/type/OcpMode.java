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

package org.extex.ocpware.type;

/**
 * This enumeration names the &Omega;CP modes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public enum OcpMode {

    /**
     * The field {@code ONE_BYTE} contains the value for one byte.
     */
    ONE_BYTE,
    /**
     * The field {@code TWO_BYTES} contains the value for two-byte higher
     * endian.
     */
    TWO_BYTES,
    /**
     * The field {@code TWO_BYTES_LE} contains the value for two-byte lower
     * endian
     */
    TWO_BYTES_LE

}
