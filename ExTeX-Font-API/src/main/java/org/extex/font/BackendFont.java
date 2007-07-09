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

import java.util.List;

/**
 * Interface for a backend font.
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
     * Returns the afm data, or <code>null</code>, if not available.
     * 
     * @return Returns the afm data, or <code>null</code>, if not available.
     */
    byte[] getAfm();

    /**
     * Returns the checksum of the font.
     * 
     * @return the checksum.
     */
    int getCheckSum();

    /**
     * Returns the encoding vectors as list of string array (without a '/') or
     * <code>null</code>, if no one exists.
     * 
     * @return Returns the encoding vectors.
     */
    List<String[]> getEncodingVectors();

    /**
     * Returns the internal name of the font.
     * 
     * @return the internal name of the font.
     */
    String getName();

    /**
     * Returns the pfa data, or <code>null</code>, if not available.
     * 
     * @return Returns the pfa data, or <code>null</code>, if not available.
     */
    byte[] getPfa();

    /**
     * Returns the pfb data, or <code>null</code>, if not available.
     * 
     * @return Returns the pfb data, or <code>null</code>, if not available.
     */
    byte[] getPfb();

    /**
     * Returns the xtf data, or <code>null</code>, if not available.
     * 
     * @return Returns the xtf data, or <code>null</code>, if not available.
     */
    byte[] getXtf();

    /**
     * Returns <code>true</code>, if the font has an encoding vector,
     * otherwise <code>false</code>.
     */
    boolean hasEncodingVector();

    /**
     * If <code>true</code>, the font has more the one font in it.
     * 
     * @return Returns <code>true</code>, if the font has more fonts in it.
     */
    boolean hasMultiFonts();

    /**
     * Returns the number of the encoding vector for the codepoint. If no
     * enocding vector exists, -1 is returned.
     * 
     * @param codepoint The codepoint of the char.
     * @return Returns the number of the encoding vector for the codepoint.
     */
    int getEncodingForChar(int codepoint);

    /**
     * Returns <code>true</code>, if the font is a type 1 font (with
     * afm/pfb), otherwise <code>false</code>.
     * 
     * @return Returns <code>true</code>, if the font is a type 1 font (with
     *         afm/pfb), otherwise <code>false</code>.
     */
    boolean isType1();

    /**
     * Returns <code>true</code>, if the font is a ttf/otf font, otherwise
     * <code>false</code>.
     * 
     * @return Returns <code>true</code>, if the font is a ttf/otf font,
     *         otherwise <code>false</code>.
     */
    boolean isXtf();

    /**
     * Tells the font, which character is used.
     * 
     * @param bc The character.
     */
    void usedCharacter(BackendCharacter bc);
}
