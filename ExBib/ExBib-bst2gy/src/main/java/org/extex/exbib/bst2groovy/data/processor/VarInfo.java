/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VarInfo {

    /**
     * The field <tt>readBeforeWrite</tt> contains the ...
     */
    private boolean readBeforeWrite = false;

    /**
     * The field <tt>noWrite</tt> contains the ...
     */
    private int noWrite = 0;

    /**
     * The field <tt>name</tt> contains the name of the variable.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name
     */
    public VarInfo(String name) {

        super();
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
     * TODO gene: missing JavaDoc
     * 
     * @param v
     */
    public void merge(VarInfo v) {

        if (noWrite <= 0) {
            readBeforeWrite |= v.readBeforeWrite;
        }
        noWrite += v.noWrite;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    public void reading() {

        if (noWrite <= 0) {
            readBeforeWrite = true;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return name + " " + noWrite + " " + readBeforeWrite;
    }

    /**
     * Recognize that the variable has been used in writing.
     */
    public void writing() {

        noWrite++;
    }

}
