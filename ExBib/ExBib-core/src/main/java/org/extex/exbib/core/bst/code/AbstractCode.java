/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.Code;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is a superclass for classes implementing the interface {@link Code}.
 * The handling of the name attribute is located in this class.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public abstract class AbstractCode implements Code {

    /**
     * Each code has a name. This is the String under which it is registered in
     * the processor context.
     */
    private String name = "";

    /**
     * Create a new object.
     */
    public AbstractCode() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the name of the code in the processor context
     */
    public AbstractCode(String name) {

        super();
        this.name = name;
    }

    /**
     * {@inheritDoc}
     * 
     * This method can be overloaded in a derived class. The default behavior is
     * that nothing is done.
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Code#execute(
     *      org.extex.exbib.core.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    public abstract void execute(Processor processor, Entry entry,
            Locator locator) throws ExBibException;

    /**
     * Getter for the name. Each code has a name. This is the String under which
     * it is registered in the processor context.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Setter for the name. Each code has a name. This is the String under which
     * it is registered in the processor context.
     * 
     * @param name the new name
     */
    public void setName(String name) {

        this.name = name;
    }

}
