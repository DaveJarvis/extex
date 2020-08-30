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

package org.extex.ocpware;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.extex.ocpware.type.OcpProgram;
import org.extex.ocpware.writer.OcpExTeXWriter;
import org.extex.ocpware.writer.OcpOmegaWriter;
import org.extex.ocpware.writer.OcpOmegaWriter2;
import org.extex.ocpware.writer.OcpWriter;

/**
 * This class provides a main program to print the contents of an ocp file.
 * 
 * <p>
 * The program takes as one argument the name of the ocp file and optionally one
 * file as output file name.
 * </p>
 * 
 * <pre class="CLI">
 *   java org.extex.ocpware.Otp2ocp &lang;options&rang; &lang;<i>file</i>&rang; &lang;<i>outfile</i>&rang;
 * </pre>
 * 
 * The <i>outfile</i> is optional and defaults to the input file with the
 * suffix {@code .otp} deleted and {@code .ocp} appended.
 * 
 * <p>
 * The following options are recognized:
 * </p>
 * <dl>
 * <dt>-extex</dt>
 * <dd>Use the output format native to ExTeX</dd>
 * <dt>-omega</dt>
 * <dd>Use an output format compatible to the one of 6Omega;</dd>
 * <dt>-omega2</dt>
 * <dd>Use an output format which is a reduced version of the one of 6Omega;</dd>
 * </dl>
 * 
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class OutOcp {


    private OutOcp() {


    }

    /**
     * This is the command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(main(args, System.out, System.err));
    }

    /**
     * Process the command line options and return the exit code. As side effect
     * an error message might be written to the error stream.
     * 
     * @param args the command line arguments
     * @param out the output stream
     * @param err the error stream
     * 
     * @return the exit code
     */
    public static int main(String[] args, PrintStream out, PrintStream err) {

        String file = null;
        OcpWriter writer = new OcpOmegaWriter();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-omega")) {
                writer = new OcpOmegaWriter();
            } else if (args[i].equals("-omega2")) {
                writer = new OcpOmegaWriter2();
            } else if (args[i].equals("-extex")) {
                writer = new OcpExTeXWriter();
            } else if (file != null) {
                return err(err, "OutOcp.TooManyArguments");
            } else {
                file = args[i];
            }
        }

        if (file == null) {
            return err(err, "OutOcp.NoFile");
        }

        try {
            InputStream stream = new FileInputStream(new File(file));

            OcpProgram ocp = OcpProgram.load(stream);
            stream.close();
            writer.write(out, ocp);

        } catch (FileNotFoundException e) {
            return err(err, "OutOcp.FileNotFound", file);
        } catch (Exception e) {
            return err(err, "OutOcp.Error", e.getMessage());
        }

        return 0;
    }

    /**
     * Print a localized error message to the error stream.
     * 
     * @param err the error stream
     * @param msg the key for the message
     * @param a the array of arguments
     * 
     * @return the exit code (-1)
     */
    private static int err(PrintStream err, String msg, Object... a) {

        ResourceBundle bundle =
                ResourceBundle.getBundle(OutOcp.class.getName());
        try {
            String fmt = bundle.getString(msg);
            err.println(MessageFormat.format(fmt, a));
        } catch (MissingResourceException e) {
            err.println("***" + msg);
        }

        return -1;
    }

}
