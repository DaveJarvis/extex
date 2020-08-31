/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bibio;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This is a test suite for {@link BibPrinterFactory}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BibPrinterFactoryTest {

  /**
   * The field {@code cfg} contains the test configuration.
   */
  private final Configuration cfg = new Configuration() {

    /**
     *      java.lang.String)
     */
    public Configuration findConfiguration( String key )
        throws ConfigurationInvalidResourceException,
        ConfigurationNotFoundException,
        ConfigurationSyntaxException,
        ConfigurationIOException {

      return this;
    }

    /**
     *      java.lang.String, java.lang.String)
     */
    public Configuration findConfiguration( String key, String attribute )
        throws ConfigurationException {

      return this;
    }

    /**
     *      java.lang.String)
     */
    public String getAttribute( String name ) {

      if( "base".equals( name ) ) {
        return null;
      }
      assertTrue( name, "class".equals( name ) );
      return BibPrinterLispImpl.class.getName();
    }

    /**
     *      java.lang.String)
     */
    public Configuration getConfiguration( String key )
        throws ConfigurationException {

      return this;
    }

    /**
     *      java.lang.String, java.lang.String)
     */
    public Configuration getConfiguration( String key, String attribute )
        throws ConfigurationException {

      return null;
    }

    public String getValue() throws ConfigurationException {

      return null;
    }

    /**
     *      java.lang.String)
     */
    public String getValue( String key ) throws ConfigurationException {

      return null;
    }

    /**
     *      java.lang.String, int)
     */
    public int getValueAsInteger( String key, int defaultValue )
        throws ConfigurationException {

      return 0;
    }

    /**
     *      java.util.List, java.lang.String)
     */
    public void getValues( List<String> list, String key ) {

      // getValues unimplemented
    }

    /**
     *      java.lang.String)
     */
    public List<String> getValues( String key ) {

      return null;
    }

    public Iterator<Configuration> iterator() throws ConfigurationException {

      return null;
    }

    /**
     *      java.lang.String)
     */
    public Iterator<Configuration> iterator( String key )
        throws ConfigurationException {

      return null;
    }

    /**
     *      org.extex.framework.configuration.ConfigurationLoader)
     */
    public void setConfigurationLoader( ConfigurationLoader loader ) {

      // setConfigurationLoader unimplemented
    }

  };

  /**
   * The default value of encoding is {@code null}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    BibPrinter ret = new BibPrinterFactory( cfg ).newInstance( "abc", null );
    assertNotNull( ret );
    assertTrue( ret instanceof BibPrinterLispImpl );
  }

}
