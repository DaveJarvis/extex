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

package org.extex.doc.writer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AbstractDocWriter {

    /**
     * The field {@code targetDir} contains the ...
     */
    private String targetDir = ".";


    public AbstractDocWriter() {

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param lang the language id
     * @param name the name
     * 
     * @throws IOException in case of an I/O error
     */
    protected void copy(String lang, String name) throws IOException {

        InputStream is =
                getClass().getClassLoader().getResourceAsStream(
                    "org/extex/doc/writer/html/" + lang + "/" + name);
        if (is == null) {
            throw new FileNotFoundException("org/extex/doc/writer/html/" + lang
                    + "/" + name);
        }
        InputStream stream = new BufferedInputStream(is);

        try {
            OutputStream out = openOutputStream(name);

            try {
                for (int c = stream.read(); c >= 0; c = stream.read()) {
                    out.write(c);
                }
            } finally {
                out.close();
            }
        } finally {
            stream.close();
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param lang the language id
     * @param name the name
     * @param eh the handler for entities
     * 
     * @throws IOException in case of an I/O error
     */
    protected void copyTemplate(String lang, String name, EntityHandler eh)
            throws IOException {

        Calendar cal = Calendar.getInstance();
        InputStream stream = openTemplate(lang, name);

        try {
            OutputStream out = openOutputStream(name);

            try {
                for (int c = stream.read(); c >= 0; c = stream.read()) {
                    if (c == '&') {
                        StringBuilder sb = new StringBuilder();
                        for (c = stream.read(); c >= 0 && c != ';'; c =
                                stream.read()) {
                            sb.append((char) c);
                        }
                        String s = sb.toString();
                        if ("year".equals(s)) {
                            out.write(Integer.toString(cal.get(Calendar.YEAR))
                                .getBytes());
                        } else if ("month".equals(s)) {
                            out.write(Integer.toString(cal.get(Calendar.MONTH))
                                .getBytes());
                        } else if ("day".equals(s)) {
                            out.write(Integer.toString(
                                cal.get(Calendar.DAY_OF_MONTH)).getBytes());
                        } else if (eh != null && eh.handle(s, out)) {
                            // done
                        } else {
                            out.write('&');
                            out.write(s.getBytes());
                            out.write(';');
                        }

                    } else {
                        out.write(c);
                    }
                }
            } finally {
                out.close();
            }
        } finally {
            stream.close();
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name ...
     * @return ...
     * 
     * @throws FileNotFoundException in case of an error
     */
    protected OutputStream openOutputStream(String name)
            throws FileNotFoundException {

        OutputStream out =
                new BufferedOutputStream(new FileOutputStream(new File(
                    targetDir, name)));
        return out;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param lang ...
     * @param name ...
     * 
     * @return ...
     * 
     * @throws FileNotFoundException in case of an error
     */
    protected InputStream openTemplate(String lang, String name)
            throws FileNotFoundException {

        String s =
                getClass().getName().replaceAll("[^.]$", "")
                    .replaceAll("\\.", "/")
                        + lang + "/" + name;
        InputStream stream = getClass().getClassLoader().getResourceAsStream(s);
        if (stream == null) {
            throw new FileNotFoundException(s);
        }
        return new BufferedInputStream(stream);
    }

    /**
     * Setter for the target.
     * 
     * @param targetDir the target
     */
    public void setTarget(String targetDir) {

        this.targetDir = targetDir;
    }

}
