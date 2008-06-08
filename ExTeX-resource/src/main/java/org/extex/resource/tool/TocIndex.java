/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Traverse a directory tree and collect the files in an index.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TocIndex {

    /**
     * The main program. The command line parameters are evaluated and the
     * appropriate actions are performed. Exceptions are caught and reported to
     * stderr.
     * 
     * @param argv list of command line parameters
     * 
     * @return the exit code
     */
    public static int commandLine(String[] argv) {

        try {
            new TocIndex().processCommandLine(argv);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return -1;
        }
        return 0;
    }

    /**
     * Command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(commandLine(args));
    }

    /**
     * The field <tt>omit</tt> contains the omit patterns.
     */
    private List<Pattern> omit = new ArrayList<Pattern>();

    /**
     * The field <tt>base</tt> contains the base directory.
     */
    private File base = null;

    /**
     * The field <tt>outFile</tt> contains the name of the output file.
     */
    private String outFile = null;

    /**
     * Creates a new object.
     */
    public TocIndex() {

        super();
    }

    /**
     * Recursive procedure to traverse a directory tree and collect the files
     * for the index.
     * 
     * @param file the current file to consider
     * @param out the output stream
     * @param strip
     */
    private void collect(File file, PrintStream out, int strip) {

        String f = file.toString().substring(strip).replace('\\', '/');

        for (Pattern p : omit) {
            if (p.matcher(f).matches()) {
                return;
            }
        }

        if (file.isDirectory()) {
            for (File fi : file.listFiles()) {
                collect(fi, out, strip);
            }
        } else if (file.isFile()) {
            printQuoted(out, file.getName());
            out.print("=");
            printQuoted(out, f);
            out.println();
        }
    }

    /**
     * Get a message for an exception.
     * 
     * @param tag the tag
     * @param args the arguments for the positional parameters
     * 
     * @return the formatted string
     */
    private String getMessage(String tag, Object... args) {

        ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
        return MessageFormat.format(bundle.getString(tag), args);
    }

    /**
     * Add a pattern to the omit patterns.
     * 
     * @param arg the pattern to omit
     * 
     * @throws PatternSyntaxException in case of an error in the regular
     *         expression
     */
    private void omit(String arg) throws PatternSyntaxException {

        omit.add(Pattern.compile(arg));
    }

    /**
     * Print a string with quoted characters for properties files.
     * 
     * @param out the print stream
     * @param s the string to print
     */
    private void printQuoted(PrintStream out, String s) {

        out.print(s.replaceAll("\\([#=:\\\\]\\)", "\\$1"));
    }

    /**
     * Process the list of string as command line parameters.
     * 
     * @param argv the command line parameters
     * 
     * @throws Exception in case of an error
     */
    public void processCommandLine(String[] argv) throws Exception {

        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];
            if ("".equals(arg)) {
                // silently ignored
            } else if (!arg.startsWith("-")) {
                setBase(new File(arg));
            } else if ("-".equals(arg) || "--".equals(arg)) {
                if (++i >= argv.length) {
                    throw new Exception(getMessage("MissingArgument", arg));
                }
                setBase(new File(argv[i]));
            } else if (arg.startsWith("-omit=") || arg.startsWith("--omit=")) {
                try {
                    omit(arg.substring(6));
                } catch (PatternSyntaxException e) {
                    throw new Exception(getMessage("PatternProblem", arg
                        .substring(6)));
                }
            } else if ("-output".startsWith(arg) || "--output".startsWith(arg)) {
                setOutFile(++i >= argv.length ? null : argv[i]);
            } else if (arg.startsWith("-output=")) {
                setOutFile(arg.substring(8));
            } else if (arg.startsWith("--output=")) {
                setOutFile(arg.substring(9));
            } else if ("-help".startsWith(arg) || "--help".startsWith(arg)) {
                System.err.println(getMessage("usage"));
                return;
            } else {
                throw new Exception(getMessage("UnknownArgument", arg));
            }
        }

        run();
    }

    /**
     * Initialize the creation of the index and perform the creation itself.
     * 
     * @throws Exception in case of an error
     */
    public void run() throws Exception {

        if (base != null && !base.isDirectory()) {
            throw new Exception(getMessage("IllegalBase", base));
        }

        if ("".equals(outFile)) {
            outFile = null;
        }
        PrintStream out;
        try {
            out = (outFile == null //
                    ? System.out
                    : new PrintStream(new FileOutputStream(outFile)));
        } catch (FileNotFoundException e) {
            throw new Exception(getMessage("OutFileNotFound", outFile));
        }
        try {
            out.println("#");
            out.print("# Created ");
            out.println(new Date().toString());
            out.println("#");
            File file = base != null ? base : new File(".");
            collect(file, out, file.toString().length());
        } finally {
            if (outFile != null) {
                out.close();
            }
        }
    }

    /**
     * Setter for base.
     * 
     * @param base the base to set
     */
    public void setBase(File base) {

        if (this.base != null) {
            throw new RuntimeException(getMessage("TooManyBases"));
        }
        this.base = base;
    }

    /**
     * Setter for outFile.
     * 
     * @param outFile the outFile to set
     * 
     * @throws RuntimeException in case the argument is <code>null</code>
     */
    public void setOutFile(String outFile) throws RuntimeException {

        if (outFile == null) {
            throw new RuntimeException(getMessage("MissingArgument", "-output"));
        }
        this.outFile = outFile;
    }

}
