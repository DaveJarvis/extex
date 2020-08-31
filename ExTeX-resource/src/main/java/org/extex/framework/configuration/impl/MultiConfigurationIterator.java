/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.impl;

import java.util.Iterator;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an Iterator over multiple Configurations.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MultiConfigurationIterator implements Iterator<Configuration> {

    /**
     * The field {@code iter} contains the internal iterator in config[ptr].
     */
    private Iterator<Configuration> iter = null;

    /**
     * The field {@code key} contains the symbolic key for this Iterator.
     */
    private final String key;

    /**
     * The field {@code config} contains the list of configurations to iterate
     * over.
     */
    private final Configuration[] configs;

    /**
     * The field {@code ptr} contains the index of configurations to be treated
     * next.
     */
    private int ptr = 0;

    /**
     * Creates a new object.
     * 
     * @param theConfigs the array of configurations to combine
     * @param theKey the name of the sub-configuration
     * 
     * @throws ConfigurationException in case of an error in a sub-iterator
     */
    public MultiConfigurationIterator(Configuration[] theConfigs, String theKey)
            throws ConfigurationException {

        this.configs = theConfigs.clone();
        this.key = theKey;

        if (theConfigs.length > 0) {
            iter = theConfigs[0].iterator(theKey);
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements. (In other
     * words, returns {@code true} if {@code next} would return an element
     * rather than throwing an exception.)
     * 
     * @return {@code true} if the iterator has more elements.
     * 
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {

        if (iter == null) {
            return false;
        }

        if (iter.hasNext()) {
            return true;
        }

        while (++ptr < configs.length) {
            iter = configs[ptr].iterator(key);

            if (iter.hasNext()) {
                return true;
            }
        }

        iter = null;
        return false;
    }

public Configuration next() {

        if (iter == null) {
            return null;
        }

        if (iter.hasNext()) {
            return iter.next();
        }

        while (++ptr < configs.length) {
            try {
                if (key != null) {
                    iter = configs[ptr].iterator(key);
                } else {
                    iter = configs[ptr].iterator();
                }
            } catch (ConfigurationException e) {
                throw new RuntimeException(e);
            }

            if (iter.hasNext()) {
                return iter.next();
            }
        }

        return iter.next();
    }

    /**
     * This operation is not supported and leads to an exception.
     * 
     * @see java.util.Iterator#remove()
     */
    public void remove() {

        throw new UnsupportedOperationException();
    }

}
