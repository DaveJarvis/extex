/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.linker;

import java.io.IOException;

import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This code is added to the linking container.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LinkingCode {

    /**
     * The field <tt>text</tt> contains the text.
     */
    private String[] text;

    /**
     * Creates a new object.
     * 
     * @param text the text
     */
    public LinkingCode(String... text) {

        super();
        this.text = text;
    }

    /**
     * Print the linking code.
     * 
     * @param writer the writer
     * @param prefix the prefix
     * @param in the indentation
     * 
     * @throws IOException in case of an I/O error
     */
    public void print(CodeWriter writer, String prefix, String in)
            throws IOException {

        writer.write(prefix);
        writer.write(text);
    }

}
