/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exdoc.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Copy {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param resource the name of the resource
     * @param file the name of the file
     * @param map
     * 
     * @throws IOException in case of an I/O error
     */
    public static void mapped(String resource, File file, Map<String, String> map)
            throws IOException {

        InputStream stream =
                Copy.class.getClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            throw new FileNotFoundException(resource);
        }
        BufferedOutputStream out =
                new BufferedOutputStream(new FileOutputStream(file));
        try {
            boolean scan = true;
            StringBuilder tag = new StringBuilder();
            for (int c = stream.read(); c >= 0; c = stream.read()) {
                if (scan) {
                    if (c == '&') {
                        scan = false;
                    }
                } else if (c != ';') {
                    tag.append((char) c);
                } else {
                    scan = true;
                    String t = tag.toString();
                    String repl = map.get(t);
                    if (repl != null) {
                        out.write(repl.getBytes());
                    } else {
                        out.write('&');
                        out.write(t.getBytes());
                        out.write(';');
                    }
                    tag.delete(0, tag.length());
                }
                out.write(c);
            }
        } finally {
            stream.close();
            out.close();
        }
    }

}
