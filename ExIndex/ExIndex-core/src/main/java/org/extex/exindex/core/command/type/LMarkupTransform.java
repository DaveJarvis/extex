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

package org.extex.exindex.core.command.type;

import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.type.transform.Transform;

/**
 * This class provides a map of arrays with a default value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupTransform extends LMarkup {

    /**
     * The field <tt>transformMap</tt> contains the transforms.
     */
    private Map<String, Transform> transformMap =
            new HashMap<String, Transform>();

    /**
     * Creates a new object.
     * 
     * @param displayName the name for debugging
     */
    public LMarkupTransform(String displayName) {

        super(displayName);
    }

    /**
     * Getter for transform.
     * 
     * @param clazz the class
     * 
     * @return the transform
     */
    public Transform getTransform(String clazz) {

        Transform t = transformMap.get(clazz);
        return (t == null && clazz != null ? transformMap.get(null) : t);
    }

    /**
     * Setter for transform.
     * 
     * @param clazz the class
     * @param transform the transform to set
     */
    public void setTransform(String clazz, Transform transform) {

        this.transformMap.put(clazz, transform);
    }

}
