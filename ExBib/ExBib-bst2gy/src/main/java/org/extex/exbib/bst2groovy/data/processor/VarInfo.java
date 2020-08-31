/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.data.processor;

/**
 * This class contains informations for variables for optimizations.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class VarInfo {

    /**
     * The field {@code readBeforeWrite} contains the indicator that the
     * variable is read before it has a proper value.
     */
    private boolean readBeforeWrite = false;

    /**
     * The field {@code noWrite} contains the number of write operations.
     */
    private int noWrite = 0;

    /**
     * The field {@code name} contains the name of the variable.
     */
    private final String name;

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public VarInfo(String name) {

        this.name = name;
    }

    /**
     * Getter for the name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Getter for the noWrite.
     * 
     * @return the noWrite
     */
    public int getNoWrite() {

        return noWrite;
    }

    /**
     * Getter for the noRead.
     * 
     * @return the noRead
     */
    public boolean getReadBeforeWrite() {

        return readBeforeWrite;
    }

    /**
     * Merge in another variable info.
     * 
     * @param v the other variable
     */
    public void merge(VarInfo v) {

        if (noWrite <= 0) {
            readBeforeWrite |= v.readBeforeWrite;
        }
        noWrite += v.noWrite;
    }

    /**
     * Recognize that the variable is read.
     */
    public void reading() {

        if (noWrite <= 0) {
            readBeforeWrite = true;
        }
    }

@Override
    public String toString() {

        return name + " " + noWrite + " " + readBeforeWrite;
    }

    /**
     * Recognize that the variable has been used in writing.
     * 
     * @return {@code true} iff this is the first writing in this context
     */
    public boolean writing() {

        return noWrite++ == 0;
    }

}
