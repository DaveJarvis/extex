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

package org.extex.unit.tex.typesetter.mark;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.MarkNode;

/**
 * This class provides an implementation for the primitive {@code \marks}.
 * 
 * <p>The Primitive {@code \marks}</p>
 * <p>
 * The primitive {@code \marks} places a mark in the current list. The marks on
 * the last page can be retrieved when the page is mounted in the output
 * routine. The text stored within the mark is a list of tokens.
 * </p>
 * <p>
 * The marks can be classified with an identifier. In ε-TeX the identifier is
 * a number. In εχTeX in addition arbitrary token lists can be used as
 * identifier.
 * </p>
 * <p>
 * The {@code \marks0} is equivalent to {@code \mark}.
 * </p>
 * 
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;marks&rang;
 *      &rarr; {@code \marks} {@linkplain
 *        org.extex.unit.tex.typesetter.mark.AbstractMarksCode#getKey(Context,TokenSource,Typesetter)
 *        &lang;mark name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *        &lang;tokens&rang;}  </pre>
 * 
 * <p>Examples</p>
 * 
 * <pre class="TeXSample">
 *    \marks123{abc}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Marks extends AbstractMarkCode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Marks(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String index = getKey(context, source, typesetter);
        Tokens toks =
                source.scanUnprotectedTokens(context, false, false, getToken());
        typesetter.add(new MarkNode(toks, index));
    }

}
