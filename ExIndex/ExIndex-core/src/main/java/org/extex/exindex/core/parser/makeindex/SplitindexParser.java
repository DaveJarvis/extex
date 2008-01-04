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

package org.extex.exindex.core.parser.makeindex;

import java.io.IOException;
import java.io.Reader;

import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.parser.util.ReaderLocator;
import org.extex.exindex.lisp.LInterpreter;

/**
 * This parser is a reader for input in the form of the splitindex format and
 * some extensions of it.
 * <p>
 * The following examples illustrate the index entries understood by this
 * parser.
 * </p>
 * 
 * <pre>
 * \indexentry{abc}{123}   </pre>
 * 
 * <pre>
 * \indexentry[idx1]{abc}{123}   </pre>
 * 
 * <pre>
 * \indexentry[idx1]{$alpha$@alpha}{123}   </pre>
 * 
 * <pre>
 * \indexentry[idx1]{$alpha$@alpha|textbf(}{123}   </pre>
 * 
 * <pre>
 * \indexentry[idx1]{a!b!c}{123}   </pre>
 * 
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
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6763 $
 */
public class SplitindexParser extends MakeindexParser {

    /**
     * The field <tt>indexOpen</tt> contains the index open character.
     */
    private char indexOpen;

    /**
     * The field <tt>indexClose</tt> contains the index close character.
     */
    private char indexClose;

    /**
     * Creates a new object and gather the parameters from an interpreter.
     * 
     * @param reader the source to read from
     * @param resource the name of the resource for error messages
     * @param interpreter the l system as storage for parameters
     * 
     * @throws RawIndexException in case of an error
     * 
     * @see #configure(LInterpreter)
     */
    public SplitindexParser(Reader reader, String resource,
            LInterpreter interpreter) throws RawIndexException {

        super(reader, resource, interpreter);
    }

    /**
     * Gather the parameters from an interpreter. If the interpreter does not
     * have an appropriate value then a fallback is used. The following
     * parameters are used by this parser: <br/> <table>
     * <tr>
     * <th>Name</th>
     * <th>Fallback</th>
     * <th>Description</th>
     * </tr>
     * <tr>
     * <td>makeindex:keyword</td>
     * <td><tt>\indexentry</tt></td>
     * <td>This string starts an entry. Anything between entries is ignored.</td>
     * </tr>
     * <tr>
     * <td>makeindex:index-open</td>
     * <td><tt>{</tt></td>
     * <td>This character opens an index argument. It is terminated by the
     * index-close character.</td>
     * </tr>
     * <tr>
     * <td>makeindex:index-close</td>
     * <td><tt>}</tt></td>
     * <td>This character closes an index argument which has been opened by the
     * index-open character.</td>
     * </tr>
     * <tr>
     * <td>makeindex:arg-open</td>
     * <td><tt>{</tt></td>
     * <td>This character opens an argument. It is terminated by the arg-close
     * character.</td>
     * </tr>
     * <tr>
     * <td>makeindex:arg-close</td>
     * <td><tt>}</tt></td>
     * <td>This character closes an argument which has been opened by the
     * arg-open character.</td>
     * </tr>
     * <tr>
     * <td>makeindex:range-open</td>
     * <td><tt>(</tt></td>
     * <td>This character indicates the start of a range.</td>
     * </tr>
     * <tr>
     * <td>makeindex:range-close</td>
     * <td><tt>)</tt></td>
     * <td>This character indicates the end of a range.</td>
     * </tr>
     * <tr>
     * <td>makeindex:escape</td>
     * <td><tt>"</tt></td>
     * <td>This is the escape character.</td>
     * </tr>
     * <tr>
     * <td>makeindex:quote</td>
     * <td><tt>\</tt></td>
     * <td>This is the quote character</td>
     * </tr>
     * <tr>
     * <td>makeindex:encap</td>
     * <td><tt>|</tt></td>
     * <td>This character is the separator for the encapsulator.</td>
     * </tr>
     * <tr>
     * <td>makeindex:level</td>
     * <td><tt>!</tt></td>
     * <td>This character is the level separator. A structured key is divided
     * into parts with this character.</td>
     * </tr>
     * <tr>
     * <td>makeindex:actual</td>
     * <td><tt>@</tt></td>
     * <td>The actual character, separating the print representation from the
     * key.</td>
     * </tr>
     * </table>
     * 
     * @param interpreter the interpreter to query for the parameters
     * 
     * @throws RawIndexException in case that the value from the interpreter has
     *         the wrong type
     */
    @Override
    public void configure(LInterpreter interpreter) throws RawIndexException {

        indexOpen = initChar("makeindex:index-open", interpreter, '[');
        indexClose = initChar("makeindex:index-close", interpreter, ']');
        super.configure(interpreter);
    }

    /**
     * Scan an argument enclosed in <tt>indexOpen</tt> and <tt>indexClose</tt>.
     * 
     * @return the argument found
     * 
     * @throws IOException in case of an error
     * @throws RawIndexEofException in case of an unexpected EOF
     * @throws RawIndexMissingCharException in case of an error
     */
    @Override
    protected String scanIndex(ReaderLocator loc, String fallback)
            throws IOException,
                RawIndexEofException,
                RawIndexMissingCharException {

        expect(indexOpen);
        StringBuilder sb = new StringBuilder();

        for (;;) {
            int c = loc.read();
            if (c < 0) {
                throw new RawIndexEofException(loc);
            } else if (c == indexClose) {
                return sb.toString();
            }
            sb.append((char) c);
        }
    }

}
