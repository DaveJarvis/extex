/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.core;

import java.io.Serializable;

/**
 * The locator is the container for the information about the name of a resource
 * and the current line number.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:5417 $
 */
public class Locator implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field <tt>cause</tt> contains the next in the sequence of chained
     * locators.
     */
    private Locator cause = null;

    /**
     * The field <tt>line</tt> contains the current line of input.
     */
    private String line;

    /**
     * The field <tt>lineNumber</tt> contains the line number.
     */
    private int lineNumber;

    /**
     * The field <tt>linePointer</tt> contains the position in the line the
     * locator refers to.
     */
    private int linePointer;

    /**
     * The field <tt>resource</tt> contains the name of the resource.
     */
    private String resource;

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource; e.g. the file name
     * @param lineNo the line number
     * @param currentLine the current line of input
     * @param currentLinePointer the current position in the line of input
     */
    public Locator(String resource, int lineNo, String currentLine,
            int currentLinePointer) {

        super();
        this.resource = resource;
        this.lineNumber = lineNo;
        this.line = currentLine;
        this.linePointer = currentLinePointer;
    }

    /**
     * Getter for cause.
     * 
     * @return the cause
     */
    public final Locator getCause() {

        return cause;
    }

    /**
     * Getter for the line.
     * 
     * @return the line.
     */
    public String getLine() {

        return line;
    }

    /**
     * Getter for the line number. If the line number is negative then it has
     * the meaning of an undefined value.
     * 
     * @return the line number
     */
    public int getLineNumber() {

        return lineNumber;
    }

    /**
     * Getter for the line pointer. The line pointer is the position within the
     * line at which something happened.
     * 
     * @return the line pointer.
     */
    public int getLinePointer() {

        return linePointer;
    }

    /**
     * Getter for the resource name. The resource name can be unset. In this
     * case <code>null</code> is returned.
     * 
     * @return the resource name or <code>null</code>
     */
    public String getResourceName() {

        return resource;
    }

    /**
     * Setter for cause.
     * 
     * @param cause the cause to set
     */
    public final void setCause(Locator cause) {

        this.cause = cause;
    }

    /**
     * Return a printable representation of this instance including the cause.
     * The result contains the file name and the line number separated by colon.
     * This is the Unix style of presenting a file position. If the file name is
     * null then it is treated as if it where the empty string.
     * 
     * @param causeLinit the limit for the number of causes to be shown. If
     *        negative then the optional, preceding continuation indicator (..)
     *        is suppressed
     * 
     * @return the printable representation
     * 
     * @see #toString()
     */
    public String toString(int causeLinit) {

        StringBuffer sb = new StringBuffer();
        if (causeLinit >= 0) {
            Locator c = cause;
            for (int i = 0; c != null && i < causeLinit; i++) {
                if (c.resource != null) {
                    sb.insert(0, c.resource);
                }
                sb.append(';');
                if (lineNumber >= 0) {
                    sb.append(lineNumber);
                }
                sb.append(';');
                sb.insert(0, "..");
                c = c.cause;
            }
            if (c == null) {
                sb.delete(0, 2);
            }
        }
        if (resource != null) {
            sb.append(resource);
        }
        sb.append(':');
        if (lineNumber >= 0) {
            sb.append(lineNumber);
        }
        sb.append(':');
        return sb.toString();
    }

    /**
     * Return a printable representation of this instance. The result contains
     * the file name and the line number separated by colon. This is the Unix
     * style of presenting a file position. If the file name is null then it is
     * treated as if it where the empty string.
     * 
     * @return the printable representation
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return toString(-1);
    }

}
