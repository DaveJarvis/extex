/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.engine;

import org.extex.framework.RegistrarException;
import org.extex.framework.RegistrarObserver;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

/**
 * This class is used to inject a resource finder when a class is loaded
 * from a format which needs it.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ResourceFinderInjector implements RegistrarObserver {

    /**
     * The field <tt>finder</tt> contains the resource finder to inject.
     */
    private ResourceFinder finder;

    /**
     * Creates a new object.
     *
     * @param finder the resource finder to inject
     */
    public ResourceFinderInjector(ResourceFinder finder) {

        this.finder = finder;
    }

    /**
     * Reconnect an object.
     * It should return the object which should actually be used. This is
     * normally the object which is passed in as argument. Nevertheless the
     * as a side effect the object can be attached to an internal list in a
     * factory or augmented with additional information by invoking some of
     * its methods.
     *
     * @param object the object to reconnect
     *
     * @return the object to be actually used
     *
     * @throws RegistrarException in case of an error during configuration
     *
     * @see org.extex.framework.RegistrarObserver#reconnect(java.lang.Object)
     */
    public Object reconnect(Object object) throws RegistrarException {

        ((ResourceAware) object).setResourceFinder(finder);
        return object;
    }
}