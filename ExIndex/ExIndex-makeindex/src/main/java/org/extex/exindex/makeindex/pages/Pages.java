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

package org.extex.exindex.makeindex.pages;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.extex.exindex.core.type.page.PageReference;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class Pages {

    /**
     * The field <tt>PAGE_PARAMS</tt> contains the default parameters for
     * displaying a page.
     */
    private static final String[] PAGE_PARAMS = {"\\", "{", "}", "--", ", "};;

    /**
     * The field <tt>from</tt> contains the star page.
     */
    private PageReference from;

    /**
     * The field <tt>encap</tt> contains the encapsulator or <code>null</code>
     * for none.
     */
    private String encap;

    /**
     * Creates a new object.
     * 
     * @param from the start and end page
     * @param encap the encapsulator
     * @param open the open indicator
     * @param close the close indicator
     */
    protected Pages(PageReference from, String encap) {

        this.from = from;
        this.encap = encap;
    }

    /**
     * Getter for the encap.
     * 
     * @return the encap
     */
    public String getEncap() {

        return encap;
    }

    /**
     * Getter for the from.
     * 
     * @return the from
     */
    public PageReference getFrom() {

        return from;
    }

    /**
     * Checks if is one.
     * 
     * @return true, if is one
     */
    public boolean isOne() {

        return true;
    }

    /**
     * Setter for from.
     * 
     * @param from the from to set
     */
    public void setFrom(PageReference from) {

        this.from = from;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringWriter writer = new StringWriter();
        try {
            write(writer, PAGE_PARAMS);
        } catch (IOException e) {
            // ignored on purpose; this could not happen
        }
        return writer.toString();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param writer the writer
     * @param pageParams the page parameters
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(Writer writer, String[] pageParams) throws IOException {

        if (encap != null) {
            writer.write(pageParams[0]);
            writer.write(encap);
            writer.write(pageParams[1]);
        }
        String fromPage = from.getPage();
        writer.write(fromPage);
        writeCore(writer, pageParams, fromPage);
        if (encap != null) {
            writer.write(pageParams[2]);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param writer the target writer
     * @param pageParams the parameters
     * @param fromPage start page
     * 
     * @throws IOException in case of an I/O error
     */
    protected abstract void writeCore(Writer writer, String[] pageParams,
            String fromPage) throws IOException;

}
