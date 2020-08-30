/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.register;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.Glue;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \gluestretchorder}.
 * 
 * <p>The Primitive {@code \gluestretchorder}</p>
 * <p>
 * The primitive {@code \gluestretchorder} determines the order of the glue
 * stretch component of the following glue specification. A fixed,
 * non-stretchable glue returns the value 0. Glue with the order fi gives 1, fil
 * gives 2, fill gives 3, and filll gives 4.
 * </p>
 * <p>
 * Note that the glue specification of 1&nbsp;fi has been introduced by
 *  Omega.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;gluestretchorder&rang;
 *      &rarr; {@code \gluestretchorder} {@linkplain
 *        org.extex.interpreter.parser.GlueParser#parseGlue(
 *        org.extex.interpreter.context.Context,
 *        org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
 *        &lang;glue&rang;} </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *   \gluestretchorder\skip1  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Ogluestretchorder extends AbstractCode
        implements
            CountConvertible,
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
    public Ogluestretchorder(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Glue glue = source.parseGlue(context, source, typesetter);
        return glue.getStretch().getOrder();
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        return context.getTokenFactory().toTokens(
            convertCount(context, source, typesetter));
    }

}
