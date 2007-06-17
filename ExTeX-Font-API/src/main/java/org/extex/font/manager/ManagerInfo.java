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

package org.extex.font.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.extex.font.BackendCharacter;
import org.extex.font.BackendFont;
import org.extex.font.BackendFontManager;
import org.extex.font.FontKey;

/**
 * Class for the info of used fonts and characters.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class ManagerInfo {

    /**
     * The List for the characters.
     */
    private List<BackendCharacter> backendCharacterList =
            new ArrayList<BackendCharacter>();

    /**
     * The backend font.
     */
    private BackendFont backendFont;

    /**
     * The font key.
     */
    private FontKey fontKey;

    /**
     * The {@link BackendFontManager} for this info.
     */
    private BackendFontManager manager;

    /**
     * Creates a new object.
     * 
     * @param fontKey The font key.
     * @param manager The {@link BackendFontManager}.
     */
    public ManagerInfo(FontKey fontKey, BackendFontManager manager) {

        this.fontKey = fontKey;
        this.manager = manager;
    }

    /**
     * Add the char to the list.
     * 
     * @param ch The char to add.
     */
    public void add(BackendCharacter ch) {

        if (!backendCharacterList.contains(ch)) {
            backendCharacterList.add(ch);
        }
    }

    /**
     * Getter for backendFont.
     * 
     * @return the backendFont
     */
    public BackendFont getBackendFont() {

        return backendFont;
    }

    /**
     * Getter for fontKey.
     * 
     * @return the fontKey
     */
    public FontKey getFontKey() {

        return fontKey;
    }

    /**
     * Getter for manager.
     * 
     * @return the manager
     */
    public BackendFontManager getManager() {

        return manager;
    }

    /**
     * Returns the iterator for the {@link BackendCharacter}s.
     * 
     * @return Returns the iterator for the {@link BackendCharacter}s.
     */
    public Iterator<BackendCharacter> iterate() {

        return backendCharacterList.iterator();
    }

    /**
     * Setter for backendFont.
     * 
     * @param backendFont the backendFont to set
     */
    public void setBackendFont(BackendFont backendFont) {

        this.backendFont = backendFont;
    }

}
