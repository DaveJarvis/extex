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

package org.extex.util.resource;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationMissingException;
import org.extex.util.framework.logger.LogEnabled;

/**
 * This the abstract base class to provide the infrastructure for
 * resource finders.
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractFinder implements LogEnabled, ResourceFinder {

    /**
     * The constant <tt>ATTR_DEFAULT</tt> contains the attribute name for the
     * default type.
     */
    protected static final String ATTR_DEFAULT = "default";

    /**
     * The field <tt>ATTR_SKIP</tt> contains the attribute name for the skip
     * indicator.
     */
    protected static final String ATTR_SKIP = "skip";

    /**
     * The constant <tt>ATTR_TRACE</tt> contains the attribute name for the
     * tracing enabler.
     */
    protected static final String ATTR_TRACE = "trace";

    /**
     * The constant <tt>EXTENSION_TAG</tt> contains the name of the tag to get
     * the possible extensions.
     */
    protected static final String EXTENSION_TAG = "extension";

    /**
     * The field <tt>bundle</tt> contains the resource bundle for messages.
     */
    private transient ResourceBundle bundle = null;

    /**
     * The field <tt>configuration</tt> contains the configuration object on
     * which this resource finder is based.
     */
    private transient Configuration configuration;

    /**
     * The field <tt>logger</tt> contains the logger to be used for tracing.
     */
    private transient Logger logger = null;

    /**
     * The field <tt>trace</tt> contains the indicator that tracing is required.
     * This field is set to <code>true</code> according to the configuration.
     */
    private boolean trace = false;

    /**
     * Creates a new object.
     *
     * @param configuration the encapsulated configuration object
     *
     * @throws ConfigurationMissingException in case of an error: The argument
     *   configuration is <code>null</code>.
     */
    public AbstractFinder(final Configuration configuration)
            throws ConfigurationMissingException {

        super();
        if (configuration == null) {
            throw new ConfigurationMissingException("");
        }
        this.configuration = configuration;
        String t = configuration.getAttribute(ATTR_TRACE);
        if (t != null && Boolean.valueOf(t).booleanValue()) {
            trace = true;
        }
    }

    /**
     * Setter for the logger.
     *
     * @param theLogger the logger to set.
     *
     * @see org.extex.util.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * @see org.extex.util.resource.ResourceFinder#enableTracing(boolean)
     */
    public void enableTracing(final boolean flag) {

        trace = flag;
    }

    /**
     * Getter for configuration.
     *
     * @return the configuration
     */
    protected Configuration getConfiguration() {

        return configuration;
    }

    /**
     * Produce an internationalized trace message.
     *
     * @param key the resource key for the message format
     * @param arg the first argument to insert
     * @param arg2 the second argument to insert
     */
    protected void trace(final String key, final String arg, final String arg2) {

        if (trace && logger != null) {
            if (bundle == null) {
                bundle = ResourceBundle.getBundle(getClass().getName());
            }

            logger.fine(MessageFormat.format(bundle.getString(key), //
                new Object[]{arg, arg2}));
        }
    }

    /**
     * Produce an internationalized trace message.
     *
     * @param key the resource key for the message format
     * @param arg the first argument to insert
     * @param arg2 the second argument to insert
     * @param arg3 the third argument to insert
     */
    protected void trace(final String key, final String arg, final String arg2,
            final String arg3) {

        if (trace && logger != null) {
            if (bundle == null) {
                bundle = ResourceBundle.getBundle(LsrFinder.class.getName());
            }

            logger.fine(MessageFormat.format(bundle.getString(key), //
                new Object[]{arg, arg2, arg3}));
        }
    }

}
