/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.CatcodeVisitor;
import org.extex.scanner.type.CatcodeVisitorException;
import org.extex.scanner.type.CatcodeWrongLengthException;
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
 * They are purely internal.
 * Despite their general definition the <tt>visit</tt>
 * methods are in fact used in the following way:
 * </p>
 *
 * <pre>
 *  Token visit<i>*</i>(String value, UnicodeChar character) throws CatcodeException
 * </pre>
 *
 * <p>
 * This means that they are expected to return the new token. The first
 * argument is the value, which is mainly meaningful for control sequence
 * tokens. The third argument contains the Unicode character for single letter
 * tokens.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4738 $
 */
public class TokenFactoryImpl implements TokenFactory, CatcodeVisitor {

    /**
     * The field <tt>crToken</tt> contains the one and only cr token in the
     * system.
     */
    private static final Token CR_TOKEN = new CrToken(null);

    /**
     * The field <tt>spaceToken</tt> contains the one and only space token in
     * the system.
     */
    private static final Token SPACE_TOKEN = new SpaceToken(null);

    /**
     * The field <tt>activeCache</tt> contains the cache for active character
     * tokens.
     */
    private Map<String, Map<UnicodeChar, WeakReference<ActiveCharacterToken>>> activeCache =
            new HashMap<String, Map<UnicodeChar, WeakReference<ActiveCharacterToken>>>();

    /**
     * The field <tt>csCache</tt> contains the cache for control sequence
     * tokens.
     */
    private Map<String, Map<String, WeakReference<ControlSequenceToken>>> csCache =
            new HashMap<String, Map<String, WeakReference<ControlSequenceToken>>>();

    /**
     * The field <tt>leftBraceCache</tt> contains the cache for left brace
     * tokens.
     */
    private Map<UnicodeChar, WeakReference<LeftBraceToken>> leftBraceCache =
            new HashMap<UnicodeChar, WeakReference<LeftBraceToken>>();

    /**
     * The field <tt>letterCache</tt> contains the cache for letter tokens.
     */
    private Map<UnicodeChar, WeakReference<LetterToken>> letterCache =
            new HashMap<UnicodeChar, WeakReference<LetterToken>>();

    /**
     * The field <tt>macroParamCache</tt> contains the cache for macro parameter
     * tokens.
     */
    private Map<UnicodeChar, WeakReference<MacroParamToken>> macroParamCache =
            new HashMap<UnicodeChar, WeakReference<MacroParamToken>>();

    /**
     * The field <tt>mathShiftCache</tt> contains the cache for math shift
     * tokens.
     */
    private Map<UnicodeChar, WeakReference<MathShiftToken>> mathShiftCache =
            new HashMap<UnicodeChar, WeakReference<MathShiftToken>>();

    /**
     * The field <tt>otherCache</tt> contains the cache for other tokens.
     */
    private Map<UnicodeChar, WeakReference<OtherToken>> otherCache =
            new HashMap<UnicodeChar, WeakReference<OtherToken>>();

    /**
     * The field <tt>rightBraceCache</tt> contains the cache for right brace
     * tokens.
     */
    private Map<UnicodeChar, WeakReference<RightBraceToken>> rightBraceCache =
            new HashMap<UnicodeChar, WeakReference<RightBraceToken>>();

    /**
     * The field <tt>subMarkCache</tt> contains the cache for sub mark tokens.
     */
    private Map<UnicodeChar, WeakReference<SubMarkToken>> subMarkCache =
            new HashMap<UnicodeChar, WeakReference<SubMarkToken>>();

    /**
     * The field <tt>supMarkCache</tt> contains the cache for super mark tokens.
     */
    private Map<UnicodeChar, WeakReference<SupMarkToken>> supMarkCache =
            new HashMap<UnicodeChar, WeakReference<SupMarkToken>>();

    /**
     * The field <tt>tabMarkCache</tt> contains the cache for tab mark tokens.
     */
    private Map<UnicodeChar, WeakReference<TabMarkToken>> tabMarkCache =
            new HashMap<UnicodeChar, WeakReference<TabMarkToken>>();

