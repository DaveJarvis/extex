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
import org.extex.ocpware.compiler.arith.ArithExpr;
import org.extex.ocpware.compiler.arith.Constant;
import org.extex.ocpware.compiler.exception.SyntaxException;
import org.extex.ocpware.compiler.parser.ParserStream;

/**
 * This utility class contains the string expression parser.
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
     * Parse a list of expressions.
     * 
     * @param s the input stream
     * 
     * @return the list of expressions found
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public static List<Expr> parse(ParserStream s)
            throws IOException,
                SyntaxException {

        List<Expr> result = new ArrayList<Expr>();

        for (;;) {
            int c = s.skipSpace();
            switch (c) {
                case '"':
                    for (c = s.read(); c >= 0 && c != '"'; c = s.read()) {
                        result.add(new Constant(c));
                    }
                    break;
                case '\\':
                    result.add(parseRef(s));
                    break;
                case '#':
                    ArithExpr a = ArithExpr.parse(s);
                    result.add(new Arith(a));
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
                    result.add(new Constant(s.parseNumber(c)));
                    break;
                default:
                    s.unread(c);
                    return result;
            }
        }
    }

    /**
     * Parse a reference.
     * 
     * @param s the input stream
     * 
     * @return the expression found
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private static Expr parseRef(ParserStream s)
            throws IOException,
                SyntaxException {

        int c = s.read();

        switch (c) {
            case '$':
                return new LastChar(0);
            case '*':
                return new Some(0, 0);
            case '(':
                c = s.skipSpace();
                if (c == '$') {
                    s.expect('-');
                    int n = s.parseNumber(s.skipSpace());
                    s.expect(')');
                    return new LastChar(n);
                } else if (c == '*') {
                    c = s.skipSpace();
                    if (c == '+') {
                        int n = s.parseNumber(s.skipSpace());
                        c = s.skipSpace();
                        if (c == '-') {
                            int m = s.parseNumber(s.skipSpace());
                            s.expect(')');
                            return new Some(n, m);
                        } else if (c == ')') {
                            return new Some(n, 0);
                        } else {
                            throw s.error(c);
                        }
                    } else if (c == '-') {
                        int m = s.parseNumber(s.skipSpace());
                        s.expect(')');
                        return new Some(0, m);
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
