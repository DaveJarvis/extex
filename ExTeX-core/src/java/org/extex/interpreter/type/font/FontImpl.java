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
import org.extex.font.FontByteArray;
import org.extex.font.FontKey;
import org.extex.font.Glyph;
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
     * The field <tt>cacheChar</tt> contains the Unicode character for the
     * most recently accessed glyph.
     */
    private transient UnicodeChar cacheChar = null;

    /**
     * The field <tt>cacheGlyph</tt> contains the cached glyph or
     * <code>null</code>. This means the last glyph accessed is stored here to
     * speed up access.
     */
    private transient Glyph cacheGlyph = null;

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
        key = null;
    }

    /**
     * Create a new Object
     *
     * @param font the font
     */
    public FontImpl(final ExtexFont font) {

        super();

        this.font = font;
        key = (font != null ? font.getFontKey() : null);
    }

    /**
     * @see org.extex.font.type.Fount#getActualSize()
     */
    public FixedDimen getActualSize() {

        return font.getActualSize();
    }

    /**
     * @see org.extex.font.type.Fount#getCheckSum()
     */
    public int getCheckSum() {

        // mgn: umbauen
        return 0; //font.getCheckSum();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getDepth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        return font.getDepth(uc);
    }

    /**
     * @see org.extex.font.type.Fount#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return font.getDesignSize();
    }

    /**
     * @see org.extex.font.type.Fount#getEm()
     */
    public FixedDimen getEm() {

        return font.getEm();
    }

    /**
     * @see org.extex.font.type.Fount#getEx()
     */
    public FixedDimen getEx() {

        return font.getEx();
    }

    /**
     * @see org.extex.font.type.Fount#getFontByteArray()
     */
    public FontByteArray getFontByteArray() {

        // mgn: umbauen
        return null;//font.getFontByteArray();
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
     * @see org.extex.font.type.Fount#getFontKey()
     */
    public FontKey getFontKey() {

        return key;
    }

    /**
     * @see org.extex.font.type.Fount#getFontName()
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
     * @see org.extex.interpreter.type.font.Font#getHeight(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        return font.getHeight(uc);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getItalicCorrection(
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

        return font.getItalicCorrection(uc);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getKerning(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

        return font.getKerning(uc1, uc2);
    }

    /**
     * @see org.extex.font.type.Fount#getLetterSpacing()
     */
    public FixedGlue getLetterSpacing() {

        // mgn: umbauen
        return null;//font.getLetterSpacing();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getLigature(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        return font.getLigature(uc1, uc2);
    }

    /**
     * @see org.extex.font.type.Fount#getProperty(java.lang.String)
     */
    public String getProperty(final String k) {

        // mgn: umbauen
        return null;//font.getProperty(k);
    }

    /**
     * @see org.extex.font.type.Fount#getSpace()
     */
    public FixedGlue getSpace() {

        return font.getSpace();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getWidth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        return font.getWidth(uc);
    }

    /**
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
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return font.getFontName();
    }

}
