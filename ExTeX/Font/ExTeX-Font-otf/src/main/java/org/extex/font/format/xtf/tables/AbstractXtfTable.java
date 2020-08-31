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

package org.extex.font.format.xtf.tables;

import org.extex.font.format.xtf.XtfReader;
import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;

/**
 * Abstract class for all TTF/OTF tables.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public abstract class AbstractXtfTable implements XtfTable, XtfGlyphName {

  private final XtfTableMap tablemap;

  public AbstractXtfTable( XtfTableMap tm ) {

    tablemap = tm;
  }

  /**
   * Returns the glyph name of an index.
   *
   * @param idx The index.
   * @return Returns the glyph name of an index.
   */
  public String getGlyphName( int idx ) {

    String gylphName = null;

    // first look in the post-table
    XtfTable post = getTableMap().get( XtfReader.POST );
    if( post instanceof TtfTablePOST ) {
      gylphName = ((TtfTablePOST) post).getGlyphName( idx );
    }

    // second search in cff
    if( gylphName == null ) {

      XtfTable cff = getTableMap().get( XtfReader.CFF );
      if( cff instanceof OtfTableCFF ) {
        OtfTableCFF cfftab = (OtfTableCFF) cff;
        // TODO mgn fontnumber 0
        gylphName = cfftab.mapGlyphPosToGlyphName( idx, 0 );
      }
    }

    // else
    if( gylphName == null ) {
      gylphName = "???";
    }
    return gylphName;
  }

  public int getInitOrder() {

    return 0;
  }

  public XtfTableMap getTableMap() {

    return tablemap;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.font.format.xtf.tables.XtfTable#init()
   */
  public void init() throws IOException {

    // do nothing
  }

  /**
   * Write the tag an the id attribute for the table element.
   *
   * @param writer The xml stream writer.
   * @throws IOException if an IO-error occurred.
   */
  protected void writeStartElement( XMLStreamWriter writer )
      throws IOException {

    writer.writeStartElement( getShortcut() );
    writer.writeAttribute( "id", XtfReader.convertIntToHexString( getType() ) );

  }
}
