/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

import org.extex.base.parser.CountConvertible;
import org.extex.base.parser.DimenConvertible;
import org.extex.base.parser.GlueParser;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.Glue;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\glueshrinkorder</code>.
 * 
 * <doc name="glueshrinkorder">
 * <h3>The Primitive <tt>\glueshrinkorder</tt></h3>
 * <p>
 * The primitive <tt>\glueshrinkorder</tt> determines the order of the glue
 * shrink component of the following glue specification. A fixed, non-shrinkable
 * glue returns the value 0. Glue with the order fil gives 1, fill gives 2, and
 * filll gives 3.
 * </p>
 * <p>
 * Note that the glue specification of 1&nbsp;fi returns also 1. This is due to
 * the compatibility with <logo>eTeX</logo> which does not have this unit. This
 * unit has been introduced by <logo>Omega</logo>.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;glueshrinkorder&rang;
 *      &rarr; <tt>\glueshrinkorder</tt> {@linkplain
 *        org.extex.base.parser.GlueParser#parse(TokenSource,Context,Typesetter)
 *        &lang;glue&rang;} </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *   \glueshrinkorder\skip1  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Glueshrinkorder extends AbstractCode
        implements
            CountConvertible,
            DimenConvertible,
            Theable {

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
    public Glueshrinkorder(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.parser.CountConvertible#convertCount(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Glue glue = GlueParser.parse(source, context, typesetter);
        int order = glue.getShrink().getOrder();
        return (order < 2 ? order : order - 1);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.parser.DimenConvertible#convertDimen(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Glue glue = GlueParser.parse(source, context, typesetter);
        int order = glue.getShrink().getOrder();
        return (order < 2 ? order : order - 1);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException, TypesetterException {

        return context.getTokenFactory().toTokens(//
            convertCount(context, source, typesetter));
    }

}
