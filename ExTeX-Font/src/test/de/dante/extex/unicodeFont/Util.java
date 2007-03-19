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

package de.dante.extex.unicodeFont;

import java.util.Properties;
import java.util.logging.Logger;

import org.extex.font.FontFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.InteractionIndicator;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Util {

    /**
     * Creates a new object.
     */
    private Util() {

    }

    /**
     * ...
     *
     * @return ...
     *
     * @throws ConfigurationException ...
     */
    public static ResourceFinder makeResourceFinder()
            throws ConfigurationException {

        Logger logger = Logger.getLogger(Util.class.getName());
        Configuration config =
                new ConfigurationFactory()
                    .newInstance("config/path/fontTestFileFinder.xml");
        Properties properties = System.getProperties();
        properties.setProperty("extex.fonts", "../ExTeX-BaseFont/src/font");

        ResourceFinder finder =
                new ResourceFinderFactory().createResourceFinder(config,
                    logger, properties, new InteractionIndicator() {

                        /**
                         * Getter for the interaction mode.
                         *
                         * @return <code>true</code> iff interaction with the
                         *         user is desirable
                         *
                         * @see org.extex.resource.InteractionIndicator#isInteractive()
                         */
                        public boolean isInteractive() {

                            return false;
                        }
                    });

        return finder;
    }

    /**
     * ...
     *
     * @return ...
     *
     * @throws Exception ...
     */
    public static FontFactory makeFontFactory() throws Exception {

        Configuration config =
                new ConfigurationFactory().newInstance(
                    "config/extex-test-font.xml").getConfiguration("Fonts");
        String fontClass = config.getAttribute("class");

        return (FontFactory) (Class.forName(fontClass).getConstructor(
            new Class[]{Configuration.class, //
                    ResourceFinder.class}).newInstance(new Object[]{config,
                makeResourceFinder()}));

    }

}
