/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.resource;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.framework.logger.LogEnabled;

/**
 * This the abstract base class to provide the infrastructure for resource
 * finders.
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractFinder implements LogEnabled, ResourceFinder {

    /**
     * The constant {@code ATTR_DEFAULT} contains the attribute name for the
     * default type.
     */
    protected static final String ATTR_DEFAULT = "default";

    /**
     * The field {@code ATTR_SKIP} contains the attribute name for the skip
     * indicator.
     */
    protected static final String ATTR_SKIP = "skip";

    /**
     * The constant {@code ATTR_TRACE} contains the attribute name for the
     * tracing enabler.
     */
    protected static final String ATTR_TRACE = "trace";

    /**
     * The constant {@code EXTENSION_TAG} contains the name of the tag to get
     * the possible extensions.
     */
    protected static final String EXTENSION_TAG = "extension";

    /**
     * The field {@code bundle} contains the resource bundle for messages.
     */
    private ResourceBundle bundle = null;

    /**
     * The field {@code configuration} contains the configuration object on
     * which this resource finder is based.
     */
    private Configuration configuration;

    /**
     * The field {@code logger} contains the logger to be used for tracing.
     */
    private Logger logger = null;

    /**
     * The field {@code trace} contains the indicator that tracing is
     * required. This field is set to {@code true} according to the
     * configuration.
     */
    private boolean trace = false;

    /**
     * Creates a new object.
     * 
     * @param configuration the encapsulated configuration object
     * 
     * @throws ConfigurationMissingException in case of an error: The argument
     *         configuration is {@code null}.
     */
    public AbstractFinder(Configuration configuration)
            throws ConfigurationMissingException {

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
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * Enable or disable the tracing. The argument indicates whether tracing
     * should be enabled or disabled. The resource finder can decide on its own
     * how to perform tracing. The preferred way is to write tracing records to
     * a logger.
     * 
     * @param flag indicator whether tracing should be turned on or off.
     * 
     * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
     */
    public void enableTracing(boolean flag) {

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
     * @param args the arguments to insert
     */
    protected void trace(String key, Object... args) {

        if (trace && logger != null) {
            if (bundle == null) {
                bundle = ResourceBundle.getBundle(getClass().getName());
            }

            logger.fine(MessageFormat.format(bundle.getString(key), args));
        }
    }

}
