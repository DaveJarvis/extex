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

import org.extex.core.count.CountConvertible;
import org.extex.core.dimen.DimenConvertible;
import org.extex.core.glue.Glue;
import org.extex.core.glue.GlueParser;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\gluestretchorder</code>.
 *
 * <doc name="gluestretchorder">
 * <h3>The Primitive <tt>\gluestretchorder</tt></h3>
 * <p>
 *  The primitive <tt>\gluestretchorder</tt> determines the order of the glue
 *  stretch component of the following glue specification.
 *  A fixed, non-stretchable glue returns the value 0.
 *  Glue with the order fil gives 1, fill gives 2, and filll gives 3.
 * </p>
 * <p>
 *  Note that the glue specification of 1&nbsp;fi returns also 1. This is due to
 *  the compatibility with <logo>eTeX</logo> which does not have this unit. This
 *  unit has been introduced by <logo>Omega</logo>.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;gluestretchorder&rang;
 *      &rarr; <tt>\gluestretchorder</tt> {@linkplain
 *        org.extex.core.glue.Glue#parse(TokenSource,Context,Typesetter)
 *        &lang;glue&rang;} </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \gluestretchorder\skip1  </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Gluestretchorder extends AbstractCode
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
    public Gluestretchorder(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertCount(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Glue glue = GlueParser.parse(source, context, typesetter);
        int order = glue.getStretch().getOrder();
        return (order < 2 ? order : order - 1);
    }

    /**
     * @see org.extex.core.dimen.DimenConvertible#convertDimen(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertDimen(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Glue glue = GlueParser.parse(source, context, typesetter);
        int order = glue.getStretch().getOrder();
        return (order < 2 ? order : order - 1);
    }

    /**
     * This method is the getter for the description of the primitive.
     *
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     *
     * @return the description of the primitive as list of Tokens
     *
     * @throws InterpreterException in case of an error
     * @throws CatcodeException in case of an error in token creation
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter)
            throws InterpreterException,
                CatcodeException {

        return context.getTokenFactory().toTokens( //
            convertCount(context, source, typesetter));
    }

}
