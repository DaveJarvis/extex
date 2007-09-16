/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingLeftBraceException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.DuplicateHyphenationException;
import org.extex.language.hyphenation.exception.IllegalTokenHyphenationException;
import org.extex.language.hyphenation.exception.IllegalValueHyphenationException;
import org.extex.language.hyphenation.exception.ImmutableHyphenationException;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
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
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\patterns</code>.
 * 
 * <doc name="patterns">
 * <h3>The Primitive <tt>\patterns</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;patterns&rang;
 *      &rarr; <tt>\patterns</tt> { &lang;patterns&rang; } </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
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
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * This class provides the token visitor which processes all tokens in the
     * argument of the <tt>\pattern</tt> macro.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4770 $
     */
    private class TV implements TokenVisitor<Object, Object> {

        /**
         * The field <tt>table</tt> contains the associated table.
         */
        private Language table;

        /**
         * The field <tt>tokens</tt> contains the the container for the
         * tokens.
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
         *         token.
         */
        public TV(Context context, Language table) throws CatcodeException {

            super();
            this.table = table;
            this.context = context;
            zero =
                    context.getTokenFactory().createToken(Catcode.OTHER, '0',
                        Namespace.DEFAULT_NAMESPACE);
        }

        /**
         * This visit method is invoked on an active token. In <logo>TeX</logo>
         * this is e.g. ~.
         * 
         * @param token the active token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitActive(
         *      org.extex.scanner.type.token.ActiveCharacterToken,
         *      java.lang.Object)
         */
        public Object visitActive(ActiveCharacterToken token, Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                "TTP.NonLetter", token.toString()));
        }

        /**
         * This visit method is invoked on a cr token.
         * 
         * @param token the cr token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitCr(
         *      org.extex.scanner.type.token.CrToken, java.lang.Object)
         */
        public Object visitCr(CrToken token, Object arg) throws Exception {

            throw new InterpreterException(getLocalizer().format(
                "TTP.NonLetter", token.toString()));
        }

        /**
         * This visit method is invoked on an escape token. In <logo>TeX</logo>
         * this normally means a control sequence.
         * 
         * @param token the control sequence token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitEscape(
         *      org.extex.scanner.type.token.ControlSequenceToken,
         *      java.lang.Object)
         */
        public Object visitEscape(ControlSequenceToken token, Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                "TTP.NonLetter", token.toString()));
        }

        /**
         * This visit method is invoked on a left brace token.
         * 
         * @param token the left brace token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitLeftBrace(
         *      org.extex.scanner.type.token.LeftBraceToken, java.lang.Object)
         */
        public Object visitLeftBrace(LeftBraceToken token, Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                "TTP.NonLetter", token.toString()));
        }

        /**
         * This visit method is invoked on a letter token.
         * 
         * @param token the letter token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitLetter(
         *      org.extex.scanner.type.token.LetterToken, java.lang.Object)
         */
        public Object visitLetter(LetterToken token, Object arg)
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
         * This visit method is invoked on a macro parameter token. In <logo>TeX</logo>
         * this normally is a #.
         * 
         * @param token the macro parameter token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitMacroParam(
         *      org.extex.scanner.type.token.MacroParamToken, java.lang.Object)
         */
        public Object visitMacroParam(MacroParamToken token, Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                "TTP.NonLetter", token.toString()));
        }

        /**
         * This visit method is invoked on a math shift token. In <logo>TeX</logo>
         * this normally is a $.
         * 
         * @param token the math shift token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitMathShift(
         *      org.extex.scanner.type.token.MathShiftToken, java.lang.Object)
         */
        public Object visitMathShift(MathShiftToken token, Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                "TTP.NonLetter", token.toString()));
        }

        /**
         * This visit method is invoked on an other token.
         * 
         * @param token the other token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitOther(
         *      org.extex.scanner.type.token.OtherToken, java.lang.Object)
         */
        public Object visitOther(OtherToken token, Object arg) throws Exception {

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
                }
                tokens.add(context.getTokenFactory().createToken(
                    Catcode.LETTER, c.equals(lc) ? c : lc,
                    Namespace.DEFAULT_NAMESPACE));
                letter = true;
            }

            return null;
        }

        /**
         * This method returns a non-null value to indicate the end of
         * processing.
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitRightBrace(
         *      org.extex.scanner.type.token.RightBraceToken, java.lang.Object)
         */
        public Object visitRightBrace(RightBraceToken token, Object arg)
                throws Exception {

            if (tokens.length() > 0) {
                table.addPattern(tokens);
                tokens.clear();
            }
            letter = true;
            return this;
        }

        /**
         * This visit method is invoked on a space token.
         * 
         * @param token the space token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitSpace(
         *      org.extex.scanner.type.token.SpaceToken, java.lang.Object)
         */
        public Object visitSpace(SpaceToken token, Object arg) throws Exception {

            if (tokens.length() > 0) {
                table.addPattern(tokens);
                tokens.clear();
            }
            letter = true;
            return null;
        }

        /**
         * This visit method is invoked on a sub mark token. In <logo>TeX</logo>
         * this normally is a _.
         * 
         * @param token the sub mark token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitSubMark(
         *      org.extex.scanner.type.token.SubMarkToken, java.lang.Object)
         */
        public Object visitSubMark(SubMarkToken token, Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                "TTP.NonLetter", token.toString()));
        }

        /**
         * This visit method is invoked on a sup mark token. In <logo>TeX</logo>
         * this normally is a ^.
         * 
         * @param token the sup mark token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitSupMark(
         *      org.extex.scanner.type.token.SupMarkToken, java.lang.Object)
         */
        public Object visitSupMark(SupMarkToken token, Object arg)
                throws Exception {

            throw new InterpreterException(getLocalizer().format(
                "TTP.NonLetter", token.toString()));
        }

        /**
         * This visit method is invoked on a tab mark token. In <logo>TeX</logo>
         * this normally is a &.
         * 
         * @param token the tab mark token to visit
         * @param arg the first argument to pass
         * 
         * @return some value
         * 
         * @throws Exception in case of an error
         * 
         * @see org.extex.scanner.type.token.TokenVisitor#visitTabMark(
         *      org.extex.scanner.type.token.TabMarkToken, java.lang.Object)
         */
        public Object visitTabMark(TabMarkToken token, Object arg)
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
    public Patterns(String name) {

        super(name);
    }

    /**
     * Scan the patterns for hyphenation and store this values in the
     * <code>HyphernationTable</code>. The <code>HyphernationTable</code>
     * are based on the value from <code>\language</code>.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

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
            throw new HelpingException(getLocalizer(), "TTP.DuplicatePattern");
        } catch (IllegalTokenHyphenationException e) {
            throw new HelpingException(getLocalizer(), "TTP.NonLetter");
        } catch (IllegalValueHyphenationException e) {
            throw new HelpingException(getLocalizer(), "TTP.BadPatterns");
        } catch (ImmutableHyphenationException e) {
            throw new HelpingException(getLocalizer(), "TTP.LatePatterns");
        } catch (HelpingException e) {
            throw e;
        } catch (Exception e) {
            throw new NoHelpException(e);
        }
    }

}
