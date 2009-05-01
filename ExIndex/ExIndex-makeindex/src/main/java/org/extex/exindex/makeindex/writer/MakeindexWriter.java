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
import org.extex.exindex.makeindex.pages.PageRange;

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
    private Writer writer;

    /**
     * Creates a new object.
     * 
     * @param writer the writer
     * @param params the parameters
     * 
     * @throws IOException in case of an I/O error
     */
    public MakeindexWriter(Writer writer, Parameters params) throws IOException {

        this.writer = writer;
        this.params = params;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.makeindex.writer.IndexWriter#write(java.util.List,
     *      java.util.logging.Logger, java.lang.String)
     */
    public int[] write(List<Entry> entries, Logger logger, String startPage)
            throws IOException {

        final String item0 = params.getString("index:item_0");
        final String item1 = params.getString("index:item_1");
        final String indentSpace = params.getString("markup:indent-space");
        final String delim0 = params.getString("index:delim_0");
        final String delim1 = params.getString("index:delim_1");
        final String encapPrefix = params.getString("index:encap-prefix");
        final String encapInfix = params.getString("index:encap-infix");
        final String encalSuffix = params.getString("index:encap-suffix");
        final long headingFlag = params.getNumber("index:headings-flag");
        int[] count = {0, 0};
        char currentHeading = '\0';

        writer.write(params.getString("markup:index-open"));

        if (startPage != null) {
            writer.write(params.getString("markup:setpage-prefix"));
            writer.write(startPage);
            writer.write(params.getString("markup:setpage-suffix"));
        }

        int level = 0;
        String[] lastKey = {};

        for (Entry entry : entries) {
            if (headingFlag != 0 && entry.getHeading() != currentHeading) {
                currentHeading = writeHeading(headingFlag, entry);
            }

            String[] display = entry.getValue();
            for (int i = 0;; i++) {
                if (i >= lastKey.length) {
                    for (; i < display.length; i++) {
                        writer.write(item0); // TODO
                        writer.write(display[i]);
                    }
                    break;
                } else if (i >= display.length) {
                    throw new RuntimeException("this should not happen");
                } else if (!lastKey[i].equals(display[i])) {

                    writer.write(item0); // TODO
                    writer.write(display[i]);
                    // throw new RuntimeException("unimplemented");
                    break;
                }
            }

            lastKey = display;
            writePages(entry, delim0, delim1, encapPrefix, encapInfix,
                encalSuffix);
        }

        writer.write(params.getString("markup:index-close"));
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
     * @param delim0 the delim0
     * @param delim1 the delim1
     * @param encapPrefix the encap prefix
     * @param encapInfix the encap infix
     * @param encapSuffix the encap suffix
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writePages(Entry entry, final String delim0,
            final String delim1, final String encapPrefix,
            final String encapInfix, final String encapSuffix)
            throws IOException {

        List<PageRange> pages = entry.getPages();
        boolean first = true;

        for (PageRange page : pages) {
            if (first) {
                first = false;
                writer.write(delim0);
            } else {
                writer.write(delim1);
            }
            page.write(writer, encapPrefix, encapInfix, encapSuffix);
        }
    }

}
