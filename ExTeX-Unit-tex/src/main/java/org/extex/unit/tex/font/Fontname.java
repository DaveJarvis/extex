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

import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This class provides an implementation for the primitive
 * <code>\fontname</code>.
 * 
 * <doc name="fontname">
 * <h3>The Primitive <tt>\fontname</tt></h3>
 * <p>
 * The primitive <tt>\fontname</tt> can be used to retrieve the name of a
 * font. It takes a font specification as argument. It expands to the name of
 * the font. If this font is not loaded at its design size then the actual size
 * is appended after the tokens <tt> at </tt>. All tokens produced this way are
 * <i>other</i> tokens except of the spaces. This means that even the letters
 * are of category <i>other</i>.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;fontname&rang;
 *       &rarr; <tt>\fontname</tt> {@linkplain
 *          org.extex.interpreter.TokenSource#getFont(Context,String)
 *          &lang;font&rang;}  </pre>
 * 
 * <h4>Example</h4>
 * 
 * <pre class="TeXSample">
 *  \font\myFont=cmr12
 *  \fontname\myfont
 *  &rArr; cmr12
 * </pre>
 * 
 * <pre class="TeXSample">
 * \font\myFont=cmr12 at 24pt
 * \fontname\myfont
 * &rArr; cmr12 at 24pt
 * </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4431 $
 */
public class Fontname extends AbstractCode implements ExpandableCode {

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
    public Fontname(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.skipSpace();
        Font font;
        Tokens fontname;
        TokenFactory tokenFactory = context.getTokenFactory();
        try {
            font = source.getFont(context, getName());
            fontname = tokenFactory.toTokens(font.getFontName());
        } catch (EofException e) {
            throw new EofException(printableControlSequence(context));
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
        FixedDimen size = font.getActualSize();
        if (font.getDesignSize().ne(size)) {
            try {
                fontname.add(tokenFactory.toTokens(" at "));
                fontname.add(tokenFactory.toTokens(size.toString()));
            } catch (CatcodeException e) {
                throw new NoHelpException(e);
            }
        }
        source.push(fontname);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        execute(prefix, context, source, typesetter);
    }

}
