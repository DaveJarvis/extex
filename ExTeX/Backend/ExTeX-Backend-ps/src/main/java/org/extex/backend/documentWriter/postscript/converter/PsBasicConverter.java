/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.postscript.converter;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.exception.DocumentWriterIOException;
import org.extex.backend.documentWriter.postscript.util.FontManager;
import org.extex.backend.documentWriter.postscript.util.PsUnit;
import org.extex.color.Color;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.color.model.GrayscaleColor;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.font.BackendCharacter;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;
import org.extex.typesetter.type.page.Page;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * This class provides a converter to PostScript code.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PsBasicConverter extends AbstractConverter
    implements
    NodeVisitor<Object, PrintStream>,
    ColorAware {

  /**
   * This inner class is used to keep track of characters which are delayed
   * for output.
   *
   * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
   */
  private class Buffer {

    /**
     * The field {@code charBuffer} contains the dynamic text.
     */
    private final StringBuilder text = new StringBuilder();

    /**
     * The field {@code currX} contains the x coordinate for the first
     * character.
     */
    private final Dimen currX = new Dimen();

    /**
     * The field {@code currY} contains the y coordinate for the first
     * character.
     */
    private final Dimen currY = new Dimen( Long.MIN_VALUE );

    /**
     * The field {@code empty} contains the indicator that the text is
     * empty.
     */
    private boolean empty = true;

    /**
     * The field {@code shift} contains the indicator that the first
     * character is not on the same y coordinate as the previous one.
     */
    private boolean shift = false;


    public Buffer() {

    }

    /**
     * Add a single character to the output.
     *
     * @param c  the character to add
     * @param xx the x position
     * @param yy the y position
     */
    public void add( char c, Dimen xx, Dimen yy ) {

      if( empty ) {
        empty = false;
        shift = currY.ne( yy );
        this.currX.set( xx );
        this.currY.set( yy );
      }
      switch( c ) {
        case '\\':
        case '(':
        case ')':
          text.append( '\\' );
          break;
        default:
          if( c < ' ' || c >= 127 ) {
            text.append( '\\' );
            text.append( Integer.toOctalString( c ) );
            return;
          }
          // nothing to do
      }
      text.append( c );
    }

    /**
     * Ship the collected characters out.
     *
     * @param out the target string text
     */
    public void clear( PrintStream out ) {

      if( empty ) {
        return;
      }
      if( shift ) {
        ps.putText( out, text, x, y );
      }
      else {
        ps.putText( out, text, x );
      }

      text.delete( 0, text.length() );
      empty = true;
    }

    /**
     * Reset the stored vertical position.
     */
    public void reset() {

      currY.set( Long.MIN_VALUE );
    }
  }

  /**
   * The field {@code text} contains the character text.
   */
  private final Buffer buffer = new Buffer();

  /**
   * The field {@code cc} contains the color converter.
   */
  private ColorConverter cc = null;

  /**
   * The field {@code currentColor} contains the current color to keep
   * track of the color changing commands.
   */
  private Color currentColor = null;

  /**
   * The field {@code ps} contains the library manager for Postscript code.
   */
  private final Ps ps = new Ps();

  /**
   * The field {@code x} contains the current x position.
   */
  private final Dimen x = new Dimen();

  /**
   * The field {@code y} contains the current y position.
   */
  private final Dimen y = new Dimen();


  public PsBasicConverter() {

  }

  /**
   * org.extex.color.ColorConverter)
   */
  public void setColorConverter( ColorConverter converter ) {

    cc = converter;
  }

  /**
   * Switch to another color.
   *
   * @param color the color to switch to
   * @param out   the target text
   */
  private void switchColors( Color color, PrintStream out ) {

    if( color instanceof GrayscaleColor ) {
      ps.setgray( out, (GrayscaleColor) color );
    }
    else {
      ps.setrgb( out, cc.toRgb( color ) );
    }
    currentColor = color;
  }

  /**
   * org.extex.typesetter.type.page.Page)
   */
  public byte[] toPostScript( Page page ) throws DocumentWriterException {

    x.set( page.getMediaHOffset() );
    y.set( page.getMediaHeight() );
    y.subtract( page.getMediaVOffset() );

    buffer.reset();
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream( outStream, true );
    out.println( "TeXDict begin" );

    try {
      page.getNodes().visit( this, out );
    } catch( GeneralException e ) {
      Throwable cause = e.getCause();
      if( cause instanceof IOException ) {
        throw new DocumentWriterIOException( cause );
      }
      throw new DocumentWriterException( e );
    }

    ps.eop( out );
    out.println( "end" );
    return outStream.toByteArray();
  }

  /**
   * org.extex.typesetter.type.node.AdjustNode, java.lang.Object)
   */
  public Object visitAdjust( AdjustNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.AfterMathNode, java.lang.Object)
   */
  public Object visitAfterMath( AfterMathNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.AlignedLeadersNode, java.lang.Object)
   */
  public Object visitAlignedLeaders( AlignedLeadersNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.BeforeMathNode, java.lang.Object)
   */
  public Object visitBeforeMath( BeforeMathNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.CenteredLeadersNode,
   * java.lang.Object)
   */
  public Object visitCenteredLeaders( CenteredLeadersNode node,
                                      PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.CharNode, java.lang.Object)
   */
  public Object visitChar( CharNode node, PrintStream out )
      throws GeneralException {

    TypesettingContext tc = node.getTypesettingContext();
    UnicodeChar c = node.getCharacter();
    Font font = tc.getFont();

    FontManager fm = getFontManager();
    String fnt = fm.add( font, c );
    if( fnt != null ) {
      buffer.clear( out );
      out.append( fnt );
    }

    Color color = tc.getColor();

    if( color != currentColor ) {
      buffer.clear( out );
      switchColors( color, out );
    }

    BackendCharacter cid = fm.getRecognizedCharId();
    if( cid != null ) {
      buffer.add( (char) cid.getId(), x, y );
    }
    else {
      buffer.clear( out );
      out.append( ' ' );
      PsUnit.toPoint( font.getWidth( c ).getLength(), out, false );
      out.append( " 0 rmoveto " );
    }
    return null;
  }

  /**
   * org.extex.typesetter.type.node.DiscretionaryNode, java.lang.Object)
   */
  public Object visitDiscretionary( DiscretionaryNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.ExpandedLeadersNode,
   * java.lang.Object)
   */
  public Object visitExpandedLeaders( ExpandedLeadersNode node,
                                      PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.GlueNode, java.lang.Object)
   */
  public Object visitGlue( GlueNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.HorizontalListNode, java.lang.Object)
   */
  public Object visitHorizontalList( HorizontalListNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );

    Dimen saveX = new Dimen( x );
    Dimen saveY = new Dimen( y );
    x.add( node.getMove() );
    y.add( node.getShift() );

    for( Node n : node ) {
      n.visit( this, out );
      x.add( n.getWidth() );
    }

    x.set( saveX );
    y.set( saveY );

    return null;
  }

  /**
   * org.extex.typesetter.type.node.InsertionNode, java.lang.Object)
   */
  public Object visitInsertion( InsertionNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.KernNode, java.lang.Object)
   */
  public Object visitKern( KernNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.LigatureNode, java.lang.Object)
   */
  public Object visitLigature( LigatureNode node, PrintStream out )
      throws GeneralException {

    return visitChar( node, out );
  }

  /**
   * org.extex.typesetter.type.node.MarkNode, java.lang.Object)
   */
  public Object visitMark( MarkNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.PenaltyNode, java.lang.Object)
   */
  public Object visitPenalty( PenaltyNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.RuleNode, java.lang.Object)
   */
  public Object visitRule( RuleNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );

    Color color = node.getTypesettingContext().getColor();
    if( color != currentColor ) {
      switchColors( color, out );
    }

    ps.rule( out, node.getWidth(), node.getHeight(), x, y );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.SpaceNode, java.lang.Object)
   */
  public Object visitSpace( SpaceNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return null;
  }

  /**
   * org.extex.typesetter.type.node.VerticalListNode, java.lang.Object)
   */
  public Object visitVerticalList( VerticalListNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );

    Dimen saveX = new Dimen( x );
    Dimen saveY = new Dimen( y );
    x.add( node.getMove() );
    y.add( node.getShift() );

    for( Node n : node ) {
      n.visit( this, out );
      y.subtract( n.getHeight() );
      y.subtract( n.getDepth() );
    }

    x.set( saveX );
    y.set( saveY );

    return null;
  }

  /**
   * org.extex.typesetter.type.node.VirtualCharNode, java.lang.Object)
   */
  public Object visitVirtualChar( VirtualCharNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );
    return visitChar( node, out );
  }

  /**
   * org.extex.typesetter.type.node.WhatsItNode, java.lang.Object)
   */
  public Object visitWhatsIt( WhatsItNode node, PrintStream out )
      throws GeneralException {

    buffer.clear( out );

    if( node instanceof SpecialNode ) {
      treatSpecial( out, (SpecialNode) node );
    }
    return null;
  }

  /**
   * java.io.PrintStream)
   */
  @Override
  public void writeHeaders( PrintStream out ) throws IOException {

    super.writeHeaders( out );
    ps.writeDict( out );
  }

}
