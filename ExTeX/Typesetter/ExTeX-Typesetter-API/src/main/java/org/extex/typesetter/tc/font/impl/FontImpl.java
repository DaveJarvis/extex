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
 */

package org.extex.typesetter.tc.font.impl;

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.font.ExtexFont;
import org.extex.font.FontKey;
import org.extex.framework.Registrar;
import org.extex.typesetter.tc.font.ModifiableFont;

import java.io.ObjectStreamException;

/**
 * This class constitutes a wrapper for a font. Here all information is stored
 * which should be saved in a format. Especially all modifiable fields have to
 * be kept in this class.
 * <p>
 *  The loadable and constant parts are delegated and should not make it into
 *  the format file. Thus it is necessary to reconstitute this contents when the
 *  format has to be provided by the loader.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FontImpl extends NullFont implements ModifiableFont {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The font.
     */
    private transient ExtexFont font;

    /**
     * The field <tt>key</tt> contains the font key. It is kept here since the
     * font is transient and will not make it into the format file.
     */
    private final FontKey key;

    /**
     * Create a new Object
     */
    public FontImpl() {

        this.font = null;
        this.key = null;
    }

    /**
     * Create a new Object
     *
     * @param font the font
     */
    public FontImpl(ExtexFont font) {

        this.font = font;
        this.key = (font != null ? font.getFontKey() : null);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.tc.font.impl.NullFont#getActualFontKey()
     */
    @Override
    public FontKey getActualFontKey() {

        return font.getFontKey();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.tc.font.Font#getActualSize()
     */
    @Override
    public FixedDimen getActualSize() {

        return font.getActualSize();
    }

    /**
     * Returns the check sum.
     *
     * @return the check sum
     *
     * @see org.extex.typesetter.tc.font.Font#getCheckSum()
     */
    @Override
    public int getCheckSum() {

        // mgn: umbauen
        return 0; //font.getCheckSum();
    }

    /**
     * Returns the depth of the char.
     *
     * @param uc the Unicode char
     *
     * @return the depth of the char
     *
     * @see org.extex.typesetter.tc.font.Font#getDepth(
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public FixedGlue getDepth(UnicodeChar uc) {

        return font.getDepth(uc);
    }

    /**
     * Returns the design size.
     *
     * @return the design size
     *
     * @see org.extex.typesetter.tc.font.Font#getDesignSize()
     */
    @Override
    public FixedDimen getDesignSize() {

        return font.getDesignSize();
    }

    /**
     * Returns the size of 1em.
     *
     * @return the size of 1em.
     *
     * @see org.extex.typesetter.tc.font.Font#getEm()
     */
    @Override
    public FixedDimen getEm() {

        return font.getEm();
    }

    /**
     * Returns the size of 1ex.
     *
     * @return the size of 1ex.
     *
     * @see org.extex.typesetter.tc.font.Font#getEx()
     */
    @Override
    public FixedDimen getEx() {

        return font.getEx();
    }

    /**
     * Return font dimen size with a key.
     *
     * @param k the key
     * @return the value for the key
     *
     * @see org.extex.typesetter.tc.font.Font#getFontDimen(java.lang.String)
     */
    @Override
    public FixedDimen getFontDimen(String k) {

        FixedDimen fd = super.getFontDimen(k);
        if (fd != null) {
            return fd;
        }
        return font.getFontDimen(k);
    }

    /**
     * Returns the key for the font.
     *
     * @return the key for the font
     *
     * @see org.extex.typesetter.tc.font.Font#getFontKey()
     */
    @Override
    public FontKey getFontKey() {

        return key;
    }

    /**
     * Returns the name of the font.
     *
     * @return Returns the name of the font.
     *
     * @see org.extex.typesetter.tc.font.Font#getFontName()
     */
    @Override
    public String getFontName() {

        return font.getFontName();
    }

    /**
     * Getter for the font.
     *
     * @return the font.
     */
    public ExtexFont getFount() {

        return font;
    }

    /**
     * Returns the height of the char.
     *
     * @param uc the Unicode char
     * @return the height of the char
     *
     * @see org.extex.typesetter.tc.font.Font#getHeight(
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public FixedGlue getHeight(UnicodeChar uc) {

        return font.getHeight(uc);
    }

    /**
     * Returns the italic correction of the char.
     *
     * @param uc the char
     *
     * @return the italic correction of the char
     *
     * @see org.extex.typesetter.tc.font.Font#getItalicCorrection(
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        return font.getItalicCorrection(uc);
    }

    /**
     * Returns the kerning between two characters.
     *
     * @param uc1 the first character
     * @param uc2 the second character
     *
     * @return the kerning between two characters
     *
     * @see org.extex.typesetter.tc.font.Font#getKerning(
     *      org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        return font.getKerning(uc1, uc2);
    }

    /**
     * Returns the ligature for two characters.
     *
     * @param uc1 the first character
     * @param uc2 the second character
     *
     * @return Returns the ligature for two characters
     *
     * @see org.extex.typesetter.tc.font.Font#getLigature(
     *      org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

        return font.getLigature(uc1, uc2);
    }

    /**
     * Returns the size of the 'space'.
     *
     * @return the size of the 'space'
     *
     * @see org.extex.typesetter.tc.font.Font#getSpace()
     */
    @Override
    public FixedGlue getSpace() {

        return font.getSpace();
    }

    /**
     * Returns the width of a character.
     *
     * @param uc the character
     *
     * @return the width of the character
     *
     * @see org.extex.typesetter.tc.font.Font#getWidth(
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public FixedGlue getWidth(UnicodeChar uc) {

        return font.getWidth(uc);
    }

    /**
     * Determine whether the glyph for a given character is present in this font.
     *
     * @param uc the character
     *
     * @return <code>true</code> iff the glyph is present
     *
     * @see org.extex.typesetter.tc.font.Font#hasGlyph(
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public boolean hasGlyph(UnicodeChar uc) {

        return font.hasGlyph(uc);
    }

    /**
     * Magic method for deserialization.
     *
     * @return the reconnection result
     *
     * @throws ObjectStreamException in case of an error
     */
    protected Object readResolve() throws ObjectStreamException {

        return Registrar.reconnect(this);
    }

    /**
     * Setter for the font.
     *
     * @param font the font to set
     *
     * @see org.extex.typesetter.tc.font.ModifiableFont#setFont(org.extex.font.ExtexFont)
     */
    public void setFont(ExtexFont font) {

        this.font = font;
    }

    /**
     * Return the printable representation for this instance.
     *
     * @return the printable representation for this instance
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return font.getFontName();
    }

}
