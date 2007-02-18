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

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.file.OutFile;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.WhatsItCloseNode;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.logger.LogEnabled;

/**
 * This class provides an implementation for the primitive
 * <code>\closeout</code>.
 *
 * <doc name="closeout">
 * <h3>The Primitive <tt>\closeout</tt></h3>
 * <p>
 *  The primitive takes one expanded integer argument. This argument denotes a
 *  write register which will be closed if it is currently assigned to a file
 *  &ndash; with {@link org.extex.unit.tex.file.Openout <tt>\openout</tt>}.
 *  If the input file assigned to the given number has not been opened or has
 *  been closed before then this primitive simply does nothing.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;closeout&rang;
 *       &rarr; <tt>\closeout</tt> {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanOutFileKey(Context,TokenSource,Typesetter)
 *        &lang;outfile&nbsp;name&rang;} </pre>
 * </p>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
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
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>logger</tt> contains the logger to use.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Closeout(final String name) {

        super(name);
    }

    /**
     * Setter for the logger.
     *
     * @param theLogger the new logger
     *
     * @see org.extex.util.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException, ConfigurationException {

        String key = AbstractFileCode.scanOutFileKey(context, source,
                typesetter);

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
