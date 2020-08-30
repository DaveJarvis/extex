/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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
import java.io.OutputStream;
import java.util.Calendar;
import java.util.logging.Logger;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamConsumer;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.InterpreterPanicException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.engine.backend.NamedOutputStream;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.loader.SerialLoader;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \dump}.
 * 
 * <p>The Primitive {@code \dump}</p>
 * <p>
 * The primitive writes out the current state of the interpreter to an format
 * file. This format file can be read back in to restore the saved state.
 * </p>
 * <p>
 * The primitive can be used outside of any group only.
 * </p>
 * <p>
 * The name of the format file is derived from the job name. The extension
 * {@code .fmt} is attached to the job name.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;dump&rang;
 *       &rarr; {@code \dump}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \dump  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Dump extends AbstractCode
        implements
            LogEnabled,
            OutputStreamConsumer {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The constant {@code FORMAT_EXTENSION} contains the extension for the
     * format file.
     */
    private static final String FORMAT_EXTENSION = "fmt";

    /**
     * The field {@code logger} contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * The field {@code outFactory} contains the output factory.
     */
    private transient OutputStreamFactory outFactory;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Dump(CodeToken token) {

        super(token);
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        this.logger = log;
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (!context.isGlobalGroup()) {
            throw new HelpingException(getLocalizer(), "TTP.DumpInGroup");
        }

        if (outFactory == null) {
            throw new RuntimeException("Missing output stream factory");
        }

        Tokens jobnameTokens = context.getToks("jobname");
        if (jobnameTokens == null) {
            throw new InterpreterPanicException(getLocalizer(),
                "Dump.MissingJobname", toText(context));
        }
        String jobname = jobnameTokens.toText();
        Calendar calendar = Calendar.getInstance();

        context.setId(jobname
                + " "
                +
                calendar.get(Calendar.YEAR) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "."
                + calendar.get(Calendar.DAY_OF_MONTH));

        OutputStream stream = null;
        try {
            stream = outFactory.getOutputStream(jobname, FORMAT_EXTENSION);
            String target =
                    (stream instanceof NamedOutputStream
                            ? ((NamedOutputStream) stream).getName()
                            : jobname);
            logger.info(getLocalizer().format("TTP.Dumping", target));

            new SerialLoader().save(stream, jobname, context);

        } catch (IOException e) {
            throw new NoHelpException(e.toString());
        } catch (DocumentWriterException e) {
            throw new NoHelpException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new NoHelpException(e);
                }
            }
        }
    }

    /**
     * This method takes an output stream factory for further use.
     * 
     * @param factory the output stream factory to use
     * 
     * @see org.extex.backend.outputStream.OutputStreamConsumer#setOutputStreamFactory(
     *      org.extex.backend.outputStream.OutputStreamFactory)
     */
    public void setOutputStreamFactory(OutputStreamFactory factory) {

        this.outFactory = factory;
    }

}
