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

package org.extex.unit.extex;

import java.util.logging.Logger;

import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.OutputStreamConsumer;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.interpreter.unit.LoadUnit;
import org.extex.typesetter.Typesetter;
import org.extex.util.exception.GeneralException;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.ConfigurationFactory;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.util.framework.logger.LogEnabled;

/**
 * This primitive initiates the loading of native code and implements the
 * primitive <tt>\ensureloaded</tt>.
 *
 * <doc name="ensureloaded">
 * <h3>The Primitive <tt>\ensureloaded</tt></h3>
 * <p>
 *  The primitive <tt>\ensureloaded</tt> dynamically requests that a unit of
 *  <logo>ExTeX</logo> is loaded.
 * </p>
 * <p>
 *  A unit consists of primitives and some initializing actions.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The general form of this primitive is
 * <pre class="syntax">
 *   &lang;ensureloaded&rang;
 *       &rarr; <tt>\ensureloaded</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *        &lang;tokens&rang;} </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \ensureloaded{etex}  </pre>
 *  <pre class="TeXSample">
 *    \ensureloaded\toks0  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EnsureLoaded extends AbstractCode
        implements
            OutputStreamConsumer,
            LogEnabled {

    /**
     * The constant <tt>CONFIG_UNIT</tt> contains the prefix for the
     * path of the configuration.
     */
    private static final String CONFIG_UNIT = "config/unit/";

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>logger</tt> contains the logger to use.
     */
    private transient Logger logger = null;

    /**
     * The field <tt>outFactory</tt> contains the output factory.
     */
    private transient OutputStreamFactory outFactory;

    /**
     * Creates a new object.
     *
     * @param codeName the name of the primitive
     */
    public EnsureLoaded(final String codeName) {

        super(codeName);
    }

    /**
     * Setter for the logger.
     *
     * @param log the logger to use
     *
     * @see org.extex.util.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger log) {

        this.logger = log;
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
     *       org.extex.interpreter.Flags,
     *       org.extex.interpreter.context.Context,
     *       org.extex.interpreter.TokenSource,
     *       org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        Tokens tokens = source.scanTokens(context, false, false, getName());

        if (tokens == null) {
            throw new EofException(getName());
        }
        String configName = tokens.toText();
        try {
            Configuration configuration = new ConfigurationFactory()
                    .newInstance(CONFIG_UNIT + configName);
            LoadUnit.loadUnit(configuration, context, source, typesetter,
                    logger, outFactory);
        } catch (InterpreterException e) {
            throw e;
        } catch (GeneralException e) {
            throw new InterpreterException(e);
        } catch (ConfigurationNotFoundException e) {
            throw new HelpingException(getLocalizer(), "UnknownUnit",
                    configName);
        }
    }

    /**
     * Getter for logger.
     *
     * @return the logger
     */
    protected Logger getLogger() {

        return this.logger;
    }

    /**
     * This method takes an output stream factory for further use.
     *
     * @param factory the output stream factory to use
     *
     * @see org.extex.interpreter.type.OutputStreamConsumer#setOutputStreamFactory(
     *      org.extex.backend.outputStream.OutputStreamFactory)
     */
    public void setOutputStreamFactory(final OutputStreamFactory factory) {

        this.outFactory = factory;
    }

}
