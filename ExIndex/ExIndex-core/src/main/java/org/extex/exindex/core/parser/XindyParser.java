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

package org.extex.exindex.core.parser;

import java.io.IOException;
import java.io.Reader;

import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexSyntaxException;
import org.extex.exindex.core.parser.raw.CloseLocRef;
import org.extex.exindex.core.parser.raw.LocRef;
import org.extex.exindex.core.parser.raw.OpenLocRef;
import org.extex.exindex.core.parser.raw.RawIndexentry;
import org.extex.exindex.core.parser.raw.RefSpec;
import org.extex.exindex.core.parser.raw.XRef;
import org.extex.exindex.lisp.parser.LParser;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This parser is a reader for input in the form of the xindy raw index format
 * and some extensions of it.
 * <p>
 * The basic syntax is the Lisp syntax with the backslash character as escape
 * character in strings. Semicolon initiates an end-line comment.
 * </p>
 * 
 * <pre>
 * (indexentry { :key <i>string-list</i> [:print <i>string-list</i>]
 *             | :tkey <i>list-of-layers</i> }
 *             [:attr <i>string</i>]
 *             { :locref <i>string</i>  [:open-range | :close-range]
 *             | :xref <i>string-list</i> } )
 * </pre>
 * 
 * <p>
 * The index entry is in principal made up of three parts: the key, the
 * attribute, and the location. They are described below.
 * </p>
 * 
 * <h3>The Key</h3>
 * <p>
 * The key consists of a pair made up of a list of sort key and print
 * representations. There are several ways to specify the key.
 * </p>
 * <p>
 * The key can be given with the <tt>:key</tt> flag. The argument following it
 * is a list of strings to be stored as sort key. If nothing else is specified
 * then the argument list is also stored as print representation. This is shown
 * in the following example.
 * </p>
 * 
 * <pre>
 *  (indexentry :key ("abc") :locref "123")
 * </pre>
 * 
 * <p>
 * If just a single string is given after the <tt>:key</tt> flag is treated as
 * an abbreviation for a single element list containing it. This is an extension
 * of the xindy format.
 * </p>
 * 
 * <pre>
 *  (indexentry :key "abc" :locref "123")
 * </pre>
 * 
 * 
 * <p>
 * The key can be augmented with a different print representation which is given
 * with the flag <tt>:print</tt>. This can be seen in the following example.
 * </p>
 * 
 * <pre>
 *  (indexentry :key ("alpha") :print ("$\alpha$") :locref "123")
 * </pre>
 * 
 * <p>
 * Again the value of the <tt>:print</tt> flag can be a single sting which
 * serves as an abbreviation for the singleton list containing it. This is an
 * extension of the xindy format.
 * </p>
 * 
 * <pre>
 *  (indexentry :key "alpha" :print "$\alpha$" :locref "123")
 * </pre>
 * 
 * <p>
 * Finally the flag <tt>:tkey</tt> can be used to specify the sort key and the
 * print value jointly. The argument is a list. This list can contain a list of
 * one or two elements. If only one element is given then this value &ndash;
 * which has to be a string &ndash; is used for both sort key and print
 * representation at this level. If two elements are given then the two elements
 * are the sort key and the print value in this order.
 * </p>
 * 
 * <pre>
 *  (indexentry :tkey (("symbol") ("alpha" "$\alpha$")) :locref "123")
 * </pre>
 * 
 * <p>
 * The elements of the argument list of <tt>:tkey</tt> can also be string in
 * this case it is used both for sort key and print value. This is an extension
 * of the xindy format.
 * </p>
 * 
 * <h3>The Attribute</h3>
 * <p>
 * The attribute is an optional value which classifies the location. It is used
 * during markup to tag the location.
 * </p>
 * <p>
 * The value of the attribute is a string which is used to select the markup.
 * </p>
 * 
 * <h3>The Reference</h3>
 * <p>
 * The reference is either a location reference or a cross reference.
 * </p>
 * <p>
 * The location reference takes as a parameter a single string containing the
 * value of the reference &ndash; e.g. the page number.
 * </p>
 * 
 * <pre>
 *  (indexentry :key ("alpha" "$\alpha$") :locref "A-123")
 * </pre>
 * 
 * <p>
 * In addition the flags <tt>:open-range</tT> and <tt>:close-range</tt> can
 * be used to signal that the entry corresponds to a range and is the beginning
 * or the end of such a range. Not both of them can be given in one entry.
 * </p>
 * <p>
 * The other type of reference is a cross reference. It refers to another entry
 * in the index. The flag <tt>:xref</tt> can be used to specify a cross
 * reference. The argument is a list of strings denoting the referenced entry.
 * </p>
 * 
 * <pre>
 *  (indexentry :key ("alpha" "$\alpha$") :xref ("greek"))
 * </pre>
 * 
 * <p>
 * As above an extension of the xindy format can be used by abbreviating a
 * singleton list as argument to the flag <tt>:xref</tt> by a single string.
 * </p>
 * 
 * <pre>
 *  (indexentry :key ("alpha" "$\alpha$") :xref "greek")
 * </pre>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class XindyParser extends LParser implements RawIndexParser {

    /**
     * The constant <tt>KEY</tt> contains the symbol for the key flag.
     */
    private static final LSymbol KEY = LSymbol.get(":key");

    /**
     * The constant <tt>PRINT</tt> contains the symbol for the print flag.
     */
    private static final LSymbol PRINT = LSymbol.get(":print");

    /**
     * The constant <tt>TKEY</tt> contains the symbol for the tkey flag.
     */
    private static final LSymbol TKEY = LSymbol.get(":tkey");

    /**
     * The constant <tt>ATTR</tt> contains the symbol for the attr flag.
     */
    private static final LSymbol ATTR = LSymbol.get(":attr");

    /**
     * The constant <tt>LOCREF</tt> contains the symbol for the locref flag.
     */
    private static final LSymbol LOCREF = LSymbol.get(":locref");

    /**
     * The constant <tt>OPEN_RANGE</tt> contains the symbol for the open-range
     * flag.
     */
    private static final LSymbol OPEN_RANGE = LSymbol.get(":open-range");

    /**
     * The constant <tt>CLOSE_RANGE</tt> contains the symbol for the
     * close-range flag.
     */
    private static final LSymbol CLOSE_RANGE = LSymbol.get(":close-range");

    /**
     * The constant <tt>XREF</tt> contains the symbol for the xref flag.
     */
    private static final LSymbol XREF = LSymbol.get(":xref");

    /**
     * Creates a new object.
     * 
     * @param reader the source to read from
     * @param resource the name of the resource for error messages
     */
    public XindyParser(Reader reader, String resource) {

        super(reader, resource);
    }

    /**
     * Check that the argument is a LList and cast it.
     * 
     * @param value the value
     * 
     * @return the list
     * 
     * @throws RawIndexException in case of an error
     */
    private LList assumeLList(LValue value) throws RawIndexException {

        if (!(value instanceof LList)) {
            throw new RawIndexException(getResource(), getLineNumber(),
                LocalizerFactory.getLocalizer(getClass()).format("MissingList"));
        }
        return (LList) value;
    }

    /**
     * Check that the given value is an LString.
     * 
     * @param x the value
     * 
     * @return the string contained
     * 
     * @throws RawIndexException in case of an error
     */
    private String assumeLString(LValue x) throws RawIndexException {

        if (!(x instanceof LString)) {
            throw exception("MissingString");
        }
        return ((LString) x).getValue();
    }

    /**
     * Check that a value is <code>null</code> and otherwise raise an
     * appropriate exception.
     * 
     * @param value the value
     * @param type the flag
     * 
     * @throws RawIndexException in case of an error
     */
    private void assumeUnbound(Object value, LSymbol type)
            throws RawIndexException {

        if (value != null) {
            throw exception("BoundTwice", type.toString());
        }
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

        return new RawIndexException(getResource(), getLineNumber(),
            LocalizerFactory.getLocalizer(getClass()).format(key, args));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParser#parse()
     */
    public RawIndexentry parse() throws IOException {

        LValue term = read();
        if (term == null) {
            return null;
        }

        LList list = assumeLList(term);
        String[] print = null;
        String[] key = null;
        String[] tkey = null;
        String attr = null;
        String locref = null;
        RefSpec ref = null;
        Boolean openRange = null;
        Boolean closeRange = null;
        int size = list.size();
        if (size == 0) {
            throw new RawIndexSyntaxException(getResource(), getLineNumber());
        }
        if (!(list.get(0) instanceof LSymbol)) {
            throw new RawIndexSyntaxException(getResource(), getLineNumber());
        }
        LSymbol sym = (LSymbol) list.get(0);
        if (!"indexentry".equals(sym.getValue())) {
            throw new RawIndexSyntaxException(getResource(), getLineNumber());
        }

        for (int i = 1; i < size; i++) {
            LValue arg = list.get(i);

            if (arg == KEY) {
                if (i >= size - 1) {
                    throw new RawIndexSyntaxException(getResource(),
                        getLineNumber());
                }
                assumeUnbound(key, KEY);
                arg = list.get(++i);
                if (arg instanceof LString) {
                    key = new String[]{((LString) arg).getValue()};
                } else {
                    key = toArray(arg);
                }

            } else if (arg == PRINT) {
                if (i >= size - 1) {
                    throw new RawIndexSyntaxException(getResource(),
                        getLineNumber());
                }
                assumeUnbound(print, PRINT);
                arg = list.get(++i);
                if (arg instanceof LString) {
                    print = new String[]{((LString) arg).getValue()};
                } else {
                    print = toArray(arg);
                }

            } else if (arg == TKEY) {
                if (i >= size - 1) {
                    throw new RawIndexSyntaxException(getResource(),
                        getLineNumber());
                }
                assumeUnbound(tkey, TKEY);
                if (key != null || print != null) {
                    throw exception("BoundTwice", TKEY.toString());
                }
                LList lst = assumeLList(list.get(++i));
                int len = lst.size();
                key = new String[len];
                print = new String[len];
                for (int j = 0; j < len; j++) {
                    LValue val = lst.get(j);
                    if (val instanceof LString) {
                        String s = ((LString) val).getValue();
                        key[j] = s;
                        print[j] = s;
                    } else if (val instanceof LList) {
                        LList ll = (LList) val;
                        switch (ll.size()) {
                            case 1:
                                String s = assumeLString(ll.get(0));
                                key[j] = s;
                                print[j] = s;
                                break;
                            case 2:
                                key[j] = assumeLString(ll.get(0));
                                print[j] = assumeLString(ll.get(1));
                                break;
                            default:
                                throw new RawIndexSyntaxException(
                                    getResource(), getLineNumber());
                        }

                    } else {
                        throw new RawIndexSyntaxException(getResource(),
                            getLineNumber());
                    }
                }

            } else if (arg == ATTR) {
                if (i >= size - 1) {
                    throw new RawIndexSyntaxException(getResource(),
                        getLineNumber());
                }
                assumeUnbound(attr, ATTR);
                attr = assumeLString(list.get(++i));

            } else if (arg == OPEN_RANGE) {
                assumeUnbound(openRange, OPEN_RANGE);
                openRange = Boolean.TRUE;

            } else if (arg == CLOSE_RANGE) {
                assumeUnbound(closeRange, CLOSE_RANGE);
                closeRange = Boolean.TRUE;

            } else if (arg == LOCREF) {
                if (i >= size - 1) {
                    throw new RawIndexSyntaxException(getResource(),
                        getLineNumber());
                }
                assumeUnbound(locref, LOCREF);
                locref = assumeLString(list.get(++i));

            } else if (arg == XREF) {
                if (i >= size - 1) {
                    throw new RawIndexSyntaxException(getResource(),
                        getLineNumber());
                }
                assumeUnbound(ref, XREF);
                arg = list.get(++i);
                String[] a;
                if (arg instanceof LString) {
                    a = new String[]{((LString) arg).getValue()};
                } else {
                    a = toArray(arg);
                }
                ref = new XRef(a);

            } else {
                throw exception("MissingTag", arg.toString());
            }
        }

        if (key == null) {
            throw exception("Unbound", KEY.toString());
        }
        if (print == null) {
            print = key;
        }
        if (ref != null) {
            if (openRange != null) {
                throw exception("XrefAndOpen");
            }
            if (closeRange != null) {
                throw exception("XrefAndClose");
            }
        }

        if (locref != null) {
            if (openRange != null) {
                if (closeRange != null) {
                    throw exception("OpenAndClose");
                }
                ref = new OpenLocRef(locref);
            } else if (closeRange != null) {
                ref = new CloseLocRef(locref);
            } else {
                ref = new LocRef(locref);
            }
        }

        if (ref == null) {
            throw exception("Unbound", LOCREF.toString());
        }
        return new RawIndexentry(key, print, attr, ref);
    }

    /**
     * Translate a list of strings to a String array.
     * 
     * @param value the list
     * 
     * @return the String array
     * 
     * @throws RawIndexException in case of an error
     */
    private String[] toArray(LValue value) throws RawIndexException {

        LList list = assumeLList(value);
        int size = list.size();
        String[] array = new String[size];

        for (int i = 0; i < size; i++) {
            array[i] = assumeLString(list.get(i));
        }

        return array;
    }
}
