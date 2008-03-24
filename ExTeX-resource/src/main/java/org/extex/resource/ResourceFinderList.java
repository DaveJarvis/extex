/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class provides a means to combine several resource finders to be queried
 * as one.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ResourceFinderList implements ResourceFinder, RecursiveFinder {

    /**
     * The field <tt>list</tt> the internal list of file finders which are
     * elements in this container.
     */
    private List<ResourceFinder> list = new ArrayList<ResourceFinder>();

    /**
     * The field <tt>parent</tt> contains the parent resource finder.
     */
    private ResourceFinder parent = null;

    /**
     * Creates a new object. Initially the list is empty.
     */
    public ResourceFinderList() {

        super();
        this.parent = this;
    }

    /**
     * Append an additional file finder to list of file finders contained.
     * 
     * @param finder the file finder to add
     */
    public void add(ResourceFinder finder) {

        list.add(finder);

        if (finder instanceof RecursiveFinder) {
            ((RecursiveFinder) finder).setParent(parent);
        }
    }

    /**
     * Enable or disable the tracing. The argument indicates whether tracing
     * should be enabled or disabled. The resource finder can decide on its own
     * how to perform tracing. The preferred way is to write tracing records to
     * a logger.
     * 
     * @param flag indicator whether tracing should be turned on or off.
     * 
     * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
     */
    public void enableTracing(boolean flag) {

        for (ResourceFinder finder : list) {
            finder.enableTracing(flag);
        }
    }

    /**
     * Find a resource which can be used for reading. If the search fails then
     * <code>null</code> is returned.
     * 
     * @param name the base name of the resource
     * @param type the type, i.e. the extension
     * 
     * @return the file or <code>null</code> if none could be found
     * 
     * @throws ConfigurationException in case of an exception
     * 
     * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
     *      java.lang.String)
     */
    public InputStream findResource(String name, String type)
            throws ConfigurationException {

        for (ResourceFinder finder : list) {
            InputStream stream = finder.findResource(name, type);
            if (stream != null) {
                return stream;
            }
        }

        return null;
    }

    /**
     * Setter for the parent resource finder.
     * 
     * @param theParent the parent finder for recursive invocation
     * 
     * @see org.extex.resource.RecursiveFinder#setParent(
     *      org.extex.resource.ResourceFinder)
     */
    public void setParent(ResourceFinder theParent) {

        for (ResourceFinder finder : list) {
            if (finder instanceof RecursiveFinder) {
                ((RecursiveFinder) finder).setParent(theParent);
            }
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("(resource");
        for (ResourceFinder finder : list) {
            sb.append(' ');
            sb.append(finder.toString());
        }
        sb.append(')');
        return sb.toString();
    }

}
