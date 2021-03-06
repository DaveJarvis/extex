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

package org.extex.font.format.vf;

import org.extex.core.dimen.FixedDimen;
import org.extex.font.exception.FontException;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.font.format.tfm.TfmReader;
import org.extex.font.format.vf.command.VfCommand;
import org.extex.font.format.vf.command.VfCommandCharacterPackets;
import org.extex.font.format.vf.command.VfCommandFontDef;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.util.file.random.RandomAccessInputStream;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class read a VF-file.
 * <p>
 * A VF file is organized as a stream of 8-bit bytes, using conventions borrowed
 * from DVI and PK files. Thus, a device driver that knows about DVI and PK
 * format will already contain most of the mechanisms necessary to process VF
 * files. We shall assume that DVI format is understood; the conventions in the
 * DVI documentation (see, for example, TeX: The Program, part 31) are adopted
 * here to define VF format.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @see <a href="package-summary.html#VFformat">VF-Format</a>
 */
public class VfFont implements XMLWriterConvertible {

  /**
   * units per em (default-value)
   */
  public static final int UNITS_PER_EM_DEFAULT = 1000;

  /**
   * The field {@code localizer} contains the localizer. It is initiated
   * with a localizer for the name of this class.
   */
  private final Localizer localizer =
      LocalizerFactory.getLocalizer( VfFont.class );

  /**
   * The font anme.
   */
  private final String fontname;

  /**
   * The tfm reader.
   */
  private final TfmReader tfmReader;

  /**
   * The list of commands.
   */
  private List<VfCommand> cmds;

  /**
   * The map for the fonts.
   */
  private Map<Integer, VfCommandFontDef> fonts;

  /**
   * The map for the characters.
   */
  private Map<Integer, VfCommandCharacterPackets> chars;

  /**
   * Creates a new object.
   *
   * @param fontname  The font name.
   * @param in        The input.
   * @param tfmreader The tfm reader for the font.
   * @throws FontException if a font errror occurred.
   */
  public VfFont( String fontname, InputStream in, TfmReader tfmreader )
      throws FontException {

    if( fontname == null ) {
      throw new IllegalArgumentException( "fontname" );
    }
    if( tfmreader == null ) {
      throw new IllegalArgumentException( "tfmreader" );
    }
    if( in == null ) {
      throw new IllegalArgumentException( "in" );
    }

    this.fontname = fontname;
    this.tfmReader = tfmreader;

    readVF( in );
  }

  /**
   * Returns the char command or {@code null}, if not found.
   *
   * @param number The number of the char.
   * @return Returns the char command.
   */
  @SuppressWarnings("boxing")
  public VfCommandCharacterPackets getChar( int number ) {

    return chars.get( number );
  }

  /**
   * Getter for cmds.
   *
   * @return the cmds
   */
  public List<VfCommand> getCmds() {

    return cmds;
  }

  /**
   * Returns the design size.
   *
   * @return Returns the design size.
   * @see org.extex.font.format.tfm.TfmReader#getDesignSize()
   */
  public FixedDimen getDesignSize() {

    return tfmReader.getDesignSize();
  }

  /**
   * Returns the design size as fix word.
   *
   * @return Returns the design size as fix word.
   * @see org.extex.font.format.tfm.TfmReader#getDesignSizeAsFixWord()
   */
  public TfmFixWord getDesignSizeAsFixWord() {

    return tfmReader.getDesignSizeAsFixWord();
  }

  /**
   * Returns the font command or {@code null}, if not found.
   *
   * @param number The number of the font.
   * @return Returns the font command.
   */
  @SuppressWarnings("boxing")
  public VfCommandFontDef getFont( int number ) {

    return fonts.get( number );
  }

  /**
   * Getter for font name.
   *
   * @return the font name
   */
  public String getFontname() {

    return fontname;
  }

  /**
   * Getter for fonts.
   *
   * @return the fonts
   */
  public Map<Integer, VfCommandFontDef> getFonts() {

    return fonts;
  }

