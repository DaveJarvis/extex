/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.token.impl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.exception.ExBibUndefinedFieldException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.extex.exbib.core.io.bibio.BibReader;
import org.extex.exbib.core.io.bibio.BibReader099Impl;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.io.bstio.BstReaderImpl;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

/**
 * This is a test suite for {@link TField}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TField2Test {

    private static final String DIR_TARGET = "build";

    /**
     * The field {@code config} contains the dummy configuration.
     */
    private final Configuration config = new Configuration() {

        public Configuration findConfiguration(String key)
                throws ConfigurationInvalidResourceException,
                    ConfigurationNotFoundException,
                    ConfigurationSyntaxException,
                    ConfigurationIOException {

            return this;
        }

        public Configuration findConfiguration(String key, String attribute)
                throws ConfigurationException {

            return this;
        }

        public String getAttribute(String name) {

            if (name.equals("class")) {
                return BstReaderImpl.class.getName();
            }
            return null;
        }

        public Configuration getConfiguration(String key)
                throws ConfigurationException {

            return this;
        }

        public Configuration getConfiguration(String key, String attribute)
                throws ConfigurationException {

            return this;
        }

        public String getValue() throws ConfigurationException {

            return null;
        }

        public String getValue(String key) throws ConfigurationException {

            return null;
        }

        public int getValueAsInteger(String key, int defaultValue)
                throws ConfigurationException {

            return 0;
        }

        public void getValues(List<String> list, String key) {


        }

        public List<String> getValues(String key) {

            return null;
        }

        public Iterator<Configuration> iterator() throws ConfigurationException {

            return null;
        }

        public Iterator<Configuration> iterator(String key)
                throws ConfigurationException {

            return new Iterator<Configuration>() {

                public boolean hasNext() {

                    return false;
                }

                public Configuration next() {

                    return null;
                }

                public void remove() {


                }
            };
        }

        public void setConfigurationLoader(ConfigurationLoader loader) {


        }

    };

    /**
     *  An undefined field leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUndefinedFieldException.class)
    public void test1() throws Exception {

        DB db = new DBImpl();
        db.setBibReaderFactory(new BibReaderFactory(config, null, null, null) {

        @Override
            public BibReader newInstance(String file)
                    throws ConfigurationException,
                        FileNotFoundException {

                BibReader099Impl r = new BibReader099Impl();
                r.open("xxx", new StringReader(
                    "@book{abc, title=\"The Bible\"}\n"));
                return r;
            }

        });
        BstInterpreter099c p =
                new BstInterpreter099c(db, new NullWriter(), null);
        p.configure(config);
        p.setResourceFinder(new ResourceFinder() {

            public void enableTracing(boolean flag) {


            }

            public NamedInputStream findResource(String name, String type)
                    throws ConfigurationException {

                return new NamedInputStream(new ByteArrayInputStream(
                    ("entry{abc}{}{}\n" + "function{book}{}\n"
                            + "function{xxx}{\n" + "  abc\n" + "  write$\n"
                            + "  newline$\n" + "}\n" + "\n" + "read\n"
                            + "iterate{xxx}\n").getBytes()), "");
            }
        });
        p.addBibliographyDatabase(". . .");
        p.addBibliographyStyle(DIR_TARGET + "/test.bst");
        p.addCitation("*");
        p.process(new NullWriter());
    }

}
