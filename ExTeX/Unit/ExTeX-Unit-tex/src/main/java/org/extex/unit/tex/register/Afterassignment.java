/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.unit.tex.register;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * {@code \afterassignment}.
 * 
 * <p>The Primitive {@code \afterassignment}</p>
 * <p>
 * The primitive {@code \afterassignment} registers the token to be inserted
 * after the next assignment. Note that there is at most one token to be
 * inserted after the next assignment. Thus the primitive may overwrite any
 * previously registered token.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;afterassignment&rang;
 *      &rarr; {@code \afterassignment} {@linkplain
 *         org.extex.interpreter.TokenSource#getToken(Context)
 *         &lang;token&rang;} </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \afterassignment\abc  </pre>
 *  <pre class="TeXSample">
 *    \afterassignment X  </pre>
 *  <pre class="TeXSample">
 *    \afterassignment ~  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Afterassignment extends AbstractCode {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Afterassignment(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        Token t = source.getToken(context);
        if (t == null) {
            throw new EofException(toText(context));
        }
        context.setAfterassignment(t);
    }

}
