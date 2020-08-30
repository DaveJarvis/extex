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

package org.extex.unit.namespace;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \export}.
 * 
 * <p>The Primitive {@code \export}</p>
 * <p>
 * The primitive {@code \export} takes a list of tokens and saves them away for
 * an associated {@code \import}. The tokens in the list are either control
 * sequence tokens or active characters. All other tokens are ignored.
 * </p>
 * <p>
 * The expansion text is empty. The primitive is an assignment. Thus
 * {@code \afterassignment} interacts with the primitive in the expected way.
 * </p>
 * <p>
 * The definitions are usually performed local to the current group. If the
 * prefix {@code \global} is given or the count register {@code \globaldefs}
 * has a positive value then the definition is made globally. Usually you want
 * to define the export as global. This is the case if the {@code \export}
 * primitive is invoked at group level 0. Interesting special effects can be
 * achieved when using the export statement in groups and together with a local
 * scope definition.
 * </p>
 * <p>
 * This primitive is one building block for the use of name spaces in
 * εχTeX. The central primitive for this purpose is
 * {@code \namespace}.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;export&rang;
 *      &rarr; &lang;prefix&rang; {@code \export} {@linkplain
 *      org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *      &lang;replacement text&rang;}
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *       |  {@code \global}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \export{\a\b}  </pre>
 * 
 *
 * 
 * @see org.extex.unit.namespace.Namespace
 * @see org.extex.unit.namespace.Import
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Export extends AbstractAssignment {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Export(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Tokens export;
        try {
            export = source.getTokens(context, source, typesetter);
        } catch (EofException e) {
            throw new EofInToksException(toText());
        }
        context.setToks(context.getNamespace() + "\bexport", export, true);
    }

}
