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
import org.extex.core.UnicodeCharList;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingLeftBraceException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.register.CharCode;

/**
 * This class provides an implementation for the primitive
 * <code>\hyphenation</code>.
 * 
 * <doc name="hyphenation">
 * <h3>The Primitive <tt>\hyphenation</tt></h3>
 * <p>
 * The primitive <tt>\hyphenation</tt> can be used to add hyphenation
 * exceptions to the current language. The argument is a list of whitespace
 * separated words enclosed in braces. The hyphenation points are indicated by
 * including a hyphen character (-) at the appropriate places.
 * </p>
 * <p>
 * When paragraph breaking needs to insert additional break points these
 * hyphenation points are translated into discretionaries. The exceptions
 * specified with the primitive <tt>\hyphenation</tt> have precedence before
 * the hyphenation points found with the help of hyphenation patterns.
 * </p>
 * <p>
 * One example which make use of this precedence is the hyphenation exception
 * without any hyphen characters. This can be used to suppress any hyphenation
 * in a single word.
 * </p>
 * 
 * <h4>Syntax</h4>
 * 
 * <pre class="syntax">
 *    &lang;hyphenation&rang;
 *     &rarr; <tt>\hyphenation</tt> {...} </pre>
 * 
 * <h4>Example:</h4>
 * 
 * <pre class="TeXSample">
 *   \hyphenation{as-so-ciate as-so-ciates}  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4770 $
 */
public class HyphenationPrimitive extends AbstractHyphenationCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 20060228L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public HyphenationPrimitive(String name) {

        super(name);
    }

    /**
     * Collect all characters that make up a word.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param token the first token already read
     * 
     * @return the first character not included into the word
     * 
     * @throws HelpingException in case of an error
     * @throws CatcodeException in case of an exception in token creation
     */
    protected UnicodeCharList collectWord(Context context, TokenSource source,
            Token token) throws HelpingException, CatcodeException {

        UnicodeCharList word = new UnicodeCharList();
        UnicodeChar uc, lc;

        for (Token t = token; t != null; t = source.getToken(context)) {

            if (t instanceof LetterToken || t instanceof OtherToken) {
                uc = t.getChar();
            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                uc = ((CharCode) code).getCharacter();
            } else {
                source.push(t);
                return word;
            }

            if (t.equals(Catcode.OTHER, '-')) {
                word.add(UnicodeChar.SHY);
            } else {
                uc = t.getChar();
                lc = context.getLccode(uc);
                word.add(lc == null ? uc : lc);
            }
        }

        return word;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Language table = getHyphenationTable(context);
        Token t = source.getNonSpace(context);
        if (!(t instanceof LeftBraceToken)) {
            throw new MissingLeftBraceException(
                printableControlSequence(context));
        }

        try {
            UnicodeCharList word;

            for (t = source.getNonSpace(context); //
            !(t instanceof RightBraceToken); //
            t = source.getNonSpace(context)) {

                if (!isWordConstituent(t, context)) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.ImproperHyphen", printableControlSequence(context));
                }
                word = collectWord(context, source, t);
                table.addHyphenation(word, (TypesetterOptions) context);
            }
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        } catch (HyphenationException e) {
            throw new NoHelpException(e);
        }

    }

    /**
     * This method checks that the given token is a word constituent. This means
     * that the token is either
     * <ul>
     * <li>a letter token, or</li>
     * <li>a other token, or</li>
     * <li>a code token defined with <tt>\chardef</tt>.</li>
     * </ul>
     * 
     * @param t the token to analyze
     * @param context the interpreter context
     * 
     * @return <code>true</code> iff the token is
     * 
     * @throws HelpingException in case of an error
     */
    protected boolean isWordConstituent(Token t, Context context)
            throws HelpingException {

        if (t == null) {
            return false;
        } else if (t instanceof LetterToken || t instanceof OtherToken) {
            return true;
        } else if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            return (code instanceof CharCode);
        }

        return false;
    }

}
