/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.Parameters;
import org.extex.exindex.core.type.Entry;
import org.extex.exindex.core.type.PageReference;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
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

        super();
        this.writer = writer;
        this.params = params;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.writer.IndexWriter#write(
     *      org.extex.exindex.core.type.Entry[], java.util.logging.Logger,
     *      String)
     */
    public int[] write(Entry[] entries, Logger logger, String startPage)
            throws IOException {

        final String item1 = params.getString("item_1");
        final int headingFlag = params.getNumber("heading_flag");
        int[] count = new int[2];
        char currentHeading = '\0';

        if (startPage != null) {
            writer.write(params.getString("setpage_prefix"));
            writer.write(startPage);
            writer.write(params.getString("setpage_suffix"));
        }

        writer.write(params.getString("preamble"));

        for (Entry e : entries) {
            if (headingFlag != 0 && e.getHeading() != currentHeading) {
                currentHeading = e.getHeading();
                writer.write(params.getString("heading_prefix"));
                if (headingFlag > 0) {
                    writer.write(Character.toUpperCase(currentHeading));
                } else {
                    writer.write(Character.toLowerCase(currentHeading));
                }
                writer.write(params.getString("heading_suffix"));
            }
            writer.write(item1);
            writer.write(e.getValue());
            writer.write(params.getString("delim_1"));
            List<PageReference> pages = e.getPages();
            for (PageReference page : pages) {
                String encap = page.getEncap();
                if (encap != null) {
                    String encapPrefix = params.getString("encap_prefix");
                    writer.write(encapPrefix);
                    writer.write(encap);
                    writer.write(params.getString("encap_infix"));
                    writer.write(page.getPage());
                    writer.write(params.getString("encap_suffix"));
                } else {
                    writer.write(page.getPage());
                }
            }
        }

        writer.write(params.getString("postamble"));
        return count;
    }

}
