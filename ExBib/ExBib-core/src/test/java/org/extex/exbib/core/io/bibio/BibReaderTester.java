/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bibio;

import org.extex.exbib.core.bst.exception.ExBibEntryUndefinedException;
import org.extex.exbib.core.bst.exception.ExBibMissingEntryTypeException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.exceptions.*;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * This is an abstract base class for {@link BibReader}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class BibReaderTester {

  /**
   * �Creating an entry is ok.
   */
  protected class EntryMakingTestDB extends TestDB {

    @Override
    public Entry makeEntry( String type, String key, Locator locator ) {

      return new Entry( null );
    }
  }

  /**
   * A database implementation for testing.
   */
  protected class TestDB implements DB {

    public List<Entry> getEntries() {

      return null;
    }

    public Entry getEntry( String key ) {

      return null;
    }

    public String getExpandedMacro( String key ) {

      return null;
    }

    public Value getMacro( String name ) {

      return null;
    }

    public List<String> getMacroNames() {

      return null;
    }

    public int getMinCrossrefs() {

      return 0;
    }

    public Value getPreamble() {

      return null;
    }

    public String getPreambleExpanded() {

      return null;
    }

    public Sorter getSorter() {

      return null;
    }

    public Iterator<Entry> iterator() {

      assertTrue( "unexpected iterator()", false );
      return null;
    }

    public List<String> load( String file, Map<String, String> citation )
        throws ExBibException,
        ConfigurationException,
        FileNotFoundException {

      assertTrue( "unexpected load()", false );
      return null;
    }

    /**
     * java.lang.String, org.extex.exbib.core.io.Locator)
     */
    public Entry makeEntry( String type, String key, Locator locator ) {

      assertTrue( "unexpected makeEntry(" + type + "," + key + ")", false );
      return null;
    }

    /**
     * org.extex.exbib.core.util.Observer)
     */
    public void registerObserver( String name, Observer observer )
        throws NotObservableException {

      assertTrue( "unexpected registerObserver()", false );
    }

    public void setBibReaderFactory( BibReaderFactory factory ) {

      assertTrue( "unexpected setBibReaderFactory()", false );
    }

    public void setMinCrossrefs( int minCrossref ) {

      assertTrue( "unexpected setMinCrossrefs()", false );
    }

    public void setSorter( Sorter sorter ) {

      assertTrue( "unexpected setSorter()", false );
    }

    public void sort() throws ConfigurationException {

      assertTrue( "unexpected sort()", false );
    }

    /**
     * java.lang.String, org.extex.exbib.core.io.Locator)
     */
    public void storeAlias( String alias, String key, Locator locator )
        throws ExBibEntryUndefinedException {

      assertTrue( "unexpected storeAlias()", false );
    }

    public void storeComment( String comment ) {


    }

    public void storePreamble( Value pre ) {

      assertTrue( "unexpected storePreamble()", false );
    }

    /**
     * org.extex.exbib.core.db.Value)
     */
    public void storeString( String name, Value value ) {

      assertTrue( "unexpected storeString()", false );
    }
  }

  /**
   * The field {@code commentSpace} contains the indicator for extra space
   * after {@literal @comment}.
   */
  private final boolean commentSpace;

  /**
   * Creates a new object.
   *
   * @param commentSpace the indicator for extra space after a comment started
   *                     with {@literal @comment}
   */
  public BibReaderTester( boolean commentSpace ) {

    this.commentSpace = commentSpace;
  }

  /**
   * Create a new instance of the BibReader to be tested.
   *
   * @return the test instance
   */
  protected abstract BibReader makeTestInstance();

  /**
   * Create a {@link BibReader} which read text provided as argument.
   *
   * @param content the content
   * @return the new instance
   * @throws FileNotFoundException  in case of a missing file
   * @throws ConfigurationException in case of a configuration error
   */
  protected BibReader openTestInstance( String content )
      throws FileNotFoundException,
      ConfigurationException {

    BibReader reader = makeTestInstance();
    reader.open( "test", new StringReader( content ) );
    return reader;
  }

  /**
   * A {@code @book} is read in.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testBook1() throws Exception {

    BibReader r = makeTestInstance();
    r.open( "test", new StringReader( "@book{abc,author={S.O. Meone}}" ) );
    TestDB db = new TestDB() {

      /**
       * The field {@code entry} contains the entry.
       */
      private Entry entry = null;

      @Override
      public Entry getEntry( String key ) {

        return entry;
      }

      /**
       *      java.lang.String, org.extex.exbib.core.io.Locator)
       */
      @Override
      public Entry makeEntry( String type, String key, Locator locator ) {

        assertEquals( "book", type );
        assertEquals( "abc", key );
        entry = new Entry( locator );
        entry.setKey( key );
        entry.setType( type );
        return entry;
      }

    };
    r.load( db );
    Entry e = db.getEntry( "abc" );
    assertNotNull( e );
    assertEquals( "{S.O. Meone}", e.get( "author" ).toString() );
  }

  /**
   * The empty input is accepted and does not require anything to be stored
   * in the database
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testComment1() throws Exception {

    BibReader r = makeTestInstance();
    r.open( "test", new StringReader( "" ) );
    r.load( new TestDB() {

      @Override
      public void storeComment( String comment ) {

        assertEquals( "", comment );
      }

    } );
  }

  /**
   * The comments are accepted and stored in the database.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testComment2() throws Exception {

    BibReader r = makeTestInstance();
    r.open( "test", new StringReader( "abc" ) );
    r.load( new TestDB() {

      @Override
      public void storeComment( String comment ) {

        assertEquals( "abc", comment );
      }

    } );
  }

  /**
   * The comments starting with {@literal @comment} are accepted and stored
   * in the database
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testComment3() throws Exception {

    BibReader r = makeTestInstance();
    r.open( "test", new StringReader( "abc @comment xyz" ) );
    r.load( new TestDB() {

      @Override
      public void storeComment( String comment ) {

        assertEquals( "abc @comment xyz", comment );
      }

    } );
  }

  /**
   * The comments starting with {@literal @comment} are accepted and stored
   * in the database
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testComment4() throws Exception {

    BibReader r = makeTestInstance();
    r.open( "test", new StringReader( "abc @comment \nxyz" ) );
    r.load( new TestDB() {

      @Override
      public void storeComment( String comment ) {

        assertEquals( "abc @comment" + (commentSpace ? " " : "")
                          + " xyz", comment );
      }

    } );
  }

  /**
   * Test that a missing key leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingKeyException.class)
  public void testError01() throws Exception {

    openTestInstance( "@book{," ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a missing entry type leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingEntryTypeException.class)
  public void testError02() throws Exception {

    openTestInstance( "@{" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a missing attribute name leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingAttributeNameException.class)
  public void testError03() throws Exception {

    openTestInstance( "@book{abc,=" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that an EOF in a block leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibEofInBlockException.class)
  public void testError04() throws Exception {

    openTestInstance( "@book{abc,x={" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that an EOF in a block leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibEofInBlockException.class)
  public void testError04s() throws Exception {

    openTestInstance( "@book{abc, x={" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that an EOF in a string leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibEofInStringException.class)
  public void testError05() throws Exception {

    openTestInstance( "@book{abc,x=\"" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that an invalid closing brace leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedEofException.class)
  public void testError06() throws Exception {

    openTestInstance( "@book{abc,x={})" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that an EOF before an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibEofException.class)
  public void testError07() throws Exception {

    openTestInstance( "@book{abc,x=" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that an EOF before an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibEofException.class)
  public void testError07s() throws Exception {

    openTestInstance( "@book{abc,x= " ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a # instead of an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedException.class)
  public void testError08() throws Exception {

    openTestInstance( "@book{abc,x=#" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a # instead of an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedException.class)
  public void testError08a() throws Exception {

    openTestInstance( "@book{abc,x=(" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a # instead of an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedException.class)
  public void testError08b() throws Exception {

    openTestInstance( "@book{abc,x=}" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a # instead of an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedException.class)
  public void testError08c() throws Exception {

    openTestInstance( "@book{abc,x==" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a # instead of an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedException.class)
  public void testError08d() throws Exception {

    openTestInstance( "@book{abc,x=)" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a # instead of an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedEofException.class)
  public void testError09() throws Exception {

    openTestInstance( "@book{abc,x=123" ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that a # instead of an attribute value leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedEofException.class)
  public void testError09s() throws Exception {

    openTestInstance( "@book{abc,x=123 " ).load( new EntryMakingTestDB() );
  }

  /**
   * Test that an EOF after an assignment leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibUnexpectedEofException.class)
  public void testError10() throws Exception {

    openTestInstance( "@book{abc,x = a " ).load( new EntryMakingTestDB() );
  }

}
