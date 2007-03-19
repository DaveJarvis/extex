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

package org.extex.exdoc.util;

import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Key {

    /**
     * The field <tt>theClass</tt> contains the ...
     */
    private String theClass;

    /**
     * The field <tt>theMethod</tt> contains the ...
     */
    private String theMethod;

    /**
     * The field <tt>theName</tt> contains the ...
     */
    private String theName;

    /**
     * The field <tt>thePackage</tt> contains the ...
     */
    private String thePackage;

    /**
     * The field <tt>theType</tt> contains the ...
     */
    private String theType;

    /**
     * The field <tt>stringCache</tt> contains the ...
     */
    private String stringCache = null;

    /**
     * Creates a new object.
     *
     * @param a the attributes
     */
    public Key(final Map a) {

        thePackage = (String) a.get("package");
        theClass = (String) a.get("class");
        theMethod = (String) a.get("method");
        theName = (String) a.get("name");
        theType = (String) a.get("type");
    }

    /**
     * Getter for theClass.
     *
     * @return the theClass
     */
    public String getTheClass() {

        return theClass;
    }

    /**
     * Getter for theMethod.
     *
     * @return the theMethod
     */
    public String getTheMethod() {

        return theMethod;
    }

    /**
     * Getter for theName.
     *
     * @return the theName
     */
    public String getTheName() {

        return theName;
    }

    /**
     * Getter for thePackage.
     *
     * @return the thePackage
     */
    public String getThePackage() {

        return thePackage;
    }

    /**
     * Getter for theType.
     *
     * @return the theType
     */
    public String getTheType() {

        return theType;
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @return ...
     */
    public String toString() {

        if (stringCache != null) {
            return stringCache;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(tuneKey(thePackage));

        if (theClass != null) {
            sb.append('.');
            sb.append(theClass);
        }
        if (theMethod != null) {
            sb.append('_');
            sb.append(theMethod);
        }

        sb.insert(0, '_');
        sb.insert(0, theName.replaceAll("\\W", "_"));
        sb.insert(0, '_');
        if (theType != null && !"".equals(theType)
                && !"register".equals(theType) && !"primitive".equals(theType)) {
            sb.insert(0, theType);
        }
        stringCache = sb.toString();
        return stringCache;
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @return ...
     */
    public String getLocation() {

        StringBuffer sb = new StringBuffer();
        sb.append(thePackage);

        if (theClass != null) {
            sb.append('.');
            sb.append(theClass);
        }
        if (theMethod != null) {
            sb.append('_');
            sb.append(theMethod);
        }
        return sb.toString();
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param p ...
     *
     * @return ...
     */
    protected String tuneKey(final String p) {

        return p.replaceFirst("^org.extex.", "");
    }

}
