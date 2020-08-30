/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.font;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This class provides an implementation for the primitive
 * {@code \fontdimen}.
 * 
 * <p>The Primitive {@code \fontdimen}</p>
 * <p>
 * The primitive {@code \fontdimen} can be used to set a font dimension value.
 * Each font has an arbitrary number of dimen values which are addressed by an
 * numerical index in TeX. In εχTeX this has been extended to arbitrary strings.
 * </p>
 * <p>
 * The primitive expands to the value of the font dimension in a right hand
 * context.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;fontdimen&rang;
 *       &rarr; {@code \fontdimen}
 *          &lang;8-bit&nbsp;number&rang; &lang;font&rang; &lang;equals&rang; &lang;dimen&rang;
 * </pre>
 * 
 * TODO gene: document Extension
 * 
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    \fontdimen13\ff=5pt  </pre>
 * 
 * <pre class="TeXSample">
 *    \the\fontdimen13\ff  </pre>
 * 
 * <pre class="TeXSample">
 *    \the\fontdimen{em}\ff=8pt  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Fontdimen extends AbstractAssignment
        implements
            ExpandableCode,
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
    public Fontdimen(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.skipSpace();
        Font font = source.getFont(context, getToken());
        source.getOptionalEquals(context);
        Dimen size = source.parseDimen(context, source, typesetter);
        font.setFontDimen(key, size);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.push(the(context, source, typesetter));
    }

    /**
     * Get the key for the font dimen. According to TeX the key is an arbitrary number. In
     * εχTeX this has been extended to take an expandable sequence
     * of tokens enclosed in braces. The left brace acts as indicator that this
     * extension is used.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the key
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t = source.getNonSpace(context);
        if (t == null) {
            throw new EofException(toText(context));
        } else if (t.isa(Catcode.LEFTBRACE)) {
            source.push(t);
            String key = source.scanTokensAsString(context, getToken());
            if (key == null) {
                throw new EofException(toText());
            }
            return key;
        }
        source.push(t);
        return Long.toString(source.parseInteger(context, source, typesetter));
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        String key = getKey(context, source, typesetter);
        source.skipSpace();
        Font font = source.getFont(context, getToken());
        FixedDimen size = font.getFontDimen(key);
        if (null == size) {
            size = Dimen.ZERO_PT;
        }
        try {
            return context.getTokenFactory().toTokens(size.toString());
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

}
