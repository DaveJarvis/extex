/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db;

import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link DB DB}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute {@code class}. This attribute holds the name of the class to
 * be instantiated. Consider the following example of a configuration file:
 * </p>
 *
 * <pre>{@code
 *   <DB class="org.extex.exbib.core.db.impl.DBImpl"/>
 * }
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DBFactory extends AbstractFactory<DB> {

  /**
   * Creates a new object.
   *
   * @param configuration the configuration
   */
  public DBFactory( Configuration configuration ) {

    super( configuration );
  }

  /**
   * Request a {@link DB DB} implementation. The new database is configured
   * with the same configuration as this factory itself.
   *
   * @param bibReaderFactory the factory for readers
   * @param minCrossrefs     the crossref.min parameter
   * @return a new instance of a DB
   * @throws ConfigurationException in case that something goes wrong
   */
  public DB newInstance( BibReaderFactory bibReaderFactory, int minCrossrefs )
      throws ConfigurationException {

    DB db = createInstance( DB.class );
    db.setBibReaderFactory( bibReaderFactory );
    db.setMinCrossrefs( minCrossrefs );
    return db;
  }

}
