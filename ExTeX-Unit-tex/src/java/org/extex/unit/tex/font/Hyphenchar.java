/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;
import org.extex.util.exception.GeneralException;

/**
 * This class provides an implementation for the primitive
 * <code>\hyphenchar</code>.
 * 
 * <doc name="hyphenchar">
 * <h3>The Primitive <tt>\hyphenchar</tt></h3>
 * <p>
 * The primitive <tt>\hyphenchar</tt> can be used to set the hyphenation
 * character for a given font. The undefined character &ndash; represented by a
 * negative value &ndash; indicates that no hyphenation should be applied.
 * Otherwise the given character will be used when hyphenating words.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *     &amp;langhyphenchar&amp;rang
 *       &amp;rarr
 * <tt>
 * \hyphenchar
 * </tt>
 *  &amp;langfont&amp;rang {@linkplain
 *         org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *         &amp;langequals&amp;rang} {@linkplain
 *         org.extex.interpreter.TokenSource#scanNumber(Context)
 *         &amp;lang8-bit number&amp;rang}
 * </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *     \hyphenchar\font=132
 * </pre>
 * 
 * <h4>Incompatibility</h4>
 * <p>
 * The TeXbook gives no indication ow the primitive should react for negative
 * values &ndash; except -1. The implementation of <logo>TeX</logo> allows to
 * store and retrieve arbitrary negative values. This behavior of <logo>TeX</logo>
 * is not preserved in <logo>ExTeX</logo>.
 * </p>
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Hyphenchar extends AbstractAssignment implements CountConvertible,
        ExpandableCode, Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Hyphenchar(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        try {
            Font font = source.getFont(context, getName());
            source.getOptionalEquals(context);
            long c = Count.scanInteger(context, source, typesetter);
            font.setHyphenChar(UnicodeChar.get((int) c));
        } catch (EofException e) {
            throw new EofException(printableControlSequence(context));
        }
    }

    /**
     * @see org.extex.interpreter.type.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertCount(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        try {
            Font font = source.getFont(context, getName());
            UnicodeChar uc = font.getHyphenChar();
            if (uc == null) {
                return -1;
            } else {
                return uc.getCodePoint();
            }
        } catch (EofException e) {
            throw new EofException(printableControlSequence(context));
        }
    }

    /**
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        source.push(the(context, source, typesetter));
    }

    /**
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Font font = source.getFont(context, getName());
        UnicodeChar uc = font.getHyphenChar();
        try {
            if (uc == null) {
                return new Tokens(context, "-1");
            } else {
                return new Tokens(context, //
                        Integer.toString(uc.getCodePoint()));
            }
        } catch (EofException e) {
            throw new EofException(printableControlSequence(context));
        } catch (GeneralException e) {
            throw new InterpreterException(e);
        }
    }

}
