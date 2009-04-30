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
import java.io.Writer;

import org.extex.exindex.core.type.page.PageReference;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PageRange {

    /**
     * The field <tt>from</tt> contains the star page.
     */
    private PageReference from;

    /**
     * The field <tt>to</tt> contains the end page.
     */
    private PageReference to;

    /**
     * The field <tt>delimiter</tt> contains the ...
     */
    private String delimiter = null;

    /**
     * The field <tt>open</tt> contains the ...
     */
    private boolean open;

    /**
     * The field <tt>close</tt> contains the ...
     */
    private final boolean close;

    /**
     * The field <tt>encap</tt> contains the ...
     */
    private final String encap;

    /**
     * Creates a new object.
     * 
     * @param from the start and end page
     * @param encap
     * @param open
     * @param close
     */
    public PageRange(PageReference from, String encap, boolean open,
            boolean close) {

        this.from = from;
        this.to = from;
        this.encap = encap;
        this.open = open;
        this.close = close;
    }

    /**
     * Getter for the encap.
     * 
     * @return the encap
     */
    protected String getEncap() {

        return encap;
    }

    /**
     * Getter for the from.
     * 
     * @return the from
     */
    protected PageReference getFrom() {

        return from;
    }

    /**
     * Getter for the to.
     * 
     * @return the to
     */
    protected PageReference getTo() {

        return to;
    }

    /**
     * Getter for the close.
     * 
     * @return the close
     */
    protected boolean isClose() {

        return close;
    }

    /**
     * Getter for the open.
     * 
     * @return the open
     */
    protected boolean isOpen() {

        return open;
    }

    /**
     * Setter for the delimiter.
     * 
     * @param delimiter the delimiter to set
     */
    protected void setDelimiter(String delimiter) {

        this.delimiter = delimiter;
    }

    /**
     * Setter for the to.
     * 
     * @param to the to to set
     */
    protected void setTo(PageReference to) {

        this.to = to;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();
        if (encap != null) {
            buffer.append("\\");
            buffer.append(encap);
            buffer.append("{");
        }
        buffer.append(from.getPage());
        if (to != from) {
            buffer.append("--");
            buffer.append(to.getPage());
        }
        if (encap != null) {
            buffer.append("}");
        }
        return buffer.toString();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param writer the writer
     * @param encapPrefix the encapsulator prefix
     * @param encapInfix the encapsulator infix
     * @param encalSuffix the encapsulator suffix
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(Writer writer, String encapPrefix, String encapInfix,
            String encalSuffix) throws IOException {

        if (encap != null) {
            writer.write(encapPrefix);
            writer.write(encap);
            writer.write(encapInfix);
        }
        writer.write(from.getPage());
        if (to != from) {
            writer.write(delimiter);
            writer.write(to.getPage());
        }
        if (encap != null) {
            writer.write(encalSuffix);
        }
    }

}
