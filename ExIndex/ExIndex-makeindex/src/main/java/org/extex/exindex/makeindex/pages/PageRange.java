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
public class PageRange {

    /**
     * TODO gene: missing JavaDoc.
     */
    public static enum Type {

        /**
         * The field <tt>SINGLE</tt> contains the ...
         */
        SINGLE {

            @Override
            protected void write(Writer writer, String fromPage, String toPage,
                    String[] pageParams) throws IOException {

            }
        },
        /**
         * The field <tt>RANGE</tt> contains the ...
         */
        RANGE,
        /**
         * The field <tt>MULTIPLE</tt> contains the ...
         */
        MULTIPLE {

            @Override
            protected void write(Writer writer, String fromPage, String toPage,
                    String[] pageParams) throws IOException {

                if (!toPage.equals(fromPage)) {
                    writer.write(pageParams[4]);
                    writer.write(toPage);
                }
            }
        },
        /**
         * The field <tt>OPEN</tt> contains the ...
         */
        OPEN,
        /**
         * The field <tt>CLOSE</tt> contains the ...
         */
        CLOSE;

        protected void write(Writer writer, String fromPage, String toPage,
                String[] pageParams) throws IOException {

            if (!toPage.equals(fromPage)) {
                writer.write(pageParams[3]);
                writer.write(toPage);
            }
        }
    }

    /**
     * The field <tt>PAGE_PARAMS</tt> contains the ...
     */
    private static final String[] PAGE_PARAMS = {"\\", "{", "}", "--", ", "};;

    /**
     * The field <tt>from</tt> contains the star page.
     */
    private PageReference from;

    /**
     * The field <tt>to</tt> contains the end page.
     */
    private PageReference to;

    /**
     * The field <tt>type</tt> contains the ...
     */
    private Type type;

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
    public PageRange(PageReference from, String encap, Type type) {

        this.from = from;
        this.to = from;
        this.encap = encap;
        this.type = type;
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
     * Getter for the to.
     * 
     * @return the to
     */
    public PageReference getTo() {

        return to;
    }

    /**
     * Getter for type.
     * 
     * @return the type
     */
    public Type getType() {

        return type;
    }

    /**
     * Checks if is one.
     * 
     * @return true, if is one
     */
    public boolean isOne() {

        return from.getPage().equals(to.getPage());
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

        if (type.equals(Type.RANGE) && other.type.equals(Type.RANGE)) {

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
        } else if (type.equals(Type.RANGE) && other.type.equals(Type.SINGLE)) {
            if (otherFromOrd >= fromOrd && otherFromOrd <= toOrd) {
                return true;
            }
        } else if (type.equals(Type.MULTIPLE) && other.type.equals(Type.SINGLE)) {
            if (otherFromOrd == toOrd + 1) {
                to = other.from;
                type = Type.RANGE;
                return true;
            }
        } else if (type.equals(Type.SINGLE) && other.type.equals(Type.SINGLE)) {
            if (otherFromOrd == fromOrd) {
                return true;
            } else if (otherFromOrd == fromOrd + 1) {
                type = Type.MULTIPLE;
                to = other.from;
                return true;
            }
        }
        return false;
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
     * Setter for type.
     * 
     * @param type the type to set
     */
    public void setType(Type type) {

        this.type = type;
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
        type.write(writer, fromPage, to.getPage(), pageParams);
        if (encap != null) {
            writer.write(pageParams[2]);
        }
    }

}
