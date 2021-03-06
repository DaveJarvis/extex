/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.SyntaxException;
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.ParserStream;
import org.extex.ocpware.type.OcpCode;

/**
 * This is the abstract base class for arithmetic expressions. It provides some
 * methods for parsing and pretty-printing.
 * <p>
 * The arithmetic expression has the following syntax:
 * </p>
 * 
 * <pre class="syntax">
 *    &lang;arith expression&rang;
 *      &rarr; &lang;n&rang;
 *       | {@code \}&lang;n&rang;
 *       | {@code \$}
 *       | {@code \($-}&lang;n&rang;{@code )}
 *       | &lang;arith expression&rang; {@code +} &lang;arith expression&rang;
 *       | &lang;arith expression&rang; {@code -} &lang;arith expression&rang;
 *       | &lang;arith expression&rang; {@code *} &lang;arith expression&rang;
 *       | &lang;arith expression&rang; {@code div:} &lang;arith expression&rang;
 *       | &lang;arith expression&rang; {@code mod:} &lang;arith expression&rang;
 *       | &lang;id&rang;{@code [}&lang;arith expression&rang;{@code ]}
 *       | {@code (}&lang;arith expression&rang;{@code )}  </pre>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class ArithExpr {

    /**
     * This enumeration provides constants for addition and subtraction.
     */
    private enum Op {

        /**
         * The field {@code MINUS} contains the subtraction.
         */
        MINUS {

            /**
        *      org.extex.ocpware.compiler.arith.ArithExpr,
             *      org.extex.ocpware.compiler.arith.ArithExpr)
             */
            @Override
            public ArithExpr eval(ArithExpr left, ArithExpr right) {

                return new BinaryOp(OcpCode.OP_SUB, " - ", left, right);
            }
        },

        /**
         * The field {@code PLUS} contains the addition.
         */
        PLUS {

            /**
        *      org.extex.ocpware.compiler.arith.ArithExpr,
             *      org.extex.ocpware.compiler.arith.ArithExpr)
             */
            @Override
            public ArithExpr eval(ArithExpr left, ArithExpr right) {

                return new BinaryOp(OcpCode.OP_ADD, " + ", left, right);
            }
        };

        /**
         * This methods creates an arithmetic expression for two arguments.
         * 
         * @param left the left argument
         * @param right the right argument
         * 
         * @return the combined term
         */
        public abstract ArithExpr eval(ArithExpr left, ArithExpr right);

    }

    /**
     * Parse an arithmetic expression.
     * 
     * @param s the input stream
     * 
     * @return the term found
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public static ArithExpr parse(ParserStream s)
            throws IOException,
                SyntaxException {

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
                    left = new BinaryOp(OcpCode.OP_MULT, " * ", left,
                        parseTerm(s), false);
                    break;
                case 'd':
                    s.unread(c);
                    t = s.parseId();
                    if ("div:".equals(t)) {
                        left = new BinaryOp(OcpCode.OP_DIV, " DIV: ", left,
                            parseTerm(s), false);
                    } else {
                        s.unread(t.getBytes());
                    }
                    break;
                case 'm':
                    s.unread(c);
                    t = s.parseId();
                    if ("mod:".equals(t)) {
                        left = new BinaryOp(OcpCode.OP_MOD, " MOD: ", left,
                            parseTerm(s), false);
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
     * Parse a reference. The starting backslash has already been consumed.
     * 
     * @param s the input stream
     * 
     * @return the arithmetic reference
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private static ArithExpr parseArithLast(ParserStream s)
            throws IOException,
                SyntaxException {

        int c = s.skipSpace();

        if (c == '$') {
            return new LastChar(0);
        } else if (c == '(') {
            s.expect('$');
            s.expect('-');
            LastChar result = new LastChar(s.parseNumber(s.skipSpace()));
            s.expect(')');
            return result;
        }

        return new Char(s.parseNumber(c));
    }

    /**
     * Parse a term.
     * 
     * @param s the input stream
     * 
     * @return the term
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private static ArithExpr parseTerm(ParserStream s)
            throws IOException,
                SyntaxException {

        ArithExpr ex;
        int c = s.skipSpace();

        switch (c) {
            case '\\':
                return parseArithLast(s);
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
                s.unread(c);
                String id = s.parseId();
                s.expect('[');
                ArithExpr a = ArithExpr.parse(s);
                s.expect(']');
                return new TableRef(id, a);
        }
    }

    /**
     * Create a string representation for an binary operation.
     * 
     * @param x the first argument
     * @param op the operator
     * @param y the second argument
     * 
     * @return the string representation
     */
    public static String toString(ArithExpr x, String op, ArithExpr y) {

        StringBuilder sb = new StringBuilder();

        if (x.needsParen()) {
            sb.append("(");
            sb.append(x.toString());
            sb.append(")");
        } else {
            sb.append(x.toString());
        }
        sb.append(op);
        if (y.needsParen()) {
            sb.append("(");
            sb.append(y.toString());
            sb.append(")");
        } else {
            sb.append(x.toString());
        }

        return sb.toString();
    }


    public ArithExpr() {

    }

    /**
     * Return the indicator that parentheses are needed when printed in a wider
     * arithmetic context.
     * 
     * @return the indicator
     */
    public boolean needsParen() {

        return false;
    }

    /**
     * Compile the arithmetic expression into a set of &Omega;CP instructions.
     * 
     * @param cs the compiler state
     * 
     * @throws ArgmentTooBigException in case that an argument is encountered
     *         which does not fit into two bytes
     * @throws IOException in case of an I/O error
     * @throws TableNotDefinedException in case that no matching table is known
     *         for a symbolic table reference
     */
    abstract void outExpr(CompilerState cs)
            throws ArgmentTooBigException,
                IOException,
                TableNotDefinedException;

}
