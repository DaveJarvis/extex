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
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TestSummary extends Task {

    /**
     * The field <tt>verbose</tt> contains the ...
     */
    private static boolean verbose = false;

    /**
     * TODO gene: missing JavaDoc
     *
     * @param args
     */
    public static void main(final String[] args) {

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
            process(in, out);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param in
     * @param out
     *
     * @throws IOException in case of an error
     */
    private static void process(final String in, final String out)
            throws IOException {

        StringBuffer sb = new StringBuffer();
        Reader reader = new FileReader(in);
        int c;
        while ((c = reader.read()) >= 0) {
            sb.append((char) c);
        }
        reader.close();

        Matcher m =
                Pattern
                    .compile(
                        ".*<td><a href=\"all-tests.html\"[^>]*\">"
                                + "([0-9]*)"
                                + "</a></td><td><a href=\"alltests-errors.html\"[^>]*>"
                                + "([0-9]+)"
                                + "</a></td><td><a href=\"alltests-fails.html\"[^>]*>"
                                + "([0-9]+)" + "</a></td><td>" + "([0-9.]+)"
                                + "%</td><td>" + "([0-9.]+)" + "</td>" + ".*",
                        Pattern.DOTALL).matcher(sb);

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
     * TODO gene: missing JavaDoc
     *
     * @param stream
     * @param value
     * @param img
     */
    private static void bar(PrintStream stream, int value, String img) {

        if (value > 0) {
            stream.print("<img src=\"../image/s-" + img + ".png\" width=\"");
            stream.print(value);
            stream.print("\" height=\"24\"\n/>");
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param sb
     * @param m
     * @param i
     * @return
     */
    private static String get(final StringBuffer sb, final Matcher m,
            final int i) {

        return sb.substring(m.start(i), m.end(i));
    }

    /**
     * The field <tt>input</tt> contains the ...
     */
    private String input = null;

    /**
     * The field <tt>output</tt> contains the ...
     */
    private String output = null;

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
     * @see org.apache.tools.ant.Task#execute()
     */
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

}
