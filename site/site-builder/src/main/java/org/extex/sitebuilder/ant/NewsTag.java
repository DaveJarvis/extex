/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.sitebuilder.ant;

import java.io.File;

import org.extex.sitebuilder.core.NewsBuilder;

/**
 * TODO gne: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NewsTag {

    /**
     * The field <tt>builder</tt> contains the ...
     */
    private NewsBuilder builder = new NewsBuilder();

    /**
     * TODO gne: missing JavaDoc
     * 
     * @throws Exception
     */
    public void run() throws Exception {

        builder.generate();
    }

    /**
     * Setter for the max value.
     * 
     * @param max the max value to set
     */
    public void setMax(int max) {

        builder.setMax(max);
    }

    /**
     * Setter for the output file.
     * 
     * @param output the output file to set
     */
    public void setOutput(File output) {

        builder.setOutput(output);
    }

    /**
     * TODO gne: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void validate() throws Exception {

    }

}
