/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.skip;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.DimenConvertible;
import org.extex.interpreter.type.glue.Glue;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\gluestretch</code>.
 *
 * <doc name="gluestretch">
 * <h3>The Primitive <tt>\gluestretch</tt></h3>
 * <p>
 *  The primitive <tt>\gluestretch</tt> translates a stretch part of a glue
 *  value into a length. The stretch order is stripped and just the size is
 *  preserved. The unit is changed to pt. For instance, if the value considered
 *  is 8pt plus 1.23 fil then <tt>\gluestretch</tt> returns 1.23 pt.
 * </p>
 * <p>
 *  The primitive <tt>\gluestretch</tt> can be used wherever a length is
 *  expected. The primitive is also applicable to <tt>\the</tt>.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;gluestretch&rang;
 *      &rarr; <tt>\gluestretch</tt> {@linkplain
 *        org.extex.interpreter.type.glue.Glue#parse(TokenSource,Context,Typesetter)
 *        &lang;glue&rang;} </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \gluestretch\skip1  </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Gluestretch extends AbstractCode
        implements
            CountConvertible,
            DimenConvertible,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Gluestretch(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertCount(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Glue glue = Glue.parse(source, context, typesetter);
        return glue.getShrink().getValue();
    }

    /**
     * @see org.extex.interpreter.type.dimen.DimenConvertible#convertDimen(
     *     org.extex.interpreter.context.Context,
     *     org.extex.interpreter.TokenSource,
     *     org.extex.typesetter.Typesetter)
     */
    public long convertDimen(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Glue glue = Glue.parse(source, context, typesetter);
        return glue.getStretch().getValue();
    }

    /**
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return new Tokens(context, (new Dimen(convertDimen(context, source,
                typesetter))).toString());
    }

}