  /**
   * Reads the virtual font.
   *
   * @param in ...
   * @throws FontException if an
   */
  @SuppressWarnings("boxing")
  private void readVF( InputStream in ) throws FontException {

    cmds = new ArrayList<VfCommand>();
    fonts = new HashMap<Integer, VfCommandFontDef>();
    chars = new HashMap<Integer, VfCommandCharacterPackets>();

    try {
      RandomAccessR rar = new RandomAccessInputStream( in );

      while( true ) {
        VfCommand command = VfCommand.getInstance( localizer, rar );
        if( command == null ) {
          break;
        }
        if( command instanceof VfCommandFontDef ) {
          VfCommandFontDef fcmd = (VfCommandFontDef) command;
          fonts.put( fcmd.getFontnumbers(), fcmd );
        }
        if( command instanceof VfCommandCharacterPackets ) {
          VfCommandCharacterPackets ccmd =
              (VfCommandCharacterPackets) command;
          chars.put( ccmd.getCharactercode(), ccmd );
        }
        cmds.add( command );
      }

      rar.close();

    } catch( IOException e ) {
      throw new FontException( e.getLocalizedMessage() );
    }
  }

  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "vf" );
    writer.writeAttribute( "name", fontname );
    // tfmReader.writeXML(writer);
    for( int i = 0; i < cmds.size(); i++ ) {
      VfCommand command = cmds.get( i );
      command.writeXML( writer );
    }
    writer.writeEndElement();

  }

  // /**
  // * @see de.dante.extex.font.type.PlFormat#toPL(
  // * de.dante.extex.font.type.PlWriter)
  // */
  // public void toPL(final PlWriter out) throws IOException, FontException {

  // out.plopen("VTITLE ").plclose();
  // out.plopen("FAMILY").addStr(mastertfm.getFontFamily()).plclose();
  // out.plopen("FACE").addFace(mastertfm.getFace()).plclose();
  // out.plopen("CODINGSCHEME").addStr(
  // mastertfm.getHeader().getCodingscheme()).plclose();
  // out.plopen("DESIGNSIZE").addReal(mastertfm.getDesignSizeAsDouble())
  // .plclose();
  // out.addComment("DESIGNSIZE IS IN POINTS");
  // out.addComment("OTHER SIZES ARE MULTIPLES OF DESIGNSIZE");
  // out.plopen("CHECKSUM").addOct(mastertfm.getChecksum()).plclose();
  // mastertfm.getParam().toPL(out);
  // // print font-info
  // for (int i = 0; i < cmds.size(); i++) {
  // VfCommand command = (VfCommand) cmds.get(i);
  // if (command instanceof VfCommandFontDef) {
  // VfCommandFontDef fd = (VfCommandFontDef) command;
  // fd.toPL(out);
  // }
  // }
  // // ligtable
  // mastertfm.getLigkern().toPL(out);

  // // character
  // for (int i = 0; i < cmds.size(); i++) {
  // VfCommand command = (VfCommand) cmds.get(i);
  // if (command instanceof VfCommandCharacterPackets) {
  // VfCommandCharacterPackets ch = (VfCommandCharacterPackets) command;
  // ch.toPL(out);
  // }
  // }
  // }

  // /**
  // * @see de.dante.extex.font.type.FontMetric#getFontMetric()
  // */
  // public Element getFontMetric() throws FontException {

  // // create efm-file
  // Element root = new Element("fontgroup");
  // root.setAttribute("name", getFontname());
  // root.setAttribute("id", getFontname());
  // root.setAttribute("default-size", String.valueOf(mastertfm
  // .getDesignSizeAsDouble()));
  // root.setAttribute("empr", "100");
  // root.setAttribute("type", "vf");
  // root.setAttribute("virtual", "true");

  // Element fontdimen = new Element("fontdimen");
  // root.addContent(fontdimen);

  // Element font = new Element("font");
  // root.addContent(font);

  // font.setAttribute("font-name", getFontname());
  // font.setAttribute("font-family", getFontname());
  // root.setAttribute("units-per-em", "1000");
  // font.setAttribute("checksum", String.valueOf(mastertfm.getChecksum()));
  // font.setAttribute("type", mastertfm.getFontType().toTFMString());
  // mastertfm.getParam().addParam(fontdimen);

  // // character
  // for (int i = 0; i < cmds.size(); i++) {
  // VfCommand command = (VfCommand) cmds.get(i);
  // if (command instanceof VfCommandCharacterPackets) {
  // VfCommandCharacterPackets ch = (VfCommandCharacterPackets) command;
  // ch.addGlyph(font);
  // }
  // }
  // return root;
  // }
}
