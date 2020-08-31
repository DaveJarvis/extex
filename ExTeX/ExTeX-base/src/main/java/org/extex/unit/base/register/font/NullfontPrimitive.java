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

package org.extex.unit.base.register.font;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ComparableCode;
import org.extex.interpreter.type.font.FontConvertible;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.impl.NullFont;

/**
 * This class provides an implementation for the primitive
 * {@code \nullfont}.
 * 
 * <p>The Primitive {@code \nullfont}</p>
 * <p>
 * The primitive {@code \nullfont} provides access to an internal font
 * contain no characters at all. Thus any attempt to produce a typeset output
 * will not succeed. Nevertheless the font dimens can be read and written
 * freely.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;nullfont&rang;
 *      &rarr; {@code \nullfont}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \font123=\nullfont  </pre>
 * 
 * <p>
 * {@code \nullfont} produces the primitive name {@code \nullfont} when
 * applied to {@code \the} or {@code \showthe}.
 * </p>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NullfontPrimitive extends AbstractCode
        implements
            FontConvertible,
            ComparableCode {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code nullFont} contains the font encapsulated in this
     * primitive.
     */
    private final NullFont nullFont = new NullFont();

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public NullfontPrimitive(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.context.Context)
     */
    public boolean compare(Token token, Context context)
            throws HelpingException {

        return (token instanceof CodeToken)
                && context.getCode((CodeToken) token) == this;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Font convertFont(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return nullFont;
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        context.set(nullFont, prefix.clearGlobal());
    }

}
