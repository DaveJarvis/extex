/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.cff;

import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;
import java.util.List;

/**
 * T2: return: return (11).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T2Return extends T2Subroutine {

  /**
   * Create a new object.
   *
   * @param stack the stack
   * @param ch    The char string.
   * @throws IOException if an IO-error occurs.
   */
  public T2Return( List<T2CharString> stack, CharString ch )
      throws IOException {

    super( stack, new short[]{T2CALLGSUBR}, ch );

  }

  @Override
  public int getID() {

    return TYPE_RETURN;
  }

  @Override
  public String getName() {

    return "return";
  }

  @Override
  public Object getValue() {

    return null;
  }

  @Override
  public String toText() {

    return getName();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( getName() );
    writer.writeEndElement();
  }

}
