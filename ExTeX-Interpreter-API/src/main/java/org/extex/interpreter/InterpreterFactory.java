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

package org.extex.interpreter;

import java.util.Properties;
import java.util.logging.Logger;

import org.extex.backend.outputStream.OutputStreamConsumer;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.PropertyConfigurable;

/**
 * This class provides a factory for
 * {@link org.extex.interpreter.Interpreter Interpreter}s.
 * The configuration and the logger are passed to the new instance if they are
 * present and required.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class InterpreterFactory extends AbstractFactory {

    /**
     * Creates a new factory object.
     *
     * @param configuration the configuration for this factory
     * @param logger the logger
     *
     * @throws ConfigurationException in case of an error in the configuration.
     */
    public InterpreterFactory(Configuration configuration,
            Logger logger) throws ConfigurationException {

        super();
        enableLogging(logger);
        configure(configuration);
    }

    /**
     * Get a instance for the interface
     * <tt>{@link org.extex.interpreter.Interpreter Interpreter}</tt>.
     *
     * @param properties the properties
     * @param outFactory the output stream factory
     *
     * @return a new instance for the interface Interpreter
     *
     * @throws ConfigurationException in case of an error in the configuration
     */
    public Interpreter newInstance(Properties properties,
            OutputStreamFactory outFactory) throws ConfigurationException {

        Interpreter interpreter =
                (Interpreter) createInstance(Interpreter.class);

        if (interpreter instanceof PropertyConfigurable) {
            ((PropertyConfigurable) interpreter).setProperties(properties);
        }
        if (interpreter instanceof OutputStreamConsumer) {
            ((OutputStreamConsumer) interpreter)
                .setOutputStreamFactory(outFactory);
        }

        return interpreter;
    }

}
