/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.parser.xindy;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexSyntaxException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.reader.ReaderLocator;
import org.extex.exindex.core.type.raw.*;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.parser.LParser;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * This parser is a reader for input in the form of the xindy raw index format
 * and some extensions of it.
 *
 *
 * <h2>The Extended xindy Raw Index Format</h2>
 * <p>
 * The basic syntax is the Lisp syntax with the backslash character as escape
 * character in strings. Semicolon initiates an end-line comment.
 * </p>
 *
 * <pre>
 * (indexentry [:index <i>index-name</i> | ]
 *             { :key [<i>string</i> | <i>string-list</i>]
 *               [:print [<i>string</i> | <i>string-list</i>]]
 *             | :tkey <i>list-of-layers</i> }
 *             [:attr <i>string</i>]
 *             { :locref [<i>string</i> | <i>string-list</i>]  [:open-range | :close-range]
 *             | :xref [<i>string</i> | <i>string-list</i>] } )   </pre>
 *
 * <p>
 * The index entry is in principal made up of four parts: the name of the index,
 * the key, the attribute, and the location. They are described below.
 * </p>
 *
 * <p>The Index</p>
 * <p>
 * The index is the name of an index. The index entry is assigned to this index.
 * The default is the index {@code default}.
 * </p>
 * <p>
 * The index is an extension compared to the xindy raw index format.
 * </p>
 *
 * <p>The Key</p>
 * <p>
 * The key consists of a pair made up of a list of sort key and print
 * representations. There are several ways to specify the key.
 * </p>
 * <p>
 * The key can be given with the {@code :key} flag. The argument following it
 * is a list of strings to be stored as sort key. If nothing else is specified
 * then the argument list is also stored as print representation. This is shown
 * in the following example.
 * </p>
 *
 * <pre>
 *  (indexentry :key ("abc") :locref "123")   </pre>
 *
 * <p>
 * If just a single string is given after the {@code :key} flag is treated as
 * an abbreviation for a single element list containing it. This is an extension
 * of the xindy format.
 * </p>
 *
 * <pre>
 *  (indexentry :key "abc" :locref "123")   </pre>
 *
 *
 * <p>
 * The key can be augmented with a different print representation which is given
 * with the flag {@code :print}. This can be seen in the following example.
 * </p>
 *
 * <pre>
 *  (indexentry :key ("alpha") :print ("$\alpha$") :locref "123")   </pre>
 *
 * <p>
 * Again the value of the {@code :print} flag can be a single sting which
 * serves as an abbreviation for the singleton list containing it. This is an
 * extension of the xindy format.
 * </p>
 *
 * <pre>
 *  (indexentry :key "alpha" :print "$\alpha$" :locref "123")   </pre>
 *
 * <p>
 * Finally the flag {@code :tkey} can be used to specify the sort key and the
 * print value jointly. The argument is a list. This list can contain a list of
 * one or two elements. If only one element is given then this value &ndash;
 * which has to be a string &ndash; is used for both sort key and print
 * representation at this level. If two elements are given then the two elements
 * are the sort key and the print value in this order.
 * </p>
 *
 * <pre>
 *  (indexentry :tkey (("symbol") ("alpha" "$\alpha$")) :locref "123")   </pre>
 *
 * <p>
 * The elements of the argument list of {@code :tkey} can also be string in
 * this case it is used both for sort key and print value. This is an extension
 * of the xindy format.
 * </p>
 *
 * <p>The Attribute</p>
 * <p>
 * The attribute is an optional value which classifies the location. It is used
 * during markup to tag the location.
 * </p>
 * <p>
 * The value of the attribute is a string which is used to select the markup.
 * </p>
 *
 * <p>The Reference</p>
 * <p>
 * The reference is either a location reference or a cross reference.
 * </p>
 * <p>
 * The location reference takes as a parameter a single string containing the
 * value of the reference &ndash; e.g. the page number.
 * </p>
 *
 * <pre>
 *  (indexentry :key ("alpha" "$\alpha$") :locref "A-123")   </pre>
 *
 * <p>
 * In addition the flags {@code :open-range} and {@code :close-range} can be
 * used to signal that the entry corresponds to a range and is the beginning or
 * the end of such a range. Not both of them can be given in one entry.
 * </p>
 * <p>
 * The other type of reference is a cross reference. It refers to another entry
 * in the index. The flag {@code :xref} can be used to specify a cross
 * reference. The argument is a list of strings denoting the referenced entry.
 * </p>
 *
 * <pre>
 *  (indexentry :key ("alpha" "$\alpha$") :xref ("greek"))   </pre>
 *
 * <p>
 * As above an extension of the xindy format can be used by abbreviating a
 * singleton list as argument to the flag {@code :xref} by a single string.
 * </p>
 *
 * <pre>
 *  (indexentry :key ("alpha" "$\alpha$") :xref "greek")   </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class XindyParser extends LParser implements RawIndexParser {

  /**
   * The constant {@code ATTR} contains the symbol for the attr flag.
   */
  private static final LSymbol ATTR = LSymbol.get( ":attr" );

  /**
   * The constant {@code CLOSE_RANGE} contains the symbol for the close-range
   * flag.
   */
  private static final LSymbol CLOSE_RANGE = LSymbol.get( ":close-range" );

  /**
   * The constant {@code INDEX} contains the symbol for the index flag.
   */
  private static final LSymbol INDEX = LSymbol.get( ":index" );

  /**
   * The constant {@code INDEXENTRY} contains the symbol for the index entry
   * head.
   */
  private static final LSymbol INDEXENTRY = LSymbol.get( "indexentry" );

  /**
   * The constant {@code KEY} contains the symbol for the key flag.
   */
  private static final LSymbol KEY = LSymbol.get( ":key" );

  /**
   * The constant {@code LOCREF} contains the symbol for the locref flag.
   */
  private static final LSymbol LOCREF = LSymbol.get( ":locref" );

  /**
   * The constant {@code OPEN_RANGE} contains the symbol for the open-range
   * flag.
   */
  private static final LSymbol OPEN_RANGE = LSymbol.get( ":open-range" );

  /**
   * The constant {@code PRINT} contains the symbol for the print flag.
   */
  private static final LSymbol PRINT = LSymbol.get( ":print" );

  /**
   * The constant {@code TKEY} contains the symbol for the tkey flag.
   */
  private static final LSymbol TKEY = LSymbol.get( ":tkey" );

  /**
   * The constant {@code XREF} contains the symbol for the xref flag.
   */
  private static final LSymbol XREF = LSymbol.get( ":xref" );

  /**
   * The field {@code locator} contains the locator.
   */
  private final ReaderLocator locator;

  /**
   * Creates a new object.
   *
   * @param reader   the source to read from
   * @param resource the name of the resource for error messages
   * @param indexer  the indexer
   */
  public XindyParser( Reader reader, String resource, Indexer indexer ) {

    super( reader, resource );
    locator = new ReaderLocator( resource, reader );
  }

  /**
   * Creates a new object.
   *
   * @param reader      the source to read from
   * @param resource    the name of the resource for error messages
   * @param interpreter the interpreter
   */
  public XindyParser( Reader reader, String resource,
                      LInterpreter interpreter ) {

    super( reader, resource );
    locator = new ReaderLocator( resource, reader );
  }

  /**
   * Check that the argument is a LList and cast it.
   *
   * @param value the value
   * @param tag   the name of the argument or {@code null}
   * @return the list
   * @throws RawIndexSyntaxException in case of an error
   */
  private LList assumeList( LValue value, LSymbol tag )
      throws RawIndexSyntaxException {

    if( !(value instanceof LList) ) {
      throw new RawIndexSyntaxException( new ReaderLocator( getResource(),
                                                            getLineNumber() ),
                                         makeErrorMessage( tag != null
                                                               ? 
                                                               "MissingListForTag"
                                                               : "MissingList",
                                                           value,
                                                           tag ) );
    }
    return (LList) value;
  }

  /**
   * Check that the given value is an LString.
   *
   * @param arg the value
   * @param tag the name of the argument
   * @return the string contained
   * @throws RawIndexSyntaxException in case of an error
   */
  private String assumeString( LValue arg, LSymbol tag )
      throws RawIndexSyntaxException {

    if( !(arg instanceof LString) ) {
      throw new RawIndexSyntaxException( locator, makeErrorMessage(
          "MissingString", tag, arg ) );
    }
    return ((LString) arg).getValue();
  }

  /**
   * Check that a value is {@code null} and otherwise raise an
   * appropriate exception.
   *
   * @param value the value
   * @param type  the flag
   * @throws RawIndexException in case of an error
   */
  private void assumeUnbound( Object value, LSymbol type )
      throws RawIndexException {

    if( value != null ) {
      throw new RawIndexException( locator, makeErrorMessage( "BoundTwice",
                                                              type ) );
    }
  }

  /**
   * Translate an LString to a String array.
   *
   * @param tag   the parameter name for error messages
   * @param var   the variable to check for not {@code null}
   * @param value the list
   * @return the String array
   * @throws RawIndexException in case of an error
   */
  private String bindableString( LSymbol tag, Object var, LValue value )
      throws RawIndexException {

    if( value == LList.NIL ) {
      throw new RawIndexSyntaxException( locator, makeErrorMessage(
          "MissingArgument", tag ) );
    }

    assumeUnbound( var, tag );
    return assumeString( value, tag );
  }

  /**
   * Translate a list of strings to a String array. As convenience a single
   * sting is interpreted as a one element list.
   *
   * @param tag   the parameter name for error messages
   * @param var   the variable to check for not {@code null}
   * @param value the list
   * @return the String array
   * @throws RawIndexException in case of an error
   */
  private String[] bindableStringList( LSymbol tag, Object var, LValue value )
      throws RawIndexException {

    if( value == LList.NIL ) {
      throw new RawIndexSyntaxException( locator, makeErrorMessage(
          "MissingArgument", tag ) );
    }

    assumeUnbound( var, tag );

    if( value instanceof LString ) {
      return new String[]{((LString) value).getValue()};
    }

    LList list = assumeList( value, tag );
    int size = list.size();
    String[] array = new String[ size ];

    for( int i = 0; i < size; i++ ) {
      array[ i ] = assumeString( list.get( i ), tag );
    }

    return array;
  }

  /**
   * Use the resource bundle to construct a message for an exception.
   *
   * @param tag  the name of the resource string to use
   * @param args the arguments
   * @return the message
   */
  private String makeErrorMessage( String tag, Object... args ) {

    return LocalizerFactory.getLocalizer( getClass() ).format( tag, args );
  }

  public RawIndexentry parse() throws IOException, RawIndexException {

    LValue term = read();
    if( term == null ) {
      return null;
    }

    String[] key = null;
    String[] print = null;
    String[] locref = null;
    String index = null;
    String attr = null;
    Reference ref = null;
    Boolean openRange = null;
    Boolean closeRange = null;
    String[] xref = null;
    LList list = assumeList( term, null );
    int size = list.size();

    if( size == 0 ) {
      throw new RawIndexSyntaxException( locator,
                                         makeErrorMessage( "IndexEntryMissing"
                                         ) );
    }
    else if( list.get( 0 ) != INDEXENTRY ) {
      throw new RawIndexSyntaxException( locator,
                                         makeErrorMessage( "IndexEntryExpected",
                                                           list.get( 0 )
                                                               .toString() ) );
    }

    for( int i = 1; i < size; i++ ) {
      LValue arg = list.get( i );

      if( arg == KEY ) {
        key = bindableStringList( KEY, key, list.get( ++i ) );

      }
      else if( arg == PRINT ) {
        print = bindableStringList( PRINT, print, list.get( ++i ) );

      }
      else if( arg == TKEY ) {
        if( i >= size - 1 ) {
          throw new RawIndexSyntaxException( locator,
                                             makeErrorMessage( 
                                                 "MissingArgument",
                                                               TKEY ) );
        }
        assumeUnbound( key, TKEY );
        assumeUnbound( print, TKEY );
        LList lst = assumeList( list.get( ++i ), TKEY );
        int len = lst.size();
        key = new String[ len ];
        print = new String[ len ];

        for( int j = 0; j < len; j++ ) {
          LValue val = lst.get( j );
          if( val instanceof LList ) {
            LList ll = (LList) val;
            switch( ll.size() ) {
              case 1:
                key[ j ] =
                    print[ j ] =
                        assumeString( ll.get( 0 ), TKEY );
                break;
              case 2:
                key[ j ] = assumeString( ll.get( 0 ), TKEY );
                print[ j ] = assumeString( ll.get( 1 ), TKEY );
                break;
              default:
                throw new RawIndexSyntaxException( locator,
                                                   makeErrorMessage(
                                                       "WrongNumberOfArguments",
                                                       TKEY,
                                                       ll ) );
            }

          }
          else if( val instanceof LString ) {
            key[ j ] = print[ j ] = ((LString) val).getValue();
          }
          else {
            throw new RawIndexSyntaxException( locator,
                                               makeErrorMessage(
                                                   "MissingStringOrStringList",
                                                   TKEY ) );
          }
        }

      }
      else if( arg == LOCREF ) {
        locref = bindableStringList( LOCREF, locref, list.get( ++i ) );
        assumeUnbound( xref, XREF );

      }
      else if( arg == XREF ) {
        xref = bindableStringList( XREF, xref, list.get( ++i ) );
        assumeUnbound( locref, XREF );

      }
      else if( arg == ATTR ) {
        attr = bindableString( ATTR, attr, list.get( ++i ) );

      }
      else if( arg == OPEN_RANGE ) {
        assumeUnbound( openRange, OPEN_RANGE );
        openRange = Boolean.TRUE;

      }
      else if( arg == CLOSE_RANGE ) {
        assumeUnbound( closeRange, CLOSE_RANGE );
        closeRange = Boolean.TRUE;

      }
      else if( arg == INDEX ) {
        index = bindableString( INDEX, index, list.get( ++i ) );

      }
      else {
        throw new RawIndexException( locator, makeErrorMessage(
            "MissingTag", arg ) );
      }
    }

    if( key == null ) {
      throw new RawIndexException( locator, makeErrorMessage( "Unbound",
                                                              KEY ) );
    }
    if( print == null ) {
      print = key;
    }

    if( xref != null ) {
      if( openRange != null ) {
        throw new RawIndexException( locator, makeErrorMessage(
            "IncompatibleArgs", XREF, OPEN_RANGE ) );
      }
      else if( closeRange != null ) {
        throw new RawIndexException( locator, makeErrorMessage(
            "IncompatibleArgs", XREF, CLOSE_RANGE ) );
      }
      // delayed since attr might not be given before
      ref = new CrossReference( attr, xref );
    }

    if( locref != null ) {
      if( openRange != null ) {
        if( closeRange != null ) {
          throw new RawIndexException( locator, makeErrorMessage(
              "IncompatibleArgs", OPEN_RANGE, CLOSE_RANGE ) );
        }
        ref = new OpenLocationReference( attr, locref );
      }
      else if( closeRange != null ) {
        ref = new CloseLocationReference( attr, locref );
      }
      else {
        ref = new LocationReference( attr, locref );
      }
    }

    if( ref == null ) {
      throw new RawIndexException( locator, makeErrorMessage( "Unbound",
                                                              LOCREF ) );
    }
    return new RawIndexentry( index == null ? "" : index, key, print, ref );
  }

}
