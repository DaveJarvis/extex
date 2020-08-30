/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.site;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Extract the test results from a HTML page and produce a HTML fragment.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TestSummary extends Task {

    /**
     * Command line interface
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        TestSummary instance = new TestSummary();
        instance.setInput("overview-summary.html");

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.length() < 2) {
                System.err.println("Unknown argument: " + arg);
            } else if ("-verbose".startsWith(arg)) {
                instance.setVerbose(true);
            } else if ("-quiet".startsWith(arg)) {
                instance.setVerbose(false);
            } else if ("-ignoreMissing".startsWith(arg)) {
                instance.setIgnoreMissing(true);
            } else if ("-input".startsWith(arg)) {
                if (++i >= args.length) {
                    System.err.println("Missing file name for " + arg);
                    return;
                }
                instance.setInput(args[i]);
            } else if ("-output".startsWith(arg)) {
                if (++i >= args.length) {
                    System.err.println("Missing file name for " + arg);
                    return;
                }
                instance.setOutput(args[i]);
            } else {
                System.err.println("Unknown argument: " + arg);
            }
        }

        try {
            instance.execute();
        } catch (BuildException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * The field {@code ignoreMissing} contains the indicator tom ignore
     * missing input files.
     */
    private boolean ignoreMissing = false;

    /**
     * The field {@code verbose} contains the verbosity indicator.
     */
    private boolean verbose = false;

    /**
     * The field {@code input} contains the input file name.
     */
    private String input = null;

    /**
     * The field {@code output} contains the output file name.
     */
    private String output = null;

    /**
     * Produce HTML code for a bar.
     * 
     * @param stream the output stream
     * @param value the width
     * @param img the image to use
     */
    private void bar(PrintStream stream, int value, String img) {

        if (value > 0) {
            stream.print("<img src=\"../image/s-" + img + ".png\" width=\"");
            stream.print(value);
            stream.print("\" height=\"24\"\n/>");
        }
    }

    /**
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() {

        StringBuilder sb = readFile();
        int i = sb.indexOf("href=\"all-tests.html\"");
        if (i >= 0) {
            sb.delete(0, i - 1);
        }
        Matcher m =
                Pattern.compile(
                    ".*href=\"all-tests.html\"[^>]*>" + "([0-9]*)"
                            + ".*href=\"alltests-errors.html\"[^>]*>"
                            + "([0-9]+)"
                            + ".*href=\"alltests-fails.html\"[^>]*>"
                            + "([0-9]+)" + ".*[^0-9.]" + "([0-9.]+)"
                            + "%</td><td>" + "([0-9.]+)"
                            + ".*", Pattern.DOTALL).matcher(sb);

        int o = 0;
        int g = 100;
        int r = 0;
        String no = "0";
        String rate = "0";

        if (m.matches()) {
            no = sb.substring(m.start(1), m.end(1));
            int n = Integer.parseInt(no);
            int e1 = Integer.parseInt(sb.substring(m.start(2), m.end(2)));
            int f = Integer.parseInt(sb.substring(m.start(3), m.end(3)));
            rate = sb.substring(m.start(4), m.end(4));

            g = (512 * (n - e1 - f)) / n;
            o = (512 * f) / n;
            r = (512 * e1) / n;
        } else {
            log("No match");
        }

        try {
            PrintStream stream = (output == null
                    ? System.out
                    : new PrintStream(new FileOutputStream(output)));

            stream.print("<div>Number of tests: " + no + "</div>\n");
            stream.print("<div>Success rate: " + rate + "%</div>\n");
            stream.print("<div>\n");

            bar(stream, 1, "gray");
            bar(stream, g, "green");
            bar(stream, o, "orange");
            bar(stream, r, "red");
            bar(stream, 1, "gray");

            stream.print("</div>");

            if (stream != System.out) {
                stream.close();
            }
        } catch (IOException e) {
            throw new BuildException(e.getLocalizedMessage());
        }
    }

    /**
     * Read the contents of the file.
     * 
     * @return the buffer with the contents
     */
    private StringBuilder readFile() {

        if (input == null) {
            throw new BuildException("No input set.");
        }
        StringBuilder sb = new StringBuilder();
        Reader reader;
        try {
            reader = new FileReader(input);
        } catch (FileNotFoundException e) {
            if (ignoreMissing) {
                if (verbose) {
                    log("missing input file ignored");
                }
                return sb;
            }
            throw new BuildException(e.getLocalizedMessage());
        }
        try {
            int c;
            while ((c = reader.read()) >= 0) {
                sb.append((char) c);
            }
            reader.close();
        } catch (IOException e) {
            throw new BuildException(e.getLocalizedMessage());
        }
        return sb;
    }

    /**
     * Setter for ignoreMissing.
     * 
     * @param ignoreMissing the ignoreMissing to set
     */
    public void setIgnoreMissing(boolean ignoreMissing) {

        this.ignoreMissing = ignoreMissing;
    }

    /**
     * Setter for input.
     * 
     * @param input the input to set
     */
    public void setInput(String input) {

        this.input = input;
    }

    /**
     * Setter for output.
     * 
     * @param output the output to set
     */
    public void setOutput(String output) {

        this.output = output;
    }

    /**
     * Setter for verbose.
     * 
     * @param verbose the verbose to set
     */
    public void setVerbose(boolean verbose) {

        this.verbose = verbose;
    }

}
