/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.postscript.util;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.font.BackendCharacter;
import org.extex.font.BackendFont;
import org.extex.font.BackendFontManager;
import org.extex.font.exception.FontException;
import org.extex.font.manager.ManagerInfo;
import org.extex.typesetter.tc.font.Font;

/**
 * The font manager keeps track of the fonts and characters used.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FontManager implements Iterable<ManagerInfo> {

    /**
     * The field <tt>currentFont</tt> contains the font encountered most
     * recently.
     */
    private BackendFont currentFont = null;

    /**
     * The field <tt>fntNo</tt> contains the next number to be used for a font
     * changing macro number.
     */
    private int fntNo = 1;

    /**
     * The field <tt>fonts</tt> contains the registered fonts and characters.
     */
    // private Map<Font, Map<UnicodeChar, UnicodeChar>> fonts =
    // new HashMap<Font, Map<UnicodeChar, UnicodeChar>>();
    /**
     * The field <tt>manager</tt> contains the encapsulated back-end font
     * manager.
     */
    private BackendFontManager manager;

    /**
     * The field <tt>texdict</tt> contains the definition of font changing
     * functions.
     */
    private StringBuilder texdict = null;

    /**
     * The field <tt>recognizedCharId</tt> contains the most recently
     * recognized back-end character.
     */
    private BackendCharacter recognizedCharId = null;

    /**
     * Creates a new object.
     * 
     * @param manager the back-end front manager
     */
    public FontManager(BackendFontManager manager) {

        super();
        this.manager = manager;
    }

    /**
     * Receive the information that a character in a certain font has been used
     * and should be remembered.
     * 
     * @param font the font which is used
     * @param c the character in the font which is used
     * 
     * @return <code>true</code> iff the font is not the one previously
     *         reported
     * 
     * @throws GeneralException in case of an error
     */
    public String add(Font font, UnicodeChar c) throws GeneralException {

        try {
            if (manager.recognize(font.getActualFontKey(), c)) {
                BackendFont fnt = manager.getRecognizedFont();
                recognizedCharId = manager.getRecognizedCharId();

                // Map<UnicodeChar, UnicodeChar> map = fonts.get(font);
                // if (map == null) {
                // map = new HashMap<UnicodeChar, UnicodeChar>();
                // fonts.put(font, map);
                // }
                // map.put(c, c);

                if (fnt != currentFont) {
                    currentFont = fnt;
                    String n = Integer.toString(fntNo++);
                    if (texdict == null) {
                        texdict = new StringBuilder("TeXDict begin\n");
                    }
                    texdict.append("/F");
                    texdict.append(n);
                    texdict.append("{/");
                    texdict.append(fnt.getName());
                    texdict.append(" findfont ");
                    PsUnit.toPoint(font.getActualSize(), texdict, false);
                    texdict.append(" scalefont setfont}def\n");

                    return "F" + n + " ";
                }
            } else {
                recognizedCharId = null;
            }
        } catch (FontException e) {
            throw new NoHelpException(e);
        }

        return null;
    }

    /**
     * Get the most recently recognized back-end character. This might be
     * <code>null</code> if the character is not defined in the font.
     * 
     * @return the back-end character
     * 
     * @see org.extex.font.BackendFontManager#getRecognizedCharId()
     */
    public BackendCharacter getRecognizedCharId() {

        return recognizedCharId;
    }

    /**
     * Return the iterator for all recognized back-end fonts as
     * {@link ManagerInfo}. The fonts are sorted by the name.
     * 
     * @return the iterator for all recognized back-end font as
     *         {@link ManagerInfo}.
     * 
     * @see org.extex.font.BackendFontManager#iterate()
     */
    public Iterator<ManagerInfo> iterator() {

        return manager.iterate();
    }

    /**
     * Clear the memory and forget everything about fonts used.
     */
    public void reset() {

        // fonts.clear();
        currentFont = null;
        fntNo = 1;
        manager.reset();
    }

    /**
     * Write all fonts to a given PostScript stream.
     * 
     * @param stream the target stream
     * 
     * @throws IOException in case of an IO error
     */
    public void write(PrintStream stream) throws IOException {

        if (texdict != null) {
            stream.print(texdict);
            stream.println("end");
        }
    }

}
