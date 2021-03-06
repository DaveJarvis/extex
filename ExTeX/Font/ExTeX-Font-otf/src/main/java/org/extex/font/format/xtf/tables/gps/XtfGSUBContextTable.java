/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font.format.xtf.tables.gps;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;

/**
 * XtfGSUBContextTable.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class XtfGSUBContextTable extends XtfLookupTable {

  /**
   * Create a new object.
   *
   * @param format  the format
   * @param xtfGlyp The glyph name.
   */
  XtfGSUBContextTable( int format, XtfGlyphName xtfGlyp ) {

    super( format, xtfGlyp );

  }

  /**
   * Create a new Instance.
   *
   * @param rar      the input
   * @param offset   the offset
   * @param xtfGlyph The glyph name.
   * @return Returns the new instance.
   * @throws IOException if an IO-error occurs
   */
  public static XtfGSUBContextTable newInstance( RandomAccessR rar,
                                                 int offset,
                                                 XtfGlyphName xtfGlyph )
      throws IOException {

    // XtfGSUBSingleTable s = null;
    // rar.seek(offset);
    // int format = rar.readUnsignedShort();
    // if (format == 1) {
    // s = new SingleTableFormat1(rar, offset);
    // } else if (format == 2) {
    // s = new SingleTableFormat2(rar, offset);
    // }
    return new XtfGSUBContextTable( 0, xtfGlyph );
  }

  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "contexttable" );
    writer.writeEndElement();
  }

}
