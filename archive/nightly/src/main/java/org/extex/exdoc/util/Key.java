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

import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Key {

    /**
     * The field {@code theClass} contains the ...
     */
    private String theClass;

    /**
     * The field {@code theMethod} contains the ...
     */
    private String theMethod;

    /**
     * The field {@code theName} contains the ...
     */
    private String theName;

    /**
     * The field {@code thePackage} contains the ...
     */
    private String thePackage;

    /**
     * The field {@code theType} contains the ...
     */
    private String theType;

    /**
     * The field {@code stringCache} contains the ...
     */
    private String stringCache = null;

    /**
     * Creates a new object.
     * 
     * @param a the attributes
     */
    public Key(Map<String, String> a) {

        thePackage = a.get("package");
        theClass = a.get("class");
        theMethod = a.get("method");
        theName = a.get("name");
        theType = a.get("type");
    }

@Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Key)) {
            return false;
        }
        Key pi = (Key) obj;
        return eq(theName, pi.theName)
                && eq(thePackage, pi.thePackage)
                && eq(theClass, pi.theClass)
                && eq(theMethod, pi.theMethod);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the first string
     * @param t the second string
     * 
     * @return {@code true} iff the arguments disagree
     */
    private boolean eq(String s, String t) {

        return (s == null ? t == null : s.equals(t));
    }

@Override
    public int hashCode() {

        return theName.hashCode();
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
    @Override
    public String toString() {

        if (stringCache != null) {
            return stringCache;
        }
        StringBuilder sb = new StringBuilder();
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

        StringBuilder sb = new StringBuilder();
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
    protected String tuneKey(String p) {

        return p.replaceFirst("^org.extex.", "");
    }

}
