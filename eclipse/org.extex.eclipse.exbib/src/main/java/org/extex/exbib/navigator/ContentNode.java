/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.navigator;

import org.eclipse.core.runtime.IAdaptable;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
class ContentNode implements IAdaptable {

    /**
     * The field <tt>name</tt> contains the ...
     */
    private String name;

    /**
     * The field <tt>parent</tt> contains the ...
     */
    private ContentDirectory parent;

    /**
     * Creates a new object.
     * 
     * @param name
     */
    public ContentNode(String name, ContentDirectory parent) {

        this.name = name;
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(Class key) {

        return null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    public String getName() {

        return name;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    public ContentDirectory getParent() {

        return parent;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param parent
     */
    public void setParent(ContentDirectory parent) {

        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return name;
    }
}
