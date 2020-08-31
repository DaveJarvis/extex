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
 *
 */

package org.extex.font.format.tfm;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.util.file.random.RandomAccessInputStream;
import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * This class read a TFM-file.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @see <a href="package-summary.html#TFMformat">TFM-Format</a>
 */
public class TfmReader implements Serializable {

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The char info.
   */
  private final TfmCharInfoArray charinfo;

  /**
   * The depth.
   */
  private final TfmDepthArray depth;

  // /**
  // * Encoder factory.
  // */
  // private EncFactory encfactory;

  /**
   * encoding table.
   */
  private String[] enctable;

  /**
   * the exten
   */
  private final TfmExtenArray exten;

  /**
   * The font name.
   */
  private final String fontname;

  /**
   * The header.
   */
  private final TfmHeaderArray header;

  /**
   * The height.
   */
  private final TfmHeightArray height;

  /**
   * The italic.
   */
  private final TfmItalicArray italic;

  /**
   * The kern.
   */
  private final TfmKernArray kern;

  /**
   * The lengths in the file.
   */
  private final TfmHeaderLengths lengths;

  /**
   * The lig/kern array.
   */
  private final TfmLigKernArray ligkern;

  /**
   * The param.
   */
  private final TfmParamArray param;

  /**
   * pfb filename.
   */
  private String pfbfilename;

  // /**
  // * the pfb parser.
  // */
  // private PfbParser pfbparser;

  // /**
  // * psfontencoding.
  // */
  // private PsFontEncoding psfenc;

  // /**
  // * psfontmap.
  // */
  // private PsFontsMapReader psfontmap;

  /**
   * The width.
   */
  private final TfmWidthArray width;

  /**
   * Create e new object.
   *
   * @param in        The input.
   * @param afontname The font name.
   * @throws IOException if a IO-error occurred.
   */
  public TfmReader( InputStream in, String afontname ) throws IOException {

    this( new RandomAccessInputStream( in ), afontname );
  }

  /**
   * Create e new object.
   *
   * @param rar       The input.
   * @param afontname The font name.
   * @throws IOException if a IO-error occurred.
   */
  public TfmReader( RandomAccessR rar, String afontname ) throws IOException {

    fontname = afontname;

    // read the input
    lengths = new TfmHeaderLengths( rar );
    header = new TfmHeaderArray( rar, lengths.getLh() );
    charinfo = new TfmCharInfoArray( rar, lengths.getCc() );
    width = new TfmWidthArray( rar, lengths.getNw() );
    height = new TfmHeightArray( rar, lengths.getNh() );
    depth = new TfmDepthArray( rar, lengths.getNd() );
    italic = new TfmItalicArray( rar, lengths.getNi() );
    ligkern = new TfmLigKernArray( rar, lengths.getNl() );
    kern = new TfmKernArray( rar, lengths.getNk() );
    exten = new TfmExtenArray( rar, lengths.getNe() );
    param = new TfmParamArray( rar, lengths.getNp(), header.getFontType() );

    // close input
    rar.close();

    // calculate lig/kern
    ligkern.calculate( charinfo, kern, lengths.getBc() );

    // create chartable
    charinfo.createCharTable( width, height, depth, italic, exten, lengths
        .getBc(), ligkern.getLigKernTable() );

    // create the lig/kern map for each character
    charinfo.createLigKernMap();

  }

  /**
   * Returns the charinfo.
   *
   * @return Returns the charinfo.
   */
  public TfmCharInfoArray getCharinfo() {

    return charinfo;
  }

  /**
   * Returns the checksum.
   *
   * @return Returns the checksum.
   */
  public int getChecksum() {

    return header.getChecksum();
  }

  /**
   * Returns the coding scheme of the font.
   *
   * @return the coding scheme of the font.
   * @see org.extex.font.format.tfm.TfmHeaderArray#getCodingscheme()
   */
  public String getCodingscheme() {

    return header.getCodingscheme();
  }

  /**
   * Returns the depth.
   *
   * @return Returns the depth.
   */
  public TfmDepthArray getDepth() {

    return depth;
  }

