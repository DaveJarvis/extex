/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

import org.extex.font.FontByteArray;
import org.extex.font.FountKey;
import org.extex.font.Glyph;
import org.extex.font.type.BoundingBox;
import org.extex.font.type.InternalFount;
import org.extex.font.type.other.NullFont;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
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
public class FontImpl extends NullFont {

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
     * The fount.
     */
    private transient InternalFount fount;

    /**
     * The field <tt>key</tt> contains the font key. It is kept here since the
     * fount is transient and will not make it into the format file.
     */
    private FountKey key;

    /**
     * Create a new Object
     */
    public FontImpl() {

        super();

        this.fount = null;
        key = null;
    }

    /**
     * Create a new Object
     *
     * @param fount the fount
     */
    public FontImpl(final InternalFount fount) {

        super();

        this.fount = fount;
        key = (fount != null ? fount.getFontKey() : null);
    }

    /**
     * @see org.extex.font.type.Fount#getActualSize()
     */
    public FixedDimen getActualSize() {

        return fount.getActualSize();
    }

    /**
     * @see org.extex.font.type.Fount#getBoundingBox()
     */
    public BoundingBox getBoundingBox() {

        return fount.getBoundingBox();
    }

    /**
     * @see org.extex.font.type.Fount#getCheckSum()
     */
    public int getCheckSum() {

        return fount.getCheckSum();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getDepth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        Glyph g = getGlyph(uc);
        return (g == null ? Glue.ZERO : new Glue(g.getDepth()));
    }

    /**
     * @see org.extex.font.type.Fount#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return fount.getDesignSize();
    }

    /**
     * @see org.extex.font.type.Fount#getEm()
     */
    public FixedDimen getEm() {

        return fount.getEm();
    }

    /**
     * @see org.extex.font.type.Fount#getEx()
     */
    public FixedDimen getEx() {

        return fount.getEx();
    }

    /**
     * @see org.extex.font.type.Fount#getFontByteArray()
     */
    public FontByteArray getFontByteArray() {

        return fount.getFontByteArray();
    }

    /**
     * @see org.extex.font.type.Fount#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(final String key) {

        FixedDimen fd = super.getFontDimen(key);
        if (fd != null) {
            return fd;
        }
        return fount.getFontDimen(key);
    }

    /**
     * @see org.extex.font.type.Fount#getFontKey()
     */
    public FountKey getFontKey() {

        return key;
    }

    /**
     * @see org.extex.font.type.Fount#getFontName()
     */
    public String getFontName() {

        return fount.getFontName();
    }

    /**
     * Getter for the fount.
     *
     * @return the fount.
     */
    public InternalFount getFount() {

        return fount;
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getGlyph(
     *      org.extex.type.UnicodeChar)
     */
    public Glyph getGlyph(final UnicodeChar c) {

        if (cacheChar != null && cacheChar.equals(c)) {
            return cacheGlyph;
        }
        cacheChar = c;
        cacheGlyph = (fount != null ? fount.getGlyph(c) : null);

        return cacheGlyph;
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getHeight(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        Glyph g = getGlyph(uc);
        return (g == null ? Glue.ZERO : new Glue(g.getHeight()));
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getItalicCorrection(
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

        Glyph g = getGlyph(uc);
        return (g == null ? Dimen.ZERO_PT : g.getItalicCorrection());
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getKerning(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

        Glyph g = getGlyph(uc1);
        return (g == null ? Dimen.ZERO_PT : g.getKerning(uc2));
    }

    /**
     * @see org.extex.font.type.Fount#getLetterSpacing()
     */
    public FixedGlue getLetterSpacing() {

        return fount.getLetterSpacing();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getLigature(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        Glyph g = getGlyph(uc1);
        return (g == null ? null : g.getLigature(uc2));
    }

    /**
     * @see org.extex.font.type.Fount#getProperty(java.lang.String)
     */
    public String getProperty(final String k) {

        return fount.getProperty(k);
    }

    /**
     * @see org.extex.font.type.Fount#getSpace()
     */
    public FixedGlue getSpace() {

        return fount.getSpace();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getWidth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        Glyph g = getGlyph(uc);
        return (g == null ? Glue.ZERO : new Glue(g.getWidth()));
    }

    /**
     * @see org.extex.interpreter.type.font.Font#hasGlyph(
     *      org.extex.type.UnicodeChar)
     */
    public boolean hasGlyph(final UnicodeChar uc) {

        return getGlyph(uc) != null;
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
     * Setter for the fount.
     *
     * @param fount the fount to set
     */
    public void setFount(final InternalFount fount) {

        this.fount = fount;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return fount.getFontName();
    }
    
}
