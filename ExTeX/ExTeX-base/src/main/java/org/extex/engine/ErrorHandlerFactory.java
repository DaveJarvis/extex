/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
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

package org.extex.engine;

import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.EditHandler;
import org.extex.interpreter.ErrorHandler;

/**
 * This is the factory for instances of
 * {@link org.extex.interpreter.ErrorHandler ErrorHandler}. This factory
 * inherits its properties from the {@link org.extex.framework.AbstractFactory
 * AbstractFactory}. Among them the support for configuration and logging.
 * <p>
 * Configuration
 *
 * <p>
 * Mainly the configuration needs to specify which class to use for the
 * ErrorHandler. The configuration provides a mapping from a type name to the
 * sub-configuration to be used. The name of the class is given as the argument
 * {@code class} of the sub-configuration as shown below.
 * </p>
 *
 * <pre>
 *   &lt;ErrorHandler default="ExTeX"&gt;
 *     &lt;ExTeX class="org.extex.main.errorHandler.ErrorHandlerImpl"&gt;
 *       &lt;EditHandler class="org.extex.main.errorHandler.editHandler.EditHandlerTeXImpl"/&gt;
 *     &lt;/ExTeX&gt;
 *     &lt;TeX class="org.extex.main.errorHandler.ErrorHandlerTeXImpl"&gt;
 *       &lt;EditHandler class="org.extex.main.errorHandler.editHandler.EditHandlerTeXImpl"/&gt;
 *     &lt;/TeX&gt;
 *     &lt;extex class="org.extex.main.errorHandler.ErrorHandlerImpl"/&gt;
 *     &lt;tex class="org.extex.main.errorHandler.ErrorHandlerTeXImpl"/&gt;
 *    &lt;/ErrorHandler&gt;
 *  </pre>
 *
 * <p>
 * The named class need to implement the interface
 * {@link org.extex.interpreter.ErrorHandler ErrorHandler}. If this interface is
 * not implemented an error is raised.
 * </p>
 * <p>
 * The configuration is passed down to the new instance if it implements the
 * interface {@link org.extex.framework.configuration.Configurable
 * Configurable}.
 * </p>
 * <p>
 * If the class implements the interface
 * {@link org.extex.framework.logger.LogEnabled LogEnabled} then a logger is
 * passed to the new instance. For this purpose the factory itself is log
 * enabled to receive the logger.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ErrorHandlerFactory extends AbstractFactory<ErrorHandler> {


  public ErrorHandlerFactory() {

  }

  /**
   * Creates a new object and configures it according to the parameter.
   *
   * @param configuration the configuration to use
   * @throws ConfigurationException in case of an error during configuration
   */
  public ErrorHandlerFactory( Configuration configuration )
      throws ConfigurationException {

    configure( configuration );
  }

  /**
   * Get an instance of an error handler. This method selects one of the
   * entries in the configuration. The selection is done with the help of a
   * type String. If the type is {@code null} or the empty string then
   * the default from the configuration is used.
   *
   * @param type the type to use
   * @return a new error handler
   * @throws ConfigurationException in case of an configuration error
   */
  public ErrorHandler newInstance( String type ) throws ConfigurationException {

    ErrorHandler errorHandler = createInstance( type, ErrorHandler.class );
    Configuration cfg =
        selectConfiguration( type ).findConfiguration( "EditHandler" );
    if( cfg != null ) {
      errorHandler
          .setEditHandler( (EditHandler) createInstanceForConfiguration(
              cfg, EditHandler.class ) );
    }
    return errorHandler;
  }

}