  /**
   * Returns the depth of a char.
   *
   * @param pos the position
   * @return the depth of a char, or {@code null}, if it does not
   * exist.
   */
  public TfmFixWord getDepth( int pos ) {

    TfmCharInfoWord ci = charinfo.getCharInfoWord( pos );
    if( ci != null ) {
      return ci.getDepth();
    }
    return null;
  }

  /**
   * Returns the design size.
   *
   * @return Returns the design size.
   */
  public FixedDimen getDesignSize() {

    TfmFixWord ds = header.getDesignsize();

    return new Dimen( ds.getValue() >> 4 );
  }

  /**
   * Returns the design size as fix word.
   *
   * @return Returns the design size as fix word.
   */
  public TfmFixWord getDesignSizeAsFixWord() {

    return header.getDesignsize();
  }

  // /**
  // * Returns the encfactory.
  // * @return Returns the encfactory.
  // */
  // public EncFactory getEncfactory() {

  // return encfactory;
  // }

  /**
   * Returns the enctable.
   *
   * @return Returns the enctable.
   */
  public String[] getEnctable() {

    return enctable;
  }

  /**
   * Returns the exten.
   *
   * @return Returns the exten.
   */
  public TfmExtenArray getExten() {

    return exten;
  }

  /**
   * Returns the face of the font.
   *
   * @return Returns the face of the font.
   */
  public int getFace() {

    return header.getFace();
  }

  /**
   * Returns the font family.
   *
   * @return Returns the font family.
   */
  public String getFontFamily() {

    return header.getFontfamily();
  }

  /**
   * Returns the fontname.
   *
   * @return Returns the fontname.
   */
  public String getFontname() {

    return fontname;
  }

  /**
   * Returns the font type.
   *
   * @return Returns the font type.
   */
  public TfmFontType getFontType() {

    return header.getFontType();
  }

  /**
   * Returns the header.
   *
   * @return Returns the header.
   */
  public TfmHeaderArray getHeader() {

    return header;
  }

  /**
   * Returns the height.
   *
   * @return Returns the height.
   */
  public TfmHeightArray getHeight() {

    return height;
  }

  /**
   * Returns the height of a char.
   *
   * @param pos the position
   * @return the height of a char, or {@code null}, if it does not
   * exist.
   */
  public TfmFixWord getHeight( int pos ) {

    TfmCharInfoWord ci = charinfo.getCharInfoWord( pos );
    if( ci != null ) {
      return ci.getHeight();
    }
    return null;
  }

  /**
   * Returns the italic.
   *
   * @return Returns the italic.
   */
  public TfmItalicArray getItalic() {

    return italic;
  }

  /**
   * Returns the italic correction of a char.
   *
   * @param pos the position
   * @return the italic correction of a char, or {@code null}, if it
   * does not exist.
   */
  public TfmFixWord getItalicCorrection( int pos ) {

    TfmCharInfoWord ci = charinfo.getCharInfoWord( pos );
    if( ci != null ) {
      return ci.getItalic();
    }
    return null;
  }

  /**
   * Returns the kern.
   *
   * @return Returns the kern.
   */
  public TfmKernArray getKern() {

    return kern;
  }

  /**
   * TODO mgn: missing JavaDoc
   *
   * @param cp1 ...
   * @param cp2 ...
   * @return ...
   * @see org.extex.font.format.tfm.TfmLigKernArray#getKerning(int, int)
   */
  public TfmFixWord getKerning( int cp1, int cp2 ) {

    return ligkern.getKerning( cp1, cp2 );
  }

  /**
   * Returns the lengths.
   *
   * @return Returns the lengths.
   */
  public TfmHeaderLengths getLengths() {

    return lengths;
  }

  /**
   * TODO mgn: missing JavaDoc
   *
   * @param cp1 ...
   * @param cp2 ...
   * @return ...
   * @see org.extex.font.format.tfm.TfmLigKernArray#getLigature(int, int)
   */
  public int getLigature( int cp1, int cp2 ) {

    return ligkern.getLigature( cp1, cp2 );
  }

  /**
   * Returns the ligkern.
   *
   * @return Returns the ligkern.
   */
  public TfmLigKernArray getLigkern() {

    return ligkern;
  }

