/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.makeindex.Entry;
import org.extex.exindex.makeindex.Parameters;
import org.extex.exindex.makeindex.pages.PageProcessor;
import org.extex.exindex.makeindex.pages.Pages;

/**
 * This class is an index writer for the makeindex emulator.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6666 $
 */
public class MakeindexWriter implements IndexWriter {

    /**
     * The field <tt>params</tt> contains the parameters.
     */
    private Parameters params;

    /**
     * The field <tt>writer</tt> contains the writer.
     */
    private LineBreakingWriter writer;

    /**
     * Creates a new object.
     * 
     * @param writer the writer
     * @param params the parameters
     * 
     * @throws IOException in case of an I/O error
     */
    public MakeindexWriter(Writer writer, Parameters params) throws IOException {

        this.writer = new LineBreakingWriter(writer, //
            (int) params.getNumber("markup:line-max"), //
            params.getString("markup:indent-space"), //
            (int) params.getNumber("markup:indent_length"));
        this.params = params;
    }

    /**
     * Find the first array index in which two arrays differ.
     * 
     * @param a the first array
     * @param b the second array
     * 
     * @return the first non equal array position
     */
    private int matchPrefix(String[] a, String[] b) {

        int len = a.length > b.length ? b.length : a.length;
        int i;
        for (i = 0; i < len; i++) {
            if (!a[i].equals(b[i])) {
                return i;
            }
        }
        return i;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.makeindex.writer.IndexWriter#write(java.util.List,
     *      java.util.logging.Logger, java.lang.String,
     *      org.extex.exindex.makeindex.pages.PageProcessor)
     */
    public int[] write(List<Entry> entries, Logger logger, String startPage,
            PageProcessor pageProcessor) throws IOException {

        // String[] item = {params.getString("index:item_0"), //
        // params.getString("index:item_1"), //
        // params.getString("index:item_2")};
        String[] itemX = {params.getString("index:item_0"), //
                params.getString("index:item_x1"), //
                params.getString("index:item_x2")};
        String[] delim = {params.getString("index:delim_0"), //
                params.getString("index:delim_1"), //
                params.getString("index:delim_2")};
        String delimN = params.getString("markup:locref-list-sep");
        String[] pageParams = {params.getString("index:encap-prefix"), //
                params.getString("index:encap-infix"), //
                params.getString("index:encap-suffix"), //
                params.getString("markup:range"), //
                params.getString("markup:locref-list-sep")};
        long headingFlag = params.getNumber("index:headings-flag");
        int[] count = {0, 0};
        char currentHeading = '\0';

        writer.write(params.getString("markup:index-open"));

        if (startPage != null) {
            writer.write(params.getString("markup:setpage-prefix"));
            writer.write(startPage);
            writer.write(params.getString("markup:setpage-suffix"));
        }

        String[] lastKey = {};

        for (Entry entry : entries) {
            if (headingFlag != 0 && entry.getHeading() != currentHeading) {
                currentHeading = writeHeading(headingFlag, entry);
            }
            String[] display = entry.getValue();
            // String[] key = entry.getKey();
            // boolean b = true;
            int level = matchPrefix(lastKey, display);
            for (int i = level; i < display.length; i++) {

                writer.write(itemX[i < itemX.length ? i : itemX.length - 1]); // TODO
                writer.write(display[i]);
            }
            // for (int i = 0;; i++) {
            // if (i >= lastKey.length) {
            // String[] it = (b ? itemX : item);
            // for (; i < display.length; i++) {
            // writer.write(it[i]);
            // write(display[i]);
            // }
            // level = i - 1;
            // break;
            // } else if (i >= key.length) {
            // level = i - 1;
            // break;
            // } else if (!lastKey[i].equals(display[i])) {
            // level = i;
            //
            // writer.write(item[0]); // TODO
            // write(display[i]);
            // // throw new RuntimeException("unimplemented");
            // break;
            // } else {
            // b = false;
            // }
            // }

            if (level > delim.length) {
                level = delim.length;
            }
            lastKey = display;
            writePages(entry, delim[level], delimN, pageParams);
        }

        writer.write(params.getString("markup:index-close"));
        writer.flush();
        return count;
    }

    /**
     * Write the heading for the new heading character.
     * 
     * @param headingFlag the heading flag
     * @param e the entry
     * 
     * @return the new heading character
     * 
     * @throws IOException in case of an I/O error
     */
    private char writeHeading(final long headingFlag, Entry e)
            throws IOException {

        char heading = e.getHeading();
        writer.write(params.getString("markup:heading-prefix"));
        if (headingFlag > 0) {
            if (heading == Entry.HEADING_SYMBOL) {
                writer.write(params.getString("markup:symhead-positive"));
            } else if (heading == Entry.HEADING_NUMBER) {
                writer.write(params.getString("markup:numhead-positive"));
            } else {
                writer.write(Character.toUpperCase(heading));
            }
        } else if (heading == Entry.HEADING_SYMBOL) {
            writer.write(params.getString("markup:symhead-negative"));
        } else if (heading == Entry.HEADING_NUMBER) {
            writer.write(params.getString("markup:numhead-negative"));
        } else {
            writer.write(Character.toLowerCase(heading));
        }
        writer.write(params.getString("markup:heading-suffix"));
        return heading;
    }

    /**
     * Write pages.
     * 
     * @param entry the entry
     * @param delim the first delimiter
     * @param delimNext the next delimiter
     * @param pageParams the page parameters
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writePages(Entry entry, String delim, String delimNext,
            String[] pageParams) throws IOException {

        boolean first = true;

        for (Pages range : entry.getPages()) {
            if (first) {
                first = false;
                writer.write(delim);
            } else {
                writer.write(delimNext);
            }
            range.write(writer, pageParams);
        }
    }

}
