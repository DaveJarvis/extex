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

package org.extex.backend;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterFactory;
import org.extex.backend.documentWriter.MultipleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.exception.BackendDocumentWriterDefinedException;
import org.extex.backend.exception.BackendException;
import org.extex.backend.exception.BackendUnknownDocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.pageFilter.PagePipe;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.core.exception.GeneralException;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontAware;
import org.extex.resource.PropertyAware;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.type.page.Page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * This back-end driver can be used to combine several components.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BackendDriverImpl
    implements
    BackendDriver,
    MultipleDocumentStream,
    PropertyAware,
    ColorAware,
    ResourceAware,
    FontAware {

  /**
   * This internal class acts as page counter as last element in the node
   * pipe.
   *
   * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
   */
  private class Counter implements PagePipe {

    public void close() throws BackendException {

      if( documentWriter == null ) {
        return;
      }
      try {
        documentWriter.close();
      } catch( GeneralException e ) {
        new BackendException( e );
      } catch( IOException e ) {
        throw new BackendException( e );
      }
      documentWriter = null;
    }

    /**
     * org.extex.backend.pageFilter.PagePipe)
     */
    public void setOutput( PagePipe next ) {

      throw new RuntimeException( "this should not happen" );
    }

    /**
     * java.lang.String, java.lang.String)
     */
    public void setParameter( String name, String value ) {

      // nothing to do
    }

    /**
     * org.extex.typesetter.type.page.Page)
     */
    public void shipout( Page nodes ) throws BackendException {

      try {
        documentWriter.shipout( nodes );
      } catch( DocumentWriterException e ) {
        throw e;
      } catch( GeneralException e ) {
        throw new DocumentWriterException( e );
      } catch( IOException e ) {
        throw new DocumentWriterException( e );
      }
      pages++;
    }

  }

  /**
   * The field {@code colorConverter} contains the color converter.
   */
  private ColorConverter colorConverter;

  /**
   * The field {@code counter} contains the counter page pipe which will
   * always be placed at the end of the of the pipe.
   */
  private final PagePipe counter = new Counter();

  /**
   * The field {@code documentWriter} contains the document writer.
   */
  private DocumentWriter documentWriter = null;

  /**
   * The field {@code documentWriterFactory} contains the factory for new
   * document writers.
   */
  private DocumentWriterFactory documentWriterFactory;

  /**
   * The field {@code documentWriterType} contains the type of the document
   * writer to use.
   */
  private String documentWriterType = "?";

  /**
   * The field {@code finder} contains the resource finder.
   */
  private ResourceFinder finder;

  /**
   * The field {@code fontFactory} contains the font factory.
   */
  private CoreFontFactory fontFactory;

  /**
   * The field {@code pages} contains the number of pages already sent to
   * the document writer.
   */
  private int pages = 0;

  /**
   * The field {@code params} contains the parameters to be passed to the
   * document writer.
   */
  private final Map<String, String> params = new HashMap<String, String>();

  /**
   * The field {@code pipeFirst} contains the elements of the pipe.
   */
  private PagePipe pipeFirst = counter;

  /**
   * The field {@code pipeLast} contains the last item in the pipe.
   * Initially it is the counter. If the pipe is longer this value is the last
   * item before the counter.
   */
  private PagePipe pipeLast = counter;

  /**
   * The field {@code properties} contains the properties.
   */
  private Properties properties;

  /**
   * The field {@code streamFactory} contains the output stream factory.
   */
  private OutputStreamFactory streamFactory;


  public BackendDriverImpl() {

  }

  /**
   * org.extex.backend.pageFilter.PagePipe)
   */
  public void add( PagePipe processor ) {

    processor.setOutput( counter );

    if( pipeFirst == counter ) {
      pipeFirst = processor;
    }
    else {
      pipeLast.setOutput( processor );
    }

    pipeLast = processor;
  }

  public void close() throws BackendException {

    pipeFirst.close();

    if( documentWriter != null ) {
      try {
        documentWriter.close();
      } catch( GeneralException e ) {
        throw new BackendException( e );
      } catch( IOException e ) {
        throw new BackendException( e );
      }
      documentWriter = null;
    }
  }

  public DocumentWriter getDocumentWriter() throws DocumentWriterException {

    if( documentWriter == null ) {

      documentWriter = documentWriterFactory.newInstance(
          documentWriterType,
          streamFactory );
      if( documentWriter instanceof PropertyAware ) {
        ((PropertyAware) documentWriter).setProperties( properties );
      }
      if( documentWriter instanceof ColorAware ) {
        ((ColorAware) documentWriter).setColorConverter( colorConverter );
      }
      if( documentWriter instanceof ResourceAware ) {
        ((ResourceAware) documentWriter).setResourceFinder( finder );
      }
      if( documentWriter instanceof FontAware ) {
        ((FontAware) documentWriter).setFontFactory( fontFactory );
      }

      for( Entry<String, String> param : params.entrySet() ) {
        documentWriter.setParameter( param.getKey(), param.getValue() );
      }

      if( documentWriter instanceof MultipleDocumentStream ) {
        ((MultipleDocumentStream) documentWriter)
            .setOutputStreamFactory( streamFactory );
      }

    }
    return documentWriter;
  }

  public String getDocumentWriterType() {

    return documentWriterType;
  }

  /**
   * Getter for the extension associated with this kind of output. For
   * instance {@code pdf} is the expected value for PDF files and
   * {@code dvi} is the expected value for DVI files.
   *
   * @return the appropriate extension for file names
   * @see org.extex.backend.documentWriter.DocumentWriter#getExtension()
   */
  public String getExtension() {

    return documentWriter.getExtension();
  }

  /**
   * Getter for the number of pages already produced.
   *
   * @return the number of pages already shipped out
   * @see org.extex.backend.BackendDriver#getPages()
   */
  public int getPages() {

    return pages;
  }

  public void setColorConverter( ColorConverter converter ) {

    this.colorConverter = converter;
  }

  /**
   * org.extex.backend.documentWriter.DocumentWriterFactory)
   */
  public void setDocumentWriterFactory(
      DocumentWriterFactory documentWriterFactory ) {

    this.documentWriterFactory = documentWriterFactory;
  }

  public void setDocumentWriterType( String type )
      throws BackendDocumentWriterDefinedException,
      BackendUnknownDocumentWriterException {

    if( documentWriterType.equals( type ) ) {
      return;
    }
    if( type == null ) {
      throw new IllegalArgumentException( "DocumentWriterType" );
    }
    if( documentWriter != null ) {
      throw new BackendDocumentWriterDefinedException();
    }
    if( documentWriterFactory != null && !documentWriterFactory.check( type ) ) {
      throw new BackendUnknownDocumentWriterException( type );
    }
    documentWriterType = type;
  }

  public void setFontFactory( CoreFontFactory factory ) {

    this.fontFactory = factory;
  }

  /**
   * Setter for the output stream.
   *
   * @param factory the output stream
   * @see org.extex.backend.documentWriter.MultipleDocumentStream#setOutputStreamFactory(
   *org.extex.backend.outputStream.OutputStreamFactory)
   */
  public void setOutputStreamFactory( OutputStreamFactory factory ) {

    this.streamFactory = factory;
  }

  /**
   * Setter for a named parameter. Parameters are a general mechanism to
   * influence the behavior of the document writer. Any parameter not known by
   * the document writer has to be ignored.
   * <p>
   * If the document writer is in use already then the parameter is passed to
   * the document writer. Otherwise the parameter is stored until a document
   * writer is created. Then the parameter is passed on.
   * </p>
   *
   * @param name  the name of the parameter
   * @param value the value of the parameter
   * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(
   *java.lang.String, java.lang.String)
   */
  public void setParameter( String name, String value ) {

    if( documentWriter != null ) {
      documentWriter.setParameter( name, value );
    }
    else {
      params.put( name, value );
    }
  }

  public void setProperties( Properties properties ) {

    this.properties = properties;
  }

  /**
   * org.extex.resource.ResourceFinder)
   */
  public void setResourceFinder( ResourceFinder f ) {

    this.finder = f;
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
   * @throws BackendException in case of an error
   * @see org.extex.backend.BackendDriver#shipout(
   *org.extex.typesetter.type.page.Page)
   */
  public int shipout( Page page ) throws BackendException {

    if( documentWriter == null ) {
      getDocumentWriter(); // to force delayed creation;
    }
    int n = pages;
    pipeFirst.shipout( page );
    return pages - n;
  }

  @Override
  public String toString() {

    return documentWriterType;
  }

}
