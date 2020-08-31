/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bsf;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BibliographyCore;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.exceptions.ExBibIoException;
import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * This class provides a plug-in replacement for a bibliography processor. It is
 * based on the Bean Scripting Framework (BSF). Thus all programming languages
 * for which BSF bindings exist can be used as extension language for writing
 * εχBib style files.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BsfProcessor extends BibliographyCore
    implements
    Processor,
    ResourceAware {

  /**
   * The field {@code outWriter} contains the output writer.
   */
  private Writer outWriter;

  /**
   * The field {@code warnings} contains the number of warnings.
   */
  private long warnings;

  /**
   * The field {@code script} contains the name of the scripting language.
   */
  private String script;

  /**
   * The field {@code finder} contains the resource finder.
   */
  private ResourceFinder finder;

  /**
   * Creates a new object without database and logger. Those have to be
   * provided via setters (from the super class).
   */
  public BsfProcessor() {
    this( null, null );
  }

  /**
   * Creates a new object with database and logger.
   *
   * @param db  the database
   * @param log the logger
   */
  public BsfProcessor( DB db, Logger log ) {
    super( db, log );
  }

  @Override
  public void configure( Configuration config ) throws ConfigurationException {

    super.configure( config );
    script = config.getAttribute( "script" );
    if( script == null ) {
      throw new ConfigurationMissingAttributeException( "script", config );
    }
    String engine = config.getAttribute( "engine" );
    if( engine == null ) {
      throw new ConfigurationMissingAttributeException( "engine", config );
    }
    String extensions = config.getAttribute( "extensions" );
    if( extensions == null ) {
      throw new ConfigurationMissingAttributeException( "extensions",
                                                        config );
    }
    BSFManager.registerScriptingEngine( script, engine,
                                        extensions.split( ":" ) );
  }

  @Override
  public List<String> getMacroNames() {
    DB db = getDB();
    return db != null ? db.getMacroNames() : new ArrayList<String>();
  }

  @Override
  public long getNumberOfWarnings() {
    return warnings;
  }

  @Override
  public Writer getOutWriter() {
    return outWriter;
  }

  /**
   * Any Entry type is treated as known.
   * <p>
   * {@inheritDoc}
   *
   * @see org.extex.exbib.core.Processor#isKnown(java.lang.String)
   */
  @Override
  public boolean isKnown( String type ) {

    return true;
  }

  /**
   * Load a script into memory.
   *
   * @param sty the script
   * @return the string representation of the resource
   * @throws IOException               in case of an I/O error
   * @throws ExBibBstNotFoundException in case of a missing BST file
   */
  private String load( String sty )
      throws IOException, ExBibBstNotFoundException {
    final ByteArrayOutputStream result = new ByteArrayOutputStream();

    try( final NamedInputStream is = finder.findResource( sty, script ) ) {
      if( is == null ) {
        throw new ExBibBstNotFoundException( sty, null );
      }

      final byte[] buffer = new byte[ 1024 ];

      int length;
      while( (length = is.read( buffer )) != -1 ) {
        result.write( buffer, 0, length );
      }
    }

    return result.toString( UTF_8.name() );
  }

  @Override
  public long process( Writer outputWriter ) throws ExBibException {

    this.outWriter = outputWriter;

    try {
      loadDatabases();
    } catch( FileNotFoundException e ) {
      throw new ExBibFileNotFoundException( e.getMessage(), null );
    }

    try {
      BSFManager manager = new BSFManager();
      manager.declareBean( "bibDB", getDB(), DB.class );
      manager.declareBean( "bibWriter", outputWriter, Writer.class );
      manager.declareBean( "bibProcessor", this, Processor.class );

      for( String style : getBibliographyStyles() ) {
        manager.exec( script, style, 0, 0, load( style ) );
      }

    } catch( BSFException e ) {
      throw new ExBibException( e.getLocalizedMessage().replaceFirst(
          "^exception from [a-zA-Z:]* ", "" ) );
    } catch( IOException e ) {
      throw new ExBibIoException( e );
    }
    return warnings;
  }

  @Override
  public void setResourceFinder( ResourceFinder resourceFinder ) {

    this.finder = resourceFinder;
  }

  @Override
  public void warning( String message ) {
    Logger logger = getLogger();

    if( logger != null ) {
      logger.warning( message + "\n" );
    }
    warnings++;
  }
}
