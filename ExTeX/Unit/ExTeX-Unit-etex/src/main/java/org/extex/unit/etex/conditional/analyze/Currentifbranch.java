/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.conditional.analyze;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \currentifbranch}.
 * 
 * <p>The Primitive {@code \currentifbranch}</p>
 * <p>
 * The primitive {@code \currentifbranch} is an integer quantity which provides
 * the information in which branch of the enclosing conditional. The value is
 * determined by the following rules:
 * </p>
 * <ul>
 * <li>If the then branch of the enclosing is active then the value is
 * {@code 1}.</li>
 * <li>If the else branch of the enclosing is active then the value is
 * {@code -1}.</li>
 * <li>If the enclosing conditional is {@code \ifcase} then the value is the
 * number selecting the current case for normal cases and {@code -1} for the
 * else case.</li>
 * <li>If there is no enclosing conditional then the value is {@code 0}.</li>
 * </ul>
 * <p>
 * The primitive {@code \currentifbranch} is a read-only quantity. an attempt
 * to use this primitive in a horizontal or vertical mode results in an error.
 * </p>
 * 
 * <p>Syntax</p>

 * <p>
 * The formal description of this primitive is the following:
 * </p>
 * 
 * <pre class="syntax">
 *    &lang;currentifbranch&rang;
 *     &rarr; {@code \currentifbranch} </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \count0=\currentifbranch  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Currentifbranch extends AbstractCode
        implements
            CountConvertible,
            Theable {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Currentifbranch(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Conditional conditional = context.getConditional();
        return (conditional == null ? 0 : conditional.getBranch());
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        return context.getTokenFactory().toTokens(
            convertCount(context, source, typesetter));
    }

}
