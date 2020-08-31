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

package org.extex.main.tex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamObserver;
import org.extex.engine.backend.NamedOutputStream;
import org.extex.engine.backend.OutputFactory;
import org.extex.scanner.stream.observer.file.OpenFileObserver;

/**
 * This observer records that a certain file has been opened.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class FileRecorder implements OpenFileObserver, OutputStreamObserver {

    /**
     * The field {@code outputFactory} contains the output factory.
     */
    private OutputFactory outputFactory = null;

    /**
     * The field {@code recorded} contains the recorded files.
     */
    private final List<String> recorded = new ArrayList<String>();


    public FileRecorder() {

    }

    /**
     * Print the recorded files to a file or stream.
     * 
     * @param out the print stream
     *
     * @throws IOException in case of an I/O error
     */
    private void print(PrintStream out) throws IOException {

        String cwd = new File(".").getCanonicalPath();
        out.print("PWD ");
        out.print(cwd);
        out.print("\n");

        for (String s : recorded) {
            out.print(s);
            out.print("\n");
        }
    }

    /**
     * Print the recorded files to a file or stream.
     * 
     * @param name the name of the output file or {@code null} for stdout
     * 
     * @throws IOException in case of an I/O error
     * @throws DocumentWriterException in case of an error in the output factory
     */
    public void print(String name) throws IOException, DocumentWriterException {

        if (name == null) {
            print(System.out);
            return;
        }

        PrintStream out = new PrintStream((outputFactory != null 
                ? outputFactory.getOutputStream(name, "fls")
                : new FileOutputStream(name + ".fls")));
        print(out);
        out.close();
    }

    /**
     * Setter for outputFactory.
     * 
     * @param outputFactory the outputFactory to set
     */
    public void setOutputFactory(OutputFactory outputFactory) {

        this.outputFactory = outputFactory;
    }

    /**
*      java.lang.String, java.io.InputStream)
     */
    public void update(String name, String filetype, InputStream stream) {

        if (name != null) {
            recorded.add("INPUT " + name);
        }
    }

    /**
*      java.lang.String, java.io.OutputStream)
     */
    public void update(String name, String type, OutputStream stream) {

        if (stream instanceof NamedOutputStream) {
            String s = ((NamedOutputStream) stream).getName();
            if (s != null) {
                recorded.add("OUTPUT " + s);
                return;
            }
        }
        if (name != null) {
            recorded.add("OUTPUT " + name + "." + type);
        }
    }

}
