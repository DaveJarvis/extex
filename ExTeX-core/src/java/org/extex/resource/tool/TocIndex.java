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

package org.extex.resource.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Traverse a directory tree and collect the files in an index.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TocIndex {

    /**
     * Command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PrintStream err = System.err;
        PrintStream out = System.out;
        TocIndex tocIndex = new TocIndex();
        File base = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (!arg.startsWith("-")) {
                if (base != null) {
                    err.println("Too many bases");
                    System.exit(-1);
                }
                base = new File(arg);
            } else if ("".equals(arg)) {
                // silently ignored
            } else if ("-".equals(arg)) {
                // silently ignored
            } else if (arg.startsWith("-omit=")) {
                tocIndex.omit(arg.substring(6));
            } else if ("-output".startsWith(arg)) {
                if (i < args.length) {
                    arg = args[++i];
                    try {
                        out = new PrintStream(new FileOutputStream(arg));
                    } catch (FileNotFoundException e) {
                        err.print(arg);
                        err.print(" ");
                        err.println(e.getLocalizedMessage());
                    }
                }
            } else {
                err.println("Unknown argument: " + arg);
                System.exit(-1);
            }
        }

        try {
            out.println("#");
            out.print("# Created ");
            out.println(new Date().toString());
            out.println("#");
            tocIndex.collect(base != null ? base : new File("."), out);
        } catch (MalformedURLException e) {
            e.printStackTrace(err);
            System.exit(-1);
        }
        System.exit(0);
    }

    /**
     * The field <tt>omit</tt> contains the omit patterns.
     */
    private List<String> omit = new ArrayList<String>();

    /**
     * Recursive procedure to traverse a directory tree and collect the files
     * for the index.
     * 
     * @param file the current file to consider
     * @param out the output stream
     * 
     * @throws MalformedURLException just in case
     */
    private void collect(File file, PrintStream out)
            throws MalformedURLException {

        String f = file.toURL().getFile();
        f = f.substring(f.indexOf("/./") + 3);

        int n = omit.size();
        for (int i = 0; i < n; i++) {
            if (f.matches(omit.get(i))) {
                return;
            }
        }

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (int i = 0; i < list.length; i++) {
                collect(list[i], out);
            }
        } else if (file.isFile()) {
            out.print(file.getName());
            out.print("=");
            out.println(f);
        }
    }

    /**
     * Add a pattern to the omit patterns.
     * 
     * @param arg the pattern to omit
     */
    private void omit(String arg) {

        omit.add(arg);
    }

}
