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

package org.extex.unit.tex.string;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.InvalidCharacterException;
import org.extex.interpreter.exception.helping.InvalidCodeException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.interpreter.type.dimen.DimenConvertible;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive <code>\lccode</code>.
 *
 * <doc name="lccode">
 * <h3>The Primitive <tt>\lccode</tt></h3>
 * <p>
 *  The primitive <tt>\lccode</tt> provides access to the mapping from
 *  characters to their lower case counterpart. This information is used by the
 *  primitive <tt>\lowercase</tt>
 * </p>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;lccode&rang;
 *        &rarr; <tt>\lccode</tt> {@linkplain
 *          org.extex.interpreter.TokenSource#scanNumber(Context)
 *          &lang;8-bit&nbsp;number&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *          &lang;equals&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#scanNumber(Context)
 *          &lang;8-bit&nbsp;number&rang;} </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \lccode 65=65  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Lccode extends AbstractAssignment
        implements
            ExpandableCode,
            Theable,
            CountConvertible,
            DimenConvertible {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Lccode(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        UnicodeChar ucCode = source.scanCharacterCode(context, typesetter,
                getName());
        source.getOptionalEquals(context);
        try {
            UnicodeChar lcCode = source.scanCharacterCode(context, typesetter,
                    getName());
            context.setLccode(ucCode, lcCode, prefix.clearGlobal());
        } catch (InvalidCharacterException e) {
            throw new InvalidCodeException(e.getChar(), Integer
                    .toString(UnicodeChar.MAX_VALUE));
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
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return new Tokens(context, convertCount(context, source, typesetter));
    }

    /**
     * @see org.extex.interpreter.type.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public long convertCount(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        UnicodeChar ucCode = source.scanCharacterCode(context, typesetter,
                getName());
        return context.getLccode(ucCode).getCodePoint();
    }

    /**
     * @see org.extex.interpreter.type.dimen.DimenConvertible#convertDimen(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public long convertDimen(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return convertCount(context, source, typesetter);
    }

}
