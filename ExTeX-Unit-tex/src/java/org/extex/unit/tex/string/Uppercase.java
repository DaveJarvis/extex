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

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.EofInToksException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>&#x5c;uppercase</code>.
 *
 * <doc name="uppercase">
 * <h3>The Primitive <tt>&#x5c;uppercase</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;uppercase&rang;
 *        &rarr; <tt>&#x5c;uppercase</tt> &lang;...&rang; </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    &#x5c;uppercase ...  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Uppercase extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Uppercase(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        expand(prefix, context, source, typesetter);
    }

    /**
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        Tokens toks;
        try {
            toks = source.getTokens(context, source, typesetter);
        } catch (EofException e) {
            throw new EofInToksException(printableControlSequence(context));
        }

        String namespace = context.getNamespace();
        Token[] result = new Token[toks.length()];
        TokenFactory factory = context.getTokenFactory();
        Token t;

        for (int i = 0; i < toks.length(); i++) {
            t = toks.get(i);
            if (t instanceof LetterToken || t instanceof OtherToken) {
                UnicodeChar uc = context.getUccode(t.getChar());
                if (uc != null && //
                    uc.getCodePoint() != 0 && //
                    !uc.equals(t.getChar())) {
                    try {
                        t = factory.createToken(t.getCatcode(), uc, namespace);
                    } catch (CatcodeException e) {
                        throw new InterpreterException(e);
                    }
                }
            }
            result[i] = t;
        }

        source.push(result);
    }

}
