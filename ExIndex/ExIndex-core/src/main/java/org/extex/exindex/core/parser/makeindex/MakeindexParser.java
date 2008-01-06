/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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
import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.util.ReaderLocator;
import org.extex.exindex.core.parser.util.TeXReader;
import org.extex.exindex.core.type.raw.CloseLocRef;
import org.extex.exindex.core.type.raw.LocRef;
import org.extex.exindex.core.type.raw.OpenLocRef;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This parser is a reader for input in the form of the <logo>makeindex</logo>
 * format and some extensions of it.
 * 
 * <doc section="Makeindex Index Format">
 * <h2>The <logo>makeindex</logo> Raw Index Format</h2>
 * 
 * <p>
 * The raw index format for <logo>makeindex</logo> is used to parse the input
 * and acquire index data. <logo>makeindex</logo> is rather general. The parser
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
 * <h3>The Parameters</h3>
 * 
 * <p>
 * The parsing of raw index entries in the <logo>makeindex</logo> format can be
 * controlled by a set of parameters. This makes the parser adaptable to a wider
 * range of applications.
 * </p>
 * <p>
 * The original need to introduce the parameters is the flexibility of <logo>TeX</logo>.
 * In <logo>TeX</logo> the category codes of characters can be redefined. Thus
 * <logo>makeindex</logo> needs to be able to adjust its behavior to cope with
 * such a situation.
 * </p>
 * <p>
 * The following table shows the parameters with their <logo>ExIndex</logo>
 * name, its <logo>makeindex</logo> name and the default value.
 * </p>
 * 
 * <table>
 * <tr>
 * <th><logo>ExIndex</logo> parameter</th>
 * <th><logo>makeindex</logo> parameter</th>
 * <th>Fallback</th>
 * <th>Type</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>makeindex:keyword</td>
 * <td>keyword</td>
 * <td><tt>\indexentry</tt></td>
 * <td>string</td>
 * <td>This string starts an entry. Anything between entries is ignored.</td>
 * </tr>
 * <tr>
 * <td>makeindex:arg-open</td>
 * <td>arg_open</td>
 * <td><tt>{</tt></td>
 * <td>character</td>
 * <td>This character opens an argument. It is terminated by the arg-close
 * character.</td>
 * </tr>
 * <tr>
 * <td>makeindex:arg-close</td>
 * <td>arg_close</td>
 * <td><tt>}</tt></td>
 * <td>character</td>
 * <td>This character closes an argument which has been opened by the arg-open
 * character. The arg-open and arg-close characters have to be balanced before
 * the closing takes place. Any arg-open or arg-close character preceded by an
 * quote character is not counted.</td>
 * </tr>
 * <tr>
 * <td>makeindex:range-open</td>
 * <td>range_open</td>
 * <td><tt>(</tt></td>
 * <td>character</td>
 * <td>This character indicates the start of a range.</td>
 * </tr>
 * <tr>
 * <td>makeindex:range-close</td>
 * <td>range_close</td>
 * <td><tt>)</tt></td>
 * <td>character</td>
 * <td>This character indicates the end of a range.</td>
 * </tr>
 * <tr>
 * <td>makeindex:escape</td>
 * <td>escape</td>
 * <td><tt>"</tt></td>
 * <td>character</td>
 * <td>This is the escape character.</td>
 * </tr>
 * <tr>
 * <td>makeindex:quote</td>
 * <td>quote</td>
 * <td><tt>\</tt></td>
 * <td>character</td>
 * <td>This is the quote character</td>
 * </tr>
 * <tr>
 * <td>makeindex:encap</td>
 * <td>encap</td>
 * <td><tt>|</tt></td>
 * <td>character</td>
 * <td>This character is the separator for the encapsulator.</td>
 * </tr>
 * <tr>
 * <td>makeindex:level</td>
 * <td>level</td>
 * <td><tt>!</tt></td>
 * <td>character</td>
 * <td>This character is the level separator. A structured key is divided into
 * parts with this character.</td>
 * </tr>
 * <tr>
 * <td>makeindex:actual</td>
 * <td>actual</td>
 * <td><tt>@</tt></td>
 * <td>character</td>
 * <td>The actual character, separating the print representation from the key.</td>
 * </tr>
 * </table>
 * 
 * <p>
 * The following example shows how the default setting can be defined in a
 * <logo>ExIndex</logo> style file.
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
 *  (setq makeindex:actual #\@)   </pre>
 * 
 * <h3>Examples of Index Entries</h3>
 * 
 * <p>
 * The following examples illustrate the index entries understood by the
 * <logo>makeindex</logo> raw index format.
 * </p>
 * 
 * <pre>
 * \indexentry{abc}{123}   </pre>
 * 
 * <p>
 * This example is the simple case of a main key <tt>abc</tt> and the page
 * reference <tt>123</tt>.
 * </p>
 * 
 * <pre>
 * \indexentry{alpha@$\alpha$}{123}   </pre>
 * 
 * <p>
 * This example sorts as <tt>alpha</tt> and prints as &alpha;.
 * </p>
 * 
 * <pre>
 * \indexentry{alpha@$\alpha$|textbf(}{123}   </pre>
 * 
 * <p>
 * This example shows an entry with the attribute <tt>textbf</tt> which is
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
 * <doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MakeindexParser implements RawIndexParser {

    /**
     * The field <tt>keyword</tt> contains the keyword for an index entry.
     */
    private String keyword;

    /**
     * The field <tt>argOpen</tt> contains the argument open character.
     */
    private char argOpen;

    /**
     * The field <tt>argClose</tt> contains the argument close character.
     */
    private char argClose;

    /**
     * The field <tt>escape</tt> contains the escape character.
     */
    private char escape;

    /**
     * The field <tt>quote</tt> contains the quote character.
     */
    private char quote;

    /**
     * The field <tt>encap</tt> contains the encapsulation separator.
     */
    private char encap;

    /**
     * The field <tt>level</tt> contains the level separator.
     */
    private char level;

    /**
     * The field <tt>actual</tt> contains the actual separator.
     */
    private char actual;

    /**
     * The field <tt>rangeOpen</tt> contains the range open separator.
     */
    private char rangeOpen;

    /**
     * The field <tt>rangeClose</tt> contains the range close separator.
     */
    private char rangeClose;

    /**
     * The field <tt>locator</tt> contains the locator.
     */
    private ReaderLocator locator;

    /**
     * The field <tt>index</tt> contains the name of the index.
     */
    private String index = "";

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
    public MakeindexParser(Reader reader, String resource,
            LInterpreter interpreter) throws RawIndexException {

        super();
        this.locator = new ReaderLocator(resource, new TeXReader(reader));
        configure(interpreter);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParser#close()
     */
    public void close() throws IOException {

        if (locator != null) {
            locator.getReader().close();
            locator = null;
        }
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
    protected void configure(LInterpreter interpreter) throws RawIndexException {

        keyword = initString("makeindex:keyword", interpreter, "\\indexentry");
        argOpen = initChar("makeindex:arg-open", interpreter, '{');
        argClose = initChar("makeindex:arg-close", interpreter, '}');
        rangeOpen = initChar("makeindex:range-open", interpreter, '(');
        rangeClose = initChar("makeindex:range-close", interpreter, ')');
        escape = initChar("makeindex:escape", interpreter, '"');
        quote = initChar("makeindex:quote", interpreter, '\\');
        encap = initChar("makeindex:encap", interpreter, '|');
        level = initChar("makeindex:level", interpreter, '!');
        actual = initChar("makeindex:actual", interpreter, '@');
    }

    /**
     * Check that the next character from the stream has a certain value.
     * 
     * @param ec the expected character
     * 
     * @throws RawIndexMissingCharException in case of an error
     * @throws IOException in case of an error
     */
    protected void expect(char ec)
            throws IOException,
                RawIndexMissingCharException {

        int c = locator.read();
        if (c != ec) {
            throw new RawIndexMissingCharException(locator, (char) c, ec);
        }
    }

    /**
     * Store an initial character in the interpreter if none is present.
     * 
     * @param var the variable name
     * @param interpreter the interpreter
     * @param value the default value
     * 
     * @return the character to use
     * 
     * @throws RawIndexException in case of an error
     */
    protected char initChar(String var, LInterpreter interpreter, char value)
            throws RawIndexException {

        LValue v = interpreter.get(var);
        if (v == null) {
            return value;
        } else if (!(v instanceof LChar)) {
            Object[] args = {v.toString()};
            throw new RawIndexException(locator, //
                LocalizerFactory.getLocalizer(getClass()).format(
                    "CharExpected", args));
        }

        return ((LChar) v).getValue();
    }

    /**
     * Store an initial String in the interpreter if none is present.
     * 
     * @param var the variable name
     * @param interpreter the interpreter
     * @param value the default value
     * 
     * @return the string to use
     * 
     * @throws RawIndexException in case of an error
     */
    private String initString(String var, LInterpreter interpreter, String value)
            throws RawIndexException {

        LValue v = interpreter.get(var);
        if (v == null) {
            return value;
        } else if (!(v instanceof LString)) {
            Object[] args = {v.toString()};
            throw new RawIndexException(locator, //
                LocalizerFactory.getLocalizer(getClass()).format(
                    "StringExpected", args));
        }

        return ((LString) v).getValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParser#parse()
     */
    public RawIndexentry parse() throws RawIndexException, IOException {

        if (locator == null) {
            return null;
        }

        char k0 = keyword.charAt(0);
        try {

            for (int c = locator.read(); c >= 0; c = locator.read()) {
                if (c == k0 && scanKeyword(keyword)) {
                    String idx = scanIndex(locator, index);
                    String arg = scanArgument();
                    String p = scanArgument();
                    String enc = null;
                    int x = arg.lastIndexOf(encap);
                    if (x >= 0) {
                        enc = arg.substring(x + 1);
                        arg = arg.substring(0, x);
                    }
                    x = arg.indexOf(actual);
                    if (x >= 0) {
                        return store(idx, arg.substring(0, x), arg
                            .substring(x + 1), p, enc);
                    }
                    return store(idx, arg, arg, p, enc);
                }
            }
        } finally {
            locator.getReader().close();
            locator = null;
        }
        return null;
    }

    /**
     * Scan an argument enclosed in <tt>argOpen</tt> and <tt>argClose</tt>.
     * 
     * @return the argument found
     * 
     * @throws IOException in case of an error
     * @throws RawIndexEofException in case of an unexpected EOF
     * @throws RawIndexMissingCharException in case of an error
     */
    protected String scanArgument()
            throws IOException,
                RawIndexEofException,
                RawIndexMissingCharException {

        expect(argOpen);
        StringBuilder sb = new StringBuilder();
        int level = 1;

        for (;;) {
            int c = locator.read();
            if (c < 0) {
                throw new RawIndexEofException(locator);
            } else if (c == argOpen) {
                level++;
            } else if (c == argClose) {
                level--;
                if (level <= 0) {
                    return sb.toString();
                }
            } else if (c == quote) {
                int l = sb.length();
                if (l <= 0 || sb.charAt(l - 1) != escape) {
                    c = locator.read();
                    if (c < 0) {
                        throw new RawIndexEofException(locator);
                    }
                }
            }
            sb.append((char) c);
        }
    }

    /**
     * This method is an extension point to acquire the index to use.
     * 
     * @param loc the locator
     * @param fallback the default index
     * 
     * @return the index to use
     * 
     * @throws RawIndexMissingCharException in case of an error
     * @throws RawIndexEofException in case of an error
     * @throws IOException in case of an I/O error
     */
    protected String scanIndex(ReaderLocator loc, String fallback)
            throws IOException,
                RawIndexEofException,
                RawIndexMissingCharException {

        return index;
    }

    /**
     * Scan a keyword.
     * 
     * @param keyword the keyword to read
     * 
     * @return <code>true</code> iff the keyword has been found
     * 
     * @throws IOException in case of an I/O error
     */
    protected boolean scanKeyword(String keyword) throws IOException {

        int length = keyword.length();
        for (int i = 1; i < length; i++) {
            int c = locator.read();
            if (c != keyword.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Create an object consisting of the required parts. The parameters are put
     * in place and the index entry is returned.
     * 
     * @param idx the name of the index
     * @param keyArg the argument
     * @param printArg the print representation
     * @param locref the page
     * @param enc the encapsulation
     * @return the new index entry
     */
    protected RawIndexentry store(String idx, String keyArg, String printArg,
            String locref, String enc) {

        List<String> list = new ArrayList<String>();
        int a = 0;
        for (int i = keyArg.indexOf(level, a); i >= 0; i =
                keyArg.indexOf(level, a)) {
            list.add(keyArg.substring(a, i));
            a = i + 1;
        }
        list.add(keyArg);
        String[] key = new String[list.size()];
        int i = 0;
        for (String s : list) {
            key[i] = s;
        }
        String[] print = new String[key.length];
        for (i = 0; i < print.length; i++) {
            print[i] = "";
        }
        if (printArg != null) {
            print[print.length - 1] = printArg;
        }

        LocRef ref;
        String attr = enc;
        if (attr == null || "".equals(attr)) {
            ref = new LocRef(locref, attr);
            attr = null;
        } else {
            char c = attr.charAt(attr.length() - 1);
            if (c == rangeOpen) {
                ref = new OpenLocRef(locref, //
                    attr.substring(0, attr.length() - 1));
            } else if (c == rangeClose) {
                ref = new CloseLocRef(locref, //
                    attr.substring(0, attr.length() - 1));
            } else {
                ref = new LocRef(locref, attr);
            }
        }
        return new RawIndexentry(idx, key, print, ref);
    }
}
