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
     * The field <tt>delimiter</tt> contains the delimiter.
     */
    private String delimiter = null;

    /**
     * The field <tt>open</tt> contains the indicator that this is an open
     * range.
     */
    private boolean open;

    /**
     * The field <tt>close</tt> contains the indicator that this is a close
     * range.
     */
    private final boolean close;

    /**
     * The field <tt>encap</tt> contains the encapsulator or <code>null</code>
     * for none.
     */
    private final String encap;

    /**
     * Creates a new object.
     * 
     * @param from the start and end page
     * @param encap the encapsulator
     * @param open the open indicator
     * @param close the close indicator
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
     * Try to join with another page range.
     * 
     * @param other the other page range
     * 
     * @return <code>true</code> iff the joining succeeded
     */
    public boolean join(PageRange other) {

        if (encap == null) {
            if (other.encap != null) {
                return false;
            }
        } else if (!encap.equals(other.encap)) {
            return false;
        }
        if (other.from.getClass() != from.getClass()) {
            return false;
        }
        int otherFromOrd = other.from.getOrd();
        int otherToOrd = other.to.getOrd();
        int fromOrd = from.getOrd();
        int toOrd = to.getOrd();
        if (otherFromOrd < 0 || otherToOrd < 0 || fromOrd < 0 || toOrd <= 0) {
            // TODO join identical pages
            return false;
        }
        if (otherFromOrd >= fromOrd) {
            if (otherFromOrd <= toOrd) {
                to = other.to;
                return true;
            } else if (otherToOrd <= toOrd) {
                return true;
            }
        }
        if (otherFromOrd <= fromOrd) {
            if (otherToOrd >= toOrd) {
                from = other.from;
                to = other.to;
                return true;
            } else if (otherToOrd >= fromOrd) {
                from = other.from;
                return true;
            }
        }

        return false;
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
        String fromPage = from.getPage();
        String toPage = to.getPage();
        writer.write(fromPage);
        if (!toPage.equals(fromPage)) {
            if (delimiter != null) {
                writer.write(delimiter);
            }
            writer.write(toPage);
        }
        if (encap != null) {
            writer.write(encalSuffix);
        }
    }

}
