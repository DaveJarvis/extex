/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.extex.backend;

import org.extex.backend.BackendDriver;
import org.extex.backend.exception.BackendDocumentWriterDefinedException;
import org.extex.backend.exception.BackendUnknownDocumentWriterException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \writerType}.
 * 
 * <p>The Primitive {@code \writerType}</p>
 * <p>
 * The primitive {@code \writerType} provides access to the type of the
 * document writer in use. The type is a sequence of letter, other, and space
 * tokens.
 * </p>
 * <p>
 * The primitive can be used wherever a token register is applicable. It acts
 * like a read only register.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;writerType&rang;
 *      &rarr; {@code \writerType}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \writerType  </pre>
 * 
 * <p>
 * This invocation might expand to "dvi" or "ps".
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class WriterType extends AbstractCode
        implements
            ExpandableCode,
            Theable,
            TokensConvertible {

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
    public WriterType(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens convertTokens(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String type = typesetter.getBackendDriver().getDocumentWriterType();
        try {
            return context.getTokenFactory().toTokens(type == null ? "" : type);
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getOptionalEquals(context);
        String type = source.scanTokensAsString(context, getToken());
        try {
            BackendDriver backendDriver = typesetter.getBackendDriver();
            backendDriver.setDocumentWriterType(type);
        } catch (BackendDocumentWriterDefinedException e) {
            throw new HelpingException(getLocalizer(), "DocumentWriterDefined",
                type);
        } catch (BackendUnknownDocumentWriterException e) {
            throw new HelpingException(getLocalizer(), "DocumentWriterUnknown",
                type);
        }
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        source.push(convertTokens(context, source, typesetter));
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                ConfigurationException,
                HelpingException,
                TypesetterException {

        return convertTokens(context, source, typesetter);
    }

}