  /**
   * Returns the param.
   *
   * @return Returns the param.
   */
  public TfmParamArray getParam() {

    return param;
  }

  /**
   * Returns the value of a font parameter.
   *
   * @param name the name of the parameter.
   * @return the value of a font parameter.
   */
  public FixedDimen getParam( String name ) {

    return param.getParam( name ).toDimen( getDesignSize() );
  }

  /**
   * Returns the value of a font parameter as a fix word.
   *
   * @param name the name of the parameter.
   * @return the value of a font parameter as a fix word.
   */
  public TfmFixWord getParamAsFixWord( String name ) {

    return param.getParam( name );
  }

  /**
   * Returns the pfb file name.
   *
   * @return Returns the pfb file name.
   */
  public String getPfbfilename() {

    return pfbfilename;
  }

  // /**
  // * Returns the pfbparser.
  // * @return Returns the pfbparser.
  // */
  // public PfbParser getPfbParser() {

  // return pfbparser;
  // }

  // /**
  // * Returns the psfenc.
  // * @return Returns the psfenc.
  // */
  // public PsFontEncoding getPsfenc() {

  // return psfenc;
  // }

  // /**
  // * Returns the psfontmap.
  // * @return Returns the psfontmap.
  // */
  // public PsFontsMapReader getPsfontmap() {

  // return psfontmap;
  // }

  /**
   * Returns the width.
   *
   * @return Returns the width.
   */
  public TfmWidthArray getWidth() {

    return width;
  }

  /**
   * Returns the width of a char.
   *
   * @param pos the position
   * @return the width of a char, or {@code null}, if it does not
   * exist.
   */
  public TfmFixWord getWidth( int pos ) {

    TfmCharInfoWord ci = charinfo.getCharInfoWord( pos );
    if( ci != null ) {
      return ci.getWidth();
    }
    return null;
  }

  // /**
  // * Set the fontmap reader and the encoding factory.
  // *
  // * @param apsfontmap the psfonts.map reader
  // * @param encf the encoding factory
  // *
  // * @throws FontException if a font-error occurred
  // * @throws ConfigurationException from the resource finder
  // */
  // public void setFontMapEncoding(PsFontsMapReader apsfontmap,
  // EncFactory encf) throws FontException, ConfigurationException {

  // psfontmap = apsfontmap;
  // encfactory = encf;

  // // read psfonts.map
  // if (psfontmap != null) {
  // psfenc = psfontmap.getPSFontEncoding(fontname);

  // // encoding
  // if (psfenc != null) {
  // if (!"".equals(psfenc.getEncfile())) {
  // enctable = encfactory.getEncodingTable(psfenc.getEncfile());
  // }
  // // filename
  // if (psfenc.getFontfile() != null) {
  // pfbfilename = filenameWithoutPath(psfenc.getFontfile());
  // }
  // // glyphname
  // if (enctable != null) {
  // charinfo.setEncodingTable(enctable);
  // }
  // }
  // }
  // }

  // /**
  // * The pfb parser to set.
  // * @param parser The pfb parser to set.
  // */
  // public void setPfbParser(PfbParser parser) {

  // pfbparser = parser;
  // if (enctable == null && parser != null) {

  // // no encoding table -> set the glyphname
  // String enc[] = parser.getEncoding();
  // // glyphname
  // charinfo.setEncodingTable(enc);
  // }
  // }

  /**
   * Visit for the {@link TfmVisitor}.
   *
   * @param visitor The visitor.
   * @throws IOException if a io error occurred.
   */
  public void visit( TfmVisitor visitor ) throws IOException {

    visitor.visitTfmReader( this );
    visitor.visitTfmHeaderLengths( lengths );
    visitor.visitTfmHeaderArray( header );
    visitor.visitTfmCharInfoArray( charinfo );
    visitor.visitTfmWidthArray( width );
    visitor.visitTfmHeightArray( height );
    visitor.visitTfmDepthArray( depth );
    visitor.visitTfmItalicArray( italic );
    visitor.visitTfmLigKernArray( ligkern );
    visitor.visitTfmKernArray( kern );
    visitor.visitTfmExtenArray( exten );
    visitor.visitTfmParamArray( param );
  }

}
