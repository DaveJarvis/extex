/*
 * Copyright (C) 2004 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */
package de.dante.extex.logging;

import java.io.File;

import de.dante.util.configuration.ConfigurationException;
import de.dante.util.configuration.ConfigurationInstantiationException;
import de.dante.util.configuration.ConfigurationMissingException;

/*
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LoggerFactory {

    /**
     * The field <tt>classname</tt> contains the name of the class to
     * instantiate.
     */
    private String classname;
    
    /**
     * Creates a new object.
     * 
     * @param classname the configuration to use
     *
     * @throws ConfigurationException in case that the configuration is missing
     */
    public LoggerFactory(final String classname) throws ConfigurationException {
        super();
        this.classname = classname;
        if ( classname == null ) {
            throw new ConfigurationMissingException("classname");//TODO i18n
        }
    }

    /**
     * ...
     * 
     * @param name ...
     * @param logfile ...
     * @param template ...
     *
     * @return ...
     *
     * @throws ConfigurationException ...
     */
    public Logger getInstance(final String name, final File logfile,
        final String template) throws ConfigurationException {
        Logger logger;

        try {
            logger = (Logger) (Class.forName(classname).getDeclaredMethod(
                "getLogger",
                new Class[]{String.class, File.class, String.class})
                .invoke(null, new Object[]{name, logfile, template}));

        } catch (Exception e) {
            throw new ConfigurationInstantiationException(e);
        }

        return logger;
    }
}
