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

import java.util.List;

import org.extex.exbib.core.db.Entry;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This interface describes a function object for sorting a list of entries.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public interface Sorter extends Configurable {

    /**
     * Sort the given list in place.
     * 
     * @param list the list to sort
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    void sort(List<Entry> list) throws ConfigurationException;

}
