/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.output;

import java.util.logging.Logger;

import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.interpreter.Interpreter;

/**
 * This class provides a factory for the output routines.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class OutputRoutineFactory extends AbstractFactory {

    /**
     * Creates a new object.
     *
     * @param configuration the configuration for this factory
     * @param logger the logger
     *
     * @throws ConfigurationException in case of an error in the configuration.
     */
    public OutputRoutineFactory(final Configuration configuration,
            final Logger logger) {

        super();
        enableLogging(logger);
        configure(configuration);
    }

    /**
     * Create a new output routine.
     *
     * @param interpreter the current interpreter. This interpreter can be
     *   used to run the output routine in; alternatively it can be used to
     *   access all information necessary
     *
     * @return the output routine
     *
     * @throws ConfigurationException in case of a configuration error
     */
    public OutputRoutine newInstance(final Interpreter interpreter) {

        return (OutputRoutine) createInstanceForConfiguration(
            getConfiguration(), OutputRoutine.class, Interpreter.class,
            interpreter);
    }
}
