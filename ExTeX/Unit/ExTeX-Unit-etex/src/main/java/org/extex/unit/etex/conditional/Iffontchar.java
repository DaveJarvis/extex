/*
 * Copyright (C) 2005-2008 The ExTeX Group and individual authors listed below
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

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;
import org.extex.unit.base.conditional.AbstractIf;

/**
 * This class provides an implementation for the primitive
 * {@code \iffontchar}.
 * 
 * <p>The Primitive {@code \iffontchar}</p>
 * <p>
 * The primitive {@code \iffontchar} can be used to check whether a certain
 * glyph exists in a font. For this purpose it takes a font and the code of a
 * character and performs the test. If the character exists the then branch is
 * expanded otherwise the else branch.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;iffontchar&rang;
 *      &rarr; {@code \iffontchar} {@link TokenSource#getFont(Context,CodeToken) &lang;font&rang;} {@link
 *      TokenSource#scanCharacterCode(Context,Typesetter,CodeToken) &lang;code&rang; &lang;true text&rang;} {@code \fi}
 *      | {@code \iffontchar} {@link TokenSource#getFont(Context,CodeToken) &lang;font&rang;} {@link
 *      TokenSource#scanCharacterCode(Context,Typesetter,CodeToken) &lang;code&rang; &lang;true text&rang;} &lang;true text&rang; {@code \else} &lang;false text&rang; {@code \fi} </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \iffontchar abc \fi  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Iffontchar extends AbstractIf {

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
    public Iffontchar(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Font font = source.getFont(context, getToken());
        UnicodeChar uc =
                source.scanCharacterCode(context, typesetter, getToken());
        return font.hasGlyph(uc);
    }

}
