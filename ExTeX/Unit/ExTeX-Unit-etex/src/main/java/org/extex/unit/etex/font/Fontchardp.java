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

package org.extex.unit.etex.font;

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.glue.FixedGlue;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This class provides an implementation for the primitive
 * {@code \fontchardp}.
 * 
 * <p>The Primitive {@code \fontchardp}</p>
 * <p>
 * The primitive {@code \fontchardp} is a read-only dimen register which
 * corresponds to the depth of a character in a given font. If the character is
 * not defined in the font then 0pt is returned.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;fontchardp&rang;
 *      &rarr; {@code \fontchardp} {@linkplain
 *          org.extex.interpreter.TokenSource#getFont(Context,CodeToken)
 *          &lang;font&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#scanCharacterCode(Context,Typesetter,CodeToken)
 *          &lang;character code&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \dimen0 = \fontchardp\tenrm `a  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Fontchardp extends AbstractCode
        implements
            ExpandableCode,
            CountConvertible,
            DimenConvertible,
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
    public Fontchardp(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return convertDimen(context, source, typesetter);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return get(context, source, typesetter).getValue();
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
     * Get the dimen value of the depth. If the character is not defined in the
     * font then ZERO_PT is returned.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the dimen value of the depth
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private FixedDimen get(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        try {
            Font fnt = source.getFont(context, getToken());
            UnicodeChar uc =
                    source.scanCharacterCode(context, typesetter, null);
            FixedGlue d = fnt.getDepth(uc);
            return (d != null ? d.getLength() : Dimen.ZERO_PT);

        } catch (EofException e) {
            throw new EofException(toText(context));
        }
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        FixedDimen depth = get(context, source, typesetter);
        try {
            return context.getTokenFactory().toTokens(depth.toString());
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

}
