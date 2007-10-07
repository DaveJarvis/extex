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

package org.extex.unit.tex.string;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\lowercase</code>.
 * 
 * <doc name="lowercase">
 * <h3>The Primitive <tt>\lowercase</tt></h3>
 * <p>
 * The primitive <tt>\lowercase</tt> takes as argument a token list in braces
 * and translates all letters to their lowercase form. The translation table is
 * stored in <tt>\lccode</tt>. Thus the translation can be modified freely.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;lowercase&rang;
 *        &rarr; <tt>\lowercase</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *        &lang;tokens&rang;}  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \lowercase {ABC}  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Lowercase extends AbstractCode implements ExpandableCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Lowercase(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        expand(prefix, context, source, typesetter);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Tokens toks;
        try {
            toks = source.getTokens(context, source, typesetter);
        } catch (EofException e) {
            throw new EofInToksException(toText(context));
        }

        String namespace = context.getNamespace();
        Token[] result = new Token[toks.length()];
        TokenFactory factory = context.getTokenFactory();
        Token t;

        try {
            for (int i = 0; i < toks.length(); i++) {
                t = toks.get(i);
                if (t instanceof LetterToken || t instanceof OtherToken) {
                    UnicodeChar uc = context.getLccode(t.getChar());
                    if (uc != null && //
                            uc.getCodePoint() != 0 && //
                            !uc.equals(t.getChar())) {
                        t = factory.createToken(t.getCatcode(), uc, namespace);
                    }
                }
                result[i] = t;
            }
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }

        source.push(result);
    }

}
