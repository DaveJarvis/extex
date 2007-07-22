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

package org.extex.ocpware.compiler.sexpression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.arith.Arith;
import org.extex.ocpware.compiler.parser.ParserStream;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class SParser {

    /**
     * Creates a new object.
     */
    private SParser() {

        // unused
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the input stream
     * 
     * @return the list of expressions found
     * 
     * @throws IOException in case of an I/O error
     */
    public static List<Expr> parse(ParserStream s) throws IOException {

        List<Expr> result = new ArrayList<Expr>();

        for (;;) {
            int c = s.skipSpace();
            switch (c) {
                case '"':
                    for (c = s.read(); c >= 0 && c != '"'; c = s.read()) {
                        result.add(new ExpressionConstant(c));
                    }
                    break;
                case '\\':
                    result.add(parseXX(s));
                    break;
                case '#':
                    result.add(Arith.parse(s));
                    break;
                case '`':
                case '@':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    result.add(new ExpressionConstant(s.parseNumber(c)));
                    break;
                default:
                    s.unread(c);
                    return result;
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the input stream
     * 
     * @return ...
     * 
     * @throws IOException in case of an I/O error
     */
    private static Expr parseXX(ParserStream s) throws IOException {

        int c = s.read();

        switch (c) {
            case '$':
                return new PrefixLast();
            case '*':
                return new Prefix();
            case '(':
                c = s.skipSpace();
                if (c == '$') {
                    s.expect('-');
                    int n = s.parseNumber(s.skipSpace());
                    s.expect(')');
                    return new PrefixLastTrim(n);
                } else if (c == '*') {
                    c = s.skipSpace();
                    if (c == '+') {
                        int n = s.parseNumber(s.skipSpace());
                        c = s.skipSpace();
                        if (c == '-') {
                            int m = s.parseNumber(s.skipSpace());
                            s.expect(')');
                            return new PrefixTrim(n, m);
                        } else if (c == ')') {
                            return new PrefixTrimLeft(n);
                        } else {
                            throw s.error(c);
                        }
                    } else if (c == '-') {
                        int n = s.parseNumber(s.skipSpace());
                        s.expect(')');
                        return new PrefixTrimRight(n);
                    } else {
                        throw s.error(c);
                    }

                } else {
                    throw s.error(c);
                }
            default:
                return new Char(s.parseNumber(c));
        }
    }

}
