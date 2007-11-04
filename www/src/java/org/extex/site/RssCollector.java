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
import java.io.IOException;
import java.io.PrintStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Collect the news and make an RSS feed.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class RssCollector extends Task {

    /**
     * The field <tt>verbose</tt> contains the verbosity indicator.
     */
    private static boolean verbose = false;

    /**
     * The command line interface.
     * 
     * @param args the command line arguments
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
            process(in, out);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Perform the real action.
     * 
     * @param in the optional input file. If <code>null</code> then reading
     *        from stdin
     * @param out the optional output file. If <code>null</code> then writing
     *        to stdout
     * 
     * @throws IOException in case of an error
     */
    private static void process(String in, String out) throws IOException {

        // TODO ???

        PrintStream stream = System.out;
        if (out != null) {
            stream = new PrintStream(new FileOutputStream(out));
        }

        stream.print("<div>\n");

        stream.print("</div>");

        if (stream != System.out) {
            stream.close();
        }
    }

    /**
     * The field <tt>input</tt> contains the optional input file.
     */
    private String input = null;

    /**
     * The field <tt>output</tt> contains the optional output file.
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
