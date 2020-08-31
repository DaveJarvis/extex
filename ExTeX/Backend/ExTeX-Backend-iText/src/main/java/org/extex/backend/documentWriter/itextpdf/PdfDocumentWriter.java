/*
 * Copyright (C) 2007-2011 The ExTeX Group
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
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterClosedChannelException;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.font.BackendFontManager;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontAware;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.page.Page;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Implementation of a pdf document writer with iText.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class PdfDocumentWriter
    implements
    DocumentWriter,
    SingleDocumentStream,
    Configurable,
    LogEnabled,
    FontAware/* ,PdftexSupport */ {

  /**
   * The field {@code localizer} contains the localizer. It is initiated with
   * a localizer for the name of this class.
   */
  private final Localizer localizer = LocalizerFactory
      .getLocalizer( PdfDocumentWriter.class );

  /**
   * The {@link CoreFontFactory}.
   */
  private CoreFontFactory corefactory;

  /**
   * The {@link BackendFontManager}.
   */
  private BackendFontManager manager;

  /**
   * The output stream.
   */
  private OutputStream out = null;

  /**
   * Map for the parameters.
   */
  private final Map<String, String> param = new HashMap<String, String>();

  /**
   * The pdf document.
   */
  private Document document = null;

  /**
   * The node visitor.
   */
  private PdfNodeVisitor nodevisitor;

  /**
   * the number of page which are shipped out.
   */
  private int shippedPages = 0;

  /**
   * The pdf writer.
   */
  private PdfWriter writer;

  /**
   * The document writer options.
   */
  private final DocumentWriterOptions options;

  /**
   * The logger.
   */
  private Logger logger;

  /**
   * Creates a new object.
   *
   * @param config  The configurations.
   * @param options The options.
   */
  public PdfDocumentWriter( Configuration config,
                            DocumentWriterOptions options ) {

    this.options = options;
    configure( config );
  }

  @Override
  public void close() throws GeneralException, IOException {

    if( out != null ) {
      if( document != null ) {
        document.close();
      }
      out.close();
      out = null;
    }
    else {
      throw new DocumentWriterClosedChannelException( "closed channel" );
    }

  }

  @Override
  public void configure( Configuration config ) throws ConfigurationException {

    if( config != null ) {
      // TODO mgn: incomplete
    }

  }

  @Override
  public void enableLogging( Logger logger ) {

    this.logger = logger;

  }

  @Override
  public String getExtension() {

    return "pdf";
  }

  /**
   * Set the page size.
   */
  private void pageSize() {

    // TODO mgn: set the right page size.
    document.setPageSize( PageSize.A4 );

    nodevisitor.setPageSize( PageSize.A4 );
  }

  @Override
  public void setFontFactory( CoreFontFactory factory ) {

    corefactory = factory;
    List<String> sl = new ArrayList<String>();
    sl.add( "ttf" );
    sl.add( "afm" );
    sl.add( "tfm" );
    manager = corefactory.createManager( sl );

  }

  @Override
  public void setOutputStream( OutputStream writer ) {

    out = writer;

  }

  /**
   * java.lang.String)
   */
  @Override
  public void setParameter( String name, String value ) {

    param.put( name, value );

  }

  @Override
  public int shipout( Page page ) throws GeneralException, IOException {

    try {
      if( document == null ) {
        document = new Document();
        writer = PdfWriter.getInstance( document, out );
        document.open();

        PdfFontFactory.setLogger( logger );
        nodevisitor =
            new PdfNodeVisitor( document, writer, manager, logger,
                                localizer );
      }
      pageSize();
      document.newPage();
      shippedPages++;

      // set start point
      nodevisitor.setX( Dimen.ONE_INCH );
      nodevisitor.setY( Dimen.ONE_INCH );

      NodeList nodes = page.getNodes();
      nodes.visit( nodevisitor, nodes );

    } catch( DocumentException e ) {
      throw new DocumentWriterException( e.getMessage() );
    }
    return shippedPages;
  }
}
