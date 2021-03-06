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

package org.extex.language.impl;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamConsumer;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.engine.backend.NamedOutputStream;
import org.extex.framework.Registrar;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * This class manages the {@code Language}s. It is a container which can be
 * asked to provide an appropriate instance. This instance is either taken from
 * existing instances or a new instance is created. Since at the time of
 * creation it can not be decided whether a new one should be used or an
 * existing one should be loaded a future object is returned which enables us to
 * postpone the operation until the decision can be made.
 * <p>
 * The future object invokes one of the methods of {@link LanguageCreator
 * LanguageCreator}. In this case we know whether to create or load the
 * language. Thus the appropriate operation is performed and the resulting
 * language is put into the map overwriting the future object.
 * </p>
 * <p>
 * TODO gene: documentation incomplete
 * </p>
 *
 * <h2>Configuration</h2>
 * <p>
 * This instance is configurable. The configuration is used to select the
 * appropriate class and optional parameters for a requested instance. In this
 * respect this class makes best use of the infrastructure of the
 * {@link org.extex.framework.AbstractFactory AbstractFactory}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LoadingLanguageManager extends BaseLanguageManager
    implements
    LanguageCreator,
    ResourceAware,
    OutputStreamConsumer {

  /**
   * The constant {@code NON_LOADABLE_LANGUAGE_PATTERN} contains the patter
   * to detect languages which should not be handled via external resources.
   * Currently this value detects purely numerical names.
   */
  private static final String NON_LOADABLE_LANGUAGE_PATTERN = "^\\d*$";

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2011L;

  /**
   * The field {@code TABLE_EXTENSION} contains the extension for language
   * files.
   */
  private static final String TABLE_EXTENSION = "lfm";

  /**
   * The constant {@code VERSION} contains the version id to be written into
   * the external file.
   */
  private static final String VERSION = "1.0";

  /**
   * The field {@code finder} contains the resource finder to search for
   * language files.
   */
  private transient ResourceFinder finder;

  /**
   * The field {@code outFactory} contains the output stream factory.
   */
  private transient OutputStreamFactory outFactory = null;


  public LoadingLanguageManager() {

  }

  /**
   * Create a new language and put it into the table.
   *
   * @param name the name of the language
   * @return the new instance of a language
   * @see org.extex.language.impl.BaseLanguageManager#createLanguage(java.lang.String)
   */
  @Override
  protected Language createLanguage( String name ) {

    FutureLanguage language = new FutureLanguage( name, this );
    getTables().put( name, language );
    return language;
  }

  /**
   * Create a new instance for the given index if required.
   *
   * @param name the symbolic name of the language
   * @return the new instance
   * @throws HyphenationException   in case of an error
   * @throws ConfigurationException in case of an configuration error
   * @see org.extex.language.impl.LanguageCreator#createLanguageInstance(java.lang.String)
   */
  @Override
  public Language createLanguageInstance( String name )
      throws HyphenationException {

    Language language;
    language = super.createLanguage( name );
    language.setName( name );
    getTables().put( name, language );
    return language;
  }

  /**
   * Load or create a new instance for the given index if required.
   *
   * @param name the symbolic name of the language
   * @return the new instance
   * @throws HyphenationException in case of an error
   * @see org.extex.language.impl.LanguageCreator#loadLanguageInstance(java.lang.String)
   */
  @Override
  public Language loadLanguageInstance( String name )
      throws HyphenationException {

    if( name == null || name.matches( NON_LOADABLE_LANGUAGE_PATTERN ) ) {

    }
    else if( finder == null ) {
      getLogger().warning( getLocalizer().format( "MissingResourceFinder" ) );
    }
    else {
      ObjectInputStream in = null;
      try {
        InputStream ins = finder.findResource( name, TABLE_EXTENSION );
        if( ins != null ) {

          ins = new BufferedInputStream( ins );

          for( int c = ins.read(); c != '\n'; c = ins.read() ) {
            if( c < 0 ) {
              throw new HyphenationException( "EOF" );
            }
          }
          in = new ObjectInputStream( new GZIPInputStream( ins ) );
          Object version = in.readObject();

          Language lang = (Language) version;

          in.close();
          getTables().put( name, lang );
          return lang;
        }
        getLogger().warning(
            getLocalizer().format( "LanguageNotFound", name ) );
      } catch( ConfigurationException e ) {
        throw new HyphenationException( e );
      } catch( IOException e ) {
        throw new HyphenationException( e );
      } catch( ClassNotFoundException e ) {
        throw new HyphenationException( e );
      } finally {
        if( in != null ) {
          try {
            in.close();
          } catch( IOException e ) {
            throw new HyphenationException( e );
          }
        }
      }
    }

    return createLanguageInstance( name );
  }

  /**
   * This method arranges that the data written out about the tables can be
   * read back in.
   *
   * @param in the stream to read from
   * @throws IOException            in case of an IO error
   * @throws ClassNotFoundException in case of a non existing class definition
   */
  private void readObject( ObjectInputStream in )
      throws IOException,
      ClassNotFoundException {

    // Registrar.register(new CreatorInjector(this), ManagedLanguage.class);
  }

  /**
   * Restore the internal state when the instance is loaded from file.
   *
   * @return the object which should be used instead of the one read
   * @throws ObjectStreamException in case of an error
   */
  @Override
  protected Object readResolve() throws ObjectStreamException {

    for( Language lang : getTables().values() ) {
      if( lang instanceof ManagedLanguage ) {
        ((ManagedLanguage) lang).setCreator( this );
      }
    }

    return Registrar.reconnect( this );
  }

  /**
   * Try to save the hyphenation table by other means. This can be used to
   * write the result to another place than the default output stream.
   * <p>
   * The table is not saved if the name is purely numeric. This guarantees the
   * backward compatibility with TeX, since TeX uses numerical names for 
   * hyphenation tables only.
   * </p>
   *
   * @param name  the name of the table
   * @param value the table itself
   * @return {@code true} iff the table has been saved
   * @throws IOException             in case of an IO error
   * @throws DocumentWriterException in case of an error
   */
  protected boolean saveTable( String name, Language value )
      throws IOException,
      DocumentWriterException {

    if( name == null || name.matches( NON_LOADABLE_LANGUAGE_PATTERN ) ) {
      return false;
    }

    OutputStream fos = outFactory.getOutputStream( name, TABLE_EXTENSION );

    String file = name;
    if( fos instanceof NamedOutputStream ) {
      file = ((NamedOutputStream) fos).getName();
    }
    fos.write( "#!extex -lfm\n".getBytes() );
    ObjectOutputStream out =
        new ObjectOutputStream( new GZIPOutputStream( fos ) );
    out.writeObject( VERSION );
    out.writeObject( value );
    out.close();
    getLogger().info( getLocalizer().format( "LanguageSaved", name, file ) );
    return true;
  }

  /**
   * This method takes an output stream factory for further use.
   *
   * @param factory the output stream factory to use
   * @see org.extex.backend.outputStream.OutputStreamConsumer#setOutputStreamFactory(org.extex.backend.outputStream.OutputStreamFactory)
   */
  @Override
  public void setOutputStreamFactory( OutputStreamFactory factory ) {

    this.outFactory = factory;
  }

  /**
   * Setter for the resource finder.
   *
   * @param resourceFinder the resource finder
   * @see org.extex.resource.ResourceAware#setResourceFinder(org.extex.resource.ResourceFinder)
   */
  @Override
  public void setResourceFinder( ResourceFinder resourceFinder ) {

    this.finder = resourceFinder;
  }

  /**
   * Write the hyphenation tables to the output stream which can not by saved
   * separately.
   *
   * @param out the output stream to write on
   * @throws IOException             in case of an I/O error
   * @throws DocumentWriterException in case of an error
   */
  private void writeObject( ObjectOutputStream out )
      throws IOException,
      DocumentWriterException {

    Map<String, Language> map = new HashMap<String, Language>();
    for( Entry<String, Language> e : getTables().entrySet() ) {
      String key = e.getKey();
      Language value = e.getValue();
      if( !saveTable( key, value ) ) {
        map.put( key, value );
      }
    }
  }

}
