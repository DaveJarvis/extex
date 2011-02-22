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

package org.extex.unit.extex;

import java.util.logging.Logger;

import org.extex.backend.outputStream.OutputStreamConsumer;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.LoadUnit;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This primitive initiates the loading of native code and implements the
 * primitive <tt>\ensureloaded</tt>.
 * 
 * <doc name="ensureloaded"> <h3>The Primitive <tt>\ensureloaded</tt></h3>
 * <p>
 * The primitive <tt>\ensureloaded</tt> dynamically requests that a unit of
 * <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> is loaded.
 * </p>
 * <p>
 * A unit consists of primitives and some initializing actions.
 * </p>
 * 
 * <h4>Syntax</h4> The general form of this primitive is
 * 
 * <pre class="syntax">
 *   &lang;ensureloaded&rang;
 *       &rarr; <tt>\ensureloaded</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *        &lang;tokens&rang;} </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \ensureloaded{etex}  </pre>
 * 
 * <pre class="TeXSample">
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
            LogEnabled,
            ResourceAware {

    /**
     * The constant <tt>CONFIG_UNIT</tt> contains the prefix for the path of the
     * configuration.
     */
    private static final String CONFIG_UNIT = "config/unit/";

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>logger</tt> contains the logger to use.
     */
    private transient Logger logger = null;

    /**
     * The field <tt>outFactory</tt> contains the output factory.
     */
    private transient OutputStreamFactory outFactory;

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private transient ResourceFinder finder;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public EnsureLoaded(CodeToken token) {

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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String configName = source.scanTokensAsString(context, getToken());

        try {
            Configuration configuration =
                    ConfigurationFactory.newInstance(CONFIG_UNIT + configName);
            LoadUnit.loadUnit(configuration, context, source, typesetter,
                logger, outFactory, finder);
        } catch (HelpingException e) {
            throw e;
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        } catch (ConfigurationNotFoundException e) {
            throw new HelpingException(getLocalizer(), "UnknownUnit",
                configName);
        }
    }

    /**
     * This method takes an output stream factory for further use.
     * 
     * @param factory the output stream factory to use
     * 
     * @see org.extex.backend.outputStream.OutputStreamConsumer#setOutputStreamFactory(org.extex.backend.outputStream.OutputStreamFactory)
     */
    public void setOutputStreamFactory(OutputStreamFactory factory) {

        this.outFactory = factory;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;
    }

}
