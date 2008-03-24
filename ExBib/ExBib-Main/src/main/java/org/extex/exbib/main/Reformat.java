/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.extex.exbib.core.io.StreamWriter;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.bibio.BibPrinterFactory;
import org.extex.exbib.core.io.bibio.BibReader;
import org.extex.exbib.core.io.bibio.BibReader099Impl;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;

/**
 * ...
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class Reformat {

    /**
     * The field <tt>PROGNAME</tt> contains the name of the program.
     */
    private static String PROGNAME = "reformat";

    /**
     * The main program.
     * 
     * @param argv list of command line parameters
     */
    public static void main(String[] argv) {

        run(argv);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param argv
     */
    private static void run(String[] argv) {

        boolean verbose = false;
        boolean quiet = false;
        String outfile = null;
        String logfile = null;
        String file = null;

        for (int i = 0; i < argv.length; i++) {
            if (argv[i].startsWith("-")) {
                if ("-help".startsWith(argv[i])) {
                    // usage(System.err, PROGNAME);
                    System.exit(0);
                    // } else if ("-copying".startsWith(argv[i])) {
                    // printCopying(System.err, PROGNAME);
                    // System.exit(0);
                } else if ("-quiet".startsWith(argv[i])) {
                    quiet = true;
                } else if ("-terse".startsWith(argv[i])) {
                    quiet = true;
                } else if ("-verbose".startsWith(argv[i])) {
                    verbose = true;
                } else if ("-version".startsWith(argv[i])) {
                    // printVersion(System.err, PROGNAME);
                    System.exit(0);
                } else if ("-release".startsWith(argv[i])) {
                    // System.out.print(getVersion());
                    System.exit(0);
                } else if ("-logfile".startsWith(argv[i])
                        && (i < (argv.length - 1))) {
                    logfile = argv[++i];
                } else if ("-outfile".startsWith(argv[i])
                        && (i < (argv.length - 1))) {
                    outfile = argv[++i];
                } else {
                    System.err.println("Unknown option ignored: " + argv[i]);
                }
            } else if (file == null) {
                file = argv[i];
            } else {
                System.err.println("bcd: Need exactly one file argument.\n"
                        + "Try `bcd --help' for more information.");
                System.exit(1);
            }
        }

        if (file == null) {
            System.err.println("bcd: Need exactly one file argument.\n"
                    + "Try `bcd --help' for more information.");
            System.exit(1);
        }

        Writer writer = null;

        try {
            if (outfile == null) {
                writer = new StreamWriter(System.out, null);
                System.err.print("The output file: stdout \n");
            } else if (outfile.equals("")) {
                System.err.print("The output is discarted.\n");
                writer = new NullWriter(null);
            } else if (outfile.equals("-")) {
                System.err.print("The output is sent to stdout.\n");
                writer = new StreamWriter(System.out, null);
            } else {
                System.err.print("The output file: " + outfile + "\n");
                writer = new StreamWriter(outfile, null);
            }
        } catch (FileNotFoundException e) {
            System.err.println("The output file could not be opened: "
                    + outfile);
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported Encoding for " + outfile);
        }

        DB db = null;

        try {
            BibReader reader = new BibReader099Impl();
            db = new DBImpl();
            reader.open(file);
            db.load(file, null, null);

            Configuration cfg =
                    ConfigurationFactory.newInstance("config/bcd.xml")
                        .getConfiguration("BibPrinter", "xml");

            new BibPrinterFactory(cfg).newInstance(writer, null).print(db);
        } catch (Exception e) {
            System.err.print("*** " + e.toString());
            System.exit(1);
        }
    }
}
