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
 * <code>\nullfont</code>.
 * 
 * <doc name="nullfont">
 * <h3>The Primitive <tt>\nullfont</tt></h3>
 * <p>
 * The primitive <tt>\nullfont</tt> provides access to an internal font
 * contain no characters at all. Thus any attempt to produce a typeset output
 * will not succeed. Nevertheless the font dimens can be read and written
 * freely.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;nullfont&rang;
 *      &rarr; <tt>\nullfont</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \font123=\nullfont  </pre>
 * 
 * <h4>The</h4>
 * <p>
 * <tt>\nullfont</tt> produces the primitive name <tt>\nullfont</tt> when
 * applied to <tt>\the</tt> or <tt>\showthe</tt>.
 * </p>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class NullfontPrimitive extends AbstractCode
        implements
            FontConvertible,
            ComparableCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>nullFont</tt> contains the font encapsulated in this
     * primitive.
     */
    private NullFont nullFont = new NullFont();

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public NullfontPrimitive(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ComparableCode#compare(
     *      org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.context.Context)
     */
    public boolean compare(Token token, Context context)
            throws HelpingException {

        return (token instanceof CodeToken)
                && context.getCode((CodeToken) token) == this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.font.FontConvertible#convertFont(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Font convertFont(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return nullFont;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        context.set(nullFont, prefix.clearGlobal());
    }

}