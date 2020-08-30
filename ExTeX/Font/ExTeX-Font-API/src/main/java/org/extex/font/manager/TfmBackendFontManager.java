/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.manager;

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.font.BackendCharacter;
import org.extex.font.BackendFont;
import org.extex.font.BackendFontManager;
import org.extex.font.FontKey;
import org.extex.font.exception.FontException;
import org.extex.font.format.TfmMetricFont;

/**
 * Backend font manager for a tfm font.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class TfmBackendFontManager extends AbstractBackendFontManager
        implements
            BackendFontManager {

    /**
     * Class for a back-end character.
     */
    private class BackendCharacterImpl implements BackendCharacter {

        /**
         * The Unicode char.
         */
        private final UnicodeChar uc;

        /**
         * Creates a new object.
         * 
         * @param uc the Unicode char.
         */
        public BackendCharacterImpl(UnicodeChar uc) {

            this.uc = uc;
        }

    public int getId() {

            int cp = uc.getCodePoint();

            if (cp >= Unicode.OFFSET && cp <= Unicode.OFFSET + 0xff) {
                return cp - Unicode.OFFSET;
            }

            if (cp <= 255) {
                return cp;
            }
            return 0;
        }

    public String getName() {

            return Integer.toString(getId());
        }

        @Override
        public int hashCode() {

            return uc.hashCode();
        }

        @Override
        public boolean equals(Object obj) {

            if (obj != null && obj instanceof BackendCharacter) {
                BackendCharacter ch = (BackendCharacter) obj;
                return ch.getId() == getId();
            }

            return false;
        }

    }


    public TfmBackendFontManager() {

    }

    /**
*      org.extex.core.UnicodeChar)
     */
    public boolean recognize(FontKey fontKey, UnicodeChar uc)
            throws FontException {

        if (fontKey == null) {
            throw new IllegalArgumentException("fontkey");
        }
        if (uc == null) {
            throw new IllegalArgumentException("unicodechar");
        }

        ManagerInfo info = fontList.get(fontKey);
        if (info != null) {
            newRecongnizedFont = false;
            recognizedFont = info.getBackendFont();
            recognizedCharcterId = new BackendCharacterImpl(uc);
            info.add(recognizedCharcterId);
            return true;
        }

        BackendFont recognizedFonttmp = factory.getBackendFont(fontKey);
        if (recognizedFonttmp == null) {
            return false;
        }

        // only tfm metric fonts
        if (!(recognizedFonttmp instanceof TfmMetricFont)) {
            return false;
        }

        info = new ManagerInfo(fontKey, this);
        newRecongnizedFont = true;
        recognizedFont = recognizedFonttmp;
        info.setBackendFont(recognizedFont);
        recognizedCharcterId = new BackendCharacterImpl(uc);
        info.add(recognizedCharcterId);
        fontList.put(fontKey, info);

        return true;
    }

}
