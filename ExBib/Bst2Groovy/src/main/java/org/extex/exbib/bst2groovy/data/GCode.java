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

package org.extex.exbib.bst2groovy.data;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.data.types.GType;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface GCode {

    /**
     * Determine the type of the expression.
     * 
     * @return the type
     */
    GType getType();

    /**
     * Print the expression to a writer.
     * 
     * @param writer the target writer
     * @param prefix the prefix to be added at the begin of a new line
     * 
     * @throws IOException in case of an I/O error
     */
    void print(Writer writer, String prefix) throws IOException;

}
