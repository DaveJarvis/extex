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

package org.extex.unit.tex;

import java.util.logging.Logger;

import org.extex.base.type.file.ExecuteFile;
import org.extex.base.type.file.LogFile;
import org.extex.base.type.file.UserAndLogFile;
import org.extex.core.count.Count;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.ContextInternals;
import org.extex.interpreter.context.observer.count.CountObservable;
import org.extex.interpreter.context.observer.count.CountObserver;
import org.extex.interpreter.context.observer.load.LoadedObserver;
import org.extex.interpreter.observer.command.CommandObservable;
import org.extex.interpreter.unit.Loader;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.typesetter.Typesetter;

/**
 * This class provides the setup for the unit <b>tex</b>.
 * 
 * <h3>Tracing</h3>
 * <p>
 * Tracing is <logo>TeX</logo> is controlled by some count registers. The
 * implementation in <logo>ExTeX</logo> is based on observers. In the first
 * stage a {@link org.extex.interpreter.context.observer.count.CountObserver
 * CountObserver} for the controlling count is registered. In this observer the
 * observer for the real event is registered if this as not been done before and
 * the value of the controlling count is greater than 0.
 * </p>
 * <p>
 * This strategies tries to achieve that the overhead for the normal mode of
 * operation is minimized. Here only the controlling cont has to be watched. The
 * observer list for the event to be traced is empty and does not impose any
 * performance overhead.
 * </p>
 * 
 * <doc name="tracingonline" type="register">
 * <h3>The Count Parameter <tt>\tracingonline</tt></h3>
 * <p>
 * This count register <tt>\tracingonline</tt> determines whether the tracing
 * should go into the log file only or put on the standard output stream as
 * well. If the value is less than 1 then the tracing goes to the log file only.
 * Otherwise logging is duplicated to the console as well.
 * </p>
 * </doc>
 * 
 * <doc name="tracingcommands" type="register">
 * <h3>The Parameter <tt>\tracingcommands</tt></h3>
 * <p>
 * This count register determines whether the execution of commands should be
 * traced. If the value is less or equal than 0 then no tracing is performed. If
 * the value is greater than 0 then the tokens are logged before they are
 * executed.
 * </p>
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class TexUnitInfo extends UnitInfo
        implements
            Configurable,
            Loader,
            LoadedObserver,
            LogEnabled {

    /**
     * Observer to reestablish a trace commands observer after loading.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private final class Observer implements CountObserver {

        /**
         * The field <tt>source</tt> contains the token source.
         */
        private transient TokenSource source = null;

        /**
         * Creates a new object.
         * 
         * @param source the source for more tokens
         */
        public Observer(TokenSource source) {

            this.source = source;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.context.observer.count.CountObserver#receiveCountChange(
         *      org.extex.interpreter.context.ContextInternals,
         *      java.lang.String, org.extex.core.count.Count)
         */
        public void receiveCountChange(ContextInternals context, String name,
                Count value) throws Exception {

            if (notRegistered && value != null && value.getValue() > 0
                    && source instanceof CommandObservable) {
                ((CommandObservable) source)
                    .registerObserver(new TraceCommandObserver(logger, context));
                notRegistered = false;
            }
        }
    }

    /**
     * The constant <tt>LOG_FILE</tt> contains the key for the log file.
     */
    private static final String LOG_FILE = "-1";

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>SYSTEM</tt> contains the key for the system execute
     * (\write18).
     */
    private static final String SYSTEM = "18";

    /**
     * The field <tt>TRACING_COMMANDS</tt> contains the name of the count
     * register controlling the activation of command tracing.
     */
    private static final String TRACING_COMMANDS = "tracingcommands";

    /**
     * The field <tt>USER_AND_LOG</tt> contains the key for the user trace and
     * log file.
     */
    private static final String USER_AND_LOG = "17";

    /**
     * The field <tt>logger</tt> contains the local reference to the logger.
     */
    private transient Logger logger;

    /**
     * The field <tt>notRegistered</tt> contains the indicator that the
     * observer for command events as not been registered yet.
     */
    private transient boolean notRegistered = true;

    /**
     * The field <tt>write18</tt> contains the indicator that the ancient
     * \write18 feature of <logo>TeX</logo> should be enabled.
     */
    private boolean write18 = false;

    /**
     * Creates a new object.
     */
    public TexUnitInfo() {

        super();
        logger = null;
        notRegistered = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        String a = config.getAttribute("write18");
        write18 = (a != null && Boolean.getBoolean(a));
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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.unit.Loader#load(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void load(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException {

        receiveLoaded(context, source, typesetter);
    }

    /**
     * Return the singleton constant object after the serialized instance has
     * been read back in.
     * 
     * @return the instance of this object
     */
    protected Object readResolve() {

        notRegistered = true;
        return this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.observer.load.LoadedObserver#receiveLoaded(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public void receiveLoaded(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        if (context.getCount(TRACING_COMMANDS).gt(Count.ZERO)) {
            if (notRegistered && source instanceof CommandObservable) {
                ((CommandObservable) source)
                    .registerObserver(new TraceCommandObserver(logger, context));
                notRegistered = false;
            }
        } else if (context instanceof CountObservable) {
            ((CountObservable) context).registerCountObserver(TRACING_COMMANDS,
                new Observer(source));
        }

        context.setOutFile(LOG_FILE, new LogFile(logger), true);
        context.setOutFile(USER_AND_LOG, new UserAndLogFile(logger), true);
        if (write18) {
            context.setOutFile(SYSTEM, new ExecuteFile(logger), true);
        }

    }

}