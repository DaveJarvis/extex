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

package org.extex.ocpware.compiler.type;

/**
 * This class represents a table of character values as two byte numbers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class Table {

    /**
     * The field <tt>contents</tt> contains the contents of the table.
     */
    private int[] contents;

    /**
     * The field <tt>name</tt> contains the name of the table.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name the name of the table
     * @param contents the items
     */
    public Table(String name, int[] contents) {

        super();
        this.name = name;
        this.contents = contents;
    }

    /**
     * Getter for contents.
     * 
     * @return the contents
     */
    public int[] getContents() {

        return contents;
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
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("  ");
        sb.append(name);
        sb.append("[@\"");
        sb.append(Integer.toHexString(contents.length));
        sb.append("] = {");

        boolean sep = false;
        int i = 0;

        for (int x : contents) {
            if (sep) {
                sb.append(",");
            } else {
                sep = true;
            }
            if (i == 0) {
                sb.append("\n    ");
            }
            i = (i + 1) % 8;
            sb.append(" @\"");
            sb.append(Integer.toHexString(x));
        }
        sb.append("};\n");

        return sb.toString();
    }

}
