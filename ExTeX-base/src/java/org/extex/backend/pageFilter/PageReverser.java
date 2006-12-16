/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.exception.BackendException;
import org.extex.backend.exception.BackendMissingTargetException;
import org.extex.backend.pageFilter.PagePipe;
import org.extex.typesetter.type.page.Page;


/**
 * This page filter reverses the order of the pages shipped out.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class PageReverser implements PagePipe {

    /**
     * The field <tt>out</tt> contains the output target.
     */
    private PagePipe out = null;

    /**
     * The field <tt>pages</tt> contains the pages.
     */
    private List pages = new ArrayList();

    /**
     * Creates a new object.
     *
     */
    public PageReverser() {

        super();
    }

    /**
     * @see org.extex.backend.pageFilter.PagePipe#close()
     */
    public void close() throws BackendException {

        if (out == null) {
            throw new BackendMissingTargetException();
        }

        for (int i = pages.size() - 1; i >= 0; i--) {
            out.shipout((Page) pages.get(i));
        }
        out.close();
    }

    /**
     * @see org.extex.backend.pageFilter.PagePipe#setOutput(
     *     org.extex.backend.pageFilter.PagePipe)
     */
    public void setOutput(final PagePipe pipe) {

        this.out = pipe;
    }

    /**
     * @see org.extex.backend.pageFilter.PagePipe#setParameter(
     *     java.lang.String, java.lang.String)
     */
    public void setParameter(final String name, final String value) {

    }

    /**
     * @see org.extex.backend.pageFilter.PagePipe#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public void shipout(final Page page) throws DocumentWriterException {

        pages.add(page);
    }

}
