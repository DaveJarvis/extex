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

package org.extex.unit.etex.conditional;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.conditional.AbstractIf;

/**
 * This class provides an implementation for the primitive {@code \if}.
 * 
 * <p>The Primitive {@code &#x005c;unless}</p>
 * 
 * <p>
 * <strong>Copied from the ε-TeX reference</strong>:
 * </p>
 * <p>
 * <i> similar in effect to {@code &#x005c;unless} {@code \ifx}
 * {@code &#x005c;undefined}, but does not require {@code &#x005c;undefined}
 * to actually be undefined, since no explicit comparison is made with any
 * particular control sequence. </i>
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;ifdefined&rang;
 *      &rarr; {@code \ifdefined} &lang;true text&rang; {@code \else}&lang;false text&rang;{@code \fi}
 *       |  {@code \ifdefined} &lang;true text&rang; {@code \else} &lang;false text&rang; {@code \fi}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *   &#x005c;ifdefined\TESTNAME\else not\fi defined  </pre>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
*/
public class Ifdefined extends AbstractIf {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Ifdefined(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        Token token = source.getToken(context);

        return (token instanceof CodeToken && context
            .getCode((CodeToken) token) != null);
    }

}
