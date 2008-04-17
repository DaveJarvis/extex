/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.auxio;

/**
 * This observer is triggered when a resource has been opened.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface ResourceObserver {

    /**
     * Invoke this method if a resource has been closed.
     * 
     * @param resource the resource requested
     * @param type the type of the resource
     * @param filename the resource found
     */
    void observeClose(String resource, String type, String filename);

    /**
     * Invoke this method if a resource has been opened.
     * 
     * @param resource the resource requested
     * @param type the type of the resource
     * @param filename the resource found
     */
    void observeOpen(String resource, String type, String filename);

}
