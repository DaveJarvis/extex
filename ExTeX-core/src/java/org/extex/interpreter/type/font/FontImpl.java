/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.font;

import java.io.ObjectStreamException;

import org.extex.font.ExtexFont;
import org.extex.font.FontKey;
import org.extex.font.type.other.NullFont;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.type.UnicodeChar;
import org.extex.util.framework.Registrar;

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
 * @version $Revision: 4726 $
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
    private FontKey key;

    /**
     * Create a new Object
     */
    public FontImpl() {

        super();

        this.font = null;
        this.key = null;
    }

    /**
     * Create a new Object
     *
     * @param font the font
     */
    public FontImpl(final ExtexFont font) {

        super();

        this.font = font;
        this.key = (font != null ? font.getFontKey() : null);
    }

    /**
     * Returns the actual size.
     *
     * @return the actual size
     *
     * @see org.extex.font.type.Fount#getActualSize()
     */
    public FixedDimen getActualSize() {

        return font.getActualSize();
    }

    /**
     * Returns the check sum.
     *
     * @return the check sum
     *
     * @see org.extex.font.type.Fount#getCheckSum()
     */
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
     * @see org.extex.interpreter.type.font.Font#getDepth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        return font.getDepth(uc);
    }

    /**
     * Returns the design size.
     *
     * @return the design size
     *
     * @see org.extex.font.type.Fount#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return font.getDesignSize();
    }

    /**
     * Returns the size of 1em.
     *
     * @return the size of 1em.
     *
     * @see org.extex.interpreter.type.font.Font#getEm()
     */
    public FixedDimen getEm() {

        return font.getEm();
    }

    /**
     * Returns the size of 1ex.
     *
     * @return the size of 1ex.
     *
     * @see org.extex.interpreter.type.font.Font#getEx()
     */
    public FixedDimen getEx() {

        return font.getEx();
    }

    /**
     * Return font dimen size with a key.
     *
     * @param k the key
     * @return the value for the key
     *
     * @see org.extex.font.type.Fount#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(final String k) {

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
     * @see org.extex.interpreter.type.font.Font#getFontKey()
     */
    public FontKey getFontKey() {

        return key;
    }

    /**
     * Returns the name of the font.
     *
     * @return Returns the name of the font.
     *
     * @see org.extex.interpreter.type.font.Font#getFontName()
     */
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
     * @see org.extex.interpreter.type.font.Font#getHeight(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        return font.getHeight(uc);
    }

    /**
     * Returns the italic correction of the char.
     *
     * @param uc the char
     *
     * @return the italic correction of the char
     *
     * @see org.extex.interpreter.type.font.Font#getItalicCorrection(
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

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
     * @see org.extex.interpreter.type.font.Font#getKerning(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

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
     * @see org.extex.interpreter.type.font.Font#getLigature(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        return font.getLigature(uc1, uc2);
    }

    /**
     * Returns the size of the 'space'.
     *
     * @return the size of the 'space'
     *
     * @see org.extex.interpreter.type.font.Font#getSpace()
     */
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
     * @see org.extex.interpreter.type.font.Font#getWidth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        return font.getWidth(uc);
    }

    /**
     * Determine whether the glyph for a given character is present in this font.
     *
     * @param uc the character
     *
     * @return <code>true</code> iff the glyph is present
     *
     * @see org.extex.interpreter.type.font.Font#hasGlyph(
     *      org.extex.type.UnicodeChar)
     */
    public boolean hasGlyph(final UnicodeChar uc) {

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
     * @see org.extex.interpreter.type.font.ModifiableFont#setFont(org.extex.font.ExtexFont)
     */
    public void setFont(final ExtexFont font) {

        this.font = font;
    }

    /**
     * Return the printable representation for this instance.
     *
     * @return the printable representation for this instance
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return font.getFontName();
    }

}
