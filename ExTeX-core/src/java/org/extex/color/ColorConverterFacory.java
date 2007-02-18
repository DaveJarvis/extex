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

package org.extex.color;

import java.util.logging.Logger;

import org.extex.util.framework.AbstractFactory;
import org.extex.util.framework.configuration.Configuration;

/**
 * This is the factory to provide an instance of a color converter.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ColorConverterFacory extends AbstractFactory {

    /**
     * Creates a new object.
     *
     * @param config the configuration
     * @param logger the logger
     *
     * @throws ConfigurationException in case of an error
     */
    public ColorConverterFacory(final Configuration config, final Logger logger) {

        super();
        enableLogging(logger);
        configure(config);
    }

    /**
     * Provide a new instance of a color converter.
     * The new instance is initiated with the sub-configuration describing it.
     *
     * @param type the type
     *
     * @return the new instance
     *
     * @throws ConfigurationException in case of a problem in the configuration
     */
    public ColorConverter newInstance(final String type) {

        return (ColorConverter) createInstance(type, ColorConverter.class);
    }

}
