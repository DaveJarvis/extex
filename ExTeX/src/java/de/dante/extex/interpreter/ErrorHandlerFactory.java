/*
 * Copyright (C) 2003-2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
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

package de.dante.extex.interpreter;

import de.dante.extex.main.errorHandler.editHandler.EditHandler;
import de.dante.util.configuration.Configuration;
import de.dante.util.configuration.ConfigurationException;
import de.dante.util.framework.AbstractFactory;

/**
 * This is the factory for instances of
 * {@link de.dante.extex.interpreter.ErrorHandler ErrorHandler}.
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ErrorHandlerFactory extends AbstractFactory {

    /**
     * Creates a new object.
     */
    public ErrorHandlerFactory() {

        super();
    }

    /**
     * Creates a new object and configures it according to the parameter.
     *
     * @param configuration the configuration to use
     *
     * @throws ConfigurationException in case of an error during configuration
     */
    public ErrorHandlerFactory(final Configuration configuration)
            throws ConfigurationException {

        super();
        configure(configuration);
    }

    /**
     * Get an instance of an error handler.
     * This method selects one of the entries in the configuration. The
     * selection is done with the help of a type String. If the type is
     * <code>null</code> or the empty string then the default from the
     * configuration is used.
     *
     * @param type the type to use
     *
     * @return a new error handler
     *
     * @throws ConfigurationException in case of an configuration error
     */
    public ErrorHandler newInstance(final String type)
            throws ConfigurationException {

        ErrorHandler errorHandler = (ErrorHandler) createInstance(type,
                ErrorHandler.class);
        Configuration cfg = selectConfiguration(type).findConfiguration(
                "EditHandler");
        if (cfg != null) {
            errorHandler
                    .setEditHandler((EditHandler) createInstanceForConfiguration(
                            cfg, EditHandler.class));
        }
        return errorHandler;
    }
}