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

package org.extex.backend.documentWriter.postscript;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.MultipleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.postscript.converter.PsBasicConverter;
import org.extex.backend.documentWriter.postscript.converter.PsBoxConverter;
import org.extex.backend.documentWriter.postscript.converter.PsConverter;
import org.extex.backend.documentWriter.postscript.util.FontManager;
import org.extex.backend.documentWriter.postscript.util.HeaderManager;
import org.extex.backend.documentWriter.postscript.util.PsUnit;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontAware;
import org.extex.font.manager.ManagerInfo;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.type.NodeList;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is the abstract base class for document writers producing PostScript
 * code. Here some utility methods of general nature are collected.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractPostscriptWriter
    implements
    DocumentWriter,
    Configurable,
    MultipleDocumentStream,
    ResourceAware,
    FontAware,
    ColorAware {

  /**
   * The constant {@code COMMENT} contains two percent signs as start of a
   * comment.
   */
  protected static final byte[] COMMENT = new byte[]{'%', '%'};

  /**
   * The field {@code DF} contains the formatter for the date.
   */
  protected static final DateFormat DF =
      new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );

  /**
   * The field {@code boxed} contains the indicator whether the box-only
   * converter should be used.
   */
  private boolean boxed;

  /**
   * The field {@code colorConverter} contains the color converter as set
   * from the managing instance.
   */
  private ColorConverter colorConverter = null;

  /**
   * The field {@code converter} contains the cached PostScript converter.
   */
  private PsConverter converter = null;

  /**
   * The field {@code extension} contains the extension.
   */
  private String extension;

  /**
   * The field {@code finder} contains the resource finder as set from the
   * managing instance.
   */
  private ResourceFinder finder = null;

  /**
   * The field {@code fontFactory} contains the font factory.
   */
  private CoreFontFactory fontFactory;

  /**
   * The field {@code fontManager} contains the font manager.
   */
  private FontManager fontManager;

  /**
   * The field {@code headerManager} contains the header manager.
   */
  private final HeaderManager headerManager = new HeaderManager();

  /**
   * The field {@code parameter} contains the map for parameters.
   */
  private final Map<String, String> parameter = new HashMap<String, String>();

  /**
   * The field {@code writerFactory} contains the output stream factory.
   */
  private OutputStreamFactory writerFactory;

  /**
   * Creates a new object.
   *
   * @param extension the default extension
   */
  public AbstractPostscriptWriter( String extension ) {

    parameter.put( "Creator", "ExTeX-Backend-ps" );
    parameter.put( "Title", "" );
    parameter.put( "PageOrder", "Ascend" );
    this.extension = extension;
  }

  /**
   * Configure an object according to a given Configuration.
   *
   * @param config the configuration object to consider
   * @throws ConfigurationException in case that something went wrong
   * @see org.extex.framework.configuration.Configurable#configure(
   *org.extex.framework.configuration.Configuration)
   */
  public void configure( Configuration config ) throws ConfigurationException {

    String s = config.getAttribute( "boxed" );
    this.boxed = (s != null && Boolean.valueOf( s ).booleanValue());

    s = config.getAttribute( "extension" );
    if( s != null ) {
      extension = s;
    }
  }

  /**
   * Create a PostScript converter.
   *
   * @return the new converter
   * @throws IOException in case of an IO error
   */
  protected PsConverter getConverter() throws IOException {

    if( converter != null ) {
      return converter;
    }

    converter = (boxed ? new PsBoxConverter() : new PsBasicConverter());

    if( converter instanceof ColorAware ) {
      ((ColorAware) converter).setColorConverter( colorConverter );
    }
    if( converter instanceof ResourceAware ) {
      ((ResourceAware) converter).setResourceFinder( finder );
    }

    headerManager.reset();
    converter.init( headerManager, fontManager );
    return converter;
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

    return extension;
  }

  /**
   * Getter for fontFactory.
   *
   * @return the fontFactory
   */
  public CoreFontFactory getFontFactory() {

    return fontFactory;
  }

  /**
   * Getter for fontManager.
   *
   * @return the fontManager
   */
  protected FontManager getFontManager() {

    return fontManager;
  }

  /**
   * Getter for a named parameter.
   *
   * @param name the name of the parameter
   * @return the value of the parameter or {@code null} if none exists
   */
  protected String getParameter( String name ) {

    return parameter.get( name );
  }

  /**
   * Acquire a new output stream.
   *
   * @param type the type for the reference to the configuration file
   * @return the new output stream
   * @throws DocumentWriterException in case of an error
   */
  protected PrintStream newOutputStream( String type )
      throws DocumentWriterException {

    return new PrintStream( writerFactory.getOutputStream( null, type ) );
  }

  /**
   * Setter for the color converter.
   *
   * @param converter the color converter
   * @see org.extex.color.ColorAware#setColorConverter(
   *org.extex.color.ColorConverter)
   */
  public void setColorConverter( ColorConverter converter ) {

    this.colorConverter = converter;
  }

  /**
   * Setter for extension.
   *
   * @param extension the extension to set
   */
  protected void setExtension( String extension ) {

    this.extension = extension;
  }

  public void setFontFactory( CoreFontFactory factory ) {

    this.fontFactory = factory;

    List<String> types = new ArrayList<String>();
    types.add( "pfa" );
    types.add( "pfb" );
    fontManager = new FontManager( factory.createManager( types ) );
  }

  /**
   * Setter for the output stream.
   *
   * @param factory the output stream
   * @see org.extex.backend.documentWriter.MultipleDocumentStream#setOutputStreamFactory(
   *org.extex.backend.outputStream.OutputStreamFactory)
   */
  public void setOutputStreamFactory( OutputStreamFactory factory ) {

    this.writerFactory = factory;
  }

  /**
   * Setter for a named parameter. Parameters are a general mechanism to
   * influence the behavior of the document writer. Any parameter not known by
   * the document writer has to be ignored.
   *
   * <p>
   * This document writer uses the following parameters:
   * </p>
   *
   * <dl>
   * <dt>Creator</dt>
   * <dd>This is the identifier of the creating component. The default value
   * is {@code ExTeX-Backend-ps}. </dd>
   * <dt>Title</dt>
   * <dd>This is the title of the document. The default is empty. </dd>
   * <dt>PageOrder</dt>
   * <dd>This parameter denotes the order in which pages are contained. The
   * default is {@code Ascend}. It denotes ascending order of pages.
   * Alternately the value {@code Descend} can e used to denote descending
   * order. Any other value is silently interpreted to be the same as
   * Ascend</dd>
   * </dl>
   *
   * @param name  the name of the parameter
   * @param value the value of the parameter
   */
  public void setParameter( String name, String value ) {

    parameter.put( name, value );
  }

  /**
   * Setter for the resource finder.
   *
   * @param resourceFinder the resource finder
   * @see org.extex.resource.ResourceAware#setResourceFinder(
   *org.extex.resource.ResourceFinder)
   */
  public void setResourceFinder( ResourceFinder resourceFinder ) {

    this.finder = resourceFinder;
  }

  /**
   * Fill the initial segment of a PostScript file.
   *
   * @param out          the output stream
   * @param magicVersion the version string
   * @throws IOException in case of an I/O error
   */
  protected void startFile( PrintStream out, String magicVersion )
      throws IOException {

    out.print( "%!" );
    out.println( magicVersion );
    writeDsc( out, "Creator", getParameter( "Creator" ) );
    writeDsc( out, "CreationDate",
              DF.format( Calendar.getInstance().getTime() ) );
    writeDsc( out, "Title", getParameter( "Title" ) );
  }

  /**
   * Write a BoundingBox DSC to an output stream.
   *
   * @param stream the target stream to write to
   * @param width  the width
   * @param height the height (including the depth)
   * @throws IOException in case of an error during writing
   */
  protected void writeBoundingBox( PrintStream stream, FixedDimen width,
                                   FixedDimen height ) throws IOException {

    stream.write( COMMENT );
    stream.print( "BoundingBox" );
    stream.print( ": 0 0 " );
    PsUnit.toPoint( width, stream, true );
    stream.write( ' ' );
    Dimen d = new Dimen( height );
    PsUnit.toPoint( d, stream, true );
    stream.println();
  }

  /**
   * Write a meta comment according to the Document Structuring Conventions.
   *
   * @param stream the target stream to write to
   * @param name   the name of the DSC comment
   * @param values the value of the DSC comment
   * @throws IOException in case of an error during writing
   */
  protected void writeDsc( PrintStream stream, String name, String... values )
      throws IOException {

    stream.write( COMMENT );
    stream.print( name );
    if( values.length > 0 ) {
      stream.write( ':' );
      for( String s : values ) {
        stream.write( ' ' );
        stream.print( s );
      }
    }
    stream.println();
  }

  /**
   * Write a meta comment according to the Document Structuring Conventions
   * containing the {@code DocumentFonts}.
   *
   * @param stream      the target stream to write to
   * @param fontManager the font manager to ask for the fonts
   * @throws IOException in case of an error during writing
   */
  protected void writeFonts( PrintStream stream, FontManager fontManager )
      throws IOException {

    stream.print( "%%DocumentFonts:" );

    for( ManagerInfo mi : fontManager ) {
      stream.write( ' ' );
      stream.print( mi.getBackendFont().getName() );
    }
    stream.println();
  }

  /**
   * Write a HiResBoundingBox DSC to an output stream.
   *
   * @param stream the target stream to write to
   * @param nodes  the nodes to extract the dimensions from
   * @throws IOException in case of an error during writing
   */
  protected void writeHighResBoundingBox( PrintStream stream, NodeList nodes )
      throws IOException {

    stream.write( COMMENT );
    stream.print( "HiResBoundingBox" );
    stream.print( ": 0.0 0.0 " );
    PsUnit.toPoint( nodes.getWidth(), stream, false );
    stream.write( ' ' );
    Dimen d = new Dimen( nodes.getHeight() );
    d.add( nodes.getDepth() );
    PsUnit.toPoint( d, stream, false );
    stream.println();
  }
}
