/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.expression.term;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.expression.ConstantFunction;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.ETypeParser;
import org.extex.interpreter.expression.Evaluator;
import org.extex.interpreter.expression.UnaryFunction;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class implements the supporting functions for the date type
 * {@linkplain org.extex.interpreter.expression.term.TDouble TDouble}
 * for the expression evaluator.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4787 $
 */
public final class TDoubleParser implements ETypeParser {

    /**
     * Creates a new object.
     */
    public TDoubleParser() {

        super();
    }

    /**
     * Try to convert some code into a proper data type.
     *
     * @param code the code to convert
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the converted value or <code>null</code> if the conversion
     *  could not be performed
     *
     * @see org.extex.interpreter.expression.ETypeParser#convert(
     *      org.extex.interpreter.type.Code,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public EType convert(Code code, Context context,
            TokenSource source, Typesetter typesetter) throws HelpingException, TypesetterException {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.ETypeParser#parse(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public EType parse(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        boolean period = false;
        StringBuffer sb = new StringBuffer();
        Token t = source.getNonSpace(context);
        char c;

        while (t instanceof OtherToken) {
            c = (char) t.getChar().getCodePoint();
            switch (c) {
                case '.':
                    if (period) {
                        source.push(t);
                        t = null;
                        break;
                    }
                    period = true;
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
                    sb.append(c);
                    t = source.scanToken(context);
                    break;
                default:
                    source.push(t);
                    t = null;
            }
        }

        if (sb.length() == 0) {
            return null;
        } else if (period) {
            return new TDouble(Double.parseDouble(sb.toString()));
        } else {
            return new TCount(Long.parseLong(sb.toString()));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.ETypeParser#registered(
     *      org.extex.interpreter.expression.Evaluator)
     */
    public void registered(Evaluator evaluator) {

        //
        //        functions.put("cos", new Function1() {
        //
        //            /**
        //             * Compute the cos value.
        //             *
        //             * @see org.extex.interpreter.type.dimen.parser.Function1#apply(
        //             *      org.extex.interpreter.type.dimen.parser.Accumulator)
        //             */
        //            public void apply(Terminal accumulator)
        //                    throws InterpreterException {
        //
        //                //                if (accumulator.sp != 0) {
        //                //                    throw new HelpingException(LocalizerFactory
        //                //                            .getLocalizer(Evaluator.class.getName()),
        //                //                            "NonScalar", "cos", accumulator.toString());
        //                //                }
        //                //                double x = ((double) accumulator.value) / ScaledNumber.ONE;
        //                //                accumulator.value = (long) (ScaledNumber.ONE * Math.cos(x));
        //            }
        //        });
        //
        //        functions.put("sin", new Function1() {
        //
        //            /**
        //             * Compute the sin value.
        //             *
        //             * @see org.extex.interpreter.type.dimen.parser.Function1#apply(
        //             *      org.extex.interpreter.type.dimen.parser.Accumulator)
        //             */
        //            public void apply(Terminal accumulator)
        //                    throws InterpreterException {
        //
        //                //                if (accumulator.sp != 0) {
        //                //                    throw new HelpingException(LocalizerFactory
        //                //                            .getLocalizer(Evaluator.class.getName()),
        //                //                            "NonScalar", "sin", accumulator.toString());
        //                //                }
        //                //                double x = ((double) accumulator.value) / ScaledNumber.ONE;
        //                //                accumulator.value = (long) (ScaledNumber.ONE * Math.sin(x));
        //            }
        //        });
        //
        //        functions.put("tan", new Function1() {
        //
        //            /**
        //             * Compute the tan value.
        //             *
        //             * @see org.extex.interpreter.type.dimen.parser.Function1#apply(
        //             *      org.extex.interpreter.type.dimen.parser.Accumulator)
        //             */
        //            public void apply(Terminal accumulator)
        //                    throws InterpreterException {
        //
        //                //                if (accumulator.sp != 0) {
        //                //                    throw new HelpingException(LocalizerFactory
        //                //                            .getLocalizer(Evaluator.class.getName()),
        //                //                            "NonScalar", "tan", accumulator.toString());
        //                //                }
        //                //                double x = ((double) accumulator.value) / ScaledNumber.ONE;
        //                //                accumulator.value = (long) (ScaledNumber.ONE * Math.tan(x));
        //            }
        //        });
        //
        evaluator.register("pi", new ConstantFunction() {

            /**
             * Compute the value of pi.
             */
            public EType apply() throws HelpingException {

                return new TDouble(Math.PI);
            }
        });

        evaluator.register("float", new UnaryFunction() {

            /**
             * {@inheritDoc}
             *
             * @see org.extex.interpreter.expression.UnaryFunction#apply(
             *      org.extex.interpreter.expression.EType)
             */
            public EType apply(EType accumulator)
                    throws HelpingException {

                return new TDouble().set(accumulator);
            }
        });

    }

}
