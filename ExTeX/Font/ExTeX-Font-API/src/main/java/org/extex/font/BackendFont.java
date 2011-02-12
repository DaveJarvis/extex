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

package org.extex.font;

/**
 * Interface for a back-end font.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface BackendFont {

    /**
     * Returns the actual FontKey for this font after a font substitution.
     * 
     * @return Returns the actual FontKey for this font.
     */
    FontKey getActualFontKey();

    /**
     * Returns the internal name of the font.
     * 
     * @return the internal name of the font.
     */
    String getName();

    /**
     * Returns the pfa data, or <code>null</code>, if not available. TODO mgn
     * raus
     * 
     * @return Returns the pfa data, or <code>null</code>, if not available.
     */
    byte[] getPfa();

    /**
     * Returns the pfb data, or <code>null</code>, if not available. TODO mgn
     * raus
     * 
     * @return Returns the pfb data, or <code>null</code>, if not available.
     */
    byte[] getPfb();

    /**
     * Returns the xtf data, or <code>null</code>, if not available. TODO mgn
     * raus
     * 
     * @return Returns the xtf data, or <code>null</code>, if not available.
     */
    byte[] getXtf();

    /**
     * Returns <code>true</code>, if the font is a type 1 font (with afm/pfb),
     * otherwise <code>false</code>. TODO mgn raus
     * 
     * @return Returns <code>true</code>, if the font is a type 1 font (with
     *         afm/pfb), otherwise <code>false</code>.
     */
    boolean isType1();

    /**
     * Returns <code>true</code>, if the font is a ttf/otf font, otherwise
     * <code>false</code>. TODO mgn raus
     * 
     * @return Returns <code>true</code>, if the font is a ttf/otf font,
     *         otherwise <code>false</code>.
     */
    boolean isXtf();

}
