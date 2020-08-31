/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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
import org.extex.exindex.core.parser.reader.ReaderLocator;
import org.extex.exindex.lisp.LInterpreter;

import java.io.IOException;
import java.io.Reader;

/**
 * This parser is a reader for input in the form of the splitindex format and
 * some extensions of it.
 *
 *
 * <h2>The  splitindex Raw Index Format</h2>
 *
 * <p>
 * The raw index format for  splitindex is used to parse the input
 * and acquire index data.  splitindex is an extension of
 * makeindex. It has additionally an optional argument for the
 * index. This makes it possible to have entries for several indices in one
 * file.
 * </p>
 * <p>
 * The parser is highly configurable. A general scheme is used. The characters
 * involved can be adjusted in the configuration. The general scheme is the
 * following:
 * </p>
 *
 * <pre>
 *  <b><i>makeindex:keyword</i></b>
 *    [<b><i>makeindex:index-open</i></b> <i>index-name</i> <b><i>makeindex:index-close</i></b>]
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
 * <p>
 * Note that the index has to be defined before it can receive data. Entries
 * sent to an undefined index are dropped and will not show up in an output.
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
 * <td>makeindex:index-open</td>
 * <td></td>
 * <td>{@code [}</td>
 * <td>character</td>
 * <td>This character opens the optional argument containing the index name. It
 * is terminated by the index-close character.</td>
 * </tr>
 * <tr>
 * <td>makeindex:index-close</td>
 * <td></td>
 * <td>{@code ]}</td>
 * <td>character</td>
 * <td>This character closes an argument which has been opened by the index-open
 * character.</td>
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
 *
 *  (setq makeindex:index-open #\[)
 *  (setq makeindex:index-close #\[)   </pre>
 *
 * <p>Examples of Index Entries</p>
 *
 * <p>
 * The following examples illustrate the index entries understood by the
 *  splitindex raw index format.
 * </p>
 *
 * <pre>
 * \indexentry{abc}{123}   </pre>
 *
 * <p>
 * This example is the simple case of a main key {@code abc} and the page
 * reference {@code 123}. The index used is the default index which is denoted
 * by the empty string.
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
 * <pre>
 * \indexentry[idx1]{abc}{123}   </pre>
 *
 * <p>
 * This example shows an entry which is sent to the index {@code idx1}.
 * </p>
 *
 * <pre>
 * \indexentry[idx1]{alpha@$\alpha$}{123}   </pre>
 *
 * <p>
 * This example shows an entry with separate print representation which is sent
 * to the index {@code idx1}.
 * </p>
 *
 * <pre>
 * \indexentry[idx1]{alpha@$\alpha$|textbf}{123}   </pre>
 *
 * <p>
 * This example shows an entry with print representation and attribute which is
 * sent to the index {@code idx1}.
 * </p>
 *
 * <pre>
 * \indexentry[idx1]{a!b!c}{123}   </pre>
 *
 * <p>
 * This example shows a structured entry which is sent to the index
 * {@code idx1}.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class SplitindexParser extends MakeindexParser {

  /**
   * The field {@code indexOpen} contains the index open character.
   */
  private char indexOpen;

  /**
   * The field {@code indexClose} contains the index close character.
   */
  private char indexClose;

  /**
   * Creates a new object and gather the parameters from an interpreter.
   *
   * @param reader   the source to read from
   * @param resource the name of the resource for error messages
   * @param indexer  the l system as storage for parameters
   * @throws RawIndexException in case of an error
   * @see #configure(LInterpreter)
   */
  public SplitindexParser( Reader reader, String resource, Indexer indexer )
      throws RawIndexException {

    super( reader, resource, indexer );
  }

  /**
   * <p>
   * Gather the parameters from an interpreter. If the interpreter does not
   * have an appropriate value then a fallback is used. The following
   * parameters are used by this parser:
   * </p>
   *
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
   * <td>makeindex:index-open</td>
   * <td>{@code {}</td>
   * <td>This character opens an index argument. It is terminated by the
   * index-close character.</td>
   * </tr>
   * <tr>
   * <td>makeindex:index-close</td>
   * <td>{@code }}</td>
   * <td>This character closes an index argument which has been opened by the
   * index-open character.</td>
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
   * </table>
   *
   * @param interpreter the interpreter to query for the parameters
   * @throws RawIndexException in case that the value from the interpreter has
   *                           the wrong type
   */
  @Override
  public void configure( LInterpreter interpreter ) throws RawIndexException {

    indexOpen = initChar( "makeindex:index-open", interpreter, '[' );
    indexClose = initChar( "makeindex:index-close", interpreter, ']' );
    super.configure( interpreter );
  }

  /**
   * Scan an argument enclosed in {@code indexOpen} and {@code indexClose}.
   *
   * @return the argument found
   * @throws IOException                  in case of an error
   * @throws RawIndexEofException         in case of an unexpected EOF
   * @throws RawIndexMissingCharException in case of an error
   * @see org.extex.exindex.core.parser.makeindex.MakeindexParser#scanIndex(org.extex.exindex.core.parser.reader.ReaderLocator,
   * java.lang.String)
   */
  @Override
  protected String scanIndex( ReaderLocator reader, String fallback )
      throws IOException,
      RawIndexEofException,
      RawIndexMissingCharException {

    reader.mark( 1 );
    int c = reader.read();
    if( c != indexOpen ) {
      reader.reset();
      return fallback;
    }

    StringBuilder sb = new StringBuilder();

    for( c = reader.read(); c != indexClose; c = reader.read() ) {
      if( c < 0 ) {
        throw new RawIndexEofException( reader );
      }
      sb.append( (char) c );
    }
    return sb.toString();
  }

}
