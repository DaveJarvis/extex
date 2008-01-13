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
 * @version $Revision$
 */
public class MarkupTransform extends Markup {

    /**
     * This is a container for parameters.
     */
    private class Param {

        /**
         * The field <tt>open</tt> contains the open markup.
         */
        private String open;

        /**
         * The field <tt>close</tt> contains the close markup.
         */
        private String close;

        /**
         * The field <tt>openHead</tt> contains the open head markup.
         */
        private String openHead;

        /**
         * The field <tt>closeHead</tt> contains the close head markup.
         */
        private String closeHead;

        /**
         * The field <tt>force</tt> contains the force indicator.
         */
        private boolean force;

        /**
         * Creates a new object.
         * 
         * @param open the open string
         * @param close the close string
         * @param openHead the open head string
         * @param closeHead the close head string
         * @param force the force indicator
         */
        public Param(String open, String close, String openHead,
                String closeHead, boolean force) {

            this.open = open;
            this.close = close;
            this.openHead = openHead;
            this.closeHead = closeHead;
            this.force = force;
        }

        /**
         * Getter for close.
         * 
         * @return the close
         */
        public String getClose() {

            return close;
        }

        /**
         * Getter for closeHead.
         * 
         * @return the closeHead
         */
        public String getCloseHead() {

            return closeHead;
        }

        /**
         * Getter for open.
         * 
         * @return the open
         */
        public String getOpen() {

            return open;
        }

        /**
         * Getter for openHead.
         * 
         * @return the openHead
         */
        public String getOpenHead() {

            return openHead;
        }

        /**
         * Getter for force.
         * 
         * @return the force
         */
        public boolean isForce() {

            return force;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();
            sb.append("openHead=");
            sb.append(openHead);
            sb.append(" closeHead=");
            sb.append(closeHead);
            sb.append(" open=");
            sb.append(open);
            sb.append(" close=");
            sb.append(close);
            sb.append(force ? " force" : "not force");
            return sb.toString();
        }

    };

    /**
     * The field <tt>transformMap</tt> contains the transforms.
     */
    private Map<String, Transform> transformMap =
            new HashMap<String, Transform>();

    /**
     * The field <tt>map</tt> contains the mapping from name to parameters.
     */
    private Map<String, Param> map = new HashMap<String, Param>();

    /**
     * Creates a new object.
     * 
     * @param displayName the name for debugging
     */
    public MarkupTransform(String displayName) {

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

        map.put(clazz, new Param(open, close, openHead, closeHead, force));
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

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(map.toString());
        return sb.toString();
    }

}
