/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.dvi;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.backend.documentWriter.dvi.DviDocumentWriter;
import org.extex.backend.documentWriter.dvi.PanicException;
import org.extex.backend.documentWriter.exception.NoOutputStreamException;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.Glue;
import org.extex.engine.typesetter.page.PageImpl;
import org.extex.framework.configuration.Configuration;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for class {@code DviDocumentWriter}.
 *
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 */
public class DviDocumentWriterTest {

  /**
   * TODO gene: missing JavaDoc.
   */
  private class MockDocumentWriterOptions implements DocumentWriterOptions {

    /**
     * The field {@code magnification} contains the magnification.
     */
    private long magnification = 1000;


    public MockDocumentWriterOptions() {

    }

    public FixedCount getCountOption( String count ) {

      return new MockFixedCount( 0 );
    }

    public FixedDimen getDimenOption( String dimen ) {

      return null;
    }

    public long getMagnification() {

      return magnification;
    }

    public String getTokensOption( String name ) {

      return null;
    }

    /**
     * Setter for the magnification.
     *
     * @param theMagnification the magnification
     */
    public void setMagnification( long theMagnification ) {

      magnification = theMagnification;
    }
  }

  /**
   * TODO missing JavaDoc.
   */
  private class MockFixedCount implements FixedCount {

    /**
     * The field {@code value} contains the value.
     */
    private final long value;

    /**
     * Creates a new object.
     *
     * @param theValue the value in scaled points
     */
    public MockFixedCount( long theValue ) {

      value = theValue;
    }

    public boolean eq( FixedCount count ) {

      // TODO eq unimplemented
      return false;
    }

    public boolean ge( FixedCount count ) {

      // TODO ge unimplemented
      return false;
    }

    /**
     * Getter for the value
     *
     * @return the value
     * @see org.extex.core.count.FixedCount#getValue()
     */
    public long getValue() {

      return value;
    }

    public boolean gt( FixedCount count ) {

      // TODO gt unimplemented
      return false;
    }

    public boolean le( FixedCount count ) {

      // TODO le unimplemented
      return false;
    }

    public boolean lt( FixedCount count ) {

      // TODO lt unimplemented
      return false;
    }

    public boolean ne( FixedCount count ) {

      // TODO ne unimplemented
      return false;
    }

    public void toString( StringBuilder buffer ) {

      buffer.append( toString() );
    }
  }

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( DviDocumentWriterTest.class );
  }

  /**
   * The field {@code documentWriter} contains the document writer.
   */
  private DocumentWriter documentWriter = null;

  /**
   * The field {@code nodeList} contains the node list.
   */
  private NodeList nodeList = null;

  /**
   * The field {@code documentWriterOptions} contains the document writer
   * options.
   */
  private MockDocumentWriterOptions documentWriterOptions = null;

  /**
   * The field {@code configuration} contains the configuration.
   */
  private final Configuration configuration = null;

  /**
   * The field {@code outputStream} contains the output stream.
   */
  private OutputStream outputStream = null;

  /**
   * Creates a new {@code DviDocumentWriterTest} instance.
   */
  public DviDocumentWriterTest() {

  }

  /**
   * The if DviDocumentWriter throws the exception, if the node is added to
   * the NodeList.
   *
   * @param node      a {@code Node} value
   * @param exception a {@code Class} value
   * @throws Exception if an error occurs
   */
  private void checkException( Node node, Class<?> exception )
      throws Exception {

    boolean gotException = false;

    nodeList.add( node );

    try {
      FixedCount[] pageNo = null;
      documentWriter.shipout( new PageImpl( nodeList, pageNo ) );
    } catch( Exception e ) {
      if( exception.isInstance( e ) ) {
        gotException = true;
      }
      else {
        throw e;
      }
    }
    assertTrue( gotException );
  }

  /**
   * Check the specified magnification.
   *
   * @param magnification for check
   * @throws Exception if an error occurs
   */
  private void checkMagnification( long magnification ) throws Exception {

    documentWriterOptions.setMagnification( magnification );
    documentWriter =
        new DviDocumentWriter( configuration, documentWriterOptions );
    ((SingleDocumentStream) documentWriter).setOutputStream( outputStream );
    FixedCount[] pageNo = null;
    documentWriter.shipout( new PageImpl( nodeList, pageNo ) );
  }

  /**
   * Setup the fixtures. All class variables get threr value here. This is not
   * done in the constructor so the variables get new values for each test.
   *
   * @throws Exception if an error occurs
   */
  @Before
  public void setUp() throws Exception {

    // TODO: do not use null for configuration (TE)

    documentWriterOptions = new MockDocumentWriterOptions();
    documentWriter =
        new DviDocumentWriter( configuration, documentWriterOptions );
    nodeList = new VerticalListNode();
    outputStream = new ByteArrayOutputStream();
    ((SingleDocumentStream) documentWriter).setOutputStream( outputStream );
  }

  /**
   * Test if the DviDocumentWriter throws new Exception if the node list is
   * empty.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void testEmptyList() throws Exception {

    FixedCount[] pageNo = null;
    documentWriter.shipout( new PageImpl( nodeList, pageNo ) );
  }

  /**
   * Test if a insertion node throws a panic Exception.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void testInsertionNode() throws Exception {

    checkException( new InsertionNode( 42, null ), PanicException.class );
  }

  /**
   * Test magnifications in the document writer options.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void testMagnification() throws Exception {

    boolean gotRangeException = false;

    checkMagnification( -1 ); // TODO
    checkMagnification( 10 );
    checkMagnification( 100 );
    checkMagnification( 1000 );
    checkMagnification( (2L << 30) - 1 ); // test 2^30-1

    try {
      checkMagnification( 2L << 30 ); // test 2^30
    } catch( GeneralException e ) {
      gotRangeException = true;
    }
    assertTrue( gotRangeException );
  }

  /**
   * Test if a mark node throws a panic Exception.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void testMarkNode() throws Exception {

    checkException( new MarkNode( Tokens.EMPTY, "0" ), PanicException.class );
  }

  /**
   * Test if {@link org.extex.backend.documentWriter.dvi.DviDocumentWriter
   * DviDocumentWriter} throws a
   * {@link org.extex.backend.documentWriter.exception.NoOutputStreamException
   * NoOutputStreamException}, if there is no OutputStream set before
   * {@link org.extex.backend.documentWriter.DocumentWriter#shipout(org.extex.typesetter.type.page.Page)
   * shipout()}.
   *
   * @throws IOException if an error occurs
   */
  @Test
  public void testNoOutputStream() throws IOException {

    documentWriter =
        new DviDocumentWriter( configuration, documentWriterOptions );
    try {
      FixedCount[] pageNo = null;
      documentWriter.shipout( new PageImpl( nodeList, pageNo ) );
    } catch( NoOutputStreamException e ) {
      assertTrue( true );
    } catch( GeneralException e ) {
      assertTrue( false );
    }
  }

  /**
   * Test valid nodes.
   *
   * @throws Exception if an error occurs
   */
  @Test
  public void testValidNodes() throws Exception {

    // TODO: nodeList.add(new CharNode()); (TE)
    nodeList.add( new ExplicitKernNode( new Dimen( 12346 ), true ) );
    nodeList.add( new GlueNode( new Glue( 1234 ), true ) );
    // TODO: nodeList.add(new LigatureNode()); (TE)
    // TODO: nodeList.add(new SpecialNode("Test")); (TE)
    // nodeList.add(new WhatsItNode("Test"));

    FixedCount[] pageNo = null;
    documentWriter.shipout( new PageImpl( nodeList, pageNo ) );
  }

}
