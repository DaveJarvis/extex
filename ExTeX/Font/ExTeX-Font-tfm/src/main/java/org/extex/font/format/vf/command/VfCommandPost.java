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

package org.extex.font.format.vf.command;

import org.extex.font.exception.FontException;
import org.extex.framework.i18n.Localizer;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;

/**
 * VfCommand: post
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class VfCommandPost extends VfCommand {

  /**
   * Create e new object.
   *
   * @param localizer TODO
   * @param rar       the input
   * @param ccode     the command code
   * @throws FontException if a font error occurred.
   */
  public VfCommandPost( Localizer localizer, RandomAccessR rar,
                        final int ccode )
      throws FontException {

    super( localizer, ccode );

    if( ccode != POST ) {
      throw new FontException( getLocalizer().format( "VF.WrongCode",
                                                      String.valueOf( ccode ) ) );
    }
  }

  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "post" );
    writer.writeAttribute( "opcode", getCommandCode() );
    writer.writeEndElement();
  }
}
