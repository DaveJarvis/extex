/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.language.Language;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This is the abstract base class for all hyphenation related primitives. It
 * provides common methods.
 * 
 * <h2>Determining the Current Language</h2>
 * 
 * <p>
 * In TeX the language is determined by the count register named
 * {@code language}. This has the disadvantage that the language is named
 * anonymously by an integer.
 * </p>
 * <p>
 * This base class implements an extension to this scheme. First the toks
 * register {@code lang} is sought. If this register is defined and not empty
 * then the contents is used as name of the current language. Otherwise the
 * count register {@code language} is used for this purpose.
 * </p>
 * 
 * 
 *  <p>The Tokens Register {@code \lang}</p>
 * <p>
 * The tokens register {@code \lang} is the primary source of information to
 * determine the current language. If this register is not defined or has the
 * empty value then the count register {@code \language} is used instead.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;lang&rang;
 *      &rarr; {@code \lang} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,org.extex.interpreter.TokenSource,org.extex.typesetter.Typesetter)
 *        &lang;tokens&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \lang={de}  </pre>
 * 
 *
 * 
 * <p>The Count Register {@code \language}</p>
 * <p>
 * The count register {@code \language} is the secondary source of information
 * to determine the current language. If this tokens register {@code \lang} is
 * not defined or has the empty value then this is used instead.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;language&rang;
 *      &rarr; {@code \language} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,org.extex.interpreter.TokenSource,org.extex.typesetter.Typesetter)
 *        &lang;number&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \language=1  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public abstract class AbstractHyphenationCode extends AbstractCode {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    static final long serialVersionUID = 2007L;

    /**
     * The field {@code LANGUAGE_COUNT} contains the name of the count register
     * to determine the language.
     */
    private static final String LANGUAGE_COUNT = "language";

    /**
     * The field {@code LANGUAGE_TOKS} contains the name of the tokens register
     * to determine the language.
     */
    private static final String LANGUAGE_TOKS = "lang";

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public AbstractHyphenationCode(CodeToken token) {

        super(token);
    }

    /**
     * Getter for the current hyphenation table.
     * 
     * @param context the interpreter context
     * 
     * @return the current hyphenation table
     * 
     * @throws HelpingException in case of an error
     */
    protected Language getHyphenationTable(Context context)
            throws HelpingException {

        Tokens lang = context.getToksOrNull(LANGUAGE_TOKS);
        String name = (lang != null 
                ? lang.toText() 
                : Long.toString(context.getCount(LANGUAGE_COUNT).getValue()));

        return context.getLanguage(name);
    }

}
