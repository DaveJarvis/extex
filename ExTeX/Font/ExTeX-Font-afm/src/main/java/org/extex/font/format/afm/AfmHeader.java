/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;

/**
 * AFM-Header.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class AfmHeader implements XMLWriterConvertible {

  /**
   * not init.
   */
  public static final float NOTINIT = -9999f;

  /**
   * Ascender.
   */
  private float ascender;

  /**
   * CapHeight.
   */
  private float capheight;

  /**
   * The character set of the font.
   */
  private String characterset = "";

  /**
   * The comment.
   */
  private String comment = "";

  /**
   * Descender.
   */
  private float descender;

  /**
   * The font's encoding name. This encoding is - StandardEncoding -
   * AdobeStandardEncoding - For all other names the font is treated as
   * symbolic.
   */
  private String encodingScheme;

  /**
   * The family name of the font.
   */
  private String familyName = "";

  /**
   * The Postscript font name.
   */
  private String fontName = "";

  /**
   * The full name of the font.
   */
  private String fullName = "";

  /**
   * {@code true} if all the characters have the same width.
   */
  private boolean isFixedPitch;

  /**
   * The italic angle of the font, usually 0.0 or negative.
   */
  private float italicangle;

  /**
   * The llx of the FontBox.
   */
  private float llx = NOTINIT;

  /**
   * The lly of the FontBox.
   */
  private float lly = NOTINIT;

  /**
   * The notice.
   */
  private String notice = "";

  /**
   * StdHW.
   */
  private float stdhw;

  /**
   * StdVW.
   */
  private float stdvw;

  /**
   * The underline position.
   */
  private float underlinePosition;

  /**
   * The underline thickness.
   */
  private float underlineThickness;

  /**
   * The lurx of the FontBox.
   */
  private float urx = NOTINIT;

  /**
   * The ury of the FontBox.
   */
  private float ury = NOTINIT;

  /**
   * The weight of the font: normal, bold, etc.
   */
  private String weight = "";

  /**
   * XHeight.
   */
  private float xheight;

  /**
   * Create a new object.
   */
  public AfmHeader() {

  }

  /**
   * Add a comment to the string.
   *
   * @param com The comment to add.
   */
  public void addComment( String com ) {

    if( comment.length() != 0 ) {
      comment += " ";
    }
    comment += com;
  }

  /**
   * Add a notice to the string.
   *
   * @param not The notice to add.
   */
  public void addNotice( String not ) {

    if( notice.length() != 0 ) {
      notice += " ";
    }
    notice += not;
  }

  /**
   * Returns the ascender.
   *
   * @return the ascender
   */
  public float getAscender() {

    return ascender;
  }

  /**
   * Returns the cap height.
   *
   * @return the cap height
   */
  public float getCapheight() {

    return capheight;
  }

  /**
   * Returns the character set.
   *
   * @return the character set
   */
  public String getCharacterset() {

    return characterset;
  }

  /**
   * Getter for comment.
   *
   * @return the comment
   */
  public String getComment() {

    return comment;
  }

  /**
   * Returns the descender.
   *
   * @return the descender
   */
  public float getDescender() {

    return descender;
  }

  /**
   * Returns the encoding scheme.
   *
   * @return Returns the encoding scheme.
   */
  public String getEncodingScheme() {

    return encodingScheme;
  }

  /**
   * Returns the family name.
   *
   * @return the family name
   */
  public String getFamilyName() {

    return familyName;
  }

  /**
   * Returns the font name.
   *
   * @return Returns the font name.
   */
  public String getFontName() {

    return fontName;
  }

  /**
   * Returns the full name.
   *
   * @return the full name
   */
  public String getFullName() {

    return fullName;
  }

  /**
   * Returns the italic angle.
   *
   * @return the italic angle
   */
  public float getItalicAngle() {

    return italicangle;
  }

  /**
   * Returns the llx.
   *
   * @return Returns the llx.
   */
  public float getLlx() {

    return llx;
  }

  /**
   * Returns the lly.
   *
   * @return Returns the lly.
   */
  public float getLly() {

    return lly;
  }

  /**
   * Getter for notice.
   *
   * @return the notice
   */
  public String getNotice() {

    return notice;
  }

  /**
   * Returns the stdhw.
   *
   * @return Returns the stdhw.
   */
  public float getStdhw() {

    return stdhw;
  }

  /**
   * Returns the stdvw.
   *
   * @return Returns the stdvw.
   */
  public float getStdvw() {

    return stdvw;
  }

  /**
   * Returns the underline position.
   *
   * @return the underline position.
   */
  public float getUnderlineposition() {

    return underlinePosition;
  }

  /**
   * Returns the underline thickness.
   *
   * @return the underline thickness.
   */
  public float getUnderlinethickness() {

    return underlineThickness;
  }

  /**
   * Returns the urx.
   *
   * @return the urx.
   */
  public float getUrx() {

    return urx;
  }

  /**
   * Returns the ury.
   *
   * @return the ury.
   */
  public float getUry() {

    return ury;
  }

  /**
   * Returns the weight.
   *
   * @return the weight
   */
  public String getWeight() {

    return weight;
  }

  /**
   * Returns the xheight.
   *
   * @return the xheight
   */
  public float getXheight() {

    return xheight;
  }

  /**
   * Returns the fixed pitch indicator.
   *
   * @return the fixed pitch indicator
   */
  public boolean isFixedPitch() {

    return isFixedPitch;
  }

  /**
   * Set the ascender.
   *
   * @param a The ascender to set.
   */
  public void setAscender( float a ) {

    ascender = a;
  }

  /**
   * Set the capheight.
   *
   * @param height The capheight to set.
   */
  public void setCapheight( float height ) {

    capheight = height;
  }

  /**
   * Set the characterset.
   *
   * @param cs The characterset to set.
   */
  public void setCharacterset( String cs ) {

    characterset = cs;
  }

  /**
   * Setter for comment.
   *
   * @param comment the comment to set
   */
  public void setComment( String comment ) {

    this.comment = comment;
  }

  /**
   * Set the desender.
   *
   * @param d The descender to set.
   */
  public void setDescender( float d ) {

    descender = d;
  }

  /**
   * Set the encodingscheme.
   *
   * @param encoding The encodingscheme to set.
   */
  public void setEncodingscheme( String encoding ) {

    encodingScheme = encoding;
  }

  /**
   * Set the familyname.
   *
   * @param fname The familyname to set.
   */
  public void setFamilyname( String fname ) {

    familyName = fname;
  }

  /**
   * Set the isfixedpitch.
   *
   * @param fixedpitch The isfixedpitch to set.
   */
  public void setFixedpitch( boolean fixedpitch ) {

    isFixedPitch = fixedpitch;
  }

  /**
   * Set the fontname.
   *
   * @param fname The fontname to set.
   */
  public void setFontname( String fname ) {

    fontName = fname;
  }

  /**
   * Set the fullname.
   *
   * @param fname The fullname to set.
   */
  public void setFullname( String fname ) {

    fullName = fname;
  }

  /**
   * Setter for isfixedpitch.
   *
   * @param isfixedpitch the isfixedpitch to set
   */
  public void setIsfixedpitch( boolean isfixedpitch ) {

    this.isFixedPitch = isfixedpitch;
  }

  /**
   * Set the italicangle.
   *
   * @param i The italicangle to set.
   */
  public void setItalicangle( float i ) {

    italicangle = i;
  }

  /**
   * Set the llx.
   *
   * @param x The llx to set.
   */
  public void setLlx( float x ) {

    llx = x;
  }

  /**
   * Set the lly.
   *
   * @param y The lly to set.
   */
  public void setLly( float y ) {

    lly = y;
  }

  /**
   * Setter for notice.
   *
   * @param notice the notice to set
   */
  public void setNotice( String notice ) {

    this.notice = notice;
  }

  /**
   * Set the stdhw.
   *
   * @param hw The stdhw to set.
   */
  public void setStdhw( float hw ) {

    stdhw = hw;
  }

  /**
   * Set the stdvw.
   *
   * @param vw The stdvw to set.
   */
  public void setStdvw( float vw ) {

    stdvw = vw;
  }

  /**
   * Set the underlineposition.
   *
   * @param position The underlineposition to set.
   */
  public void setUnderlineposition( float position ) {

    underlinePosition = position;
  }

  /**
   * Set the thickness.
   *
   * @param thickness The underlinethickness to set.
   */
  public void setUnderlinethickness( float thickness ) {

    underlineThickness = thickness;
  }

  /**
   * Set the urx.
   *
   * @param x The urx to set.
   */
  public void setUrx( float x ) {

    urx = x;
  }

  /**
   * Set the ury.
   *
   * @param y The ury to set.
   */
  public void setUry( float y ) {

    ury = y;
  }

  /**
   * Set the weight.
   *
   * @param w The weight to set.
   */
  public void setWeight( String w ) {

    weight = w;
  }

  /**
   * Set the xheight.
   *
   * @param x The xheight to set.
   */
  public void setXheight( float x ) {

    xheight = x;
  }

  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "header" );
    writer.writeAttribute( "name", fontName );
    writer.writeAttribute( "fullname", fullName );
    writer.writeAttribute( "familyname", familyName );
    writer.writeAttribute( "comment", comment );
    writer.writeAttribute( "notice", notice );
    writer.writeAttribute( "weight", weight );
    writer.writeFormatAttribute( "italicangle", italicangle );
    writer.writeAttribute( "isfixedpitch", isFixedPitch );
    writer.writeAttribute( "characterset", characterset );
    writer.writeFormatAttribute( "llx", llx );
    writer.writeFormatAttribute( "lly", lly );
    writer.writeFormatAttribute( "urx", urx );
    writer.writeFormatAttribute( "ury", ury );
    writer.writeFormatAttribute( "underlineposition", underlinePosition );
    writer.writeFormatAttribute( "underlinethickness", underlineThickness );
    writer.writeAttribute( "encodingscheme", encodingScheme );
    writer.writeFormatAttribute( "capheight", capheight );
    writer.writeFormatAttribute( "xheight", xheight );
    writer.writeFormatAttribute( "ascender", ascender );
    writer.writeFormatAttribute( "descender", descender );
    writer.writeFormatAttribute( "stdhw", stdhw );
    writer.writeFormatAttribute( "stdvw", stdvw );
    writer.writeEndElement();
  }
}
