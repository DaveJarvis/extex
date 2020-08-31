/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.pageFilter;

import java.util.ArrayList;
import java.util.List;

import org.extex.backend.exception.BackendException;
import org.extex.backend.exception.BackendMissingTargetException;
import org.extex.typesetter.type.page.Page;

/**
 * This page filter reverses the order of the pages shipped out.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PageReverser implements PagePipe {

    /**
     * The field {@code out} contains the output target.
     */
    private PagePipe out = null;

    /**
     * The field {@code pages} contains the pages.
     */
    private final List<Page> pages = new ArrayList<Page>();

    /**
     * Creates a new object.
     *
     */
    public PageReverser() {

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.backend.pageFilter.PagePipe#close()
     */
    public void close() throws BackendException {

        if (out == null) {
            throw new BackendMissingTargetException();
        }

        for (int i = pages.size() - 1; i >= 0; i--) {
            out.shipout(pages.get(i));
        }
        out.close();
    }

    /**
     * Setter for the output node pipe.
     *
     * @param pipe the output node pipe
     *
     * @see org.extex.backend.pageFilter.PagePipe#setOutput(
     *     org.extex.backend.pageFilter.PagePipe)
     */
    public void setOutput(PagePipe pipe) {

        this.out = pipe;
    }

    /**
     * Setter for a named parameter.
     * Parameters are a general mechanism to influence the behavior of the
     * document writer. Any parameter not known by the document writer has to
     * be ignored.
     *
     * @param name the name of the parameter
     * @param value the value of the parameter
     *
     * @see org.extex.backend.pageFilter.PagePipe#setParameter(
     *     java.lang.String, java.lang.String)
     */
    public void setParameter(String name, String value) {

        //not needed
    }

    /**
     * This is the entry point for the document writer. Here it receives a
     * complete node list to be sent to the output writer. It can be assumed
     * that all values for width, height, and depth of the node lists are
     * properly filled. Thus all information should be present to place the
     * ink on the paper.
     *
     * @param page the page to send
     *
     * @see org.extex.backend.pageFilter.PagePipe#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public void shipout(Page page) {

        pages.add(page);
    }

}
