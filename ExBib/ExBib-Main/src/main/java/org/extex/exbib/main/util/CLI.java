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

package org.extex.exbib.main.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.text.MessageFormat;

import org.extex.exbib.core.i18n.Messages;

/**
 * This is the abstract base class for all command line interfaces of the ExBib
 * family.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public abstract class CLI extends Messages {

    /**
     * Getter for the year of the release.
     * 
     * @return the year
     */
    protected static int getYear() {

        return Integer
            .parseInt("$Date: 2003/11/10 07:04:14 $".substring(7, 11));
    }

    /**
     * Send the copyright message to the given print stream. The copyright
     * message is stored in a file which is located with the help of the class
     * loader.
     * 
     * @param writer the output writer
     * @param copying
     */
    protected static void printCopying(PrintStream writer, String copying) {

        URL url = CLI.class.getClassLoader().getResource(copying);

        if (url != null) {
            try {
                FileInputStream in = new FileInputStream(url.getPath());
                int c;

                while ((c = in.read()) >= 0) {
                    writer.write(c);
                }
            } catch (FileNotFoundException e) {
                writer
                    .println(MessageFormat.format(Messages
                        .format("CLI.File_not_found"), new Object[]{url
                        .getPath()}));
            } catch (IOException e) {
                writer.println(e.getMessage());
            }
        } else {
            writer.println(MessageFormat.format(Messages
                .format("CLI.Resource_not_found"), new Object[]{copying}));
        }
    }

    /**
     * The field <tt>version</tt> contains the ...
     */
    private String version = "0.0";

    /**
     * Getter for the version number.
     * 
     * @return the version number
     */
    public String getVersion() {

        return version;
    }

    /**
     * Print the version number and a short copyright notice to the given
     * writer.
     * 
     * @param writer the target writer
     * @param progname the name of the program
     * 
     * @return 1
     */
    protected int printVersion(PrintStream writer, String progname) {

        int releaseYear = getYear();
        String year = (releaseYear <= 2002 ? "2002" : ("2002-" + releaseYear));
        writer.println(version(progname));
        writer.println(MessageFormat.format(Messages.format("CLI.Copyright"),
            new Object[]{year}));
        return 1;
    }

    /**
     * Print the usage information to the given writer.
     * 
     * @param writer the target writer
     * @param progname the name of the program
     * 
     * @return 1
     */
    protected int usage(PrintStream writer, String progname) {

        writer.println(version(progname));
        writer.println(MessageFormat.format(Messages
            .format(progname + ".Usage"), new Object[]{progname}));
        return 1;
    }

    /**
     * Return the version number as String.
     * 
     * @param progname the name of the program
     * 
     * @return the version number string
     */
    protected String version(String progname) {

        return MessageFormat.format(Messages.format("CLI.Version"),
            new Object[]{progname, getVersion(),
                    System.getProperty("java.version")});
    }

}
