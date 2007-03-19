/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.site.report;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Summary {

    /**
     * The command line interface.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        String directory = ".";

        int i = 0;

        for (; i < args.length; i++) {
            String a = args[i];
            if ("-directory".startsWith(a)) {
                if (++i < args.length) {
                    directory = args[i];
                }
            } else {
                // ...
            }
        }

        File dir = new File(directory);
        String[] list = dir.list(new FilenameFilter() {

            /**
             * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
             */
            public boolean accept(final File dir, final String name) {

                return name.endsWith(".xml");
            }
        });
        List l = new ArrayList();
        for (int j = 0; j < list.length; j++) {
            l.add(list[j]);
        }

        Collections.sort(l, new Comparator() {

            /**
             * Compare two objects.
             *
             * @param o1 the first object
             * @param o2 the second object
             *
             * @return ...
             *
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare(final Object o1, final Object o2) {

                return ((String) o1).compareTo((String) o2);
            }
        });

        PrintStream stream = System.out;
        stream.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        stream.print("<summary name=\"");
        stream.print(dir.getAbsolutePath());
        stream.print("\" date=\"");
        stream.print(new Date().toString());
        stream.println("\">");

        for (int j = 0; j < list.length; j++) {
            stream.print("  <file name=\"");
            stream.print(l.get(j));
            stream.println("\"/>");
        }
        stream.println("</summary>");
    }

    /**
     * Creates a new object.
     */
    private Summary() {

        super();
    }

}
