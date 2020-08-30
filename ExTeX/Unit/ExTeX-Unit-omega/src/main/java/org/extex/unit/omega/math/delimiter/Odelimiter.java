/*
 * Copyright (C) 2004-2008 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.math.delimiter;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.math.MathDelimiter;

/**
 * This class provides an implementation for the primitive
 * {@code \odelimiter}.
 * 
 * <p>The Math Primitive {@code \odelimiter}</p>
 * <p>
 * The math primitive {@code \odelimiter} can be used to insert a delimiter.
 * Thus it is possible to bypass the definition of the delimiter code as
 * assigned to single characters.
 * </p>
 * TODO missing documentation
 * 
 * <p>Syntax</p>

 * <p>
 * The formal description of this primitive is the following:
 * </p>
 * 
 * <pre class="syntax">
 *    &lang;odelimiter&rang;
 *       &rarr; {@code \odelimiter} &lang;delcode&rang; </pre>
 * 
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \odelimiter "426830A </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Odelimiter extends AbstractOmegaDelimiter {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Odelimiter(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        NoadConsumer nc = getListMaker(context, typesetter);
        MathDelimiter del =
                parseDelimiter(context, source, typesetter, getToken());
        nc.add(del);
    }

}
