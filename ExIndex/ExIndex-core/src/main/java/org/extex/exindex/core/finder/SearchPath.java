/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.finder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;

/**
 * This resource finder imitating the search strategy of Xindy.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SearchPath implements ResourceFinder {

    /**
     * The field <tt>fallback</tt> contains the fallback resource finder.
     */
    private ResourceFinder fallback;

    /**
     * The field <tt>dirs</tt> contains the list of directories.
     */
    private String[] dirs = new String[]{""};

    /**
     * The field <tt>tracing</tt> contains the indicator for tracing.
     */
    private boolean tracing;

    /**
     * Creates a new object.
     * 
     * @param fallback the fallback resource finder; it can be <code>null</code>
     *        for none
     */
    public SearchPath(ResourceFinder fallback) {

        super();
        this.fallback = fallback;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
     */
    public void enableTracing(boolean flag) {

        this.tracing = flag;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
     *      java.lang.String)
     */
    public InputStream findResource(String name, String type)
            throws ConfigurationException {

        for (String d : dirs) {
            if (d == null) {
                if (fallback != null) {
                    InputStream stream = fallback.findResource(name, type);
                    if (stream != null) {
                        return stream;
                    }
                }
            } else {
                File f = new File(d, name);
                if (f.canRead()) {
                    InputStream stream;
                    try {
                        stream = new FileInputStream(f);
                        if (stream != null) {
                            return stream;
                        }
                    } catch (FileNotFoundException e) {
                        // keep on trying
                    }
                }
            }
        }
        return null;
    }

    /**
     * Set the search path. The argument is a string of colon separated
     * directories to be prepended before the file name to be sought. These
     * directories can either be absolute or relative. If the last character is
     * a colon then the fallback resources finder is used at the end.
     * 
     * @param path the path
     */
    public void set(String path) {

        this.dirs = path.split(":");
        if (path.endsWith(":")) {
            this.dirs[this.dirs.length - 1] = null;
        }
    }

    /**
     * Set the search path. The argument is a list of directories to be
     * prepended before the file name to be sought. These directories can either
     * be absolute or relative. If a directory is <code>null</code> then the
     * fallback resources finder is used instead.
     * 
     * @param path the path
     */
    public void set(String[] path) {

        dirs = path.clone();
    }

}
