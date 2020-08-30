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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.extex.exbib.core.bst.exception.ExBibEntryUndefinedException;
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.exceptions.ExBibMissingKeyException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedEofException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.junit.Test;

/**
 * This is a test suite for {@link BibReaderImpl}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BibReaderImplTest extends BibReaderTester {


    public BibReaderImplTest() {

        super(false);
    }

@Override
    protected BibReader makeTestInstance() {

        return new BibReaderImpl();
    }

    /**
     * Create a test instance and test it.
     * 
     * @param content the content to be read
     * @param db the db
     * 
     * @return the reader
     * 
     * @throws ConfigurationException in case of an error
     * @throws FileNotFoundException in case of an error
     * @throws ExBibException in case of an error
     */
    protected BibReader runTest(String content, DB db)
            throws FileNotFoundException,
                ConfigurationException,
                ExBibException {

        BibReader reader = new BibReaderImpl();
        reader.open("test", new StringReader(content));
        reader.load(db);
        return reader;
    }

    /**
     *  {@code alias} needs a parameter.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedEofException.class)
    public void testAlias01() throws Exception {

        runTest("@alias", new TestDB());
    }

    /**
     *  {@code alias} stores an alias.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAlias10() throws Exception {

        TestDB db = new TestDB() {

            /**
             * The field {@code visited} contains the entry if an alias has
             * been defined.
             */
            private Entry visited = null;

            @Override
            public Entry getEntry(String key) {

                return key.equals("abc") ? visited : null;
            }

            @Override
            public void storeAlias(String alias, String key, Locator locator)
                    throws ExBibEntryUndefinedException {

                visited = new Entry(null);
                assertEquals("abc", alias);
                assertEquals("def", key);
            }
        };
        runTest("@alias{abc=def}", db);
        assertNotNull(db.getEntry("abc"));
    }

    /**
     *  {@code alias} stores an alias.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAlias11() throws Exception {

        TestDB db = new TestDB() {

            /**
             * The field {@code visited} contains the entry if an alias has
             * been defined.
             */
            private Entry visited = null;

            @Override
            public Entry getEntry(String key) {

                return key.equals("abc") ? visited : null;
            }

            @Override
            public void storeAlias(String alias, String key, Locator locator)
                    throws ExBibEntryUndefinedException {

                visited = new Entry(null);
                assertEquals("abc", alias);
                assertEquals("def#ghi", key);
            }
        };
        runTest("@alias{abc=def#ghi}", db);
        assertNotNull(db.getEntry("abc"));
    }

    /**
     * The empty input is accepted and does not require anything to be stored in the database
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCommentExt1() throws Exception {

        runTest("abc @comment{@abc} def", new TestDB() {

        @Override
            public void storeComment(String comment) {

                assertEquals("abc @comment{@abc} def", comment);
            }

        });
    }

    /**
     *  {@code include} needs a parameter.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedEofException.class)
    public void testInclude01() throws Exception {

        runTest("@include", new TestDB());
    }

    /**
     *  {@code include} needs a parameter.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibFileNotFoundException.class)
    public void testInclude09() throws Exception {

        runTest("@include{}", new TestDB() {

            /**
        *      java.lang.String, java.util.Map)
             */
            @Override
            public List<String> load(String file, Map<String, String> citation)
                    throws ExBibException,
                        ConfigurationException,
                        FileNotFoundException {

                assertEquals("", file);
                throw new FileNotFoundException();
            }
        });
    }

    /**
     *  {@code include} needs a parameter.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testInclude10() throws Exception {

        runTest("@include{abc}", new TestDB() {

            /**
        *      java.lang.String, java.util.Map)
             */
            @Override
            public List<String> load(String file, Map<String, String> citation)
                    throws ExBibException,
                        ConfigurationException,
                        FileNotFoundException {

                assertEquals("abc", file);
                return new ArrayList<String>();
            }
        });
    }

    /**
     *  {@code modify} needs a parameter.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedEofException.class)
    public void testModify01() throws Exception {

        runTest("@modify", new TestDB());
    }

    /**
     *  {@code modify} needs a parameter.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedEofException.class)
    public void testModify02() throws Exception {

        runTest("@modify$", new TestDB());
    }

    /**
     *  {@code modify} needs a parameter.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingKeyException.class)
    public void testModify03() throws Exception {

        runTest("@modify{", new TestDB());
    }

    /**
     * {@code modify} needs a parameter which names an existing entry
* 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingEntryException.class)
    public void testModify04() throws Exception {

        runTest("@modify{abc ", new TestDB());
    }

    /**
     *  {@code modify} needs a parameter which names an existing
     * entry. Nothing else is needed.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testModify05() throws Exception {

        runTest("@modify{abc}", new TestDB() {

            /**
        *      java.lang.String)
             */
            @Override
            public Entry getEntry(String key) {

                return new Entry(null);
            }
        });
    }

    /**
     *  {@code modify} needs a parameter which names an existing
     * entry. Nothing else is needed.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testModify06() throws Exception {

        runTest("@modify(abc)", new TestDB() {

            /**
        *      java.lang.String)
             */
            @Override
            public Entry getEntry(String key) {

                return new Entry(null);
            }
        });
    }

    /**
     *  {@code modify} needs to be closed by the same type of brace
     * that opened it.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedException.class)
    public void testModify07() throws Exception {

        runTest("@modify{abc)", new TestDB() {

            /**
        *      java.lang.String)
             */
            @Override
            public Entry getEntry(String key) {

                return new Entry(null);
            }
        });
    }

    /**
     *  {@code modify} needs to be closed by the same type of brace
     * that opened it.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedException.class)
    public void testModify08() throws Exception {

        runTest("@modify{abc  ", new TestDB() {

            /**
        *      java.lang.String)
             */
            @Override
            public Entry getEntry(String key) {

                return new Entry(null);
            }
        });
    }

    /**
     *  {@code modify} works.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testModify10() throws Exception {

        TestDB db = new TestDB() {

            /**
             * The field {@code entry} contains the entry.
             */
            private final Entry entry = new Entry( null);

            /**
        *      java.lang.String)
             */
            @Override
            public Entry getEntry(String key) {

                return entry;
            }
        };
        runTest("@modify{abc,xyz=123}", db);
        assertEquals("123", db.getEntry("abc").get("xyz").toString());
    }

    /**
     *  {@code modify} works.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testModify11() throws Exception {

        TestDB db = new TestDB() {

            /**
             * The field {@code entry} contains the entry.
             */
            private final Entry entry = new Entry( null);

            /**
        *      java.lang.String)
             */
            @Override
            public Entry getEntry(String key) {

                return entry;
            }
        };
        runTest("@modify{abc,xyz=123,}", db);
        assertEquals("123", db.getEntry("abc").get("xyz").toString());
    }

}
