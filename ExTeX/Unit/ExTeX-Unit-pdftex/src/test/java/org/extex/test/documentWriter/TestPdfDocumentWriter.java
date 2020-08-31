/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.test.documentWriter;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.pdf.api.PdftexSupport;
import org.extex.pdf.api.action.ActionSpec;
import org.extex.pdf.api.node.PdfAnnotation;
import org.extex.pdf.api.node.PdfObject;
import org.extex.pdf.api.node.PdfRefXImage;
import org.extex.pdf.api.node.PdfXForm;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;
import org.extex.typesetter.type.page.Page;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is an implementation of a document writer which can act both as sample
 * and as tool for testing.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TestPdfDocumentWriter
    implements
    DocumentWriter,
    SingleDocumentStream,
    PdftexSupport,
    Configurable {

  /**
   * The field {@code nodeVisitor} contains the node visitor instance to use
   * in the form of an anonymous inner class.
   */
  private final NodeVisitor<Object, Object> nodeVisitor =
      new NodeVisitor<Object, Object>() {

        /**
         * The field {@code vmode} contains the indicator that a vlist
         * is processed.
         */
        private boolean vmode = false;

        /**
         * Print a new line in vertical mode.
         *
         * @throws GeneralException in case of an error
         */
        private void nl() throws GeneralException {

          if( vmode ) {
            try {
              out.write( '\n' );
            } catch( IOException e ) {
              throw new GeneralException( e );
            }
          }
        }

        @Override
        public Object visitAdjust( AdjustNode node, Object oOut )
            throws GeneralException {

          write( "\n" );
          return null;
        }

        @Override
        public Object visitAfterMath( AfterMathNode node, Object oOut )
            throws GeneralException {

          if( node.getWidth().ne( Dimen.ZERO_PT ) ) {
            write( ' ' );
          }
          return null;
        }

        @Override
        public Object visitAlignedLeaders( AlignedLeadersNode node,
                                           Object oOut )
            throws GeneralException {

          write( " " );
          node.visit( this, oOut );
          node.visit( this, oOut );
          write( "  " );
          return null;
        }

        @Override
        public Object visitBeforeMath( BeforeMathNode node, Object oOut )
            throws GeneralException {

          if( node.getWidth().ne( Dimen.ZERO_PT ) ) {
            write( ' ' );
          }
          return null;
        }

        @Override
        public Object visitCenteredLeaders( CenteredLeadersNode node,
                                            Object oOut )
            throws GeneralException {

          write( "  " );
          node.visit( this, oOut );
          node.visit( this, oOut );
          write( "  " );
          return null;
        }

        @Override
        public Object visitChar( CharNode node, Object oOut )
            throws GeneralException {

          write( node.getCharacter().getCodePoint() );
          return null;
        }

        @Override
        public Object visitDiscretionary( DiscretionaryNode node,
                                          Object oOut )
            throws GeneralException {

          write( "--" );
          return null;
        }

        @Override
        public Object visitExpandedLeaders( ExpandedLeadersNode node,
                                            Object oOut )
            throws GeneralException {

          write( "  " );
          node.visit( this, oOut );
          node.visit( this, oOut );
          write( " " );
          return null;
        }

        @Override
        public Object visitGlue( GlueNode node, Object oOut )
            throws GeneralException {

          if( vmode ) {
            if( node.getHeight().ne( Dimen.ZERO_PT )
                && node.getDepth().ne( Dimen.ZERO_PT ) ) {
              write( '\n' );
            }
          }
          else {
            if( node.getWidth().ne( Dimen.ZERO_PT ) ) {
              write( ' ' );
            }
          }
          return null;
        }

        @Override
        public Object visitHorizontalList( HorizontalListNode list,
                                           Object oOut )
            throws GeneralException {

          boolean mode = vmode;
          vmode = false;
          for( int i = 0; i < list.size(); i++ ) {
            list.get( i ).visit( this, oOut );
          }
          vmode = mode;
          nl();
          return null;
        }

        @Override
        public Object visitInsertion( InsertionNode node, Object oOut )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitKern( KernNode node, Object oOut )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitLigature( LigatureNode node, Object oOut )
            throws GeneralException {

          write( node.getCharacter().getCodePoint() );
          return null;
        }

        @Override
        public Object visitMark( MarkNode node, Object oOut )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitPenalty( PenaltyNode node, Object oOut )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitRule( RuleNode node, Object oOut )
            throws GeneralException {

          write( "---" );
          return null;
        }

        @Override
        public Object visitSpace( SpaceNode node, Object oOut )
            throws GeneralException {

          write( ' ' );
          return null;
        }

        @Override
        public Object visitVerticalList( VerticalListNode list,
                                         Object oOut ) throws GeneralException {

          boolean mode = vmode;
          vmode = true;
          for( int i = 0; i < list.size(); i++ ) {
            list.get( i ).visit( this, oOut );
          }
          vmode = mode;
          nl();
          return null;
        }

        @Override
        public Object visitVirtualChar( VirtualCharNode node, Object oOut )
            throws GeneralException {

          write( node.getCharacter().getCodePoint() );
          return null;
        }

        @Override
        public Object visitWhatsIt( WhatsItNode node, Object oOut )
            throws GeneralException {

          return null;
        }

        /**
         * Write a char to out.
         *
         * @param s the char to write
         *
         * @throws GeneralException in case of an error
         */
        private void write( int s ) throws GeneralException {

          try {
            out.write( s );
          } catch( IOException e ) {
            throw new GeneralException( e );
          }
        }

        /**
         * Write a string to out.
         *
         * @param s the string to write
         *
         * @throws GeneralException in case of an error
         */
        private void write( String s ) throws GeneralException {

          try {
            out.write( s.getBytes() );
            if( vmode ) {
              out.write( '\n' );
            }
          } catch( IOException e ) {
            throw new GeneralException( e );
          }

        }
      };

  /**
   * The field {@code out} contains the output stream to use.
   */
  private OutputStream out = null;

  /**
   * The field {@code tree} contains the indicator whether to use the tree
   * representation.
   */
  private boolean tree = true;

  /**
   * Creates a new object.
   *
   * @param opts the dynamic access to the context
   */
  public TestPdfDocumentWriter( DocumentWriterOptions opts ) {

  }

  @Override
  public void close() throws IOException {

    if( out != null ) {
      out.close();
      out = null;
    }
  }

  @Override
  public void configure( Configuration config ) throws ConfigurationException {

    tree = Boolean.valueOf( config.getAttribute( "tree" ) ).booleanValue();
  }

  /**
   * java.lang.String)
   */
  @Override
  public PdfAnnotation getAnnotation( RuleNode node, String annotation )
      throws HelpingException {

    // TODO gene: getAnnotation unimplemented
    return null;
  }

  @Override
  public String getExtension() {

    return "out";
  }

  /**
   * java.lang.String)
   */
  @Override
  public PdfObject getObject( String attr, boolean isStream, String text )
      throws HelpingException {

    // TODO gene: getObject unimplemented
    return null;
  }

  /**
   * java.lang.String, org.extex.typesetter.type.NodeList)
   */
  @Override
  public PdfXForm getXForm( String attr, String resources, NodeList list )
      throws HelpingException {

    // TODO gene: getXForm unimplemented
    return null;
  }

  /**
   * org.extex.typesetter.type.node.RuleNode, java.lang.String, long,
   * boolean)
   */
  @Override
  public PdfRefXImage getXImage( String resource, RuleNode rule, String attr,
                                 long page, boolean immediate )
      throws HelpingException {

    // TODO gene: getXImage unimplemented
    return null;
  }

  /**
   * org.extex.pdf.api.action.ActionSpec)
   */
  @Override
  public void pdfcatalog( String text, ActionSpec action ) {

    // TODO gene: pdfcatalog unimplemented

  }

  @Override
  public String pdffontname( Font font ) {

    // TODO gene: pdffontname unimplemented
    return null;
  }

  @Override
  public long pdffontobjnum( Font font ) {

    // TODO gene: pdffontobjnum unimplemented
    return 0;
  }

  /**
   * java.lang.String)
   */
  @Override
  public void pdfincludechars( Font font, String text ) {

    // TODO gene: pdfincludechars unimplemented

  }

  @Override
  public void pdfinfo( String text ) {

    // TODO gene: pdfinfo unimplemented

  }

  @Override
  public long pdflastannot() {

    // TODO gene: pdflastannot unimplemented
    return 0;
  }

  @Override
  public long pdflastobj() {

    // TODO gene: pdflastobj unimplemented
    return 0;
  }

  @Override
  public long pdflastxform() {

    // TODO gene: pdflastxform unimplemented
    return 0;
  }

  @Override
  public long pdflastximage() {

    // TODO gene: pdflastximage unimplemented
    return 0;
  }

  @Override
  public void pdfnames( String text ) {

    // TODO gene: pdfnames unimplemented

  }

  /**
   * long, java.lang.String)
   */
  @Override
  public void pdfoutline( ActionSpec action, long count, String text ) {

    // TODO gene: pdfoutline unimplemented

  }

  @Override
  public void setOutputStream( OutputStream outStream ) {

    out = outStream;
  }

  /**
   * java.lang.String)
   */
  @Override
  public void setParameter( String name, String value ) {

    // not needed
  }

  /**
   * Setter for tree.
   *
   * @param tree the tree to set
   */
  public void setTree( boolean tree ) {

    this.tree = tree;
  }

  @Override
  public int shipout( Page page ) throws DocumentWriterException {

    NodeList nodes = page.getNodes();
    try {
      if( tree ) {
        StringBuilder sb = new StringBuilder();
        nodes.toString( sb, "\n", Integer.MAX_VALUE, Integer.MAX_VALUE );
        out.write( sb.toString().getBytes() );
        out.write( '\n' );
      }
      else {
        nodes.visit( nodeVisitor, out );
        out.write( '\n' );
      }
    } catch( IOException e ) {
      throw new DocumentWriterException( e );
    } catch( GeneralException e ) {
      Throwable ex = e.getCause();
      throw (ex instanceof DocumentWriterException
          ? (DocumentWriterException) ex
          : new DocumentWriterException( e.getLocalizedMessage() ));
    }
    return 1;
  }

}
