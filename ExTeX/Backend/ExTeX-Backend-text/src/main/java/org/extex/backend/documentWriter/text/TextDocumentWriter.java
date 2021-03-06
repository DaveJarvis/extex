/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.text;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterClosedChannelException;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.exception.DocumentWriterIOException;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configuration;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;
import org.extex.typesetter.type.page.Page;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is a text dummy implementation of a document writer (very simple).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TextDocumentWriter
    implements
    DocumentWriter,
    SingleDocumentStream,
    NodeVisitor<Object, Object> {

  /**
   * The field {@code out} ...
   */
  private OutputStream out = null;

  /**
   * The field {@code shippedPages} ...
   */
  private int shippedPages = 0;

  /**
   * Creates a new object.
   *
   * @param cfg     the configuration
   * @param options the options
   */
  public TextDocumentWriter( Configuration cfg,
                             DocumentWriterOptions options ) {

  }

  /**
   * @see org.extex.backend.documentWriter.DocumentWriter#close()
   */
  public void close() throws DocumentWriterIOException {

    if( out != null ) {
      try {
        out.close();
      } catch( IOException e ) {
        throw new DocumentWriterIOException( e );
      }
      out = null;
    }
    else {
      throw new DocumentWriterClosedChannelException( "clodes channel" );
    }
  }

  /**
   * @see org.extex.backend.documentWriter.DocumentWriter#getExtension()
   */
  public String getExtension() {

    return "txt";
  }

  /**
   * process Node.
   *
   * @param nodes the node list
   * @throws DocumentWriterException if an error occurred.
   */
  private void processNodes( NodeList nodes ) throws DocumentWriterException {

    showNode( nodes );
    for( Node n : nodes ) {
      if( n instanceof NodeList ) {
        processNodes( (NodeList) n );
      }
      else {
        showNode( n );
      }
    }
  }

  /**
   * @see org.extex.backend.documentWriter.SingleDocumentStream#setOutputStream(java.io.OutputStream)
   */
  public void setOutputStream( OutputStream outStream ) {

    out = outStream;
  }

  /**
   * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(java.lang.String,
   * java.lang.String)
   */
  public void setParameter( String name, String value ) {

    // not supported
  }

  /**
   * @see org.extex.backend.documentWriter.DocumentWriter#shipout(org.extex.typesetter.type.page.Page)
   */
  public int shipout( Page page ) throws DocumentWriterException {

    NodeList nodes = page.getNodes();
    try {
      processNodes( nodes );
      out.write( '\n' );
      out.flush();
    } catch( IOException e ) {
      throw new DocumentWriterIOException( e );
    }
    shippedPages++;
    return 1;
  }

  /**
   * show node.
   *
   * @param node the node
   * @throws DocumentWriterException if an error occurred.
   */
  private void showNode( Node node ) throws DocumentWriterException {

    try {
      Object o = node.visit( this, node );
      if( o != null ) {
        if( o instanceof String ) {
          out.write( ((String) o).getBytes() );
        }
      }
    } catch( GeneralException e ) {
      e.printStackTrace();

    } catch( IOException e ) {
      throw new DocumentWriterIOException( e );
    }
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(AdjustNode,
   * java.lang.Object)
   */
  public Object visitAdjust( AdjustNode value, Object value2 ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(AfterMathNode,
   * java.lang.Object)
   */
  public Object visitAfterMath( AfterMathNode value, Object value2 ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(AlignedLeadersNode,
   * java.lang.Object)
   */
  public Object visitAlignedLeaders( AlignedLeadersNode value, Object value2 ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(BeforeMathNode,
   * java.lang.Object)
   */
  public Object visitBeforeMath( BeforeMathNode node, Object value2 ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(CenteredLeadersNode,
   * java.lang.Object)
   */
  public Object visitCenteredLeaders( CenteredLeadersNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitChar(CharNode,
   * java.lang.Object)
   */
  public Object visitChar( CharNode node, Object value ) {

    return node.toString();
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(DiscretionaryNode,
   * java.lang.Object)
   */
  public Object visitDiscretionary( DiscretionaryNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(ExpandedLeadersNode,
   * java.lang.Object)
   */
  public Object visitExpandedLeaders( ExpandedLeadersNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitGlue(GlueNode,
   * java.lang.Object)
   */
  public Object visitGlue( GlueNode node, Object value ) {

    return " ";
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(HorizontalListNode,
   * java.lang.Object)
   */
  public Object visitHorizontalList( HorizontalListNode node, Object value ) {

    return "\n";
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(InsertionNode,
   * java.lang.Object)
   */
  public Object visitInsertion( InsertionNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitKern(KernNode,
   * java.lang.Object)
   */
  public Object visitKern( KernNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitLigature(LigatureNode,
   * java.lang.Object)
   */
  public Object visitLigature( LigatureNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitMark(MarkNode,
   * java.lang.Object)
   */
  public Object visitMark( MarkNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(PenaltyNode,
   * java.lang.Object)
   */
  public Object visitPenalty( PenaltyNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitRule(RuleNode,
   * java.lang.Object)
   */
  public Object visitRule( RuleNode node, Object value ) {

    return "\n" +
        "---------------------------------------------------------------------";
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitSpace(SpaceNode,
   * java.lang.Object)
   */
  public Object visitSpace( SpaceNode node, Object value ) {

    return " ";
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(VerticalListNode,
   * java.lang.Object)
   */
  public Object visitVerticalList( VerticalListNode node, Object value ) {

    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(org.extex.typesetter.type.node.VirtualCharNode,
   * java.lang.Object)
   */
  public Object visitVirtualChar( VirtualCharNode node, Object value )
      throws GeneralException {

    // TODO visitVirtualChar unimplemented
    return null;
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(WhatsItNode,
   * java.lang.Object)
   */
  public Object visitWhatsIt( WhatsItNode nde, Object value ) {

    return null;
  }
}
