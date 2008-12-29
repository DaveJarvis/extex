/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst;

import java.util.Iterator;

import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;

/**
 * This class implements a processor. The major features are configurable.
 * 
 * <h3>The Configuration</h3>
 * <p>
 * The configuration can be performed with the method
 * {@link #configure(Configuration)}. The configuration should contain a list of
 * function tags which are used to set up the functions of the processor. Each
 * one needs to have the attributes <tt>name</tt> and <tt>class</tt>.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class BstInterpreter extends BstInterpreterCore {

    /**
     * Create a new object.
     * 
     * @throws ExBibException in case of an error
     */
    public BstInterpreter() throws ExBibException {

        super();
    }

    /**
     * Configure an object according to a given Configuration.
     * 
     * @param config the configuration object to consider
     * 
     * @throws ConfigurationException in case that something went wrong
     */
    @Override
    public void configure(Configuration config) throws ConfigurationException {

        super.configure(config);

        Iterator<Configuration> iterator = config.iterator("function");

        while (iterator.hasNext()) {
            Configuration builtin = iterator.next();
            String name = builtin.getAttribute("name");
            if (name == null || name.equals("")) {
                throw new ConfigurationMissingException("name");
            }
            String className = builtin.getAttribute("class");
            if (className == null || className.equals("")) {
                throw new ConfigurationMissingException("class");
            }
            try {
                Code code = (Code) Class.forName(className).newInstance();
                code.setName(name);
                if (code instanceof Configurable) {
                    ((Configurable) code).configure(builtin);
                }
                addFunction(name, code, new Locator(config.toString(), 0));
            } catch (Exception e) {
                System.err.println(name + " " + className);
                throw new ConfigurationWrapperException(e);
            }
        }
    }

}
