/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
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

package org.extex.exbib.core.db.sorter;

import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is a factory to deliver sorters.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class DbSorterFactory extends AbstractFactory {

    /**
     * Creates a new object.
     * 
     * @param configuration the configuration for this factory
     */
    public DbSorterFactory(Configuration configuration) {

        super(configuration);
    }

    /**
     * Provides a {@link Sorter Sorter} implementation. The new
     * {@link Sorter Sorter} is configured with the same configuration as the
     * factory.
     * 
     * @return the new Sorter instance
     * 
     * @throws ConfigurationException in case that something goes wrong
     */
    public Sorter newInstance() throws ConfigurationException {

        return (Sorter) createInstance(Sorter.class);
    }

}
