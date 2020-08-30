/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.rtf;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.junit.Test;

/**
 * This is the test suite for the RTF writer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class RtfDocumentWriterTest {

    /**
     *  Test that the default extension is {@code rtf}.
     *
     */
    @Test
    public final void testGetExtension1() {

        assertEquals("rtf", new RtfDocumentWriter(null).getExtension());
    }

    /**
     *  Test that the extension is {@code rtf}.
     */
    @Test
    public final void testConfigure1() {

        RtfDocumentWriter rtfDocumentWriter = new RtfDocumentWriter(null);
        rtfDocumentWriter.configure(new Configuration() {

            public Configuration findConfiguration(String key)
                    throws ConfigurationInvalidResourceException,
                        ConfigurationNotFoundException,
                        ConfigurationSyntaxException,
                        ConfigurationIOException {

                throw new ConfigurationNotFoundException("", "");
            }

            public Configuration findConfiguration(String key, String attribute)
                    throws ConfigurationException {

                throw new ConfigurationNotFoundException("", "");
            }

            public String getAttribute(String name) {

                if (name.equals("extension")) {
                    return "abc";
                }
                throw new ConfigurationNotFoundException("", "");
            }

            public Configuration getConfiguration(String key)
                    throws ConfigurationException {

                throw new ConfigurationNotFoundException("", "");
            }

            public Configuration getConfiguration(String key, String attribute)
                    throws ConfigurationException {

                throw new ConfigurationNotFoundException("", "");
            }

            public String getValue() throws ConfigurationException {

                throw new ConfigurationNotFoundException("", "");
            }

            public String getValue(String key) throws ConfigurationException {

                throw new ConfigurationNotFoundException("", "");
            }

            public int getValueAsInteger(String key, int defaultValue)
                    throws ConfigurationException {

                return 42;
            }

            public List<String> getValues(String key) {

                return null;
            }

            public void getValues(List<String> list, String key) {

                // nothing to do
            }

            public Iterator<Configuration> iterator()
                    throws ConfigurationException {

                throw new ConfigurationNotFoundException("", "");
            }

            public Iterator<Configuration> iterator(String key)
                    throws ConfigurationException {

                throw new ConfigurationNotFoundException("", "");
            }

            public void setConfigurationLoader(ConfigurationLoader loader) {

                // nothing to do
            }

        });
        assertEquals("abc", rtfDocumentWriter.getExtension());
    }

    /**
     *  Test that a {@code null} page is not shipped.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout1() throws Exception {

        RtfDocumentWriter writer = new RtfDocumentWriter(null);
        assertEquals(0, writer.shipout(null));
    }

    // TODO implement more test cases
}
