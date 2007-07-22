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

package org.extex.ocpware.compiler.arith;

import java.io.IOException;

import org.extex.ocpware.compiler.parser.ParserStream;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Arith {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the input stream
     * 
     * @return the term found
     * 
     * @throws IOException in case of an I/O error
     */
    public static ArithExpr parse(ParserStream s) throws IOException {

        Op op = null;
        ArithExpr save = null;
        ArithExpr left = parseTerm(s);
        String t;

        for (;;) {
            int c = s.skipSpace();
            switch (c) {
                case '+':
                    if (save != null) {
                        save = op.eval(save, left);
                    } else {
                        save = left;
                    }
                    op = Op.PLUS;
                    left = parseTerm(s);
                    break;
                case '-':
                    if (save != null) {
                        save = op.eval(save, left);
                    } else {
                        save = left;
                    }
                    op = Op.MINUS;
                    left = parseTerm(s);
                    break;
                case '*':
                    left = new Times(left, parseTerm(s));
                    break;
                case 'd':
                    s.unread(c);
                    t = s.parseId();
                    if ("div".equals(t)) {
                        s.expect(':');
                        left = new Div(left, parseTerm(s));
                    } else {
                        s.unread(t.getBytes());
                    }
                    break;
                case 'm':
                    t = s.parseId();
                    if ("mod".equals(t)) {
                        s.expect(':');
                        left = new Mod(left, parseTerm(s));
                    } else {
                        s.unread(t.getBytes());
                    }
                    break;
                default:
                    s.unread(c);
                    if (save != null) {
                        left = op.eval(save, left);
                    }
                    return left;
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the input stream
     * 
     * @return the arithmetic reference
     * 
     * @throws IOException in case of an I/O error
     */
    private static ArithExpr parseArithRef(ParserStream s) throws IOException {

        int c = s.skipSpace();

        if (c == '$') {
            return new RefEnd();
        } else if (c == '(') {
            s.expect('$');
            s.expect('-');
            RefNeg result = new RefNeg(s.parseNumber(s.skipSpace()));
            s.expect(')');
            return result;
        }

        return new Ref(s.parseNumber(c));
    }
    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the input stream
     * 
     * @return the term
     * 
     * @throws IOException in case of an I/O error
     */
    private static ArithExpr parseTerm(ParserStream s) throws IOException {

        ArithExpr ex;
        int c = s.skipSpace();

        switch (c) {
            case '\\':
                return parseArithRef(s);
            case '(':
                ex = parse(s);
                s.expect(')');
                return ex;
            case '@':
            case '`':
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
                return new Constant(s.parseNumber(c));
            default:
                String id = s.parseId();
                s.expect('[');
                ArithExpr a = Arith.parse(s);
                s.expect(']');
                return new TableRef(id, a);
        }
    }

    /**
     * Creates a new object.
     */
    private Arith() {

        // unused
    }
    /**
     * TODO gene: missing JavaDoc
     *
     * @param x the first argument
     * @param op the operator
     * @param y the second argument
     *
     * @return the string representation
     */
    public static String toString(ArithExpr x, String op, ArithExpr y) {

        StringBuffer sb = new StringBuffer();

        if (x instanceof Plus || x instanceof Minus) {
            sb.append("(");
            sb.append(x.toString());
            sb.append(")");
        } else {
            sb.append(x.toString());
        }
        sb.append(op);
        if (y instanceof Plus || y instanceof Minus) {
            sb.append("(");
            sb.append(y.toString());
            sb.append(")");
        } else {
            sb.append(x.toString());
        }

        return sb.toString();
    }

}
