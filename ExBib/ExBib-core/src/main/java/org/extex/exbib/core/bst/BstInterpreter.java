/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst;

import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.token.impl.TLocalLocator;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;

import java.util.Iterator;

/**
 * This class implements a processor. The major features are configurable.
 * <p>
 * Configuration
 * </p>
 * <p>
 * The configuration can be performed with the method
 * {@link #configure(Configuration)}. The configuration should contain a list of
 * function tags which are used to set up the functions of the processor. Each
 * one needs to have the attributes {@code name} and {@code class}.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BstInterpreter extends BstInterpreterCore {

  /**
   * Create a new object.
   *
   * @throws ExBibException in case of an error
   */
  public BstInterpreter() throws ExBibException {

  }

  /**
   * Configure an object according to a given Configuration.
   *
   * @param config the configuration object to consider
   * @throws ConfigurationException in case that something went wrong
   */
  @Override
  public void configure( Configuration config ) throws ConfigurationException {

    super.configure( config );

    Iterator<Configuration> iterator = config.iterator( "function" );

    while( iterator.hasNext() ) {
      Configuration builtin = iterator.next();
      String name = builtin.getAttribute( "name" );
      if( name == null || "".equals( name ) ) {
        throw new ConfigurationMissingException( "name" );
      }
      String className = builtin.getAttribute( "class" );
      if( className == null || "".equals( className ) ) {
        throw new ConfigurationMissingException( "class" );
      }
      try {
        Code code = (Code) Class.forName( className ).newInstance();
        code.setName( name );
        if( code instanceof Configurable ) {
          ((Configurable) code).configure( builtin );
        }
        addFunction( name, code, new Locator( config.toString(), 0 ) );
      } catch( ExBibException e ) {
        throw new ConfigurationWrapperException( e );
      } catch( InstantiationException e ) {
        throw new ConfigurationWrapperException( e );
      } catch( IllegalAccessException e ) {
        throw new ConfigurationWrapperException( e );
      } catch( ClassNotFoundException e ) {
        throw new ConfigurationWrapperException( e );
      }
    }
  }

  @Override
  public void reset() {

    super.reset();
    Locator locator = new Locator( getClass().getName() + "#reset()", 0 );
    try {
      addFunction( "locator.resource$", new TLocalLocator(
          "locator.resource$", locator,
          TLocalLocator.LocatorField.RESOURCE ), locator );
      addFunction( "locator.line$",
                   new TLocalLocator( "locator.line$",
                                      locator,
                                      TLocalLocator.LocatorField.RESOURCE ),
                   locator );
    } catch( ExBibException e ) {
      throw new ConfigurationWrapperException( e );
    }
  }

}
