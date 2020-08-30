/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type.markup;

import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.type.transform.Transform;

/**
 * This class provides a map of arrays with a default value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MarkupTransform extends Markup {

    /**
     * The field {@code transformMap} contains the transforms.
     */
    private final Map<String, Transform> transformMap =
            new HashMap<String, Transform>();

    /**
     * The field {@code map} contains the mapping from name to Booleans.
     */
    private final Map<String, Boolean> forceMap = new HashMap<String, Boolean>();

    /**
     * Creates a new object.
     * 
     * @param displayName the name for debugging
     */
    public MarkupTransform(String displayName) {

        super(displayName);
    }

    /**
     * Getter for the force value.
     * 
     * @param clazz the class name
     * 
     * @return the force indicator
     */
    public Boolean getForce(String clazz) {

        return forceMap.get(clazz);
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
     * Set some major attributes.
     * 
     * @param clazz the class
     * @param open the open string
     * @param close the close string
     * @param openHead the open head string
     * @param closeHead the close head string
     * @param force the force indicator
     */
    public void set(String clazz, String open, String close, String openHead,
            String closeHead, boolean force) {

        set(clazz, open, close, "", openHead, closeHead);
        forceMap.put(clazz, Boolean.valueOf(force));
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

@Override
    public String toString() {

        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(forceMap.toString());
        return sb.toString();
    }

}
