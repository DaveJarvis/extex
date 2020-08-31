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

package org.extex.backend.documentWriter.dvix;

import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.dviware.Dvi;
import org.extex.dviware.dvitype.DviDisassemble;
import org.extex.engine.typesetter.page.PageImpl;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.page.Page;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * This is a test suite for the dvi document writer.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DviDocumentWriterTest {

  /**
   * The field {@code OPTIONS} contains the dummy options.
   */
  private static final DocumentWriterOptions OPTIONS =
      new DocumentWriterOptions() {

        /**
         *      java.lang.String)
         */
        public FixedCount getCountOption( String name ) {

          if( "day".equals( name ) ) {
            return new Count( 13 );
          }
          else if( "month".equals( name ) ) {
            return new Count( 6 );
          }
          else if( "year".equals( name ) ) {
            return new Count( 58 );
          }
          return Count.ZERO;
        }

        /**
         *      java.lang.String)
         */
        public FixedDimen getDimenOption( String name ) {

          return null;
        }

        public long getMagnification() {

          return 1000;
        }

        /**
         *      java.lang.String)
         */
        public String getTokensOption( String name ) {

          return null;
        }

      };

  /**
   *
   */
  @Test
  public final void testgetExtension() {

    assertEquals( "dvi", new DviDocumentWriter( null ).getExtension() );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testShipout1() throws Exception {

    DviDocumentWriter writer = new DviDocumentWriter( null );
    assertEquals( 0, writer.shipout( null ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testShipout2() throws Exception {

    DviDocumentWriter writer = new DviDocumentWriter( OPTIONS );
    writer.setOutputStream( new ByteArrayOutputStream() );
    Page page =
        new PageImpl( new VerticalListNode(), new FixedCount[]{
            Count.ONE, Count.ONE, Count.ONE, Count.ONE, Count.ONE,
            Count.ONE, Count.ONE, Count.ONE, Count.ONE,
            Count.ONE} );
    assertEquals( 1, writer.shipout( page ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testClose1() throws Exception {

    DviDocumentWriter writer = new DviDocumentWriter( OPTIONS );
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    writer.setOutputStream( out );
    Page page =
        new PageImpl( new VerticalListNode(), new FixedCount[]{
            Count.ONE, Count.ONE, Count.ONE, Count.ONE, Count.ONE,
            Count.ONE, Count.ONE, Count.ONE, Count.ONE,
            Count.ONE} );
    writer.shipout( page );
    writer.close();
    OutputStream result = new ByteArrayOutputStream();
    new Dvi( new ByteArrayInputStream( out.toByteArray() ) )
        .parse( new DviDisassemble( new PrintStream( result ) ) );
    assertEquals(
        "0000\tpre 2 25400000 473628672 1000 \" ExTeX output 58.06.13:0\"\n"
            + "0027\tbop 1 1 1 1 1 1 1 1 1 1 0xffffffff\n"
            + "0054\teop\n"
            + "0055\tpost 0x27 25400000 473628672 1000 0 0 1 1\n"
            + "0072\tpost_post 0x55 2\n" + "", result.toString()
                                                     .replaceAll( "\r", "" ) );
  }

  // TODO implement more test cases
}
