/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.hyphen;

import org.extex.interpreter.Flags;
import org.extex.interpreter.Namespace;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.MissingLeftBraceException;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.DuplicateHyphenationException;
import org.extex.language.hyphenation.exception.IllegalTokenHyphenationException;
import org.extex.language.hyphenation.exception.IllegalValueHyphenationException;
import org.extex.language.hyphenation.exception.ImmutableHyphenationException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeException;
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
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive <code>\patterns</code>.
 *
 * <doc name="patterns">
 * <h3>The Primitive <tt>\patterns</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;patterns&rang;
 *      &rarr; <tt>\patterns</tt> { &lang;patterns&rang; } </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \patterns{.ach4 .ad4der .af1t}  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4770 $
 */
public class Patterns extends AbstractHyphenationCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * This class provides the token visitor which processes all tokens in the
     * argument of the <tt>\pattern</tt> macro.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4770 $
     */
    private class TV implements TokenVisitor {

        /**
         * The field <tt>table</tt> contains the associated table.
         */
        private Language table;

        /**
         * The field <tt>toks</tt> contains the the container for the tokens.
         */
        private Tokens tokens = new Tokens();

        /**
         * The field <tt>context</tt> contains the interpreter context to use.
         */
        private Context context;

        /**
         * The field <tt>zero</tt> contains the OtherToken 0.
         */
        private Token zero;

        /**
         * The field <tt>letter</tt> contains the indicator that the character
         * read recently has been a letter.
         */
        private boolean letter = true;

        /**
         * Creates a new object.
         *
         * @param context the interpreter context
         * @param table the hyphenation table to feed
         *
         * @throws CatcodeException in case of a problem when creating the zero
         *  token.
         */
        public TV(final Context context, final Language table)
                throws CatcodeException {

            super();
            this.table = table;
            this.context = context;
            zero = context.getTokenFactory().createToken(Catcode.OTHER, '0',
                    Namespace.DEFAULT_NAMESPACE);
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitActive(
         *      org.extex.scanner.type.ActiveCharacterToken,
         *      java.lang.Object)
         */
        public Object visitActive(final ActiveCharacterToken token,
                final Object arg) throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitCr(
         *      org.extex.scanner.type.CrToken,
         *      java.lang.Object)
         */
        public Object visitCr(final CrToken token, final Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitEscape(
         *      org.extex.scanner.type.ControlSequenceToken,
         *      java.lang.Object)
         */
        public Object visitEscape(final ControlSequenceToken token,
                final Object arg) throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitLeftBrace(
         *      org.extex.scanner.type.LeftBraceToken,
         *      java.lang.Object)
         */
        public Object visitLeftBrace(final LeftBraceToken token,
                final Object arg) throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitLetter(
         *      org.extex.scanner.type.LetterToken,
         *      java.lang.Object)
         */
        public Object visitLetter(final LetterToken token, final Object arg)
                throws Exception {

            if (letter) {
                tokens.add(zero);
            }

            UnicodeChar c = token.getChar();
            UnicodeChar lc = context.getLccode(c);
            if (lc == null) {
                throw new InterpreterException(getLocalizer().format(
                        "TTP.NonLetterInHyph", token.toString()));
            } else if (c.equals(lc)) {
                tokens.add(token);
            } else {
                tokens.add(context.getTokenFactory().createToken(
                        Catcode.LETTER, lc, Namespace.DEFAULT_NAMESPACE));
            }
            letter = true;
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitMacroParam(
         *      org.extex.scanner.type.MacroParamToken,
         *      java.lang.Object)
         */
        public Object visitMacroParam(final MacroParamToken token,
                final Object arg) throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitMathShift(
         *      org.extex.scanner.type.MathShiftToken,
         *      java.lang.Object)
         */
        public Object visitMathShift(final MathShiftToken token,
                final Object arg) throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitOther(
         *      org.extex.scanner.type.OtherToken,
         *      java.lang.Object)
         */
        public Object visitOther(final OtherToken token, final Object arg)
                throws Exception {

            UnicodeChar c = token.getChar();

            if (c.getCodePoint() == '.') {
                if (letter) {
                    tokens.add(zero);
                }
                tokens.add(context.getTokenFactory().createToken(
                        Catcode.LETTER, '.', Namespace.DEFAULT_NAMESPACE));
                letter = true;
            } else if (letter) {
                if (c.isDigit()) {
                    tokens.add(token);
                    letter = false;
                } else {
                    throw new InterpreterException(getLocalizer().format(
                            "TTP.NonLetter", token.toString()));
                }
            } else {
                UnicodeChar lc = context.getLccode(c);
                if (lc == null) {
                    throw new InterpreterException(getLocalizer().format(
                            "TTP.NonLetterInHyph", token.toString()));
                } else {
                    tokens.add(context.getTokenFactory().createToken(
                            Catcode.LETTER, c.equals(lc) ? c : lc,
                            Namespace.DEFAULT_NAMESPACE));
                }
                letter = true;
            }

            return null;
        }

        /**
         * This method returns a non-null value to indicate the end of
         * processing.
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitRightBrace(
         *      org.extex.scanner.type.RightBraceToken,
         *      java.lang.Object)
         */
        public Object visitRightBrace(final RightBraceToken token,
                final Object arg) throws Exception {

            if (tokens.length() > 0) {
                table.addPattern(tokens);
                tokens.clear();
            }
            letter = true;
            return this;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSpace(
         *      org.extex.scanner.type.SpaceToken,
         *      java.lang.Object)
         */
        public Object visitSpace(final SpaceToken token, final Object arg)
                throws Exception {

            if (tokens.length() > 0) {
                table.addPattern(tokens);
                tokens.clear();
            }
            letter = true;
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSubMark(
         *      org.extex.scanner.type.SubMarkToken,
         *      java.lang.Object)
         */
        public Object visitSubMark(final SubMarkToken token, final Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSupMark(
         *      org.extex.scanner.type.SupMarkToken,
         *      java.lang.Object)
         */
        public Object visitSupMark(final SupMarkToken token, final Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitTabMark(
         *      org.extex.scanner.type.TabMarkToken,
         *      java.lang.Object)
         */
        public Object visitTabMark(final TabMarkToken token, final Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter", token.toString()));
        }
    }

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Patterns(final String name) {

        super(name);
    }

    /**
     * Scan the patterns for hyphenation and store this values
     * in the <code>HyphernationTable</code>.
     * The <code>HyphernationTable</code> are based on the
     * value from <code>\language</code>.
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        Token t = source.getNonSpace(context);
        if (t == null) {
            throw new EofException(printableControlSequence(context));
        } else if (!t.isa(Catcode.LEFTBRACE)) {
            throw new MissingLeftBraceException("???");
        }

        try {
            TV tv = new TV(context, getHyphenationTable(context));

            do {
                t = source.getToken(context);
            } while (t != null && t.visit(tv, null) == null);

        } catch (DuplicateHyphenationException e) {
            throw new InterpreterException(getLocalizer().format(
                    "TTP.DuplicatePattern"));
        } catch (IllegalTokenHyphenationException e) {
            throw new InterpreterException(getLocalizer().format(
                    "TTP.NonLetter"));
        } catch (IllegalValueHyphenationException e) {
            throw new InterpreterException(getLocalizer().format(
                    "TTP.BadPatterns"));
        } catch (ImmutableHyphenationException e) {
            throw new InterpreterException(getLocalizer().format(
                    "TTP.LatePatterns"));
        } catch (InterpreterException e) {
            throw e;
        } catch (Exception e) {
            throw new InterpreterException(e);
        }
    }

}
