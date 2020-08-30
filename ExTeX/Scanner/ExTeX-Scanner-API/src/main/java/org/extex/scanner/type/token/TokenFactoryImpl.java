/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.type.token;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.extex.core.UnicodeChar;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.api.exception.CatcodeVisitorException;
import org.extex.scanner.api.exception.CatcodeWrongLengthException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeVisitor;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This is a implementation of a token factory. This means that the factory
 * pattern is applied here. This pattern opens the possibility to cache the
 * instances for Tokens to reduce the number of objects present in the system.
 * 
 * <h2>The Visitor Pattern</h2>
 * <p>
 * In addition the visitor pattern is used to select the appropriate
 * instantiation method. The visit methods are not meant to be used externally.
 * They are purely internal. Despite their general definition the {@code visit}
 * methods are in fact used in the following way:
 * </p>
 * 
 * <pre>
 *  Token visit<i>*</i>(String value, UnicodeChar character) throws CatcodeException
 * </pre>
 * 
 * <p>
 * This means that they are expected to return the new token. The first argument
 * is the value, which is mainly meaningful for control sequence tokens. The
 * third argument contains the Unicode character for single letter tokens.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TokenFactoryImpl
        implements
            TokenFactory,
            CatcodeVisitor<Token, String, UnicodeChar, String> {

    /**
     * The field {@code crToken} contains the one and only cr token in the
     * system.
     */
    private static final Token CR_TOKEN = new CrToken(null);

    /**
     * The field {@code spaceToken} contains the one and only space token in
     * the system.
     */
    private static final Token SPACE_TOKEN = new SpaceToken(null);

    /**
     * The field {@code activeCache} contains the cache for active character
     * tokens.
     */
    private Map<String, Map<UnicodeChar, WeakReference<ActiveCharacterToken>>> activeCache =
            new HashMap<String, Map<UnicodeChar, WeakReference<ActiveCharacterToken>>>();

    /**
     * The field {@code csCache} contains the cache for control sequence
     * tokens.
     */
    private Map<String, Map<String, WeakReference<ControlSequenceToken>>> csCache =
            new HashMap<String, Map<String, WeakReference<ControlSequenceToken>>>();

    /**
     * The field {@code leftBraceCache} contains the cache for left brace
     * tokens.
     */
    private Map<UnicodeChar, WeakReference<LeftBraceToken>> leftBraceCache =
            new HashMap<UnicodeChar, WeakReference<LeftBraceToken>>();

    /**
     * The field {@code letterCache} contains the cache for letter tokens.
     */
    private Map<UnicodeChar, WeakReference<LetterToken>> letterCache =
            new HashMap<UnicodeChar, WeakReference<LetterToken>>();

    /**
     * The field {@code macroParamCache} contains the cache for macro parameter
     * tokens.
     */
    private Map<UnicodeChar, WeakReference<MacroParamToken>> macroParamCache =
            new HashMap<UnicodeChar, WeakReference<MacroParamToken>>();

    /**
     * The field {@code mathShiftCache} contains the cache for math shift
     * tokens.
     */
    private Map<UnicodeChar, WeakReference<MathShiftToken>> mathShiftCache =
            new HashMap<UnicodeChar, WeakReference<MathShiftToken>>();

    /**
     * The field {@code otherCache} contains the cache for other tokens.
     */
    private Map<UnicodeChar, WeakReference<OtherToken>> otherCache =
            new HashMap<UnicodeChar, WeakReference<OtherToken>>();

    /**
     * The field {@code rightBraceCache} contains the cache for right brace
     * tokens.
     */
    private Map<UnicodeChar, WeakReference<RightBraceToken>> rightBraceCache =
            new HashMap<UnicodeChar, WeakReference<RightBraceToken>>();

    /**
     * The field {@code subMarkCache} contains the cache for sub mark tokens.
     */
    private Map<UnicodeChar, WeakReference<SubMarkToken>> subMarkCache =
            new HashMap<UnicodeChar, WeakReference<SubMarkToken>>();

    /**
     * The field {@code supMarkCache} contains the cache for super mark tokens.
     */
    private Map<UnicodeChar, WeakReference<SupMarkToken>> supMarkCache =
            new HashMap<UnicodeChar, WeakReference<SupMarkToken>>();

    /**
     * The field {@code tabMarkCache} contains the cache for tab mark tokens.
     */
    private Map<UnicodeChar, WeakReference<TabMarkToken>> tabMarkCache =
            new HashMap<UnicodeChar, WeakReference<TabMarkToken>>();

    /**
     * Create a new {@link org.extex.scanner.type.token.Token Token} of the
     * appropriate kind. Tokens are immutable (no setters) thus the factory
     * pattern can be applied.
     * 
     * @param code the category code
     * @param c the character value
     * @param namespace the name space to use
     * 
     * @return the new token
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.token.TokenFactory#createToken(org.extex.scanner.type.Catcode,
     *      int, java.lang.String)
     */
    @Override
    public Token createToken(Catcode code, int c, String namespace)
            throws CatcodeException {

        try {
            return (Token) code.visit(this, null, UnicodeChar.get(c),
                namespace);
        } catch (CatcodeException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // this should not happen
            throw new CatcodeException(e);
        }
    }

    /**
     * Get an instance of a token with a given Catcode and Unicode character
     * value.
     * 
     * @param code the catcode
     * @param c the Unicode character value
     * @param namespace the name space for the token. This is relevant for
     *        ACTIVE and ESCAPE category codes only.
     * 
     * @return the appropriate token
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.token.TokenFactory#createToken(org.extex.scanner.type.Catcode,
     *      org.extex.core.UnicodeChar, java.lang.String)
     */
    @Override
    public Token createToken(Catcode code, UnicodeChar c, String namespace)
            throws CatcodeException {

        try {
            return (Token) code.visit(this, null, c, namespace);
        } catch (CatcodeException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // this should not happen
            throw new CatcodeException(e);
        }
    }

    /**
     * Get an instance of a token with a given Catcode and value.
     * 
     * @param code the catcode
     * @param esc the Unicode character value of the escape character
     * @param value the value
     * @param namespace the name space for the token. This is relevant for
     *        ACTIVE and ESCAPE category codes only.
     * 
     * @return the appropriate token
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.token.TokenFactory#createToken(org.extex.scanner.type.Catcode,
     *      org.extex.core.UnicodeChar, java.lang.String, java.lang.String)
     */
    @Override
    public Token createToken(Catcode code, UnicodeChar esc, String value,
            String namespace) throws CatcodeException {

        try {
            return (Token) code.visit(this, value, esc, namespace);
        } catch (CatcodeException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // this should not happen
            throw new CatcodeException(e);
        }
    }

    /**
     * Convert a character sequence to a list of tokens.
     * <p>
     * Each character of the string is converted into a {@code OtherToken}
     * and added to the internal list. An exception is made for spaces which are
     * converted into a {@code SpaceToken}.
     * </p>
     * 
     * @param s the character sequence to translate to tokens
     * 
     * @return the token list
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.token.TokenFactory#toTokens(java.lang.CharSequence)
     */
    @Override
    public Tokens toTokens(CharSequence s) throws CatcodeException {

        Tokens tokens = new Tokens();
        if (s == null) {
            return tokens;
        }

        int len = s.length();

        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            tokens.add(createToken((c == ' ' ? Catcode.SPACE : Catcode.OTHER),
                c, Namespace.DEFAULT_NAMESPACE));
        }

        return tokens;
    }

    /**
     * Convert a long value into a list of tokens.
     * <p>
     * Each character is converted into a {@code OtherToken} and added to
     * the internal list.
     * </p>
     * 
     * @param l the value to convert
     * 
     * @return the token list
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.token.TokenFactory#toTokens(long)
     */
    @Override
    public Tokens toTokens(long l) throws CatcodeException {

        Tokens tokens = new Tokens();
        StringBuilder s = new StringBuilder();
        s.append(l);

        int len = s.length();

        for (int i = 0; i < len; i++) {
            tokens.add(createToken(Catcode.OTHER, s.charAt(i),
                Namespace.DEFAULT_NAMESPACE));
        }

        return tokens;
    }

    /**
     * Active characters are cached. Thus a look-up in the cache precedes the
     * creation of a new token.
     * 
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the active token
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitActive(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitActive(String value, UnicodeChar uchar, String namespace)
            throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        Map<UnicodeChar, WeakReference<ActiveCharacterToken>> map =
                activeCache.get(namespace);

        if (map == null) {
            map =
                    new HashMap<UnicodeChar, WeakReference<ActiveCharacterToken>>();
            activeCache.put(namespace, map);
        }

        WeakReference<ActiveCharacterToken> wr = map.get(uc);
        ActiveCharacterToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new ActiveCharacterToken(uc, namespace);
            map.put(uc, new WeakReference<ActiveCharacterToken>(token));
        }

        return token;
    }

    /**
     * Comments are ignored thus {@code null} is returned in any case.
     * 
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return {@code null}
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitComment(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitComment(String value, UnicodeChar uchar, String namespace) {

        return null;
    }

    /**
     * There is only one CrToken.
     * 
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the CR token
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitCr(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitCr(String value, UnicodeChar uchar, String namespace) {

        return CR_TOKEN;
    }

    /**
     * This visit method is invoked on an escape token. In TeX this normally means a control sequence.
     * 
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitEscape(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitEscape(String value, UnicodeChar uchar, String namespace)
            throws CatcodeException {

        String name;
        if (value != null) {
            name = value;
        } else if (uchar != null) {
            name = uchar.toString();
        } else {
            throw new CatcodeVisitorException();
        }

        Map<String, WeakReference<ControlSequenceToken>> map =
                csCache.get(namespace);

        if (map == null) {
            map = new HashMap<String, WeakReference<ControlSequenceToken>>();
            csCache.put(namespace, map);
        }

        WeakReference<ControlSequenceToken> wr = map.get(name);
        ControlSequenceToken token = null;

        if (wr != null) {
            token = wr.get();
        }
        if (token == null) {
            token = new ControlSequenceToken(uchar, name, namespace);
            map.put(name, new WeakReference<ControlSequenceToken>(token));
        }

        return token;
    }

    /**
     * Ignored characters are simply ignored;-)
     * 
     * @param value the string value token or {@code null}
     * @param uchar the requested character code
     * @param namespace the third argument is ignored
     * 
     * @return {@code null}
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitIgnore(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitIgnore(String value, UnicodeChar uchar, String namespace) {

        return null;
    }

    /**
     * Invalid characters are ignored; even without any error message.
     * 
     * @param value the string value token or {@code null}
     * @param uchar the requested character code
     * @param namespace the third argument is ignored
     * 
     * @return {@code null}
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitInvalid(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitInvalid(String value, UnicodeChar uchar, String namespace) {

        return null;
    }

    /**
     * A left brace token is expected to take a single character only.
     * 
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @throws CatcodeException in case of an error
     * 
     * @return the token requested
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitLeftBrace(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitLeftBrace(String value, UnicodeChar uchar,
            String namespace) throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<LeftBraceToken> wr = leftBraceCache.get(uc);
        LeftBraceToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new LeftBraceToken(uc);
            leftBraceCache.put(uc, new WeakReference<LeftBraceToken>(token));
        }

        return token;
    }

    /**
     * A letter token is expected to take a single character only.
     * 
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitLetter(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitLetter(String value, UnicodeChar uchar, String namespace)
            throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<LetterToken> wr = letterCache.get(uc);
        LetterToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new LetterToken(uc);
            letterCache.put(uc, new WeakReference<LetterToken>(token));
        }

        return token;
    }

    /**
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitMacroParam(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitMacroParam(String value, UnicodeChar uchar,
            String namespace) throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<MacroParamToken> wr = macroParamCache.get(uc);
        MacroParamToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new MacroParamToken(uc);
            macroParamCache.put(uc, new WeakReference<MacroParamToken>(token));
        }

        return token;
    }

    /**
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitMathShift(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitMathShift(String value, UnicodeChar uchar,
            String namespace) throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<MathShiftToken> wr = mathShiftCache.get(uc);
        MathShiftToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new MathShiftToken(uc);
            mathShiftCache.put(uc, new WeakReference<MathShiftToken>(token));
        }

        return token;
    }

    /**
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitOther(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitOther(String value, UnicodeChar uchar, String namespace)
            throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<OtherToken> wr = otherCache.get(uc);
        OtherToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new OtherToken(uc);
            otherCache.put(uc, new WeakReference<OtherToken>(token));
        }

        return token;
    }

    /**
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitRightBrace(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitRightBrace(String value, UnicodeChar uchar,
            String namespace) throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<RightBraceToken> wr = rightBraceCache.get(uc);
        RightBraceToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new RightBraceToken(uc);
            rightBraceCache.put(uc, new WeakReference<RightBraceToken>(token));
        }

        return token;
    }

    /**
     * There is only one space token. It has the character code 32.
     * 
     * @param value the string value token or {@code null}
     * @param uchar the requested character code
     * @param namespace the third argument is ignored
     * 
     * @return the space token.
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitSpace(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * @see "The TeXbook [Chapter 8, p. 47]"
     */
    @Override
    public Token visitSpace(String value, UnicodeChar uchar, String namespace) {

        return SPACE_TOKEN;
    }

    /**
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitSubMark(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitSubMark(String value, UnicodeChar uchar, String namespace)
            throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<SubMarkToken> wr = subMarkCache.get(uc);
        SubMarkToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new SubMarkToken(uc);
            subMarkCache.put(uc, new WeakReference<SubMarkToken>(token));
        }

        return token;
    }

    /**
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitSupMark(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitSupMark(String value, UnicodeChar uchar, String namespace)
            throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<SupMarkToken> wr = supMarkCache.get(uc);
        SupMarkToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new SupMarkToken(uc);
            supMarkCache.put(uc, new WeakReference<SupMarkToken>(token));
        }

        return token;
    }

    /**
     * @param value the string value of the token
     * @param uchar the character value of the token
     * @param namespace the name space of the token
     * 
     * @return the token requested
     * 
     * @throws CatcodeException in case of an error
     * 
     * @see org.extex.scanner.type.CatcodeVisitor#visitTabMark(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public Token visitTabMark(String value, UnicodeChar uchar, String namespace)
            throws CatcodeException {

        UnicodeChar uc;
        if (uchar != null) {
            uc = uchar;
        } else if (value != null) {
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        WeakReference<TabMarkToken> wr = tabMarkCache.get(uc);
        TabMarkToken token = (wr != null ? wr.get() : null);

        if (token == null) {
            token = new TabMarkToken(uc);
            tabMarkCache.put(uc, new WeakReference<TabMarkToken>(token));
        }

        return token;
    }

}
