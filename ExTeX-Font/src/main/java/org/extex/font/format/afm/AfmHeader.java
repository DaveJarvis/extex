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

package org.extex.font.format.afm;

import java.io.IOException;

import org.extex.util.XMLWriterConvertible;
import org.extex.util.xml.XMLStreamWriter;

/**
 * AFM-Header.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class AfmHeader implements XMLWriterConvertible {

    /**
     * The Postscript font name.
     */
    private String fontname = "";

    /**
     * The full name of the font.
     */
    private String fullname = "";

    /**
     * The family name of the font.
     */
    private String familyname = "";

    /**
     * The weight of the font: normal, bold, etc.
     */
    private String weight = "";

    /**
     * The italic angle of the font, usually 0.0 or negative.
     */
    private float italicangle;

    /**
     * <code>true</code> if all the characters have the same width.
     */
    private boolean isfixedpitch;

    /**
     * The character set of the font.
     */
    private String characterset = "";

    /**
     * not init.
     */
    public static final float NOTINIT = -9999f;

    /**
     * The llx of the FontBox.
     */
    private float llx = NOTINIT;

    /**
     * The lly of the FontBox.
     */
    private float lly = NOTINIT;

    /**
     * The lurx of the FontBox.
     */
    private float urx = NOTINIT;

    /**
     * The ury of the FontBox.
     */
    private float ury = NOTINIT;

    /**
     * The underline position.
     */
    private float underlineposition;

    /**
     * The underline thickness.
     */
    private float underlinethickness;

    /**
     * The font's encoding name.
     * This encoding is
     * - StandardEncoding
     * - AdobeStandardEncoding
     * - For all other names the font is treated as symbolic.
     */
    private String encodingscheme;

    /**
     * CapHeight.
     */
    private float capheight;

    /**
     * XHeight.
     */
    private float xheight;

    /**
     * Ascender.
     */
    private float ascender;

    /**
     * Descender.
     */
    private float descender;

    /**
     * StdHW.
     */
    private float stdhw;

    /**
     * StdVW.
     */
    private float stdvw;

    /**
     * Create a new object.
     */
    public AfmHeader() {

        super();
    }

    /**
     * Returns the ascender.
     * @return Returns the ascender.
     */
    public float getAscender() {

        return ascender;
    }

    /**
     * Set the ascender.
     * @param a The ascender to set.
     */
    public void setAscender(float a) {

        ascender = a;
    }

    /**
     * Returns the capheight.
     * @return Returns the capheight.
     */
    public float getCapheight() {

        return capheight;
    }

    /**
     * Set the capheight.
     * @param height The capheight to set.
     */
    public void setCapheight(float height) {

        capheight = height;
    }

    /**
     * Returns the characterset.
     * @return Returns the characterset.
     */
    public String getCharacterset() {

        return characterset;
    }

    /**
     * Set the characterset.
     * @param cs The characterset to set.
     */
    public void setCharacterset(String cs) {

        characterset = cs;
    }

    /**
     * Returns the descender.
     * @return Returns the descender.
     */
    public float getDescender() {

        return descender;
    }

    /**
     * Set the desender.
     * @param d The descender to set.
     */
    public void setDescender(float d) {

        descender = d;
    }

    /**
     * Returns the encodingscheme.
     * @return Returns the encodingscheme.
     */
    public String getEncodingscheme() {

        return encodingscheme;
    }

    /**
     * Set the encodingscheme.
     * @param encoding The encodingscheme to set.
     */
    public void setEncodingscheme(String encoding) {

        encodingscheme = encoding;
    }

    /**
     * Returns the familyname.
     * @return Returns the familyname.
     */
    public String getFamilyname() {

        return familyname;
    }

    /**
     * Set the familyname.
     * @param fname The familyname to set.
     */
    public void setFamilyname(String fname) {

        familyname = fname;
    }

    /**
     * Returns the fontname.
     * @return Returns the fontname.
     */
    public String getFontname() {

        return fontname;
    }

    /**
     * Set the fontname.
     * @param fname The fontname to set.
     */
    public void setFontname(String fname) {

        fontname = fname;
    }

    /**
     * Returns the fullname.
     * @return Returns the fullname.
     */
    public String getFullname() {

        return fullname;
    }

    /**
     * Set the fullname.
     * @param fname The fullname to set.
     */
    public void setFullname(String fname) {

        fullname = fname;
    }

    /**
     * Returns the isfixedpitch.
     * @return Returns the isfixedpitch.
     */
    public boolean isFixedpitch() {

        return isfixedpitch;
    }

    /**
     * Set the isfixedpitch.
     * @param fixedpitch The isfixedpitch to set.
     */
    public void setFixedpitch(boolean fixedpitch) {

        isfixedpitch = fixedpitch;
    }

    /**
     * Returns the italicangle.
     * @return Returns the italicangle.
     */
    public float getItalicangle() {

        return italicangle;
    }

    /**
     * Set the italicangle.
     * @param i The italicangle to set.
     */
    public void setItalicangle(float i) {

        italicangle = i;
    }

    /**
     * Returns the llx.
     * @return Returns the llx.
     */
    public float getLlx() {

        return llx;
    }

    /**
     * Set the llx.
     * @param x The llx to set.
     */
    public void setLlx(float x) {

        llx = x;
    }

    /**
     * Returns the lly.
     * @return Returns the lly.
     */
    public float getLly() {

        return lly;
    }

    /**
     * Set the lly.
     * @param y The lly to set.
     */
    public void setLly(float y) {

        lly = y;
    }

    /**
     * Returns the stdhw.
     * @return Returns the stdhw.
     */
    public float getStdhw() {

        return stdhw;
    }

    /**
     * Set the stdhw.
     * @param hw The stdhw to set.
     */
    public void setStdhw(float hw) {

        stdhw = hw;
    }

    /**
     * Returns the stdvw.
     * @return Returns the stdvw.
     */
    public float getStdvw() {

        return stdvw;
    }

    /**
     * Set the stdvw.
     * @param vw The stdvw to set.
     */
    public void setStdvw(float vw) {

        stdvw = vw;
    }

    /**
     * Returns the underlineposition.
     * @return Returns the underlineposition.
     */
    public float getUnderlineposition() {

        return underlineposition;
    }

    /**
     * Set the underlineposition.
     * @param position The underlineposition to set.
     */
    public void setUnderlineposition(float position) {

        underlineposition = position;
    }

    /**
     * Returns the underlinethickness.
     * @return Returns the underlinethickness.
     */
    public float getUnderlinethickness() {

        return underlinethickness;
    }

    /**
     * Set the thickness.
     * @param thickness The underlinethickness to set.
     */
    public void setUnderlinethickness(float thickness) {

        underlinethickness = thickness;
    }

    /**
     * Returns the urx.
     * @return Returns the urx.
     */
    public float getUrx() {

        return urx;
    }

    /**
     * Set the urx.
     * @param x The urx to set.
     */
    public void setUrx(float x) {

        urx = x;
    }

    /**
     * Returns the ury.
     * @return Returns the ury.
     */
    public float getUry() {

        return ury;
    }

    /**
     * Set the ury.
     * @param y The ury to set.
     */
    public void setUry(float y) {

        ury = y;
    }

    /**
     * Returns the weight.
     * @return Returns the weight.
     */
    public String getWeight() {

        return weight;
    }

    /**
     * Set the weight.
     * @param w The weight to set.
     */
    public void setWeight(String w) {

        weight = w;
    }

    /**
     * Returns the xheight.
     * @return Returns the xheight.
     */
    public float getXheight() {

        return xheight;
    }

    /**
     * Set the xheight.
     * @param x The xheight to set.
     */
    public void setXheight(float x) {

        xheight = x;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("header");
        writer.writeAttribute("name", fontname);
        writer.writeAttribute("fullname", fullname);
        writer.writeAttribute("familyname", familyname);
        writer.writeAttribute("weight", weight);
        writer.writeFormatAttribute("italicangle", italicangle);
        writer.writeAttribute("isfixedpitch", isfixedpitch);
        writer.writeAttribute("characterset", characterset);
        writer.writeFormatAttribute("llx", llx);
        writer.writeFormatAttribute("lly", lly);
        writer.writeFormatAttribute("urx", urx);
        writer.writeFormatAttribute("ury", ury);
        writer.writeFormatAttribute("underlineposition", underlineposition);
        writer.writeFormatAttribute("underlinethickness", underlinethickness);
        writer.writeAttribute("encodingscheme", encodingscheme);
        writer.writeFormatAttribute("capheight", capheight);
        writer.writeFormatAttribute("xheight", xheight);
        writer.writeFormatAttribute("ascender", ascender);
        writer.writeFormatAttribute("descender", descender);
        writer.writeFormatAttribute("stdhw", stdhw);
        writer.writeFormatAttribute("stdvw", stdvw);
        writer.writeEndElement();
    }
}
