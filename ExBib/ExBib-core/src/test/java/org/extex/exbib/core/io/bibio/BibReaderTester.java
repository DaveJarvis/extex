/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.extex.exbib.core.bst.exception.ExBibEntryUndefinedException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.junit.Test;

/**
 * This is an abstract base class for {@link BibReader}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class BibReaderTester {

    /**
     * A database implementation for testing.
     */
    protected class TestDB implements DB {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configurable#configure(
         *      org.extex.framework.configuration.Configuration)
         */
        public void configure(Configuration config)
                throws ConfigurationException {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#getEntries()
         */
        public List<Entry> getEntries() {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#getEntry(java.lang.String)
         */
        public Entry getEntry(String key) {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#getExpandedMacro(java.lang.String)
         */
        public String getExpandedMacro(String key) {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#getMacro(java.lang.String)
         */
        public Value getMacro(String name) {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#getMacroNames()
         */
        public List<String> getMacroNames() {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#getMinCrossrefs()
         */
        public int getMinCrossrefs() {

            return 0;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#getPreamble()
         */
        public Value getPreamble() {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#getSorter()
         */
        public Sorter getSorter() {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#load(java.lang.String, java.util.Map)
         */
        public List<String> load(String file, Map<String, String> citation)
                throws ExBibException,
                    ConfigurationException,
                    FileNotFoundException {

            assertTrue("unexpected load()", false);
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#makeEntry(java.lang.String,
         *      java.lang.String, org.extex.exbib.core.io.Locator)
         */
        public Entry makeEntry(String type, String key, Locator locator) {

            assertTrue("unexpected makeEntry()", false);
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#registerObserver(java.lang.String,
         *      org.extex.exbib.core.util.Observer)
         */
        public void registerObserver(String name, Observer observer)
                throws NotObservableException {

            assertTrue("unexpected registerObserver()", false);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#save(
         *      org.extex.exbib.core.io.bibio.BibPrinter)
         */
        public void save(BibPrinter writer) throws IOException {

            assertTrue("unexpected save()", false);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#setBibReaderFactory(
         *      org.extex.exbib.core.io.bibio.BibReaderFactory)
         */
        public void setBibReaderFactory(BibReaderFactory factory) {

            assertTrue("unexpected setBibReaderFactory()", false);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#setMinCrossrefs(int)
         */
        public void setMinCrossrefs(int minCrossref) {

            assertTrue("unexpected setMinCrossrefs()", false);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#setSorter(
         *      org.extex.exbib.core.db.sorter.Sorter)
         */
        public void setSorter(Sorter sorter) {

            assertTrue("unexpected setSorter()", false);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#sort()
         */
        public void sort() throws ConfigurationException {

            assertTrue("unexpected sort()", false);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#storeAlias(java.lang.String,
         *      java.lang.String, org.extex.exbib.core.io.Locator)
         */
        public void storeAlias(String alias, String key, Locator locator)
                throws ExBibEntryUndefinedException {

            assertTrue("unexpected storeAlias()", false);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#storeComment(java.lang.String)
         */
        public void storeComment(String comment) {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#storePreamble(
         *      org.extex.exbib.core.db.Value)
         */
        public void storePreamble(Value pre) {

            assertTrue("unexpected storePreamble()", false);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.DB#storeString(java.lang.String,
         *      org.extex.exbib.core.db.Value)
         */
        public void storeString(String name, Value value) {

            assertTrue("unexpected storeString()", false);
        }
    }

    /**
     * Create a new instance of the BibReader to be tested.
     * 
     * @return the test instance
     * 
     * @throws FileNotFoundException this should not happen
     */
    protected abstract BibReader makeTestInstance()
            throws FileNotFoundException;

    /**
     * <testcase> The empty input is accepted and does not require anything to
     * be stored in the database. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testComment1() throws Exception {

        BibReader r = makeTestInstance();
        r.open("test", new StringReader(""));
        r.load(new TestDB() {

            /**
             * {@inheritDoc}
             * 
             * @see BibReaderTester.TestDB#storeComment( java.lang.String)
             */
            @Override
            public void storeComment(String comment) {

                assertEquals("", comment);
            }

        });
    }

    /**
     * <testcase> The comments are accepted and stored in the database.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testComment2() throws Exception {

        BibReader r = makeTestInstance();
        r.open("test", new StringReader("abc"));
        r.load(new TestDB() {

            /**
             * {@inheritDoc}
             * 
             * @see BibReaderTester.TestDB#storeComment( java.lang.String)
             */
            @Override
            public void storeComment(String comment) {

                assertEquals("abc", comment);
            }

        });
    }

    /**
     * <testcase> The comments starting with {@literal @comment} are accepted
     * and stored in the database. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testComment3() throws Exception {

        BibReader r = makeTestInstance();
        r.open("test", new StringReader("abc @comment xyz"));
        r.load(new TestDB() {

            /**
             * {@inheritDoc}
             * 
             * @see BibReaderTester.TestDB#storeComment( java.lang.String)
             */
            @Override
            public void storeComment(String comment) {

                assertEquals("abc @comment xyz", comment);
            }

        });
    }

    /**
     * <testcase> The comments starting with {@literal @comment} are accepted
     * and stored in the database. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testComment4() throws Exception {

        BibReader r = makeTestInstance();
        r.open("test", new StringReader("abc @comment \nxyz"));
        r.load(new TestDB() {

            /**
             * {@inheritDoc}
             * 
             * @see BibReaderTester.TestDB#storeComment( java.lang.String)
             */
            @Override
            public void storeComment(String comment) {

                assertEquals("abc @comment  xyz", comment);
            }

        });
    }

}
