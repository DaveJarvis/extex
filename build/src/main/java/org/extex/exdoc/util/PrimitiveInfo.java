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

package org.extex.exdoc.util;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PrimitiveInfo {

    /**
     * The field <tt>implementor</tt> contains the ...
     */
    private String implementer;

    /**
     * The field <tt>name</tt> contains the ...
     */
    private String name;

    /**
     * The field <tt>namespace</tt> contains the ...
     */
    private String namespace;

    /**
     * Creates a new object.
     * 
     * @param name the name of the primitive
     * @param namespace the optional name space; this can be <code>null</code>
     * @param implementer the implementing class
     */
    public PrimitiveInfo(String name, String namespace, String implementer) {

        this.implementer = implementer;
        this.name = name;
        this.namespace = namespace;
    }

    /**
     * Getter for implementer.
     * 
     * @return the implementer
     */
    public String getImplementer() {

        return implementer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof PrimitiveInfo)) {
            return false;
        }
        PrimitiveInfo pi = (PrimitiveInfo) obj;
        return eq(name, pi.name)
                && eq(implementer, pi.implementer)
                && eq(namespace, pi.namespace);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the first string
     * @param t the second string
     * 
     * @return <code>true</code> iff the arguments disagree
     */
    private boolean eq(String s, String t) {

        return (s == null ? t == null : s.equals(t));
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return name.hashCode();
    }

    /**
     * Getter for name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Getter for name space.
     * 
     * @return the name space
     */
    public String getNamespace() {

        return namespace;
    }

}
