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
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This class provides an implementation for the primitive
 * <code>\fontdimen</code>.
 * 
 * <doc name="fontdimen">
 * <h3>The Primitive <tt>\fontdimen</tt></h3>
 * <p>
 * The primitive <tt>\fontdimen</tt> can be used to set a font dimension
 * value. Each font has an arbitrary number of dimen values which are addressed
 * by an numerical index in <logo>TeX</logo>. In <logo>ExTeX</logo> this has
 * been extended to arbitrary strings.
 * </p>
 * <p>
 * The primitive expands to the value of the font dimension in a right hand
 * context.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;fontdimen&rang;
 *       &rarr; <tt>\fontdimen</tt> {@linkplain
 *          org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
 *          &lang;8-bit&nbsp;number&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#getFont(Context,String)
 *          &lang;font&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *          &lang;equals&rang;} {@linkplain
 *          org.extex.core.dimen#Dimen(Context,TokenSource)
 *          &lang;dimen&rang;}   </pre>
 * 
 * TODO gene: document Extension
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \fontdimen13\ff=5pt  </pre>
 *  <pre class="TeXSample">
 *    \the\fontdimen13\ff  </pre>
 *  <pre class="TeXSample">
 *    \the\fontdimen{em}\ff=8pt  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4431 $
 */
public class Fontdimen extends AbstractAssignment
        implements
            ExpandableCode,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Fontdimen(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.skipSpace();
        Font font = source.getFont(context, getName());
        source.getOptionalEquals(context);
        Dimen size = source.parseDimen(context, source, typesetter);
        font.setFontDimen(key, size);
    }

    /**
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.push(the(context, source, typesetter));
    }

    /**
     * Get the key for the font dimen. According to <logo>TeX</logo> the key is
     * an arbitrary number. In <logo>ExTeX</logo> this has been extended to
     * take an expandable sequence of tokens enclosed in braces. The left brace
     * acts as indicator that this extension is used.
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
            throw new EofException(printableControlSequence(context));
        } else if (t.isa(Catcode.LEFTBRACE)) {
            source.push(t);
            String key =
                    source.scanTokensAsString(context,
                        printableControlSequence(context));
            if (key == null) {
                throw new EofException(printableControlSequence(context));
            }
            return key;
        }
        source.push(t);
        return Long.toString(source.parseInteger(context, source, typesetter));
    }

    /**
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        String key = getKey(context, source, typesetter);
        source.skipSpace();
        Font font = source.getFont(context, getName());
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
