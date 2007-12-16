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
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.exception.EofException;
import org.extex.exindex.core.exception.MissingException;
import org.extex.exindex.core.parser.raw.CloseLocRef;
import org.extex.exindex.core.parser.raw.Indexentry;
import org.extex.exindex.core.parser.raw.Key;
import org.extex.exindex.core.parser.raw.LocRef;
import org.extex.exindex.core.parser.raw.OpenLocRef;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This parser is a reader for input in the form of the makeindex format and
 * some extensions of it.
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MakeindexParser implements RawIndexParser {

    /**
     * The field <tt>reader</tt> contains the reader.
     */
    private LineNumberReader reader;

    /**
     * The field <tt>resource</tt> contains the name of the resource for error
     * messages.
     */
    private String resource;

    /**
     * The field <tt>keyword</tt> contains the ...
     */
    private String keyword;

    /**
     * The field <tt>argOpen</tt> contains the ...
     */
    private char argOpen;

    /**
     * The field <tt>argClose</tt> contains the ...
     */
    private char argClose;

    /**
     * The field <tt>escape</tt> contains the ...
     */
    private char escape;

    /**
     * The field <tt>quote</tt> contains the ...
     */
    private char quote;

    /**
     * The field <tt>encap</tt> contains the ...
     */
    private char encap;

    /**
     * The field <tt>level</tt> contains the ...
     */
    private char level;

    /**
     * The field <tt>actual</tt> contains the ...
     */
    private char actual;

    /**
     * The field <tt>rangeOpen</tt> contains the ...
     */
    private char rangeOpen;

    /**
     * The field <tt>rangeClose</tt> contains the ...
     */
    private char rangeClose;

    /**
     * Creates a new object.
     * 
     * @param reader the source to read from
     * @param resource the name of the resource for error messages
     * @param interpreter the l system as storage for parameters
     */
    public MakeindexParser(Reader reader, String resource,
            LInterpreter interpreter) {

        super();
        this.reader = new LineNumberReader(reader);
        this.resource = resource;

        LValue k = interpreter.get("makeindex:keyword");
        keyword = (k != null ? keyword = k.toString() : "\\indexentry");
        argOpen = initChar('{', interpreter, "makeindex:arg-open");
        argClose = initChar('}', interpreter, "makeindex:arg-close");
        rangeOpen = initChar('(', interpreter, "makeindex:range-open");
        rangeClose = initChar(')', interpreter, "makeindex:range-close");
        escape = initChar('"', interpreter, "makeindex:escape");
        quote = initChar('\\', interpreter, "makeindex:quote");
        encap = initChar('|', interpreter, "makeindex:encap");
        level = initChar('!', interpreter, "makeindex:level");
        actual = initChar('@', interpreter, "makeindex:actual");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParser#close()
     */
    public void close() throws IOException {

        if (reader != null) {
            reader.close();
        }
        reader = null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param ec the expected character
     * 
     * @throws IOException in case of an error
     */
    private void expect(char ec) throws IOException {

        int c = reader.read();
        if (c != ec) {
            throw new MissingException(resource, reader.getLineNumber(),
                (char) c, ec);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param value the default value
     * @param interpreter the interpreter
     * @param var the variable name
     * 
     * @return the character to use
     */
    private char initChar(char value, LInterpreter interpreter, String var) {

        LValue v = interpreter.get(var);
        if (v == null) {
            return value;
        }
        if (!(v instanceof LChar)) {
            // TODO gene: set unimplemented
            throw new RuntimeException("unimplemented");
        }

        return ((LChar) v).getValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParser#parse()
     */
    public Indexentry parse() throws IOException {

        LineNumberReader r = new LineNumberReader(reader);
        char k0 = keyword.charAt(0);
        try {

            for (int c = r.read(); c >= 0; c = r.read()) {
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
                    String a;
                    if (x >= 0) {
                        a = arg.substring(x + 1);
                        arg = arg.substring(0, x);
                    } else {
                        a = arg;
                    }
                    return store(arg, p, a, enc);
                }
            }
        } finally {
            r.close();
        }
        return null;
    }

    /**
     * Scan an argument enclosed in <tt>argOpen</tt> and <tt>argClose</tt>.
     * 
     * @return the argument found
     * 
     * @throws IOException in case of an error
     */
    private String scanArgument() throws IOException {

        expect(argOpen);
        StringBuilder sb = new StringBuilder();
        int level = 1;

        for (;;) {
            int c = reader.read();
            if (c < 0) {
                throw new EofException(resource, reader.getLineNumber());
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
                    c = reader.read();
                    if (c < 0) {
                        throw new EofException(resource, reader.getLineNumber());
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
            int c = reader.read();
            if (c != keyword.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param arg
     * @param locref the page
     * @param display the display representation
     * @param enc TODO
     * 
     * @return the new index entry
     */
    private Indexentry store(String arg, String locref, String display,
            String enc) {

        List<String> list = new ArrayList<String>();
        int a = 0;
        for (int i = arg.indexOf(level, a); i >= 0; i = arg.indexOf(level, a)) {
            list.add(arg.substring(a, i));
            a = i + 1;
        }
        list.add(arg);
        String[] key = new String[list.size()];
        int i = 0;
        for (String s : list) {
            key[i] = s;
        }
        String[] print = new String[key.length];
        for (i = 0; i < print.length; i++) {
            print[i] = "";
        }
        if (display != null) {
            print[print.length - 1] = display;
        }

        LocRef ref;
        String attr = enc;
        if (attr == null || "".equals(attr)) {
            ref = new LocRef(locref);
            attr = null;
        } else {
            char c = attr.charAt(attr.length() - 1);
            if (c == rangeOpen) {
                ref = new OpenLocRef(locref);
                attr = attr.substring(0, attr.length() - 1);
            } else if (c == rangeClose) {
                ref = new CloseLocRef(locref);
                attr = attr.substring(0, attr.length() - 1);
            } else {
                ref = new LocRef(locref);
            }
        }
        return new Indexentry(new Key(key, print), //
            ("".equals(attr) ? null : attr), ref);
    }
}
