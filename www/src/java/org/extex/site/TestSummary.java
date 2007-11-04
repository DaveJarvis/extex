/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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
 * @version $Revision$
 */
public class TestSummary extends Task {

    /**
     * The field <tt>verbose</tt> contains the verbosity indicator.
     */
    private static boolean verbose = false;

    /**
     * Produce HTML code for a bar.
     * 
     * @param stream the output stream
     * @param value the width
     * @param img the image to use
     */
    private static void bar(PrintStream stream, int value, String img) {

        if (value > 0) {
            stream.print("<img src=\"../image/s-" + img + ".png\" width=\"");
            stream.print(value);
            stream.print("\" height=\"24\"\n/>");
        }
    }

    /**
     * Get the substring for a certain sub-match.
     * 
     * @param sb the data to extract from
     * @param m the matcher
     * @param i the index of the sub-match
     * 
     * @return the value of the sub-match
     */
    private static String get(StringBuffer sb, Matcher m, int i) {

        return sb.substring(m.start(i), m.end(i));
    }

    /**
     * Command line interface
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        String in = "overview-summary.html";
        String out = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.length() < 2) {
                System.err.println("Unknown argument: " + arg);
            } else if ("-verbose".startsWith(arg)) {
                verbose = true;
            } else if ("-quiet".startsWith(arg)) {
                verbose = false;
            } else if ("-input".startsWith(arg)) {
                i++;
                in = args[i];
            } else if ("-output".startsWith(arg)) {
                i++;
                out = args[i];
            } else {
                System.err.println("Unknown argument: " + arg);
            }
        }

        try {
            new TestSummary().process(in, out);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * The field <tt>input</tt> contains the input file name.
     */
    private String input = null;

    /**
     * The field <tt>output</tt> contains the output file name.
     */
    private String output = null;

    /**
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() {

        if (input == null) {
            throw new BuildException("No input set.");
        }
        try {
            process(input, output);
        } catch (IOException e) {
            throw new BuildException(e.getLocalizedMessage());
        }
    }

    /**
     * Process the input and produce the output.
     * 
     * @param in the input file name
     * @param out the output file name
     * 
     * @throws IOException in case of an error
     */
    private void process(String in, String out) throws IOException {

        StringBuffer sb = new StringBuffer();
        Reader reader = new FileReader(in);
        int c;
        while ((c = reader.read()) >= 0) {
            sb.append((char) c);
        }
        reader.close();

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
                            + "%</td><td>" + "([0-9.]+)" //
                            + ".*", Pattern.DOTALL).matcher(sb);

        int o = 0;
        int g = 100;
        int r = 0;
        String no = "0";
        String rate = "0";

        if (m.matches()) {
            no = get(sb, m, 1);
            int n = Integer.parseInt(no);
            int e = Integer.parseInt(get(sb, m, 2));
            int f = Integer.parseInt(get(sb, m, 3));
            rate = get(sb, m, 4);

            g = (512 * (n - e - f)) / n;
            o = (512 * f) / n;
            r = (512 * e) / n;
        } else {
            log("No match");
        }

        PrintStream stream = System.out;
        if (out != null) {
            stream = new PrintStream(new FileOutputStream(out));
        }

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

}
