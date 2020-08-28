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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.type.OcpProgram;

/**
 * This class provides a main program to compile the contents of an otp file
 * into an ocp file.
 * 
 * <p>
 * The program takes as one argument the name of the otp file:
 * </p>
 * 
 * <pre class="CLI">
 *   java org.extex.ocpware.Otp2ocp &lang;<i>file</i>&rang;
 * </pre>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public final class Otp2Ocp {


    private Otp2Ocp() {

        //
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
        String outFile = null;

        for (int i = 0; i < args.length; i++) {
            if (file == null) {
                file = args[i];
            } else if (outFile == null) {
                outFile = args[i];
            } else {
                return err(err, "Otp2Ocp.TooManyArguments");
            }
        }

        if (file == null) {
            return err(err, "Otp2Ocp.NoFile");
        }
        if (outFile == null) {
            outFile = file.replaceAll("\\.[oO][tT][pP]$", "") + ".ocp";
        }

        CompilerState cs;
        InputStream stream = null;
        try {
            stream = new FileInputStream(new File(file));
            cs = new CompilerState(stream);
            stream.close();
        } catch (FileNotFoundException e) {
            return err(err, "Otp2Ocp.FileNotFound", file);
        } catch (Exception e) {
            return err(err, "Otp2Ocp.Error", e.getMessage());
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                return err(err, "Otp2Ocp.Error", e.getMessage());
            }
        }

        OcpProgram ocp;
        try {
            ocp = cs.compile();
        } catch (Exception e) {
            return err(err, "Otp2Ocp.Error", e.getMessage());
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(outFile);
            ocp.save(os);
        } catch (FileNotFoundException e) {
            return err(err, "Otp2Ocp.FileNotFound", outFile);
        } catch (Exception e) {
            return err(err, "Otp2Ocp.Error", e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                return err(err, "Otp2Ocp.Error", e.getMessage());
            }
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
                ResourceBundle.getBundle(Otp2Ocp.class.getName());
        try {
            String fmt = bundle.getString(msg);
            err.println(MessageFormat.format(fmt, a));
        } catch (MissingResourceException e) {
            err.println("***" + msg);
        }

        return -1;
    }

}
