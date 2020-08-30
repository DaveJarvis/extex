/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.info;

import java.util.logging.Logger;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \message}
 * .
 * 
 * <p>The Primitive {@code \message}</p>
 * <p>
 * The primitive {@code \message} takes as argument a list of tokens enclosed
 * in braces and writes them to output stream and into the log file.
 * </p>
 * <p>
 * If the keywords {@code to log} are given then the message is written to the
 * log file only. This is an extension not present in TeX and friends.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;message&rang;
 *      &rarr; {@code \message} {@code {} &lang;unprotected tokens&rang; {@code }}
 *       |   {@code \message} {@code to} {@code log} {@code {} &lang;unprotected tokens&rang; {@code }}
 *       </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \message{Hello World!}  </pre>
 * 
 * <pre class="TeXSample">
 *    \message to log {Hello World!}  </pre>
 * 
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Message extends AbstractCode implements LogEnabled {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code logger} contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Message(CodeToken token) {

        super(token);
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        this.logger = log;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        boolean log = false;

        if (source.getKeyword(context, "to")) {

            if (source.getKeyword(context, "log")) {
                log = true;
            } else {
                throw new HelpingException(getLocalizer(), "logMissing");
            }
        }

        Tokens toks =
                source.scanUnprotectedTokens(context, true, false, getToken());
        if (log) {
            logger.fine(" " + toks.toText());
        } else {
            logger.severe(" " + toks.toText());
        }
    }

}
