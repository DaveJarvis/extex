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

package org.extex.unit.etex.scan;

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
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \detokenize}.
 *
 * <p>The Primitive {@code \detokenize}</p>
 * <p>
 *  The primitive {@code \detokenize} ...
 * </p>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>

 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;detokenize&rang;
 *      &rarr; {@code \detokenize} </pre>
 *
 * <p>Examples</p>

 *  <pre class="TeXSample">
 *    \detokenize...  </pre>
 *
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Detokenize extends AbstractCode implements ExpandableCode {

    /**
     * The field {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     *
     * @param token the initial token for the primitive
     */
    public Detokenize(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        Tokens tokens;
        try {
            tokens = source.getTokens(context, source, typesetter);
            source.push(context.getTokenFactory().toTokens(tokens.toText()));
        } catch (EofException e) {
            throw new EofInToksException(toText(context));
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        Tokens tokens;
        try {
            tokens = source.getTokens(context, source, typesetter);
        } catch (EofException e) {
            throw new EofInToksException(toText(context));
        }
        try {
            source.push(context.getTokenFactory().toTokens(tokens.toText()));
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

}
