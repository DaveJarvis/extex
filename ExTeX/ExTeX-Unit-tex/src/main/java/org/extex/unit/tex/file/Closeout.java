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

package org.extex.unit.tex.file;

import java.io.IOException;
import java.util.logging.Logger;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.file.OutFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.unit.tex.file.nodes.WhatsItCloseNode;

/**
 * This class provides an implementation for the primitive
 * <code>\closeout</code>.
 * 
 * <doc name="closeout">
 * <h3>The Primitive <tt>\closeout</tt></h3>
 * <p>
 * The primitive takes one expanded integer argument. This argument denotes a
 * write register which will be closed if it is currently assigned to a file
 * &ndash; with {@link org.extex.unit.tex.file.Openout <tt>\openout</tt>}. If
 * the input file assigned to the given number has not been opened or has been
 * closed before then this primitive simply does nothing.
 * </p>
 * 
 * <h4>Syntax</h4>
 * <p>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;closeout&rang;
 *       &rarr; <tt>\closeout</tt> {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanOutFileKey(Context,TokenSource,Typesetter)
 *        &lang;outfile&nbsp;name&rang;} </pre>
 * 
 * </p>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \closeout5  </pre>
 *  <pre class="TeXSample">
 *    \closeout\count120  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4441 $
 */
public class Closeout extends AbstractCode implements LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>logger</tt> contains the logger to use.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Closeout(CodeToken token) {

        super(token);
    }

    /**
     * Setter for the logger.
     * 
     * @param theLogger the new logger
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key =
                AbstractFileCode.scanOutFileKey(context, source, typesetter);

        if (prefix.clearImmediate()) {
            OutFile file = context.getOutFile(key);
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    logger.info(e.getLocalizedMessage() + "\n");
                }
            }
        } else {
            typesetter.add(new WhatsItCloseNode(key));
        }
    }

}
