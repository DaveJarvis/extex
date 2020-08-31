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

package org.extex.backend.documentWriter.postscript;

import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.postscript.converter.PsConverter;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.page.Page;

import java.io.IOException;
import java.io.PrintStream;

/**
 * This document writer produces Encapsulated Postscript documents.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class EpsWriter extends AbstractPostscriptWriter {

  /**
   * Creates a new object.
   *
   * @param options the options for the document writer
   */
  public EpsWriter( DocumentWriterOptions options ) {

    super( "eps" );
  }

  /**
   * This method is invoked upon the end of the processing. It does simply
   * nothing for this class since all pages have already been written out.
   *
   * @see org.extex.backend.documentWriter.DocumentWriter#close()
   */
  public void close() {

    // nothing to do
  }

  /**
   * This is the entry point for the document writer. Here it receives a
   * complete node list to be sent to the output writer. It can be assumed
   * that all values for width, height, and depth of the node lists are
   * properly filled. Thus all information should be present to place the ink
   * on the paper.
   *
   * @param page the page to send
   * @return returns the number of pages shipped
   * @throws GeneralException in case of a general exception<br>
   *                          especially<br>
   *                          DocumentWriterException in case of an error
   * @throws IOException      in case of an IO exception
   * @see org.extex.backend.documentWriter.DocumentWriter#shipout(
   *org.extex.typesetter.type.page.Page)
   */
  public int shipout( Page page ) throws GeneralException, IOException {

    if( page == null ) {
      return 0;
    }

    PsConverter converter = getConverter();
    PrintStream out = newOutputStream( "eps" );

    startFile( out, "PS-Adobe-2.0 EPSF-2.0" );
    NodeList nodes = page.getNodes();
    Dimen d = new Dimen( nodes.getHeight() );
    d.add( nodes.getDepth() );
    writeBoundingBox( out, nodes.getWidth(), d );
    writeHighResBoundingBox( out, nodes );

    byte[] bytes = converter.toPostScript( page );
    writeFonts( out, getFontManager() );
    writeDsc( out, "EndComments" );
    out.println( "/TeXDict 300 dict def" );
    getFontManager().write( out );
    getFontManager().reset();
    converter.writeHeaders( out );
    out.write( bytes );
    writeDsc( out, "EOF" );
    out.close();
    out = null;
    return 1;
  }

}
