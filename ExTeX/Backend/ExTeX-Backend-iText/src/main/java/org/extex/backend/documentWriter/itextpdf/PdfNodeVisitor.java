/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.itextpdf;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.font.BackendFontManager;
import org.extex.framework.i18n.Localizer;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;
import org.extex.util.Unit;

import java.awt.*;
import java.util.logging.Logger;

/**
 * PDF NodeVisitor.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class PdfNodeVisitor implements NodeVisitor<Object, Object> {

  /**
   * The line width.
   */
  private static final float LINEWIDTH = 0.1f;

  /**
   * The logger.
   */
  private final Logger logger;

  /**
   * The base font.
   */
  private BaseFont bf;

  /**
   * The direct content of the pdf page.
   */
  private final PdfContentByte cb;

  /**
   * current x position.
   */
  private final Dimen currentX = new Dimen();

  /**
   * current y position.
   */
  private final Dimen currentY = new Dimen();

  /**
   * The pdf document.
   */
  private final Document document;

  /**
   * The backend font manager.
   */
  private final BackendFontManager manager;

  /**
   * the color from the character before.
   */
  private org.extex.color.Color oldcolor = null;

  /**
   * The page size.
   */
  private Rectangle pageSize;

  /**
   * The pdf writer.
   */
  private final PdfWriter writer;

  /**
   * Creates a new object.
   *
   * @param document  The pdf document.
   * @param writer    The pdf writer.
   * @param manager   The backend font manager
   * @param logger    The logger.
   * @param localizer The localizer
   */
  public PdfNodeVisitor( Document document, PdfWriter writer,
                         BackendFontManager manager, Logger logger,
                         Localizer localizer ) {

    this.document = document;
    this.writer = writer;
    this.manager = manager;
    this.logger = logger;
    this.localizer = localizer;
    cb = writer.getDirectContent();
  }

  /**
   * The localizer.
   */
  private final Localizer localizer;

  /**
   * Draw a box around the node (only for test).
   *
   * @param node the node
   * @param fill fill the box.
   */
  private void drawNode( Node node ) {

    Color line = Color.GREEN;
    if( node instanceof VerticalListNode ) {
      line = Color.RED;
    }
    else if( node instanceof HorizontalListNode ) {
      line = Color.YELLOW;
    }
    cb.setColorStroke( line );
    cb.setLineWidth( LINEWIDTH );
    float cx = Unit.getDimenAsBP( currentX );
    float cy = Unit.getDimenAsBP( currentY );
    float w = Unit.getDimenAsBP( node.getWidth() );
    float h = Unit.getDimenAsBP( node.getHeight() );
    float d = Unit.getDimenAsBP( node.getDepth() );

    cb.moveTo( cx, pageSize.height() - cy );
    cb.lineTo( cx + w, pageSize.height() - cy );
    cb.moveTo( cx, pageSize.height() - cy );
    cb.lineTo( cx, pageSize.height() - cy + h );
    cb.moveTo( cx + w, pageSize.height() - cy );
    cb.lineTo( cx + w, pageSize.height() - cy + h );
    cb.moveTo( cx + w, pageSize.height() - cy + h );
    cb.lineTo( cx, pageSize.height() - cy + h );
    cb.stroke();
    if( node.getDepth().getValue() != 0 ) {
      cb.moveTo( cx, pageSize.height() - cy );
      cb.lineTo( cx, pageSize.height() - cy - d );
      cb.moveTo( cx, pageSize.height() - cy - d );
      cb.lineTo( cx + w, pageSize.height() - cy - d );
      cb.moveTo( cx + w, pageSize.height() - cy - d );
      cb.lineTo( cx + w, pageSize.height() - cy );
      cb.stroke();
    }
  }

  /**
   * Set the page size.
   *
   * @param pageSize The size of the page.
   */
  public void setPageSize( Rectangle pageSize ) {

    this.pageSize = pageSize;

  }

  /**
   * Set the x position.
   *
   * @param x The x value.
   */
  public void setX( FixedDimen x ) {

    currentX.set( x );

  }

  /**
   * Set the y position.
   *
   * @param y The y value.
   */
  public void setY( FixedDimen y ) {

    currentY.set( y );

  }

  /**
   * java.lang.Object)
   */
  public Object visitAdjust( AdjustNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitAdjust unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitAfterMath( AfterMathNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitAfterMath unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitAlignedLeaders( AlignedLeadersNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitAlignedLeaders unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitBeforeMath( BeforeMathNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitBeforeMath unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitCenteredLeaders( CenteredLeadersNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitCenteredLeaders unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitChar( CharNode node, Object value )
      throws GeneralException {

    try {

      drawNode( node );

      UnicodeChar uc = node.getCharacter();
      Font font = node.getTypesettingContext().getFont();
      org.extex.color.Color newcolor =
          node.getTypesettingContext().getColor();

      boolean aviable = manager.recognize( font.getFontKey(), uc );
      if( !aviable ) {
        logger.severe( localizer.format( "Pdf.incompatiblefont", font
            .getFontKey(), uc.toString() ) );
      }
      else {

        bf = PdfFontFactory.getFont( manager.getRecognizedFont(), uc );

        // the same color?
        if( !newcolor.equals( oldcolor ) ) {
          // newcolor.visit(colorVisitor, contentStream);
          oldcolor = newcolor;
        }
        cb.beginText();
        cb.setFontAndSize( bf, (float) Unit.getDimenAsPT( font
                                                              .getActualSize() ) );
        cb.setTextMatrix( Unit.getDimenAsBP( currentX ), pageSize.height()
            - Unit.getDimenAsBP( currentY ) );
        cb.showText( uc.toString() );
        cb.endText();
      }
      currentX.add( node.getWidth() );
    } catch( Exception e ) {
      throw new GeneralException( e.getMessage() );
    }
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitDiscretionary( DiscretionaryNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitDiscretionary unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitExpandedLeaders( ExpandedLeadersNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitExpandedLeaders unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitGlue( GlueNode node, Object value )
      throws GeneralException {

    currentX.add( node.getWidth() );
    currentY.add( node.getHeight() );
    currentY.add( node.getDepth() );
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitHorizontalList( HorizontalListNode node, Object value )
      throws GeneralException {

    Dimen saveX = new Dimen( currentX );
    Dimen saveY = new Dimen( currentY );

    drawNode( node );

    for( Node n : node ) {
      n.visit( this, node );
    }
    currentX.set( saveX );
    currentY.set( saveY );
    currentX.add( node.getWidth() );

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitInsertion( InsertionNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitInsertion unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitKern( KernNode node, Object value )
      throws GeneralException {

    currentX.subtract( node.getWidth() );
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitLigature( LigatureNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitLigature unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitMark( MarkNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitMark unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitPenalty( PenaltyNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitPenalty unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitRule( RuleNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitRule unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitSpace( SpaceNode node, Object value )
      throws GeneralException {

    currentX.add( node.getWidth() );
    currentY.add( node.getHeight() );
    currentY.add( node.getDepth() );
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitVerticalList( VerticalListNode node, Object value )
      throws GeneralException {

    Dimen saveX = new Dimen( currentX );
    Dimen saveY = new Dimen( currentY );

    drawNode( node );

    for( Node n : node ) {
      n.visit( this, node );
    }
    currentX.set( saveX );
    currentY.set( saveY );
    currentY.add( node.getDepth() );
    currentY.add( node.getHeight() );

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitVirtualChar( VirtualCharNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitVirtualChar unimplemented
    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitWhatsIt( WhatsItNode node, Object value )
      throws GeneralException {

    // TODO mgn: visitWhatsIt unimplemented
    return null;
  }

}
