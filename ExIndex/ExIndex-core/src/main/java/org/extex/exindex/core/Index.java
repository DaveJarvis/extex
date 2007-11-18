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

package org.extex.exindex.core;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.type.Entry;
import org.extex.exindex.core.type.PageReference;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Index {

    /**
     * The field <tt>params</tt> contains the parameters.
     */
    private Parameters params;

    /**
     * The field <tt>content</tt> contains the ...
     */
    private List<Entry> content = new ArrayList<Entry>();

    /**
     * Creates a new object.
     * 
     * @throws IOException
     */
    public Index() throws IOException {

        super();
        params = Parameters.load();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param entry
     */
    public void add(Entry entry) {

        content.add(entry);
    }

    /**
     * Getter for params.
     * 
     * @return the params
     */
    public Parameters getParams() {

        return params;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param reader the reader
     * 
     * @return
     * 
     * @throws IOException in case of an I/O error
     */
    public int[] loadStyle(Reader reader) throws IOException {

        return Parameters.load(reader, params);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param w the writer
     * @param logger the logger
     * 
     * @return the number of lines and the number of warnings produced
     * 
     * @throws IOException in case of an I/O error
     */
    public int[] print(Writer w, Logger logger) throws IOException {

        String item1 = params.getString("item_1");
        int headingFlag = params.getNumber("heading_flag");
        int[] count = new int[2];
        char currentHeading = '\0';

        w.write(params.getString("preamble"));

        for (Entry e : content) {
            if (headingFlag != 0 && e.getHeading() != currentHeading) {
                currentHeading = e.getHeading();
                w.write(params.getString("heading_prefix"));
                if (headingFlag > 0) {
                    w.write(Character.toUpperCase(currentHeading));
                } else {
                    w.write(Character.toLowerCase(currentHeading));
                }
                w.write(params.getString("heading_suffix"));
            }
            w.write(item1);
            w.write(e.getValue());
            w.write(params.getString("delim_1"));
            List<PageReference> pages = e.getPages();
            for (PageReference page : pages) {
                String encap = page.getEncap();
                if (encap != null) {
                    String encapPrefix = params.getString("encap_prefix");
                    w.write(encapPrefix);
                    w.write(encap);
                    w.write(params.getString("encap_infix"));
                    w.write(page.getPage());
                    w.write(params.getString("encap_suffix"));
                } else {
                    w.write(page.getPage());
                }
            }
        }

        w.write(params.getString("postamble"));
        return count;
    }

}
