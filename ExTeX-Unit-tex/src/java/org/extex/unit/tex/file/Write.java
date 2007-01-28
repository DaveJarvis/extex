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
import org.extex.interpreter.type.TokensWriter;
import org.extex.interpreter.type.file.ExecuteFile;
import org.extex.interpreter.type.file.LogFile;
import org.extex.interpreter.type.file.OutFile;
import org.extex.interpreter.type.file.UserAndLogFile;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.WhatsItWriteNode;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.util.framework.configuration.Configurable;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.logger.LogEnabled;

/**
 * This class provides an implementation for the primitive <code>\write</code>.
 *
 * <doc name="write">
 * <h3>The Primitive <tt>\write</tt></h3>
 * <p>
 *  The primitive <tt>\write</tt> can be used to write some text to an output
 *  stream. There are two modes of operation: Either the writing is delayed
 *  until the page is shipped or the writing is performed immediately. The
 *  default mode of operation is the delayed writing. The prefix
 *  <tt>\immediate</tt> can be used to switch to the immediate writing.
 * </p>
 * <p>
 *  The first argument to <tt>\write</tt> is the stream. It is usually opened
 *  with <tt>\openin</tt>. If the stream has not been opened this way or has
 *  been closed in the mean time then the result is written to the console and
 *  the log file.
 * </p>
 * <p>
 *  The second argument is a block of text. It is stored away and expanded just
 *  before the writing occurs. This means that the values of control sequences
 *  or registers are in fact used with their meaning when the page is shipped
 *  in the case of delayed writing.
 * </p>
 * <p>
 *  TODO missing documentation
 * </p>
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;write&rang;
 *      &rarr; &lang;modifier&rang; <tt>\write</tt> {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanOutFileKey(Context,TokenSource,Typesetter)
 *        &lang;outfile&nbsp;name&rang;} ...
 *
 *    &lang;modifier&rang;
 *      &rarr;
 *       |  <tt>\immediate</tt> &lang;modifier&rang;  </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 * \immediate\openout3= abc.def
 * \write3{Hi there!}
 * \closeout3 </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4441 $
 */
public class Write extends AbstractCode
        implements
            TokensWriter,
            LogEnabled,
            Configurable {

    /**
     * The constant <tt>LOG_FILE</tt> contains the key for the log file.
     */
    private static final String LOG_FILE = "-1";

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20061001L;

    /**
     * The field <tt>SYSTEM</tt> contains the key for the system execute
     * (\write18).
     */
    private static final String SYSTEM = "18";

    /**
     * The field <tt>USER_AND_LOG</tt> contains the key for the user trace and
     * log file.
     */
    private static final String USER_AND_LOG = "17";

    /**
     * The field <tt>init</tt> contains the indicator that the standard streams
     * are initialized.
     */
    private transient boolean init = false;

    /**
     * The field <tt>logger</tt> contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * The field <tt>write18</tt> contains the indicator that the ancient
     * \write18 feature of <logo>TeX</logo> should be enabled.
     */
    private boolean write18 = false;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Write(final String name) {

        super(name);
    }

    /**
     * @see org.extex.util.framework.configuration.Configurable#configure(
     *      org.extex.util.framework.configuration.Configuration)
     */
    public void configure(final Configuration config)
            throws ConfigurationException {

        write18 = Boolean.valueOf(config.getAttribute("write18"))
                .booleanValue();
    }

    /**
     * @see org.extex.util.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = AbstractFileCode.scanOutFileKey(context, source,
                typesetter);

        if (prefix.clearImmediate()) {

            Tokens toks = source.scanUnprotectedTokens(context, false, false,
                    getName());
            write(key, toks, context);

        } else {

            Tokens toks = source.getTokens(context, source, typesetter);

            try {
                typesetter.add(new WhatsItWriteNode(key, toks, source, this));
            } catch (TypesetterException e) {
                throw new InterpreterException(e);
            } catch (ConfigurationException e) {
                throw new InterpreterException(e);
            }
        }
    }

    /**
     * Write some tokens to a write register.
     *
     * @param key the name (number) of the write register
     * @param toks the tokens to write
     * @param context the processing context
     *
     * @throws InterpreterException in case of an error
     */
    public void write(final String key, final Tokens toks, final Context context)
            throws InterpreterException {


        OutFile file = context.getOutFile(key);

        if (file == null || !file.isOpen()) {

            if (!init) {
                init = true;
                context.setOutFile(LOG_FILE, new LogFile(logger), true);
                context.setOutFile(USER_AND_LOG, new UserAndLogFile(logger), true);
                if (write18) {
                    context.setOutFile(SYSTEM, new ExecuteFile(logger), true);
                }
            }

            if (key == null || "".equals(key) || key.charAt(0) == '-') {
                file = context.getOutFile(LOG_FILE);
            } else {
                file = context.getOutFile(USER_AND_LOG);
            }
        }

        try {
            file.write(toks);
        } catch (IOException e) {
            throw new InterpreterException(e);
        }
    }

}
