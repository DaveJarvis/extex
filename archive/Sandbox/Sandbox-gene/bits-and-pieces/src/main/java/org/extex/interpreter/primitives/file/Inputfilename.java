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

package org.extex.interpreter.primitives.file;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \inputfilename}. This is an extension primitive not present in
 *  TeX.
 *
 * <p>The Primitive {@code \inputfilename}</p>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>

 *
 * <p>Examples</p>

 *  <pre class="TeXSample">
 *    \inputfilename  </pre>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Inputfilename extends AbstractCode
        implements
            ExpandableCode,
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
    public Inputfilename(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        try {
            source.push(the(context, source, typesetter));
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        try {
            source.push(the(context, source, typesetter));
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source,
            Typesetter typesetter)
            throws CatcodeException, HelpingException, TypesetterException {

        String filename = source.getLocator().getResourceName();
        return context.getTokenFactory().toTokens(
            (filename == null ? "" : filename));
    }

}
