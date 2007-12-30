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

package org.extex.exindex.core.parser.makeindex;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.ReaderLocator;
import org.extex.exindex.core.parser.raw.CloseLocRef;
import org.extex.exindex.core.parser.raw.LocRef;
import org.extex.exindex.core.parser.raw.OpenLocRef;
import org.extex.exindex.core.parser.raw.RawIndexentry;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This parser is a reader for input in the form of the makeindex format and
 * some extensions of it.
 * <p>
 * The following examples illustrate the index entries understood by this
 * parser.
 * </p>
 * 
 * <pre>
 * \indexentry{abc}{123}
 * </pre>
 * 
 * <pre>
 * \indexentry{$alpha$@alpha}{123}
 * </pre>
 * 
 * <pre>
 * \indexentry{$alpha$@alpha|textbf(}{123}
 * </pre>
 * 
 * <pre>
 * \indexentry{a!b!c}{123}
 * </pre>
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
 * </pre>
 * 
 * 
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
    private String index;

    /**
     * Creates a new object and gather the parameters from an interpreter.
     * 
     * @param reader the source to read from
     * @param index the name of the index to store the entries in
     * @param resource the name of the resource for error messages
     * @param interpreter the l system as storage for parameters
     * 
     * @throws RawIndexException in case of an error
     * 
     * @see #configure(LInterpreter)
     */
    public MakeindexParser(Reader reader, String index, String resource,
            LInterpreter interpreter) throws RawIndexException {

        super();
        this.index = index;
        this.locator = new ReaderLocator(resource, reader);
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
    public void configure(LInterpreter interpreter) throws RawIndexException {

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
     * Create an exception and fill it with a value from the resource bundle for
     * this class.
     * 
     * @param key the key
     * @param args the additional arguments
     * 
     * @return the exception
     */
    protected RawIndexException exception(String key, Object... args) {

        return new RawIndexException(locator, //
            LocalizerFactory.getLocalizer(getClass()).format(key, args));
    }

    /**
     * Check that the next character from the stream has a certain value.
     * 
     * @param ec the expected character
     * 
     * @throws RawIndexMissingCharException in case of an error
     * @throws IOException in case of an error
     */
    private void expect(char ec)
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
    private char initChar(String var, LInterpreter interpreter, char value)
            throws RawIndexException {

        LValue v = interpreter.get(var);
        if (v == null) {
            return value;
        } else if (!(v instanceof LChar)) {
            throw exception("CharExpected", v.toString());
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
            throw exception("StringExpected", v.toString());
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
                        return store(arg.substring(0, x), arg.substring(x + 1),
                            p, enc);
                    }
                    return store(arg, arg, p, enc);
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
    private String scanArgument()
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
     * Scan a keyword.
     * 
     * @param keyword the keyword to read
     * 
     * @return <code>true</code> iff the keyword has been found
     * 
     * @throws IOException in case of an I/O error
     */
    private boolean scanKeyword(String keyword) throws IOException {

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
     * @param keyArg the argument
     * @param printArg the print representation
     * @param locref the page
     * @param enc the encapsulation
     * @return the new index entry
     */
    private RawIndexentry store(String keyArg, String printArg, String locref,
            String enc) {

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
        return new RawIndexentry(index, key, print, ref);
    }
}
