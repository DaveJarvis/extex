/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.file.xslt;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.token.ActiveCharacterToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.CrToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.MacroParamToken;
import org.extex.scanner.type.token.MathShiftToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.SubMarkToken;
import org.extex.scanner.type.token.SupMarkToken;
import org.extex.scanner.type.token.TabMarkToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenVisitor;
import org.extex.typesetter.Typesetter;


/**
 * This class reads token until a null token
 * and return the text (toString()) for each token.
 * <p>For test cases only</p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class ReadTokenUntilNull extends AbstractCode {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new object.
     * @param codeName  the CodeName
     */
    public ReadTokenUntilNull(String codeName) {

        super(codeName);
    }

    /**
     * initial size for the buffer
     */
    private static final int INITSIZE = 1024;

    /**
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        try {
            StringBuffer buf = new StringBuffer(INITSIZE);

            TokenVisitor visitor = new MyTokenVisitor();

            Token t = null;
            do {
                t = source.getToken(context);
                if (t != null) {
                    buf.append(t.visit(visitor, null));
                }

            } while (t != null);

            TokenStreamFactory factory = source.getTokenStreamFactory();

            source.addStream(factory.newInstance(buf.toString()));
        } catch (Exception e) {
            throw new InterpreterException(e);
        }
    }

    /**
     * TokenVistor
     * <p>
     * Returns the content of a token as special String.
     * </p>
     */
    private class MyTokenVisitor implements TokenVisitor {

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitActive(
         *      org.extex.scanner.type.token.ActiveCharacterToken,
         *      java.lang.Object)
         */
        public Object visitActive(ActiveCharacterToken token,
                Object arg) throws Exception {

            return "[A:" + token.toText() + "]";
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitCr(
         *      org.extex.scanner.type.token.CrToken,
         *      java.lang.Object)
         */
        public Object visitCr(CrToken token, Object arg)
                throws Exception {

            return "[CR]";
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitEscape(
         *      org.extex.scanner.type.token.ControlSequenceToken,
         *      java.lang.Object)
         */
        public Object visitEscape(ControlSequenceToken token,
                Object arg) throws Exception {

            return "/" + token.getName() + " ";
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitLeftBrace(
         *      org.extex.scanner.type.token.LeftBraceToken,
         *      java.lang.Object)
         */
        public Object visitLeftBrace(LeftBraceToken token,
                Object arg) throws Exception {

            return "(";
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitLetter(
         *      org.extex.scanner.type.token.LetterToken,
         *      java.lang.Object)
         */
        public Object visitLetter(LetterToken token, Object arg)
                throws Exception {

            return token.toText();
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitMacroParam(
         *      org.extex.scanner.type.token.MacroParamToken,
         *      java.lang.Object)
         */
        public Object visitMacroParam(MacroParamToken token,
                Object arg) throws Exception {

            return "[M:" + token.toText() + "]";
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitMathShift(
         *      org.extex.scanner.type.token.MathShiftToken,
         *      java.lang.Object)
         */
        public Object visitMathShift(MathShiftToken token,
                Object arg) throws Exception {

            return token.toText();
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitOther(
         *      org.extex.scanner.type.token.OtherToken,
         *      java.lang.Object)
         */
        public Object visitOther(OtherToken token, Object arg)
                throws Exception {

            return token.toText();
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitRightBrace(
         *      org.extex.scanner.type.token.RightBraceToken,
         *      java.lang.Object)
         */
        public Object visitRightBrace(RightBraceToken token,
                Object arg) throws Exception {

            return ")";
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSpace(
         *      org.extex.scanner.type.token.SpaceToken,
         *      java.lang.Object)
         */
        public Object visitSpace(SpaceToken token, Object arg)
                throws Exception {

            return " ";
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSubMark(
         *      org.extex.scanner.type.token.SubMarkToken,
         *      java.lang.Object)
         */
        public Object visitSubMark(SubMarkToken token, Object arg)
                throws Exception {

            return token.toText();
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSupMark(
         *      org.extex.scanner.type.token.SupMarkToken,
         *      java.lang.Object)
         */
        public Object visitSupMark(SupMarkToken token, Object arg)
                throws Exception {

            return token.toText();
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitTabMark(
         *      org.extex.scanner.type.token.TabMarkToken,
         *      java.lang.Object)
         */
        public Object visitTabMark(TabMarkToken token, Object arg)
                throws Exception {

            return token.toText();
        }
    }
}
