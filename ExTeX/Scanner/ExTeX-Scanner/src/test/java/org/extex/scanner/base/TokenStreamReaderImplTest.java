/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.base;

import org.extex.scanner.api.TokenStream;
import org.junit.runner.JUnitCore;

import java.io.IOException;
import java.io.StringReader;

/**
 * Test cases for the string implementation of a token stream.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TokenStreamReaderImplTest extends TokenStreamStringImplTest {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( TokenStreamReaderImplTest.class );
  }


  public TokenStreamReaderImplTest() {

  }

  /**
   * Create a stream of tokens fed from a string.
   *
   * @param line the input string
   * @return the new token stream
   * @throws IOException in case of an error
   */
  @Override
  protected TokenStream makeStream( String line ) throws IOException {

    return new TokenStreamImpl( null, null, new StringReader( line ),
                                Boolean.FALSE, "test" );
  }

}
