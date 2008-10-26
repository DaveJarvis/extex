/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import org.extex.exbib.bst2groovy.data.GCode;

/**
 * This class is a container for any information needed for linking. This
 * includes additional methods to be defined and a list of imports.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LinkContainer {

    /**
     * The field <tt>linked</tt> contains the list of methods to be linked in.
     */
    private Set<GCode> linked = new HashSet<GCode>();

    /**
     * The field <tt>imports</tt> contains the set of imports.
     */
    private Set<String> imports = new HashSet<String>();

    /**
     * Add code to be linked in.
     * 
     * @param e the code
     */
    public void add(GCode e) {

        linked.add(e);
    }

    /**
     * Add an import statement.
     * 
     * @param s the import to add
     */
    public void addImports(String s) {

        imports.add(s);
    }

    /**
     * Write all import statements.
     * 
     * @param writer the writer
     * 
     * @throws IOException in case of an I/O error
     */
    public void writeImports(Writer writer) throws IOException {

        for (String s : imports) {
            writer.write("import ");
            writer.write(s);
            writer.write("\n");
        }
        writer.write("\n");
    }

    /**
     * Write the linked code
     * 
     * @param writer the writer
     * 
     * @throws IOException in case of an I/O error
     */
    public void writeMethods(Writer writer) throws IOException {

        for (GCode fct : linked) {
            fct.print(writer, "\n" + Bst2Groovy.INDENT);
        }
    }

}
