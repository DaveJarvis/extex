/*
 * Copyright (C) 2004  Gerd Neugebauer
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
package de.dante.extex.interpreter.context;

import de.dante.util.configuration.Configuration;
import de.dante.util.configuration.ConfigurationException;
import de.dante.util.configuration.ConfigurationMissingAttributeException;

/*
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TypesettingContextFactory {

    private Configuration config;
    
    private String classname;
    
    /**
     * Creates a new object.
     * 
     *  
     */
    public TypesettingContextFactory(Configuration config)
            throws ConfigurationException {
        super();
        this.config = config;
        classname = config.getAttribute("class");
        if ( classname == null ) {
            throw new ConfigurationMissingAttributeException("class");
        }
    }

    /**
     * ...
     * 
     * @return
     * @throws ConfigurationException
     */
    public TypesettingContext newInstance() throws ConfigurationException {
        TypesettingContext context;

        try {
            context = (TypesettingContext) (Class.forName(classname)
                    .newInstance());
        } catch (Exception e) {
            throw new ConfigurationException("TypesettingContextFactory", e);
        }

        return context;
    }
}