    /**
     * Creates a new object.
     */
    public TokenFactoryImpl() {

        super();
    }

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
     * @see org.extex.scanner.type.token.TokenFactory#createToken(
     *      org.extex.scanner.type.Catcode,
     *      int,
     *      java.lang.String)
     */
    public Token createToken(Catcode code, int c,
            String namespace) throws CatcodeException {

        try {
            return (Token) code.visit(this, null, //
                UnicodeChar.get(c), namespace);
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
     *   ACTIVE and ESCAPE category codes only.
     *
     * @return the appropriate token
     *
     * @throws CatcodeException in case of an error
     *
     * @see org.extex.scanner.type.token.TokenFactory#createToken(
     *      org.extex.scanner.type.Catcode,
     *      org.extex.core.UnicodeChar,
     *      java.lang.String)
     */
    public Token createToken(Catcode code, UnicodeChar c,
            String namespace) throws CatcodeException {

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
     *  ACTIVE and ESCAPE category codes only.
     *
     * @return the appropriate token
     *
     * @throws CatcodeException in case of an error
     *
     * @see org.extex.scanner.type.token.TokenFactory#createToken(
     *      org.extex.scanner.type.Catcode,
     *      org.extex.core.UnicodeChar,
     *      java.lang.String,
     *      java.lang.String)
     */
    public Token createToken(Catcode code, UnicodeChar esc,
            String value, String namespace) throws CatcodeException {

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
     * Each character of the string is converted into a <code>OtherToken</code>
     * and added to the internal list. An exception is made for spaces which
     * are converted into a <code>SpaceToken</code>.
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
     * Each character is converted into a <code>OtherToken</code>
     * and added to the internal list.
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
    public Tokens toTokens(long l) throws CatcodeException {

        Tokens tokens = new Tokens();
        StringBuffer s = new StringBuffer();
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
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param oNamespace the name space of the token
     *
     * @return the active token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitActive(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitActive(Object oValue, Object oChar,
            Object oNamespace) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
            if (value.length() != 1) {
                throw new CatcodeWrongLengthException(value);
            }
            uc = UnicodeChar.get(value.charAt(0));
        } else {
            throw new CatcodeVisitorException();
        }

        String namespace = (String) oNamespace;
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
     * Comments are ignored thus <code>null</code> is returned in any case.
     *
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @return <code>null</code>
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitComment(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitComment(Object oValue, Object oChar,
            Object ignore) {

        return null;
    }

    /**
     * There is only one CrToken.
     *
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @return the CR token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitCr(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitCr(Object oValue, Object oChar,
            Object ignore) {

        return CR_TOKEN;
    }

    /**
     * This visit method is invoked on an escape token.
     * In <logo>TeX</logo> this normally means a control sequence.
     *
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param oNamespace the name space of the token
     *
     * @return the token requested
     *
     * @throws CatcodeException in case of an error
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitEscape(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitEscape(Object oValue, Object oChar,
            Object oNamespace) throws CatcodeException {

        String value;
        if (oValue != null) {
            value = (String) oValue;
        } else if (oChar != null) {
            value = ((UnicodeChar) oChar).toString();
        } else {
            throw new CatcodeVisitorException();
        }

        String namespace = (String) oNamespace;
        Map<String, WeakReference<ControlSequenceToken>> map =
                csCache.get(namespace);

        if (map == null) {
            map = new HashMap<String, WeakReference<ControlSequenceToken>>();
            csCache.put(namespace, map);
        }

        WeakReference<ControlSequenceToken> wr = map.get(value);
        ControlSequenceToken token = null;

        if (wr != null) {
            token = wr.get();
        }
        if (token == null) {
            token =
                    new ControlSequenceToken((UnicodeChar) oChar, value,
                        namespace);
            map.put(value, new WeakReference<ControlSequenceToken>(token));
        }

        return token;
    }

    /**
     * Ignored characters are simply ignored;-)
     *
     * @param oValue the string value token or <code>null</code>
     * @param oChar the requested character code
     * @param ignore the third argument is ignored
     *
     * @return <code>null</code>
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitIgnore(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitIgnore(Object oValue, Object oChar,
            Object ignore) {

        return null;
    }

    /**
     * Invalid characters are ignored; even without any error message.
     *
     * @param oValue the string value token or <code>null</code>
     * @param oChar the requested character code
     * @param ignore the third argument is ignored
     *
     * @return <code>null</code>
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitInvalid(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitInvalid(Object oValue, Object oChar,
            Object ignore) {

        return null;
    }

    /**
     * A left brace token is expected to take a single character only.
     *
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitLeftBrace(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitLeftBrace(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitLetter(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitLetter(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitMacroParam(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitMacroParam(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitMathShift(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitMathShift(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitOther(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitOther(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitRightBrace(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitRightBrace(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
     * @param oValue the string value token or <code>null</code>
     * @param oChar the requested character code
     * @param ignore the third argument is ignored
     *
     * @return the space token.
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitSpace(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * @see "The TeXbook [Chapter 8, p. 47]"
     */
    public Object visitSpace(Object oValue, Object oChar,
            Object ignore) {

        return SPACE_TOKEN;
    }

    /**
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitSubMark(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitSubMark(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitSupMark(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitSupMark(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
     * @param oValue the string value of the token
     * @param oChar the character value of the token
     * @param ignore the name space of the token
     *
     * @see org.extex.scanner.type.CatcodeVisitor#visitTabMark(
     *      java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object visitTabMark(Object oValue, Object oChar,
            Object ignore) throws CatcodeException {

        UnicodeChar uc;
        if (oChar != null) {
            uc = (UnicodeChar) oChar;
        } else if (oValue != null) {
            String value = (String) oValue;
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
