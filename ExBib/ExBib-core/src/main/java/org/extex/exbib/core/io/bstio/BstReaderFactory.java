/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bstio;

import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link BstReader BstReader}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute {@code class}. This attribute holds the name of the class to
 * be instantiated. Consider the following example of a configuration file:
 * </p>
 *
 * <pre>
 *   &lt;BstReader
 *     class="org.extex.exbib.core.io.bstio.BstReaderImpl"/&gt;
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BstReaderFactory extends AbstractFactory<BstReader> {

  /**
   * Creates a new object.
   *
   * @param configuration the configuration
   * @param finder        the resource finder
   * @throws ConfigurationException in case of an configuration error
   */
  public BstReaderFactory( Configuration configuration, ResourceFinder finder )
      throws ConfigurationException {

    super( configuration );
    setResourceFinder( finder );
  }

  /**
   * Return a new instance of a BstReader.
   *
   * @return the new instance of a BstReader
   * @throws ConfigurationException in case that the configuration is invalid
   */
  public synchronized BstReader newInstance() throws ConfigurationException {

    BstReader reader = createInstance( BstReader.class );
    reader.setResourceFinder( getResourceFinder() );
    return reader;
  }

}
