/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.parser.makeindex;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.reader.ReaderLocator;
import org.extex.exindex.core.parser.reader.TeXReader;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.type.raw.CloseLocationReference;
import org.extex.exindex.core.type.raw.LocationReference;
import org.extex.exindex.core.type.raw.OpenLocationReference;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * This parser is a reader for input in the form of the  makeindex
 * format and some extensions of it.
 *
 *
 * <h2>The  makeindex Raw Index Format</h2>
 *
 * <p>
 * The raw index format for  makeindex is used to parse the input
 * and acquire index data.  makeindex is rather general. The parser
 * is highly configurable. A general scheme is used. The characters involved can
 * be adjusted in the configuration. The general scheme is the following:
 * </p>
 *
 * <pre>
 *  <b><i>makeindex:keyword</i></b>
 *    <b><i>makeindex:arg-open</i></b>
 *     [<i>main-key</i>
 *      [<b><i>makeindex:level</i></b> <i>main-key-2</i>
 *       [<b><i>makeindex:level</i></b> <i>main-key-3</i> ] ]
 *      <b><i>makeindex:actual</i></b> ]
 *     <i>print-key</i>
 *     [<b><i>makeindex:encap</i></b> <i>encap</i>
 *      [ | <b><i>makeindex:range-open</i></b> | <b><i>makeindex:range-close</i></b> ]
 *     ]
 *    <b><i>makeindex:arg-close</i></b>
 *    <b><i>makeindex:arg-open</i></b>
 *     <i>page-ref</i>
 *    <b><i>makeindex:arg-close</i></b>   </pre>
 *
 * <p>
 * In the description above the elements in bold and italic indicate the
 * parameters for the parsing. The elements in italic only are the variable
 * parts containing the data of the entry.
 * </p>
 *
 * <p>The Parameters</p>
 *
 * <p>
 * The parsing of raw index entries in the  makeindex format can be
 * controlled by a set of parameters. This makes the parser adaptable to a wider
 * range of applications.
 * </p>
 * <p>
 * The original need to introduce the parameters is the flexibility of
 * TeX. In TeX the category codes of characters can be redefined. Thus
 * makeindex needs to be able to adjust its behavior to cope with
 * such a situation.
 * </p>
 * <p>
 * The following table shows the parameters with their
 * &epsilon;&chi;Index name, its  makeindex name and
 * the default value.
 * </p>
 *
 * <table>
 * <caption>TBD</caption>
 *
 * <tr>
 * <th> &epsilon;&chi;Index parameter</th>
 * <th> makeindex parameter</th>
 * <th>Fallback</th>
 * <th>Type</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>makeindex:keyword</td>
 * <td>keyword</td>
 * <td>{@code \indexentry}</td>
 * <td>string</td>
 * <td>This string starts an entry. Anything between entries is ignored.</td>
 * </tr>
 * <tr>
 * <td>makeindex:arg-open</td>
 * <td>arg_open</td>
 * <td>{@code {}</td>
 * <td>character</td>
 * <td>This character opens an argument. It is terminated by the arg-close
 * character.</td>
 * </tr>
 * <tr>
 * <td>makeindex:arg-close</td>
 * <td>arg_close</td>
 * <td>{@code }}</td>
 * <td>character</td>
 * <td>This character closes an argument which has been opened by the arg-open
 * character. The arg-open and arg-close characters have to be balanced before
 * the closing takes place. Any arg-open or arg-close character preceded by an
 * quote character is not counted.</td>
 * </tr>
 * <tr>
 * <td>makeindex:range-open</td>
 * <td>range_open</td>
 * <td>{@code (}</td>
 * <td>character</td>
 * <td>This character indicates the start of a range.</td>
 * </tr>
 * <tr>
 * <td>makeindex:range-close</td>
 * <td>range_close</td>
 * <td>{@code )}</td>
 * <td>character</td>
 * <td>This character indicates the end of a range.</td>
 * </tr>
 * <tr>
 * <td>makeindex:escape</td>
 * <td>escape</td>
 * <td>{@code "}</td>
 * <td>character</td>
 * <td>This is the escape character.</td>
 * </tr>
 * <tr>
 * <td>makeindex:quote</td>
 * <td>quote</td>
 * <td>{@code \}</td>
 * <td>character</td>
 * <td>This is the quote character</td>
 * </tr>
 * <tr>
 * <td>makeindex:encap</td>
 * <td>encap</td>
 * <td>{@code |}</td>
 * <td>character</td>
 * <td>This character is the separator for the encapsulator.</td>
 * </tr>
 * <tr>
 * <td>makeindex:level</td>
 * <td>level</td>
 * <td>{@code !}</td>
 * <td>character</td>
 * <td>This character is the level separator. A structured key is divided into
 * parts with this character.</td>
 * </tr>
 * <tr>
 * <td>makeindex:actual</td>
 * <td>actual</td>
 * <td>{@code @}</td>
 * <td>character</td>
 * <td>The actual character, separating the print representation from the key
 * .</td>
 * </tr>
 * <tr>
 * <td>makeindex:encap-prefix</td>
 * <td>encap_prefix</td>
 * <td>{@code \\}</td>
 * <td>string</td>
 * <td>The string to be inserted before encapsulations</td>
 * </tr>
 * <tr>
 * <td>makeindex:encap-infix</td>
 * <td>encap_infix</td>
 * <td>{@code {}</td>
 * <td>string</td>
 * <td>The string to be inserted between encapsulations</td>
 * </tr>
 * <tr>
 * <td>makeindex:encap-suffix</td>
 * <td>encap_suffix</td>
 * <td>{@code }}</td>
 * <td>string</td>
 * <td>The string to be inserted after encapsulations</td>
 * </tr>
 * </table>
 *
 * <p>
 * The following example shows how the default setting can be defined in a
 *  &epsilon;&chi;Index style file.
 * </p>
 *
 * <pre>
 *  (setq makeindex:keyword "\\indexentry")
 *  (setq makeindex:arg-open #\{)
 *  (setq makeindex:arg-close #\})
 *  (setq makeindex:range-open #\()
 *  (setq makeindex:range-close #\))
 *  (setq makeindex:escape #\")
 *  (setq makeindex:quote #\\)
 *  (setq makeindex:encap #\|)
 *  (setq makeindex:level #\!)
 *  (setq makeindex:actual #\@)
 *  (setq makeindex:encap-prefix "\\")
 *  (setq makeindex:encap-infix "{")
 *  (setq makeindex:encap-suffix "}")   </pre>
 *
 * <p>Examples of Index Entries</p>
 *
 * <p>
 * The following examples illustrate the index entries understood by the
 *  makeindex raw index format.
 * </p>
 *
 * <pre>
 * \indexentry{abc}{123}   </pre>
 *
 * <p>
 * This example is the simple case of a main key {@code abc} and the page
 * reference {@code 123}.
 * </p>
 *
 * <pre>
 * \indexentry{alpha@$\alpha$}{123}   </pre>
 *
 * <p>
 * This example sorts as {@code alpha} and prints as &alpha;.
 * </p>
 *
 * <pre>
 * \indexentry{alpha@$\alpha$|textbf(}{123}   </pre>
 *
 * <p>
 * This example shows an entry with the attribute {@code textbf} which is
 * started here.
 * </p>
 *
 * <pre>
 * \indexentry{a!b!c}{123}   </pre>
 *
 * <p>
 * This example shows a structured entry with the structuring a &rarr; b &rarr;
 * c.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MakeindexParser implements RawIndexParser {

  /**
   * The field {@code keyword} contains the keyword for an index entry.
   */
  private String keyword;

  /**
   * The field {@code argOpen} contains the argument open character.
   */
  private char argOpen;

  /**
   * The field {@code argClose} contains the argument close character.
   */
  private char argClose;

  /**
   * The field {@code escape} contains the escape character.
   */
  private char escape;

  /**
   * The field {@code quote} contains the quote character.
   */
  private char quote;

  /**
   * The field {@code encap} contains the encapsulation separator.
   */
  private char encap;

  /**
   * The field {@code level} contains the level separator.
   */
  private char level;

  /**
   * The field {@code actual} contains the actual separator.
   */
  private char actual;

  /**
   * The field {@code rangeOpen} contains the range open separator.
   */
  private char rangeOpen;

  /**
   * The field {@code rangeClose} contains the range close separator.
   */
  private char rangeClose;

  /**
   * The field {@code locator} contains the locator.
   */
  private ReaderLocator locator;

  /**
   * The field {@code index} contains the name of the index.
   */
  private final String index = "";

  /**
   * The field {@code encapPrefix} contains the encapsulation prefix.
   */
  private String encapPrefix;

  /**
   * The field {@code encapInfix} contains the encapsulation infix.
   */
  private String encapInfix;

  /**
   * The field {@code encapSuffix} contains the encapsulation suffix.
   */
  private String encapSuffix;

  /**
   * The field {@code indexer} contains the indexer.
   */
  private final Indexer indexer;

  /**
   * Creates a new object and gather the parameters from an interpreter.
   *
   * @param reader   the source to read from
   * @param resource the name of the resource for error messages
   * @param indexer  the l system as storage for parameters
   * @throws RawIndexException in case of an error
   * @see #configure(LInterpreter)
   */
  public MakeindexParser( Reader reader, String resource, Indexer indexer )
      throws RawIndexException {

    this.locator = new TeXReader( resource, reader );
    this.indexer = indexer;
    configure( indexer );
  }

  public void close() throws IOException {

    if( locator != null ) {
      locator.close();
      locator = null;
    }
  }

  /**
   * Gather the parameters from an interpreter. If the interpreter does not
   * have an appropriate value then a fallback is used. The following
   * parameters are used by this parser: <br>
   * <table>
   * <caption>TBD</caption>
   * <tr>
   * <th>Name</th>
   * <th>Fallback</th>
   * <th>Description</th>
   * </tr>
   * <tr>
   * <td>makeindex:keyword</td>
   * <td>{@code \indexentry}</td>
   * <td>This string starts an entry. Anything between entries is ignored
   * .</td>
   * </tr>
   * <tr>
   * <td>makeindex:arg-open</td>
   * <td>{@code {}</td>
   * <td>This character opens an argument. It is terminated by the arg-close
   * character.</td>
   * </tr>
   * <tr>
   * <td>makeindex:arg-close</td>
   * <td>{@code }}</td>
   * <td>This character closes an argument which has been opened by the
   * arg-open character.</td>
   * </tr>
   * <tr>
   * <td>makeindex:range-open</td>
   * <td>{@code (}</td>
   * <td>This character indicates the start of a range.</td>
   * </tr>
   * <tr>
   * <td>makeindex:range-close</td>
   * <td>{@code )}</td>
   * <td>This character indicates the end of a range.</td>
   * </tr>
   * <tr>
   * <td>makeindex:escape</td>
   * <td>{@code "}</td>
   * <td>This is the escape character.</td>
   * </tr>
   * <tr>
   * <td>makeindex:quote</td>
   * <td>{@code \}</td>
   * <td>This is the quote character</td>
   * </tr>
   * <tr>
   * <td>makeindex:encap</td>
   * <td>{@code |}</td>
   * <td>This character is the separator for the encapsulator.</td>
   * </tr>
   * <tr>
   * <td>makeindex:level</td>
   * <td>{@code !}</td>
   * <td>This character is the level separator. A structured key is divided
   * into parts with this character.</td>
   * </tr>
   * <tr>
   * <td>makeindex:actual</td>
   * <td>{@code @}</td>
   * <td>The actual character, separating the print representation from the
   * key.</td>
   * </tr>
   * <tr>
   * <td>makeindex:encap-prefix</td>
   * <td>{@code \\}</td>
   * <td>The string to be inserted before encapsulations</td>
   * </tr>
   * <tr>
   * <td>makeindex:encap-infix</td>
   * <td>{@code {}</td>
   * <td>The string to be inserted between encapsulations</td>
   * </tr>
   * <tr>
   * <td>makeindex:encap-suffix</td>
   * <td>{@code }}</td>
   * <td>The string to be inserted after encapsulations</td>
   * </tr>
   * </table>
   *
   * @param interpreter the interpreter to query for the parameters
   * @throws RawIndexException in case that the value from the interpreter has
   *                           the wrong type
   */
  protected void configure( LInterpreter interpreter )
      throws RawIndexException {

    keyword = initString( "makeindex:keyword", interpreter, "\\indexentry" );
    argOpen = initChar( "makeindex:arg-open", interpreter, '{' );
    argClose = initChar( "makeindex:arg-close", interpreter, '}' );
    rangeOpen = initChar( "makeindex:range-open", interpreter, '(' );
    rangeClose = initChar( "makeindex:range-close", interpreter, ')' );
    escape = initChar( "makeindex:escape", interpreter, '"' );
    quote = initChar( "makeindex:quote", interpreter, '\\' );
    encap = initChar( "makeindex:encap", interpreter, '|' );
    level = initChar( "makeindex:level", interpreter, '!' );
    actual = initChar( "makeindex:actual", interpreter, '@' );

    encapPrefix = initString( "makeindex:encap_prefix", indexer, "\\" );
    encapInfix = initString( "makeindex:encap_infix", indexer, "{" );
    encapSuffix = initString( "makeindex:encap_sufffix", indexer, "}" );
  }

  /**
   * Check that the next character from the stream has a certain value.
   *
   * @param ec the expected character
   * @throws RawIndexMissingCharException in case of an error
   * @throws IOException                  in case of an error
   */
  protected void expect( char ec )
      throws IOException,
      RawIndexMissingCharException {

    int c = locator.read();
    if( c != ec ) {
      throw new RawIndexMissingCharException( locator, (char) c, ec );
    }
  }

  /**
   * Store an initial character in the interpreter if none is present.
   *
   * @param var         the variable name
   * @param interpreter the interpreter
   * @param value       the default value
   * @return the character to use
   * @throws RawIndexException in case of an error
   */
  protected char initChar( String var, LInterpreter interpreter, char value )
      throws RawIndexException {

    LValue v = interpreter.get( var );
    if( v == null ) {
      return value;
    }
    else if( v instanceof LChar ) {
      return ((LChar) v).getValue();
    }

    throw new RawIndexException( locator,
                                 LocalizerFactory.getLocalizer( getClass() )
                                                 .format( "CharExpected",
                                                          v.toString() ) );

  }

  /**
   * Store an initial String in the interpreter if none is present.
   *
   * @param var         the variable name
   * @param interpreter the interpreter
   * @param value       the default value
   * @return the string to use
   * @throws RawIndexException in case of an error
   */
  private String initString( String var, LInterpreter interpreter,
                             String value )
      throws RawIndexException {

    LValue v = interpreter.get( var );
    if( v == null ) {
      return value;
    }
    else if( v instanceof LString ) {
      return ((LString) v).getValue();
    }

    throw new RawIndexException( locator,
                                 LocalizerFactory.getLocalizer( getClass() )
                                                 .format( "StringExpected",
                                                          v.toString() ) );

  }

  /**
   * Create an entry.
   *
   * @param idx      the name of the index
   * @param keyArg   the argument
   * @param printArg the print representation
   * @param locref   the page
   * @param enc      the encapsulation
   * @return the new index entry
   */
  protected RawIndexentry makeEntry( String idx, String keyArg,
                                     String printArg, String locref,
                                     String enc ) {

    List<String> list = new ArrayList<String>();
    int a = 0;
    for( int i = keyArg.indexOf( level, a ); i >= 0; i =
        keyArg.indexOf( level, a ) ) {
      list.add( keyArg.substring( a, i ) );
      a = i + 1;
    }
    list.add( keyArg.substring( a ) );
    int size = list.size();
    String[] key = new String[ size ];
    String[] print = new String[ size ];
    int i = 0;
    for( String s : list ) {
      print[ i ] = s;
      key[ i++ ] = s;
    }
    if( printArg != null ) {
      print[ print.length - 1 ] = printArg;
    }

    LocationReference ref;
    String attr = enc;
    if( attr == null || "".equals( attr ) ) {
      ref = new LocationReference( attr, locref );
      attr = null;
    }
    else {
      final int last = attr.length() - 1;
      char c = attr.charAt( last );
      if( c == rangeOpen ) {
        ref = new OpenLocationReference( attr.substring( 0, last ),
                                         locref );
      }
      else if( c == rangeClose ) {
        ref = new CloseLocationReference( attr.substring( 0, last ),
                                          locref );
      }
      else {
        ref = new LocationReference( attr, locref );
      }
    }
    RawIndexentry entry = new RawIndexentry( idx, key, print, ref );
    return entry;
  }

  public RawIndexentry parse() throws RawIndexException, IOException {

    if( locator == null ) {
      return null;
    }

    char k0 = keyword.charAt( 0 );
    for( int c = locator.read(); c >= 0; c = locator.read() ) {
      if( c == k0 && scanKeyword( keyword ) ) {
        String idx = scanIndex( locator, index );
        String arg = scanArgument();
        String pageref = scanArgument();
        String enc = null;
        int i = arg.lastIndexOf( encap );
        if( i >= 0 ) {
          enc = arg.substring( i + 1 );
          arg = arg.substring( 0, i );
        }
        i = arg.indexOf( actual );
        if( i >= 0 ) {
          return store( idx, arg.substring( 0, i ),
                        arg.substring( i + 1 ), pageref, enc );
        }
        return store( idx, arg, null, pageref, enc );
      }
    }
    return null;
  }

  /**
   * Scan an argument enclosed in {@code argOpen} and {@code argClose}.
   *
   * @return the argument found
   * @throws IOException                  in case of an error
   * @throws RawIndexEofException         in case of an unexpected EOF
   * @throws RawIndexMissingCharException in case of an error
   */
  protected String scanArgument()
      throws IOException,
      RawIndexEofException,
      RawIndexMissingCharException {

    expect( argOpen );
    StringBuilder sb = new StringBuilder();
    int level = 1;

    for( ; ; ) {
      int c = locator.read();
      if( c == argOpen ) {
        level++;
      }
      else if( c == argClose ) {
        level--;
        if( level <= 0 ) {
          return sb.toString();
        }
      }
      else if( c == quote ) {
        int l = sb.length();
        if( l <= 0 || sb.charAt( l - 1 ) != escape ) {
          sb.append( (char) c );
          c = locator.read();
          if( c < 0 ) {
            throw new RawIndexEofException( locator, argClose );
          }
        }
      }
      else if( c < 0 ) {
        throw new RawIndexEofException( locator, argClose );
      }
      sb.append( (char) c );
    }
  }

  /**
   * This method is an extension point to acquire the index to use.
   *
   * @param loc      the locator
   * @param fallback the default index
   * @return the index to use
   * @throws RawIndexMissingCharException in case of an error
   * @throws RawIndexEofException         in case of an error
   * @throws IOException                  in case of an I/O error
   */
  protected String scanIndex( ReaderLocator loc, String fallback )
      throws IOException,
      RawIndexEofException,
      RawIndexMissingCharException {

    return fallback;
  }

  /**
   * Scan a keyword.
   *
   * @param keyword the keyword to read
   * @return {@code true} iff the keyword has been found
   * @throws IOException in case of an I/O error
   */
  protected boolean scanKeyword( String keyword ) throws IOException {

    int length = keyword.length();
    for( int i = 1; i < length; i++ ) {
      if( locator.read() != keyword.charAt( i ) ) {
        return false;
      }
    }
    return true;
  }

  /**
   * Create an object consisting of the required parts. The parameters are put
   * in place and the index entry is returned.
   *
   * @param idx      the name of the index
   * @param keyArg   the argument
   * @param printArg the print representation
   * @param locref   the page
   * @param enc      the encapsulation
   * @return the new index entry
   */
  protected RawIndexentry store( String idx, String keyArg, String printArg,
                                 String locref, String enc ) {

    RawIndexentry entry = makeEntry( idx, keyArg, printArg, locref, enc );

    String layer = entry.getRef().getLayer();
    if( layer == null ) {
      return entry;
    }
    StructuredIndex index = indexer.getContainer().get( entry.getIndex() );
    if( index == null ) {
      return entry;
    }
    Markup markup = index.getMarkup( "markup-locref-layer" );
    if( markup == null ) {
      return entry;
    }
    if( "".equals( markup.get( layer, Markup.OPEN ) )
        && "".equals( markup.get( layer, Markup.CLOSE ) ) ) {
      markup.set( layer, encapPrefix + layer + encapInfix, encapSuffix );
    }

    return entry;
  }

}
